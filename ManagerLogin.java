package com.zoho.project.bank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
public class ManagerLogin
{
	Staff mDetails=	managerDetails();
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
		
		}
	}
	void table()
	{do{
		System.out.println("1.Change password\n2. Approve Staff\n3. Remove Staff\n4. Approve account\n5. Activate account\n6. Exit");
		byte select=Validation.bytetype();
		switch(select)
		{
			case 1: forgetpassword();
				 break;
			case 2: ApproveStaff c=new ApproveStaff();
				c.approveStaff();
				 break;
			case 3:RemoveStaff rm=new RemoveStaff();
				rm.removeStaff();
				 break;
			case 4:ApproveAccount aa=new ApproveAccount();
				aa.approveAccount();
				 break;
			case 5:Activate a=new Activate();
				a.activateAccount();
				  break;
					
			case 6: 
					return;
			default: System.out.println("Enter the valid choice");
		}
		System.out.println("press 1 to continue");
		cont=Validation.bytetype();
		}while(cont==1);
	}
	Scanner scanner=new Scanner(System.in);
	PreparedStatement ps=null;
	ResultSet rs=null;
	Staff managerDetails()
	{
		System.out.println("Enter your customerid");
		int customerid=Validation.inttype();
		try{
			ps=con.prepareStatement("select c.role_id,c.name,c.branch_id,c.customer_id,c.status,u.password from customer c join users u on u.customer_id=c.customer_id where c.customer_id=? ");
			ps.setInt(1,customerid);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return (new Staff(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Manager is not checked");
		}
		System.out.println("Enter the valid customerid");
		return null;
	}
	void checkManager()
	{
		if(mDetails==null)
		{
			System.out.println("Enter the valid customerid");
		}
		else if(mDetails.getRoleId()!=1)
		{
			System.out.println("You are not manger");
			return;
		}	
		else if(mDetails.getStatus().equals("appointed"))
		{
			System.out.println(mDetails.getStatus());
			System.out.println("You are not appointed now");
			return;	
		}
		System.out.println("Enter your password");
		String password=scanner.nextLine();
		if(!mDetails.getPassword().equals(password))
		{
			System.out.println("Your password is wrong");
			System.out.println("1. Enter valid password 2. change password 3. exit");
			byte selection=Validation.bytetype();	
			if(selection==1)
			{
				System.out.println("Enter your password");
				password=Validation.password();
			}
			else if(selection==2)
			{
				forgetpassword();
			}
			else if(selection==3)
			{
				return;
			}
			else
			{
				System.out.println("Enter the valid selection");
			}
			
		}
		table();			
	}
	String forgetpassword()
		{
			System.out.println("Enter the password");
			String password1=Validation.password();
			System.out.println("Confirm password");
			String password2=Validation.password();
			while(password1.equals(password2))
			{
				System.out.println("your password is not match");
				System.out.println("Enter the password");
				 password1=Validation.password();
				System.out.println("Confirm password");
				 password2=Validation.password();
			
			}
			try{
				ps=con.prepareStatement("update Users set password=? where customer_id=?");
				ps.setString(1,password1);
				ps.setInt(2,mDetails.getCustomerId());
			}
			catch(SQLException e)
			{
				System.out.println("password not updated");
			}
			return password1;
		}
}
