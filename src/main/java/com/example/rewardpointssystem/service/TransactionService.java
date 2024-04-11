package com.example.rewardpointssystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rewardpointssystem.model.dto.TransactionDTO;
import com.example.rewardpointssystem.model.pojo.DateRange;
import com.example.rewardpointssystem.model.pojo.Transaction;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionService extends IService<Transaction> {
    /**
     * get transaction by date range, optional customerId
     * @param customerId
     * @param dateRange
     * @return
     */
    List<Transaction> findTransactionByCustomerIdAndDateRange(Optional<Long> customerId, DateRange dateRange);

    /**
     * create transaction
     * @param transactionDTO
     * @return
     */
    Transaction createTransaction(TransactionDTO transactionDTO);

    /**
     * update transaction
     * @param id
     * @param transactionDTO
     * @return
     */
    Transaction updateTransaction(Long id, TransactionDTO transactionDTO);

}
