package com.amazon.dmataccountmanager.controller;

import java.awt.peer.SystemTrayPeer;
import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.UserSession;
import com.amazon.dmataccountmanager.db.TransactionDAO;
import com.amazon.dmataccountmanager.db.UserDAO;
import com.amazon.dmataccountmanager.db.passwordEncryptor;
import com.amazon.dmataccountmanager.model.Shares;
import com.amazon.dmataccountmanager.model.Transactions;
import com.amazon.dmataccountmanager.model.Users;

public class UserManagement {

	private static UserManagement manageUsers = new UserManagement();
	passwordEncryptor encryptor = new passwordEncryptor();
	UserDAO userdao = new UserDAO();
	Scanner scanner = new Scanner(System.in);
	Users user = new Users();
	Shares share = new Shares();
	TransactionDAO transactiondao = new TransactionDAO();
	
	
	public static UserManagement getInstance() {
		return manageUsers;
	}
	
	private UserManagement() {
		
	}
	
	public boolean login(Users user) {
		String sql = "SELECT * FROM Users WHERE accountNumber = '"+user.accountNumber+"' AND password = '"+encryptor.encryptor(user.password)+"'";
		List<Users> users = userdao.retrieve(sql);
		
		if(users.size() > 0) {
			Users u = users.get(0);
			user.userID = u.userID;
			user.userName = u.userName;
			user.accountNumber = u.accountNumber;
			user.password = u.password;
			user.accountBalance = u.accountBalance;
			user.lastUpdatedOn = u.lastUpdatedOn;
			return true;
		}
		return false;
	}
	
	public boolean register(Users user) {
		
		user.getDetails(user);
		
		if (userdao.insert(user)>0) {
			return true;
		}
		
		return false;	
	}
	
	//For User
	public void displayAccountDetails() {

	       //fetching user's account details based on account number
	        String sql = "SELECT * FROM Users WHERE accountNumber= '"+UserSession.user.accountNumber+"'";
	        List <Users> userDetail = userdao.retrieve(sql);

	        //Displaying the Details
	        user.prettyPrint(userDetail.get(0));
	}
	
	public boolean update() {

        //Fetching User Details
        String sql = "SELECT * FROM Users WHERE accountNumber= '"+UserSession.user.accountNumber+"'";
        List <Users> userDetail = userdao.retrieve(sql);

        //Asking the user to update the details
        user.getDetails(userDetail.get(0));

        //Update the details in table.
        if (userdao.update(userDetail.get(0))>0) {
        	System.out.println("Profile Updated Successfully");
        	return true;
        }
        else {
			System.err.println("Profile Update Failed...");
			return false;
		}
    }
	
	
	//Deposit Money without parameter
	public boolean withdrawMoney() {
			
			System.out.println("Enter the Amount to be withdrawn from account: ");
			double amount = Double.parseDouble(scanner.nextLine());
			
			user.accountNumber = UserSession.user.accountNumber;
			
			if(amount<=UserSession.user.accountBalance) {
				UserSession.user.accountBalance=UserSession.user.accountBalance-amount;
				user.accountBalance=UserSession.user.accountBalance;
				if(userdao.update(user)>0) {
					System.out.println("Money Withdrawn Successfully");
					return true;
				}
			}
			else {
				System.err.println("Transaction Failed");
				return false;
			}
			
			return false;
		}
		
		//Withdraw Money Overrided with parameter
	public boolean withdrawMoney(double amount) {
			
			user.accountNumber = UserSession.user.accountNumber;
			
	        
			if(amount<=UserSession.user.accountBalance) {
				UserSession.user.accountBalance=UserSession.user.accountBalance-amount;
				user.accountBalance=UserSession.user.accountBalance;
				if(userdao.update(user)>0) {
					System.out.println("Money Withdrawn Successfully");
					return true;
				}
			}
			else {
				System.err.println("Transaction Failed");
				return false;
			}
			
			return false;
		}

		
		//Deposit Money without parameter
	public boolean depositMoney() {
			
			System.out.println("Enter the Amount to be deposited: ");
			double amount = Double.parseDouble(scanner.nextLine());
			
			user.accountNumber = UserSession.user.accountNumber;
	        
			UserSession.user.accountBalance=UserSession.user.accountBalance+amount;
			user.accountBalance = UserSession.user.accountBalance;
			
			if(userdao.update(user)>0) {
				System.out.println("Money Deposited Successfully");
				return true;
			}
			else {
				System.err.println("Transaction Failed");
				return false;
			}
		}
		
		//Deposit Money Overrided with parameter
	public boolean depositMoney(double amount) {
			
			user.accountNumber = UserSession.user.accountNumber;
			
			UserSession.user.accountBalance=UserSession.user.accountBalance+amount;
			user.accountBalance = UserSession.user.accountBalance;
			
			if(userdao.update(user)>0) {
				System.out.println("Money Deposited Successfully");
				return true;
			}
			else {
				System.err.println("Transaction Failed");
				return false;
			}
		}
	
	public void viewTransactionReport() {

	        System.out.println("1. View a report of all transactions");
			System.out.println("2. view a report of all transactions between a given date range");
			System.out.println("3. view a report of all transactions for a given share");
			System.out.println();
			
			System.out.println("Enter your choice: ");
			int choice = Integer.parseInt(scanner.nextLine());
			String sql="";
			
			boolean validInput = true;
			
			if (choice == 1) {
				sql = "Select * from Transactions where userID = "+UserSession.user.userID;
			}
			
			else if (choice == 2) {
				System.out.println("Enter the date range (YYYY-MM-DD): ");
				System.out.println("Enter From Date");
				String date1 = scanner.nextLine();
				System.out.println("Enter To Date");
				String date2 = scanner.nextLine();
				sql = "Select * from Transactions where userID = "+UserSession.user.userID+" and transactedOn between '"+date1+"' and '"+date2+"'";		
			
			}
			
			else if (choice==3) {
				System.out.println("Enter the ShareID"); 
				int shareID = Integer.parseInt(scanner.nextLine());
				
				share.displaySharesForTransaction(shareID);
				sql = "Select * from Transactions where userID = "+UserSession.user.userID+" and shareID = "+shareID;			
			}
			else {
				System.err.println("Invalid Choice !");
				validInput = false;
			}
			
			if(validInput) {
				List <Transactions> transactionDetails = transactiondao.retrieve(sql);
				
				if (!transactionDetails.isEmpty()) {
					for (Transactions transaction : transactionDetails)
						transaction.prettyPrint(transaction);
				}else {
					System.out.println("No Transactions Done !");
				}
			}
		}
		
}
