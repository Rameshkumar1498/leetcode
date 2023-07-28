package com.zoho.project.bank;
 interface CustomerInteface
{
	void checkBalance(int customerId,String Ifsccode,String accountNo);
	void transferMoney(int customerId);
	void showAccount();	
}
interface AccountApprove
{
	void approveCustomerAccount(int branchid);
}
interface ClerkInterface extends CustomerInteface,AccountApprove
{
	void checkCustomerBalance();
}
interface CashierInterface extends CustomerInteface,AccountApprove
{
	void depositAmount(int branchid);
	void withdrawAmount(int branchid);
}
interface ManagerInterface extends ClerkInterface,CashierInterface
{
	void approveStaff();
}
interface AdminInterface extends ManagerInterface
{
	void approveManager(int roleid,String state);
	void removeManager(int  roleid,String state);
}
