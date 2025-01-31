package com.example.bankAccountInterest.controller;

import com.example.bankAccountInterest.model.InterestRule;
import com.example.bankAccountInterest.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterestRuleControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private InterestRuleController interestRuleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDefineInterestRule() {
        InterestRule rule = new InterestRule("20230615", "RULE01", 2.20);
        when(accountService.defineInterestRule("20230615", "RULE01", 2.20)).thenReturn(rule);

        InterestRule result = interestRuleController.defineInterestRule("20230615", "RULE01", 2.20);

        assertNotNull(result);
        assertEquals("RULE01", result.getRuleId());
        assertEquals(2.20, result.getRate());
        verify(accountService).defineInterestRule("20230615", "RULE01", 2.20);
    }

    @Test
    void testListInterestRules() {
        Map<String, List<InterestRule>> rulesMap = Map.of(
                "20230615", List.of(new InterestRule("20230615", "RULE01", 2.20))
        );
        when(accountService.getInterestRules()).thenReturn(rulesMap);

        Map<String, List<InterestRule>> result = interestRuleController.listInterestRules();

        assertNotNull(result);
        assertTrue(result.containsKey("20230615"));
        assertEquals(1, result.get("20230615").size());
        assertEquals("RULE01", result.get("20230615").get(0).getRuleId());
        verify(accountService).getInterestRules();
    }
}
