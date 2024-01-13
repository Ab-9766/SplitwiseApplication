package org.example;

import org.example.Models.ExpensePaidBy;
import org.example.Models.ExpenseSharedBy;
import org.example.Models.Transaction;
import org.example.Strategies.RoundRobinSettlementStrategy;
import org.example.Strategies.SettlementStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        // real input is group name
        // we will get the following two lists by navigating the following repo =>
        // group, groupExpense, Expense, ExpensePaidBy, ExpenseSharedBy
        List<ExpensePaidBy> paidBy = new ArrayList<>();
        paidBy.add(new ExpensePaidBy("A", 5000));
        paidBy.add(new ExpensePaidBy("B", 5000));
        paidBy.add(new ExpensePaidBy("A", 5000));
        paidBy.add(new ExpensePaidBy("B", 5000));
        paidBy.add(new ExpensePaidBy("C", 5000));

        List<ExpenseSharedBy> sharedBy = new ArrayList<>();
        sharedBy.add(new ExpenseSharedBy("A", 2000));
        sharedBy.add(new ExpenseSharedBy("B", 2000));
        sharedBy.add(new ExpenseSharedBy("C", 3000));
        sharedBy.add(new ExpenseSharedBy("D", 3000));
        sharedBy.add(new ExpenseSharedBy("A", 4000));
        sharedBy.add(new ExpenseSharedBy("B", 4000));
        sharedBy.add(new ExpenseSharedBy("C", 4000));
        sharedBy.add(new ExpenseSharedBy("D", 3000));

        settleUp(paidBy, sharedBy, new RoundRobinSettlementStrategy());
    }
    public static void settleUp(List<ExpensePaidBy> paidBy, List<ExpenseSharedBy> sharedBy,
                         SettlementStrategy settlementStrategy) {
        //Create BalanceSheet

        HashMap<String, Integer> balanceSheet = new HashMap<>();
        for (ExpensePaidBy payer : paidBy) {
            if (balanceSheet.containsKey(payer.getName())) {
                balanceSheet.put(payer.getName(), balanceSheet.get(payer.getName()) + payer.getContribution());

            } else {
                balanceSheet.put(payer.getName(), payer.getContribution());
            }
        }

        for (ExpenseSharedBy sharer : sharedBy) {
            if (balanceSheet.containsKey(sharer.getName())) {
                balanceSheet.put(sharer.getName(), balanceSheet.get(sharer.getName()) - sharer.getContribution());

            } else {
                balanceSheet.put(sharer.getName(), -sharer.getContribution());
            }
        }
        //Invoke Settlement Algo
        List<Transaction> transactions = settlementStrategy.settleUp(balanceSheet);

        //Print
        System.out.println(transactions);
    }
}