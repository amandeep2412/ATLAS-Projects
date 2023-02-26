package com.amazon.buspassmanagement.model;


public class Vehicles {

	//attributes...............
	public int vehicleID;
	public String regNo;
	public int type;
	public int filledSeats;
	public int totalSeats;
	public String startPickUpTime;
	public String startDropOffTime;
	public int vehicleAvailability;
	public int driverID;
	public int routeID;
	public int adminID;
	public String createdOn;
	
	public Vehicles() {
		
	}

	public Vehicles(int vehicleID, String regNo, int type, int filledSeats, int totalSeats, String startPickUpTime,
			String startDropOffTime, byte vehicleAvailability, int driverID, int routeID, int adminID,
			String createdOn) {
		
		this.vehicleID = vehicleID;
		this.regNo = regNo;
		this.type = type;
		this.filledSeats = filledSeats;
		this.totalSeats = totalSeats;
		this.startPickUpTime = startPickUpTime;
		this.startDropOffTime = startDropOffTime;
		this.vehicleAvailability = vehicleAvailability;
		this.driverID = driverID;
		this.routeID = routeID;
		this.adminID = adminID;
		this.createdOn = createdOn;
	}

	public void prettyPrintForAdmin(Vehicles vehicle) {
        String vehicleType ="";
        String vehicleAvailable="";

        if (vehicle.type == 1)
            vehicleType = "Bus";
        if (vehicle.type == 2)
            vehicleType = "Innova";


        if (vehicle.vehicleAvailability == 1)
            vehicleAvailable = "Available";
        else
            vehicleAvailable = "Under Maintenance";

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("VehicleID:\t\t\t"+vehicle.vehicleID);
        System.out.println("Vehicle Registration No:\t"+vehicle.regNo);
        System.out.println("Vehicle Type:\t\t\t"+vehicleType);
        System.out.println("Filled Seats:\t\t\t"+vehicle.filledSeats);
        System.out.println("Total Seats:\t\t\t"+vehicle.totalSeats);
        System.out.println("Start PickUp Time:\t\t"+vehicle.startPickUpTime);
        System.out.println("Start DropOff Time:\t\t"+vehicle.startDropOffTime);
        System.out.println("Vehicle Availability:\t\t"+vehicleAvailable);
        System.out.println("DriverID:\t\t\t"+vehicle.driverID);
        System.out.println("RouteID:\t\t\t"+vehicle.routeID);
        System.out.println("AdminID:\t\t\t"+vehicle.adminID);
        System.out.println("CreatedOn:\t\t\t"+vehicle.createdOn);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
    }

	
	@Override
	public String toString() {
		return "Vehicles [vehicleID=" + vehicleID + ", regNo=" + regNo + ", type=" + type + ", filledSeats="
				+ filledSeats + ", totalSeats=" + totalSeats + ", startPickUpTime=" + startPickUpTime
				+ ", startDropOffTime=" + startDropOffTime + ", vehicleAvailability=" + vehicleAvailability
				+ ", driverID=" + driverID + ", routeID=" + routeID + ", adminID=" + adminID + ", createdOn="
				+ createdOn + "]";
	}
	
	
}
