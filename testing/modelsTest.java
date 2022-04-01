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

    private SystemController controller;                                                    // Create new SystemController object
    protected AccountView view;                                                             // Create new AccountView object
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Create new SimpleDateFormat object
    static int MAX = 20;                                                                    // Create new int MAX and set to 20
    Random r = new Random();                                                                // Create new random object, r
    
    @Test
    void getAccNoTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times                                           
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1, str2);                                       // Create new account object with parameters str1 and str2
            String msg = acc.getAccNo();                                                    // Call the get account number function and store as String in msg
            assertTrue(msg instanceof String);                                              // Assert true if msg is of type String
        }
    }

    @Test
    void getBalanceTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1, str2);                                       // Create new account object with parameters str1 and str2
            long msg = acc.getBalance();                                                    // Call the get balance function and store as long in msg 
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }

    @Test
    void getPinTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times 
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            String msg = acc.getPin();                                                      // Call the get pin function and store as String in msg 
            assertTrue(msg instanceof String);                                              // Assert true if msg is of type String
        }
    }
    
    @Test
    void setWithdrawalLimitTest() { // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times 
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            long withdrawlimit = r.nextInt((1000000 - 100) + 1) + 100;                      // Generate random value and store as long in withdrawal limit
            acc.setWithdrawalLimit(withdrawlimit);                                          // Call function setWithdrwalLimit and store as long in msg
            long msg = acc.setWithdrawalLimit(withdrawlimit);                               
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }
    
    @Test
    void getWithdrawalLimitTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times 
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            long withdrawlimit = r.nextInt((1000000 - 100) + 1) + 100;                      // Generate random value and store as long in withdrawal limit
            acc.setWithdrawalLimit(withdrawlimit);                                          // Call function setWithdrwalLimit
            long msg = acc.getWithdrawalLimit();                                            // Call function getWithdrawalLimit and store output in msg as long
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }

    @Test
    void addTransactionTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times 
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            this.controller = new SystemController(acc);                                    // Create new systemController with acc
            long amt = r.nextInt((1000000 - 100) + 1) + 100;                                // Generate random value and store as long in amt 
            Transaction T = controller.addDeposit(amt);                                     // Create transaction object with add deposit amt
            long withdrawal = r.nextInt((1000000 - 100) + 1) + 100;                         // Generate random value and store as long in withdrawal
            T = controller.addWithdrawal(withdrawal);                                       // Call addWithdrawal function with withdrawal amount
            String msg = acc.getTransactionListing(i);                                      // Call getTransactionListing function and store output as String in msg
            assertTrue(msg instanceof String);                                              // Assert true if msg is of type String
        }
    }
    
    @Test
    void getTransactionListingTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            String msg = acc.getTransactionListing(10);                                     // Call getTransactionListing function and store output as String in msg
            assertTrue(msg instanceof String);                                              // Assert true if msg is of type String
        }
    }
    
    @Test
    void getOverdraftLimitTest(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            CurrentAcc acc = new CurrentAcc(str1,str2);                                     // Create new account object with parameters str1 and str2
            long overdraftlimit = r.nextInt((1000000 - 100) + 1) + 100;                     // Generate random value and store as long in overdraftlimit 
            acc.setOverdraftLimit(overdraftlimit);                                          // Call setOverdraftLimit function to set limit
            long msg = acc.getOverdraftLimit();                                             // Call getOverdraftLimit function and store as long in msg
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }

    @Test
    void setOverdraftLimit(){ // PASSED
        for(int i=0; i < MAX; i++){                                                         // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            CurrentAcc acc = new CurrentAcc(str1,str2);                                     // Create new account object with parameters str1 and str2
            long overdraftlimit = r.nextInt((1000000 - 100) + 1) + 100;                     // Generate random value and store as long in overdraftlimit
            long msg = acc.setOverdraftLimit(overdraftlimit);                               // Call serOverdraftLimit function and store as long in msg
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }

    @Test
    void getTransactionDateTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as long in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as long in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as long in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            String date = t.getTransactionDate();                                           // Get the transaction date and store in variable msg
            assertTrue(isValidDate(date));                                                  // Assert true if date is valid
        }
    }

    @Test
    void getValueDateTest(){ // PASSED  
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as long in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            String date = t.getValueDate();                                                 // Get the valuedate and store in variable date
            assertTrue(isValidDate(date));                                                  // Assert true if date is valid
        }
    }

    @Test
    void getChequeNoTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as string in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            assertTrue(t.getChequeNo() instanceof String);                                  // Assert true if getChequeNo function call returns type String
        }
    }

    @Test
    void getDescriptionTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as string in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            assertTrue(t.getDescription() instanceof String);                               // Assert true if getDescription function call returns type String
        }
    }

    @Test
    void getWithdrawTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as string in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            long msg = t.getWithdraw();                                                     // Get the withdraw amount and store in variable msg
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if flag is true
        }
    }

    @Test
    void getDepositTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transDate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as string in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    // Create a new object Transaction and pass in the generated values
            long msg = t.getDeposit();                                                      // Retrieve the running deposit value and store in msg
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Return true if the value is long
        }
    }

    @Test
    void getRunningBalanceTest(){ // PASSED
        for (int i=0; i<MAX ;i++){                                                          // For loop to loop the program MAX times
            String transDate = dateFormat.format(new Date());                               // Create new string transFate for date format
            String valueDate = dateFormat.format(new Date());                               // Create new string valueDate for date format
            String chq = (r.nextInt((1000000 - 100) + 1) + 100) + "";                       // Generate random value and store as string in chq
            String desc = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in desc
            long withdraw = (r.nextLong((100000 - 100) + 1) + 100);                         // Generate random value and store as string in withdraw
            long deposit = (r.nextLong((100000 - 100) + 1) + 100);                          // Generate random value and store as string in deposit
            long runningBalance = (r.nextLong((100000 - 100) + 1) + 100);                   // Generate random value and store as string in running balance
            Transaction t = new Transaction(transDate, valueDate, chq, desc, withdraw, deposit, runningBalance);    //  Create a new object Transaction and pass in the generated values
            long msg = t.getRunningBalance();                                               // Retrieve the running balance value and store in msg
            boolean flag = msg>=-Long.MAX_VALUE && msg<=Long.MAX_VALUE;                     // Check if msg is larger than long max size and store in boolean flag
            assertTrue(flag);                                                               // Assert true if msg is of type Long
        }
    }
    
    @Test
    void getUsernameTest(){ // PASSED
        for (int i=0; i<MAX; i++){                                                          // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            User user = new User(str1, str2);                                               // Create new user object with parameters str1 and str2
            assertTrue(user.getUsername() instanceof String);                               // Assert true if msg is of type String
        }
    }
    
    @Test
    void addAcctest(){ // PASSED
        for (int i=0; i<MAX; i++){                                                          // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            User user = new User(str1, str2);                                               // Create new user object with parameters str1 and str2
            Account acc = new SavingsAcc(str1,str2);                                        // Create new account object with parameters str1 and str2
            assertTrue(user.addAcc(str1, acc) instanceof String);                           // Assert true if msg is of type String
        }
    }
    
    @Test
    void addCurrentAccTest(){ // PASSED
        for (int i=0; i<MAX; i++){                                                          // For loop to loop the program MAX times
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));          // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";                      // Generate random value and store as string in str2
            User user = new User(str1, str2);                                               // Create new user object with parameters str1 and str2
            CurrentAcc acc = new CurrentAcc(str1, str2);                                    // Create new currentaccount object with parameters str1 and str2
            assertTrue(user.addCurrentAcc(str1, acc) instanceof String);                    // Assert true if msg is of type String
        }
    }
    
    public static boolean isValidDate(String inDate) {                                  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");                   // Initialise the date format
        dateFormat.setLenient(false);                                                       // Change the leniency to false
        try {                                                                                   
            dateFormat.parse(inDate.trim());                                                // Pass in the date value 
        } catch (Exception e) {                                                             
            return false;                                                                   // Return false value
        }
        return true;                                                                        // Return true value
    }
}