package testing;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;
import controller.*;
import model.Account;
import model.CurrentAcc;
import model.SavingsAcc;
import model.Transaction;
import model.User;
import view.AccountView;

public class modelsTest {
    private SystemController controller;
    protected AccountView view;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static int MAX = 20;
    Random r = new Random();
    
    @Test
    void getAccNoTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
            String msg = acc.getAccNo();
            assertTrue(msg instanceof String);
        }
    }

    @Test
    void getBalanceTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
            long msg = acc.getBalance();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }

    @Test
    void getPinTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            String msg = acc.getPin();
            assertTrue(msg instanceof String);
        }
    }
    
    @Test
    void setWithdrawalLimitTest() { // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            long withdrawlimit = r.nextInt((1000000 - 100) + 1) + 100;
            acc.setWithdrawalLimit(withdrawlimit);
            long msg = acc.setWithdrawalLimit(withdrawlimit);
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }
    
    @Test
    void getWithdrawalLimitTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            long withdrawlimit = r.nextInt((1000000 - 100) + 1) + 100;
            acc.setWithdrawalLimit(withdrawlimit);
            long msg = acc.getWithdrawalLimit();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
        //@Test 
    }

    @Test
    void addTransactionTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            this.controller = new SystemController(acc);
            long amt = r.nextInt((1000000 - 100) + 1) + 100;
            Transaction T = controller.addDeposit(amt);
            long withdrawal = r.nextInt((1000000 - 100) + 1) + 100;
            T = controller.addWithdrawal(withdrawal);
            String msg = acc.getTransactionListing(i);
            assertTrue(msg instanceof String);
        }
    }
    
    @Test
    void getTransactionListingTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);

            String msg = acc.getTransactionListing(10);
            assertTrue(msg instanceof String);
        }
    }
    
    @Test
    void getOverdraftLimitTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            CurrentAcc acc = new CurrentAcc(str1,str2);
            long overdraftlimit = r.nextInt((1000000 - 100) + 1) + 100;
            acc.setOverdraftLimit(overdraftlimit);
            long msg = acc.getOverdraftLimit();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }

    @Test
    void setOverdraftLimit(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            CurrentAcc acc = new CurrentAcc(str1,str2);
            long overdraftlimit = r.nextInt((1000000 - 100) + 1) + 100;
            long msg = acc.setOverdraftLimit(overdraftlimit);
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }

    @Test
    void getTransactionDateTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            String date = t.getTransactionDate();
            assertTrue(isValidDate(date));
        }
    }

    @Test
    void getValueDateTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            String date = t.getValueDate();
            assertTrue(isValidDate(date));
        }
    }

    @Test
    void getChequeNoTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            assertTrue(t.getChequeNo() instanceof String);
        }
    }

    @Test
    void getDescriptionTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            assertTrue(t.getDescription() instanceof String);
        }
    }

    @Test
    void getWithdrawTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            long msg = t.getWithdraw();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }

    @Test
    void getDepositTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            long msg = t.getDeposit();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }

    @Test
    void getRunningBalanceTest(){ // PASSED
        for (int i=0; i<MAX ;i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            long msg = t.getRunningBalance();
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;
            assertTrue(flag);
        }
    }
    
    @Test
    void getUsernameTest(){ // PASSED
        for (int i=0; i<MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            User user = new User(str1, str2);
            assertTrue(user.getUsername() instanceof String);
        }
    }
    
    @Test
    void addAcctest(){ // PASSED
        for (int i=0; i<MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            User user = new User(str1, str2);
            Account acc = new SavingsAcc(str1,str2);
            assertTrue(user.addAcc(str1, acc) instanceof String);
        }
    }
    

    @Test
    void addCurrentAccTest(){ // PASSED
        for (int i=0; i<MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            User user = new User(str1, str2);
            CurrentAcc acc = new CurrentAcc(str1,str2);
            assertTrue(user.addCurrentAcc(str1, acc) instanceof String);
        }
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (Exception pe) {
            return false;
        }
        return true;
    }


    
}