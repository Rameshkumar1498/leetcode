package com.zoho.project.bank;
public class Staff
{
	private String password,  name;private long phone;private int role_id;private String status;private int branchid,customerid ;
	Staff(String password, String name, long phone, int role_id,String status)	
	{
		this.password=password;
		this.name=name;
		this.phone=phone;
		this.role_id=role_id;
		this.status=status;
	}
	Staff(int role_id, String name, int branch_id, int customerid,String status, String password)
	{
		this.role_id=role_id;
		this.name=name;
		this.branchid=branchid;
		this.customerid=customerid;
		this.status=status;
		this.password=password;
	}	
	String getPassword()
	{
		return password;
	}
	String getName()
	{
		return name;
	}
	long getPhone()
	{
		return phone;
	}
	int getRoleId()
	{
		return role_id;
	}
	String getStatus()
	{
		return status;
	}
	int getBrachId()
	{
		return branchid;
	}
	int getCustomerId()
	{
		return customerid;
	}
	void setBranchId()
	{
		this.branchid=branchid;
	}
}	
