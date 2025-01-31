package com.example.bankAccountInterest.service;

import com.example.bankAccountInterest.model.Account;
import com.example.bankAccountInterest.model.InterestRule;
import com.example.bankAccountInterest.model.Transaction;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AccountService {
    private Map<String, Account> accountRepository = new HashMap<>();
    private Map<String, Integer> transactionCounters = new HashMap<>();
    private final Map<String, List<InterestRule>> interestRules = new HashMap<>();

    public Account addTransaction(String date, String accountId, String type, double amount) {
        // Validate the transaction
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (!type.equalsIgnoreCase("D") && !type.equalsIgnoreCase("W")) {
            throw new IllegalArgumentException("Invalid transaction type. Use D for deposit or W for withdrawal.");
        }

        accountRepository.putIfAbsent(accountId, new Account(accountId, "Default Holder", 0.0));
        Account account = accountRepository.get(accountId);

        if (type.equalsIgnoreCase("W") && account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal.");
        }

        int counter = transactionCounters.getOrDefault(date, 0) + 1;
        transactionCounters.put(date, counter);
        String transactionId = String.format("%s-%02d", date, counter);

        Transaction transaction = new Transaction(transactionId, date, type, amount);
        account.addTransaction(transaction);

        return account;
    }

    public Account getAccount(String accountId) {
        return accountRepository.get(accountId);
    }

    public InterestRule defineInterestRule(String date, String ruleId, double rate) {
        if (rate <= 0 || rate >= 100) {
            throw new IllegalArgumentException("Rate must be greater than 0 and less than 100.");
        }

        InterestRule rule = new InterestRule(date, ruleId, rate);

        interestRules.putIfAbsent(date, new ArrayList<>());
        interestRules.get(date).add(rule);

        return rule;
    }

    public Map<String, List<InterestRule>> getInterestRules() {
        return interestRules;
    }

    public double calculateMonthlyInterest(Account account, int year, int month, Map<String, List<InterestRule>> interestRules) {
        double totalInterest = 0.0;
        List<Transaction> monthlyTransactions = account.getTransactionsForMonth(year, month);

        monthlyTransactions.sort(Comparator.comparing(Transaction::getDate));

        double dailyBalance = account.getBalance();

        Map<String, Double> periodInterest = new HashMap<>();
        Map<String, Integer> periodDays = new HashMap<>();


        for (Transaction txn : monthlyTransactions) {
            if (txn.getType().equalsIgnoreCase("D")) {
                dailyBalance += txn.getAmount();
            } else if (txn.getType().equalsIgnoreCase("W")) {
                dailyBalance -= txn.getAmount();
            }

            List<InterestRule> applicableRules = interestRules.get(txn.getDate());

            for (InterestRule rule : applicableRules) {
                double rate = rule.getRate();
                String startPeriod = txn.getDate();
                String endPeriod = getNextPeriodStart(txn.getDate());
                int numDays = calculateDaysInPeriod(startPeriod, endPeriod);

                double interest = dailyBalance * (rate / 100) * numDays / 365;
                periodInterest.put(txn.getDate(), interest);
                totalInterest += interest;
            }
        }

        return totalInterest;
    }

    private String getNextPeriodStart(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate currentDate = LocalDate.parse(date, formatter);

         LocalDate nextPeriodStart = currentDate.plusMonths(1).withDayOfMonth(1); // Add one month and set day to 1st of the next month

        return nextPeriodStart.format(formatter); // Return the next period's start date in the same format (yyyyMMdd)
    }

    private int calculateDaysInPeriod(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        return (int) daysBetween;
    }

    public Map<String, Object> getAccountStatement(String accountId, String yearMonth) {
        Account account = accountRepository.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4, 6));

        List<Transaction> monthlyTransactions = account.getTransactionsForMonth(year, month);
        Map<String, List<InterestRule>> applicableRules = getInterestRules();

        double interest = calculateMonthlyInterest(account, year, month, applicableRules);

        DecimalFormat df = new DecimalFormat("0.00");

        double balanceWithInterest = account.getBalance() + interest;


        Map<String, Object> statement = new HashMap<>();
        statement.put("accountId", accountId);
        statement.put("transactions", monthlyTransactions);
        statement.put("interest", df.format(interest));
        statement.put("finalBalance", df.format(balanceWithInterest));

        return statement;
    }


}