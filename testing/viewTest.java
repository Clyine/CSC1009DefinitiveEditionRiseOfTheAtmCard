package testing;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;
import controller.*;
import model.*;
import view.AccountView;

public class viewTest {

    static int MAX = 20;            // Define a constant
    Random r = new Random();        // Create a random object
    protected AccountView view;     // Declare a object AccountView

    @Test
    void printBalanaceTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                                                // For loop to loop the program MAX times For loop to loop the program MAX times
            long balance = (r.nextLong((100000 - 100) + 1) + 100);                  // Generate a random long value for balance                                        
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                // Creating a new object and passing in str1 and str2
            SystemController controller = new SystemController(acc);                // Create new controller object
            controller.addDeposit(balance);                                         // Add deposit with the generated value
            String msg = controller.printBalance();                                 // Store the data into msg from printBalance
            assertTrue(msg instanceof String);                                      // Assert true if msg is String
        }
    }

    @Test
    void printTransactionListingTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                                                // For loop to loop the program MAX times For loop to loop the program MAX times
            long balance = (r.nextLong((100000 - 100) + 1) + 100);                  // Generate a random long value for balance     
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                // Creating a new object and passing in str1 and str2
            SystemController controller = new SystemController(acc);                // Create new controller object
            controller.addDeposit(balance);                                         // Add deposit with the generated value
            String msg = controller.printTransactionListing();                      // Store the data into msg from printTransactionListing
            assertTrue(msg instanceof String);                                      // Assert true if msg is String
        }
    }
    
    @Test
    void printWithdrawalLimitTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                                                // For loop to loop the program MAX times                             
            long balance = (r.nextLong((100000 - 100) + 1) + 100);                  // Generate a random long value for balance     
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                // Creating a new object and passing in str1 and str2
            SystemController controller = new SystemController(acc);                // Create new controller object
            controller.addDeposit(balance);                                         // Add deposit with the generated value
            String msg = controller.printWithdrawalLimit();                         // Store the data into msg from printWithdrawalLimit
            assertTrue(msg instanceof String);                                      // Assert true if msg is String
        }
    }

    @Test
    void printOverdraftLimitTest() throws IOException{ // PASSED
        for (int i=0; i < MAX; i++){                                                // For loop to loop the program MAX times loop to loop the program MAX times 
            long balance = (r.nextLong((100000 - 100) + 1) + 100);                  // Generate a random long value for balance     
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));  // Generate random value and store as string in str1
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";              // Generate random value and store as string in str2
            Account acc = new SavingsAcc(str1,str2);                                // Creating a new object and passing in str1 and str2
            SystemController controller = new SystemController(acc);                // Create new controller object
            controller.addDeposit(balance);                                         // Add deposit with the generated value
            String msg = controller.printOverdraftLimit();                          // Store the data into msg from printOverDraftLimit
            assertTrue(msg instanceof String);                                      // Assert true if msg is String
        }
    }
}