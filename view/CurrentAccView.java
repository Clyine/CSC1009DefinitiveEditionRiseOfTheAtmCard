package view;

import java.io.IOException;


/*
CurrentAccView Class -
Class that takes in unformatted values from account objects, formats it into CLI strings to be sent to Terminal
Contains View methods unique to CurrentAcc object
*/
public class CurrentAccView extends AccountView{
    public CurrentAccView() {
        super();
    }

    //Takes in the (long) account overdraft limits (in cents) and convert it into dollars (double)
    //Returns formatted String for CLI display
    public String printOverdraftLimit(long amt) throws IOException {
        String str = "The current overdraft limit is : $" + String.format("%.2f", (double)amt/100);
        return str;
    }
}
