package model;

import java.io.FileWriter;

public class Transaction {
    private String transactionDate;
    private String valueDate;
    private String chequeNo;
    private String description;
    private long withdraw;
    private long deposit;
    private long runningBalance;

    public Transaction(String transDate, String valueDate, String chq, String desc, long withdraw, long deposit, long runningBalance){
        this.transactionDate = transDate;
        this.valueDate = valueDate;
        this.chequeNo = chq;
        this.description = desc;
        this.withdraw = withdraw;
        this.deposit = deposit;
        this.runningBalance = runningBalance;
    }

    @Override
    public String toString(){
        String s = String.format("|%1$s   |%2$s  |%3$-9s|%4$-60s|%5$12.2f|%6$12.2f|%7$15.2f|", this.transactionDate, this.valueDate, this.chequeNo, this.description, (double)this.withdraw/100, (double)this.deposit/100, (double)this.runningBalance/100);

        return s;
    }


    public String getTransactionDate() {
        return this.transactionDate;          //return the transaction date
    }

    public String getValueDate() {
        return this.valueDate;                //return the value date
    }

    public String getChequeNo() {
        return this.chequeNo;               //return the cheque no
    }

    public String getDescription() {
        return this.description;           //return the description 
    }

    public long getWithdraw() {
        return this.withdraw;                  //return the withdraw
    }

    public long getDeposit() {
        return this.deposit;                    //return the deposit
    }

    public long getRunningBalance() {
        return this.runningBalance;            //return the running balance
    }


    public void exportTransaction(String accNo){
        String str = "";                    //Create string str variable
        String csvFilename = "bankTransactions.csv";    //Set the excel file name

        str += accNo + ",";        // Retrieve account number and add to string, append a comma at the end
        str += this.transactionDate + ",";   // Retrieve transaction date and add to string, append a comma at the end
        str += this.description + ",";           // Retrieve account description and add to string, append a comma at the end
        str += this.chequeNo + ",";                  // Retrieve cheque number and add to string, append a comma at the end
        str += this.valueDate + ",";                    // Retrieve value date and add to string, append a comma at the end
        str += String.format("%.2f", (double)this.withdraw/100) + ",";  // Retrieve withdraw amount and add to string, append a comma at the end
        str += String.format("%.2f", (double)this.deposit/100) + ",";    // Retrieve deposit amount and add to string, append a comma at the end
        str += String.format("%.2f", (double)this.runningBalance/100);  // Retrieve deposit amount and add to string, append a comma at the end

        try {
            FileWriter fw = new FileWriter(csvFilename,true);      // Set new file writer object
            fw.append("\n");                                        // Add next line to end of object
            fw.append(str);                                            // Add the strings declared above
            fw.close();                                                 // Close the file writer object
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());            // Throw exception message
        }
    }

}
