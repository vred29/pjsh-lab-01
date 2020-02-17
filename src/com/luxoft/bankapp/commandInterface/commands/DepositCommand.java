package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.model.Client;
import java.util.Scanner;

public class DepositCommand extends AbstractCommand
{
    private Client client;

    public DepositCommand(int num, Client client)
    {
        super(num);
        this.client = client;
    }

    @Override
    public void execute()
    {
        System.out.println("How much do you want to deposit?");
        Scanner s = new Scanner(System.in);
        String amount = s.nextLine();

        try
        {
            client.deposit(Double.parseDouble(amount));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Illegal amount: " + amount);
        }
        catch (ActiveAccountNotSet activeAccountNotSet)
        {
            System.out.println("Active account not set");
        }
    }

}
