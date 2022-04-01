package testing;

// Imports
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;
import controller.SystemController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import model.*;
import datastore.DataStore;
import view.AccountView;

public class datastoreTest{

    static int MAX = 20;                    // Set a constant 20 for the loop
    private SystemController controller;    // Initialise a new object
    Random r = new Random();                // Create a new object from random
    DataStore d = new DataStore();          // Create a new object from datastore
    protected AccountView view;             // Create a new object from AccountView
    protected Account account;              // Create a new object from Account
    ConcurrentHashMap<String, Account> test;    // Create a concurrent hash map 
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Initialise a new date format
    
    @Test
    void getDataStoreTest(){ // PASSED
        ConcurrentHashMap<String, Account> testing = this.d.getDataStore();  // Create concurrent hash map object testing
        for(String key: testing.keySet()){
            assertTrue(key instanceof String);                               // Assert true if key is of type string       
            assertTrue(testing.get(key) instanceof Object);                  // Assert true if return value of get key is of type object
        
        }
    }

    @Test
    void initDataTest(){ // PASSED
        for(int i=0; i<MAX; i++){                                                       // For loop to loop the program MAX times      
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));      // Generate random value and store inside String str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                  // Generate random value and store inside String str2
            Account acc = new SavingsAcc(str1, str2);                                   // Create new object account, acc, with str1 and str2 as parameters
    
            ConcurrentHashMap<String, Account> testData = new ConcurrentHashMap<String, Account>();     // Create concurrent hash map object testData
            testData.put(str1,acc);                                                                     // Store acc into hash map
            Boolean flag = d.initData(testData);                                                        // Call the initialize data function and store result in boolean flag
            assertTrue(flag);                                                                           // Assert true if flag is true
        }
    }

    @Test
    void initHeaderTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                             // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));              // Generate random value and store inside String str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                          // Generate random value and store inside String str2
            Account acc = new SavingsAcc(str1, str2);                                           // Create new object account, acc, with str1 and str2 as parameters
            ConcurrentHashMap<String, Account> testData = new ConcurrentHashMap<String, Account>();     // Create concurrent hash map object testData
            testData.put(str1,acc);                                                                     // Store acc into hash map
            Boolean flag = d.initData(testData);                                                        // Call the initialize data function and store result in boolean flag
            assertTrue(flag);                                                                           // Assert true if flag is true
        }
    }      

    @Test
    void writeLedgerTest(){ // PASSED
        for(int i = 0; i < MAX; i++){                                       // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());               // Generate a random date for transDate
            String valueDate = dateFormat.format(new Date());               // Generate a random date for valueDatae
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";       // Generate a random cheque number
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";      // Generate a random desc number
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);         // Generate a random withdraw amount
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);          // Generate a random deposit amount
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);   // Generate a random running balance amount
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate a random long number
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate a random account number
            Account acc = new SavingsAcc(str1, str2);                               // Pass in the random long number and account number into the object
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance); // Pass in all the generated values into object
            this.controller = new SystemController(acc);                            // Pass in the object into SystemController
            boolean flag = d.writeLedger(controller, t);                            // Return the value of the function writeLedger
            assertTrue(flag);                                                       // Check if it is true
        }
    }

    @Test
    void writeNewAccTest(){ // PASSED
        for(int i = 0; i < MAX; i++){                                               // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate a random long number
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate a random account number
            Account acc = new SavingsAcc(str1, str2);                               // Pass the two generated values into the object
            Boolean flag = d.writeNewAcc(acc.getAccNo());                           // Return the value of the function writeNewAcc
            assertTrue(flag);                                                       // Check if it is true
        }
    }

    @Test
    void writeHeaderTest(){ // PASSED
        for(int i = 0; i < MAX; i++){                                               // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate a random long number
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate a random account number
            Account acc = new SavingsAcc(str1, str2);                               // Pass in the two random values into acc object
            this.controller = new SystemController(acc);                            // Initialise the object to system controller
            controller.setWithdrawalLimit(Integer.parseInt(str2));                  // Set withdrawal limit based on the account number
            Boolean flag = d.writeHeader(controller);                               // Return the value of the function writeHeader
            assertTrue(flag);                                                       // Check if it is true
        }
    }
}