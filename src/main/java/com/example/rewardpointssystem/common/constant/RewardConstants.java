package com.example.rewardpointssystem.common.constant;

import java.math.BigDecimal;

public class RewardConstants {

    private RewardConstants() {

    }

    // reward points
    public static final int POINTS_PER_DOLLAR_FIRST_TIER = 1;
    public static final int POINTS_PER_DOLLAR_SECOND_TIER = 2;

    // reward points calculation thresholds, amount over 100 gets 2 points, amount between 50 and 100 gets 1 point
    public static final BigDecimal THRESHOLD_FIRST_TIER = new BigDecimal(50);
    public static final BigDecimal THRESHOLD_SECOND_TIER = new BigDecimal(100);
}
