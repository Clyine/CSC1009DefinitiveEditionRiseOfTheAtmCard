package exceptions;

public class NegativeInputException extends Exception{
    String message = "Inputs must be greater than 0.";  // this is our thrown exception message

    @Override
    public String getMessage(){
        return this.message;  // return the message above
    }
}
