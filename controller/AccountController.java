package controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.*;
import view.AccountView;

/*
AccountController Class - 
An abstract class of the object used to interact with the Account objects (models)
Methods used for graphical interface for ATM terminal is called from the AccountView object (view)

The constructor is protected and is can only be initailised by the classes within the Controller package
*/
public abstract class AccountController {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected Account model;
    protected AccountView view;

    protected AccountController(Account model, AccountView view) {
        this.model = model;
        this.view = view;
    }

    //Returns the account number of the current Account Object
    public String getAccNo(){
        return model.getAccNo();
    }

    //Returns the balance of the of the current Account Object (Used for computation)
    //For display to user, please see printBalance()
    public long getBalance(){
        return model.getBalance();
    }

    //Creates a new transaction of a given amount and add said transaction into Account as a Deposit 
    public Transaction addDeposit(long amt) {
        Transaction newT = new Transaction(dateFormat.format(new Date()), dateFormat.format(new Date()), "", "New Deposit", 0l, amt, model.getBalance() + amt);
        model.addTransaction(newT);
        return newT;
    }

    //Creates a new transaction of a given amount and add said transaction into Account as a Deposit with a custom description
    //Used for Transfer activity to include accounts involved
    public Transaction addDeposit(long amt, String message) {
        Transaction newT = new Transaction(dateFormat.format(new Date()), dateFormat.format(new Date()), "", message, 0l, amt, model.getBalance() + amt);
        model.addTransaction(newT);
        return newT;
    }

    //Creates a new transaction of a given amount and add said transaction into Account as a Withdrawal 
    public Transaction addWithdrawal(long amt) {
        Transaction newT = new Transaction(dateFormat.format(new Date()), dateFormat.format(new Date()), "", "New Withdrawal", amt, 0l, model.getBalance() - amt);
        model.addTransaction(newT);
        return newT;
    }

    //Creates a new transaction of a given amount and add said transaction into Account as a withdrawal with a custom description
    //Used for Transfer activity to include accounts involved
    public Transaction addWithdrawal(long amt,String message) {
        Transaction newT = new Transaction(dateFormat.format(new Date()), dateFormat.format(new Date()), "", message, amt, 0l, model.getBalance() - amt);
        model.addTransaction(newT);
        return newT;
    }

    //Create a new empty transaction to be added upon creation of a new account
    public Transaction addOpeningTransaction() {
        Transaction newT = new Transaction(dateFormat.format(new Date()), dateFormat.format(new Date()), "", "Account Opened", 0l, 0l, model.getBalance());
        model.addTransaction(newT);
        return newT;
    }

    //Adds a defined transaction object to account.
    //Transaction data is extracted from database on Server initialisation
    public Transaction addTransaction(Transaction t) {
        model.addTransaction(t);
        return t;
    }

    public String setPin(String pin) {
        return model.setPin(pin);
    }

    //Get the pin of the current account object
    public String getPin() {
        return model.getPin();
    }

    //Sets the current withdrawal limit of the account
    public long setWithdrawalLimit(long amt) {
        return model.setWithdrawalLimit(amt);
    }

    //Get the current withdrawal limit of the account
    public long getWithdrawalLimit() {
        return model.getWithdrawalLimit();
    }

    //Abstract methods for overdraft settings
    //Implemented in the Child Controller object (i.e. Savings Accounts do not have overdraft option)
    public abstract long getOverdraftLimit();
    public abstract long setOverdraftLimit(long amt);


    //*************************************VIEWS METHODS************************************************

    //Returns formatted tabular transaction statement
    public String printTransactionListing() throws IOException {
        String str = model.getTransactionListing(20);
        return view.printTransactionListing(str);
    }

    //Returns formatted account balance for user consumption
    public String printBalance() throws IOException {
        return view.printBalance(getBalance());
    }

    //Returns formatted account withdrawal limit for user consumption
    public String printWithdrawalLimit() throws IOException {
        return view.printWithdrawalLimit(getWithdrawalLimit());
    }

    //Abstract methods for formatted account overdraft information for user consumption
    public abstract String printOverdraftLimit() throws IOException;



}
