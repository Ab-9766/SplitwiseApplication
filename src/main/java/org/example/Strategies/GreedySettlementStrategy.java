package org.example.Strategies;

import org.example.Models.Transaction;
import org.example.Models.UserForPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class GreedySettlementStrategy implements SettlementStrategy{
    @Override
    public List<Transaction> settleUp(HashMap<String, Integer> balanceSheet) {
        PriorityQueue<UserForPQ> pqLess = new PriorityQueue<>();
        PriorityQueue<UserForPQ> pqMore = new PriorityQueue<>();

        for(String user: balanceSheet.keySet()){
            if(balanceSheet.get(user) > 0){
                pqMore.add(new UserForPQ(user, balanceSheet.get(user)));
            }
            else {
                pqLess.add(new UserForPQ(user, -balanceSheet.get(user)));
            }
            }

        List<Transaction> result= new ArrayList<>();
        while( pqMore.size() > 0 && pqLess.size() > 0 )
        {
            //Initially remove form MaxPriorityQueue
            UserForPQ fromUser= pqLess.remove();  //Pay from less to more
            UserForPQ toUser= pqMore.remove();

            int amount=Math.min(fromUser.getBalance(), toUser.getBalance()); //Min of two to be paid
            Transaction t= new Transaction(fromUser.getName(),toUser.getName(),amount);
            if(t!=null){
            result.add(t);}

            fromUser.setBalance(fromUser.getBalance() - amount);
            toUser.setBalance(toUser.getBalance() - amount);

            if(fromUser.getBalance() > 0)
            {
                pqLess.add(fromUser);
            }
            if(toUser.getBalance() > 0){
                pqMore.add(toUser);
            }
        }
        return result;
    }
}
