package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;
import java.util.Map;

public interface BankReportService
{
    int getNumberOfBankClients(Bank bank);

    int getAccountsNumber(Bank bank);

    List<Client> getClientsSorted(Bank bank);

    float getBankCreditSum(Bank bank);

    Map<String, List<Client>> getClientsByCity(Bank bank);
}
