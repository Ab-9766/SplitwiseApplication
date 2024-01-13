package org.example.Strategies;

import org.example.Models.Transaction;

import java.util.HashMap;
import java.util.List;

public interface SettlementStrategy {
    List<Transaction> settleUp(HashMap<String,Integer> balanceSheet);
}
