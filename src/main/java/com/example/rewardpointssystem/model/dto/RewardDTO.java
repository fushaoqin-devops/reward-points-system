package com.example.rewardpointssystem.model.dto;

import com.example.rewardpointssystem.model.pojo.Customer;
import lombok.Data;

import java.time.YearMonth;
import java.util.Map;

@Data
public class RewardDTO {

    private Customer customer;

    private Integer totalPoints;

    private Map<String, Integer> monthlyPoints;

}
