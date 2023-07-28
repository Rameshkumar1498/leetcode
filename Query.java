package com.zoho.project.bank.Query;
import com.zoho.project.bank.*;
import java.sql.Statement;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Connection;
public class Query
{
	static Scanner scanner=new Scanner(System.in);
	PreparedStatement ps=null;
	ResultSet rs=null;
	static Statement st=null;
	static Connection con=null;
	static 
	{
		try
		{
			con=DbConnection.getInstance().getConnection();
			st=con.createStatement();
		}
		catch(SQLException e)	
		{
			System.out.println(e.getMessage());
		}
	}
	public boolean checkAccounts(long aadhar,int schemid,int roleid) throws SQLException
	{
		ps=con.prepareStatement("select  from account   where aadharnumber=? and scheme_id=?");
		ps.setLong(1,aadhar);
		ps.setInt(2,schemid);
		rs=ps.executeQuery();
		if(rs.next())
		{
			 return false;
		}
		return true;
	}
	public String newAccount(long aadharid) throws SQLException
	{
		ps = con.prepareStatement("select s.scheme_name from scheme s join account a on a.scheme_id=s.scheme_id where a.aadharnumber=?");
        	ps.setLong(1, aadharid);
        	rs = ps.executeQuery();
        	if (rs.next()) {
        		return rs.getString(1);
        	}
        	return "";
	}
	public void accountinsert(int customerid,int branchid,int schemeid,long aid,String account) throws SQLException
	{
		ps = con.prepareStatement("INSERT INTO Account (customer_id, branchid, scheme_id, balance, aadharnumber, status, approveStatus, activated_date,accountno) VALUES (?, ?, ?, 0.00, ?, 'NotActive', 'Registered', now(),?) ");
        	ps.setInt(1, customerid);
        	ps.setInt(2, branchid);
        	ps.setInt(3, schemeid);
        	ps.setLong(4, aid);
		ps.setString(5,account);
	}
	public ArrayList<Schemedetails> scheme(String dob) throws SQLException
	{
		ArrayList<Schemedetails> al=new ArrayList();
		System.out.println(String.format("%-10s %-50s %-25s %-5s","schemeid","schemename","MAXTransaction per month","minimum Transaction limit"));
		
		rs=st.executeQuery("select scheme_id,scheme_name,maxlimitperday,minimum_amount,Date_part('year',age(now(),'"+dob+"'))>agetype as dp from scheme s ");
		while(rs.next() && rs.getBoolean(5))
		{
			al.add(new Schemedetails(rs.getInt(1),rs.getString(2),rs.getFloat(3),rs.getFloat(4)));
		}
		return al;
	} 
	public int selectBranch(int branchId) throws SQLException
	{
		ps=con.prepareStatement("select * from branch where id=?");
        	ps.setInt(1,branchId);
        	rs=ps.executeQuery();
		if(rs.next())
		{
			branchId=rs.getInt(1);
		}
		else
		{
			System.out.println("Enter the correct branchid");
		}
		return branchId;
	}
	public ArrayList<Branch> showBranches() throws SQLException
	{
		ArrayList<Branch> al=new ArrayList();
		
			ps=con.prepareStatement("select b.id,b.branchname,b.branchaddress,b.ifsccode from branch b join customer c on c.branch_id<>b.id ");
			System.out.println(String.format("%-10s %-70s %-25s %-5s","branchid","branchname","branch address","ifsccode"));
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(String.format("%-10s %-70s %-25s %-5s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
				al.add(new Branch(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
			return al;
	}
	public ArrayList<Accounts> showAllaccounts(int customerId) throws SQLException
	{
		ArrayList<Accounts> account=new ArrayList<>();
		ps=con.prepareStatement("select a.accountno,a.balance,a.status,a.approvestatus,a.activated_date,u.password,a.customer_id, c.role_id, c.status, b.ifsccode from customer c join users u on u.customer_id=c.customer_id join account a on a.customer_id=c.customer_id join branch b on b.id=a.branchid where c.customer_id=?");
		ps.setInt(1,customerId);
		rs=ps.executeQuery();
		while(rs.next())
		{
			account.add(new Accounts(rs.getString(1), rs.getFloat(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
		}
		return account;
	}
	public void insertTransaction(String fromaccountNo,String toAccountNo,float amount,String type) throws SQLException
	{
		ps=con.prepareStatement("insert into transaction(from_accountid,to_accountid,type,amount,transferdate,transfertype) values(?,?,'Transfer',?,now(),?)");
		ps.setString(1,fromaccountNo);
		ps.setString(2,toAccountNo);
		ps.setFloat(3,amount);
		ps.setString(4,type);
		ps.executeUpdate();
	}
	public int insertCustomer(int roleid,String name,String dob,String address,String bloodgroup,String mail,int branchid,String status,long mobile,String gender)
	{
		try{
			PreparedStatement ps = con.prepareStatement("insert into customer(role_id, name, dob, address, blood_group, email, branch_id, status, mobileno, gender) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning customer_id");
			ps.setInt(1, roleid);
			ps.setString(2, name);
			ps.setDate(3, Date.valueOf(dob));
			ps.setString(4, address);
			ps.setString(5, bloodgroup);
			ps.setString(6, mail);
			ps.setInt(7, branchid);
			ps.setString(8, status);
			ps.setLong(9, mobile);
			ps.setString(10, gender);
			rs = ps.executeQuery();
			if(rs.next())
			{
					return rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	
		return 0;
	}
	public int insertid(int id,String password)
	{
		try{
		ps= con.prepareStatement("insert into users(customer_id,password) values(?,?)");
		ps.setInt(1,id);
		ps.setString(2,password);
		ps.executeUpdate();
		}
		catch(SQLException e)
		{System.out.println("not inserted ");}
		System.out.println("your customer id="+id);
		return id;		
	}	
	public String checkAccountNo()
	{
		String accountNumber="";
		while(true){
		System.out.println("Enter the to account number");
		accountNumber=scanner.nextLine();
		System.out.println("Enter the ifsccod");
		String ifsccode=scanner.nextLine();		
		try{
			ps=con.prepareStatement("select b.ifsccode from account a join branch b on a.branchid=b.id where a.accountNo=?");
			ps.setString(1,accountNumber);
			rs=ps.executeQuery();
			if(rs.next())
			{
				while(!ifsccode.equals(rs.getString(1)))
				{
					System.out.println("Enter the valid ifsc code");
					 ifsccode=scanner.nextLine();
				}
				if(ifsccode.equals(rs.getString(1)))
				{
					break;
				}
			}
			else
			{
				System.out.println("Enter the valid customer id");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}	
		return accountNumber;
	}
	public void checkApproval()
	{
			StaffLogin sl=new StaffLogin();
			Accounts aDetails=sl.accountDetails();
			try{
				ps=DbConnection.getInstance().getConnection().prepareStatement("update customer set status='approve' where customer_id=?");			
				ps.setInt(1,aDetails.getCustomerId());
			}
			catch(SQLException e)
			{
				System.out.println("customer id is not approve");
			}
			try{
				ps=DbConnection.getInstance().getConnection().prepareStatement("update account set approvestatus='active1' where customer_id=?");
				ps.setInt(1,aDetails.getCustomerId());
			}
			catch(SQLException e)
			{
				System.out.println("customer id is not activate");
			}
			
			try{
				ps=DbConnection.getInstance().getConnection().prepareStatement("update account set status='active' where customer_id=?");
				ps.setInt(1,aDetails.getCustomerId());
			}
			catch(SQLException e)
			{
				System.out.println("customer id is not activate");
			}
			System.out.println("customerid approved");
	}
	public int checkcustomer(String name,String dob,int roleid)
	{
		try{
			ps=con.prepareStatement("select customer_id from customer  where role_id=? and name=? and dob=?");
			ps.setInt(1,roleid);
			ps.setString(2,name);
			ps.setDate(3,Date.valueOf(dob));
			rs=ps.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	public boolean checkApproved(int customerid,int roleid,long mobile)
	{
		try{
		ps=con.prepareStatement("select * from users where customer_id=? where status!<'approved2' and status<>'Appointed' role_id=?");
		ps.setInt(1,customerid);
		ps.setInt(2,roleid);
		rs=ps.executeQuery();
		if(rs.next())
		{
			return true;
		}
		}
		catch(SQLException e)
		{
			System.out.println("not check the approved");
		}
			
		return false;
	}
}

