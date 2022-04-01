package model;

import java.util.concurrent.ConcurrentHashMap;

public class User {
    private String username, pin;
    private ConcurrentHashMap<String, Account> accountList;

    public User(String username, String pin) {
        this.username = username;
        this.pin = pin;
        this.accountList = new ConcurrentHashMap<String, Account>();
    }

    public String getUsername() {
        return this.username;   // return username
    }

    public String getPin() {
        return this.pin;        // return pin
    }
    
    public void setPin(String pin) {
        this.pin = pin;         // set pin to the passed in value
    }

    public ConcurrentHashMap<String, Account> getAccountList(){
        return this.accountList;    // return concurrenthashmap
    }

    public String addAcc(String accNo, Account acc) {
        accountList.put(accNo, acc);    // add data into hash map
        return accNo;                   // return accNo
    }

    public String addCurrentAcc(String accNo, Account acc) {
        accountList.put(accNo, acc);    // add data into hashmap
        return accNo;                   // return accno
    }
}
