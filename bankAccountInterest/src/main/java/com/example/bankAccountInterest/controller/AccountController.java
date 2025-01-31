package com.example.bankAccountInterest.controller;

import com.example.bankAccountInterest.model.Account;
import com.example.bankAccountInterest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/transaction")
    public Account addTransaction(
            @RequestParam String date,
            @RequestParam String accountId,
            @RequestParam String type,
            @RequestParam double amount) {
        return accountService.addTransaction(date, accountId, type, amount);
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable String accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/{accountId}/statement")
    public ResponseEntity<Map<String, Object>> getAccountStatement(
            @PathVariable String accountId,
            @RequestParam String yearMonth) {
        try {
            // Validate yearMonth format
            if (yearMonth.length() != 6 || !yearMonth.matches("\\d{6}")) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid yearMonth format. Expected format: YYYYMM"));
            }

            // Delegate to service
            Map<String, Object> statement = accountService.getAccountStatement(accountId, yearMonth);

            return ResponseEntity.ok(statement);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", ex.getMessage()));
        }
    }


}