package com.amazon.buspassmanagement.controller;

import java.util.List;
import java.util.Scanner;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.FeedbacksDAO;
import com.amazon.buspassmanagement.model.Feedbacks;

public class FeedbacksManagement {

	private static FeedbacksManagement manageFeedbacks = new FeedbacksManagement();
		
	FeedbacksDAO feedbackdao = new FeedbacksDAO();
	Feedbacks feedbacks = new Feedbacks();
	
	Scanner scanner = new Scanner(System.in);
	
	public static FeedbacksManagement getInstance() {
		return manageFeedbacks;
	}
	public FeedbacksManagement() {
		
	}
	
	public void manageFeedback() {
		
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: View Feedbacks");
				System.out.println("2: View Feedbacks by User");
				System.out.println("3: Delete a Feedback");
				System.out.println("4: Quit Managing Feedbacks");
				
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				switch(choice) {
				case 1:
					viewFeedbacks();
					break;
				case 2:
					System.out.println("Enter User ID: ");
					int userID = Integer.parseInt(scanner.nextLine());//scanner.nexInt();
					viewFeedbacksByUser(userID);
					break;

				case 3:
					deleteFeedback();
				case 4:
					quit = true;
					break;
					
				default:
					System.err.println("Invalid Choice");
					break;
				}
				
				if(quit) {
					break;
				}
					
			} catch (Exception e) {
				System.err.println("Invalid input: "+e);
			}
			
		} 	
	}	
	
	
	// Handler for the Feedback
	public void createFeedback() {
			feedbacks.getDetails();
			
			// Add the User ID Implicitly.
			feedbacks.userID = BusPassSession.user.id;		
			feedbacks.raisedBy = BusPassSession.user.email;
				
			int result = feedbackdao.insert(feedbacks);
			String message = (result > 0) ? "Feedback Created Successfully" : "Creating Feedback Failed. Try Again.."; 
			System.out.println(message);
	}
	
	public void deleteFeedback() {
		viewFeedbacks();
		
		System.out.println("Enter Feedback ID to be deleted: ");
		feedbacks.feedbackID = Integer.parseInt(scanner.nextLine());
		int result = feedbackdao.delete(feedbacks);
		String message = (result > 0) ? "Feedback Deleted Successfully" : "Deleting Feedback Failed. Try Again.."; 
		System.out.println(message);
	}
			
	public void viewFeedbacks() {
		List<Feedbacks> feedbacks = feedbackdao.retrieve();
		
		if(feedbacks.size()>0) {
			for(Feedbacks object : feedbacks) {
				object.prettyPrint();
			}
		}else {
			System.out.println("No Feedbacks posted yet !");
		}
		
	}
	
	public void viewFeedbacksByUser(int userID) {
		
		String sql = "SELECT * from Feedbacks where userID = "+userID;
		
		List<Feedbacks> feedbacks = feedbackdao.retrieve(sql);
		
		if(feedbacks.size()>0) {
			for(Feedbacks object : feedbacks) {
				object.prettyPrint();
			}
		}else {
			System.out.println("No feedbacks posted by this user");
		}
		
	}
}
