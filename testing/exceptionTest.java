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
        for(int i = 0; i < MAX; i++){
            String msg = insuf.getMessage();
            assertTrue(msg instanceof String);
        }
    }
}