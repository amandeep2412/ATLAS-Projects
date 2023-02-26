package com.amazon.dmataccountmanager;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Assert;
import org.junit.Test;

import com.amazon.dmataccountmanager.controller.UserManagement;
import com.amazon.dmataccountmanager.db.UserDAO;
import com.amazon.dmataccountmanager.db.passwordEncryptor;
import com.amazon.dmataccountmanager.model.Users;
/**
 * Unit test for simple App.
 */
public class AppTest
{
	UserManagement manageUser = UserManagement.getInstance();
	passwordEncryptor encrypt = new passwordEncryptor();
	UserDAO userdao = new UserDAO();
	
	@Test
	public void testLogin() {
		Users user = new Users();
		
		user.accountNumber = "ABC1234";
		user.password = "aman123";
		
		boolean result = manageUser.login(user);
		
		Assert.assertEquals(true,result);
		
	}
	
	@Test
	public void testCreateAccount() {
		Users user = new Users();
		
		user.userName = "Aman";
		user.accountNumber = "PNBA1234";
		user.password = "aman123";
		user.accountBalance = 45000;
		
		int result = userdao.insert(user);
		
		Assert.assertTrue(result>0);
		
	}
	
	@Test
	public void testDepositMoney() {
		
		Users user = new Users();
		
		double amount = 20000.00;
		user.accountNumber = "ABC1234";
		user.password = "aman123";
		
		manageUser.login(user);
		UserSession.user = user;
		boolean result = manageUser.depositMoney(amount);
		
		Assert.assertEquals(true,result);
		
	}
	
	@Test
	public void testWithdrawMoney() {
		Users user = new Users();
		
		double amount = 10000.00;
		user.accountNumber = "ABC1234";
		user.password = "aman123";
		
		manageUser.login(user);
		UserSession.user = user;
		boolean result = manageUser.withdrawMoney(amount);
		
		Assert.assertEquals(true,result);
		
	}
	
	
}
