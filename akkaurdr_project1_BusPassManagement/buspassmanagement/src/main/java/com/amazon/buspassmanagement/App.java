package com.amazon.buspassmanagement;

import com.amazon.buspassmanagement.db.DB;

public class App 
{	
	private App() { 
		
	}
	
    public static void main(String[] args)
    {
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println( "Welcome to Amazon Bus Pass Maintenance" );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        Menu menu = new Menu();
       
        if(args.length>0) {
    	   DB.FILEPATH = args[0];
        }
       
        menu.showMainMenu();
       
    }
}
