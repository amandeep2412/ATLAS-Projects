package com.amazon.internalclassifieds.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB {

	public static String FILEPATH = "C:\\Users\\akkaurdr\\Desktop\\ATLAS_Java\\internalclassifieds\\src\\main\\java\\com\\amazon\\internalclassifieds\\db\\dbconfig.txt";
	public static String URL="";
	public static String USER="";
	public static String PASSWORD="";
	
	private static DB db = new DB();
	
	public static DB getInstance() {
		return db;
	}
	
	Connection connection;
	Statement statement;
	
	private DB() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Driver loaded successfully");
			createConnection();
		} catch (Exception e) {
			System.out.println("Something went wrong in loading the driver: "+e);
		}
	}
	
	private void createConnection() {
		try {
			File file = new File(FILEPATH);
			if(file.exists()) {
				FileReader reader = new FileReader(file);
				BufferedReader buffer = new BufferedReader(reader);
				
				URL = buffer.readLine();
				USER = buffer.readLine();
				PASSWORD = buffer.readLine();
				
				buffer.close();
				reader.close();
				System.out.println("DB configured using File");
			}else {
				System.err.println("Can't read the db config file..");
			}
			connection =DriverManager.getConnection(URL+";user="+USER+";password="+PASSWORD+";integratedSecurity=false;" +
                    "encrypt=true;trustServerCertificate=true");
			System.out.println("[DB] connection created..");
			
		} catch (Exception e) {
			System.out.println("Something went wrong in creating the connection: "+e);
		}
	}
	
	public int executeSQL(String sql) {
		int result =0;
		try {
			statement = connection.createStatement();
			result = statement.executeUpdate(sql);
			System.out.println("[DB] SQL Query Executed");
			
		} catch (Exception e) {
			System.err.println("something went wrong in executing the query: "+e);
		}
		return result;
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet set=null;
		try {
			//System.out.println("executing query: "+sql);
			statement = connection.createStatement();
			set = statement.executeQuery(sql);//which will retrieve the data from table into java application - kinda read operation
			//System.out.println("[DB] SQL query executed");
		}
		catch(Exception e) {
			System.err.println("something went wrong in executing the query: "+e);
		}
		return set;
	}
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (Exception e) {
			System.out.println("something went wrong: "+e);
		}
	}
	
	
}

