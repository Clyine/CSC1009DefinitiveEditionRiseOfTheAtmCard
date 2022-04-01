package controller;
import model.*;
import view.*;

import java.io.IOException;

/*
SystemController Class - 
An abstract class of the object used to interact with the AccountController objects.
This object is used by accessed by the terminal to interact with various Account Controllers.
Methods used for graphical interface for ATM terminal is called from the AccountView object (view)

Constructor will cast the account object to the correct type based on the predefined account number format
4-Prefix = Savings Account
8-Prefix = Current Account
*/
public class SystemController {

    private AccountController accCon;

    public SystemController(Account acc){
        if (acc.getAccNo().charAt(0) == '8'){
            this.accCon = new CurrentAccountController((CurrentAcc)acc, new CurrentAccView());
        }
        else {
            this.accCon = new SavingAccountController((SavingsAcc)acc , new SavingAccView());
        }
    }

    //Add a deposit transaction of given amount to account
    public Transaction addDeposit(long amt){
        return this.accCon.addDeposit(amt);
    } 

    //Add a withdrawal transaction of given amount to account
    public Transaction addWithdrawal(long amt){
        return this.accCon.addWithdrawal(amt);
    } 

    //Add a deposit transaction of given amount to account
    //Overload with custom message for transfer data (payor, payee)
    public Transaction addDeposit(long amt, String message){
        return this.accCon.addDeposit(amt, message);
    } 

    //Add a withdrawal transaction of given amount to account
    //Overload with custom message for transfer data (payor, payee)
    public Transaction addWithdrawal(long amt, String message){
        return this.accCon.addWithdrawal(amt, message);
    } 

    //Add a new empty transaction to be added upon creation of a new account
    public Transaction addOpeningTransaction(){
        return this.accCon.addOpeningTransaction();
    }

    //Adds a defined transaction object to account.
    //Transaction data is extracted from database on Server initialisation
    public Transaction addTransaction(Transaction t) {
        return this.accCon.addTransaction(t);
    }

    //Sets the overdraft limit for the account
    public long setOverdraftLimit(long amt) {
        return accCon.setOverdraftLimit(amt);
    }
    
    //Sets the withdrawal limit for the account
    public long setWithdrawalLimit(long amt) {
        return accCon.setWithdrawalLimit(amt);
    }

    //Get the account number of the account
    public String getAccNo(){
        return accCon.getAccNo();
    }

    //Returns the balance of the of the current Account Object (Used for computation)
    //For display to user, please see printBalance()
    public long getBalance(){
        return accCon.getBalance();
    }

    //Returns the withdrawal limit of the of the current Account Object (Used for computation)
    //For display to user, please see printWithdrawalLimit()
    public long getWithdrawalLimit() {
        return accCon.getWithdrawalLimit();
    }

    //Returns the overdraft limit of the of the current Account Object (Used for computation)
    //For display to user, please see printOverdraftLimit()
    public long getOverdraftLimit() {
        return accCon.getOverdraftLimit();
    }

    //Sets the pin of the current account
    public String setPin(String pin) {
        return accCon.setPin(pin);
    }

    //Gets the pin of the current account
    public String getPin() {
        return accCon.getPin();
    }

    //*************************************VIEWS METHODS************************************************

    //Returns formatted tabular transaction statement
    public String printTransactionListing() throws IOException {
        return accCon.printTransactionListing();
    }

    //Returns formatted account overdraft limit for user consumption
    public String printOverdraftLimit() throws IOException{
        return accCon.printOverdraftLimit();
    }

    //Returns formatted account withdrawal limit for user consumption
    public String printWithdrawalLimit() throws IOException{
        return accCon.printWithdrawalLimit();
    }

    //Returns formatted account balance for user consumption
    public String printBalance() throws IOException {
        return accCon.printBalance();
    }

}
