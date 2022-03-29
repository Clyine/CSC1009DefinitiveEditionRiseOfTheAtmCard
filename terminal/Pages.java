package terminal;

import java.io.*;
import java.net.Socket;
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
    //private UserController currentUser;
    //private AccountController currentAcc;
    private static final Random r = new Random();
    private final Validate v;

    // contructor, pass datastore object, input/output stream objects
    public Pages(DataInputStream dis, DataOutputStream dos, DataStore d) {
        this.dis = dis;
        this.dos = dos;
        this.d = d;
        this.v = new Validate(this.dis, this.dos);
    }
    
    public void authPage() throws Exception{
        this.dos.writeUTF("Welcome to ABC ATM!\nEnter 1 to open a new account with us\nEnter 2 to login using an existing account\nPlease enter your choice: ");
        String selection = dis.readUTF(); //take input from Inputstream
        switch (selection) {
                case "1":
                    createAcc();
                    break;
                case "2":
                    login();
                    break;
                default:
                    dos.writeUTF("\nInvalid Selection. Press enter to continue");
                    dis.readUTF();
                    authPage();
                    break;
                }
    }
        
    public void login() throws Exception{
        while (true) {
            this.dos.writeUTF("SCANRFID");
            String accountNum = this.dis.readUTF().replaceAll("[\\n\\t ]",""); //read from RFID
            this.dos.writeUTF("Please enter your pin no.: ");
            String pin = this.dis.readUTF();

            try {
                this.controller = new SystemController(this.d.getAccount(accountNum));
                System.out.println(this.controller.getPin());
                //System.out.println("db:"+pin);

                if (!this.controller.getPin().equals(pin)) {
                    
                    this.dos.writeUTF("Invalid username or PIN! Press enter to continue\n");
                    this.controller = null;
                    this.dis.readUTF();
                    //login();
                } else {
                    break;
                }
            } catch (Exception e) {
                this.dos.writeUTF("Invalid username or PIN! + Press enter to continue\n");
                this.dis.readUTF();
                this.controller = null;
            }

        }
    }

    private void createAcc() throws Exception{
        this.dos.writeUTF("Please select account to create\n1) Savings Account\n2) Current Account");
        String input = this.dis.readUTF();
        switch (input) {
            case "1":
                this.dos.writeUTF("\nPlease input desired PIN for your account (Alphanumeric)");
                String pin = v.validateStringNoSpecialChar(this.dis.readUTF());
                String accNo = generateSavAccNo(this.d); 
                Account newAcc = new SavingsAcc(accNo, pin);
                this.controller = new SystemController(newAcc);
                Transaction t = controller.addOpeningTransaction(); //add opening transaction into account
                this.d.getDataStore().put(accNo, newAcc); //add new account into memory
                this.d.writeLedger(controller, t); //write account details into csv
                this.d.writeHeader(controller); //write account header into csv
                this.dos.writeUTF("\nSuccess, your new savings account number is : " + accNo + "\nPress enter to continue.");
                this.dis.readUTF();
                break;
            case "2":
                this.dos.writeUTF("\nPlease input desired PIN for your account (Alphanumeric)");
                pin = v.validateStringNoSpecialChar(this.dis.readUTF());
                accNo = generateCurrAccNo(this.d); 
                newAcc = new CurrentAcc(accNo, pin);
                this.controller = new SystemController(newAcc);
                t = controller.addOpeningTransaction(); //add opening transaction into account
                this.d.getDataStore().put(accNo, newAcc); //add new account into memory
                this.d.writeLedger(controller, t); //write account details into csv
                this.d.writeHeader(controller); //write account header into csv
                this.dos.writeUTF("\nSuccess, your new current account number is : " + accNo + "\nPress enter to continue.");
                this.dis.readUTF();
                break;
        
            default:
                dos.writeUTF("\nInvalid Selection. Press enter to continue");
                dis.readUTF();
                break;
        }
    }

    public void depositPage() throws Exception {
        this.dos.writeUTF("\nAmount to deposit: ");
        long amt = v.validateAmount(this.dis.readUTF());
        Transaction T = controller.addDeposit(amt);
        d.writeLedger(controller, T);
        this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f", (double) (this.controller.getBalance()) / 100) + "\nPress enter to continue");
        this.dis.readUTF();
    }

    public void withdrawPage() throws Exception {
        try {
            this.dos.writeUTF("\nAmount to withdraw: ");
            long amt = v.validateAmount(this.dis.readUTF());
            if (amt > controller.getWithdrawalLimit()){
                throw new WithdrawalLimitExceedException(controller.getWithdrawalLimit());
            }

            if (controller.getBalance() - amt < controller.getOverdraftLimit()) {
                if (controller.getOverdraftLimit() == 0) {
                    throw new InsufficientBalanceException();
                }
                else {
                    throw new OverdraftLimitExceedException(controller.getOverdraftLimit());
                }
            }
            Transaction T= controller.addWithdrawal(amt);
            d.writeLedger(controller, T);
            this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f", (double) (this.controller.getBalance()) / 100) + "\nPress enter to continue");
            this.dis.readUTF();
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage()+ "\nPress enter to continue");
            this.dis.readUTF();
        }
    }

    public void transferPage() throws Exception {
        try{
            this.dos.writeUTF("\nInput payee's Account Number: ");
            String str = dis.readUTF();
            if (d.getDataStore().get(str) == null) {
                this.dos.writeUTF("\nInvalid Account Number. Press Enter to continue");
            }
            else {
                SystemController payeeCon = new SystemController(d.getDataStore().get(str));
                SystemController payorCon = this.controller;
                this.dos.writeUTF("\nAmount to transfer: ");
                long amt = v.validateAmount(this.dis.readUTF());
                if (amt > controller.getWithdrawalLimit()){
                    throw new WithdrawalLimitExceedException(controller.getWithdrawalLimit());
                }

                if (controller.getBalance() - amt < controller.getOverdraftLimit()) {
                    if (controller.getOverdraftLimit() == 0) {
                        throw new InsufficientBalanceException();
                    }
                    else {
                        throw new OverdraftLimitExceedException(controller.getOverdraftLimit());
                    }
                }
                Transaction inbound = payeeCon.addDeposit(amt, "Transfer from " + payorCon.getAccNo());
                Transaction outbound = payorCon.addWithdrawal(amt, "Transfer to " + payeeCon.getAccNo());

                this.d.writeLedger(payeeCon, inbound);
                this.d.writeLedger(payorCon, outbound);
                this.dos.writeUTF("\nSuccess, Your new balance is $" + String.format("%.2f",(double)(payorCon.getBalance())/100) + "\nPress enter to continue");
                }
            this.dis.readUTF();
        } catch (Exception e) {
            this.dos.writeUTF(e.getMessage()+ "\nPress enter to continue");
            this.dis.readUTF();
        }
    }

    public void detailsPage() throws Exception {
        this.dos.writeUTF("\nPlease select option:\n1)View Withdrawal Limit\n2)Set Withdrawal Limit\n3)View Overdraft Limit\n4)Set Overdraft Limit\n");
        String select = this.dis.readUTF();
        switch (select) {
            case "1":
                this.dos.writeUTF(this.controller.printWithdrawalLimit() + "\nPress enter to continue");
                this.dis.readUTF();
                break;
            case "2":
                this.dos.writeUTF("\nPlease input preferred withdrawal limit");
                long input = v.validateAmount(this.dis.readUTF());
                controller.setWithdrawalLimit(input);
                this.d.writeHeader(controller);
                this.dos.writeUTF("Success, " + this.controller.printWithdrawalLimit() + "\nPress enter to continue");
                this.dis.readUTF();
                break;
            case "3":
                this.dos.writeUTF(this.controller.printOverdraftLimit() + "\nPress enter to continue");
                this.dis.readUTF();
                break;
            case "4":
                if (this.controller.printOverdraftLimit().equals("This is a savings account. Overdraft is not available.")){
                    this.dos.writeUTF("Failure, " + this.controller.printOverdraftLimit()+ "\nPress enter to continue");
                } else {
                this.dos.writeUTF("\nPlease input preferred overdraft limit");
                input = v.validateAmount(this.dis.readUTF());
                controller.setOverdraftLimit(input);
                this.d.writeHeader(controller);
                this.dos.writeUTF("Success, " + this.controller.printOverdraftLimit() + "\nPress enter to continue");
                this.dis.readUTF();
                }
                break;
            default:
                dos.writeUTF("\nInvalid Selection. Press enter to continue");
                dis.readUTF();
                detailsPage();
                break;
        }
    }

    public void statementPage() throws Exception{
        this.dos.writeUTF(this.controller.printTransactionListing() + "\nPress Enter to continue");
        this.dis.readUTF();
    }

    public void balancePage() throws Exception{
        this.dos.writeUTF(this.controller.printBalance() + "\nPress Enter to continue");
        this.dis.readUTF();
    }

    public void printPrompt() throws Exception{
        this.dos.writeUTF("\nCurrently in: " + this.controller.getAccNo() + "\n\n1. View Balance\n2. View Statement\n3. Deposit\n4. Withdraw\n5. Transfer\n6. View Account Details\n9. Logout\n\nPlease select option: ");
    }

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
        String str = String.format("%d", 4000000000l + r.nextLong(10000000));
        if (!d.getDataStore().containsKey(str)){
            return str;
        }
        else {
            return generateCurrAccNo(d);
        }
    }

}
