package com.amazon.buspassmanagement.model;

import java.util.Scanner;

public class Feedbacks {

	public int feedbackID;
	public int userID;	// user id for the user who raised the feedback
	public String raisedBy; // email
	public int type;
	public String title;
	public String description;
	public String createdOn;
	
	
	public Feedbacks() {
		
	}


	public Feedbacks(int feedbackID, int userID, String raisedBy, int type, String title, String description,
			String createdOn) {
		this.feedbackID = feedbackID;
		this.userID = userID;
		this.raisedBy = raisedBy;
		this.type = type;
		this.title = title;
		this.description = description;
		this.createdOn = createdOn;
	}

	public void getDetails() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Capturing Feedback details............");
		
		System.out.println("1. Suggestion");
		System.out.println("2: Complaint");
		System.out.println("3: BusPass Suspension");
		
		System.out.println("Select type of Feedback");
		type = Integer.parseInt(scanner.nextLine());
		
		if(type==1) {
			title = "Suggestion";
		}else if(type==2) {
			title = "Complaint";
		}else if(type ==3) {
			title = "BusPass Suspension";
			System.out.println("Please mention the No.of Months also in the description to suspend your buspass");
		}else {
			title="";
		}
		
		System.out.println("Enter Description");
		description = scanner.nextLine();
	}
	
	
	public void prettyPrint() {
		System.out.println("_____________________Feedback___________________________");
		System.out.println("Feedback ID is:\t\t"+feedbackID);
		System.out.println("User ID is:\t\t"+userID);
		System.out.println("Raised by:\t\t"+raisedBy);
		System.out.println("Type is:\t\t"+type);
		System.out.println("Title is:\t\t"+title);
		System.out.println("Description is:\t\t"+description);
		System.out.println("Created on:\t\t"+createdOn);
		System.out.println("________________________________________________________");
		
	}
	
	
	
	@Override
	public String toString() {
		return "Feedbacks [feedbackID=" + feedbackID + ", userID=" + userID + ", raisedBy=" + raisedBy + ", type="
				+ type + ", title=" + title + ", description=" + description + ", createdOn=" + createdOn + "]";
	}
	
	
	
	
	
}
