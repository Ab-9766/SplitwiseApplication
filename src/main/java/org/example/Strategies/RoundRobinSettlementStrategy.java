package org.example.Strategies;

import org.example.Models.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoundRobinSettlementStrategy implements SettlementStrategy{
    @Override
    public List<Transaction> settleUp(HashMap<String, Integer> balanceSheet) {
        int N=balanceSheet.size();
        ArrayList<String> users= new ArrayList<>(balanceSheet.keySet());

        List<Transaction> result= new ArrayList<>();


        for(int i=0 ;i<=N-2;i++){
            String currentUser= users.get(i);
            String currentPlus1User= users.get(i+1);

            int balanceCurrent= balanceSheet.get(currentUser);
            int balanceCurrentPlus1= balanceSheet.get(currentPlus1User);

            Transaction t= null;

            if(balanceCurrent > 0) {
                //Current user has paid more and he should be recieving money

                t= new Transaction(currentPlus1User,currentUser, balanceCurrent);

            }else if(balanceCurrent < 0){
                //He has paid less and should be paying money
                t= new Transaction(currentUser,currentPlus1User,-balanceCurrent);
            }

            balanceSheet.put(currentUser,0);
            balanceSheet.put(currentPlus1User, balanceCurrentPlus1 + balanceCurrent);
            if(t!=null)
            {
            result.add(t);}
        }
        return result;
    }
}
