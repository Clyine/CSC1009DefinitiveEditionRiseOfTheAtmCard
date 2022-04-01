package terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import exceptions.NegativeInputException;

public class Validate {

    private DataInputStream dis;
    private DataOutputStream dos;

    public Validate(DataInputStream dis, DataOutputStream dos){
        this.dis = dis;
        this.dos = dos;
    }
    // Function to validate if there are special characters in input, if special characters found, throw exception, else return string
    public String validateStringNoSpecialChar(String str) throws Exception{
        try{
            if (str.matches(".*[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\^\\_\\`\\~]+.*")) throw new Exception("Invalid input: No special characters allowed.");
            return str; //return str
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage() + "\nPlease try again\nInput: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            String newStr = validateStringNoSpecialChar(this.dis.readUTF()); // If special character found, run the validateStringNoSpecialChar function again to validate user input
            return newStr;
        }
    }
    // Function to validate the account number
    public String validateAccountNumber(String str) throws Exception {
        try {
            if (str.length() != 10) throw new Exception("Invalid account number format"); 
            return str;
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage() + "\nPlease try again\nInput: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            String newStr = validateAccountNumber(this.dis.readUTF()); //reads the string that has been encoded using a modified UTF-8 format
            return newStr;
        }
    }
    // Function to validate the amount
    public Long validateAmount(String str) throws NumberFormatException, IOException{
        try{
            str = str.replaceAll("[\\n\\t ]","");
            long amt = (long)((Float.parseFloat(str))*100);

            if (amt <= 0) {
                throw new NegativeInputException();
            }
            return amt;
        } catch (NumberFormatException e) {
            this.dos.writeUTF("\nInvalid number format" + "\nPlease try again\nRe-enter amount: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            long amt = validateAmount(this.dis.readUTF()); //reads the string that has been encoded using a modified UTF-8 format
            return amt;
        } catch (NegativeInputException e) {
            this.dos.writeUTF("\n"+e.getMessage()+" Please try again\nRe-enter amount: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            long amt = validateAmount(this.dis.readUTF()); //reads the string that has been encoded using a modified UTF-8 format
            return amt;
        }
    }
}