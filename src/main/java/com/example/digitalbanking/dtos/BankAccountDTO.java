package com.example.digitalbanking.dtos;

import com.example.digitalbanking.model.Operation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BankAccountDTO {
    private Long id;
    private double balance;
    private Date createdAt;
    private CustomerDTO customer;
    private String accountType;    // "SAVING" ou "CURRENT"
    private Double interestRate;   // nullable
    private Double overDraft;      // nullable

    private List<OperationDTO> operations; // 🔥 Ajouter cette ligne


}
