package com.example.digitalbanking.service;

import com.example.digitalbanking.model.BankAccount;
import com.example.digitalbanking.model.Customer;
import com.example.digitalbanking.model.Operation;
import com.example.digitalbanking.repository.BankAccountRepository;
import com.example.digitalbanking.repository.CustomerRepository;
import com.example.digitalbanking.repository.OperationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final OperationRepository operationRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, OperationRepository operationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return  customerRepository.save(customer);
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }


    @Override
    public BankAccount getBankAccount(Long accountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        }
        throw  new RuntimeException("Bank account not found with ID: " + accountId);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        Long customerId = bankAccount.getCustomer().getId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer introuvable, id=" + customerId));
        bankAccount.setCustomer(customer);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(Long accountId, BankAccount bankAccount) {
        if (!bankAccount.getId().equals(accountId)) {
            throw  new RuntimeException("Bank account not found with ID: " + accountId);
        }
        bankAccount.setId(accountId);
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccount(Long accountId) {
        if (!bankAccountRepository.existsById(accountId)) {
            throw new RuntimeException("Bank account not found with ID: " + accountId);
        }
        bankAccountRepository.deleteById(accountId);
    }
    @Override
    public List<BankAccount> getPaginatedAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bankAccountRepository.findAll(pageable).getContent();
    }
    @Override
    public List<Operation> getAccountHistory(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return operationRepository.findByBankAccountId(accountId);
    }

    @Override
    public List<Customer> getCustomersByname(String name) {
        return customerRepository.findByNameContains(name);
    }
}
