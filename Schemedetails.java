package com.zoho.project.bank;
public class Schemedetails
{
	private int schemeid;private String schemename;private float MAXTransactionLimit;private float minimumamount;
	public Schemedetails( int schemeid, String schemename, float MAXTransactionLimit, float minimumamount)
	{
		this.schemeid=schemeid;
		this.schemename=schemename;
		this.MAXTransactionLimit=MAXTransactionLimit;
		this.minimumamount=minimumamount;
	}
	int getschemeid()
	{
		return schemeid;
	}
	String getschemename()
	{
		return schemename;
	}
	float getTransactionlimit()
	{
		return MAXTransactionLimit;
	}
	float getminimumamount()
	{
		return minimumamount;
	}
	public String toString()
	{
		return ""+schemeid+"\t"+schemename+"\t"+MAXTransactionLimit+"\t"+minimumamount+"\n";
	}
}
