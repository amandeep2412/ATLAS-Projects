package com.amazon.buspassmanagement;

import com.amazon.buspassmanagement.controller.AuthenticationService;
import com.amazon.buspassmanagement.controller.RoutesManagement;
import com.amazon.buspassmanagement.db.BusPassDAO;
import com.amazon.buspassmanagement.db.FeedbacksDAO;
import com.amazon.buspassmanagement.db.RoutesDAO;
import com.amazon.buspassmanagement.db.StopsDAO;
import com.amazon.buspassmanagement.db.VehiclesDAO;
import com.amazon.buspassmanagement.model.BusPass;
import com.amazon.buspassmanagement.model.Feedbacks;
import com.amazon.buspassmanagement.model.Routes;
import com.amazon.buspassmanagement.model.Stops;
import com.amazon.buspassmanagement.model.User;
import com.amazon.buspassmanagement.model.Vehicles;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
	AuthenticationService auth = AuthenticationService.getInstance();
	RoutesManagement routes = RoutesManagement.getInstance();
	RoutesDAO routedao = new RoutesDAO();
	StopsDAO stopdao = new StopsDAO();
	Routes route = new Routes();
	VehiclesDAO vehicledao = new VehiclesDAO();
	FeedbacksDAO feedbackdao = new FeedbacksDAO();
	BusPassDAO buspassdao = new BusPassDAO();
	//creating test case
	
	
	@Test
	public void testAdminLogin() {
		User user = new User();
		user.email = "aman@example.com";
		user.password = "admin123";
		
		//either test case passes or fails
		boolean result = auth.loginUser(user);
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(1, user.type);
	}
	
	@Test
	public void testUserLogin() {
		User user = new User();
		user.email = "hello@gmail.com";
		user.password = "aman123";
		
		//either test case passes or fails
		boolean result = auth.loginUser(user);
		
		Assert.assertEquals(true, result);
		Assert.assertEquals(2, user.type);
	}
	
	
	@Test
	public void testAddRoute() {
		User user = new User();

		user.email ="aman@example.com";
		user.password ="admin123";
		
		auth.loginUser(user);
		
		BusPassSession.user = user;
		
		route.title = "PAT to Delhi";
		route.description = "patiala to delhi";
		route.adminID = BusPassSession.user.id;
		
		int result = routedao.insert(route);
		Assert.assertTrue(result>0);
	}
	
	@Test
	public void testAddStop() {
	
		int result = 0;
		int sequenceOrder = 0;
		int idx = 0;
		
		User user = new User();
		Stops stop = new Stops();
		
		user.email = "aman@example.com";
		user.password = "admin123";
		
		auth.loginUser(user);
		
		BusPassSession.user = user;
		
		String[] stops = {"Rameswaram","Manamadurai Jn","Vijayawada Jn","Balharshah",
				 "Rani Kamalapati Habibganj","V Lakshmibai Jhansi Jhs","Delhi Safdarjng"};
		
		stop.adminID = BusPassSession.user.id;
		stop.routeID = 1; //manually giving routeID
		
		while(idx<stops.length) {
			stop.address = stops[idx];
			idx++;
			stop.sequenceOrder = ++sequenceOrder;
			result = stopdao.insert(stop);
			if(result==0)
				break;
		}
		Assert.assertTrue(result>0);
	}
	
	@Test
	public void testUpdateVehicle() {
		
		Vehicles vehicle = new Vehicles();
		User user = new User();
		
		user.email = "aman@example.com";
		user.password = "admin123";
		
		auth.loginUser(user);
		
		BusPassSession.user = user;
		vehicle.vehicleID = 6;
		vehicle.adminID = 1;
		vehicle.regNo = "hfkdau9g";
		vehicle.type = 1;
		vehicle.filledSeats = 10;
		vehicle.totalSeats = 30;
		vehicle.startPickUpTime = "8:00 AM";
		vehicle.startDropOffTime = "6:00 PM";
		vehicle.vehicleAvailability = 1;
		vehicle.driverID = 13;
		vehicle.routeID = 8;
		
		int result = vehicledao.update(vehicle);
		Assert.assertTrue(result>0);
		
	}
	
	@Test
	public void testApproveRejectPass() {
		
		User user = new User();
		user.email = "aman@example.com";
		user.password = "admin123";
		
		BusPassSession.user = user;
		BusPass testBusPass = new BusPass();
		testBusPass.buspassID = 2;
		
		testBusPass.status = 2;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        testBusPass.approvedRejectedOn = dateFormat.format(date1);
        calendar.add(Calendar.YEAR, 1);
        
        Date date2 = calendar.getTime();
        testBusPass.validTill = dateFormat.format(date2);
        
        int result = buspassdao.update(testBusPass);
        
        Assert.assertTrue(result>0);
		
	}
	
	@Test
	public void testCreateFeedback() {
		User user = new User();
		user.email = "hello@gmail.com";
		user.password = "aman123";
		auth.loginUser(user);
		
		BusPassSession.user = user;
	
		Feedbacks feedback = new Feedbacks();
		
		feedback.type = 1;
		feedback.title = "Test Suggestion";
		feedback.description = "this is to check if feedback is working properly";
		feedback.raisedBy = BusPassSession.user.email;
		feedback.userID = BusPassSession.user.id;
		boolean result = (feedbackdao.insert(feedback)>0);
		Assert.assertEquals(true, result);
		
	}
}
