package com.zoho.project.bank;

public class Accounts
{
private  String name,dob,address,email;private long phone;private String gender;
private String acccountno;private float balance; private String accountstatus; private String approvestatus; private String activated_date; private String deactive_date; private int customer_id; private int roleid; private  String customerstatus; private String ifsccode;private int branchid;private String password;
	public Accounts( String acccountno,  float balance,  String accountstatus,  String approvestatus,  String activated_date, String deactive_date,  int customer_id, int roleid,   String customerstatus,  String ifsccode,int branchid)
	{
		this.acccountno=acccountno;
		this.balance=balance;
		this.accountstatus=accountstatus;
		this.approvestatus=approvestatus;
		this.activated_date=activated_date;
		this.customer_id=customer_id;
		this.deactive_date=deactive_date;
		this.roleid=roleid;
		this.customerstatus=customerstatus;
		this.ifsccode=ifsccode;
		this.branchid=branchid;
	}
	public Accounts( String acccountno,  float balance,  String accountstatus,  String approvestatus,  String activated_date, String password,  int customer_id, int roleid,   String customerstatus,  String ifsccode)
	{
		this.acccountno=acccountno;
		this.balance=balance;
		this.accountstatus=accountstatus;
		this.approvestatus=approvestatus;
		this.activated_date=activated_date;
		this.customer_id=customer_id;
		this.password=password;
		this.roleid=roleid;
		this.customerstatus=customerstatus;
		this.ifsccode=ifsccode;
	}
	String getPassword()
	{
		return password;
	}
	public int getCustomerId()
	{
		return customer_id;
	}
	String getAccountNo()
	{
		return acccountno;
	}
	float getBalance()
	{
		return balance;
	}
	String getAccountstatus()
	{
		return accountstatus;
	}
	String getApprovestatus()
	{
		return approvestatus;
	}
	String getDeactiveDate()
	{
		return deactive_date;
	}
	int getRoleId()
	{
		return roleid;
	}
	String getCustomerstatus()
	{
		return customerstatus;
	}
	String getIfsccode()
	{
		return ifsccode;
	}
	int getBranchId()
	{
		return branchid;
	}
	
}
