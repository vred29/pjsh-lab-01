package com.luxoft.bankapp.service;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.*;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BankServiceImpl implements BankService
{
    private static final String SERIALIZING_PATH = "./client.ser";

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

    @Override
    public void saveClient(Client client)
    {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream(SERIALIZING_PATH))))
        {
            outputStream.writeObject(client);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Client loadClient(String path)
    {
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(path))))
        {
            return (Client) inputStream.readObject();
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
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

    @Test
    public void seraializationTest()
    {
        Client client = new Client("TestClient", 357, Client.Gender.MALE);
        client.addAccount(client.createAccount("Checking"));
        client.addAccount(client.createAccount("Saving"));
        client.setActiveAccountIfNotSet();
        client.setCity("Chelyabinsk");
        client.setInitialBalance(200f);

        BankCommander.bankService.saveClient(client);
        Client loadClient = BankCommander.bankService.loadClient(SERIALIZING_PATH);

        assertEquals("TestClient", loadClient.getName());
        assertEquals(357, loadClient.getInitialOverdraft(), 0);
        assertEquals("m", loadClient.getGender());
        assertEquals(2, loadClient.getAccounts().size());
        assertEquals("Chelyabinsk", loadClient.getCity());
        assertEquals(200f, loadClient.getInitialBalance(), 0);
        Account checking = loadClient.getAccount("Checking Account");
        Account saving = loadClient.getAccount("Saving Account");
        assertNotNull(checking);
        assertNotNull(saving);
        assertEquals(checking.getBalance(), 0, 0);
        assertEquals(saving.getBalance(), 0, 0);

    }

}
