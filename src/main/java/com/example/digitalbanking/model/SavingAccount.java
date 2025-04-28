package com.example.digitalbanking.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor

@DiscriminatorValue("SAVING")
public class SavingAccount extends BankAccount {
    private double interestRate;

}
