package com.example.digitalbanking.dtos;

import com.example.digitalbanking.model.OperationType;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date date;
    private double amount;
    private OperationType operationType;
}