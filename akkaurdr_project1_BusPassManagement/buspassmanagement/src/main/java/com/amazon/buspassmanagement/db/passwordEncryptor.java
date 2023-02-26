package com.amazon.buspassmanagement.db;

import java.math.BigInteger;
import java.security.MessageDigest; //SHA-256 - for data encryption. security :)

public class passwordEncryptor {
	
	private static passwordEncryptor passEncryptor = new passwordEncryptor();
	
	public static passwordEncryptor getInstance() {
		return passEncryptor;
	}
	public String encryptor(String password) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes()); //array - bytes 
			byte[] digestedBytes = md.digest();
			
			BigInteger bigInt= new BigInteger(1,digestedBytes); //1- positive
			return bigInt.toString(16); //hexadecimal - encrypted pwd
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Something went wrong during encryption: "+e);
		}
		return password;
	
	}
}
