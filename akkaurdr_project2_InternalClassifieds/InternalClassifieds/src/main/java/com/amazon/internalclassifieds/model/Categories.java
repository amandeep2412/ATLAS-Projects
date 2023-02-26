package com.amazon.internalclassifieds.model;

import java.util.Scanner;

public class Categories {

	public int categoryID;
	public String title;
	public String lastUpdatedOn;
	
	public Categories() {
		
	}

	
	public Categories(int categoryID, String title, String lastUpdatedOn) {
		this.categoryID = categoryID;
		this.title = title;
		this.lastUpdatedOn = lastUpdatedOn;
	}


	@Override
	public String toString() {
		return "Categories [categoryID=" + categoryID + ", title=" + title + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}
	
	
	public void prettyPrint(Categories category) {
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Category ID:\t"+category.categoryID);
		System.out.println("Title is:\t"+category.title);
	}
	
	public void getDetails(Categories category) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter category title:");
		String title = scanner.nextLine();
		
		if(!title.isEmpty()) {
			category.title = title;
		}
	}
}
