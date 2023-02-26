package com.amazon.internalclassifieds.model;

import java.awt.peer.SystemTrayPeer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.amazon.internalclassifieds.controller.CategoryManagement;
import com.amazon.internalclassifieds.db.CategoryDAO;

public class Classifieds {
	
	Categories category = new Categories();
	CategoryManagement manageCategory = CategoryManagement.getInstance();
	CategoryDAO categorydao = new CategoryDAO();

	public int classifiedID;
	public int categoryID;
	public int userID;
	public int status;
	public String headline;
	public String productName;
	public String brand;
	public String condition;
	public String description;
	public int price;
	public String pictures;
	public String lastUpdatedOn;
	
	public Classifieds() {
		
	}
	
	public Classifieds(int classifiedID, int categoryID, int userID, int status, String headline, String productName,
			String brand, String condition, String description, int price, String pictures, String lastUpdatedOn) {
		this.classifiedID = classifiedID;
		this.categoryID = categoryID;
		this.userID = userID;
		this.status = status;
		this.headline = headline;
		this.productName = productName;
		this.brand = brand;
		this.condition = condition;
		this.description = description;
		this.price = price;
		this.pictures = pictures;
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public String toString() {
		return "Classifieds [classifiedID=" + classifiedID + ", categoryID=" + categoryID + ", userID=" + userID
				+ ", status=" + status + ", headline=" + headline + ", productName=" + productName + ", brand=" + brand
				+ ", condition=" + condition + ", description=" + description + ", price=" + price + ", pictures="
				+ pictures + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}

	public void getDetails(Classifieds classified) {
		
		Scanner scanner = new Scanner(System.in);
		
		if(classified.classifiedID==0) {
			classified.status = 2;
		}
		
		System.out.println("Enter Headline: ");
		String headline = scanner.nextLine();
		if (!headline.isEmpty())
			classified.headline = headline;
		
		System.out.println("Enter Product Name: ");
		String productName = scanner.nextLine();
		if(!productName.isEmpty())
			classified.productName = productName;
		
		System.out.println("Enter Brand: ");
		String brand = scanner.nextLine();
		if(!brand.isEmpty())
			classified.brand = brand;
		
		//displaying category list
		String sql = "Select * from Category";
		List<Categories> categories = categorydao.retrieve(sql);
		for(Categories displayCategory: categories) {
			category.prettyPrint(displayCategory);
		}
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Enter Category ID: ");
		String categoryID = scanner.nextLine();
		if(!categoryID.isEmpty())
			classified.categoryID = Integer.parseInt(categoryID);
		
		System.out.println("Enter Product Description: ");
		String description = scanner.nextLine();
		if(!description.isEmpty())
			classified.description = description;
		
		System.out.println("Enter Product's picture URL: ");
		String pictures = scanner.nextLine();
		if(!pictures.isEmpty())
			classified.pictures = pictures;
		
		System.out.println("Enter Product's condition: ");
		String condition = scanner.nextLine();
		if(!condition.isEmpty())
			classified.condition = condition;
		
		System.out.println("Enter price of product: ");
		String price = scanner.nextLine();
		if(!price.isEmpty())
			classified.price = Integer.parseInt(price);
		
	}
	
	public void prettyPrintForAdmin(Classifieds classifieds) {
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Classified ID:\t\t"+classifieds.classifiedID);
        System.out.println("User ID:\t\t"+classifieds.userID);
        System.out.println("Category ID:\t\t"+classifieds.categoryID);
        System.out.println("Headline:\t\t"+classifieds.headline);
        System.out.println("Product Name:\t\t"+classifieds.productName);
        System.out.println("Brand:\t\t\t"+classifieds.brand);
        System.out.println("Description:\t\t"+classifieds.description);
        System.out.println("Condition:\t\t"+classifieds.condition);
        System.out.println("Price:\t\t\t"+classifieds.price);
        System.out.println("Pictures:\t\t"+classifieds.pictures);
        
        if(classifieds.status == 1)
        	System.out.println("Status is:\t\tApproved");
        else if(classifieds.status ==0)
        	System.out.println("Status is:\t\tRejected");
        else if(classifieds.status==2)
        	System.out.println("Status is:\t\tPending for Admin Approval");
        
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
	
	public void prettyPrintForUser(Classifieds classifieds) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Classified ID:\t"+classifieds.classifiedID);
        System.out.println("Headline:\t"+classifieds.headline);
        System.out.println("Product Name:\t"+classifieds.productName);
        System.out.println("Brand:\t\t"+classifieds.brand);
        System.out.println("Description:\t"+classifieds.description);
        System.out.println("Condition:\t"+classifieds.condition);
        System.out.println("Price:\t\t"+classifieds.price);
        System.out.println("Pictures:\t"+classifieds.pictures);
        
        if (classifieds.status == 1)
        	System.out.println("Status:\t\tUp For Sale");
        else if (classifieds.status == 0)
        	System.out.println("Status:\t\tRejected");
        else if (classifieds.status == 2)
        	System.out.println("Status:\t\tPending for Admin Approval");
        else if (classifieds.status == 3)
        	System.out.println("Status:\t\tSold");
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
}
