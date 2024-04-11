package com.example.rewardpointssystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rewardpointssystem.common.exception.CustomException;
import com.example.rewardpointssystem.common.result.AppHttpCodeEnum;
import com.example.rewardpointssystem.mapper.TransactionMapper;
import com.example.rewardpointssystem.model.dto.TransactionDTO;
import com.example.rewardpointssystem.model.pojo.Customer;
import com.example.rewardpointssystem.model.pojo.DateRange;
import com.example.rewardpointssystem.model.pojo.Transaction;
import com.example.rewardpointssystem.service.CustomerService;
import com.example.rewardpointssystem.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    private final CustomerService customerService;

    @Autowired
    public TransactionServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public List<Transaction> findTransactionByCustomerIdAndDateRange(Optional<Long> customerId, DateRange dateRange) {
        if (dateRange == null || !dateRange.isValidate()) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Invalid date range");
        }

        LambdaQueryWrapper<Transaction> queryWrapper = new LambdaQueryWrapper<>();

        // check if customerId is present
        if (customerId.isPresent()) {
            queryWrapper.eq(Transaction::getCustomerId, customerId);
        }

        queryWrapper
                .ge(Transaction::getCreateTime, dateRange.getStartDate())
                .le(Transaction::getCreateTime, dateRange.getEndDate());
        return list(queryWrapper);
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        // check if transactionDTO is valid
        if (transactionDTO == null || transactionDTO.getCustomerId() == null) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Invalid transaction");
        }

        // check if customer exists
        Customer existingCustomer = customerService.getById(transactionDTO.getCustomerId());
        if (existingCustomer == null) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Customer not found");
        }

        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionDTO, transaction);
        transaction.setCreateTime(LocalDate.now());
        transaction.setUpdateTime(LocalDate.now());

        save(transaction);

        return transaction;
    }

    @Override
    public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction existingTrx = getById(id);

        if (existingTrx == null) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Transaction not found");
        }

        // check if transactionDTO is valid, assuming we don't want to update customerId
        if (transactionDTO == null || transactionDTO.getCustomerId() != null) {
            throw new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Invalid transaction");
        }

        BeanUtils.copyProperties(transactionDTO, existingTrx);
        existingTrx.setUpdateTime(LocalDate.now());

        updateById(existingTrx);

        return existingTrx;
    }

}
