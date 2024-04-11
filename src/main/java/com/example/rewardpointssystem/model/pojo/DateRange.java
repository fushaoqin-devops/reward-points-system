package com.example.rewardpointssystem.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isValidate() {
        return startDate != null && endDate != null && startDate.isBefore(endDate);
    }

}
