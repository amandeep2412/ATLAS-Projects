package com.amazon.internalclassifieds.controller;

import java.util.List;
import java.util.Scanner;

import com.amazon.internalclassifieds.db.CategoryDAO;
import com.amazon.internalclassifieds.model.Categories;

public class CategoryManagement {
	
	Scanner scanner = new Scanner(System.in);
	Categories category = new Categories();
	CategoryDAO categorydao = new CategoryDAO();
	
	private static CategoryManagement manageCategories = new CategoryManagement();
	
	public static CategoryManagement getInstance() {
		return manageCategories;
	}
	
	private CategoryManagement() {
		
	}
	
	public void manageCategory() {
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: List all Classifieds Categories available");
				System.out.println("2: Add a new Classified Category");
				System.out.println("3: Update title of an existing Classified Category");
				System.out.println("4: Delete an existing Classified Category");
				System.out.println("5: Quit Managing Classified Categories");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
				System.out.println("Enter your choice: ");
				int choice = Integer.parseInt(scanner.nextLine());
				
				boolean quit = false;
				switch (choice) {
				case 1:
					displayCategory();
					break;
				case 2:
					addCategory(category);
					break;
				case 3:
					updateCategory();
					break;
				case 4:
					deleteCategory(category);
					break;
				case 5:
					quit = true;
					break;
				default:
					break;
				}
				if(quit) {
					break;
				}
				
			} catch (Exception e) {
				System.err.println("Invalid Input"+e);
			}
		}
	}

	public boolean displayCategory() {
		List<Categories> categoryDetail = categorydao.retrieve();
		
		if(categoryDetail.size()>0) {
			for(Categories categories: categoryDetail) {
				category.prettyPrint(categories);
			}
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			return true;
		}else {
			
			return false;
		}
		
	}
	
	public void addCategory(Categories category) {
		category.getDetails(category);
		
		if(categorydao.insert(category)>0) {
			System.out.println("Classified category Added successfully");
			
		}else {
			System.out.println("Classified Category insertion failed");
			
		}
	}
	
	public void deleteCategory(Categories category) {
		
		displayCategory();
		
		System.out.println("Enter the Classified Category ID to be deleted: ");
		category.categoryID = Integer.parseInt(scanner.nextLine());
		
		if(categorydao.delete(category)>0) {
			System.out.println("Classified category deleted successfully");
			
		}else {
			System.out.println("Classified category deletion failed");
			
		}
	}
	
	public void updateCategory() {
		
		if(displayCategory()) {
			String title = "";
			System.out.println("Enter the Classified Category title for which you want to update the title: ");
			title = scanner.nextLine();
			
			String sql = "Select * from Category where title ='"+title+"'";
			List<Categories> categoryDetail = categorydao.retrieve(sql);
			
			category.getDetails(categoryDetail.get(0));
			
			if(categorydao.update(categoryDetail.get(0))>0) {
				System.out.println("Classified category updated successfully");
				
			}else {
				System.out.println("Classified category updation failed");
				
			}
		}else {
			System.out.println("No categories to display");
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}
		
	}
	
}
