package com.amazon.buspassmanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.BusPassException;

public class BusPassService {

	BusPassDAO passDAO = new BusPassDAO();
	
	
	// Create it as a Singleton 
	private static BusPassService passService = new BusPassService();
	Scanner scanner = new Scanner(System.in);
	
	public static BusPassService getInstance() {
		return passService;
	}
	
	private BusPassService() {
	
	}
	
	// Handler for the Bus Pass :)
	public void requestPass() throws BusPassException {
		
			BusPass pass = new BusPass();
			pass.getDetails(false);
			
			String sql = "Select * from BusPass where uid="+BusPassSession.user.id+" AND status!=3 AND routeId="+pass.routeId;
			List<BusPass> passes = passDAO.retrieve(sql);
			
			if(passes.size()>0) {
				throw new BusPassException();
			}
		 
			// Add the User ID Implicitly.
			pass.uid = BusPassSession.user.id;
		
			// As initially record will be inserted by User where it is a request
			pass.status = 1; // initial status as requested :)
		
			int result = passDAO.insert(pass);
			String message = (result > 0) ? "Pass Requested Successfully" : "Request for Pass Failed. Try Again.."; 
			System.out.println(message);
			
		
	}
	
	public void deletePass() {
		BusPass pass = new BusPass();
		System.out.println("Enter Pass ID to be deleted: ");
		pass.id = scanner.nextInt();
		int result = passDAO.delete(pass);
		String message = (result > 0) ? "Pass Deleted Successfully" : "Deleting Pass Failed. Try Again.."; 
		System.out.println(message);
	}
	
	/*
	 
	 	Extra Task:
	 	IFF : You wish to UpSkill :)
	 
	 	Scenario: Open the same application in 2 different terminals
	 	1 logged in by user
	 	another logged in by admin
	 	
	 	If admin, approves or rejects the pass -> User should be notified :)
	 	
	 	Reference Link
	 	https://github.com/ishantk/AmazonAtlas22/blob/master/Session8/src/com/amazon/atlas/casestudy/YoutubeApp.java
	 
	 */
	
	public void approveRejectPassRequest() {
		
		BusPass pass = new BusPass();

		System.out.println("Enter Pass ID: ");
		pass.id = scanner.nextInt();
		
		System.out.println("2: Approve");
		System.out.println("3: Cancel");
		System.out.println("Enter Approval Choice: ");
		pass.status = scanner.nextInt();

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		pass.approvedRejectedOn = dateFormat.format(date1);
		
		if(pass.status == 2) {
			//calendar.add(Calendar.YEAR, 1);
			Date date2 = calendar.getTime();
			pass.validTill = dateFormat.format(date2);
		}
		else {
			pass.validTill = pass.approvedRejectedOn;
		}
		
		int result = passDAO.update(pass);
		String message = (result > 0) ? "Pass Request Updated Successfully" : "Updating Pass Request Failed. Try Again.."; 
		System.out.println(message);
	}
	
	public void viewPassRequests() {
		
		System.out.println("Enter Route ID to get All the Pass Reqeuests for a Route");
		System.out.println("Or 0 for All Bus Pass Requests");
		System.out.println("Enter Route ID: ");
		
		int routeId = scanner.nextInt();
		
		List<BusPass> objects = null;
		
		if(routeId == 0) {
			objects = passDAO.retrieve();
		}else {
			String sql = "SELECT * from BusPass where routeId = "+routeId;
			objects = passDAO.retrieve(sql);
		}
		
		for(BusPass object : objects) {
			object.prettyPrint();
		}
	}
	
	public void viewExpiredPasses() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		
		String currentDate = dateFormat.format(date1);
		//System.out.println("current date is:"+currentDate);
		String sql = "Select * from BusPass where validTill < '"+currentDate+"'";
		
		List<BusPass> passes = passDAO.retrieve(sql);
		
		
		if(passes.size() ==0) {
			System.out.println("No Expired BusPasses");
		}
		for(BusPass pass:passes) {
			pass.prettyPrint();
		}
	}
	
	public void listPassesBetweenDates() {
	
		String date1, date2;
		System.out.println("Enter From Date: ");
		date1= scanner.nextLine();
		System.out.println("Enter To Date: ");
		date2= scanner.nextLine();
		
		String sql = "Select * from BusPass where status = 1 and requestedOn BETWEEN'"+date1+"' AND '"+date2+"'";
		List<BusPass> passes = passDAO.retrieve(sql);
		
		if(passes.size() ==0) {
			System.out.println("No BusPasses between specified date range");
		}
		for(BusPass pass:passes) {
			pass.prettyPrint();
		}
	}
	
	
	
	
	public void viewPassRequestsByUser(int uid) {
		
		String sql = "SELECT * from BusPass where uid = "+uid;
		List<BusPass> objects = passDAO.retrieve(sql);
		
		for(BusPass object : objects) {
			object.prettyPrint();
		}
	}
	
}
