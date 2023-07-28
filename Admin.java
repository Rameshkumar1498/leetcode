package com.zoho.project.bank;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
public class Admin extends ApproveStaff implements AdminInterface
{
	int customerid1=0;
	void table(int roleid)
	{
		
		System.out.println("1.approveManager\n 2.Remove manager");
		while(true)
		{
			byte select=Validation.bytetype();
			if(select==1)
			{
					approveManager(1,"Approve");
					break;	
				
			}
			else if(select==2)
			{
				removeManager(1,"remove");
				break;
			}
			else
			{
				System.out.println("Enter the valid choice");
			}
		}
	}
	PreparedStatement ps=null;
	ResultSet rs=null;
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
	public void approveManager(int roleid,String state)
	{
		
		System.out.println("Enter the customerid for"+state);
		int customerid=Validation.inttype();
		
		Staff mDetails=managerDetails(customerid);
		
		if(!check(roleid, customerid))
		{
			System.out.println("This person is not a manager");
		
		 return;
		}if(mDetails==null)
		{
			return;
		}
		try{
		if((mDetails.getStatus().equals("appointed")||mDetails.getStatus().equals("Appointed")||mDetails.getStatus().equals("APPOINTED")))
		{
			System.out.println("This manager is currently  working in our bank");
			return;
		}
			check(roleid, customerid);
			ps=con.prepareStatement("update customer set status='appointed ' where customer_id=? ");
			ps.setInt(1,customerid);
			
			ps.executeUpdate(); 
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
			}
	}
	public void removeManager(int  roleid,String state)
	{
		System.out.println("Enter the customerid for"+state);
		int customerid=Validation.inttype();
		Staff mDetails=managerDetails(customerid);
		System.out.println(customerid);
		if(mDetails==null)
		{
			System.out.println("This person not available");
			return;
		}
		if(!check(roleid, customerid))
		{	System.out.println("this person not a manager");
		 return;
		}
		System.out.println(mDetails.getStatus());
		if(!(mDetails.getStatus().equals("appointed")||mDetails.getStatus().equals("Appointed")||mDetails.getStatus().equals("APPOINTED")))
		{
			System.out.println("This manager is currently not working in our bank");
			return;
		}
		try{	
		ps=con.prepareStatement("update customer set status='Rejected ' where customer_id=?");
		ps.setInt(1,customerid);
		ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		} 
	}
	private boolean check(int roleid,int customerid)	{	
		Staff mDetails=managerDetails(customerid);		
		if(roleid!=mDetails.getRoleId())
		{
			return false;
		}
		return true;
	}
	private Staff managerDetails(int customerId)  
	{
		try{
			ps=con.prepareStatement("select  u.password, c.name, c.phone, c.role_id,c.status from users u join customer c on c.customer_id=u.customer_id  join branch b on c.branch_id=b.id where c.customer_id=?");
			ps.setInt(1,customerId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return new Staff(rs.getString(1),rs.getString(2),rs.getLong(3),rs.getInt(4),rs.getString(5));
			}
		}
		catch(SQLException e)
		{
			System.out.println("The Customerid is not available");
		}
		return null;
	}
}
