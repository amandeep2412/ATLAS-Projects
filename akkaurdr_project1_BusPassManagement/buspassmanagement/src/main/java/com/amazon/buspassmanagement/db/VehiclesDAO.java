package com.amazon.buspassmanagement.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.Vehicles;
import com.amazon.buspassmanagement.db.DAO;
import com.amazon.buspassmanagement.db.DB;

public class VehiclesDAO implements DAO<Vehicles>{

	DB db = DB.getInstance();
	
	public int insert(Vehicles object) {
		
		String sql = "INSERT INTO Vehicles (regNo, type, filledSeats, totalSeats, startPickUpTime, startDropOffTime, vehicleAvailability, driverID, routeId, adminID) VALUES ('"+object.regNo+"', '"+object.type+"', "+object.filledSeats+
				","+object.totalSeats+", '"+object.startPickUpTime+"', '"+object.startDropOffTime+"', "+object.vehicleAvailability+", "+object.driverID+
				", "+object.routeID+", "+object.adminID+")";
		return db.executeSQL(sql);
		
	}

	public int update(Vehicles object) {
		
		String sql = "UPDATE Vehicles set regNo = '"+object.regNo+"', type='"+object.type+"', filledSeats='"+object.filledSeats+"', totalSeats='"+object.totalSeats+"', startPickUpTime='"+
		object.startPickUpTime+"', startDropOffTime='"+object.startDropOffTime+"', vehicleAvailability='"+object.vehicleAvailability+"', driverID='"+object.driverID+
		"', routeID='"+object.routeID+"' WHERE vehicleID = '"+object.vehicleID+"'";
		return db.executeSQL(sql);
	}

	public int delete(Vehicles object) {
		
		String sql = "DELETE FROM Vehicles WHERE regNo = '"+object.regNo+"'";
		return db.executeSQL(sql);
	}

	public List<Vehicles> retrieve() {
		
		String sql = "SELECT * from Vehicles";
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Vehicles> vehicles = new ArrayList<Vehicles>();
		
		try {
			while(set.next()) {
				
				Vehicles vehicle = new Vehicles();
				
				// Read the row from ResultSet and put the data into Vehicle Object
				vehicle.vehicleID = set.getInt("vehicleID");
				vehicle.regNo = set.getString("regNo");
				vehicle.type = set.getInt("type");
				vehicle.filledSeats = set.getInt("filledSeats");
				vehicle.totalSeats = set.getInt("totalSeats");
				vehicle.startPickUpTime = set.getString("startPickUpTime");
				vehicle.startDropOffTime = set.getString("startDropOffTime");
				vehicle.vehicleAvailability = set.getInt("vehicleAvailability");
				vehicle.driverID = set.getInt("driverID");
				vehicle.routeID = set.getInt("routeID");
				vehicle.adminID = set.getInt("adminID");
				vehicle.createdOn = set.getString("createdOn");
				
				vehicles.add(vehicle);
			}
		} 
		
		catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}

		return vehicles;
		
	}

	public List<Vehicles> retrieve(String sql) {
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Vehicles> vehicles = new ArrayList<Vehicles>();
		
		try {
			while(set.next()) {
				
				Vehicles vehicle = new Vehicles();
				
				// Read the row from ResultSet and put the data into Vehicle Object
				vehicle.vehicleID = set.getInt("vehicleID");
				vehicle.regNo = set.getString("regNo");
				vehicle.type = set.getInt("type");
				vehicle.filledSeats = set.getInt("filledSeats");
				vehicle.totalSeats = set.getInt("totalSeats");
				vehicle.startPickUpTime = set.getString("startPickUpTime");
				vehicle.startDropOffTime = set.getString("startDropOffTime");
				vehicle.vehicleAvailability = set.getInt("vehicleAvailability");
				vehicle.driverID = set.getInt("driverID");
				vehicle.routeID = set.getInt("routeID");
				vehicle.adminID = set.getInt("adminID");
				vehicle.createdOn = set.getString("createdOn");
				
				vehicles.add(vehicle);
			}
		} 
		
		catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}

		return vehicles;
	}

}
