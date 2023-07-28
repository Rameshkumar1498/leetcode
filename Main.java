package com.zoho.project.bank;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.sql.SQLException;
import com.zoho.project.bank.Query.*;
class Main
{
	Scanner scanner=new Scanner(System.in);
	public static void main(String args[])
	{
		new Database().createtable();
		try{
			new Main().bankModelView();
		}
		finally
		{	
			try{
				DbConnection.getInstance().closedb();
			}
			catch(SQLException e)
			{
				System.out.println("Database not closed");
			}
			System.out.println("Thanks for using our bank service");
		}
	}
	static Customer CI=new Customer(); 	
		
	void bankModelView() 
	{
		System.out.println("1.AccountCreation\n2.Customer/Staff Login\n3. Register\n4. Exit");
		byte select=0;
		while(true)
		{
			 select=Validation.bytetype();
			if(select<=5&&select>0)
			{
					break;
			}
			else
			{	
				System.out.println("Enter the correct number");
			}	
		}
		switch(select)
		{
			case 1:
					new Account().createAccount(1);
					break;
			case 2:
					userlogin();
					break;
			case 3:
					userRegister();
					break;
			case 4: 
					return;
		}	
		
	}
	void userlogin()
	{
		System.out.println("Enter the role for Login");
		System.out.println("1. Manager\n2. clerk\n3. Accountant\n4. Accountholder\n5. Admin\n6. Exit");
		int id=0;
		while(true)
		{
			id=Validation.inttype();
			if((id<=3&&id>0)||id==5)
			{
				new Login().checkLogin(id);
				break;
			}
			else if(id==4)
			{
				new AccountHolderLogin().login();
			}
			else if(id==6)
			{
				return;
			}
			else
			{	
				System.out.println("Enter the correct number");
			}
		}
	}
	void userRegister()
	{
		System.out.println("Enter the role for Register");
		System.out.println("1. Staff\n2. Accountholder\n 3. Exit");
		int id=0;byte select=0;
		while(true)
		{
			id=Validation.inttype();
			if(id>=3||id==0)
			{
				id=Validation.inttype();
			}
			else
			{	
				break;
			}		
		}
		if(id==1)
		{
			staffRegister(select);	
		}
		else if(id==2)
		{
			accountHolderRegister(select);
		}
		else
		{
			return;
		}
	}
	void staffRegister(byte select){
		System.out.println("1. Manager\n2. clerk\n3. Accountant");
		while(select<=0||select>3){
			select=Validation.bytetype();}
		
		CI.createcustomer(select);
	}
		
	void accountHolderRegister(byte select)
	{
			new Account().createAccount(select);
	}
}

