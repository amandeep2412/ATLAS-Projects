	package com.amazon.buspassmanagement.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Routes;

public class BusPassDAO implements DAO<BusPass> {

	DB db = DB.getInstance();
	public int insert(BusPass object) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO BusPass(userID,routeID,status)"
				+ "VALUES("+object.userID+","+object.routeID+", "+object.status+
				")";
		return db.executeSQL(sql);
	}

	public int update(BusPass object) {
		// TODO Auto-generated method stub
		String sql = "UPDATE BusPass SET approvedRejectedOn ='"+object.approvedRejectedOn +"', validTill = '"+
				object.validTill+"', status="+object.status+
				" WHERE buspassID = "+object.buspassID;
		return db.executeSQL(sql);
	}

	public int delete(BusPass object) {
		String sql = "DELETE FROM BusPass where buspassID="+object.buspassID;
		return db.executeSQL(sql);
	}

	public List<BusPass> retrieve() {
		
		String sql = "Select * from BusPass";
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<BusPass> passes = new ArrayList<BusPass>();
		
		try {
			while(set.next()) {
				BusPass pass = new BusPass();
				
				pass.buspassID = set.getInt("buspassID");
				pass.requestedOn = set.getString("requestedOn");
				pass.approvedRejectedOn = set.getString("approvedRejectedOn");
				pass.validTill = set.getString("validTill");
				pass.status = set.getInt("status");
				pass.userID = set.getInt("userID");
				pass.routeID = set.getInt("routeID");
				pass.createdOn = set.getString("createdOn");
				
				passes.add(pass);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("something went wrong: "+e);
		}
		return passes;
	}

	public List<BusPass> retrieve(String sql) {
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<BusPass> passes = new ArrayList<BusPass>();
		
		try {
			while(set.next()) {
				
				BusPass pass = new BusPass();
				
				pass.buspassID = set.getInt("buspassID");
				pass.requestedOn = set.getString("requestedOn");
				pass.approvedRejectedOn = set.getString("approvedRejectedOn");
				pass.validTill = set.getString("validTill");
				pass.status = set.getInt("status");
				pass.userID = set.getInt("userID");
				pass.routeID = set.getInt("routeID");
				pass.createdOn = set.getString("createdOn");
				
				passes.add(pass);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		return passes;
		
	}

}
