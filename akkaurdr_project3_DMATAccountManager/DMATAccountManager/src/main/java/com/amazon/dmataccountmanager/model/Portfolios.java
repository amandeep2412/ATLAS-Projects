package com.amazon.dmataccountmanager.model;

public class Portfolios {

	public int portfolioID;
	public int userID;
	public int shareID;
	public int transactionID;
	public String companyName;
	public int shareCount;
	
	public Portfolios() {
		
	}

	public Portfolios(int portfolioID, int userID, int shareID, int transactionID, String companyName, int shareCount) {
		this.portfolioID = portfolioID;
		this.userID = userID;
		this.shareID = shareID;
		this.transactionID = transactionID;
		this.companyName = companyName;
		this.shareCount = shareCount;
	}

	public void prettyPrint(Portfolios portfolio) {
		System.out.println("_______________________________________________");
		System.out.println("PortfolioID:\t"+portfolio.portfolioID);
		System.out.println("UserID:\t\t"+portfolio.userID);
		System.out.println("ShareID:\t"+portfolio.shareID);
		System.out.println("TransactionID:\t"+portfolio.transactionID);
		System.out.println("Company Name:\t"+portfolio.companyName);
		System.out.println("Share Count:\t"+portfolio.shareCount);
		System.out.println("_______________________________________________");
	}
	
	@Override
	public String toString() {
		return "Portfolios [portfolioID=" + portfolioID + ", userID=" + userID + ", shareID=" + shareID
				+ ", transactionID=" + transactionID + ", companyName=" + companyName + ", shareCount=" + shareCount
				+ "]";
	}
	
	
}
