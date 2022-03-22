package model;
import java.util.LinkedList;

public class Account {
    private String name = "";
    private String pin;
    private LinkedList<Transaction> transactionsList;
    private long WithdrawalLimit = 0;
    private long runningBalance = 0;

    protected Account(String accNo, String pin) {
        this.name = accNo;
        this.pin = pin;
        this.transactionsList = new LinkedList<Transaction>();
        this.runningBalance = 0l;
        this.WithdrawalLimit = 300000;
    }

    public String getAccNo(){
        return this.name;
    }

    public long getBalance(){
        return this.runningBalance;
    }

    public String getPin() {
        return this.pin;
    }

    public String setPin(String pin){
        return this.pin = pin;
    }

    public long getWithdrawalLimit(){
        return this.WithdrawalLimit;
    }

    public long setWithdrawalLimit(long amt) {
        this.WithdrawalLimit = amt;
        return this.WithdrawalLimit;
    }
    
    public void addTransaction(Transaction t) {
        this.transactionsList.add(t);
        this.runningBalance += t.getDeposit() - t.getWithdraw();
    }

    private String getTransactions(int n){
        String str = "\n";
        if (transactionsList.size() >= n ) {
            for (int i = transactionsList.size()-n; i < transactionsList.size() ; i++){
                str += transactionsList.get(i).toString() + "\n"; 
            }
        }
        else {
            for (int i = 0; i < transactionsList.size() ; i++){
                str += transactionsList.get(i).toString() + "\n"; 
            }
        }

        return str;
    }

    public String getTransactionListing(int n){
        String str = "";
        
        str += String.format("+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
        str += String.format("\n|%-13s|%-12s|%-8s|%-60s|%-12s|%-12s|%-12s|", "Date", "ValueDate", "Cheque No", "Description", "Debit", "Credit", "Running Balance");
        str += String.format("\n+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
        str += (getTransactions(n));
        str += String.format("+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
    
        return str;
    }     
}