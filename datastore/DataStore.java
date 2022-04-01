package datastore;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.*;
import view.*;
import model.*;

public class DataStore {

    private ConcurrentHashMap<String, Account> data;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DataStore() {
        this.data = new ConcurrentHashMap<String, Account>();
        initData(data);
        initHeader(data);
    }

    public ConcurrentHashMap<String, Account> getDataStore() {
        return this.data;
    }

    public Account getAccount(String accNo) {
        return this.data.get(accNo);
    }

    public boolean initData(ConcurrentHashMap<String, Account> data) {
        try {
            Scanner scan = new Scanner(new File("Ledger.csv"));         // Loads the csv into scanner
            // skip header
            scan.nextLine();                                            // Take in user input
            while (scan.hasNextLine()) {                                // Iterate line by line
                String line = scan.nextLine();                          // Scans the first line and put the data into variable line
                Scanner lineScan = new Scanner(line);                   // Stores the scanned data into a scanner
                lineScan.useDelimiter(",");                             // Split string by delimiter
                String accountNum = lineScan.next();                    // Store the first delimited value into accountNum
                String transDate = lineScan.next();                     // Store the next delimited value into transDate
                String details = lineScan.next();                       // Store the next delimited value into details
                String chqNum = lineScan.next();                        // Store the next delimited value into chqNum
                String valueDate = lineScan.next();                     // Store the next delimited value into valueDate
                long wAmt = 0, dAmt = 0;                                // Set variables wAmt and dAmt to 0
                try {
                    wAmt = (long) (Double.parseDouble(lineScan.next()) * 100);  //Store the value as a double into wAmt
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    dAmt = (long) (Double.parseDouble(lineScan.next()) * 100);  //Store the value as a double into dAmt
                } catch (Exception e) {
                    // do nothing
                }
                // skips balance amt
                lineScan.next();
                // close line scanner
                lineScan.close();
                long runningBal = 0;
                // If current user is not in memory
                if (data.get(accountNum) == null) {
                    Account newAcc;
                    if (accountNum.charAt(0) == '4') {
                        newAcc = new SavingsAcc(accountNum,"");
                    } else {
                        newAcc = new CurrentAcc(accountNum,"");
                    }
                    SystemController SysCon = new SystemController(newAcc);     // Create a new object with SystemController class and passing in an object
                    Transaction newT = new Transaction(transDate, valueDate, chqNum, details, wAmt, dAmt, runningBal - wAmt + dAmt);    // Creating a new object
                    SysCon.addTransaction(newT);            // Add object into SystemController object
                    data.put(accountNum, newAcc);           // Put data into ConcurrentHashMap
                } else {
                    SystemController SysCon = new SystemController(data.get(accountNum)); //Create new object with SystemController class and passing in an object accounNum
                    runningBal = SysCon.getBalance();       // get balance and store in runningBal
                    Transaction newT = new Transaction(transDate, valueDate, chqNum, details, wAmt, dAmt, runningBal - wAmt + dAmt); // Creating new object
                    SysCon.addTransaction(newT);            // Add object into SystemController object
                }
            }
            // close file scanner
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean initHeader(ConcurrentHashMap<String, Account> data) {
        try {
            Scanner scan = new Scanner(new File("AccountHeaders.csv"));
            // skip header
            scan.nextLine();
            // iterate Line by Line
            while (scan.hasNextLine()) {
                String line = scan.nextLine();                          // Store the data into variable line
                Scanner lineScan = new Scanner(line);                   // Pass the data into a scanner
                lineScan.useDelimiter(",");                             // Splitting the data by delimiter
                String accountNum = lineScan.next();                    // Storing the first delimited value into accountNum
                String pin = lineScan.next();                           // Storing the next delimited value into pin
                long wLimit = 0, oLimit = 0;                            // Reset the value of wLimit and oLimit to 0
                try {
                    wLimit = (long) (Double.parseDouble(lineScan.next()) * 100); // Store the next delimited value into wLimit
                } catch (Exception e) {                                          // Catch any exception
                    // do nothing
                }
                try {
                    oLimit = (long) (Double.parseDouble(lineScan.next()) * 100);    // Store the next delimited value into oLimit
                } catch (Exception e) {                                             // Catch any exception
                    // do nothing
                }
                // close line scanner
                lineScan.close();

                //System.out.println(accountNum);
                //System.out.println(data.keySet());
                if (data.get(accountNum) == null) {
                    throw new Exception("Account number not initialised");  // Throw exception to tell user the input account number is not initialised
                }

                if (accountNum.charAt(0) == '4') {
                    SystemController SysCon = new SystemController(data.get(accountNum));   // Create a new object with SystemController class and passing in an object
                    SysCon.setWithdrawalLimit(wLimit);                                      // Set Withdrawal limit
                    SysCon.setPin(pin);                                                     // Set pin
                }

                if (accountNum.charAt(0) == '8') {
                    SystemController SysCon = new SystemController(data.get(accountNum));   // Create a new object with SystemController class and passing in an object
                    SysCon.setWithdrawalLimit(wLimit);                                      // Set withdrawal limit
                    SysCon.setOverdraftLimit(oLimit);                                       // Set overdraft limit
                    SysCon.setPin(pin);                                                     // Set pin
                }
            }
            // close file scanner
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();                                                            // Throw exception
            return false;
        }
        return true;
    }

    public boolean writeLedger(SystemController controller, Transaction t) {
        String str = "";                                                            // Set String str
        String csvFilename = "Ledger.csv";                                          // Set csvFilename to output file, Ledger.csv

        str += controller.getAccNo() + ",";                                         // Retrieve account number and add to string, append a comma at the end
        str += t.getTransactionDate() + ",";                                        // Retrieve transaction date and add to string, append a comma at the end
        str += t.getDescription() + ",";                                            // Retrieve account description and add to string, append a comma at the end
        str += t.getChequeNo() + ",";                                               // Retrieve cheque number and add to string, append a comma at the end
        str += t.getValueDate() + ",";                                              // Retrieve value date and add to string, append a comma at the end
        str += String.format("%.2f", (double) t.getWithdraw() / 100) + ",";         // Retrieve withdraw amount and add to string, append a comma at the end
        str += String.format("%.2f", (double) t.getDeposit() / 100) + ",";          // Retrieve deposit amount and add to string, append a comma at the end
        str += String.format("%.2f", (double) t.getRunningBalance() / 100)+ ",";    // Retrieve deposit amount and add to string, append a comma at the end

        try {
            FileWriter fw = new FileWriter(csvFilename, true);                      // Set new file writer object
            fw.append("\n");                                                        // Add next line to end of object
            fw.append(str);                                                         // Add the strings declared above
            fw.close();                                                             // Close the file writer object
            return true;                                                            // Return true value
        } catch (Exception e) {                                                      
            System.out.println("Exception:" + e.getMessage());                      // Throw exception message
            return false;                                                           // Return false
        }
    }
    
    public boolean writeNewAcc(String accountNum) {                             
        String str = "";                                                        // Set String str
        String csvFilename = "Ledger.csv";                                      // Set csvFilename to output file, Ledger.csv
        
        str += accountNum + ",";                                                // Retrieve account number and add to string, append a comma at the end
        str += dateFormat.format(new Date()) + ",New user Account,,,,,,";       // Add in a new date into the string
        try {
            FileWriter fw = new FileWriter(csvFilename, true);                  // Initialise a file writer
            fw.append("\n");                                                    // Add a next line to the end of object
            fw.append(str);                                                     // Add in the data from variable str
            fw.close();                                                         // Close the filewrite
            return true;                                                        // Return true value
        } catch (Exception e) {                                             
            System.out.println("Exception:" + e.getMessage());                  // Print out exception error message
            return false;                                                       // Return false value
        }

    }

    public boolean writeHeader(SystemController controller) {
        String str = "";                                                        // Set String str 
        String csvFilename = "AccountHeaders.csv";                              // Set csvFilename to output file, AccountHeaders.csv

        str += controller.getAccNo() + ",";                                     // Retrieve Account number and add to string, append a comma at the end
        str += controller.getPin() + ",";                                       // Retrieve pin and add to string, append a comma at the end
        str += String.format("%.2f", (double)(controller.getWithdrawalLimit())/100)+ ",";   // Retrieve withdrawal limit and add to string, append a comma at the end
        str += String.format("%.2f", (double)(controller.getOverdraftLimit())/100*-1)+ ","; // Retrieve overdraft limit and add to string, append a comma at the end

        try {
            FileWriter fw = new FileWriter(csvFilename, true);              // Create a new file writer object
            fw.append("\n");                                                // Add a new line
            fw.append(str);                                                 // Add the string declared above to the object
            fw.close();                                                     // Close the filewriter
            return true;                                                    // Return the true value
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());              // Print out exception message
            return false;                                                   // Return the false value
        }
    }
}