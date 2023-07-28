package com.zoho.project.bank;
class Transaction
{
	private int id;
	private String fromAccount,toAccount,type ;private float amount;private String transferdate,transfertype;
	Transaction(int id,String FromAccount,String toAccount,String type ,float amount,String Transferdate,String transfertype)
	{
		this.id=id;
		this.fromAccount=fromAccount;
		this.toAccount=toAccount;
		this.amount=amount;
		this.transferdate=transferdate;
		this.transfertype=transfertype;
	}
	int getTransactionId()
	{
		return id;
	}
	String getFromAccount()
	{
		return fromAccount;
	}
	String gettoAccount()
	{
		return toAccount;
	}
	Float getAmount()
	{
		return amount;
	}
	String getTransferDate()
	{
		return transferdate;
	}
	String getTransfertype()
	{
		return transfertype;
	}
	public String toString()
	{
		return " "+id+" "+fromAccount+" "+toAccount+" "+amount+" "+transferdate+" "+transfertype;
	}
}
