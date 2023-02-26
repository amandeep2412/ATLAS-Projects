package com.amazon.buspassmanagement;

import java.util.Scanner;

import com.amazon.buspassmanagement.controller.AuthenticationService;
import com.amazon.buspassmanagement.controller.BusPassManagement;
import com.amazon.buspassmanagement.controller.FeedbacksManagement;
import com.amazon.buspassmanagement.controller.RoutesManagement;
import com.amazon.buspassmanagement.controller.StopsManagement;
import com.amazon.buspassmanagement.controller.VehiclesManagement;
import com.amazon.buspassmanagement.db.DB;

public class Menu {
	
	AuthenticationService auth = AuthenticationService.getInstance();
	//we can't create objects of this app anywhere, except its main
	DB db = DB.getInstance();
	BusPassManagement managePasses = new BusPassManagement();
	RoutesManagement manageRoute = RoutesManagement.getInstance();
	FeedbacksManagement feedbacks = FeedbacksManagement.getInstance();
	StopsManagement stops = StopsManagement.getInstance();
	VehiclesManagement vehicle = VehiclesManagement.getInstance();
	
	Scanner scanner = new Scanner(System.in);
	public void showMainMenu() {
		//initial menu for application
		 while(true) {
			 try {
				 	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				 	System.out.println("1: Admin");
		        	System.out.println("2: User");
		        	System.out.println("3: Quit");
		        	System.out.println("Select an option");
		        	int choice = Integer.parseInt(scanner.nextLine());
		        	
		        	if(choice==3) {
		        		db.closeConnection();
		        		System.out.println("Thank you for using Amazon Bus Pass Maintenance");
		        		break;
		        	}
		        	try {
						MenuFactory.getMenu(choice).showMenu();
					} 
		        	catch (Exception e) {
						System.out.println("Something went wrong: "+e);
					}
			} catch (Exception e) {
				System.out.println("Invalid input: "+e);
			} 	
	       }
	}
	
	public void showMenu() {
		System.out.println("Showing Menu...............");
	}
	
}
