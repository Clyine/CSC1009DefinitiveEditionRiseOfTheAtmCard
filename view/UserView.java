package view;

import java.util.concurrent.ConcurrentHashMap;
import model.*;

public class UserView {

    public UserView() {

    }

    public String printAccList(ConcurrentHashMap<String, Account> AccList) {
        Object[] currList = AccList.keySet().toArray(); //get List of accounts from User Object
        String out = "\n"; //set account list output format
        for (int i = 0; i < AccList.values().size(); i++){
            out += String.format("%2d",(i+1)) + ") " + currList[i] + "\n";
        }
        return (out + "\nPlease select account: "); //generate account list menu
    }
}
