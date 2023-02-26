package com.amazon.internalclassifieds;

import java.util.Date;

import com.amazon.internalclassifieds.controller.ClassifiedManagement;
import com.amazon.internalclassifieds.controller.OrderManagement;
import com.amazon.internalclassifieds.controller.UserManagement;
import com.amazon.internalclassifieds.model.Users;

public class UserMenu extends Menu{

	UserManagement userService = UserManagement.getInstance();
	ClassifiedManagement classifiedService = ClassifiedManagement.getInstance();
	OrderManagement orderService = OrderManagement.getInstance();
	private static UserMenu usermenu = new UserMenu();
	
	public static UserMenu getInstance() {
		return usermenu;
	}
	
	public void showMenu() {
			
			System.out.println("******************************************");
			System.out.println("Welcome to User App");
			System.out.println("Hello, "+UserSession.user.name);
			System.out.println("Its: "+new Date());
			
			
			boolean quit = false;
			
			while(true) {
				try {
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("1: My Profile");
		        	System.out.println("2: Manage your Classifieds");
		        	System.out.println("3: List all Classifieds Up for Sale");
		        	System.out.println("4: Connect with other Users & Buy/Sell");
		        	System.out.println("5: Quit User App");
		        		
		        	System.out.println("Select an Option");
		        	
		        	int choice = Integer.parseInt(scanner.nextLine());
		        	
		        	switch (choice) {
					case 1:
						System.out.println("My profile: ");
						userService.displayUser();
						
						System.out.println("Do you wish to update Profile (1: Update 0: Cancel)");
						choice = Integer.parseInt(scanner.nextLine());
						
						if(choice == 1) {
							userService.update();	
						}
						break;
					case 2:
						classifiedService.manageClassifiedsForUser();
						break;
					case 3:
						classifiedService.displayClassified();
						break;
					case 4:
						if(orderService.buyClassified())
							System.out.println("Classified bought");
						break;
					case 5:
						System.out.println("Thank You for Using User App !!");
						quit=true;
						break;
					default:
						System.err.println("Invalid Choice...");
						break;
					}
		        	if(quit) {
		        		break;
		        	}
				} catch (Exception e) {
					System.out.println("Invalid input: "+e);
				}
			}
		}
}
