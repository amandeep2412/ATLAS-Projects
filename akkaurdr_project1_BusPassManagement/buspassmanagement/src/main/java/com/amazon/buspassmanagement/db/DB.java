package com.amazon.buspassmanagement.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
//to connect and to execute sql statements in database mysql or mssql
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.management.remote.JMXConnectionNotification;

/*detailed:
 * add dependency in pom
 * class.forName and then load the driver..
 */
/*
 * JDBC procedure:
 * 
 * 1.load the driver
 * 		1.1 add the mssql library in your project
 * 			from mssql server - in pom.. latest version
 * 		1.2 API -> Class.forname
 * 2. connect to database
 * 		url
 * 		username, pwd,
 * 3. 
 */

public class DB {

	public static String FILEPATH ="C:\\Users\\akkaurdr\\Desktop\\ATLAS_Java\\buspassmanagement\\src\\main\\java\\com\\amazon\\buspassmanagement\\db\\dbconfig.txt";
	public static String URL = "";
	public static String USER = "";
	public static String PASSWORD = "";
	Connection connection; 
	Statement statement; //API from JDBC to execute sql statemnts
	private static DB db = new DB();
	//API from JDBC package to store connection
	
	
	public static DB getInstance() {
		
		return db;
	}
	
	
	//SINGLETON
	private DB() {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("[DB] driver loaded successfully");
			createConnection();
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println("Something went wrong: "+e);
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
			connection = DriverManager.getConnection(URL+";user="+USER+";password="+PASSWORD+";integratedSecurity=false;" +
                    "encrypt=true;trustServerCertificate=true");
			System.out.println("[DB] connection created..");
		
		
		} catch (Exception e) {
			System.err.println("something went wrong in creating connection: "+e);
			// TODO: handle exception
		}
	}
	
	public int executeSQL(String sql) {
	int result = 0;
	try {
		statement = connection.createStatement();
		result = statement.executeUpdate(sql);
		//System.out.println("[DB] SQL query executed");
	}
	catch(Exception e) {
		System.err.println("something went wrong in executing the query: "+e);
	}
	return result;
	}
	
	//supposed to return some data from table
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
			// TODO: handle exception
			System.out.println("something went wrong: "+e);
		}
	}
}
