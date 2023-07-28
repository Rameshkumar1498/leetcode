package com.zoho.project.bank;
import java.sql.Statement;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
class Login 
{
	private PreparedStatement ps=null;
	private ResultSet rs=null;
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
	Query q=new Query();
	void checkLogin(int roleId) 
	{
		int branchId=-1;
			System.out.println("Enter your userid");
			int id=0;
				id=checkManager(roleId);
			if(id<1)
			{
				System.out.println("You don't have an account please create account");
				return;
			}
			System.out.println("Enter your phone number");
			long mobile=Validation.phonenumber();
		boolean check=q.checkApproved(id,roleId, mobile);
		if(check)
		{
			System.out.println("You are registed and waiting for approval");
			return;
		}
			System.out.println("Enter your password");
			id=-1;
			try{
				branchId=checkUsers(id,roleId,mobile);	
			}
			catch(SQLException e)
			{
				System.out.println("id and password not checked");
			}
			if(id==-1)
			{
				return;
			}
			else
			{
				loginOptions(branchId,roleId);
			}
			
	}
	private int checkManager(int roleId)
	{
		int id=0;
		while(true){
			id=Validation.inttype();
			try{
				ps=con.prepareStatement("select * from customer   where customer_id=? and role_id=?");
				ps.setInt(1,id);
				ps.setInt(2,roleId);
				rs=ps.executeQuery();
				if(!rs.next())
				{
					System.out.println(" 1. Enter the valid userid 2. exit");
					int select=Validation.inttype();;
					while(select!=1&&select!=2)
					{
						System.out.println("Enter the valid inputs");
						select=Validation.inttype();
					}
					if(select==1)
					{
						id=Validation.inttype();
					}
					if(select==2)
					{
						return -1;
					}
				}
				else
				{
					return id;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
				
		}
	}
	int checkUsers(int id,int roleId,long mobile) throws SQLException
	{
		String password=Validation.password();
		while(true){
			ps=con.prepareStatement("select  from c.branchIdusers u join customer c on c.where u.customer_id=c. customer_id customer_id=? and u.password=?");
			ps.setInt(1,id);
			ps.setString(2,password);
			rs=ps.executeQuery();
			
			if(rs.next())
			{
				return rs.getInt(1);				
				
			}
			else
			{
				System.out.println(" 1. Enter the valid password 2. forgetpassword 3.exit");
				int select=Validation.inttype();
				while(select!=1&&select!=2&&select!=3)
				{
					System.out.println("Enter the valid inputs");
					select=Validation.inttype();
				}
				if(select==1)
				{
					System.out.println("Again Enter the valid userId");
					id=Validation.inttype();
					System.out.println("Again Enter the valid password");
					password=Validation.password();
				}
				else if(select==2)
				{
					password=forgetPassword(roleId, id,mobile);
					System.out.println("Password changed.");
					break;
				}
				else if(select==3)
				{
					return -1;
				}
			}
		}return -1;				
	}
	void loginOptions(int roleId,int branchId)
	{
		Staffselect ss=new Staffselect();
		if(roleId==1)
		{
			new ManagerLogin().checkManager();
		}
		else if(roleId==2||roleId==3)
		{
			ss.clerkselection(branchId);
		}
		else if(roleId==3)
		{
			ss.cashierSelection(branchId);
		}
		else if(roleId==4)
		{
			new AccountHolderLogin().login();
		}
				
		else if(roleId==5)
		{
			new Admin().table(roleId);		
		}
		else 
		{					
			System.out.println("Enter the valid inputs");
		}
	}
	String forgetPassword(int roleId,int customerId,long mobile)
	{
		String password="";
		String password1="";
		while(!password.equals(password1) ||password.equals(""))
		{
			System.out.println("Enter the valid password");
			password=Validation.password();
			System.out.println("Re-enter the password");
			password1=Validation.password();
		}
		try{		
			System.out.println("password");
			ps=con.prepareStatement("update users set password=? where customer_id=?");
			ps.setString(1,password);
			ps.setInt(2,customerId);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("password is not updated");	
		}
		return password;
	}	
}
