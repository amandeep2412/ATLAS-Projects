package com.amazon.internalclassifieds.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.internalclassifieds.model.Categories;
import com.amazon.internalclassifieds.model.Users;

public class CategoryDAO implements DAO<Categories> {

	DB db = DB.getInstance();
	
	public int insert(Categories object) {
		String sql = "INSERT INTO Category(title) VALUES('"+object.title+"')";	
		return db.executeSQL(sql);
	}

	public int update(Categories object) {
		String sql = "UPDATE Category set title = '"+object.title+"' where categoryID='"+object.categoryID+"'";
		return db.executeSQL(sql);
	}

	public int delete(Categories object) {
		String sql = "DELETE from Category where categoryID='"+object.categoryID+"'";
		return db.executeSQL(sql);
	}

	public List<Categories> retrieve() {
		String sql = "Select * from Category";
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Categories> categories = new ArrayList<Categories>();
		try {
			while(set.next()) {
				Categories category = new Categories();
				category.categoryID = set.getInt("categoryID");
				category.title = set.getString("title");
				category.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				categories.add(category);
			}
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
		return categories;
	}

	public List<Categories> retrieve(String sql) {
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Categories> categories = new ArrayList<Categories>();
		try {
			while(set.next()) {
				Categories category = new Categories();
				category.categoryID = set.getInt("categoryID");
				category.title = set.getString("title");
				category.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				categories.add(category);
			}
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
		return categories;
	}
	

}
