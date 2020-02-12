package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.feed.Feed;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Client implements Identifiable, Serializable
{
    private Long id;

    @Feed("NAME")
    private String name;

    @Feed("ACCOUNTS")
    private Set<Account> accounts = new HashSet<>(2);

    private Account activeAccount;

    @Feed("GENDER")
    private Gender gender;

    @Feed("CITY")
    private String city;

    public Client(String name)
    {
        this(name, Gender.UNDEFINED);
    }

    public Client(String name, Gender gender)
    {
        this.name = name;
        this.gender = gender;
    }

    public synchronized double getBalance() throws ActiveAccountNotSet
    {
        if (!checkIfActiveAccountSet())
        {
            throw new ActiveAccountNotSet();
        }
        return activeAccount.getBalance();
    }

    public synchronized void deposit(float x) throws IllegalArgumentException, ActiveAccountNotSet
    {
        if (!checkIfActiveAccountSet())
        {
            throw new ActiveAccountNotSet();
        }
        activeAccount.deposit(x);
    }

    public synchronized void withdraw(float x) throws NotEnoughFundsException, ActiveAccountNotSet
    {
        if (!checkIfActiveAccountSet())
        {
            throw new ActiveAccountNotSet();
        }
        activeAccount.withdraw(x);
    }

    public void removeAccount(AccountType type)
    {
        accounts = accounts.stream()
                .filter(a -> a.getType() != type)
                .collect(Collectors.toSet());
    }


    public Set<Account> getAccounts()
    {
        return Collections.unmodifiableSet(accounts);
    }

    public Account getAccount(String type)
    {
        Account account = getAccountSafe(type);
        if (account == null)
        {
            throw new AccountNotFoundException();
        }
        return account;
    }

    public Account getAccountSafe(String type)
    {
        for (Account account : accounts)
        {
            if (account.getType().equals(type))
            {
                return account;
            }
        }
        return null;
    }

    public void addAccount(Account account) throws AccountNumberLimitException
    {
        if (accounts.size() >= 2)
        {
            throw new AccountNumberLimitException();
        }
        if (account != null)
        {
            accounts.add(account);
        }
    }

    public void setDefaultActiveAccountIfNotSet()
    {
        if (activeAccount == null && accounts != null && !accounts.isEmpty())
        {
            activeAccount = accounts.iterator().next();
        }
    }

    public void parseFeed(Map<String, String> map)
    {
        this.city = map.get("city");
        this.gender = parseGender(map.get("gender"));
        Account account = getAccountSafe(map.get("accountType"));
        if (account == null)
        {
            Account created = createAccount(map.get("accountType"));
            addAccount(created);

            account = created;
        }
        account.parseFeed(map);
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
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = getSimpleInfoBuilder();
        builder.append("\nAccounts:");
        for (Account account : accounts)
        {
            builder.append(account.toString());
        }
        builder.append("\nActive account: ");
        if (checkIfActiveAccountSet())
        {
            builder.append(activeAccount.getType());
        }
        else
        {
            builder.append("not set");
        }
        return builder.toString();
    }

    @Override
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Account getActiveAccount()
    {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount)
    {
        this.activeAccount = activeAccount;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public enum Gender
    {
        MALE("Mr"), FEMALE("Ms"), UNDEFINED("");

        private String prefix;

        String getSalutation()
        {
            return prefix;
        }

        Gender(String prefix)
        {
            this.prefix = prefix;
        }
    }
}
