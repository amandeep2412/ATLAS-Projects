package com.amazon.dmataccountmanager;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.amazon.dmataccountmanager.UserSession;
import com.amazon.dmataccountmanager.controller.TransactionManagement;
import com.amazon.dmataccountmanager.controller.UserManagement;
import com.amazon.dmataccountmanager.db.DB;
import com.amazon.dmataccountmanager.model.Shares;
import com.amazon.dmataccountmanager.model.Users;

public class Menu {

	Scanner scanner = new Scanner(System.in);
	UserManagement userService = UserManagement.getInstance();
	TransactionManagement transactionService = TransactionManagement.getInstance();
	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	Shares shares = new Shares();
	
	public void showMainMenu() {
		shares.shareRandomizer();
		while(true) {
			try {
				
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: Create a DEMAT Account");
            	System.out.println("2: Login");
            	System.out.println("3: Quit");
            	
            	
            	System.out.println("Select an Option");
            	
            	int choice = Integer.parseInt(scanner.nextLine());
            	
            	boolean result = false, quit = false;
            	Users user = new Users();
            	
            	switch (choice) {
				case 1:
					result = userService.register(user);
					if(!result) {
						System.out.println("Registration failed. Please try Again !!");
					}else {
						System.out.println("____________________________________________");
						System.out.println ("Successfully Registered!!!");
						
					}
					break;
				case 2:
					System.out.println("Enter the DMAT account Number: ");
					user.accountNumber = scanner.nextLine();
					System.out.println("Enter the password: ");
					user.password = scanner.nextLine();
					result = userService.login(user);
					if(!result) {
						System.err.println("Invalid Credentials. Please try again !!");
					}
					else {
						System.out.println("Login Successful !");
						UserSession.user = user;
						
						try {
							showMenu();
						} catch (Exception e) {
							System.out.println("[Main] Error while showing the menu: "+e);
						}
					}
					break;
				case 3:
					scanner.close();
					
					DB db = DB.getInstance();
					db.closeConnection();
					scheduler.shutdown();
					quit=true;
					break;
				default:
					System.err.println("Invalid Input");
					break;
				}
            	if(quit) {
            		
            		System.out.println("Thank you for using D-MAT Trading Account Manager");
            		break;
            	}
			} catch (Exception e) {
				System.err.println("Invalid Input: "+e);
			}
		}
	}
	
	public void showMenu() {
		System.out.println("Displaying the Menu................");
		System.out.println("...................................");
		System.out.println("Hello! "+UserSession.user.userName);
		System.out.println("It's: "+new Date());	
		
		
		
		boolean quit = false;
		
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("0: Quit");
	        	System.out.println("1: Display DEMAT account details");
	        	System.out.println("2: Deposit Money");
	        	System.out.println("3: Withdraw Money");
	        	System.out.println("4: Buy Transaction");
	        	System.out.println("5: Sell Transaction");
	        	System.out.println("6: View Transaction Report");
	        	System.out.println("Select an Option");
	        	
	        	int choice = Integer.parseInt(scanner.nextLine());
	        	switch (choice) {
	        	
					case 0:
						quit = true;
						break;
					case 1:
						userService.displayAccountDetails();
						break;
					case 2:
						userService.depositMoney();
						break;
					case 3:
						userService.withdrawMoney();
						break;
					case 4:
						transactionService.buyTransaction();
						break;
					case 5:
						transactionService.sellTransaction();
						break;
					case 6:
						userService.viewTransactionReport();
						break;
					default:
						System.err.println("Invalid choice !");
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
