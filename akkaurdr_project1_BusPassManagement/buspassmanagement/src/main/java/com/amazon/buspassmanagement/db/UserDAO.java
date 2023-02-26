package com.amazon.buspassmanagement.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.User;
import com.amazon.buspassmanagement.db.passwordEncryptor;
//import com.amazon.buspassmanagement.db.DAO;
//will implement DAO interface.. type T is User
//every model will have a UserDAO layer.. route DAO like that

public class UserDAO implements DAO<User>{

	DB db = DB.getInstance();
	passwordEncryptor encryptor = passwordEncryptor.getInstance();
	
	public int insert(User object) { //insert into db
		
		String sql = "INSERT INTO USERS(name, phone,email,password,address,department,type)"
				+ "VALUES('"+object.name+"','"+object.phone+"','"+object.email+"' ,'"+encryptor.encryptor(object.password)+"',"
						+ "'"+object.address+"','"+object.department+"',"+object.type+")";
		
		return db.executeSQL(sql);
	}

	public int update(User object) { //update into db
		
		String sql = "UPDATE Users set name = '"+object.name+"', "
				+ "phone='"+object.phone+"', password='"+encryptor.encryptor(object.password)+"', address='"+object.address+"',"
						+ " department='"+object.department+"' WHERE email = '"+object.email+"'";
		return db.executeSQL(sql);
	}

	public int delete(User object) {
		
		String sql = "DELETE FROM Users WHERE email = '"+object.email+"'";
		return db.executeSQL(sql);
		
	}

	public List<User> retrieve() {
		
		return null;
	}

	public List<User> retrieve(String sql){
	
		ResultSet set = db.executeQuery(sql); 
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			while(set.next()) {
				User user = new User();
				
				//read the row from resultsset and put it into user object
				user.id = set.getInt("id");
				user.name = set.getString("name");
				user.phone = set.getString("phone");
				user.email = set.getString("email");
				user.password = set.getString("password");
				user.department = set.getString("department");
				user.type = set.getInt("type");
				user.createdOn = set.getString("createdOn");
				
				users.add(user); //adding data in arraylist
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("something went wrong: "+e);
		}
			
		return users;
	}
}
