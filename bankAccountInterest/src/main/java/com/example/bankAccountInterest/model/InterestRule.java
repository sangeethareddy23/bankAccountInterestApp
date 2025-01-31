package com.example.bankAccountInterest.model;

public class InterestRule {
    private String date;
    private String ruleId;
    private double rate;

    public InterestRule(String date, String ruleId, double rate) {
        this.date = date;
        this.ruleId = ruleId;
        this.rate = rate;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
