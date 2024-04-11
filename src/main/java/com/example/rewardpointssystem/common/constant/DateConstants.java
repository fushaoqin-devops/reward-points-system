package com.example.rewardpointssystem.common.constant;

import java.time.LocalDate;

public final class DateConstants {

    private DateConstants() {

    }

    // default start and end date is last 3 months
    public static final int DEFAULT_MONTHS = 3;
    public static final LocalDate DEFAULT_START_DATE = LocalDate.now().minusMonths(DEFAULT_MONTHS);
    public static final LocalDate DEFAULT_END_DATE = LocalDate.now();
}
