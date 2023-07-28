package com.zoho.project.bank;
import com.zoho.project.bank.Query.*;
public class Clerk extends AccountHolderProcess implements ClerkInterface
{
	StaffLogin s=new StaffLogin();
	private Accounts aDetails=s.accountDetails();
	public void checkCustomerBalance()
	{
		System.out.println("your balance is"+aDetails.getBalance());
	}
	Query q=new Query();
	public void approveCustomerAccount(int branchid)
	{
		if(!s.checkBank(branchid))
			return;
			
		if(aDetails.getApprovestatus().equals("pending"))
		{
			q.checkApproval();
		}
		
	}
}
