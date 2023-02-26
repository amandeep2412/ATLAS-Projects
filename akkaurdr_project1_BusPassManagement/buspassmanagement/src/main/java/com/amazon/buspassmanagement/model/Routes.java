package com.amazon.buspassmanagement.model;

public class Routes {

	//attributes list
	public int routeID;
	public String title;
	public String description;
	public int adminID;
	public String createdOn;
	public Routes() {
		
	}
	
	public Routes(int routeID, String title, String description, int adminID, String createdOn) {
		this.routeID = routeID;
		this.title = title;
		this.description = description;
		this.adminID = adminID;
		this.createdOn = createdOn;
	}
	
	public void prettyPrintForAdmin(Routes route) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RouteID:\t\t"+route.routeID);
		System.out.println("Title:\t\t\t"+route.title);
		System.out.println("Description:\t\t"+route.description);
		System.out.println("AdminID:\t\t"+route.adminID);
		System.out.println("CreatedOn:\t\t"+route.createdOn);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void prettyPrintForUser(Routes route) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("RouteID:\t"+route.routeID);
		System.out.println("Title:\t\t"+route.title);
		System.out.println("Description:\t"+route.description);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	@Override
	public String toString() {
		return "Routes [id=" + routeID + ", title=" + title + ", description=" + description + ", adminID=" +
	adminID + ", createdOn=" + createdOn
				+ "]";
	}
	
	
}
