package model;

public class CurrentAcc extends Account{
    private long overdraftLimit = 300000;   //private overdraftLimit variable

    public CurrentAcc(String accNo, String pin) {
        super(accNo, pin);           //calling the account parent
    }

    public long getOverdraftLimit(){
        return -this.overdraftLimit;          //return overdraftlimit
    }

    public long setOverdraftLimit(long limit){    // set the overdraft limit
        this.overdraftLimit = limit;
        return -this.overdraftLimit;
    }

}
