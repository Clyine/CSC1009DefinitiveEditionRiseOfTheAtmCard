package terminal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import datastore.*;

import exceptions.NegativeInputException;

public class Validate {

    private DataInputStream dis;
    private DataOutputStream dos;

    public Validate(DataInputStream dis, DataOutputStream dos){
        this.dis = dis;
        this.dos = dos;
    }

    public String validateStringNoSpecialChar(String str) throws Exception{
        try{
            if (str.matches(".*[\\!\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\>\\=\\?\\@\\[\\]\\{\\}\\\\\\^\\_\\`\\~]+.*")) throw new Exception("Invalid input: No special characters allowed.");
            return str;
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage() + "\nPlease try again\nInput: ");
            String newStr = validateStringNoSpecialChar(this.dis.readUTF());
            return newStr;
        }
    }

    public String validateAccountNumber(String str) throws Exception {
        try {
            if (str.length() != 10) throw new Exception("Invalid account number format"); 
            return str;
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage() + "\nPlease try again\nInput: ");
            String newStr = validateAccountNumber(this.dis.readUTF());
            return newStr;
        }
    }

    public Long validateAmount(String str) throws NumberFormatException, IOException{
        try{
            long amt = (long)((Double.parseDouble(str))*100);

            if (amt <= 0) {
                throw new NegativeInputException();
            }
            return amt;
        } catch (NumberFormatException e) {
            this.dos.writeUTF("\nInvalid number format" + "\nPlease try again\nRe-enter amount: ");
            long amt = validateAmount(this.dis.readUTF());
            return amt;
        } catch (NegativeInputException e) {
            this.dos.writeUTF("\n"+e.getMessage()+" Please try again\nRe-enter amount: ");
            long amt = validateAmount(this.dis.readUTF());
            return amt;
        }
    }
}
