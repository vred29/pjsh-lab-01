package com.luxoft.bankapp.service.feed;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public interface BankFeedService
{
    void loadFeed(String folder);

    void loadFeed(File file);

    void saveFeed(String file) throws IllegalAccessException, InvocationTargetException;
}
