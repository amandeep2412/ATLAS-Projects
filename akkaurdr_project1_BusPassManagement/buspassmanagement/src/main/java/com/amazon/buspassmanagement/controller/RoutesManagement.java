package com.amazon.buspassmanagement.controller;

import java.awt.Choice;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.InternalFrameUI;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.db.DAO;
import com.amazon.buspassmanagement.db.RoutesDAO;
import com.amazon.buspassmanagement.db.StopsDAO;
import com.amazon.buspassmanagement.db.VehiclesDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;
import com.amazon.buspassmanagement.model.Vehicles;
import com.microsoft.aad.msal4j.IClientCertificate;
import com.microsoft.aad.msal4j.PublicClientApplication;

public class RoutesManagement {
	
	//AuthenticationService auth = AuthenticationService.getInstance();
	StopsManagement manageStop = StopsManagement.getInstance();
	VehiclesManagement manageVehicle = VehiclesManagement.getInstance();
	
	private static RoutesManagement manageRoute = new RoutesManagement();
	
	BusPass buspass = new BusPass();
	Vehicles vehicles = new Vehicles();
	Stops stops = new Stops();
	Routes routes = new Routes();
	
	BusPassDAO buspassdao = new BusPassDAO();
	VehiclesDAO vehiclesdao = new VehiclesDAO();
	StopsDAO stopsdao = new StopsDAO();
	RoutesDAO rdao = new RoutesDAO();
	
	Scanner scanner = new Scanner(System.in);
	
	public static RoutesManagement getInstance() {
		return manageRoute;
	}
	
	public RoutesManagement() {
		
	}
	
	
	public void manageRoutes() {
		
		boolean insertedOrNot = false;
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1. Create a route");
				System.out.println("2. Retrieve a route");
				System.out.println("3. Update a route");
				System.out.println("4. Delete a route");
				System.out.println("5: Quit managing routes");
				System.out.println();
				System.out.println("Enter your choice:");
				int ch = Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				switch(ch) {
				case 1:
					if(createRoute()) {
						System.out.println("Route added");
					}
					else {
						System.out.println("something went wrong ! route not added");
					}
					break;
				case 2:
					retrieveRoute();
					break;
				case 3:
					updateRoute();
					break;
				case 4:
					deleteRoute();
					break;
				case 5:
					quit = true;
					break;
				default:
					System.out.println("invalid choice");
					break;
				}
				if(quit)
					break;
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
			
		}
	}
	
	
	public boolean createRoute() {
		
		//scanner.nextLine();
			
		System.out.println("Enter the route title:");
		routes.title = scanner.nextLine();
		
		System.out.println("Enter route description");
		routes.description = scanner.nextLine();
		
		routes.adminID = BusPassSession.user.id;
		
		if(rdao.insert(routes)>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void retrieveRoute() {
		//creRoutes.viewRoute();
		System.out.println("Do you want to view all the Routes or details of a particular route?");
		System.out.println("1: All Routes");
		System.out.println("2: Details of a particular Route");
		
		int choice = Integer.parseInt(scanner.nextLine());
		
		if(choice==1) {
			List <Routes> routesList = new ArrayList<Routes>();
			routesList = rdao.retrieve();
			
			if(routesList.size()>0) {
				for (Routes routesDetails : routesList) {
					routes.prettyPrintForAdmin(routesDetails);
				}
			}
			else {
				System.out.println("No Routes Exist");
			}
			
		}
		
		else if(choice==2) {
			
			System.out.println("Enter the Route Title: ");
			String titleInput = scanner.nextLine();
			
			String sql = "Select * from Routes where title ='"+titleInput+"'";
			
			List <Routes> routeList = rdao.retrieve(sql);
			
			if(routeList.size()>0) {
				for (Routes routesDetails : routeList) {
					
					routes.prettyPrintForAdmin(routesDetails);
					manageStop.retrieveStopForRoute(routesDetails);
					manageVehicle.retrieveVehicleBasedOnRoute(routesDetails);
				}
			}
			else {
				System.out.println("No such route exists");
			}
		}
		
	}
	
	public void updateRoute() {
		//display routes
		retrieveRoute();
		
		System.out.println("Enter the route ID you want to modify: ");
		routes.routeID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("enter the title to be updated: ");
		String title = scanner.nextLine();
		
		if(!title.isEmpty()) {
			routes.title = title;
		}
		
		System.out.println("Enter the description to be updated: ");
		String description = scanner.nextLine();
		if(!description.isEmpty()) {
			routes.description = description;
		}
		
		routes.adminID = BusPassSession.user.id;;
		//creRoutes.createdOn = creRoutes.createdOn;
		
		if(rdao.update(routes)>0)
			System.out.println("Route updated successfully");
		else {
			System.err.println("Something went wrong while updating the Route details");
		}
		
	}
	
	public void deleteRoute() {
		retrieveRoute();
		System.out.println("Select the routeID for the Route you want to delete: ");
		routes.routeID = Integer.parseInt(scanner.nextLine());
		
		
		System.out.println("All the Stops, Vehicles and BusPasses on this route will be deleted too.");
        System.out.println("Do you wish to continue Deleting this route /n1. Yes 2. No");
        int choice = Integer.parseInt(scanner.nextLine());
		
        
        if (choice == 1) {
        	String sql = "Select * from Routes where routeID="+routes.routeID;
        	List<Routes> routeDetails = rdao.retrieve(sql);
        	
        	if(routeDetails.size()>0) {
        		sql = "Select * from BusPass where routeId="+routes.routeID;
        		List<BusPass> passDetails = buspassdao.retrieve(sql);
        		
        		if(passDetails.size()>0) {
        			for(BusPass pass:passDetails) {
    					buspassdao.delete(pass);
    				}
        			System.out.println("Bus Passes of this Route are deleted !");
        		}else {
        			System.out.println("No Buspass for this route");
        		}
        		
        		sql = "Select * from Stops where routeID="+routes.routeID;
        		List<Stops> stopDetails = stopsdao.retrieve(sql);
        		
        		if(stopDetails.size()>0) {
        			for(Stops stop:stopDetails) {
        				stopsdao.delete(stop);
        			}
        			System.out.println("Stops on this route are deleted !");
        		}else {
        			System.out.println("No stops for this route");
        		}
        		
        		sql = "Select * from Vehicles where routeID="+routes.routeID;
        		List<Vehicles> vehicleDetails = vehiclesdao.retrieve(sql);
        		
        		if(vehicleDetails.size()>0) {
        			for(Vehicles vehicle:vehicleDetails) {
        				vehiclesdao.delete(vehicle);
        			}
        			System.out.println("Vehicles on this route are deleted !");
        		}else {
        			System.out.println("No Vehicles for this route");
        		}
        		
        		int result = rdao.delete(routes);
        		String message = (result > 0) ? "Route Deleted Successfully" : "Deleting Route Failed. Try Again.."; 
    			System.out.println(message);
        		
        	}else {
        		System.err.println("No such route exists !");
        	}
        	
        }
        else
        	System.out.println("Cancelled by Admin");
        
       
	}
	
	public boolean displayRoutes(Routes routes) {
		System.out.println("Enter the route title of the Route you want to display: ");
		routes.title = scanner.nextLine();
		String sql = "Select * From Routes Where title ='"+routes.title+"';";
		List<Routes> routetoDisplay = new ArrayList<Routes>();
		routetoDisplay = rdao.retrieve(sql);
		
		if(routetoDisplay.size()>0) {
			for (Routes route : routetoDisplay) {
				routes.prettyPrintForUser(route);
			}
			return true;
		}else {
			System.out.println("No such route to display");
			return false;
		}
		
		
	}
}
	

