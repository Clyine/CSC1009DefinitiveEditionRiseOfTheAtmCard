package model;
import java.util.LinkedList;

public class Account {

    private String name = "";                                                  //declare private name variable
    private String pin;                                                        // declare private pin variable
    private LinkedList<Transaction> transactionsList;                         //declare private linkedlist variable
    private long WithdrawalLimit = 0;                                          //declare private WithdrawalLimit variable
    private long runningBalance = 0;                                           //declare private runningBalance variable

    protected Account(String accNo, String pin) {                          //constuctor for account
        this.name = accNo;
        this.pin = pin;
        this.transactionsList = new LinkedList<Transaction>();
        this.runningBalance = 0l;
        this.WithdrawalLimit = 300000;
    }

    public String getAccNo(){                //getAccNo function to return acc no
        return this.name;
    }

    public long getBalance(){              //getBalance function to return balance
        return this.runningBalance;
    }

    public String getPin() {                 //getpin function to return pin
        return this.pin;
    }

    public String setPin(String pin){       //setpin funtion to set the pin
        return this.pin = pin;
    }

    public long getWithdrawalLimit(){       // getwithdrawllimit to return the withdrawallimit
        return this.WithdrawalLimit;
    }

    public long setWithdrawalLimit(long amt) {  //setWithdrawalLimit to return the set withdrawal limit
        this.WithdrawalLimit = amt;
        return this.WithdrawalLimit;
    }
    
    public void addTransaction(Transaction t) {     //addTransaction to add transaction to the transaction list 
        this.transactionsList.add(t);
        this.runningBalance += t.getDeposit() - t.getWithdraw();
    }

    //Helper code for getTransactionListing to convert Transaction objects within list to formatted String
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

    public String getTransactionListing(int n){     //Returns formatted Transaction list
        String str = "";
        
        str += String.format("+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
        str += String.format("\n|%-13s|%-12s|%-8s|%-60s|%-12s|%-12s|%-12s|", "Date", "ValueDate", "Cheque No", "Description", "Debit", "Credit", "Running Balance");
        str += String.format("\n+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
        str += (getTransactions(n));
        str += String.format("+-------------+------------+---------+------------------------------------------------------------+------------+------------+---------------+");
    
        return str;
    }     
}