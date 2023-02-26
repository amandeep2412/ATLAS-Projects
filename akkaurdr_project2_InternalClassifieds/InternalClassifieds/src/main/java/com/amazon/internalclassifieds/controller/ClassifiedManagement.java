package com.amazon.internalclassifieds.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazon.internalclassifieds.UserSession;
import com.amazon.internalclassifieds.db.ClassifiedDAO;
import com.amazon.internalclassifieds.model.Categories;
import com.amazon.internalclassifieds.model.Classifieds;

public class ClassifiedManagement {

	Scanner scanner = new Scanner(System.in);
	ClassifiedDAO classifieddao = new ClassifiedDAO();
	Classifieds classifieds = new Classifieds();
	
	private static ClassifiedManagement manageClassifieds = new ClassifiedManagement();
	
	public static ClassifiedManagement getInstance() {
		return manageClassifieds;
	}
	
	public void manageClassifiedsForAdmin() {
		while(true) {
			try {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("1: Classifieds Approval/Rejection");
				System.out.println("2: Post a new Classified");
				System.out.println("3: Update an existing Classified");
				System.out.println("4: Remove Classifieds(One/Many)");
				System.out.println("5: Quit Managing Classified");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
				System.out.println("Enter Your Choice: ");
				int choice = Integer.parseInt(scanner.nextLine());//scanner.nextInt();
				boolean quit = false;
				
				switch (choice) {
				case 1:
					approveRejectClassified();
					break;
				case 2:
					if(postClassified())
						System.out.println("Classified Posted");
					else
						System.out.println("Classified Upload Failed");
					break;
				case 3:
					updateClassified();
					break;
				case 4:
					removeClassified();
					break;
				case 5:
					quit=true;
					break;
				default:
					System.err.println("Invalid Choice !");
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
	
	public void manageClassifiedsForUser() {
		while(true) {
            try {
            	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("1: View your Classifieds");
                System.out.println("2: Post a new Classified");
                System.out.println("3: Update your Existing Classified");
                System.out.println("4: Quit Managing Classified");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Enter Your Choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                boolean quit = false;
                switch(choice) {
                case 1:
                    displayUserClassified();
                    break;

                case 2:
                    if(postClassified())
                        System.out.println("Classified Added");
                    else
                        System.out.println("Classified Upload Failed");
                    break;

                case 3:
                    updateClassified();
                    break;

                case 4:
                    quit = true;
                    break;

                default:
                	System.err.println("Invalid Choice !");
                	break;
                }

                if (quit) {
                	break;
                }
                    
            } catch (Exception e) {
                System.err.println("Invalid Input"+e);
            }
        }
	}
	
	public void approveRejectClassified() {
		List<Classifieds> classifiedDetails = new ArrayList<Classifieds>();
		String sql = "Select * from Classifieds where status="+2;
		classifiedDetails = classifieddao.retrieve(sql);
		
		if(classifiedDetails.size()>0) {
			
			for(Classifieds classifiedDetail: classifiedDetails) {
				classifieds.prettyPrintForAdmin(classifiedDetail);
			}
		
			// Asking for the Classified ID to be Approve or Rejected
			System.out.println("Enter the Classified ID to be Approve/Reject: ");
			int classifiedID = Integer.parseInt(scanner.nextLine());
		
			//Retrieving that particular classified
			sql = "Select * from Classifieds where classifiedID="+classifiedID;
			List<Classifieds> classifiedToActivate = new ArrayList<Classifieds>();
		
			classifiedToActivate = classifieddao.retrieve(sql);
		
			classifieds = classifiedToActivate.get(0);
		
			//Asking the admin to approval or reject
			System.out.println("1-Approve");
			System.out.println("0-Reject");
			int status = Integer.parseInt(scanner.nextLine());
		
			//change the status of classified
			classifieds.status = (status==1)?1:0;
		
			//update the classified
			if(classifieddao.update(classifieds)>0)
				System.out.println("Classified status updated");
			else
				System.out.println("Classified status update failed");
		}
		else {
			System.out.println("No Classifieds pending for approval");
		}
	}
	
	public boolean postClassified() {
		Classifieds classifieds = new Classifieds();
		classifieds.userID = UserSession.user.userID;
		
		UserManagement manageUser = UserManagement.getInstance();
		
		if(manageUser.checkUserStatus()) {
			
			//Asking the user to add the classified details
			classifieds.getDetails(classifieds);
			
			//Adding the classified to table
			if(classifieddao.insert(classifieds)>0)
				return true;
			else {
				return false;
			}
		}
		else {
			System.out.println("You can't post a classified as you're not Active");
			return false;
		}	
	}
	
	public void updateClassified() {
		String sql = "Select * from Classifieds where userID="+UserSession.user.userID;
		List<Classifieds> classifiedList = classifieddao.retrieve(sql);
		
		if(classifiedList.size()>0) {
			
			for(Classifieds classified: classifiedList) {
				classified.prettyPrintForUser(classified);
			}
			
			//asking the user for classified id to be updated:
			System.out.println("Enter the classified ID to be updated");
			int classifiedID = Integer.parseInt(scanner.nextLine());
			
			//fetch the classified based on the classified ID
			sql = "Select * from Classifieds where classifiedID="+classifiedID;
			
			List<Classifieds> classifiedDetail = classifieddao.retrieve(sql);
			
			if (classifiedDetail.get(0).status == 3) {
	            System.err.println("You can't modify a Classified which is already Sold");
	        }
			else {
				//ask the user to update the details
				classifieds.getDetails(classifiedDetail.get(0));
				//update the details in sql:
				if(classifieddao.update(classifiedDetail.get(0))>0) {
					System.out.println("Classified updated successfully");
				}else {
					System.err.println("Failed to update the classified");
				}	
				
			}			
		}
		else {
			System.out.println("No classifieds posted from this user account");
		}
		
	}
	
	public void removeClassified() {
		System.out.println("Do you want to remove all rejected classified or Any particular classified");
		System.out.println("1. for All Rejected Classifieds");
		System.out.println("2. for Any Particular Classified");
		int choice = Integer.parseInt(scanner.nextLine());
		
		boolean deletion = true;
		
		if(choice==1) {
			List<Classifieds> classifiedList = new ArrayList<Classifieds>();
			String sql = "Select * from Classifieds where status="+0;
			classifiedList = classifieddao.retrieve(sql);
			
			if(classifiedList.size()>0) {
				//remove all classifieds one by one
				for(Classifieds classifiedToBeDeleted: classifiedList) {
					if(classifieddao.delete(classifiedToBeDeleted)<=0)
						deletion = false;
				}
				if(deletion) {
					System.out.println("All rejected Classifieds removed");
				}else {
					System.err.println("Something went wrong while deleting the classifieds");
				}
			}
			else {
				System.out.println("No classifieds to remove");
			}
			
			
		}
		else if(choice==2) {
			
			List<Classifieds> classifiedDetails = new ArrayList<Classifieds>();
			classifiedDetails = classifieddao.retrieve();
			
			if(classifiedDetails.size()>0) {
				//display each classified..
				for(Classifieds classifiedInfo: classifiedDetails) {
					classifiedInfo.prettyPrintForAdmin(classifiedInfo);
				}
				
				//input the classified id to be deleted
				System.out.println("Enter the classified ID to be deleted");
				classifieds.classifiedID = Integer.parseInt(scanner.nextLine());
			
				//remove the classifieds..
				if(classifieddao.delete(classifieds)>0) {
					System.out.println("Classified deleted successfully");
				}else {
					System.err.println("Deletion of Classified failed");
				}
				
			}
			else {
				System.out.println("No classifieds to remove");
			}
		}
	}
	
	public void displayClassified() {
		String sql = "Select * from Classifieds where status="+1;
		
		List<Classifieds> classifiedList = classifieddao.retrieve(sql);
		
		if(classifiedList.size()>0) {
			//display the results...........
			for(Classifieds classified: classifiedList) {
				classified.prettyPrintForUser(classified);
			}
		}else {
			System.out.println("No classifieds");
		}
		
	}
	
	// Display User's Classified
    public void displayUserClassified() {
        String sql = "Select * from Classifieds where userID= "+UserSession.user.userID;
        List <Classifieds> classifiedList = classifieddao.retrieve(sql);

        if(classifiedList.size()>0) {
        	//Display the Details
            for (Classifieds classified : classifiedList) {
                classified.prettyPrintForUser(classified);
            }
        }else {
        	System.out.println("No classified posted from this user account");
		}
        
    }
	
	// To set the price of apartment to 20000
    public void setPrice() {
        // Setting up price for houses for rent
        // Retrieving classifieds which are houses
        String sql = "SELECT * from Classifieds WHERE productName LIKE '%House%' OR productName LIKE '%Apartment%' OR headline LIKE '%House%' OR headline LIKE '%Apartment%' OR description LIKE '%House for rent%' OR description LIKE '%Apartment for rent%'";
        List <Classifieds> classifiedList = new ArrayList<Classifieds>();
        classifiedList = classifieddao.retrieve(sql);

        // Change the price of each house to 20000
        for (Classifieds classified : classifiedList) {
            classified.price = 20000;
            classifieddao.update(classified);
        }
    }
	
}
