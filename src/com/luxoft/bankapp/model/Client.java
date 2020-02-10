package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.feed.Feed;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    @Feed("NAME")
    private String name;
    @Feed("ACCOUNTS")
    private Set<Account> accounts = new HashSet<>();

    private Account activeAccount;
    private float initialOverdraft;
    private float initialBalance;
    private Gender gender;
    @Feed("CITY")
    private String city;

    public enum Gender {
        MALE("Mr"), FEMALE("Ms"), UNDEFINED("");

        private String prefix;

        String getSalutation() {
            return prefix;
        }

        Gender(String prefix) {
            this.prefix = prefix;
        }
    }

    @Feed("GENDER")
    public String getGender() {
        switch (gender) {
            case MALE: return "m";
            case FEMALE: return "f";
            default: return "u";
        }
    }

    public Client(String name, float initialOverdraft, Gender gender) {
        this.name = name;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;
    }

    public Client(String name, float initialOverdraft) {
        this(name, initialOverdraft, Gender.UNDEFINED);
    }

    public Client(String name) {
        this(name, 0, Gender.UNDEFINED);
    }

    public Client(String name, Gender gender) {
        this(name, 0, gender);
    }

    public synchronized float getBalance() throws ActiveAccountNotSet {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet();
        }
        return activeAccount.getBalance();
    }

    public synchronized void deposit(float x) throws IllegalArgumentException, ActiveAccountNotSet {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet();
        }
        activeAccount.deposit(x);
    }

    public synchronized void withdraw(float x) throws NotEnoughFundsException, ActiveAccountNotSet {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet();
        }
        activeAccount.withdraw(x);
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public Account getAccount(String type) {
        Account account = getAccountSafe(type);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    public Account getAccountSafe(String type) {
        for (Account account : accounts) {
            if (account.getAccountName().equals(type)) {
                return account;
            }
        }
        return null;
    }

    public void addAccount(Account account) throws AccountNumberLimitException {
        if (accounts.size() >= 2) {
            throw new AccountNumberLimitException();
        }
        if (account != null) {
            accounts.add(account);
        }
    }

    public Account createAccount(String accountType)
    {
        System.out.println("accountType: " + accountType);
        if (accountType.toLowerCase().startsWith("s"))
        {
            return new SavingAccount(initialBalance);
        }
        return new CheckingAccount(initialOverdraft);
    }

    public boolean checkIfActiveAccountSet() {
        return activeAccount != null;
    }

    public void setActiveAccountIfNotSet() {
        if (activeAccount != null || accounts == null || accounts.isEmpty()) {
            return;
        }
        Iterator<Account> iterator = getAccounts().iterator();
        if (iterator.hasNext()) {
            Account next = iterator.next();
            if (next != null) {
                activeAccount = next;
                return;
            }
        }
    }

    private StringBuilder getSimpleInfoBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nClient: ")
                .append(name)
                .append("\nGender: ")
                .append(getGender());
        return builder;
    }

    public String getSimpleNameInfo() {
        return getSimpleInfoBuilder().toString();
    }

    public void parseFeed(Map<String, String> map) {
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

    public static Client.Gender parseGender(String string) {
        if (string == null) {
            return Client.Gender.UNDEFINED;
        }
        if (string.equalsIgnoreCase("m") || string.equalsIgnoreCase("male")) {
            return Client.Gender.MALE;
        }
        if (string.equalsIgnoreCase("f") || string.equalsIgnoreCase("female")) {
            return Client.Gender.FEMALE;
        }
        return Client.Gender.UNDEFINED;
    }

    public String getClientSalutation() {
        return gender.getSalutation();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(float initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return name != null ? name.equals(client.name) : client.name == null;
    }

    @Override
    public String toString() {
        StringBuilder builder = getSimpleInfoBuilder();
        builder.append("\nAccounts:");
        for (Account account : accounts) {
            builder.append(account.toString());
        }
        builder.append("\nActive account: ");
        if (checkIfActiveAccountSet()) {
            builder.append(activeAccount.getAccountName());
        } else {
            builder.append("not set");
        }
        return builder.toString();
    }
}
