package com.amazon.buspassmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.amazon.buspassmanagement.controller.AuthenticationService;
import com.amazon.buspassmanagement.controller.RouteService;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.db.FeedbackDAO;
import com.amazon.buspassmanagement.db.RouteDAO;
import com.amazon.buspassmanagement.db.StopDAO;
import com.amazon.buspassmanagement.db.VehicleDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Feedback;
import com.amazon.buspassmanagement.model.Route;
import com.amazon.buspassmanagement.model.Stop;
import com.amazon.buspassmanagement.model.User;
import com.amazon.buspassmanagement.model.Vehicle;
import com.nimbusds.jose.crypto.impl.AuthenticatedCipherText;

// Reference Link to Use JUnit as Testing Tool in your Project
// https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit.html

public class AppTest {
    
	AuthenticationService authService = AuthenticationService.getInstance();
	Feedback feedback = new Feedback();
	FeedbackDAO feedbackdao = new FeedbackDAO();
	
	
	// UNIT TESTS
	
	@Test
	public void testUserLogin() {
		
		User user = new User();
		user.email = "aman@example.com";
		user.password = "RfMLDanOKvBIvqGYfswqvTX77iyXL2lsZeBOZII5wNg=";
		
		boolean result = authService.loginUser(user);
		
		// Assertion -> Either Test Cases Passes or It will Fail :)
		Assert.assertEquals(true, result);
		
	}
	
	@Test
	public void testAdminLogin() {
		
		User user = new User();
		user.email = "aman@gmail.com";
		user.password = "RfMLDanOKvBIvqGYfswqvTX77iyXL2lsZeBOZII5wNg=";
		
		boolean result = authService.loginUser(user);
		
		// Assertion -> Either Test Cases Passes or It will Fail :)
		Assert.assertEquals(true, result);
		Assert.assertEquals(1, user.type); // 1 should be equal to 1
		
	}
	
	@Test
	public void checkPassValidity() {
	
		BusPassDAO passdao = new BusPassDAO();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		String currentDate = dateFormat.format(date1);
				
		String sql = "Select * from BusPass where validTill >= '"+currentDate+"' and routeID=3";
		
		List<BusPass> passes = passdao.retrieve(sql);
		boolean result = false;
		
		if(passes.size()>0) {
			result = true;
		}
		
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testVehicleExistenceOnRoute() {
		VehicleDAO vehicledao = new VehicleDAO();
		
		String sql = "Select * from Vehicle where routeID = 3 and vehicleStatus=1";
		List<Vehicle> vehicles  = vehicledao.retrieve(sql);
		
		boolean result = false;
		if(vehicles.size() >0) {
			result = true;
		}else {
			result=false;
		}
		
		Assert.assertEquals(true, result);
		
	}
	
	@Test
	public void testStopExistenceOnRoute() {
		StopDAO stopdao = new StopDAO();
		
		String sql = "Select * from Stop where routeId=3";
		List<Stop> stops = stopdao.retrieve(sql);
		
		boolean result = false;
		if(stops.size()>0) {
			result = true;
		}
		
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void testMinimumStops() {
		StopDAO stopdao = new StopDAO();
		
		String sql = "Select * from Stop where routeId=3";
		List<Stop> stops = stopdao.retrieve(sql);
		
		boolean result = false;
		
		if(stops.size()>=2) {
			result= true;
		}
		
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void raiseComplaint() {
		
		User user = new User();
		user.email = "aman@example.com";
		user.password = "RfMLDanOKvBIvqGYfswqvTX77iyXL2lsZeBOZII5wNg=";
		
		boolean result1 = authService.loginUser(user);
		
		Assert.assertEquals(true, result1);
		
		BusPassSession.user = user;
		
		
        feedback.type = 2;
        feedback.description = "This is Feedback Testing";
        feedback.title = "Test Feebdack";
        feedback.userId = BusPassSession.user.id;
        feedback.raisedBy = BusPassSession.user.name;
        int result = feedbackdao.insert(feedback);
        Assert.assertTrue(result>0);
		
	}
}
