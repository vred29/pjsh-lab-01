package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Scanner;

public class WithdrawCommand extends AbstractCommand {

    public WithdrawCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("How much do you want to withdraw?");
        Scanner s = new Scanner(System.in);
        String money = s.nextLine();
        try {
            BankCommander.currentClient.withdraw(Float.parseFloat(money));
        } catch (NumberFormatException e) {
            System.out.println("Wrong number");
        } catch (NotEnoughFundsException e) {
            System.out.println("Not enough money");
        } catch (ActiveAccountNotSet activeAccountNotSet) {
            System.out.println("Active account not set");
        }
    }

}
