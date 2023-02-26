package com.amazon.buspassmanagement;

import java.util.Date;

import com.amazon.buspassmanagement.model.User;

public class AdminMenu extends Menu {
	
	
	private static AdminMenu adminMenu = new AdminMenu();
	
	public static AdminMenu getInstance() {
		return adminMenu;
	}
	public void showMenu() {
		
		//login code should come before the menu becomes visible to the admin
		
		
		//instead of taking i/p in variables, creating object here..
		
		User adminUser = new User(); //no data in user obj
		
		System.out.println("Enter Email: ");		
		adminUser.email = scanner.nextLine();
		
		System.out.println("Enter Password: ");
		adminUser.password = scanner.nextLine();
		
		boolean result = auth.loginUser(adminUser); //adminUser - obj //true
		
		if(result && adminUser.type ==1) { 
			BusPassSession.user = adminUser;
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Welcome to Admin app");
			System.out.println("Hello "+adminUser.name); //referring to u which has name
			System.out.println("*******************");
			System.out.println("It is: "+new Date());
			
			boolean quit = false; 
			while(true) { 
				try {
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("1: Managing Routes");
		        	System.out.println("2: Manage Stops");
		        	System.out.println("3: Manage Vehicles");
		        	System.out.println("4: Manage Bus Pass");
		        	System.out.println("5: Manage feedback");
		        	System.out.println("6: Quit Admin App");
		        	System.out.println("Select an option");
		        	int choice = Integer.parseInt(scanner.nextLine());
		        	switch(choice) {
		        	case 1:
		        		manageRoute.manageRoutes();
		        		break;
		        		
		        	case 2:
		        		stops.manageStops();
		        		break;
		        	
		        	case 3:
		        		vehicle.manageVehicles();
		        		break;

		        	case 4:
		        		managePasses.manageBusPass();
		        		break;
		        	
		        	case 5:
		        		feedbacks.manageFeedback();
		        		break;
		        	
		        	case 6:
		        		System.out.println("Thank you for using admin app");
		        		quit = true; 
		        		break;	
		        	
		        	default:
		        		System.err.println("Invalid Choice");
		        		break;
		        	}
		        	
		        	if(quit) { //quits the admin application
		        		break;
		        	}
		        }
				 catch (Exception e) {
					System.out.println("Invalid input: "+e);
				}	
	        
			}
		
    	
		}else {
			System.err.println("Invalid Credentials ! Please try again !!");
		}
	}
}
