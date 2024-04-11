package com.example.rewardpointssystem.controller.v1;

import com.example.rewardpointssystem.common.result.Result;
import com.example.rewardpointssystem.model.dto.TransactionDTO;
import com.example.rewardpointssystem.model.pojo.Transaction;
import com.example.rewardpointssystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trx")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Result<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.createTransaction(transactionDTO);
        return Result.okResult(transaction);
    }

    @PutMapping("/{id}")
    public Result<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.updateTransaction(id, transactionDTO);
        return Result.okResult(transaction);
    }

}
