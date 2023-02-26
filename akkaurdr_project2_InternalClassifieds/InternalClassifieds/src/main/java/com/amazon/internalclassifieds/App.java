package com.amazon.internalclassifieds;

import com.amazon.internalclassifieds.db.DB;


public class App
{
	private App() {
		
	}
    public static void main( String[] args )
    {
    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Welcome to Amazon Internal Classifieds App");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        if(args.length>0)
        {
     	   DB.FILEPATH = args[0];
        }
         
        new Menu().showMainMenu();
    }
}
