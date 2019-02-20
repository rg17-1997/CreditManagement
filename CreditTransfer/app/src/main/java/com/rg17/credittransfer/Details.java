package com.rg17.credittransfer;

/**
 * Created by Win8 on 4/21/2018.
 */


public class Details
{
    String firstName;
    String lastName;
    int currentCredit;

    public Details(String firstName, String lastName, int currentCredit)
    {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCurrentCredit(currentCredit);
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public int getCurrentCredit()
    {
        return currentCredit;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setCurrentCredit(int currentCredit)
    {
        this.currentCredit = currentCredit;
    }

}
