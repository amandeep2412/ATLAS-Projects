package com.amazon.internalclassifieds.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazon.internalclassifieds.model.Orders;

public class OrderDAO implements DAO<Orders> {

	DB db = DB.getInstance();
	public int insert(Orders object) {
		String sql = "INSERT INTO Orders (classifiedID, fromUserID, toUserId, proposedPrice, status) VALUES ("+object.classifiedID+", "+object.fromUserID+", "+object.toUserID+", "+object.proposedPrice+", "+object.status+")";
		return db.executeSQL(sql);
	}

	//We will not be updating Orders
	public int update(Orders object) {
		return 0;
	}

	//We will not be deleting Orders
	public int delete(Orders object) {
		return 0;
	}

	public List<Orders> retrieve() {
		String sql = "SELECT * from Orders";
		
		ResultSet set = db.executeQuery(sql);
		
		ArrayList<Orders> orders = new ArrayList<Orders>();
		
		try {
			while(set.next()) {
				
				Orders order = new Orders();
				
				// Read the row from ResultSet and put the data into Orders Object
				order.orderID = set.getInt("orderID");
				order.classifiedID = set.getInt("classifiedID");
				order.fromUserID = set.getInt("orderID");
				order.toUserID = set.getInt("classifiedID");
				order.proposedPrice = set.getInt("orderID");
				order.status = set.getInt("classifiedID");
				order.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				orders.add(order);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		return orders;
	}

	public List<Orders> retrieve(String sql) {
ResultSet set = db.executeQuery(sql);
		
		ArrayList<Orders> orders = new ArrayList<Orders>();
		
		try {
			while(set.next()) {
				
				Orders order = new Orders();
				
				// Read the row from ResultSet and put the data into Orders Object
				order.orderID = set.getInt("orderID");
				order.classifiedID = set.getInt("classifiedID");
				order.fromUserID = set.getInt("orderID");
				order.toUserID = set.getInt("classifiedID");
				order.proposedPrice = set.getInt("orderID");
				order.status = set.getInt("classifiedID");
				order.lastUpdatedOn = set.getString("lastUpdatedOn");
				
				orders.add(order);
			}
		} catch (Exception e) {
			System.err.println("Something Went Wrong: "+e);
		}
		return orders;
	}

}
