package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Map;

public interface Account
{
    String getAccountName();

    float getBalance();

    void deposit(float x) throws IllegalArgumentException;

    void withdraw(float x) throws NotEnoughFundsException;

    void parseFeed(Map<String, String> map);
}
