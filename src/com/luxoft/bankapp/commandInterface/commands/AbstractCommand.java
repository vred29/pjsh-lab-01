package com.luxoft.bankapp.commandInterface.commands;

public abstract class AbstractCommand implements Command, Comparable {
    String commandName;

    public AbstractCommand(int num) {
        this.commandName = num < 10 ? "0" + num : "" + num;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public int compareTo(Object o) {
        return this.getCommandName().compareTo(((AbstractCommand)o).getCommandName());
    }
}
