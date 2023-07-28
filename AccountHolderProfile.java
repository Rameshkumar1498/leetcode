package com.zoho.project.bank;
class AccountHolderProfile
{
 	 String name,dob,address,email; long phone; String gender;
 	AccountHolderProfile( String name, String dob, String address, String email, long phone, String gender)
 	{
 		this.name=name;
 		this.dob=dob;
 		this.address=address;
 		this.email=email;
 		this.phone=phone;
 		this.gender=gender;
 	}
	public String toString()
	{
		return "name : "+name+"\nDate Of Birth: "+dob+"\n Address: "+address+"\nemail: "+email+"\nphone number: "+phone+"\ngender: "+gender;
	}
}

