package com.example.digitalbanking.service;


import com.example.digitalbanking.model.BankAccount;
import com.example.digitalbanking.model.Customer;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    List<Customer> listCustomers();
    BankAccount getBankAccount(Long accountId);
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount updateBankAccount(Long accountId, BankAccount bankAccount);
    void deleteBankAccount(Long accountId);

}
