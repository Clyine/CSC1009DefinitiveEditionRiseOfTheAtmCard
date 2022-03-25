package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runners.Parameterized.BeforeParam;

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
    Random r = new Random();
    DataStore data = new DataStore();
    protected AccountView view;
    protected Account account;
    ConcurrentHashMap<String, Account> test = new ConcurrentHashMap<>();

    @Before  
    void create(){

        for (int i=0; i<MAX; i++){
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1, str2);
            //Enumeration<String> enu1 = testing.keys();

            
            while(enu1.hasMoreElements()){
                
                String something = enu1.nextElement();
                test.put(something,acc);   // Value
            }         



        }

    }
    

    @Test
    void getDataStoreTest(){ // NOT PASSED
        for (int i=0; i < MAX; i++){ 
            ConcurrentHashMap<String, Account> testing = data.getDataStore();
            Enumeration<String> enu1 = testing.keys();
            while(enu1.hasMoreElements()){
                assertTrue(enu1.nextElement() instanceof String);       // Key
                Object something = enu1.nextElement();
                assertTrue(testing.get(something) instanceof Object);   // Value
            }         
        }
    }


    @Test
    void getAccountTest(){
        Account object =  data.getAccount("8009857096");
        System.out.println(data.getDataStore().keySet());
        assertTrue(data.getDataStore().keys() == null);
    }

    @Test
    void initDataTest(){

    }

    @Test
    void initHeaderTest(){

    }

    @Test
    void writeLedgerTest(){

    }


    @Test
    void writeNewAccTest(){

    }


    @Test
    void writeHeaderTest(){

    }





















}