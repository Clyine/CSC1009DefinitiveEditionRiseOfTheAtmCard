package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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

    @Test
    void getDataStoreTest(){
        for (int i=0; i < MAX; i++){ 
            ConcurrentHashMap<String, Account> testing = data.getDataStore();
            Enumeration enu1 = testing.keys();
            while(enu1.hasMoreElements()){
                assertTrue(enu1.nextElement() instanceof String);       // Key
                Object something = enu1.nextElement();
                assertTrue(testing.get(something) instanceof Object);   // Value
            }         
        }
    }
    

    @Test
    void getAccountTest(){

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