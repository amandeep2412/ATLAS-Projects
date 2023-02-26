package com.amazon.buspassmanagement.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;

public class StopsDAO implements DAO<Stops>{

	DB db = DB.getInstance();
	
	public int insert(Stops object) {
		
		String sql = "INSERT INTO Stops (address, sequenceOrder, routeID, adminID) "
				+ "VALUES ('"+object.address+"', '"+object.sequenceOrder+"',"
						+ " '"+object.routeID+"', "+object.adminID+")";
		return db.executeSQL(sql);
	}

	public int update(Stops object) {
		
		String sql = "UPDATE Stops set address='"+object.address+"', sequenceOrder='"+object.sequenceOrder+"', "
				+ "adminID='"+object.adminID+"' WHERE stopID = "+object.stopID;
		return db.executeSQL(sql);
	}

	public int delete(Stops object) {
		
		String sql = "DELETE FROM Stops WHERE routeID = '"+object.routeID+"'";
		return db.executeSQL(sql);
	}

	public List<Stops> retrieve() {
		String sql = "Select * from Stops";
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Stops> stops = new ArrayList<Stops>();
		try {
			while(set.next()) {
				
				Stops stop = new Stops();
				
				// Read the row from ResultSet and put the data into Stop Object
				stop.stopID = set.getInt("stopID");
				stop.address = set.getString("address");
				stop.sequenceOrder = set.getInt("sequenceOrder");
				stop.routeID = set.getInt("routeID");
				stop.adminID = set.getInt("adminID");
				stop.createdOn = set.getString("createdOn");
				
				stops.add(stop);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}

		return stops;
	}

	//for particular route.........
	public List<Stops> retrieve(String sql) {
		
		ResultSet set = db.executeQuery(sql);
		ArrayList<Stops> stops = new ArrayList<Stops>();
		try {
			while(set.next()) {
				
				Stops stop = new Stops();
				
				// Read the row from ResultSet and put the data into Stop Object
				stop.stopID = set.getInt("stopID");
				stop.address = set.getString("address");
				stop.sequenceOrder = set.getInt("sequenceOrder");
				stop.routeID = set.getInt("routeID");
				stop.adminID = set.getInt("adminID");
				stop.createdOn = set.getString("createdOn");
				
				stops.add(stop);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		return stops;
	}

}
