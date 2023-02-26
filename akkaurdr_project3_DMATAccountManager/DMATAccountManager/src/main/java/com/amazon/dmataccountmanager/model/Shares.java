package com.amazon.dmataccountmanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amazon.dmataccountmanager.controller.ShareManagement;
import com.amazon.dmataccountmanager.db.ShareDAO;

public class Shares {

	ShareDAO sharedao = new ShareDAO();
	ShareManagement manageShares = ShareManagement.getInstance();
	
	ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	public int shareID;
	public String symbol;
	public String companyName;
	public double price;
	public String lastUpdatedOn;
	
	public Shares() {
		
	}
	
	
	public Shares(int shareID, String symbol, String companyName, int price, String lastUpdatedOn) {
		this.shareID = shareID;
		this.symbol = symbol;
		this.companyName = companyName;
		this.price = price;
		this.lastUpdatedOn = lastUpdatedOn;
	}


	public void displaySharesForTransaction(int shareID) {

        String sql = "SELECT * from Shares where shareID = "+shareID;
        List<Shares> shareDetails = new ArrayList<Shares>();
        shareDetails = sharedao.retrieve(sql);

        for (Shares share : shareDetails)
            share.prettyPrintForTransaction(share);

    }
	
	public void prettyPrintForTransaction(Shares share) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Company Name:\t\t"+share.companyName);
        System.out.println("SYMBOL:\t\t\t"+share.symbol);

    }
	
	public void prettyPrint(Shares share) {
		System.out.println("_______________________________________________");
		System.out.println("Share ID:\t\t"+share.shareID);
		System.out.println("Symbol is:\t\t"+share.symbol);
		System.out.println("Company Name is:\t"+share.companyName);
		System.out.println("Price is:\t\t"+share.price);
		System.out.println("_______________________________________________");
	}


	
	public void shareRandomizer() {

        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                // Calling dynamic shares function where we update the share prices randomly
                manageShares.generateRandomPrices();

            }
        }, 0, 2, TimeUnit.SECONDS);//Initial delay, Interval, timeUnit
        
    }

		
	@Override
	public String toString() {
		return "Shares [shareID=" + shareID + ", symbol=" + symbol + ", companyName=" + companyName + ", price=" + price
				+ ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}
	
	
	
	
}
