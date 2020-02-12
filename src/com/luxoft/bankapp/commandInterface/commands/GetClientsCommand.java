package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;

public class GetClientsCommand extends AbstractCommand {

    public GetClientsCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Clients:");
//        BankCommander.currentBank.getClients().forEach(v -> {
//            System.out.println(v.getSimpleNameInfo());
//        });
    }
}
