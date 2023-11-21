package com.luxoft.bankapp.service.demo;

import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Profile("dev")
@Service
public class DemoBankInitializationService implements BankInitializationService
{
    public static final String DEMO_FEED_FILE_NAME = "default.feed";

    @Value("${feed.filename}")
    private String fileName;

    @Autowired
    private BankFeedServiceImpl feedService;


    @PostConstruct
    @Override
    public void createClientsForDemo()
    {
        if (fileName == null)
        {
            fileName = DEMO_FEED_FILE_NAME;
        }

        feedService.loadFeed(new File("demo.feed"));
    }
}
