package com.example.rewardpointssystem;

import com.example.rewardpointssystem.common.exception.CustomException;
import com.example.rewardpointssystem.common.result.AppHttpCodeEnum;
import com.example.rewardpointssystem.service.RewardService;
import com.example.rewardpointssystem.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @MockBean
    private TransactionService transactionService;

    /**
     * Test case for valid date range
     * @throws Exception
     */
    @Test
    public void getRewardPointsForCustomer_Success() throws Exception {
        when(rewardService.getRewardPointsSummary(any(), any())).thenReturn(new HashMap<>());
        mockMvc.perform(get("/reward/summary")
                        .param("start", "2023-01-01")
                        .param("end", "2023-03-31"))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(content().contentType("application/json")) // Verify response content type
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * Test case for invalid date range
     * @throws Exception
     */
    @Test
    public void getRewardPointsForCustomer_NotFound() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 3, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        when(rewardService.getRewardPointsSummary(any(), any()))
                .thenThrow(new CustomException(AppHttpCodeEnum.INVALID_PARAMS, "Invalid date range"));

        mockMvc.perform(get("/reward/summary")
                        .param("start", "2023-03-31")
                        .param("end", "2023-01-01"))
                // for purpose of simple project, we are returning 200 for all exceptions, actual status code in encapsulated in response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400)) // Verify error code in the body
                .andExpect(jsonPath("$.errorMessage").value("Invalid parameters")); // Verify error message
    }

}
