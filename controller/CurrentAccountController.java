package controller;
import java.io.IOException;

import model.CurrentAcc;
import view.CurrentAccView;

/*
CurrentAccountController Class - 
An abstract class of the object used to interact with the CurrentAcc objects (models)
Methods used for graphical interface for ATM terminal is called from the CurrentAccView object (view)

The constructor is protected and is can only be initailised by the classes within the Controller package
*/
public class CurrentAccountController extends AccountController{

    private final CurrentAcc model = (CurrentAcc) super.model;
    private final CurrentAccView view = (CurrentAccView) super.view;

    protected CurrentAccountController(CurrentAcc model, CurrentAccView view) {
        super(model, view);
    }

    //Returns the overdraft limit of the of the current Account Object (Used for computation)
    //For display to user, please see printOverdraftLimit()
    public long getOverdraftLimit() {
        return this.model.getOverdraftLimit();
    }

    //Set the overdraft limit for the current Account object
    public long setOverdraftLimit(long amt) {
        return this.model.setOverdraftLimit(amt);
    }

    //*************************************VIEWS METHODS************************************************

    //Returns formatted account overdraft limit for user consumption
    public String printOverdraftLimit() throws IOException {
        return view.printOverdraftLimit(getOverdraftLimit());
    }

}