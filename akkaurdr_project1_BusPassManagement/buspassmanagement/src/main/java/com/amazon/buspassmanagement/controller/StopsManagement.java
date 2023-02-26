package com.amazon.buspassmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.buspassmanagement.BusPassSession;
import com.amazon.buspassmanagement.db.StopsDAO;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;



public class StopsManagement {

	private static StopsManagement smg= new StopsManagement();
	
	Stops stop = new Stops();
	Routes route = new Routes();
	StopsDAO sdao = new StopsDAO();
	Scanner scanner = new Scanner(System.in);
	
	
	public StopsManagement() {
		
	}
	
	public static StopsManagement getInstance() {
		return smg;
	}
	
	public void manageStops() {
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: Create new stop");
				System.out.println("2: Retrieve Stop");
				System.out.println("3: Update Stop");
				System.out.println("4: Remove Stop");
				System.out.println("5: Quit Managing Stops");
				
				System.out.println("Enter your choice: ");
				int choice = Integer.parseInt(scanner.nextLine());
				boolean quit = false;
				
				switch(choice) {
				case 1:
					if (createStop())
						System.out.println("Stops Added");
					else 
						System.err.println("Something Went Wrong");
					break;
				case 2:
					retrieveStop();
					break;
				case 3:
					updateStop();
					break;
				case 4:
					deleteStop();
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
	public boolean createStop() {
		int sequenceOrder = 0;
		RoutesManagement manageRoute = RoutesManagement.getInstance();
		manageRoute.retrieveRoute();
		
		System.out.println("Select the routeID of the route for which you want to add stops: ");
		stop.routeID = Integer.parseInt(scanner.nextLine());
		
		int choice = 0;
		stop.adminID = BusPassSession.user.id;;
		boolean inserted = false;
		
		while(true) {
			
			System.out.println("Enter stop address: ");
			stop.address = scanner.nextLine();
			stop.sequenceOrder = ++sequenceOrder;
			
			System.out.println("Are you done adding stops?: -> Y:1 /n->0");
			choice = Integer.parseInt(scanner.nextLine());
			
			if(sdao.insert(stop)>0) {
				inserted = true;
			}else {
				inserted = false;
			}
			if(choice==1) {
				break;
			}
		}
		return inserted;
		
	}
	
	public void retrieveStop() {
		System.out.println("Enter the RouteId for which you want to retrieve the Stops: ");
		int id = Integer.parseInt(scanner.nextLine());
		
		String sql = "Select * from Stops where routeID="+id;
		
		List <Stops> stops = sdao.retrieve(sql);
		
		if(stops.size()>0) {
			for (Stops stopsDetails :stops) {
				stop.prettyPrintForAdmin(stopsDetails);
			}
		}
		else {
			System.out.println("No stop exists on this Route");
		}
		
	}
	
	public void retrieveStopForRoute(Routes route) {
		String sql="Select * from Stops where routeID="+route.routeID;
		
		List <Stops> stops = sdao.retrieve(sql);
		
		if(stops.size()>0) {
			for (Stops stopsDetails :stops) {
				stop.prettyPrintForAdmin(stopsDetails);
			}
		}
		else {
			System.out.println("No stop exists on this Route");
		}
		
	}
	
	public void updateStop() {
		
		System.out.println("Do you want update all ths stops of a route or change a particular stop");
		System.out.println("1. for All Stops \n2. for changing Particular Stop");
		
		int choice = Integer.parseInt(scanner.nextLine());
		
		displayStops();
		if (choice == 1) {
			//System.out.println("enter the route id:");
			//stop.routeID = Integer.parseInt(scanner.nextLine());
			sdao.delete(stop);
			createStop();
		}
		else {
			System.out.println("Enter stop ID of the stop to be updated:");
			stop.stopID = Integer.parseInt(scanner.nextLine());
			
			List <Stops> stops = new ArrayList<Stops>();
			
			int id = stop.stopID-1;
			
			String sql="Select * from Stops where stopID = " +id;
			
			stops = sdao.retrieve(sql);
			
			for (Stops stopsDetails : stops) {
				stop.sequenceOrder = stopsDetails.sequenceOrder + 1;
			}
			
			System.out.println("Enter the Stop Address: ");
			
			stop.address = scanner.nextLine();
			stop.adminID = BusPassSession.user.id;;
			sdao.update(stop);
		}
	}
	
	public boolean displayStops() {
       
        System.out.println("Enter the Route ID for the stops you want to search");
        stop.routeID = Integer.parseInt(scanner.nextLine());
        
        String sql = "SELECT * FROM Stops WHERE routeID = '"+stop.routeID+"'";
        List <Stops> getStop =  new ArrayList<Stops>();
        
        getStop = sdao.retrieve(sql);
        //System.out.println(stopdao.retrieve(sql));
        
        if(getStop.size()>0) {
        	for (Stops stops : getStop) {
                stop.prettyPrintForAdmin(stops);
            }
        	return true;
        }
        else {
			return false;
		}
        
    }
	
	public void deleteStop() {
		if(displayStops()) {
			System.out.println("Select the routeID of the Route for which you want to delete all of its stops: ");
			stop.routeID = Integer.parseInt(scanner.nextLine());
			
			if(sdao.delete(stop)>0)
				System.out.println("Stops deleted successfully");
			else {
				System.err.println("Something went wrong while deleting the stops !");
			}
		}
		else {
			System.out.println("No stops exist on this Route to be deleted");
		}
	}
	
}
