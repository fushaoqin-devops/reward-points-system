package com.example.rewardpointssystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rewardpointssystem.model.pojo.Transaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

}
