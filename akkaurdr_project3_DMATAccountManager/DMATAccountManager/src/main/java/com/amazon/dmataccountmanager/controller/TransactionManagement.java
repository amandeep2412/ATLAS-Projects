package com.amazon.dmataccountmanager.controller;

import java.awt.peer.SystemTrayPeer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.UserSession;
import com.amazon.dmataccountmanager.db.PortfolioDAO;
import com.amazon.dmataccountmanager.db.ShareDAO;
import com.amazon.dmataccountmanager.db.TransactionDAO;
import com.amazon.dmataccountmanager.model.Portfolios;
import com.amazon.dmataccountmanager.model.Shares;
import com.amazon.dmataccountmanager.model.Transactions;


public class TransactionManagement {

	Scanner scanner = new Scanner(System.in);
	Transactions transaction = new Transactions();
	TransactionDAO transactiondao = new TransactionDAO();
	PortfolioManagement portfolioService = PortfolioManagement.getInstance();
	Portfolios portfolio = new Portfolios();
	PortfolioDAO portfoliodao = new PortfolioDAO();
	ShareDAO sharedao = new ShareDAO();
	Shares share = new Shares();
	UserManagement userService = UserManagement.getInstance();
	ShareManagement shareService = ShareManagement.getInstance();
	
	
	double transactionCharge = 0.5/100;
	double sttCharges = 0.1/100;
	int minTransCharge = 100;
	
	private static TransactionManagement manageTransactions = new TransactionManagement();
	
	public static TransactionManagement getInstance() {
		return manageTransactions;
	}
	
	private TransactionManagement() {
		
	}
	
	public void sellTransaction() {
		//displaying the portfolio for the user..
		portfolioService.displayPortfolio(portfolio);
		String sql = "Select * from Portfolios where userID="+UserSession.user.userID;
		List<Portfolios> userPortfolio = portfoliodao.retrieve(sql);
		
		if(userPortfolio.size()>0) {
			
			System.out.println("________________________________________________");
			System.out.println("Enter the Share ID: ");
			int shareID = Integer.parseInt(scanner.nextLine());
		
			System.out.println("Enter the no.of Shares");
			int numberOfShares = Integer.parseInt(scanner.nextLine());		
		
			double totalStockPrice = 0;
		
			sql = "Select * from Portfolios where userID="+UserSession.user.userID+"AND shareID="+shareID;
			List<Portfolios> portfolioToUpdate = new ArrayList<Portfolios>();
		
			portfolioToUpdate = portfoliodao.retrieve(sql);
		
			portfolio = portfolioToUpdate.get(0);
		
			if(portfolio.shareCount >=numberOfShares) {
				sql = "Select * from Shares where shareID="+shareID;
				List<Shares> shareDetail = new ArrayList<Shares>();
				shareDetail = sharedao.retrieve(sql);
				share = shareDetail.get(0);
			
				double pricePerShare = share.price;
				double tax = 0;
			
				double transactionValue = 0;
			
				transactionValue = pricePerShare*numberOfShares;
			
				//condition: minimum transaction charge should be Rs. 100.
				if((transactionValue*transactionCharge)<=100) {
					tax = 100;
				}
				else {
					tax = transactionValue*transactionCharge;
				}
			
				//total tax including transaction charges and STT charges
				tax = tax + (sttCharges*transactionValue);
			
				totalStockPrice = transactionValue + tax;
			
				//amount will be credited into user account
			
				userService.depositMoney(totalStockPrice);
			
				portfolio.shareCount = portfolio.shareCount - numberOfShares;
			
				//portfolio - update function - updating shareCount in portfolio
			
				if(portfoliodao.update(portfolio)>0) {
					System.out.println("Portfolio updated");
				}
				else {
					System.err.println("Something went wrong in updating the portfolio");
				}	
				//update transaction table
			
				transaction.shareID = shareID; //ip from user
				transaction.shareCount = numberOfShares; //no.of shares sold
				transaction.pricePerShare = pricePerShare;
				transaction.transactionCharges = transactionCharge*transactionValue;
				transaction.sttCharges = sttCharges*transactionValue;
				transaction.type = 0;
			
				if(transactiondao.insert(transaction)>0) {
					System.out.println("Transaction details inserted");
				}
				else {
					System.out.println("Something went wrong while inserting the transaction details");
				}
			}else {
				System.out.println("Insufficient no.of shares to sell");
			}
		}
	}
	
	public void buyTransaction() {
		
		//Displaying the Shares in the market..
		shareService.displayShares();
		
		System.out.println("________________________________________________");
		System.out.println("Enter the Share ID: ");
		int shareID = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Enter the No.of Shares");
		int numberOfShares = Integer.parseInt(scanner.nextLine());		
		
		String sql = "Select * from Shares where shareID="+shareID;
		List<Shares> share = new ArrayList<Shares>();
		
		share = sharedao.retrieve(sql);
		
		double totalStockPrice = share.get(0).price * numberOfShares;
			
		share = sharedao.retrieve(sql);
		
		double pricePerShare = share.get(0).price;
		double tax = 0;
		
		double transactionValue = 0;
		
		transactionValue = pricePerShare*numberOfShares;
		
		//condition: minimum transaction charge is Rs.100.
		if((transactionValue*transactionCharge)<=100) {
			tax = 100;
		}
		else {
			tax = transactionValue*transactionCharge;
		}
			
			//total tax including transaction charges and sttCharges
		tax = tax + (sttCharges*transactionValue);
		
		totalStockPrice = transactionValue + tax;
		
		
		if (UserSession.user.accountBalance >= totalStockPrice){
			
			userService.withdrawMoney(totalStockPrice);
			
			//update transaction table
			
			transaction.shareID = shareID; //input from user
			transaction.shareCount = numberOfShares; //no.of shares sold
			transaction.pricePerShare = pricePerShare;
			transaction.transactionCharges = transactionCharge*transactionValue;
			transaction.sttCharges = sttCharges*transactionValue;
			transaction.type = 1;
			transaction.userID = UserSession.user.userID;
			
			if(transactiondao.insert(transaction)>0) {
				System.out.println("Transaction details inserted");
			}
			else {
				System.out.println("Something went wrong while inserting the transaction details");
			}
			
			sql = "Select * from Portfolios where userID ="+UserSession.user.userID+" and shareID ="+shareID;
			List<Portfolios> portfolioToUpdate = new ArrayList<Portfolios>();
			
			portfolioToUpdate = portfoliodao.retrieve(sql);
			
			if (!portfolioToUpdate.isEmpty()) {
				portfolioToUpdate.get(0).shareCount += numberOfShares;
				
				//Portfolio - update function - updating shareCount in Portfolio
				if(portfoliodao.update(portfolioToUpdate.get(0))>0) {
					System.out.println("Portfolio updated");
				}
				else {
					System.out.println("Something went wrong in updating the portfolio");
				}
			}
			else {
				
				sql = "Select * from Transactions where shareID="+shareID;
				List<Transactions> transactionDetail = new ArrayList<Transactions>();
				transactionDetail = transactiondao.retrieve(sql);
				
				Portfolios portfolio = new Portfolios();
				portfolio.companyName = share.get(0).companyName;
				portfolio.shareCount = numberOfShares;
				portfolio.shareID = shareID;
				portfolio.userID = UserSession.user.userID;
				portfolio.transactionID = transactionDetail.get(0).transactionID;
				
				if (portfoliodao.insert(portfolio)>0) {
					System.out.println("Portfolio updated");
				}
				else {
					System.out.println("Something went wrong in updating the portfolio");
				}
			}	
		}
		
		else {
			System.out.println("Insufficient Amount");
		}
	}

}
