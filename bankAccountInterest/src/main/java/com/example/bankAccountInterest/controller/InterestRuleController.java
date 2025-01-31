package com.example.bankAccountInterest.controller;

import com.example.bankAccountInterest.model.InterestRule;
import com.example.bankAccountInterest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interest-rules")
public class InterestRuleController {

    @Autowired
    private AccountService accountService;

    // Define or update an interest rule
    @PostMapping("/define")
    public InterestRule defineInterestRule(
            @RequestParam String date,
            @RequestParam String ruleId,
            @RequestParam double rate) {
        return accountService.defineInterestRule(date, ruleId, rate);
    }

    // List all interest rules
    @GetMapping
    public Map<String, List<InterestRule>> listInterestRules() {
        return accountService.getInterestRules();
    }
}
