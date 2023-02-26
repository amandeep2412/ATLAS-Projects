package com.amazon.buspassmanagement.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.Routes;


public class RoutesDAO implements DAO<Routes> {

	DB db = DB.getInstance();
	public int insert(Routes object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO ROUTES(title,description,adminID)"+
						"VALUES('"+object.title+"', '"+object.description+"', "+
						object.adminID+")";
		return db.executeSQL(sql);
		
	}

	public int update(Routes object) {
		// TODO Auto-generated method stub
		String sql = "UPDATE Routes set title='"+object.title+"', description='"+object.description+"', "
				+ "adminID='"+object.adminID+
				"' WHERE routeID = "+object.routeID;
		return db.executeSQL(sql);
	}

	public int delete(Routes object) {
		// TODO Auto-generated method stub
		String sql = "DELETE from ROUTES where routeID='"+object.routeID+"'";
		return db.executeSQL(sql);
	}

	public List<Routes> retrieve() {
		
		String sql = "Select * from ROUTES";
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Routes> routes = new ArrayList<Routes>();
		
		try {
			while(set.next()) {
				Routes route = new Routes();
				
				route.routeID = set.getInt("routeID");
				route.title = set.getString("title");
				route.description = set.getString("description");
				route.adminID = set.getInt("adminID");
				route.createdOn = set.getString("createdOn");
				
				routes.add(route);
			}
			
			
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
		return routes;
	}

	public List<Routes> retrieve(String sql) {
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Routes> routes = new ArrayList<Routes>();
		
		try {
			while(set.next()) {
				
				Routes route = new Routes();
				
				// Read the row from ResultSet and put the data into User Object
				route.routeID = set.getInt("routeID");
				route.title = set.getString("title");
				route.description = set.getString("description");
				route.adminID = set.getInt("adminID");
				route.createdOn = set.getString("createdOn");
				
				routes.add(route);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		return routes;
		
	}
	

}
