package com.example.rewardpointssystem.service;

import com.example.rewardpointssystem.model.dto.RewardDTO;

import java.time.LocalDate;
import java.util.Map;

public interface RewardService {

    /**
     * Get reward points for a customer within a given date range
     * @param customerId
     * @return
     */
    RewardDTO getRewardPointsSummaryForCustomer(Long customerId, LocalDate startDate, LocalDate endDate);

    /**
     * Get reward points summary for all customers within a given date range
     * @param startDate
     * @param endDate
     * @return
     */
    Map<Long, RewardDTO> getRewardPointsSummary(LocalDate startDate, LocalDate endDate);

}
