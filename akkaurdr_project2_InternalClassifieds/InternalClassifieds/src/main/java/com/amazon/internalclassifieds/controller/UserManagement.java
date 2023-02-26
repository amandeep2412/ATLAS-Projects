package com.amazon.internalclassifieds.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.internalclassifieds.UserSession;
import com.amazon.internalclassifieds.db.UserDAO;
import com.amazon.internalclassifieds.db.passwordEncryptor;
import com.amazon.internalclassifieds.model.Users;

public class UserManagement {

	private static UserManagement userService = new UserManagement();
	passwordEncryptor encryptor = passwordEncryptor.getInstance();
	UserDAO userdao = new UserDAO();
	Users user = new Users();
	Scanner scanner = new Scanner(System.in);
	
	
	public static UserManagement getInstance() {
		return userService;
	}
	
	private UserManagement() {
		/*Users user = new Users();
		user.name = "Amandeep Kaur";
		user.phone = "+91 9876543210";
		user.email = "aman@example.com";
		user.password = "aman123";
		user.address = "BLR 14";
		user.userType = 1;
		user.userStatus = 1; //1-active, 0-inactive
		
		UserDAO userdao = new UserDAO();
		userdao.insert(user);
		*/
		
	}
	
	public void manageUser() {
		while(true) {
			try {
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				System.out.println("1: Activate/Deactivate User");
				System.out.println("2: Quit Managing User");
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());
				
				boolean quit = false;
				switch (choice) {
				case 1:
					activateUser();
					break;
				case 2:
					quit =true;
					break;
				default:
					System.err.println("Invalid Choice !");
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
	
	public boolean login(Users user) {
		
		String sql = "Select * from Users where email='"+user.email+"'AND password='"+encryptor.encryptor(user.password)+"'";
		List<Users> users = userdao.retrieve(sql);
		
		if(users.size()>0) {
			Users u = users.get(0);
			user.userID = u.userID;
			user.name = u.name;
			user.phone = u.phone;
			user.email = u.email;
			user.password = u.password;
			user.address = u.address;
			user.userType =u.userType;
			user.userStatus = u.userStatus;
			return true;
		}
		return false;
	}
	
	//display the details of user:
	public void displayUser() {
		String sql = "Select * from Users where email='"+UserSession.user.email+"'";
		List<Users> userDetail = userdao.retrieve(sql);
		user.prettyPrintForUser(userDetail.get(0)); //userdetail.get(0) has object.
	}
	
	public boolean register(Users user) {
		user.getDetails(user);
		//user.userType = 2;
		//user.userStatus = 1;
		if (userdao.insert(user)>0) {
			return true;
		}
		return false;	
	}
	
	public boolean update() {
		String sql = "Select * from Users where email='"+UserSession.user.email+"'";
		List<Users> userDetail = userdao.retrieve(sql);
		
		user.getDetails(userDetail.get(0));
		//Update the details in SQL.
        if (userdao.update(userDetail.get(0))>0) {
        	System.out.println("Profile Updated Successfully");
        	return true;
        }
        else {
			System.err.println("Profile Update Failed...");
			return false;
		}
	}
	
	public void activateUser() {
		List<Users> users = new ArrayList<Users>();
		users = userdao.retrieve();
		
		if(users.size()>0) {
			
			for(Users userDetails: users) {
				user.prettyPrintForAdmin(userDetails);
			}
			
			System.out.println("Enter the UserID of the User to Activate/Deactivate: ");
			int userID = Integer.parseInt(scanner.nextLine());
			
			String sql = "Select * From Users Where userID = "+userID;
			List <Users> usertoActivate = new ArrayList<Users>();
			usertoActivate = userdao.retrieve(sql);
			user = usertoActivate.get(0);
			
			System.out.println();
			System.out.println("1-Activate");
			System.out.println("0-Deactivate");
			int status = Integer.parseInt(scanner.nextLine());
			user.userStatus=(status==1) ? 1 : 0;
			
			String text = (user.userStatus==1) ? "Activated": "Deactivated";
			if(userdao.update(user)>0)
				System.out.println(text+" the user");
			else
				System.out.println("Failed to set the user to status: "+text);	
		}
		else {
			System.out.println("No other users to display");
		}
		
	}

	public boolean checkUserStatus() {
		if(UserSession.user.userStatus==1) 
			return true;
		else 
			return false;
	}
	
}
