package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;

public class GetAccountsCommand extends AbstractCommand {

    public GetAccountsCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println(BankCommander.currentClient.toString());
        System.out.println();
    }

}
