package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankReportServiceImpl;

import java.util.List;
import java.util.Map;

public class BankReportCommand extends AbstractCommand {
    private static BankReportService bankReportService = new BankReportServiceImpl();

    public BankReportCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        BankingImpl bank = BankCommander.currentBank;
        System.out.println("========== Bank " + bank.getName() + " report ====================");
        System.out.println("Number of clients: " + bankReportService.getNumberOfBankClients(bank));
        System.out.println("Number of accounts: " + bankReportService.getAccountsNumber(bank));
        System.out.println("Credit sum: " + bankReportService.getBankCreditSum(bank));
        System.out.println("Clients (by balance): ");
        bankReportService.getClientsSorted(bank).forEach(client -> {
            System.out.println("\t");
            System.out.println(client.getSimpleNameInfo());
        });
        System.out.println("Clients (by city): ");
        Map<String, List<Client>> clientsByCity = bankReportService.getClientsByCity(bank);
        for (String city : clientsByCity.keySet()) {
            System.out.println("\t" + city);
            clientsByCity.get(city).forEach(c -> System.out.println("\t\t" + c.getSimpleNameInfo()));
        }
        System.out.println("============================================= end of report");
    }
}
