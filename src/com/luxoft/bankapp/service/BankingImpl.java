package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.Storage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankingImpl implements Banking
{
    private Storage<Client> storage;

    @Override
    public void addClient(Client c)
    {
        storage.add(c);
    }

    @Override
    public Client getClient(String name)
    {
        Client found = storage.getBy(name);

        if (found != null)
        {
            return found;
        }

        throw new ClientNotFoundException(name);
    }

    @Override
    public Set<Client> getClients()
    {
        return storage.getAll();
    }

    @Override
    public void removeClient(Client c)
    {
        if (c.getId() != null)
        {
            storage.remove(c.getId());
        }
    }

    @Override
    public void addAccount(Client c, Account account)
    {
        if (c.getId() != null)
        {
            storage.get(c.getId()).addAccount(account);
            storage.update(c);
        }
    }

    @Override
    public void updateAccount(Client c, Account account)
    {
        if (c.getId() != null)
        {
            Client toUpdate = storage.get(c.getId());

            toUpdate.removeAccount(account.getType());
            toUpdate.addAccount(account);

            storage.update(c);
        }
    }

    @Override
    public Account getAccount(Client c, AccountType type)
    {
        for (Account account : c.getAccounts())
        {
            if (type == account.getType())
            {
                return account;
            }
        }

        throw new AccountNotFoundException();
    }

    @Override
    public Set<Account> getAllAccounts()
    {
        Set<Account> accounts = new HashSet<>();

        for (Client client : storage.getAll())
        {
            accounts.addAll(client.getAccounts());
        }

        return accounts;
    }

    @Override
    public void removeAccount(Client c, AccountType type)
    {
        if (c.getId() != null)
        {
            Client toUpdate = storage.get(c.getId());
            toUpdate.removeAccount(type);
            storage.update(toUpdate);
        }
    }

    // TODO feed
    public void parseFeed(Map<String, String> map)
    {
//        String name = map.get("name");
//        Client client = getClientsIndex().get(name);
//        Client.Gender gender = Client.parseGender(map.get("gender"));
//        if (client == null)
//        {
//            try
//            {
//                addClient(new Client(name, gender));
//                client = getClientsIndex().get(name);
//            }
//            catch (ClientExistsException ignore)
//            {
//                System.err.println("Persistence in bank index corrupted!");
//
//            }
//        }
//        client.parseFeed(map);
    }

}
