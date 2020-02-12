package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class TransferCommand extends AbstractCommand {

    public TransferCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Who do you want to transfer money?");
        System.out.println("Enter name of the client");
        Scanner s = new Scanner(System.in);
        String clientName = s.nextLine();
        Client client;
        Float money;
//        try {
//            client = BankCommander.bankService.getClient(BankCommander.currentBank, clientName);
//            while (true) {
//                try {
//                    System.out.println("How much money do you want to transfer?");
//                    money = Float.parseFloat(s.nextLine());
//                    break;
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid number, try again");
//                }
//            }
//            BankCommander.currentClient.withdraw(money);
//            client.deposit(money);
//        } catch (ClientNotFoundException e) {
//            System.out.println("Client wasn't found");
//        } catch (NotEnoughFundsException e) {
//            System.out.println("Not enought money");
//        } catch (ActiveAccountNotSet activeAccountNotSet) {
//            System.out.println("Active account not set");
//        }
    }
}
