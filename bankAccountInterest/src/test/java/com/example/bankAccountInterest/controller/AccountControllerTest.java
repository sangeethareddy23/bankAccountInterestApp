package com.example.bankAccountInterest.controller;

import com.example.bankAccountInterest.model.Account;
import com.example.bankAccountInterest.model.InterestRule;
import com.example.bankAccountInterest.model.Transaction;
import com.example.bankAccountInterest.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTransaction() {
        Account account = new Account("AC001", "John Doe", 500.0);
        when(accountService.addTransaction("20250101", "AC001", "D", 500.0)).thenReturn(account);

        Account result = accountController.addTransaction("20250101", "AC001", "D", 500.0);

        assertNotNull(result);
        assertEquals("AC001", result.getAccountId());
        assertEquals(500.0, result.getBalance());
        verify(accountService).addTransaction("20250101", "AC001", "D", 500.0);
    }

    @Test
    void testGetAccount() {
        Account account = new Account("AC001", "John Doe", 500.0);
        when(accountService.getAccount("AC001")).thenReturn(account);

        Account result = accountController.getAccount("AC001");

        assertNotNull(result);
        assertEquals("AC001", result.getAccountId());
        assertEquals(500.0, result.getBalance());
        verify(accountService).getAccount("AC001");
    }

    @Test
    void testGetAccountStatement() {
        List<Transaction> transactions = List.of(new Transaction("20250101-01", "20250101", "D", 500.0));
        Map<String, Object> responseMap = Map.of(
                "accountId", "AC001",
                "transactions", transactions,
                "interest", "10.00",
                "finalBalance", "510.00"
        );

        when(accountService.getAccountStatement("AC001", "202501")).thenReturn(responseMap);

        ResponseEntity<Map<String, Object>> response = accountController.getAccountStatement("AC001", "202501");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("AC001", response.getBody().get("accountId"));
        assertEquals(transactions, response.getBody().get("transactions"));
        verify(accountService).getAccountStatement("AC001", "202501"); // Verify service method call
    }


    @Test
    void testGetAccountStatementInvalidFormat() {
        ResponseEntity<Map<String, Object>> response = accountController.getAccountStatement("AC001", "2025");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
    }
}
