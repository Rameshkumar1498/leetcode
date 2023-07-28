package com.zoho.project.bank;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
class Validation
{
	static Scanner scanner=new Scanner(System.in);
	static int inttype()
	{
		int a=-1;
		while(a==-1)
		{
			if(a!=-1)
			{
				break;
			}
		try
		{
			a=scanner.nextInt();
		}
		catch(InputMismatchException e)
		{
			System.out.println("Enter the valid inputs");
			a=-1;
		}		
			scanner.nextLine();
		}
		return a;
	}
	static float floattype()
	{
		float a=-1.0f;
		while(a==-1.0f)
		{
			if(a!=-1.0f)
			{
				break;
			}
		try
		{
			a=scanner.nextFloat();
		}
		catch(InputMismatchException e)
		{
			System.out.println("Enter the valid inputs");
			a=-1.0f;
		}
		
			scanner.nextLine();
		}
		return a;
	}
	static long longtype()
	{
		long a=-1;
		while(a==-1)
		{
			if(a!=-1)
			{
				break;
			}
		try
		{
			a=scanner.nextLong();
		}
		catch(InputMismatchException e)
		{
			System.out.println("Enter the valid inputs");
			a=-1;
		}
		
			scanner.nextLine();
		}
		return a;
	}
	static byte bytetype()
	{
		byte b=-1;
		while(b==-1)
		{
		
			if(b!=-1)
			{
				break;
			}
			try
			{
				b=scanner.nextByte();
			}
			catch(InputMismatchException e)
			{
				System.out.println("Enter the valid inputs");
				b=-1;
			}
			scanner.nextLine();
		}
		return b;	
	}
	static String datetype() {
        Scanner scanner = new Scanner(System.in);
	int year=0;
	byte date=0;byte month=0;
            	System.out.println("Enter the year");
           	
            		year=inttype();
            		while(year<1880)
            		{
            			System.out.println("Enter the valid year");
            			year=inttype();          			
            		}
            			System.out.println("Enter the month");
            			month=bytetype();
            			while(month==0||month>12)
            			{
            				System.out.println("Enter the valid month");
            				month=bytetype();
            			}
            			System.out.println("Enter the date");
            			date=bytetype();
            			
            			while((((month%2!=0 && month<8)||(month%2==0 && month>7))&&(date>31||date==0))||(((month%2==0 && month<8)||(month%2!=0 && month>7))&&(date>30||date==0)))
            			{
            				System.out.println("Enter the valid date");
            				date=bytetype();
            			}
            			
            			while(((year%4==0 && month==2 && date>28)||date==0)||((year%4!=0 && month==2 && date>27)||date==0))
            			{
            				System.out.println("Enter the valid date");
            				date=bytetype();
            			}
            			
        return (String.format("%02d",year)+"-"+String.format("%02d",month)+"-"+String.format("%02d",date));
   
}
	static String name()
	{
	String a_name="";
		do{
		a_name=scanner.nextLine();
			for(int i=0;i<a_name.length();i++){
				if ((a_name.charAt(i) < 'a' || a_name.charAt(i) > 'z') && (a_name.charAt(i) < 'A' && a_name.charAt(i) > 'Z') && a_name.charAt(i) != ' ')
				{
					System.out.println("Enter the valid name");
					a_name="";
					break;
				}
				
			}
		}
		while(a_name=="");
		
		return a_name;
	}
	static String mailid() {
		String mail = "";
		do {
			mail = scanner.nextLine();
			if (!mail.endsWith(".com") && !mail.endsWith(".in")&& !mail.endsWith(".org")) {
			System.out.println("Enter a valid mailid");
            		mail = "";
        		}
        		byte count = 0;
        		for (int i = 0; i < mail.length(); i++) {
            			if (mail.charAt(i) == '@') {
                				count++;
            			}
            
            
				if (mail.endsWith("com") && ((count ==2)||(count==1))) {
            				mail = "";break;
        			} else if (count != 0 && !mail.endsWith(".com")) {
            				System.out.println("Enter a valid mailid");
            				mail = "";
            				break;
        			}     
            
            				if ((mail.charAt(i) < '0' || mail.charAt(i) > '9') &&(mail.charAt(i) < 'a' || mail.charAt(i) > 'z') && (mail.charAt(i) < 'A' || mail.charAt(i) > 'Z') &&mail.charAt(i) != '.' && mail.charAt(i) != '_' && mail.charAt(i) != '-') {
                				mail = "";
                				break;
            			}
        		}
    		} while (mail.equals(""));
    		return mail;
	}	
	static long phonenumber()
	{
		long phone=0;
		try{
			 phone=scanner.nextLong();
		}
		catch(InputMismatchException e)
		{
			System.out.println("Enter the valid phone number");
		}	
		while(phone<6000000000l || phone>9999999999l)
		{
			System.out.println("Enter the valid phone number");
			try{
				phone=scanner.nextLong();
			}
			catch(InputMismatchException e)
			{
				e.printStackTrace();
				
			}
			scanner.nextLine();
		}
		return phone;
	}
	static PreparedStatement ps=null;
	static ResultSet rs=null;
	static String accountNo(int branchid,int schemeid)
	{
		String accountNo="";
		long count=1l;
		try{
			ps=DbConnection.getInstance().getConnection().prepareStatement("select count(id) from account where branchid=? and scheme_id=?");
			ps.setInt(1,branchid);
			ps.setInt(2,schemeid);
			rs=ps.executeQuery();
			while(rs.next())
			{
				count=rs.getLong(1);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return String.format("%04d",branchid)+""+String.format("%02d",schemeid)+String.format("%08d",count);
	}
	static long aadhar()
	{
		long aadhar=0l;
		do{
			try{
				aadhar=scanner.nextLong();
				if(aadhar>9999_9999_9999l||aadhar<1999_9999_9999l)
				{
					System.out.println("enter the correct aadhar number");
					aadhar=0l;
				}
				else
				{
					break;
				}
			}
			catch(InputMismatchException e)
			{
				aadhar=0l;
			}
			scanner.nextLine();
		}while(aadhar==0l);
		return aadhar;
	}
	static String password() {
		char[] password;
		do {
        		password = System.console().readPassword();
        		if (password.length < 8) {
				    System.out.println("Minimum length of password must be 8 digits");
				    password = new char[0];
			} else if (password.length > 20) {
				System.out.println("Maximum length of password must be 20 digits");
				password = new char[0];
        		}
			boolean cap = false, small = false, sp = false, num = false;
			for (int i = 0; i < password.length; i++) {
				if (password[i] >= '0' && password[i] <= '9') {
				num = true;
				} else if (password[i] >= 'a' && password[i] <= 'z') {
					small = true;
				} else if (password[i] >= 'A' && password[i] <= 'Z') {
					cap = true;
				} else if (password[i] == '!' || password[i] == '+' || password[i] == '@' || password[i] == '#' || password[i] == '$' || password[i] == '%' || password[i] == '^' || password[i] == '&' || password[i] == '*' || password[i] == '(' || password[i] == ')' || password[i] == '-' || password[i] == '=' || password[i] == '|' || password[i] == '<' || password[i] == ',' || password[i] == '.' || password[i] == '>' || password[i] == '?' || password[i] == '/' || password[i] == '[' || password[i] == '{' || password[i] == ']' || password[i] == '}' || password[i] == '_') {
					sp = true;
				}

				if (i == password.length - 1 && !num) {
					System.out.println("The password must contain at least one number");
					password = new char[0];
				} else if (i == password.length - 1 && !cap) {
					System.out.println("The password must contain at least one capital letter");
					password = new char[0];
				} else if (i == password.length - 1 && !small) {
					System.out.println("The password must contain at least one small letter");
					password = new char[0];
				} else if (i == password.length - 1 && !sp) {
					System.out.println("The password must contain at least one special character");
					password = new char[0];
				}
 			}
		} while (password.length == 0);
    		String password1 = String.valueOf(password);
    		String password2 = "";
	    	for (int i = 0; i < password.length; i++) {
			password2 += password1.charAt(i) + "0";
	    	}
    		return password2;
	}
	String address()
	{
		System.out.println("Enter your door number");
		String doorno=String.valueOf(Validation.inttype());
		System.out.println("Enter you Streetname");
		String streetname=Validation.name().replaceAll("\\s","");
		System.out.println("Enter your city");
		String city=Validation.name().replaceAll("\\s","");
		System.out.println("Enter your pincode");
		int pincode=0;
	
		pincode=Validation.inttype();
		while(pincode>999999||pincode<100000){
		System.out.println("Enter the valid inputs");}
			
		System.out.println("Enter your district name");
		String district=Validation.name().replaceAll("\\s","");
		System.out.println("Enter your state");
		String state=Validation.name().replaceAll("\\s","");
		return ""+doorno+","+streetname+","+city+","+district+","+state;	
	}	
}
