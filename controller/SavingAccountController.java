package controller;

import java.io.IOException;

import model.SavingsAcc;
import view.SavingAccView;

/*
SavingAccountController Class - 
An abstract class of the object used to interact with the SavingsAcc objects (models)
Methods used for graphical interface for ATM terminal is called from the SavingAccView object (view)

The constructor is protected and is can only be initailised by the classes within the Controller package
*/
public class SavingAccountController extends AccountController{

    private final SavingsAcc account = (SavingsAcc) super.model;
    private final SavingAccView view = (SavingAccView) super.view;

    protected SavingAccountController(SavingsAcc account, SavingAccView view) {
        super(account, view);
    }

    //Returns formatted string of account overdraft limit
    public String printOverdraftLimit() throws IOException{
        return view.printOverdraftLimit(getOverdraftLimit());
    }


    //Savings accounts does not allow overdraft limits. Getters and setters cannot be accessed by user or programs.
    //Validation will be perform at user input at Terminal
    public long getOverdraftLimit() {
        return 0;
    }

    public long setOverdraftLimit(long amt) {
        return 0;
    }
}
