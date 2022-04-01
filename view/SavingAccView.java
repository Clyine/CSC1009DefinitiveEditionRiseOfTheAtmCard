package view;


/*
SavingAccView Class -
Class that takes in unformatted values from account objects, formats it into CLI strings to be sent to Terminal
Contains View methods unique to SavingAcc object
*/
public class SavingAccView extends AccountView {
    public SavingAccView() {
        super();
    }

    //Returns formatted message for non-availablitity of Overdraft settings
    public String printOverdraftLimit(long amt){
        return "This is a savings account. Overdraft is not available.";
    }
    
}
