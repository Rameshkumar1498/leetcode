package com.zoho.project.bank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
public class StaffLogin  extends AccountHolderProcess implements CashierInterface
{
	PreparedStatement ps=null;
	ResultSet rs=null;
	Scanner scanner=new Scanner(System.in);
	Query q=new Query();
	Staff mDetails=	new ManagerLogin().managerDetails();
	byte cont=0;
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
	private Accounts aDetails=accountDetails();
	public void checkCustomerBalance()
	{
		System.out.println("your balance is"+aDetails.getBalance());
	}
	public void approveCustomerAccount(int branchid)
	{
		if(!checkBank(branchid))
			return;
			
		if(aDetails.getApprovestatus().equals("pending"))
		{
			q.checkApproval();
		}
	}
	public void depositAmount(int branchid)
	{
		if(!checkBank(branchid))
			return;
		System.out.println("Enter the amount for deposit");
		float amount=Validation.floattype();
		try{
		//accountDetails
			ps=DbConnection.getInstance().getConnection().prepareStatement("update account set balance=balance+?where accountno=?");
			ps.setFloat(1,amount);
			ps.setString(2,aDetails.getAccountNo());
			ps.executeUpdate();
			
		}
		catch(SQLException e)
		{
			System.out.println("amount not added");
		}
		try{
			ps=DbConnection.getInstance().getConnection().prepareStatement("insert into transaction(from_accountid,to_accountid,type,amount,transferdate)values(?,?,'deposit',?,now())");
			ps.setString(1,aDetails.getAccountNo());
			ps.setString(2,aDetails.getAccountNo());
			ps.setFloat(3,amount);
			ps.executeUpdate();
			
		}
		catch(SQLException e)
		{
			System.out.println("amount not added");
		}
	}
	public void withdrawAmount(int branchid)
	{
		if(!checkBank(branchid))
			return;
		System.out.println("Enter the amount for withdraw");
		float amount=Validation.floattype();
		if(amount>aDetails.getBalance())
		{
			System.out.println("you don't have an enough amount");
			return;
		}
		try{
			ps=DbConnection.getInstance().getConnection().prepareStatement("update account set balance=balance-?where accountno=?");
			ps.setFloat(1,amount);
			ps.setString(2,aDetails.getAccountNo());
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("amount not withdraw");
		}
		
	}
	boolean checkBank(int branchid)
	{
		if(aDetails.getBranchId()!=branchid)
		{
			System.out.println("You are from another branch");;
			return false;
		}
		
	return true;
	}
	public Accounts accountDetails()
	{
	
		try{
		
			System.out.println("Enter your accountNo");
			String account=scanner.nextLine();
			ps=DbConnection.getInstance().getConnection().prepareStatement("select a.acccountno, a.balance, a.status, a.approvestatus, a.activated_date, a.deactive_date, a.customer_id, c.roleid,  c.status, , b.ifsccode,a.branchid  from account a on  join customer c on a.customer_id=c.customer_id join position p on c.role_id=p.role_id join branch b on b.id=a.branchid join a.scheme_id=scheme_id a.acccountno=?");
			ps.setString(1,account);
			rs=ps.executeQuery();
			if(rs.next())
			{
					return( new Accounts(rs.getString(1),rs.getFloat(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getString(9),rs.getString(10),rs.getInt(11)));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
