package com.zoho.project.bank;
import java.util.Scanner;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
class Customer
{
	Scanner scanner=new Scanner(System.in);
	PreparedStatement ps=null;
	ResultSet rs=null;
	Query q=new Query();
	static Connection con=null;
	static 
	{
		try
		{
			con=DbConnection.getInstance().getConnection();
		}
		catch(SQLException e)	
		{
			System.out.println(e.getMessage());
		}
	}
	int createcustomer(int roleid)
	{
		System.out.println("Enter your name");
		String name=Validation.name().replaceAll("\\s","").toLowerCase();
		System.out.println("Enter the dateofbirth");
		String dob=Validation.datetype();
		int id=q.checkcustomer(name,dob,roleid);
		if(id<0)
		{
			System.out.println("you already have a customer_id and Your customer_id"+id);
			return id;
		}
		System.out.println("Enter your address below");
		String address=new Validation().address().toLowerCase();
		ArrayList<Branch>  al=new Account().branchdetails();
		Account acc=new Account();
		int branchid=acc.checkbranchid();
		System.out.println("Enter your mailid");
		String mail=Validation.mailid().replaceAll("\\s","").toLowerCase();
		System.out.println("Enter a bloodgroup");
		String bloodgroup=scanner.nextLine();
		System.out.println("Enter your password");
		String password=Validation.password();
		String status="pending";		
		System.out.println("Enter the mobile number");
		long mobile=Validation.phonenumber();
		System.out.println("Enter your gender");
		scanner.nextLine();
		String gender=Validation.name();
		id=q.insertCustomer(roleid, name, dob,address, bloodgroup,mail, branchid,status,mobile,gender);
		return q.insertid( id,password);	
	}
}
