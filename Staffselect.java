package com.zoho.project.bank;
import java.sql.Statement;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import com.zoho.project.bank.Query.*;
class Staffselect
{
	StaffLogin sl=new StaffLogin();
	Staff staffId=new ManagerLogin().managerDetails();
	Scanner scanner=new Scanner(System.in);
	PreparedStatement ps=null;
	static Connection con=null;
	static 
	{
		try
		{
			con=DbConnection.getInstance().getConnection();
		}
		catch(SQLException e)	
		{
			e.printStackTrace();
		}
	}
	void staffSelection (int roleId,int branchId)
	{
	byte check=-1;
		
		do{
			
			StaffLogin sl=new StaffLogin();
			if(roleId==2)
			{
				clerkselection(branchId);
			}
		
			else if(roleId==3){
				cashierSelection( branchId);
			}
		System.out.println("Enter 1 to continue");
		check=Validation.bytetype();
		}while(check==1);	
	}
	void clerkselection(int branchId)
	{
	Clerk c=new Clerk();
	System.out.println("1. Show Balance\n2..Approve account\n3. Back"); 	
	byte select=Validation.bytetype();
	switch(select)
	{		
		case 1:	 c.checkCustomerBalance();
				break;
		case 2:	c.approveCustomerAccount(branchId);
				break;
		case 3: 	break;
		default:	System.out.println("Enter the correct choice");
		}			
	}
	void cashierSelection(int branchId)
	{
		System.out.println("1. Deposit\n2. Withdraw\n3.Approve account\n4. Back"); 
		byte select=Validation.bytetype();
		switch(select)
		{
			case 1: 	sl.depositAmount(branchId);
					break;
			case 2: 	sl.withdrawAmount(branchId);
					break;
			case 3: 	sl.approveCustomerAccount(branchId);
					break;
			case 4:	try{
						DbConnection.getInstance().closedb();
					}
					catch(SQLException e)
					{
						System.out.println(e.getMessage());
					}
					return;
			default:
					System.out.println("Enter the correct choice");
		}	
	}
	ResultSet rs=null;
	void checkStaff()
	{
		if(staffId==null)
		{
			System.out.println("Enter the valid id");
			return;
		}
		else if(staffId.getRoleId()!=2&&staffId.getRoleId()!=3)
		{
			System.out.println("you are not a staff");
			return;
		}
		else if(!staffId.getStatus().equals("appointed"))
		{
			System.out.println("You are not appointed now");
			return;	
		}
		System.out.println("Enter your password");
		String password=scanner.nextLine();
		if(!staffId.getPassword().equals(password))
		{
			System.out.println("Your password is wrong");
			System.out.println("1. Enter valid password 2. change password 3. exit");
			byte selection=Validation.bytetype();
			switch(selection)
			{
				case 1: 
						System.out.println("Enter your password");
						password=Validation.password();
						break;
				case 2: 
						forgetpassword();
						break;
				case 3: 
						return;
				default:
						System.out.println("Enter the valid selection");			
			}			
		}
		int roleid=staffId.getRoleId(),branchid=staffId.getBrachId();			
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
			System.out.println("Again Enter the password");
			 password1=Validation.password();
			System.out.println("Confirm password");
			 password2=Validation.password();
		
		}
		try{
			ps=con.prepareStatement("update Users set password=? where customer_id=?");
			ps.setString(1,password1);
			ps.setInt(2,staffId.getCustomerId());
		}
		catch(SQLException e)
		{
		e.printStackTrace();
			System.out.println("password not updated");
		}
	return password1;
	}	
}
