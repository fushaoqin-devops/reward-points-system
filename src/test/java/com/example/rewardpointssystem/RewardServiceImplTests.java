package com.example.rewardpointssystem;

import com.example.rewardpointssystem.common.constant.DateConstants;
import com.example.rewardpointssystem.common.exception.CustomException;
import com.example.rewardpointssystem.model.dto.RewardDTO;
import com.example.rewardpointssystem.model.pojo.Customer;
import com.example.rewardpointssystem.model.pojo.DateRange;
import com.example.rewardpointssystem.model.pojo.Transaction;
import com.example.rewardpointssystem.service.CustomerService;
import com.example.rewardpointssystem.service.RewardService;
import com.example.rewardpointssystem.service.TransactionService;
import com.example.rewardpointssystem.service.impl.RewardServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class RewardServiceImplTests {

    @Mock
    private CustomerService customerService;;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private RewardServiceImpl rewardService;

    /**
     * Test the calculation of reward points for a customer
     */
    @Test
    void testCalculateRewardPointsForCustomer() {
        // Given
        Long customerId = 1L;
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();
        DateRange dateRange = new DateRange(startDate, endDate);
        List<Transaction> mockTransactions = List.of(
                new Transaction(1L, customerId, BigDecimal.valueOf(120), LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(2)),
                new Transaction(2L, customerId, BigDecimal.valueOf(60), LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(2))
        );
        int expectedRewardPoints = 100; // 20 * 20 + 50 = 90 for first transaction, 10 * 1 = 10 for second transaction

        when(customerService.getById(customerId)).thenReturn(new Customer(customerId, "Test Customer", "test@123.com"));
        when(transactionService.findTransactionByCustomerIdAndDateRange(Optional.of(customerId), dateRange))
                .thenReturn(mockTransactions);

        RewardDTO result = rewardService.getRewardPointsSummaryForCustomer(customerId, startDate, endDate);

        assertNotNull(result);
        assertEquals(expectedRewardPoints, result.getTotalPoints());

        verify(customerService).getById(customerId);
        verify(transactionService).findTransactionByCustomerIdAndDateRange(Optional.of(customerId), dateRange);
    }

    /**
     * Test the calculation of reward points for all customers
     */
    @Test
    void testCalculateRewardPointsForAllCustomer() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);
        DateRange dateRange = new DateRange(startDate, endDate);
        Long customerId1 = 1L;
        Long customerId2 = 2L;
        Customer mockCustomer1 = new Customer(customerId1, "Test Customer 1", "");
        Customer mockCustomer2 = new Customer(customerId2, "Test Customer 2", "");

        List<Transaction> mockTransactions = List.of(
                new Transaction(1L, customerId1, BigDecimal.valueOf(120), startDate.plusDays(10), startDate.plusDays(10)),
                new Transaction(2L, customerId1, BigDecimal.valueOf(60), startDate.plusDays(20), startDate.plusDays(20)),
                new Transaction(3L, customerId2, BigDecimal.valueOf(150), startDate.plusDays(15), startDate.plusDays(15)),
                new Transaction(4L, customerId2, BigDecimal.valueOf(90), startDate.plusDays(25), startDate.plusDays(25))
        );

        when(transactionService.findTransactionByCustomerIdAndDateRange(Optional.empty(), dateRange))
                .thenReturn(mockTransactions);
        when(customerService.getById(customerId1)).thenReturn(mockCustomer1);
        when(customerService.getById(customerId2)).thenReturn(mockCustomer2);

        Map<Long, RewardDTO> result = rewardService.getRewardPointsSummary(startDate, endDate);

        // verify both customers are present in the result
        assertNotNull(result);
        assertEquals(2, result.size(), "Should contain summary for two customers");

        // Verify that the map contains entries for both customers with correct reward details
        assertTrue(result.containsKey(customerId1) && result.containsKey(customerId2));

        RewardDTO summary1 = result.get(customerId1);
        RewardDTO summary2 = result.get(customerId2);

        assertNotNull(summary1);
        assertNotNull(summary2);

        // Assuming calculateTotalRewardPoints and getMonthlyPoints are correctly implemented, you would check:
        // For example, verify the total points and monthly points breakdown for each customer
        assertEquals(100, summary1.getTotalPoints());
        assertEquals(190, summary2.getTotalPoints());

        // Further detailed assertions about the monthly points breakdown can be added here

        // Verifying that the customer service was called for both customer IDs
        verify(customerService).getById(customerId1);
        verify(customerService).getById(customerId2);
    }

}
