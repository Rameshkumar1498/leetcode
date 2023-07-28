package com.zoho.project.bank;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
	class ApproveStaff extends StaffLogin implements ManagerInterface {
	
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
		public void approveStaff() 
		{
		Checknumber cn=new Checknumber();
		int arr[]=cn.check();
		        try{
				ps=con.prepareStatement("update customer set status='appointed' where customer_id=? and role_id=?");
				ps.setInt(1,arr[0]);
				ps.setInt(2,arr[1]);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				System.out.println("Staff is not appointed");
			}
		}
	}
	public class Checknumber{
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
		{}
	}
	
		int customerid=0,roleid=0;
			public int[] check()
			{
				System.out.println("Enter the customerid");
				customerid=Validation.inttype();
				System.out.println("roleid	role name");
		try{
			rs=st.executeQuery("select * from position");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"	"+rs.getString(2));
			}
		}
		catch(SQLException e)
		{
			System.out.println("roles are not display");
		}
				
				System.out.println("Enter the roleid");
				roleid=Validation.inttype();
				try{
					ps=con.prepareStatement("select * from customer where customer_id=? and role_id=?");
					ps.setInt(1,customerid);
					ps.setInt(2,roleid);
					rs=ps.executeQuery();
					if(!rs.next())
					{
						System.out.println("This id is not available");
						return new int[0];
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					System.out.println("Problem in customer table");
				}
				int arr[]=new int[2];
				arr[0]=customerid;
				arr[1]=roleid;
				return arr;		
			}
			
		}
	class RemoveStaff 
	{
		int ar[]=new Checknumber().check();
		int roleid=ar[1];
		int customerid=ar[0];
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
				e.printStackTrace();
			}
		}
		public void removeStaff()
		{
			try{
				ps=con.prepareStatement("update customers set status='Dismiss' where where customer_id=? and role_id=?");
				ps.setInt(1,customerid);
				ps.setInt(2,roleid);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				System.out.println("id is not removed by manager");
			}
		}	
	}
class ApproveAccount extends Checknumber 
{
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
	protected void approveAccount()
	{
		super.check();
		try{
			ps=con.prepareStatement("update customers set approvestatus='approve-2' where where customer_id=? and role_id=4");
			ps.setInt(1,customerid);
			ps.setInt(2,roleid);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Account is not verified by manager");
		}
		try{
			ps=con.prepareStatement("update account set status='active' where where customer_id=?");
			ps.setInt(1,customerid);
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Account is not active by manager");
		}
	}
}
	class Activate extends Checknumber{
		static 
	{
		try
		{
			con=DbConnection.getInstance().getConnection();
			st=con.createStatement();
		}
		catch(SQLException e)	
		{}
	}
	
	void activateAccount()
	{
	PreparedStatement ps=null;
	ResultSet rs=null;
	
			try{
				ps=con.prepareStatement("update account set status='active' where where customer_id=?");
				ps.setInt(1,customerid);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Account is not active by manager");
			}				
	}
	void login()
	{
		
		try{
		ps=con.prepareStatement("select * from customer where customer_id=?");
		ps.setInt(1,customerid);
		rs=ps.executeQuery();
		if(!rs.next())
		{
			System.out.println("Enter the valid id");
			return;	
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("customer id problem");
		}
		try{
		ps=con.prepareStatement("select * from customer where customer_id=? and roleid=1");
		ps.setInt(1,customerid);
		rs=ps.executeQuery();
		if(!rs.next())
		{
			System.out.println("you are not a manager");	
			return;
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("customer details problem");
		}try{
		ps=con.prepareStatement("select * from customer and roleid=1 and status='appoint'");
		ps.setInt(1,customerid);
		rs=ps.executeQuery();
		if(!rs.next())
		{
			System.out.println("you are not a approved");
			return;	
		}}
	catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("customer id approve problem");
		}
		
	}
} 
