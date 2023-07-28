package com.zoho.project.bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.InputMismatchException;  
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
class Database
{
	static Connection con=null;	
	static Statement st=null;
	static
	{
		try
		{
			con=DbConnection.getInstance().getConnection();
			st= con.createStatement();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}	
	}
	void createtable()
	{
		position();
		scheme();
		branch();
		customer();
		account();
		users();
		Transaction();
		createrole();
		addBranch();
		addScheme();
		addAdmin();
	}
	static private void position(){
		//roles
		try{
			st.executeUpdate("create table if not exists position(role_id serial primary key,role_name varchar)");
		}
		catch(SQLException e){
			System.out.println("position table is not created");
		}
	}
	static private void scheme(){
		//scheme
		try{
			st.executeUpdate("create table if not exists scheme(scheme_id serial primary key,scheme_name varchar not null,maxlimitperday real,minimum_amount real,agetype int)");
		 }
		 catch(SQLException e)
		 {
		 	System.out.println("scheme is not created");
		 }
	}
	static private void branch(){
		 //branch
		try{
			st.executeUpdate("create table if not exists Branch(id serial primary key,branchname varchar,branchaddress varchar,Ifsccode varchar)");
		}
		catch(SQLException e)
		{
			System.out.println("branch table is not created");
		}
	}
	static private void customer(){	//details
		try{
			st.executeUpdate("create table if not exists customer(customer_id serial primary key,role_id int, foreign key(role_id) references position(role_id) on delete cascade, name varchar not null, dob date, address varchar , blood_group varchar, email varchar,branch_id int, foreign key (branch_id) references branch(id) on delete cascade,status varchar,mobileno bigint,gender varchar(11))");
		}
		catch(SQLException e){
			System.out.println("customer table is not created");
		}
	}
	static private void account()
	{
		try{//Account detals
		st.executeUpdate("create table if not exists Account(id serial,AccountNo varchar primary key ,customer_id int, foreign key(customer_id) references customer(customer_id) on delete cascade, branchid int,  foreign key(branchid) references Branch(id) on delete cascade,scheme_id int, foreign key (scheme_id) references scheme(scheme_id) on delete cascade,balance decimal(15,2),aadharnumber bigint,status varchar,approveStatus varchar,deactive_date date,activated_date date)");
		}
		catch(SQLException e)
		{
			System.out.println("account table is not created");
		}
	}
	static private void users(){
		//userid
		try{
		st.executeUpdate("create table if not exists users(id serial primary key, customer_id int, foreign key(customer_id) references customer(customer_id) on delete cascade,password varchar)");
		}
		catch(SQLException e)
		{
			System.out.println("users table is not created");
		}
	}
	static private void Transaction(){
		//Transaction
		try{
		st.executeUpdate("create table if not exists Transaction(Transaction_id serial,from_Accountid varchar,foreign key(from_Accountid) references account(AccountNo) on delete cascade,to_accountid  varchar,foreign key(to_accountid) references account(AccountNo) on delete cascade,type varchar,amount decimal(15,2),Transferdate date,transfertype varchar)");//transfertype-created/debited;
		}
		catch(SQLException e)
		{
			System.out.println("Transaction table is not created");
		}
	}
	static private void createrole()
	{
		int count=0;
		try{
			ResultSet rs=st.executeQuery("select count(role_id) from position");
			if(rs.next())
			{
				count=rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Selection problem");
		}
		if(count<5){
		try{	
			st.executeUpdate("insert into position(role_id,role_name ) values(1,'manager')");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("manager not added");
		}
		try{
			st.executeUpdate("insert into position(role_id,role_name ) values(2,'clerk')");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("cashier not added");
		}
		try{
			st.executeUpdate("insert into position(role_id,role_name ) values(3,'cashier')");
		}	
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("accountant not added");
		}
		try{
			st.executeUpdate("insert into position(role_id,role_name ) values(4,'accountholder')");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("accountholder id not created");
		}
		try{
			st.executeUpdate("insert into position(role_id,role_name ) values(5,'admin')");
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("admin id not added");
		}
		}
	}
	static private void addBranch()
	{
	int count=0;
	try{
		PreparedStatement ps=con.prepareStatement("select count(id) from branch");
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			count=rs.getInt(1);
		}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("branch is not added");
		}
		
		if(count==0){
		try{
			PreparedStatement ps=con.prepareStatement("insert into branch(branchname,branchaddress,ifsccode) values ('srivilliputtur','12,vadakku street,srivilliputtur,virudhunagar,tamilnadu','sbi0101')");
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("branch is not added");
		}
	}}
	static private void addAdmin() {
	ResultSet rs=null;int count=0;
	try {
		 rs = st.executeQuery("SELECT COUNT(customer_id)  FROM customer WHERE role_id = 5");
		if (rs.next()) {
			count=rs.getInt(1);
		}
	} catch (SQLException e) {
	System.out.println(e.getMessage());
	}
	
	int id = 0;
	if(count==0){
	try {
		rs=st.executeQuery("INSERT INTO customer (role_id, name, dob, address, blood_group, email, branch_id, status, mobileno, gender) VALUES (5, 'ramesh', '2002-02-01', '10, madathupatti, srivilliputtur', 'b+', 'rameshkumar@mail.com', 1, 'appointed', 9087654321, 'male') RETURNING customer_id");

		if (rs.next()) {
			id = rs.getInt(1);
		}
	} catch (SQLException e) {
		System.out.println("admin not inserted");
		e.printStackTrace();
	}

	try {
		st.executeUpdate("INSERT INTO users (customer_id, password) VALUES ("+id+", 'R0a0m0e0s0h0@0102030')");
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}
	static private void addScheme()
	{
	int count=0;
		try{
			ResultSet rs=st.executeQuery("select count(scheme_id) from scheme");
		
			if(!rs.next())
			{
				count=rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		if(count==0){
		try{
			st.executeUpdate("insert into scheme(scheme_name,maxlimitperday,minimum_amount,agetype)values('saving',4,1000,18)");
		}
		catch(SQLException e)
		{
			System.out.println("Scheme not inserted");
		}}
	}
}
