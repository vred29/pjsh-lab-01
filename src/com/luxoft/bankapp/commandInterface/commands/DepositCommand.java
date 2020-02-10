package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;

import java.util.Scanner;

public class DepositCommand extends AbstractCommand {

    public DepositCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("How much do you want to deposit?");
        Scanner s = new Scanner(System.in);
        String money = s.nextLine();
        try {
            BankCommander.currentClient.deposit(Float.parseFloat(money));
        } catch (NumberFormatException e) {
            System.out.println("Wrong number");
        } catch (ActiveAccountNotSet activeAccountNotSet) {
            System.out.println("Active account not set");
        }
    }

}
