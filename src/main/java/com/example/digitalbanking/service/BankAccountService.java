package com.example.digitalbanking.service;


import com.example.digitalbanking.model.BankAccount;
import com.example.digitalbanking.model.Customer;
import com.example.digitalbanking.model.Operation;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    List<Customer> listCustomers();
    BankAccount getBankAccount(Long accountId);
    BankAccount createBankAccount(BankAccount bankAccount);
    BankAccount updateBankAccount(Long accountId, BankAccount bankAccount);
    void deleteBankAccount(Long accountId);
    List<BankAccount> getPaginatedAccounts(int page, int size);
    List<Operation> getAccountHistory(Long accountId);
    List<Customer> getCustomersByname(String name);


}
