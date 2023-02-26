package com.amazon.dmataccountmanager.model;

import java.util.Date;

public class Transactions {

	public int transactionID;
	public int shareID;
	public int shareCount;
	public double pricePerShare;
	public String transactedOn;
	public double transactionCharges;
	public double sttCharges;
	public int type;
	public int userID;
	
	public Transactions() {
		
	}
	

	public Transactions(int transactionID, int shareID, int shareCount, double pricePerShare, String transactedOn,
			double transactionCharges, double sttCharges, int type, int userID) {
		super();
		this.transactionID = transactionID;
		this.shareID = shareID;
		this.shareCount = shareCount;
		this.pricePerShare = pricePerShare;
		this.transactedOn = transactedOn;
		this.transactionCharges = transactionCharges;
		this.sttCharges = sttCharges;
		this.type = type;
		this.userID = userID;
	}


	public void prettyPrint(Transactions transaction) {
		System.out.println("_______________________________________________");
		System.out.println("TransactionID:\t\t"+transaction.transactionID);
		System.out.println("ShareID:\t\t"+transaction.shareID);
		System.out.println("Share Count:\t\t"+transaction.shareCount);
		System.out.println("PricePerShare:\t\t"+transaction.pricePerShare);
		System.out.println("TransactedOn:\t\t"+transaction.transactedOn);
		System.out.println("Transaction Charges:\t"+transaction.transactionCharges);
		System.out.println("STT Charges:\t\t"+transaction.sttCharges);
		if(transaction.type == 1) {
			System.out.println("Transaction Type:\tBuy");
		}else {
			System.out.println("Transaction Type:\tSell");
		}
		System.out.println("_______________________________________________");
	}


	@Override
	public String toString() {
		return "Transactions [transactionID=" + transactionID + ", shareID=" + shareID + ", shareCount=" + shareCount
				+ ", pricePerShare=" + pricePerShare + ", transactedOn=" + transactedOn + ", transactionCharges="
				+ transactionCharges + ", sttCharges=" + sttCharges + ", type=" + type + ", userID=" + userID + "]";
	}


	


	
		
	
}
