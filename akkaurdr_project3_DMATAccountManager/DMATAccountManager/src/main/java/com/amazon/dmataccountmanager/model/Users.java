package com.amazon.dmataccountmanager.model;

import java.util.Scanner;

public class Users {

	public int userID;
	public String userName;
	public String accountNumber;
	public String password;
	public double accountBalance;
	public String lastUpdatedOn;
	
	
	public Users() {
		
	}
	public Users(int userID, String userName, String accountNumber, String password, int accountBalance,
			String lastUpdatedOn) {
		
		this.userID = userID;
		this.userName = userName;
		this.accountNumber = accountNumber;
		this.password = password;
		this.accountBalance = accountBalance;
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
	public void getDetails(Users user) {
	
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter userName: ");
		String name = scanner.nextLine();
		
		if(!name.isEmpty())
			user.userName = name;
		
		if(user.accountNumber == null) {
			String accountNumber;
			do {
				System.out.println("Enter the account Number: ");
				accountNumber = scanner.nextLine();
			}
			while(accountNumber.isBlank() || accountNumber.isEmpty());
			user.accountNumber = accountNumber;
			
			
			String password;
			do {
				System.out.println("Enter the password: ");
				password = scanner.nextLine();
			}
			while(password.isBlank() ||  password.isEmpty());
			user.password = password;
		}
		
		System.out.println("Enter the account balance: ");
		String accountBalance =scanner.nextLine();
		
		if(!accountBalance.isEmpty()) {
			user.accountBalance = Integer.parseInt(accountBalance);
		}
		
		
	}
	
	public void prettyPrint(Users user) {
		System.out.println("_______________________________________________");
		System.out.println("User ID:\t\t "+user.userID);
		System.out.println("Name is:\t\t "+user.userName);
		System.out.println("Account Number is:\t "+user.accountNumber);
		System.out.println("Account Balance:\t "+user.accountBalance);
		System.out.println("_______________________________________________");
	}
	
	@Override
	public String toString() {
		return "Users [userID=" + userID + ", userName=" + userName + ", accountNumber=" + accountNumber + ", password="
				+ password + ", accountBalance=" + accountBalance + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}
	
	
	
}
