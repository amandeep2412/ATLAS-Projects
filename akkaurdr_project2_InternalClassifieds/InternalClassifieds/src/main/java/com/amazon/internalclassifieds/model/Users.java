package com.amazon.internalclassifieds.model;

import java.util.Scanner;

public class Users {

	public int userID;
	public String name;
	public String phone;
	public String email;
	public String password;
	public String address;
	public int userType;
	public int userStatus;
	public String createdOn;
	
	public Users() {
		
	}

	public Users(int userID, String name, String phone, String email, String password, String address, int userType,
			int userStatus, String createdOn) {
		this.userID = userID;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.address = address;
		this.userType = userType;
		this.userStatus = userStatus;
		this.createdOn = createdOn;
	}

	public void getDetails(Users user) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter Name: ");
		String name = scanner.nextLine();
		if(!name.isEmpty())
			user.name = name;
		
		System.out.println("Enter Phone: ");
		String phone = scanner.nextLine();
		if(!phone.isEmpty())
			user.phone = phone;
		
		
		System.out.println("Enter the address: ");
		String address = scanner.nextLine();
		if(!address.isEmpty())
			user.address = address;
		
		if (user.email == null) {
			
            String email;
            do {
                System.out.println("Enter your Email ID: ");
                email = scanner.nextLine();
            }
            while (email.isBlank() || email.isEmpty());
            user.email = email;

            String password;
            do {
            System.out.println("Enter your Password: ");
            password = scanner.nextLine();
            }
            while (password.isBlank() || password.isEmpty());
            user.password = password;
		
		
		}
	}
	
	public void prettyPrintForAdmin(Users user) {
		System.out.println("__________________________________________________________");
		System.out.println("User ID:\t"+user.userID);
		System.out.println("Name is:\t"+user.name);
		System.out.println("Phone is:\t"+user.phone);
		System.out.println("Email:\t\t"+user.email);
		System.out.println("Address:\t"+user.address);
		
		String status = "";
		
		if(user.userStatus == 1) {
			status = "Active";
		}else if (user.userStatus == 0) {
			status = "Inactive";
		}
		System.out.println("User Type:\t\t"+user.userType);
		System.out.println("User Status:\t\t"+status);
		
	}
	
	public void prettyPrintForUser(Users user) {
		System.out.println("__________________________________________________________");
		System.out.println("Name is:\t\t"+user.name);
		System.out.println("Phone is:\t\t"+user.phone);
		System.out.println("Email:\t\t\t"+user.email);
		System.out.println("Address:\t\t"+user.address);
		
		String status = "";
		
		if(user.userStatus == 1) {
			status = "Active";
		}else if (user.userStatus == 0) {
			status = "Inactive";
		}
		System.out.println("User Status:\t\t"+status);
		System.out.println("__________________________________________________________");
	}
	
	@Override
	public String toString() {
		return "Users [userID=" + userID + ", name=" + name + ", phone=" + phone + ", email=" + email + ", password="
				+ password + ", address=" + address + ", userType=" + userType + ", userStatus=" + userStatus
				+ ", createdOn=" + createdOn + "]";
	}
	
	
}
