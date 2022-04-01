package terminal;

import java.io.*;
import java.util.*;
import controller.*;
import model.*;
import datastore.*;
import exceptions.*;

public class Pages {

    private final DataStore d;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private SystemController controller;
    private static final Random r = new Random();
    private final Validate v;

    // contructor, pass datastore object, input/output stream objects
    public Pages(DataInputStream dis, DataOutputStream dos, DataStore d) {
        this.dis = dis;
        this.dos = dos;
        this.d = d;
        this.v = new Validate(this.dis, this.dos);
    }
    // Function to authenticate user
    public void authPage() throws Exception{
        this.dos.writeUTF("Welcome to ABC ATM!\nEnter 1 to open a new account with us\nEnter 2 to login using an existing account\nPlease enter your choice: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        String selection = dis.readUTF(); //take input from Inputstream
        switch (selection) {
            case "1":
                createAcc();    // If user enters 1, goes to createAcc function
                break;
            case "2":
                login();        // If user enters 2, goes to login function
                break;
            default:
                dos.writeUTF("\nInvalid Selection. Press enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                authPage();     // If user do not enter 1 or 2, it will call function authPage
                break;
            }
    }
        
    public void login() throws Exception{
        while (true) {
            this.dos.writeUTF("SCANRFID");
            String accountNum = this.dis.readUTF().replaceAll("[\\n\\t ]",""); //read from RFID
            this.dos.writeUTF("Please enter your pin no.: ");
            String pin = this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format

            try {
                this.controller = new SystemController(this.d.getAccount(accountNum));
                System.out.println(this.controller.getPin());

                if (!this.controller.getPin().equals(pin)) {
                    
                    this.dos.writeUTF("Invalid username or PIN! Press enter to continue\n"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                    this.controller = null; 
                    this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                } else {
                    break;
                }
            } catch (Exception e) {
                this.dos.writeUTF("Invalid username or PIN! + Press enter to continue\n"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                this.controller = null;
            }
        }
    }

    private void createAcc() throws Exception{
        this.dos.writeUTF("Please select account to create\n1) Savings Account\n2) Current Account"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        String input = this.dis.readUTF();
        switch (input) {
            case "1":
                this.dos.writeUTF("\nPlease input desired PIN for your account (Alphanumeric)"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                String pin = v.validateStringNoSpecialChar(this.dis.readUTF());
                String accNo = generateSavAccNo(this.d); 
                Account newAcc = new SavingsAcc(accNo, pin);
                this.controller = new SystemController(newAcc);
                Transaction t = controller.addOpeningTransaction(); //add opening transaction into account
                this.d.getDataStore().put(accNo, newAcc); //add new account into memory
                this.d.writeLedger(controller, t); //write account details into csv
                this.d.writeHeader(controller); //write account header into csv
                this.dos.writeUTF("\nSuccess, your new savings account number is : " + accNo + "\nPress enter to continue.");  //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                break;
            case "2":
                this.dos.writeUTF("\nPlease input desired PIN for your account (Alphanumeric)"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                pin = v.validateStringNoSpecialChar(this.dis.readUTF());
                accNo = generateCurrAccNo(this.d); 
                newAcc = new CurrentAcc(accNo, pin);
                this.controller = new SystemController(newAcc);
                t = controller.addOpeningTransaction(); //add opening transaction into account
                this.d.getDataStore().put(accNo, newAcc); //add new account into memory
                this.d.writeLedger(controller, t); //write account details into csv
                this.d.writeHeader(controller); //write account header into csv
                this.dos.writeUTF("\nSuccess, your new current account number is : " + accNo + "\nPress enter to continue."); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                break;
        
            default:
                dos.writeUTF("\nInvalid Selection. Press enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                dis.readUTF();
                break;
        }
    }

    public void depositPage() throws Exception {
        this.dos.writeUTF("\nAmount to deposit: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        long amt = v.validateAmount(this.dis.readUTF()); // CHeck if user input amount is valid before storing as long in amt
        Transaction T = controller.addDeposit(amt);
        d.writeLedger(controller, T);                      //write transaction to ledger
        this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f", (double) (this.controller.getBalance()) / 100) + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
    }

    public void withdrawPage() throws Exception {
        try {
            this.dos.writeUTF("\nAmount to withdraw: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            long amt = v.validateAmount(this.dis.readUTF()); //reads the long variable that has been encoded using a modified UTF-8 format
            if (amt > controller.getWithdrawalLimit()){
                throw new WithdrawalLimitExceedException(controller.getWithdrawalLimit());
            }

            if (controller.getBalance() - amt < controller.getOverdraftLimit()) {   // Check if balance - amount is less than the overdraft limit
                if (controller.getOverdraftLimit() == 0) {
                    throw new InsufficientBalanceException();
                }
                else {
                    throw new OverdraftLimitExceedException(controller.getOverdraftLimit());
                }
            }
            Transaction T= controller.addWithdrawal(amt);
            d.writeLedger(controller, T);
            this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f", (double) (this.controller.getBalance()) / 100) + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage()+ "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
        }
    }

    public void transferPage() throws Exception {
        try{
            this.dos.writeUTF("\nInput payee's Account Number: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            String str = dis.readUTF();
            if (d.getDataStore().get(str) == null) {
                this.dos.writeUTF("\nInvalid Account Number. Press Enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            }
            else {
                SystemController payeeCon = new SystemController(d.getDataStore().get(str));
                SystemController payorCon = this.controller;
                this.dos.writeUTF("\nAmount to transfer: ");      //writes a string message to the underlying output stream using modified UTF-8 encoding.
                long amt = v.validateAmount(this.dis.readUTF());    // Validate amount if amount to transfer is valid
                if (amt > controller.getWithdrawalLimit()){         // Check if amount to transfer is greater than withdrawal limit
                    throw new WithdrawalLimitExceedException(controller.getWithdrawalLimit());
                }

                if (controller.getBalance() - amt < controller.getOverdraftLimit()) { //Check if balance - amt is less than overdraft limit
                    if (controller.getOverdraftLimit() == 0) {
                        throw new InsufficientBalanceException();   // Throw new exception if the overdraft limit is 0
                    }
                    else {
                        throw new OverdraftLimitExceedException(controller.getOverdraftLimit());    // Else throw another exception
                    }
                }
                Transaction inbound = payeeCon.addDeposit(amt, "Transfer from " + payorCon.getAccNo());     // Transfer amount from payer
                Transaction outbound = payorCon.addWithdrawal(amt, "Transfer to " + payeeCon.getAccNo());   // Transfer amount to payee
                
                // Write transaction to ledger for payer and payee
                this.d.writeLedger(payeeCon, inbound);
                this.d.writeLedger(payorCon, outbound);
                this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f",(double)(payorCon.getBalance())/100) + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                }
            this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage()+ "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
            this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
        }
    }

    public void detailsPage() throws Exception {
        this.dos.writeUTF("\nPlease select option:\n1)View Withdrawal Limit\n2)Set Withdrawal Limit\n3)View Overdraft Limit\n4)Set Overdraft Limit\n5)Set Account Pin"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        String select = this.dis.readUTF();
        switch (select) {
            // If user input 1, it will display withdrawal limit for the account
            case "1":
                this.dos.writeUTF(this.controller.printWithdrawalLimit() + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF();
                break;
            //If user input 2, it will set withdrawal limit for the account
            case "2":
                this.dos.writeUTF("\nPlease input preferred withdrawal limit"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                long input = v.validateAmount(this.dis.readUTF()); //program will validate user input to check if amount is valid before storing as withdrawal limit
                controller.setWithdrawalLimit(input);
                this.d.writeHeader(controller);
                this.dos.writeUTF("Success, " + this.controller.printWithdrawalLimit() + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                break;
            // If user input 3, it display the overdraft limit
            case "3":
                this.dos.writeUTF(this.controller.printOverdraftLimit() + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF();
                break;
            // If user input 4, it will set overdraft limit, it will check if the account is a savings account before proceeding
            case "4":
                if (this.controller.printOverdraftLimit().equals("This is a savings account. Overdraft is not available.")){
                    this.dos.writeUTF("Failure, " + this.controller.printOverdraftLimit()+ "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                } else {
                this.dos.writeUTF("\nPlease input preferred overdraft limit"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                input = v.validateAmount(this.dis.readUTF());
                controller.setOverdraftLimit(input);
                this.d.writeHeader(controller);
                this.dos.writeUTF("Success, " + this.controller.printOverdraftLimit() + "\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                }
                break;
            // If user input 5, it will ask for user input for the pin
            case "5":
                this.dos.writeUTF("\nPlease input preferred PIN"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                String newPin = v.validateStringNoSpecialChar(this.dis.readUTF());
                this.controller.setPin(newPin);
                this.d.writeHeader(controller);
                this.dos.writeUTF("Success, Pin successfully changed\nPress enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
                break;
            default:
                dos.writeUTF("\nInvalid Selection. Press enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
                dis.readUTF();  //reads the string that has been encoded using a modified UTF-8 format
                detailsPage();
                break;
        }
    }
    // Function to display transaction list
    public void statementPage() throws Exception{
        this.dos.writeUTF(this.controller.printTransactionListing() + "\nPress Enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
    }
    // Function to display balance 
    public void balancePage() throws Exception{
        this.dos.writeUTF(this.controller.printBalance() + "\nPress Enter to continue"); //writes a string message to the underlying output stream using modified UTF-8 encoding.
        this.dis.readUTF(); //reads the string that has been encoded using a modified UTF-8 format
    }

    // Function to print prompt on user interface
    public void printPrompt() throws Exception{
        this.dos.writeUTF("\nCurrently in: " + this.controller.getAccNo() + "\n\n1. View Balance\n2. View Statement\n3. Deposit\n4. Withdraw\n5. Transfer\n6. View Account Details\n9. Logout\n\nPlease select option: "); //writes a string message to the underlying output stream using modified UTF-8 encoding.
    }

    //  Generate current account number, if generated account number does not exitst, return the account number
    private static String generateCurrAccNo(DataStore d) {
        String str = String.format("%d", 8000000000l + r.nextLong(10000000));
        if (!d.getDataStore().containsKey(str)){
            return str;
        }
        else {
            return generateCurrAccNo(d);
        }
    }

    private static String generateSavAccNo(DataStore d) {
        String str = String.format("%d", 4000000000l + r.nextLong(10000000));   // Generate a long value and store into str
        if (!d.getDataStore().containsKey(str)){                                // Check if d.getDataStore returns the generated value
            return str;                                                         // If true, return the generated value
        }
        else {
            return generateCurrAccNo(d);                                        // Else, go to generateCurrAccNo
        }
    }
}