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
            Scanner scan = new Scanner(new File("Ledger.csv"));
            // skip header
            scan.nextLine();
            // iterate Line by Line
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(",");
                String accountNum = lineScan.next();
                String transDate = lineScan.next();
                String details = lineScan.next();
                String chqNum = lineScan.next();
                String valueDate = lineScan.next();
                long wAmt = 0, dAmt = 0;
                try {
                    wAmt = (long) (Double.parseDouble(lineScan.next()) * 100);
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    dAmt = (long) (Double.parseDouble(lineScan.next()) * 100);
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
                    SystemController SysCon = new SystemController(newAcc);
                    Transaction newT = new Transaction(transDate, valueDate, chqNum, details, wAmt, dAmt, runningBal - wAmt + dAmt);
                    SysCon.addTransaction(newT);
                    data.put(accountNum, newAcc);
                } else {
                    SystemController SysCon = new SystemController(data.get(accountNum));
                    runningBal = SysCon.getBalance();
                    Transaction newT = new Transaction(transDate, valueDate, chqNum, details, wAmt, dAmt, runningBal - wAmt + dAmt);
                    SysCon.addTransaction(newT);
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
                String line = scan.nextLine();
                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(",");
                String accountNum = lineScan.next();
                String pin = lineScan.next();
                long wLimit = 0, oLimit = 0;
                try {
                    wLimit = (long) (Double.parseDouble(lineScan.next()) * 100);
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    oLimit = (long) (Double.parseDouble(lineScan.next()) * 100);
                } catch (Exception e) {
                    // do nothing
                }
                // close line scanner
                lineScan.close();

                System.out.println(accountNum);
                System.out.println(data.keySet());
                if (data.get(accountNum) == null) {
                    throw new Exception("Account number not initialised");
                }

                if (accountNum.charAt(0) == '4') {
                    SystemController SysCon = new SystemController(data.get(accountNum));
                    SysCon.setWithdrawalLimit(wLimit);
                    SysCon.setPin(pin);
                }

                if (accountNum.charAt(0) == '8') {
                    SystemController SysCon = new SystemController(data.get(accountNum));
                    SysCon.setWithdrawalLimit(wLimit);
                    SysCon.setOverdraftLimit(oLimit);
                    SysCon.setPin(pin);
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

    public boolean writeLedger(SystemController controller, Transaction t) {
        String str = "";
        String csvFilename = "Ledger.csv";

        str += controller.getAccNo() + ",";
        str += t.getTransactionDate() + ",";
        str += t.getDescription() + ",";
        str += t.getChequeNo() + ",";
        str += t.getValueDate() + ",";
        str += String.format("%.2f", (double) t.getWithdraw() / 100) + ",";
        str += String.format("%.2f", (double) t.getDeposit() / 100) + ",";
        str += String.format("%.2f", (double) t.getRunningBalance() / 100)+ ",";

        try {
            FileWriter fw = new FileWriter(csvFilename, true);
            fw.append("\n");
            fw.append(str);
            fw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            return false;
        }
    }

    public boolean writeNewAcc(String accountNum) {
        String str = "";
        String csvFilename = "Ledger.csv";

        str += accountNum + ",";
        str += dateFormat.format(new Date()) + ",New user Account,,,,,,";
        try {
            FileWriter fw = new FileWriter(csvFilename, true);
            fw.append("\n");
            fw.append(str);
            fw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            return false;
        }

    }

    public boolean writeHeader(SystemController controller) {
        String str = "";
        String csvFilename = "AccountHeaders.csv";

        str += controller.getAccNo() + ",";
        str += controller.getPin() + ",";
        str += String.format("%.2f", (double) controller.getWithdrawalLimit() / 100)+ ",";
        str += String.format("%.2f", (double) controller.getOverdraftLimit() / 100 * -1)+ ",";

        try {
            FileWriter fw = new FileWriter(csvFilename, true);
            fw.append("\n");
            fw.append(str);
            fw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            return false;
        }
    }
}
