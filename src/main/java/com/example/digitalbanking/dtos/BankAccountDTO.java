package com.example.digitalbanking.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDTO {
    private Long id;
    private double balance;
    private Date createdAt;
    private CustomerDTO customer;
    private String accountType;    // "SAVING" ou "CURRENT"
    private Double interestRate;   // nullable
    private Double overDraft;      // nullable

}
