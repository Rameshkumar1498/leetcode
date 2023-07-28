package com.zoho.project.bank;
public class Branch
{
	private int id;private String branchname,branchaddress,ifsccode;
	public Branch(int id,String branchname,String branchaddress,String ifsccode)
	{
		this.id=id;
		this.branchname=branchname;
		this.branchaddress=branchaddress;
		this.ifsccode=ifsccode;
	}
	int getid()
	{
		return id;
	}
	String getbranchname()
	{
		return branchname;
	}
	String getbranchaddress()
	{
		return branchaddress;
	}
	String getifsccode()
	{
		return ifsccode;
	}
}
