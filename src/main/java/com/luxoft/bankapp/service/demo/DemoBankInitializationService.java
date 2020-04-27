package com.luxoft.bankapp.service.demo;

import com.luxoft.bankapp.service.Banking;

import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
@Profile("dev")
public class DemoBankInitializationService implements BankInitializationService
{
    public static final String DEMO_FEED_FILE_NAME = "demo.feed";

    @Autowired
    private Banking banking;

    @Autowired
    private BankFeedServiceImpl feedService;

    @Override
    public void createClientsForDemo()
    {
        feedService.loadFeed(new File(DEMO_FEED_FILE_NAME));
    }

    @PostConstruct
    private void init()
    {
        feedService.setBanking(banking);
        createClientsForDemo();
    }

    public Banking getBanking()
    {
        return banking;
    }
}
