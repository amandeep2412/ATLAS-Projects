package com.amazon.buspassmanagement.model;

public class Stops {

	public int stopID;
	public String address;
	public int sequenceOrder;
	public int routeID;
	public int adminID;
	public String createdOn;
	
	public Stops() {
		
	}

	public Stops(int stopID, String address, int sequenceOrder, int routeID, int adminID, String createdOn) {
		this.stopID = stopID;
		this.address = address;
		this.sequenceOrder = sequenceOrder;
		this.routeID = routeID;
		this.adminID = adminID;
		this.createdOn = createdOn;
	}

	public void prettyPrintForAdmin(Stops stop) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("StopID:\t\t\t"+stop.stopID);
		System.out.println("Address:\t\t"+stop.address);
		System.out.println("Sequence Order:\t\t"+stop.sequenceOrder);
		System.out.println("RouteID:\t\t"+stop.routeID);
		System.out.println("AdminID:\t\t"+stop.adminID);
		System.out.println("CreatedOn:\t\t"+stop.createdOn);
		
	}

	@Override
	public String toString() {
		return "Stops [stopID=" + stopID + ", address=" + address + ", sequenceOrder=" + sequenceOrder + ", routeID="
				+ routeID + ", adminID=" + adminID + ", createdOn=" + createdOn + "]";
	}
	
	
}
