package com.zoho.project.bank;
import java.sql.Statement;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import com.zoho.project.bank.Query.*;
public class Account
{	
	PreparedStatement ps=null;
	ResultSet rs=null;
		ArrayList<String> al1=new ArrayList<>();
		Query q=new Query();
	boolean checkAccount(long aadhar,int schemid,int roleid)
	{
		boolean check=false;
		try{
			 check= q.checkAccounts(aadhar,schemid,roleid);
		}
		catch(SQLException e)
		{
			System.out.println("account not checked");
		}
		return check;
	}
	int createAccount(int roleid){
		System.out.println("Enter adhaar number");
        	long aid = Validation.aadhar();int id=-1;	
        	int customerid=-1;String scheme="";
  		System.out.println("Enter your Date of birth yyyy-mm-dd");
		String dob=Validation.datetype();	
		try{
			scheme=q.newAccount(aid);       		
        		customerid=q.checkcustomer(Validation.name(),dob,roleid);        			
        	}
        	catch(SQLException e)
        	{
        		System.out.println("aadhar number not checked");
        	}
         	if(customerid!=-1){
          		System.out.println("Already you have register a account for " +scheme+ "plan");
        		return customerid;
        	}
        	System.out.println( schemedetails(dob).toString());
        	
		ArrayList<Schemedetails> al = schemedetails(dob);
		System.out.println("Enter the scheme id");
		int schemeid = Validation.inttype();
		if(checkAccount(aid, schemeid,roleid)) {
            		System.out.println("Sorry! you already have an same type account");
            		return id;
        	}
        	ArrayList<Branch> branchList = branchdetails();
        	if(customerid==-1)
      			customerid = new Customer().createcustomer(roleid);   	
        		System.out.println("Enter the branch id");
        		int branchid=checkbranchid();
        		String account = Validation.accountNo(branchid,schemeid);
        		insertAccount(customerid,branchid,schemeid, aid,account);        	
	    		return id;
	}
	void insertAccount(int customerid,int branchid,int schemeid,long aid,String account)
	{
		try{
		q.accountinsert( customerid, branchid, schemeid, aid, account);
        	 ps.executeUpdate();
        	}
        	catch(SQLException e)
        	{
        		System.out.println("not updated");
        	}
	}
	int checkbranchid()
	{
		int branchid=0;
		do{
      			branchid = Validation.inttype();
			try{
        			branchid=q.selectBranch(branchid);
        			break;	 
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
				System.out.println("branch is not shown");
				break;
			}	
        	}while(true);
        	return branchid;
	}
	ArrayList<Branch> branchdetails()
	{
		ArrayList<Branch> al=new ArrayList<>();
		try{
			al=q.showBranches();	
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Branch list is not added in 	array");
		}
		return al;
	}
	ArrayList<Schemedetails> schemedetails(String dob)
	{
		ArrayList<Schemedetails> al=null;
		try{
			 al=q.scheme(dob);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Scheme is not added");
		}
		return al;
	}
		
}
