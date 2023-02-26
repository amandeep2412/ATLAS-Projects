package com.amazon.buspassmanagement.model;

import java.util.Scanner;

public class BusPass {

	public int buspassID;
	public String requestedOn;
	public String approvedRejectedOn;
	public String validTill;
	public int status;
	public int userID;
	public int routeID;
	public String createdOn;
	
	public BusPass() {
		
	}

	public BusPass(int buspassID, String requestedOn, String approvedRejectedOn, String validTill, int status,
			int userID, int routeID, String createdOn) {
		this.buspassID = buspassID;
		this.requestedOn = requestedOn;
		this.approvedRejectedOn = approvedRejectedOn;
		this.validTill = validTill;
		this.status = status;
		this.userID = userID;
		this.routeID = routeID;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "BusPass [buspassID=" + buspassID + ", requestedOn=" + requestedOn + ", approvedRejectedOn="
				+ approvedRejectedOn + ", validTill=" + validTill + ", status=" + status + ", userID=" + userID
				+ ", routeID=" + routeID + ", createdOn=" + createdOn + "]";
	}

	public void getDetails(boolean updateMode) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Capturing Bus Pass Details......");
		
		System.out.println("Enter Route ID to apply for Bus Pass");
		routeID = Integer.parseInt(scanner.nextLine());
	}
	
	
	public void prettyPrint() {
		System.out.println("______________________________________________________");
		System.out.println("Bus pass Details:");
		System.out.println("______________________________________________________");
		
		System.out.println("Bus Pass ID:\t"+buspassID);
		System.out.println("User ID:\t"+userID);
		System.out.println("Route ID:\t"+routeID);
		System.out.println("Updated on: \t"+approvedRejectedOn);
		System.out.println("Valid Till: \t"+validTill);
		
	
		String statusText ="";
		
		if(status==1) {
			statusText ="Requested";
		}else if(status==2) {
			statusText ="Approved";
		}else if(status == 3) {
			statusText ="Cancelled";
		}else {
			statusText ="Suspended";
		}
		System.out.println("Status:\t\t"+statusText);
		System.out.println("Created On:\t"+createdOn);
		System.out.println("______________________________________________________");
	
	}
}