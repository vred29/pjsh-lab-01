package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.Client;

public class ClientStorage extends MapStorage<Client>
{
    @Override
    public Client getBy(String name)
    {
        for (Client client : getAll())
        {
            if (name.equals(client.getName()))
            {
                return client;
            }
        }

        return null;
    }
}
