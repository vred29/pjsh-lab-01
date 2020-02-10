package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Map;

public class SavingAccount extends AbstractAccount
{

    public SavingAccount(float initialBalance) throws IllegalArgumentException
    {
        if (initialBalance >= 0)
        {
            balance = initialBalance;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException
    {
        if (balance >= x)
        {
            balance -= x;
            System.out.println("Successful withdrawal from Saving account");
        }
        else
        {
            throw new NotEnoughFundsException(x);
        }
    }

    public String getAccountName()
    {
        return "Saving Account";
    }

    @Override
    public void parseFeed(Map<String, String> map)
    {
        super.parseFeed(map);
    }
}
