package com.amazon.buspassmanagement;

import java.util.Date;

import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.User;

public class UserMenu extends Menu {

	
	private static UserMenu userMenu = new UserMenu();
	
	
	public static UserMenu getInstance() {
		return userMenu;
	}
	
	Routes routes = new Routes();
	
	public void showMenu() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("1: Register");
		System.out.println("2: Login");
		System.out.println("3: Cancel");
		System.out.println("Enter your choice: ");
		
		int initialChoice = Integer.parseInt(scanner.nextLine());
		boolean result = false;
		
		User user = new User();
		switch(initialChoice) {

        case 1:
            System.out.println("Enter Your Name:");
            user.name = scanner.nextLine();

            System.out.println("Enter Your Phone:");
            user.phone = scanner.nextLine();

            System.out.println("Enter Your Email:");
            user.email = scanner.nextLine();

            System.out.println("Enter Your Password:");
            user.password = scanner.nextLine();

            System.out.println("Enter Your Address:");
            user.address = scanner.nextLine();

            System.out.println("Enter Your Department:");
            user.department = scanner.nextLine();

            // As we know, user is registering 🙂
            user.type = 2;

            result = auth.registerUser(user);

            System.out.println ("Successfully Registered!!!");
            break;

        case 2:
        	
            System.out.println("Enter Your Email:");
            user.email = scanner.nextLine();

            System.out.println("Enter Your Password:");
            user.password = scanner.nextLine();

            result = auth.loginUser(user);

            if(!result && user.type != 2) {
                System.err.println("Invalid Credentials. Please Try Again !!");
            }

            break;

        case 3:
            System.out.println("Thank You for Using Bus Pass App");
            break;
            
        default:
            System.err.println("Invalid Choice...");
            System.out.println("Thank You for Using Bus Pass App");
            break;
		}
		
		//user must login before to access this menu
		
		if(result && user.type ==2) { //1-admin, 2 -normal user
			BusPassSession.user = user;
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("welcome to User app");
			System.out.println("***********************");
			System.out.println("it is: "+new Date());
			boolean quit= false;
			while(true) {
				try {
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("1: View Routes");
		        	System.out.println("2: Apply for Bus Pass");
		        	System.out.println("3: My Bus Pass");
		        	System.out.println("4: Write feedback or Request BusPass Suspension");
		        	System.out.println("5: My profile");
		        	System.out.println("6: Quit user App");
		        	System.out.println("Select an option");
		        	int choice = Integer.parseInt(scanner.nextLine());
		        	
		        	switch(choice) {
		        	case 1:
		        		manageRoute.displayRoutes(routes);
		        		break;
		        		
		        	case 2:
		        		if(manageRoute.displayRoutes(routes)) {
		        			System.out.println("****************************");
			        		managePasses.requestPass();
		        		}
		        		break;
		        	
		        	case 3:
		        		managePasses.viewPassRequestsByUser(BusPassSession.user.id);
		        		break;

		        	
		        	case 4:
		        		feedbacks.createFeedback();
		        		break;
		        	
		        	case 5:
		        		System.out.println("My profile:");
		        		user.prettyPrint(); // in user.java file
		        		
		        		System.out.println("_________________________________________");
		        		System.out.println("Do you wish to update your profile details ? 1: Update, 2: No");
		        		
		        		choice = Integer.parseInt(scanner.nextLine());
		        		if(choice==1) {
		        			
		        			
		        			System.out.println("Enter your Name: ");
		        			String name = scanner.nextLine();
		        			
		        			if(!name.isEmpty()) { 
		        				user.name =name;
		        			}
		        			
		        			System.out.println("Enter your Phone: ");
		        			String phone = scanner.nextLine();
		        			
		        			if(!phone.isEmpty()) {
		        				user.phone =phone;
		        			}
		        			
		        			System.out.println("Enter your password: ");
		        			String password = scanner.nextLine();
		        			
		        			if(!password.isEmpty()) {
		        				user.password =password;
		        			}
		        		
		        			System.out.println("Enter your address: ");
		        			String address = scanner.nextLine();
		        			
		        			if(!address.isEmpty()) {
		        				user.address =address;
		        			}
		        			
		        			System.out.println("Enter your dept: ");
		        			String department = scanner.nextLine();
		        		
		        			if(!department.isEmpty()) {
		        				user.department =department;
		        			}       			
		        			
		        			
		        			if(auth.updateUser(user)) { //true
		        				System.out.println("profile updated");
		        			}else {
		        				System.out.println("Couldn't update the profile");
		        			}
		        		}
		        		break;
		        	
		        	case 6:
		        		System.out.println("Thank you for using user app");
		        		quit = true;
		        		break;
		        	
		        	default:
		        		System.err.println("Invalid Choice");
		        		break;
		        	}
		        	if(quit) {
		        		break;
		        	}
				}
				 catch (Exception e) {
					System.out.println("Invalid input: "+e);
				}
			}
		}
	}
}
