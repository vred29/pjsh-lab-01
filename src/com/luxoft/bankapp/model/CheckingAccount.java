package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.service.feed.Feed;

import java.util.Map;

public class CheckingAccount extends AbstractAccount
{
    @Feed
    private double overdraft = 0;

    public CheckingAccount(double overdraft)
    {
        super(AccountType.CHECKING);

        setOverdraft(overdraft);
    }

    public void setOverdraft(double amount)
    {
        if (overdraft < 0)
        {
            return;
        }

        overdraft = amount;
    }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException
    {
        if (getBalance() + overdraft < amount)
        {
            throw new OverDraftLimitExceededException(
                    getType().toString(), getBalance() + overdraft);
        }

        setBalance(getBalance() - amount);

        System.out.println("Successful withdrawal from checking account");
    }

    // TODO feed
    @Override
    public void parseFeed(Map<String, String> map)
    {
        super.parseFeed(map);
        try
        {
            String overdraft = map.get("overdraft");
            this.overdraft = Float.parseFloat(overdraft != null ? overdraft : "");
        }
        catch (NumberFormatException ignore)
        {

        }
    }
}
