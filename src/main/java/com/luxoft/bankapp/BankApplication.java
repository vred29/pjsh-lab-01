package com.luxoft.bankapp;

import com.luxoft.bankapp.service.demo.BankInitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

@SpringBootApplication
@PropertySource("classpath:clients.properties")
public class BankApplication
{
	public static void main(String[] args)
	{
		ConfigurableApplicationContext context =
				SpringApplication.run(BankApplication.class, args);
	}

}
