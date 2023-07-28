package com.zoho.project.bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
final public class DbConnection
{
	private Connection con=null;
	private static DbConnection db=null;
	private DbConnection()  
	{
		try{
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/databasename","postgres","password");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	
	}	
	public static DbConnection getInstance() throws SQLException
	{	
		if(db==null)	db=new DbConnection();
		return db;
	}
	public Connection getConnection()
	{
		return con;
	}
	public Connection closedb()
	{
		if(con!=null)
		{				
			con=null;
		}
		return con;
	}
}
