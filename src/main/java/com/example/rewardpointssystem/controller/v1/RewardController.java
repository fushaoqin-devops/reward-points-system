package com.example.rewardpointssystem.controller.v1;

import com.example.rewardpointssystem.common.result.Result;
import com.example.rewardpointssystem.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reward")
public class RewardController {

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/summary/{customerId}")
    public Result<?> getRewardPoints(@PathVariable Long customerId,
                                     @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                     @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.okResult(rewardService.getRewardPointsSummaryForCustomer(customerId, startDate, endDate));
    }

    @GetMapping("/summary")
    public Result<?> getRewardPointsSummary(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.okResult(rewardService.getRewardPointsSummary(startDate, endDate));
    }

}
