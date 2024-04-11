package com.example.rewardpointssystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rewardpointssystem.mapper.CustomerMapper;
import com.example.rewardpointssystem.model.pojo.Customer;
import com.example.rewardpointssystem.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
