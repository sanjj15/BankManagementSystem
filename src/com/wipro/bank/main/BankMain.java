package com.wipro.bank.main;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.service.BankService;
import com.wipro.bank.util.InsufficientFundException;

public class BankMain {
	public static void main(String[] args) throws InsufficientFundException {
		BankService bankService=new BankService();
		System.out.println(bankService.checkBalance("12345678"));		 
		TransferBean transferBean = new TransferBean();		 
		transferBean.setFromAccountNumber("12345678");
		transferBean.setAmount(880);
		transferBean.setToAccountNumber("12345687");
		transferBean.setDateOfTransaction(new java.util.Date());		 
		System.out.println(bankService.transfer(transferBean));
		System.out.println(bankService.checkBalance("12345678"));		 
		System.out.println(bankService.checkBalance("12345687"));		 

		}

}