package com.example.bankAccountInterest.service;

import com.example.bankAccountInterest.model.Account;
import com.example.bankAccountInterest.model.InterestRule;
import com.example.bankAccountInterest.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private Map<String, Account> accountRepository;

    @Mock
    private Map<String, Integer> transactionCounters;

    @Mock
    private Map<String, List<InterestRule>> interestRules;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        account = new Account("AC001", "John Doe", 1000.0);

        when(transactionCounters.getOrDefault(anyString(), eq(0))).thenReturn(0); // Default to 0 for new dates
    }

    @Test
    void testAddTransactionDeposit() {
        // Given
        String date = "20250101";
        String accountId = "AC001";
        String type = "D";
        double amount = 500.0;

        when(accountRepository.get(accountId)).thenReturn(account); // Mock the repository call

        // When
        Account updatedAccount = accountService.addTransaction(date, accountId, type, amount);

        // Then
        assertNotNull(updatedAccount);
        assertEquals(1500.0, updatedAccount.getBalance()); // Check if balance is updated after deposit
        verify(accountRepository, times(1)).get(accountId); // Verify that accountRepository.get() is called once
    }

    @Test
    void testAddTransactionWithdrawal() {
        // Given
        String date = "20250101";
        String accountId = "AC001";
        String type = "D";
        double depositAmount = 1000.0;

        when(accountRepository.get(accountId)).thenReturn(account);

        // Deposit money first
        accountService.addTransaction(date, accountId, type, depositAmount);

        // Now withdraw
        double withdrawalAmount = 200.0;
        String withdrawalType = "W";

        // When
        Account updatedAccount = accountService.addTransaction(date, accountId, withdrawalType, withdrawalAmount);

        // Then
        assertNotNull(updatedAccount);
        assertEquals(1800.0, updatedAccount.getBalance()); // Check if balance is updated after withdrawal
    }

    @Test
    void testInsufficientBalanceWithdrawal() {
        // Given
        String date = "20250101";
        String accountId = "AC001";
        String depositType = "D";
        double depositAmount = 500.0;

        when(accountRepository.get(accountId)).thenReturn(account);

        // Deposit money first
        accountService.addTransaction(date, accountId, depositType, depositAmount);

        // Now try to withdraw more than the balance
        double withdrawalAmount = 2000.0;
        String withdrawalType = "W";

        // Expect an exception to be thrown due to insufficient balance
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.addTransaction(date, accountId, withdrawalType, withdrawalAmount);
        });

        assertEquals("Insufficient balance for withdrawal.", exception.getMessage());
    }


    @Test
    void testGetAccount() {
        // Given
        when(accountRepository.get("AC001")).thenReturn(account);

        // When
        Account fetchedAccount = accountService.getAccount("AC001");

        // Then
        assertNotNull(fetchedAccount);
        assertEquals("AC001", fetchedAccount.getAccountId());
        assertEquals("John Doe", fetchedAccount.getAccountHolderName());
        assertEquals(1000.0, fetchedAccount.getBalance());
    }


    @Test
    void testGetTransactionsForMonth() {
        // Given
        Account account = new Account("AC001", "John Doe", 500.0);
        when(accountRepository.get("AC001")).thenReturn(account);

        // Adding transactions
        accountService.addTransaction("20250101", "AC001", "D", 500.0);
        accountService.addTransaction("20250115", "AC001", "W", 100.0);

        // When
        List<Transaction> januaryTransactions = accountService.getAccount("AC001").getTransactionsForMonth(2025, 1);

        // Then
        assertEquals(2, januaryTransactions.size()); // Should be 2 transactions
        assertEquals("20250101", januaryTransactions.get(0).getDate());
        assertEquals("20250115", januaryTransactions.get(1).getDate());
    }

    @Test
    void testDefineInterestRule_InvalidRate_ThrowsException() {
        // Given
        String date = "20250131";
        String ruleId = "RULE01";
        double invalidRate = 105.0;  // Invalid rate (greater than 100)

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.defineInterestRule(date, ruleId, invalidRate);
        });

        assertEquals("Rate must be greater than 0 and less than 100.", exception.getMessage());
    }

    @Test
    void testDefineInterestRule_InvalidRate_LowerThanZero_ThrowsException() {
        // Given
        String date = "20250131";
        String ruleId = "RULE01";
        double invalidRate = -1.0;  // Invalid rate (less than 0)

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.defineInterestRule(date, ruleId, invalidRate);
        });

        assertEquals("Rate must be greater than 0 and less than 100.", exception.getMessage());
    }

    @Test
    void testDefineInterestRule_AddRuleSuccessfully() {
        // Given
        String date = "20250131";
        String ruleId = "RULE01";
        double rate = 5.0;  // Valid rate

        InterestRule expectedRule = new InterestRule(date, ruleId, rate);

        List<InterestRule> ruleList = new ArrayList<>();

        when(interestRules.get(date)).thenReturn(ruleList);

        when(interestRules.putIfAbsent(eq(date), anyList())).thenReturn(null);  // Simulate putIfAbsent adding the list

        InterestRule returnedRule = accountService.defineInterestRule(date, ruleId, rate);

        assertNotNull(returnedRule);
        assertEquals(expectedRule.getRuleId(), returnedRule.getRuleId());  // Ensure correct rule ID
        assertEquals(expectedRule.getRate(), returnedRule.getRate(), 0.01);  // Ensure correct rate
    }
}