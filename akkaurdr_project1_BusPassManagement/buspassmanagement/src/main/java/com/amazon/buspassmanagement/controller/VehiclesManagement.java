package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.VehiclesDAO;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Vehicles;



public class VehiclesManagement {

	private static VehiclesManagement vehicleManage = new VehiclesManagement();
	Scanner scanner = new Scanner(System.in);
	Vehicles vehicle = new Vehicles();
	VehiclesDAO vdao = new VehiclesDAO();
	
	public static VehiclesManagement getInstance() {
		return vehicleManage;
	}
	
	public void manageVehicles() {
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: Register a new vehicle");
				System.out.println("2: Display all registered vehicles");
				System.out.println("3: Update Vehicle Details");
				System.out.println("4: Remove a Vehicle");
				System.out.println("5: Quit Managing vehicles");
				
				System.out.println("Enter your choice: ");
				int choice = Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				
				switch(choice) {
				case 1:
					if(addVehicle()) {
						System.out.println("vehicle added");
					}else {
						System.out.println("vehicle not added");
					}
					break;
				case 2:
					displayVehicles(vehicle);
					break;
				case 3:
					updateVehicle();
					break;
				case 4:
					deleteVehicle();
					break;
				case 5:
					quit = true;
					break;
				default:
					System.out.println("Invalid Choice");
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
	
	public void addVehicleDetails(Vehicles vehicle) {
		
		System.out.println("Enter vehicle registration number: ");
		String regNo = scanner.nextLine();
		
		if(!regNo.isEmpty()) {
			vehicle.regNo = regNo;
		}
		
		System.out.println("Enter vehicle type: \n1: Bus \n2:Innova");
		String type = scanner.nextLine();
		
		if(!type.isEmpty()) {
			vehicle.type = Integer.parseInt(type);
		}
		
		System.out.println("enter total seats: ");
		String totalSeats = scanner.nextLine();
		
		if(!totalSeats.isEmpty()) {
			vehicle.totalSeats = Integer.parseInt(totalSeats);
		}
		
		System.out.println("Enter filled seats: ");
		String filledSeats = scanner.nextLine();
		
		if(!filledSeats.isEmpty()) {
			vehicle.filledSeats = Integer.parseInt(filledSeats);
		}
		
		
		System.out.println("Enter start pickup time: ");
		String startPickUpTime = scanner.nextLine();
		
		if(!startPickUpTime.isEmpty()) {
			vehicle.startPickUpTime = startPickUpTime;
		}
		
		System.out.println("Enter dropoff time: ");
		String startDropOffTime = scanner.nextLine();
		
		if(!startDropOffTime.isEmpty()) {
			vehicle.startDropOffTime = startDropOffTime;
		}
		
		System.out.println("Enter vehicle availability /n 0 -unavailable, 1-available: ");
		String vehicleAvailability = scanner.nextLine();
		
		if(!vehicleAvailability.isEmpty()) {
			vehicle.vehicleAvailability = Integer.parseInt(vehicleAvailability);
		}
		
		System.out.println("Enter driver id: ");
		String driverID = scanner.nextLine();
		
		if(!driverID.isEmpty()) {
			vehicle.driverID = Integer.parseInt(driverID);
		}
		
		System.out.println("Enter route id: ");
		String routeID = scanner.nextLine();
		
		if(!(routeID.isEmpty() || routeID.isBlank())) {
			vehicle.routeID = Integer.parseInt(routeID);
		}
		
	}
	
	public boolean addVehicle() {
		boolean inserted = false;
		vehicle.adminID = BusPassSession.user.id;;
		
		addVehicleDetails(vehicle);
		
		if(vdao.insert(vehicle)>0) {
			inserted = true;
		}else {
			inserted = false;
		}
		return inserted;
	}
	
	public boolean displayVehicles(Vehicles vehicle) {
		List <Vehicles> vehiclesList = new ArrayList<Vehicles>();
		vehiclesList = vdao.retrieve();
		
		if(vehiclesList.size()>0) {
			for (Vehicles vehiclesDetail : vehiclesList) {
				vehicle.prettyPrintForAdmin(vehiclesDetail);
			}
			return true;
		}else {
			System.out.println("No vehicles to display");
			return false;
		}
		
	}
	
	public void retrieveVehicleBasedOnRoute(Routes route) {
		String sql = "Select * from Vehicles where routeID="+route.routeID;
		List <Vehicles> vehicles = vdao.retrieve(sql);
		
		if(vehicles.size()>0) {
			for (Vehicles vehiclesDetail : vehicles) {
				vehicle.prettyPrintForAdmin(vehiclesDetail);
			}
		}
		else {
			System.out.println("No vehicle exists on this route");
		}
	}
	
	public void updateVehicle() {
		
		if(displayVehicles(vehicle)) {
			System.out.println("Enter Vehicle ID of the Vehicle to be updated: ");
			vehicle.vehicleID = Integer.parseInt(scanner.nextLine());
			
			addVehicleDetails(vehicle);
			vehicle.adminID = BusPassSession.user.id;;
			
			if (vdao.update(vehicle) > 0)
				System.out.println("Vehicle details updated successfully");
			else
				System.out.println("Something went wrong while updating the Vehicle details");
		}
		
	}
	
	public void deleteVehicle() {
		
		if(displayVehicles(vehicle)) {
			System.out.println("Enter Vehicle Registration Number of vehicle you want to delete: ");
			vehicle.regNo = scanner.nextLine();
			if (vdao.delete(vehicle) > 0)
				System.out.println("Vehicle deleted successfully");
			else
				System.out.println("Unable to delete this vehicle");
		}
		
		
	}
}
