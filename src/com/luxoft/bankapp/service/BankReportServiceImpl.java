package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

public class BankReportServiceImpl implements BankReportService {

    @Override
    public int getNumberOfBankClients(Bank bank) {
        return bank.getClients().size();
    }

    @Override
    public int getAccountsNumber(Bank bank) {
        int result = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                result++;
            }
        }
        return result;
    }

    @Override
    public List<Client> getClientsSorted(Bank bank) {
        List<Client> clients = new ArrayList<>(bank.getClients());
        clients.sort((o1, o2) -> {
            try {
                return (int) (o1.getBalance() - o2.getBalance());
            } catch (ActiveAccountNotSet activeAccountNotSet) {
                return 0;
            }
        });
        return clients;
    }

    @Override
    public float getBankCreditSum(Bank bank) {
        float creditSum = 0;
        for (Client client : bank.getClients()) {
            creditSum += (float)client.getAccounts().stream()
                    .filter(v -> v.getAccountName().equals("Checking Account"))
                    .mapToDouble(Account::getBalance)
                    .filter(v -> v<0)
                    .sum();
        }
        return creditSum;
    }

    @Override
    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        Map<String, List<Client>> result = new HashMap<>();

        for (Client client : bank.getClients()) {
            String city = client.getCity();
            if (!result.containsKey(city)) {
                result.put(city, new ArrayList<>());
            }
            result.get(city).add(client);
        }

        return result;
    }
}
