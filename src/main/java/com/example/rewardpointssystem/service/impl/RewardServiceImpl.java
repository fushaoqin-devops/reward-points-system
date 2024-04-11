package com.example.rewardpointssystem.service.impl;

import com.example.rewardpointssystem.common.constant.DateConstants;
import com.example.rewardpointssystem.common.constant.RewardConstants;
import com.example.rewardpointssystem.common.exception.CustomException;
import com.example.rewardpointssystem.common.result.AppHttpCodeEnum;
import com.example.rewardpointssystem.model.dto.RewardDTO;
import com.example.rewardpointssystem.model.pojo.Customer;
import com.example.rewardpointssystem.model.pojo.DateRange;
import com.example.rewardpointssystem.model.pojo.Transaction;
import com.example.rewardpointssystem.service.CustomerService;
import com.example.rewardpointssystem.service.RewardService;
import com.example.rewardpointssystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardServiceImpl implements RewardService {

    private final CustomerService customerService;

    private final TransactionService transactionService;

    @Autowired
    public RewardServiceImpl(CustomerService customerService, TransactionService transactionService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @Override
    public RewardDTO getRewardPointsSummaryForCustomer(Long customerId, LocalDate startDate, LocalDate endDate) {
        // check if customer exists
        Customer existingCustomer = customerService.getById(customerId);
        if (existingCustomer == null) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Customer not found");
        }

        // set default start and end date
        DateRange dateRange = getDateRange(startDate, endDate);

        // get transactions between start and end date
        List<Transaction> transactions = transactionService.findTransactionByCustomerIdAndDateRange(Optional.of(customerId), dateRange);

        RewardDTO rewardDTO = new RewardDTO();
        rewardDTO.setCustomer(existingCustomer);
        // calculate reward points
        rewardDTO.setTotalPoints(calculateTotalRewardPoints(transactions));
        rewardDTO.setMonthlyPoints(getMonthlyPoints(transactions));
        return rewardDTO;
    }

    @Override
    public Map<Long, RewardDTO> getRewardPointsSummary(LocalDate startDate, LocalDate endDate) {
        // set default start and end date
        DateRange dateRange = getDateRange(startDate, endDate);

        // get all transactions between start and end date
        List<Transaction> transactions = transactionService.findTransactionByCustomerIdAndDateRange(Optional.empty(), dateRange);

        // calculate reward points for each customer
        Map<Long, List<Transaction>> transactionsByCustomer = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));
        Map<Long, RewardDTO> rewardsSummary = transactionsByCustomer.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Transaction> customerTransactions = entry.getValue();
                            RewardDTO summary = new RewardDTO();
                            summary.setCustomer(customerService.getById(entry.getKey()));
                            summary.setTotalPoints(calculateTotalRewardPoints(customerTransactions));
                            summary.setMonthlyPoints(getMonthlyPoints(customerTransactions));

                            return summary;
                        }
                ));
        return rewardsSummary;
    }

    /**
     * Calculate total reward points from a list of transactions
     * @param transactions
     * @return
     */
    private int calculateTotalRewardPoints(List<Transaction> transactions) {
        return transactions.stream().mapToInt(this::calculateRewardPoints).sum();
    }

    /**
     * get monthly reward points
     * @param transactions
     * @return
     */
    private Map<String, Integer> getMonthlyPoints(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCreateTime().getMonth().name(),
                        Collectors.summingInt(this::calculateRewardPoints)));
    }

    /**
     * Get DateRange based on start and end date, if not provided, default values are used
     * @param startDate
     * @param endDate
     * @return
     */
    private DateRange getDateRange(LocalDate startDate, LocalDate endDate) {
        // set default start and end date
        if (startDate == null && endDate == null) {
            startDate = DateConstants.DEFAULT_START_DATE;
            endDate = DateConstants.DEFAULT_END_DATE;
        } else if (startDate == null) {
            startDate = endDate.minusMonths(DateConstants.DEFAULT_MONTHS);
        } else if (endDate == null) {
            LocalDate provisionalEndDate = startDate.plusMonths(DateConstants.DEFAULT_MONTHS);
            endDate = LocalDate.now().isBefore(provisionalEndDate) ? DateConstants.DEFAULT_END_DATE : provisionalEndDate;
        }
        return new DateRange(startDate, endDate);
    }

    /**
     * Calculate reward points for a transaction
     * @param transaction
     * @return
     */
    private int calculateRewardPoints(Transaction transaction) {
        int points = 0;
        BigDecimal amount = transaction.getAmount();

        if (amount.compareTo(RewardConstants.THRESHOLD_FIRST_TIER) > 0) {
            if (amount.compareTo(RewardConstants.THRESHOLD_SECOND_TIER) > 0) {
                // amount over 100 gets 2 points
                points += amount
                        .subtract(RewardConstants.THRESHOLD_SECOND_TIER)
                        .multiply(BigDecimal.valueOf(RewardConstants.POINTS_PER_DOLLAR_SECOND_TIER))
                        .intValue();
                // amount between 50 and 100 gets 1 point
                points += RewardConstants.THRESHOLD_FIRST_TIER.intValue();
            } else {
                // amount over 50 gets 1 point
                points += amount.
                        subtract(RewardConstants.THRESHOLD_FIRST_TIER)
                        .multiply(BigDecimal.valueOf(RewardConstants.POINTS_PER_DOLLAR_FIRST_TIER))
                        .intValue();
            }
        }
        return points;
    }

}
