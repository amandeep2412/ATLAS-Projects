package com.amazon.internalclassifieds.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.internalclassifieds.model.Classifieds;

public class ClassifiedDAO implements DAO<Classifieds>{

	DB db = DB.getInstance();
	
	public int insert(Classifieds Object) {
		String sql = "INSERT INTO Classifieds (productName, headline, brand, description, condition, userID, price, pictures, status, categoryID) Values ('"+Object.productName+"','"+Object.headline+"','"+Object.brand+"','"+Object.description+"','"+Object.condition+"',"+Object.userID+","+Object.price+",'"+Object.pictures+"',"+Object.status+","+Object.categoryID+")";
		return db.executeSQL(sql);
	}

	public int update(Classifieds Object) {
		String sql = "UPDATE Classifieds SET productName = '"+Object.productName+"', headline = '"+Object.headline+"', brand = '"+Object.brand+"', description = '"+Object.description+"', condition = '"+Object.condition+"', price = "+Object.price+", pictures = '"+Object.pictures+"', status = "+Object.status+", categoryID = "+Object.categoryID+" WHERE classifiedID = "+Object.classifiedID;
		return db.executeSQL(sql);
	}

	public int delete(Classifieds object) {
		String sql = "DELETE FROM Classifieds WHERE classifiedID = "+object.classifiedID;
		return db.executeSQL(sql);
	}

	public List<Classifieds> retrieve() {
		String sql = "Select * from Classifieds";
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Classifieds> classifieds = new ArrayList<Classifieds>();
		
		try {
			while(set.next()) {
				Classifieds classified = new Classifieds();
				classified.classifiedID = set.getInt("classifiedID");
				classified.productName = set.getString("productName");
				classified.brand = set.getString("brand");
				classified.categoryID = set.getInt("categoryID");
				classified.userID = set.getInt("userID");
				classified.description = set.getString("description");
				classified.headline = set.getString("headline");
				classified.price = set.getInt("price");
				classified.status = set.getInt("status");
				classified.lastUpdatedOn = set.getString("lastUpdatedOn");
				classified.pictures = set.getString("pictures");
				classified.condition = set.getString("condition");
				
				classifieds.add(classified);
			}
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
		
		return classifieds;
	}

	public List<Classifieds> retrieve(String sql) {
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Classifieds> classifieds = new ArrayList<Classifieds>();
		
		try {
			while(set.next()) {
				Classifieds classified = new Classifieds();
				classified.classifiedID = set.getInt("classifiedID");
				classified.productName = set.getString("productName");
				classified.brand = set.getString("brand");
				classified.categoryID = set.getInt("categoryID");
				classified.userID = set.getInt("userID");
				classified.description = set.getString("description");
				classified.headline = set.getString("headline");
				classified.price = set.getInt("price");
				classified.status = set.getInt("status");
				classified.lastUpdatedOn = set.getString("lastUpdatedOn");
				classified.pictures = set.getString("pictures");
				classified.condition = set.getString("condition");
				
				classifieds.add(classified);
			}
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
		
		return classifieds;
	}
	
}
