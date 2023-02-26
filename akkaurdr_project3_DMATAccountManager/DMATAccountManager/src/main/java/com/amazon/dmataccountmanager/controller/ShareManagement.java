package com.amazon.dmataccountmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.amazon.dmataccountmanager.UserSession;
import com.amazon.dmataccountmanager.db.ShareDAO;
import com.amazon.dmataccountmanager.model.Shares;
import com.amazon.dmataccountmanager.model.Users;

public class ShareManagement {

	private static ShareManagement manageShares = new ShareManagement();
	Shares share = new Shares();
	ShareDAO sharedao = new ShareDAO();
	
	public static ShareManagement getInstance() {
		return manageShares;
	}
	
	private ShareManagement() {
		
	}
	
	
	//display shares
	public void displayShares() {
		//Fetch User Detail
        String sql = "SELECT * FROM Shares";
        List <Shares> shareDetail = sharedao.retrieve(sql);

        for(Shares share: shareDetail) {
        	share.prettyPrint(share);
        }
	}
	
	public void displaySharesForTransaction(int shareID) {
		String sql = "SELECT * from Shares where shareID = "+shareID;
		List<Shares> shareDetails = new ArrayList<Shares>();
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		shareDetails = sharedao.retrieve(sql);
		
		for(Shares share: shareDetails) {
			share.prettyPrintForTransaction(share);
		}
	}
	
	public void generateRandomPrices() {
		Random randomValues= new Random();
	       List<Shares> shareDetails = sharedao.retrieve();

	        double minPrice = 0.1;
	        double maxPrice = 0.5;
	        try {
	             for(Shares object:shareDetails) {
	                 object.price = randomValues.nextDouble() + (maxPrice-minPrice) + object.price;
	                sharedao.update(object);
	             }

	        } catch (Exception e) {
	             System.out.println("Something went wrong: "+e);
	        }
	        
	}
	
}
