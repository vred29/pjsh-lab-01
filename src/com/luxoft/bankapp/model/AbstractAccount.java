package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.feed.Feed;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractAccount implements Account, Serializable
{
    protected float balance;

    @Override
    public void deposit(float x) throws IllegalArgumentException
    {
        if (x >= 0)
        {
            balance += x;
            System.out.println("Successful deposit operation");
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Feed("BALANCE")
    public float getBalance()
    {
        return balance;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AbstractAccount that = (AbstractAccount) o;

        return Float.compare(that.balance, balance) == 0;

    }

    public int decimalValue()
    {
        return Math.round(getBalance());
    }

    @Override
    public int hashCode()
    {
        return (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder
                .append("\n")
                .append(getAccountName())
                .append("\n\tbalance = ")
                .append(decimalValue());
        return builder.toString();
    }

    public void parseFeed(Map<String, String> map)
    {
        try
        {
            String balance = map.get("balance");
            this.balance = Float.parseFloat(balance != null ? balance : "");
        }
        catch (NumberFormatException ignore)
        {

        }
    }
}
