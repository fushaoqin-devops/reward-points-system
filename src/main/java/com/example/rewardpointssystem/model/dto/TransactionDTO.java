package com.example.rewardpointssystem.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {

    private Long customerId;
    private BigDecimal amount;

}
