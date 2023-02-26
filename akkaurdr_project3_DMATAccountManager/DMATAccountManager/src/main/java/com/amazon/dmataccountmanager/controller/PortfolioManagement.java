package com.amazon.dmataccountmanager.controller;

import java.util.List;
import java.util.Scanner;

import com.amazon.dmataccountmanager.UserSession;
import com.amazon.dmataccountmanager.db.PortfolioDAO;
import com.amazon.dmataccountmanager.model.Portfolios;
import com.amazon.dmataccountmanager.model.Shares;

public class PortfolioManagement {

	Scanner scanner = new Scanner(System.in);
	Portfolios portfolio = new Portfolios();
	PortfolioDAO portfoliodao = new PortfolioDAO();
	
	private static PortfolioManagement managePortfolios = new PortfolioManagement();
	
	public static PortfolioManagement getInstance() {
		return managePortfolios;
	}
	
	private PortfolioManagement() {
		
	}
	
	public void displayPortfolio(Portfolios portfolio) {
		String sql = "SELECT * FROM Portfolios WHERE userID= '"+UserSession.user.userID+"'";
        List <Portfolios> portfolios = portfoliodao.retrieve(sql);
        
        //display the details
        if(portfolios.size()>0) {
        	for(Portfolios portfolioDetails: portfolios) {
        		portfolio.prettyPrint(portfolioDetails);
        	}
        }
        else {
			System.out.println("No portfolio to display");
		}
	}
	
	
	
	
}
