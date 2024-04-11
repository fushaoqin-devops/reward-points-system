package com.example.rewardpointssystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rewardpointssystem.model.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}
