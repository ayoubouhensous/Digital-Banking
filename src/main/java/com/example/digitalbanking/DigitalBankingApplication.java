package com.example.digitalbanking;

import com.example.digitalbanking.model.*;
import com.example.digitalbanking.repository.BankAccountRepository;
import com.example.digitalbanking.repository.CustomerRepository;
import com.example.digitalbanking.repository.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication  {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }


    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, OperationRepository operationRepository, BankAccountRepository accountRepository) {
        return args -> {

            Stream.of("Hassan","Yassine","AICHA").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);

            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setCustomer(customer);
                currentAccount.setBalance(Math.random() * 100);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                accountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setCustomer(customer);
                savingAccount.setBalance(Math.random() * 100);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(8000);
                accountRepository.save(savingAccount);
            });
            accountRepository.findAll().forEach(account -> {
               for (int i =0; i < 10; i++) {
                   Operation operation = new Operation();
                   operation.setDate(new Date());
                   operation.setAmount(Math.random() * 100);
                   operation.setBankAccount(account);
                   operation.setOperationType(Math.random()>0.5 ? OperationType.CREDIT : OperationType.DEBIT);
                   operationRepository.save(operation);
               }

            });



        };
    }
}
