package com.amazon.internalclassifieds;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Assert;
import org.junit.Test;

import com.amazon.internalclassifieds.controller.UserManagement;
import com.amazon.internalclassifieds.db.UserDAO;
import com.amazon.internalclassifieds.model.Users;

public class AppTest 
{
	
	UserManagement manageUser = UserManagement.getInstance();
	UserDAO userdao = new UserDAO();
    
	@Test
	public void testAdminLogin() {
		Users user = new Users();
		
		user.email = "aman@example.com";
		user.password = "aman123";
		
		boolean result = manageUser.login(user);
		
		Assert.assertEquals(true,result);
		Assert.assertEquals(1,user.userType);
	}
	
	@Test
	public void testUserLogin() {
		Users user = new Users();
		
		user.email = "amandeep@gmail.com";
		user.password = "aman123";
		
		boolean result = manageUser.login(user);
		
		Assert.assertEquals(true,result);
		Assert.assertEquals(2,user.userType);
	}
	
	@Test
	public void testRegisterUser() {
		Users user = new Users();
		
		user.name = "Jimmt Watson";
		user.phone = "8459820002";
		user.email = "jimwatson@gmail.com";
		user.password = "aman123";
		user.address = "Chandigarh";
		user.userType = 2;
		user.userStatus = 1;
		
		int result = userdao.insert(user);
		Assert.assertTrue(result>0);
		
	}
	
}
