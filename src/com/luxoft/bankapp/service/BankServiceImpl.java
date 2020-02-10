package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankServiceImpl implements BankService
{
    @Override
    public void addClient(Bank bank, Client client)
            throws ClientExistsException
    {
        bank.addClient(client);
    }

    @Override
    public void removeClient(Bank bank, Client client)
    {
        bank.removeClient(client);
    }

    @Override
    public void addAccount(Client client, Account account) throws AccountNumberLimitException
    {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account)
    {
        client.setActiveAccount(account);
    }

    @Override
    public Client getClient(Bank bank, String clientName) throws ClientNotFoundException
    {
        for (String name : bank.getClientsIndex().keySet())
        {
            if (name.equals(clientName))
            {
                return bank.getClientsIndex().get(clientName);
            }
        }
        throw new ClientNotFoundException();
    }

    public Account createAccount(Client client, String accountType)
    {
        return client.createAccount(accountType);
    }

    public void deposit(Client client, float x) throws ActiveAccountNotSet
    {
        client.deposit(x);
    }

    public void withdraw(Client client, float x)
            throws NotEnoughFundsException, ActiveAccountNotSet
    {
        client.withdraw(x);
    }


    public void populateBankData(Bank bank)
    {
        try
        {
            Client client = new Client("Alena", 200, Client.Gender.FEMALE);
            client.addAccount(client.createAccount("Checking"));
            client.addAccount(client.createAccount("Saving"));
            client.setActiveAccountIfNotSet();

            Client client2 = new Client("Vasya", 400, Client.Gender.MALE);
            client2.addAccount(client2.createAccount("Checking"));
            client2.addAccount(client2.createAccount("Saving"));
            client2.setActiveAccountIfNotSet();

            Client client3 = new Client("John Smith");
            client3.setInitialBalance(1000);
            client3.addAccount(client3.createAccount("Saving"));
            client3.setActiveAccountIfNotSet();

            bank.addClient(client);
            bank.addClient(client2);
            bank.addClient(client3);
        }
        catch (AccountNumberLimitException e)
        {
            System.out.println("Account number limit for one client (2) reached");
        }
        catch (ClientExistsException e)
        {
            System.out.println("System data corrupted. CLient already exists");
            System.exit(1);
        }
    }

}
