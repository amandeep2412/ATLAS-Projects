package com.amazon.internalclassifieds;

import java.util.Date;
import java.util.Scanner;

import com.amazon.internalclassifieds.controller.CategoryManagement;
import com.amazon.internalclassifieds.controller.ClassifiedManagement;
import com.amazon.internalclassifieds.controller.OrderManagement;
import com.amazon.internalclassifieds.controller.UserManagement;
import com.amazon.internalclassifieds.model.Users;

public class AdminMenu extends Menu {

	UserManagement userService = UserManagement.getInstance();
	ClassifiedManagement classifiedService = ClassifiedManagement.getInstance();
	CategoryManagement categoryService = CategoryManagement.getInstance();
	OrderManagement orderService = OrderManagement.getInstance();
	
	private static AdminMenu adminmenu =new AdminMenu();
	
	public static AdminMenu getInstance() {
		return adminmenu;
	}
	
	public void showMenu() {
		
			System.out.println("******************************************");
			System.out.println("Welcome to Admin App");
			System.out.println("Hello, "+UserSession.user.name);
			System.out.println("It's: "+new Date());
			
			boolean quit = false;
			
			while(true) {
				try {
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("1: Manage Users");
		        	System.out.println("2: Manage Classifieds");
		        	System.out.println("3: Manage Classifieds Category/Type");
		        	System.out.println("4: Generate Transaction Reports");
		        	System.out.println("5: Quit Admin App");
		        	System.out.println("Select an Option");
		        	
		        	int choice = Integer.parseInt(scanner.nextLine());
		        	switch (choice) {
					case 1:
						userService.manageUser();
						break;
					case 2:
						classifiedService.manageClassifiedsForAdmin();
						break;
					case 3:
						categoryService.manageCategory();
						break;
					case 4:
						orderService.orderReport();
						break;
					case 5:
						System.out.println("Thank You for Using Admin App !!");
						quit = true;
						break;
					default:
						System.err.println("Invalid Choice...");
						break;
					}
					if(quit) {
						break;
					}
				} catch (Exception e) {
					System.err.println("Invalid Input:" +e);
				}
			}
	}
}
