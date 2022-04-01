package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.Random;
import exceptions.InsufficientBalanceException;
import exceptions.OverdraftLimitExceedException;
import view.AccountView;

public class exceptionTest{

    private int MAX = 20;
    InsufficientBalanceException insuf = new InsufficientBalanceException();
    OverdraftLimitExceedException overd;

    Random r = new Random();
    protected AccountView view;

    @Test
    void getMessageTest(){ // PASSED
        for(int i = 0; i < MAX; i++){               // For loop to loop the program MAX times
            String msg = insuf.getMessage();        // Retrieve message and store in String msg
            assertTrue(msg instanceof String);      // Assert true if message is of type String
        }
    }
}