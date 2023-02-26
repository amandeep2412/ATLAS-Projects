package com.amazon.buspassmanagement.controller;


import java.awt.peer.SystemTrayPeer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.db.FeedbacksDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Feedbacks;


public class BusPassManagement {

	BusPassDAO passDAO = new BusPassDAO();
	FeedbacksDAO feedbackdao = new FeedbacksDAO();
	
	//create it as singleton..............

	private static BusPassManagement managePass = new BusPassManagement();
	Scanner scanner = new Scanner(System.in);
	
	public static BusPassManagement getInstance() {
		return managePass;
	}
	
	public BusPassManagement() {
		
	}
	
	//Handler for Bus Pass.....................................
	public void manageBusPass() {
		while(true)
		{
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: View Bus Pass Requests");
				System.out.println("2: View Bus Pass Request By UserId");
				System.out.println("3: Approve or Reject Bus Pass Request");
				System.out.println("4: Delete Bus Pass Request");
				System.out.println("5: Suspend a Bus Pass based on User's request");
				System.out.println("6: Quit Managing Bus Passes");
				System.out.println("Enter Your Choice: ");
				
				int choice = Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				
				switch (choice) {
				case 1:
					viewPassRequests();
					break;
				case 2:
					System.out.println("Enter User ID:");
					int userId = Integer.parseInt(scanner.nextLine());
					viewPassRequestsByUser(userId);
					break;
					
				case 3:
					if(viewPassRequests()) {
						approveRejectPassRequest();
					}
					break;
				
				case 4:
					if(viewPassRequests()) {
						deletePass();
					}
					break;
					
				case 5:
					busPassSuspensionHandler();
					break;
				case 6:
					quit=true;
					break;
				default:
					System.out.println("Invalid Pass choice:");
					break;
				}
				if(quit) {
					break;
				}
			} catch (Exception e) {
				System.err.println("Invalid Input: "+e);
			}
			
		}
	}
	public void requestPass() {
		BusPass pass = new BusPass();
		
		pass.getDetails(false);
		
		//add the user ID implicitly...........
		pass.userID = BusPassSession.user.id;
		
		//as initially record will be requested by User where it is a request
		pass.status = 1; //initial status set to requested
		
		int result = passDAO.insert(pass);
		String message = (result>0)? "Pass Requested Successfully" : "Request for Pass Failed.Try again";
		System.out.println(message);
		
		
	}
	
	
	public void deletePass() {
		BusPass pass = new BusPass();
		System.out.println("Enter passID to delete a Pass: ");
		pass.buspassID = Integer.parseInt(scanner.nextLine());
		
		int result = passDAO.delete(pass);
		String message = (result>0)? "Pass deleted" :"Deleting Pass Failed. Try Again.....";
		System.out.println(message);
	}
	
	
	public void approveRejectPassRequest() {
		
		BusPass pass = new BusPass();
		System.out.println("Enter Pass ID:");
		pass.buspassID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("2. Approve");
		System.out.println("3. Cancel");
		
		System.out.println("Enter Approval Choice: ");
		pass.status = Integer.parseInt(scanner.nextLine());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		
		pass.approvedRejectedOn = dateFormat.format(date1);
		
		if(pass.status ==2) {
			calendar.add(Calendar.YEAR, 1);
			Date date2 = calendar.getTime();
			pass.validTill = dateFormat.format(date2);
			
		}else {
			pass.validTill = pass.approvedRejectedOn;
		}
		
		int result = passDAO.update(pass);
		String message = (result>0) ? "Pass Request Updated Successfully": "Updating Pass Request Failed. Try Again !";
		System.out.println(message);
	}
	
	public boolean viewPassRequests() {
		
		System.out.println("Enter 0 for All Bus Pass Requests");
		System.out.println("Enter 1 to get Bus Pass Requests for a specific Route");
		System.out.println("Enter your choice:");
		
		int choice = Integer.parseInt(scanner.nextLine());
		
		List<BusPass> passes = null;
		
		if(choice==0) {
			passes = passDAO.retrieve();
		}
		else if(choice==1) {
			
			System.out.println("Enter the RouteID:");
			int routeId = Integer.parseInt(scanner.nextLine());
			String sql = "Select * from BusPass where routeID="+routeId;
			passes = passDAO.retrieve(sql);
		}
		
		if(passes.size()>0) {
			for(BusPass object : passes) {
				object.prettyPrint();
			}
			return true;
		}
		else {
			System.out.println("No BusPass Requests received yet");
			return false;
		}
		
	}
	
	public void viewPassRequestsByUser(int userID) {
		
		String sql = "Select * from BusPass where userID="+userID;
		
		List<BusPass> passes = passDAO.retrieve(sql);
		
		if(passes.size()>0) {
			for(BusPass object: passes) {
				object.prettyPrint();
			}
		}else {
			System.out.println("No BusPasses to display for this user");
		}
		
	}
	
	public void busPassSuspensionHandler() {
		
		//Retrieve Suspension request from feedbacks with type of 3
		String sql = "Select * from Feedbacks where type =" +3;
		
		List <Feedbacks> feedback = new ArrayList<Feedbacks>();
		feedback = feedbackdao.retrieve(sql);
		
		if(feedback.size()>0) {
			int month=0;
			
			//Displaying the requests to Admin
			for (Feedbacks suspensionRequest : feedback) {
				suspensionRequest.prettyPrint();
				String description = suspensionRequest.description;
				String digits = description.replaceAll("[^0-9]", "");
				
				if(digits.isBlank() || digits.isEmpty()) {
					month=1; //by default, suspend the buspass for 1 month
				}else {
					month = Integer.parseInt(digits);
				}
			}
			
			System.out.println("Enter the UserID of the suspension request you want to modify:");
			int choice = Integer.parseInt(scanner.nextLine());
			
			sql = "Select * from BusPass where status=2 and userID =" +choice; //only displays approved buspasses for further suspension
			List <BusPass> buspass = new ArrayList<BusPass>();
			buspass = passDAO.retrieve(sql);
			
			if(buspass.size()>0) {
				for (BusPass buspasses : buspass) {
					buspasses.prettyPrint();
				}
				
				// If a particular user has multiple buspasses, we ask for the buspass id to be modified.
				System.out.println("Enter the BusPassID of the suspension request you want to modify:");
				choice = Integer.parseInt(scanner.nextLine());
				sql = "Select * from BusPass where buspassID =" +choice;
				
				buspass = passDAO.retrieve(sql);
				//Now we have the buspass on which we have to perform suspension..
				for (BusPass buspasses : buspass) {
					System.out.println("Do you want to Approve or Reject the Suspension Request?");
					System.out.println("1 for Approval /n.2 for Rejection");
					choice = Integer.parseInt(scanner.nextLine());
					
					if (choice == 1) {
						//Approve
						//System.out.println("Before"+buspasses.validTill);
						String date1 = buspasses.validTill.substring(0,10);
						String addOn = buspasses.validTill.substring(11,21);
						buspasses.status = 4;
						LocalDate date = LocalDate.parse(date1);
						//Month monthString = date.getMonth();
						date = date.plusMonths(month);
						System.out.println(date);
						buspasses.validTill = date.toString()+" " +addOn;
						
						//System.out.println(buspasses.validTill);
						
						passDAO.update(buspasses);				
					}
					
					else if (choice == 2) {
						System.out.println("Bus Pass Suspension request Rejected !");
						break;
					}
					else
						System.out.println("Invalid Choice");
				}
			}
			else {
				System.out.println("No Approved BusPasses for this user to be further Suspended");
			}
			
		}
		else {
			System.out.println("No Suspension Requests for Bus Passes");
		}
		
	}
	
}