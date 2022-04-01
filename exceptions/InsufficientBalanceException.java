package exceptions;

public class InsufficientBalanceException extends Exception{
    String msg = "Your account has insuffient balance"; // this is our thrown exception message

    @Override
    public String getMessage(){
        return this.msg;   //return string message above
    }

    public String WithdrawalLimitExceedException(long withdrawlimit) {
        return null;  //return null
    }
}
