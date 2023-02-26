package com.amazon.buspassmanagement.controller;

import java.nio.channels.NonWritableChannelException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.amazon.buspassmanagement.db.RoutesDAO;
import com.amazon.buspassmanagement.db.UserDAO;
import com.amazon.buspassmanagement.db.passwordEncryptor;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.User;

public class AuthenticationService {
//singleton pattern......... constructor to go as private, and create method like instance which returns reference of the object
	//can't create more thn one proj of this class
	private static AuthenticationService service = new AuthenticationService();
	UserDAO dao = new UserDAO();
	
	passwordEncryptor encryptor = passwordEncryptor.getInstance();
	/*
	//dummy database in RAM
	//LinkedHashMap<Integer,User> users = new LinkedHashMap<Integer,User>();
	
	private AuthenticationService() {
		// TODO Auto-generated constructor stub
		
		User user1 = new User();
		user1.id = 1;
		user1.name = "Amandeep Kaur";
		user1.email = "aman@example.com";
		user1.password = "admin123";
		user1.type = 1;
		
		User user2 = new User();
		user2.id = 2;
		user2.name = "John Watson";
		user2.email = "john@example.com";
		user2.password = "john123";
		user2.type = 2;
		
		
		//dummy users in hashmap :)
		users.put(user1.id, user1);
		users.put(user2.id, user2);
		
		System.out.println("database details: ");
		System.out.println(users);
	}*/
	
	private AuthenticationService() {
		/*User user1 = new User();
		user1.id = 1;
		user1.name = "Amandeep Kaur";
		user1.phone = "+91 9876543210";
		user1.email = "aman@example.com";
		user1.password = "admin123";
		user1.address = "Patiala";
		user1.department = "admin";
		user1.type = 1;
		
		User user2 = new User();
		user2.id = 2;
		user2.name = "John Watson";
		user2.phone = "+91 9876643210";
		user2.email = "john@example.com";
		user2.password = "john123";
		user2.address = "Patiala";
		user2.department = "hr";
		user2.type = 2;
		
		//adding users into database
		UserDAO dao = new UserDAO();
		dao.insert(user1);
		dao.insert(user2);*/
		
	}
	
	static public AuthenticationService getInstance() {
		return service; //object
	}
	
	/*public boolean loginUser(User user) {
		
		boolean result = false;
		
	//iterating through linked hash map
		
		Iterator<Integer> itr = users.keySet().iterator();
		while(itr.hasNext()) {
			Integer key = itr.next();
			User u = users.get(key);
			
			//select * from user where email = ' and password = ''
			if(u.email.equals(user.email) && u.password.equals(user.password)) {
				//user will now point to a new object i.e. referred by you
				user.name = u.name;
				user.type = u.type;
				result = true;
				break;
			}
		}
		
		return result;
	}*/
	
	public boolean loginUser(User user) {
		boolean result = false;
		//retrieve and compare..
		String sql = "SELECT * from Users where email='"+user.email+"' AND password='"+encryptor.encryptor(user.password)+"'";
		List<User> users = dao.retrieve(sql);
		
		if(users.size()>0) {
			User u = users.get(0);
			user.id = u.id;
			user.name = u.name;
			user.phone = u.phone;
			user.email = u.email;
			user.address = u.address;
			user.department = u.department;
			user.type = u.type;
			user.createdOn = u.createdOn;
			return true;
		}
		return false;
	}
	
	public boolean registerUser(User user) {
		//int result = dao.insert(user);
		
		//return result>0;
		return dao.insert(user)>0;  //true
	}
	
	public boolean updateUser(User user) {
		
		
		//int result = dao.update(user);
		
		//return result>0;
		return dao.update(user)>0; //true - successfully query executed
	}
	
	
}
