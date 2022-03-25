package testing;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runners.Parameterized.BeforeParam;

import controller.SystemController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.View;
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
    ConcurrentHashMap<String, Account> test = new ConcurrentHashMap<>();

    
    
    @Test
    void getDataStoreTest(){ // PASSED
        ConcurrentHashMap<String, Account> testing = d.getDataStore();  
        for(String key: testing.keySet()){
            assertTrue(key instanceof String);
            assertTrue(testing.get(key) instanceof Object);
        
        }
    }

    @Test
    void getAccountTest(){
        //create();
        //Object object =  data.getAccount("8009857096");
        //String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
        //String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
        //Account acc = new SavingsAcc(str1,"");
        //test.put("8009857096", acc);
        //data.put(str1, acc);
        //this.controller = new SystemController(data.getAccount(str1));
        Object object = this.d.getAccount("8009857096");
        System.out.println(object.getClass().getSimpleName());
        //System.out.println(object.getClass().getSimpleName());
        //assertTrue(object instanceof Object);
        boolean flag = object.getClass().getSimpleName() == "Account";
        assertTrue(flag);
    }

    // @Test
    // void getAccountTest1(){
    //     Object object =  data.getAccount("8009857096");
    //     assertTrue(object instanceof Object);
    // }

    @Test
    void initDataTest(){
        ConcurrentHashMap<String, Account> testHM = new ConcurrentHashMap<String, Account>();
        
    }

    @Test
    void initHeaderTest(){
        ConcurrentHashMap<String, Account> testHM = new ConcurrentHashMap<String, Account>();
        
    }

    @Test
    void writeLedgerTest(){
        for(int i = 0; i < MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);


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
    void writeHeaderTest(){
        for(int i = 0; i < MAX; i++){
            String str2 = (r.nextInt((1000 - 100) + 1) + 100) + "";
            controller.setWithdrawalLimit(Integer.parseInt(str2));
            
            Boolean flag = d.writeHeader(controller);
            assertTrue(flag);
        }
    }





















}