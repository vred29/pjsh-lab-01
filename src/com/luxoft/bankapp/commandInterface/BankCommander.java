package com.luxoft.bankapp.commandInterface;

import com.luxoft.bankapp.commandInterface.commands.*;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.feed.BankFeedService;
import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;

import java.util.*;

public class BankCommander
{
    //    public static BankServiceImpl bankService = new BankServiceImpl();
//    public static BankFeedService bankFeedService = new BankFeedServiceImpl();
//    public static BankingImpl currentBank = new BankingImpl("MyBank");
    public static Client currentClient;
    public static Map<String, Command> commands = new TreeMap<>();

    static
    {
        int i = 1;
        registerCommand(new BankReportCommand(i++));
        registerCommand(new GetClientsCommand(i++));
        registerCommand(new AddClientCommand(i++));
        registerCommand(new BankFeedCommand(i++));
        registerCommand(new FindClientCommand(i++));
        registerCommand(new GetAccountsCommand(i++));
        registerCommand(new DepositCommand(i++));
        registerCommand(new WithdrawCommand(i++));
        registerCommand(new TransferCommand(i++));
        registerCommand(new SaveFeedCommand(i++));
        registerCommand(new ExitCommand(i));

//        bankService.populateBankData(currentBank);
    }

    private static void registerCommand(Command command)
    {
        commands.put(command.getCommandName(), command);
    }

    private static void removeCommand(String name)
    {
        commands.remove(name);
    }


    public static void main(String args[])
    {
        Scanner s = new Scanner(System.in);

        while (true)
        {
            System.out.println("\n ----------");
            for (Map.Entry<String, Command> command : commands.entrySet())
            {
                System.out.print(command.getKey() + ") ");
                command.getValue().printCommandInfo();
            }
            System.out.println("----------\n");

            String commandString = s.nextLine();
            if (commandString == null)
            {
                continue;
            }
            if (!commands.containsKey(commandString))
            {
                System.out.println("Invalid command");
                continue;
            }
            commands.get(commandString).printCommandInfo();
            commands.get(commandString).execute();
        }
    }
}

