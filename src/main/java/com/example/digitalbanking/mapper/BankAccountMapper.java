package com.example.digitalbanking.mapper;

import com.example.digitalbanking.dtos.BankAccountDTO;
import com.example.digitalbanking.dtos.CustomerDTO;
import com.example.digitalbanking.model.BankAccount;
import com.example.digitalbanking.model.CurrentAccount;
import com.example.digitalbanking.model.Customer;
import com.example.digitalbanking.model.SavingAccount;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public CustomerDTO fromCustomer(Customer customer) {
        if (customer == null) return null;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }
    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        if (bankAccount == null) return null;
        BankAccountDTO dto = new BankAccountDTO();
        dto.setId(bankAccount.getId());
        if (bankAccount instanceof SavingAccount) {
            dto.setAccountType("SAVING");
            dto.setInterestRate(((SavingAccount) bankAccount).getInterestRate());
        } else if (bankAccount instanceof CurrentAccount) {
            dto.setAccountType("CURRENT");
            dto.setOverDraft(((CurrentAccount) bankAccount).getOverDraft());
        } else {
            dto.setAccountType("UNKNOWN");
        }
        dto.setBalance(bankAccount.getBalance());
        dto.setCreatedAt(bankAccount.getCreatedAt());
        dto.setCustomer(fromCustomer(bankAccount.getCustomer()));
        return dto;
    }

    public BankAccount fromBankAccountDTO(BankAccountDTO dto) {
        if (dto == null) return null;
        BankAccount account;
        if ("SAVING".equalsIgnoreCase(dto.getAccountType())) {
            SavingAccount sa = new SavingAccount();
            sa.setInterestRate(dto.getInterestRate());
            account = sa;
        } else {
            CurrentAccount ca = new CurrentAccount();
            ca.setOverDraft(dto.getOverDraft());
            account = ca;
        }
        account.setId(dto.getId());
        account.setBalance(dto.getBalance());
        account.setCreatedAt(dto.getCreatedAt());
        account.setCustomer(fromCustomerDTO(dto.getCustomer()));
        return account;
    }

}
