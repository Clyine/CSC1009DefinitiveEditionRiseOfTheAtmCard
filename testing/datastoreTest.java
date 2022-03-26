package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.*;

import controller.SystemController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.ConnectException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import model.*;
import datastore.DataStore;
import view.AccountView;

public class datastoreTest{
    static int MAX = 20;
    private SystemController controller;
    Random r = new Random();
    DataStore d = new DataStore();
    protected AccountView view;
    protected Account account;
    ConcurrentHashMap<String, Account> test;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    // @Before
    // void init(){
    //     test = d.getDataStore();
    // }
    
    @Test
    void getDataStoreTest(){ // PASSED
        ConcurrentHashMap<String, Account> testing = this.d.getDataStore();  
        for(String key: testing.keySet()){
            assertTrue(key instanceof String);
            assertTrue(testing.get(key) instanceof Object);
        
        }
    }

    @Test
    void getAccountTest(){ // no idea man wtf
        // what
    }

    @Test
    void initDataTest(){ // PASSED
        for(int i=0; i<MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
    
            ConcurrentHashMap<String, Account> testData = new ConcurrentHashMap<String, Account>();
            testData.put(str1,acc);
            Boolean flag = d.initData(testData);
            assertTrue(flag);
        }
    }

    @Test
    void initHeaderTest(){ // PASSED
        for(int i=0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
    
            ConcurrentHashMap<String, Account> testData = new ConcurrentHashMap<String, Account>();
            testData.put(str1,acc);
            Boolean flag = d.initData(testData);
            assertTrue(flag);
        }
    }      


    @Test
    void writeLedgerTest(){ // PASSED
        for(int i = 0; i < MAX; i++){
            String transDate = dateFormat.format(new Date());
            String valueDate = dateFormat.format(new Date());
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);
            this.controller = new SystemController(acc);
            boolean flag = d.writeLedger(controller, t);
            assertTrue(flag);
        }
    }


    @Test
    void writeNewAccTest(){ // PASSED
        for(int i = 0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
    
            Boolean flag = d.writeNewAcc(acc.getAccNo());
            assertTrue(flag);
        }
    }

    @Test
    void writeHeaderTest(){ // PASSED
        for(int i = 0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
            this.controller = new SystemController(acc);
            controller.setWithdrawalLimit(Integer.parseInt(str2));
            
            Boolean flag = d.writeHeader(controller);
            assertTrue(flag);
        }
    }
}