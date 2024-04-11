package com.example.rewardpointssystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.rewardpointssystem.mapper")
public class RewardPointsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardPointsSystemApplication.class, args);
    }

}
