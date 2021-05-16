package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inquiry {

	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.cj.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget",
	 "root", "");
	 //For testing
	// System.out.print("Successfully connected");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}
	
	
	
	public String readInquiry()
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Name</th>" 
	 + "<th>Email</th><th>Subject</th>"
	 + "<th>Message</th>" 
	 + "<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from inquiry"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String eID = Integer.toString(rs.getInt("eID")); 
	 String name = rs.getString("name"); 
	 String email = rs.getString("email"); 
	 String subject = rs.getString("subject"); 
	 String message = rs.getString("message"); 
	// Add into the html table
	 output += "<tr><td>" + name + "</td>"; 
	 output += "<td>" + email + "</td>"; 
	 output += "<td>" + subject + "</td>"; 
	 output += "<td>" + message + "</td>"; 
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' "
	+ "class='btnUpdate btn btn-secondary' data-itemid='" + eID + "'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove' "
	+ "class='btnRemove btn btn-danger' data-itemid='" + eID + "'></td></tr>"; 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}
	
	
	public String insertItem(String name, String email, 
			 String subject, String message) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting  to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = " insert into inquiry(`eID`,`name`,`email`,`subject`,`message`)"
			+ " values (?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, name); 
			 preparedStmt.setString(3, email); 
			 preparedStmt.setString(4, subject); 
			 preparedStmt.setString(5, message); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readInquiry(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
	
	public String updateInquiry(String eID, String name, String email, 
			 String subject, String message) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE inquiry SET name=?,email=?,subject=?,message=? WHERE eID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, name); 
			 preparedStmt.setString(2, email); 
			 preparedStmt.setString(3, subject); 
			 preparedStmt.setString(4, message); 
			 preparedStmt.setInt(5, Integer.parseInt(eID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readInquiry(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
	
	
	public String deleteInquiry(String eID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from inquiry where eID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(eID)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readInquiry(); 
	 output = "{\"status\":\"success\", \"data\": \"" + 
	 newItems + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
}
