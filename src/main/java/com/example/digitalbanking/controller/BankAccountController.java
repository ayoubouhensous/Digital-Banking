package com.example.digitalbanking.controller;

import com.example.digitalbanking.dtos.BankAccountDTO;
import com.example.digitalbanking.dtos.CustomerDTO;
import com.example.digitalbanking.mapper.BankAccountMapper;
import com.example.digitalbanking.model.BankAccount;
import com.example.digitalbanking.model.Customer;
import com.example.digitalbanking.repository.BankAccountRepository;
import com.example.digitalbanking.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

public class BankAccountController {
    private BankAccountService bankAccountService;
    private BankAccountMapper bankAccountMapper;

    public BankAccountController(BankAccountService bankAccountService, BankAccountMapper bankAccountMapper) {
        this.bankAccountService = bankAccountService;
        this.bankAccountMapper = bankAccountMapper;
    }

    @PostMapping("/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saved = bankAccountService.saveCustomer(customer);
        return bankAccountMapper.fromCustomer(saved);
    }

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return bankAccountService.listCustomers().stream()
                .map(bankAccountMapper::fromCustomer)
                .collect(Collectors.toList());
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getAccount(@PathVariable Long id) {
        BankAccount account = bankAccountService.getBankAccount(id);
        return bankAccountMapper.fromBankAccount(account);
    }
    @PostMapping("/accounts")
    public BankAccountDTO createAccount(@RequestBody BankAccountDTO dto) {
        BankAccount account = bankAccountMapper.fromBankAccountDTO(dto);

        BankAccount saved = bankAccountService.createBankAccount(account);
        return bankAccountMapper.fromBankAccount(saved);
    }
    @PutMapping("/accounts/{id}")
    public BankAccountDTO updateAccount(@PathVariable Long id, @RequestBody BankAccountDTO dto) {
        BankAccount account = bankAccountMapper.fromBankAccountDTO(dto);
        BankAccount updated = bankAccountService.updateBankAccount(id, account);
        return bankAccountMapper.fromBankAccount(updated);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
    }
}
