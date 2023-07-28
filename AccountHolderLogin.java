package com.zoho.project.bank;
import com.zoho.project.bank.Query.*;
import java.util.Scanner;
import java.util.ArrayList;
public class AccountHolderLogin
{
AccountHolderProcess ahp=new AccountHolderProcess();
	
	
	private Scanner scanner=new Scanner(System.in);
	Query q=new Query();
	void login() 
	{
		System.out.println("Enter your customerId");
		int customerId=Validation.inttype();
	
		ArrayList<Accounts>al1=ahp.check(customerId);
		System.out.println("Enter your password");
		String password=scanner.nextLine();
		if(al1.size()==0)
		{
			return;
		}
		while(!al1.get(0).getPassword().equals(password))
		{
			System.out.println("Enter a valid password");
			password=scanner.nextLine();
		}
		System.out.println("1.TransferMoney\n2.checkBalance\n3.Show Details\n4.Show History\n 5. Show All accounts");
		byte check=Validation.bytetype();
		switch(check)
		{
			case 1:	ahp.transferMoney(customerId);
					break;
			case 2:
					System.out.println("Enter the IFSC code");
					String Ifsccode=scanner.nextLine();
					System.out.println("Enter your account Number");
					String accountNo=scanner.nextLine();
					ahp.checkBalance(customerId,Ifsccode,accountNo);
					break;
			case 3:	
					System.out.println(ahp.accountHolderDetails(customerId).toString());
					break;	
			case 4:	
					System.out.println("Enter your account Number");
					accountNo=scanner.nextLine();
					System.out.println("Enter the IFSC code");
					Ifsccode=scanner.nextLine();
					ahp.showTransaction(accountNo,Ifsccode);
					break;
			case 5:	
					ahp.showAccount();
					break;
			default:
				 	System.out.println("Enter the valid choice");
		}
		
	} 	
		
	
}
