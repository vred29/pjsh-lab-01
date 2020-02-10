package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService
{
    void addClient(Bank bank, Client client)
            throws ClientExistsException;

    void removeClient(Bank bank, Client client);

    void addAccount(Client client, Account account) throws AccountNumberLimitException;

    void setActiveAccount(Client client, Account account);

    Client getClient(Bank bank, String clientName) throws ClientNotFoundException;

    void saveClient(Client client);

    Client loadClient(String path);

    void populateBankData(Bank bank);

}
