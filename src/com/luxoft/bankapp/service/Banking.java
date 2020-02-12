package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.Storage;

import java.util.Set;

public interface Banking
{
    void setStorage(Storage<Client> storage);

    void addClient(Client c);

    Client getClient(String name);

    Set<Client> getClients();

    void removeClient(Client c);

    void addAccount(Client c, Account account);

    void updateAccount(Client c, Account account);

    Account getAccount(Client c, AccountType type);

    Set<Account> getAllAccounts();

    void removeAccount(Client c, AccountType type);
}
