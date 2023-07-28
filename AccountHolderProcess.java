package com.zoho.project.bank;
import java.util.Scanner;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
public class AccountHolderProcess  implements CustomerInteface
{
	static Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	Scanner scanner=new Scanner(System.in);
	
	Query q=new Query();
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
	}public void checkBalance(int customerid,String ifsccode, String accountNo)
	{
		ArrayList<Accounts> al=check(customerid);
		if(al.size()==0){System.out.println("Please check the userid");return;}
		for(int i=0;i<al.size();i++)
		{
			if(al.get(i).getAccountNo().equals(accountNo) &&!ifsccode.equals(al.get(i).getIfsccode()))
			{
				
					System.out.println("please check the ifsccode");
			}
			else if(al.get(i).getAccountNo().equals(accountNo) &&ifsccode.equals(al.get(i).getIfsccode()))	
			{
				System.out.println(al.get(i).getBalance());
				
			}	
		}
	}
	void checkBalance(int customerid)
	{
		ArrayList<Accounts> al=check(customerid);
		if(al.size()==0){System.out.println("Please check the userid");return;}
		for(int i=0;i<al.size();i++)
		{
			System.out.println(al.get(i).getAccountNo()+"-"+al.get(i).getBalance());
		}
	}
	
	
	ArrayList<Accounts> check(int customerId)
	{
		ArrayList<Accounts> al=new ArrayList<>();
		try{
			al=q.showAllaccounts(customerId);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("account details not show");
		}
		return al;
	}
	public void showAccount()
	{
		ArrayList<Accounts> al=new ArrayList<>();
		for(int i=0;i<al.size();i++)
		{
			System.out.println(al.get(i).getAccountNo());
		}
	}
	AccountHolderProfile accountHolderDetails(int customerid)
	{
		try{
			ps=con.prepareStatement("select name,dob,address,email,mobileno,gender from customer  where customer_id=?");
			ps.setInt(1,customerid);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return (new AccountHolderProfile(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getLong(5),rs.getString(6)));
			}
			else
			{
				            System.out.println("Customer with ID " + customerid + " not found.");
			}	
		}
		catch(SQLException e)
		{
			System.out.println("Details have some problem");
		}
		return null;
	}
	public void transferMoney(int customerid)
	{
		ArrayList<Accounts>	al=check(customerid);
		System.out.println("Enter your account number");
		String fromaccountNo=scanner.nextLine();
		float balance=0.0f;
		for(int i=0;i<al.size();i++)
		{
			if(al.get(i).getAccountNo().equals(fromaccountNo))
			{
				balance=al.get(i).getBalance();
			}
			if(i==al.size()-1)
			{
				break;
			}
		}
		System.out.println("Enter the amout for transaction");
		float amount=Validation.floattype();
		if(amount>balance)
		{
			System.out.println("you don't have enough amount");
			return;
		}
		String type=transfertype(amount);
		String toAccountNo=checkAccountNumber();
		try{
			q.insertTransaction(fromaccountNo,toAccountNo,amount,type);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	String checkAccountNumber()
	{
		return q.checkAccountNo();	
	}
	ArrayList<Transaction> show(String accountNo,String ifsccode)
	{
		ArrayList<Transaction> al=new ArrayList();
		try{
			ps=DbConnection.getInstance().getConnection().prepareStatement("select  * from transaction where from_Accountid=? or to_accountid=? ");
			ps.setString(1,accountNo);
			ps.setString(2,accountNo);
			rs=ps.executeQuery();
			while(rs.next()){
				al.add(new Transaction(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getFloat(5),rs.getString(6),rs.getString(7)));	
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
	return al;		
	}
	void showTransaction(String accountNo,String ifsccode)
	{
		ArrayList<Transaction> al=show( accountNo, ifsccode);
		System.out.println(al);
	}
	
	
	String transfertype(float balance)
	{
		while(true){
			System.out.println("1.IMPS 2.NEFT 3.rtgs");
			byte selecttype=Validation.bytetype();
			while(selecttype<=0||selecttype>3)
			{
				System.out.println("Enter the valid type");
				selecttype=Validation.bytetype();
			}
			if(selecttype==1 && balance<200001)
			{
				return "IMPS";
			}
			else if(selecttype==3 && balance>200000)
			{
				return "RTGS";
			}
			else if(selecttype==2)
			{
				return "NEFT";
			}
		}
	}
}
