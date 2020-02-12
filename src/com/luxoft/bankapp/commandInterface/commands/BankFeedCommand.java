package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;

import java.util.Scanner;

public class BankFeedCommand extends AbstractCommand {

    public BankFeedCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter folder");
        Scanner s = new Scanner(System.in);
        String folder = s.nextLine();
//        BankCommander.bankFeedService.loadFeed(folder);
    }
}
