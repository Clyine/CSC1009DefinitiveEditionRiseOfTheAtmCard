package controller;
import model.*;
import view.*;

import java.io.IOException;

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

    public Transaction addDeposit(long amt){
        return this.accCon.addDeposit(amt);
    } 

    public Transaction addWithdrawal(long amt){
        return this.accCon.addWithdrawal(amt);
    } 

    public Transaction addDeposit(long amt, String message){
        return this.accCon.addDeposit(amt, message);
    } 

    public Transaction addWithdrawal(long amt, String message){
        return this.accCon.addWithdrawal(amt, message);
    } 

    public Transaction addOpeningTransaction(){
        return this.accCon.addOpeningTransaction();
    }

    public Transaction addTransaction(Transaction t) {
        return this.accCon.addTransaction(t);
    }

    public long setOverdraftLimit(long amt) {
        return accCon.setOverdraftLimit(amt);
    }
    
    public long setWithdrawalLimit(long amt) {
        return accCon.setWithdrawalLimit(amt);
    }

    public String getAccNo(){
        return accCon.getAccNo();
    }

    public long getBalance(){
        return accCon.getBalance();
    }

    public long getWithdrawalLimit() {
        return accCon.getWithdrawalLimit();
    }

    public long getOverdraftLimit() {
        return accCon.getOverdraftLimit();
    }

    public String setPin(String pin) {
        return accCon.setPin(pin);
    }

    public String getPin() {
        return accCon.getPin();
    }

    public String printTransactionListing() throws IOException {
        return accCon.printTransactionListing();
    }

    public String printOverdraftLimit() throws IOException{
        return accCon.printOverdraftLimit();
    }

    public String printWithdrawalLimit() throws IOException{
        return accCon.printWithdrawalLimit();
    }

    public String printBalance() throws IOException {
        return accCon.printBalance();
    }

}
