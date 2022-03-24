package testing;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.text.View;

import org.junit.jupiter.api.Test;
import controller.*;
import model.*;
import view.*;
import view.AccountView;

public class viewTest {

    static int MAX = 20;
    Random r = new Random();
    protected AccountView view;
    private SystemController controller;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void printBalanaceTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                  
            long balance = (r.nextLong((100000 - 100) + 1) + 100);
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            SystemController controller = new SystemController(acc);
            controller.addDeposit(balance);
            String msg = controller.printBalance();
            assertTrue(msg instanceof String);
        }
    }

    @Test
    void printTransactionListingTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                  
            long balance = (r.nextLong((100000 - 100) + 1) + 100);
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            SystemController controller = new SystemController(acc);
            controller.addDeposit(balance);
            String msg = controller.printTransactionListing();
            assertTrue(msg instanceof String);
        }
    }
    
    @Test
    void printWithdrawalLimitTest() throws IOException{ // Passed
        for (int i=0; i < MAX; i++){                  
            long balance = (r.nextLong((100000 - 100) + 1) + 100);
            String str1 = String.format("%d", 4000000000l + r.nextLong(10000000));
            String str2 = (r.nextInt((1000000 - 100) + 1) + 100) + "";
            Account acc = new SavingsAcc(str1,str2);
            SystemController controller = new SystemController(acc);
            controller.addDeposit(balance);
            String msg = controller.printWithdrawalLimit();
            assertTrue(msg instanceof String);
        }
    }

    @Test
    void printOverdraftLimitTest() throws IOException{

    }

    
    
}
