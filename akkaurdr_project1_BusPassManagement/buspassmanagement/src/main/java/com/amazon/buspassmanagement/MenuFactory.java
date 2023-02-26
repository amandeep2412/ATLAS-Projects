package com.amazon.buspassmanagement;


public class MenuFactory {

	//menu type...
	public static Menu getMenu(int type) {
		
		if(type==1) {
			return AdminMenu.getInstance();
		}else if(type==2) {
			return UserMenu.getInstance();
		}
		return null;
	}
}

