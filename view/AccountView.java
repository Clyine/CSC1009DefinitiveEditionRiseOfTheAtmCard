package view;

import java.io.IOException;

/*
AccountView Class -
Class that takes in unformatted values from account objects, formats it into CLI strings to be sent to Terminal
*/
public abstract class AccountView {
    
    protected AccountView() {
    }

    //Takes in the (long) account balance (in cents) and convert it into dollars (double)
    //Returns formatted String for CLI display
    public String printBalance(long balance) throws IOException {
        String str = "Your current balance is $" + String.format("%.2f", ((double)balance/100));
        return str;
    }

    //Returns formatted tabular transaction statement for CLI display
    public String printTransactionListing(String str) throws IOException {
        return str;
    }

    //Takes in the (long) account withdrawal limits (in cents) and convert it into dollars (double)
    //Returns formatted String for CLI display
    public String printWithdrawalLimit(long balance) throws IOException {
        String str = "Your current withdrawal limit is $" + String.format("%.2f", ((double)balance/100));
        return str;
    }

    //Abstract methods takes in the (long) account overdraft limits (in cents) and convert it into dollars (double)
    //Returns formatted String for CLI display
    public abstract String printOverdraftLimit(long amt) throws IOException;
}
