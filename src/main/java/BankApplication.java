import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankReportServiceImpl;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.model.Client.Gender;
import com.luxoft.bankapp.service.storage.ClientRepository;
import com.luxoft.bankapp.service.storage.MapClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan("com.luxoft.bankapp")
@PropertySource("classpath:clients.properties")
public class BankApplication {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("clients.properties"));
        return configurer;
    }

    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context =
//                new ClassPathXmlApplicationContext(new String[]{"application-context.xml", "test-clients.xml"});

         ApplicationContext context =
                 new AnnotationConfigApplicationContext(BankApplication.class);

//      HEALTHCHECK
//        SavingAccount savingAccount = (SavingAccount) context.getBean("savingAccount1");
//        CheckingAccount checkingAccount = (CheckingAccount) context.getBean("checkingAccount1");
//        Client client = (Client) context.getBean("client1");


//        ClientRepository repository = new MapClientRepository();

        Banking banking = initialize(context);

        workWithExistingClients(context);

        bankingServiceDemo(context);

        bankReportsDemo(context);
    }

    public static void bankReportsDemo(ApplicationContext context) {

        System.out.println("\n=== Using BankReportService ===\n");

//        BankReportService reportService = new BankReportServiceImpl();
//        reportService.setRepository(repository);

        BankReportService reportService = (BankReportService) context.getBean(BankReportService.class);

        System.out.println("Number of clients: " + reportService.getNumberOfBankClients());

        System.out.println("Number of accounts: " + reportService.getAccountsNumber());

        System.out.println("Bank Credit Sum: " + reportService.getBankCreditSum());
    }

    public static void bankingServiceDemo(ApplicationContext context) {
        Banking banking = context.getBean(Banking.class);

        System.out.println("\n=== Initialization using Banking implementation ===\n");

        Client anna = new Client(CLIENT_NAMES[2], Gender.FEMALE);
        anna = banking.addClient(anna);

        AbstractAccount saving = banking.createAccount(anna, SavingAccount.class);
        saving.deposit(1000);

        banking.updateAccount(anna, saving);

        AbstractAccount checking = banking.createAccount(anna, CheckingAccount.class);
        checking.deposit(3000);

        banking.updateAccount(anna, checking);

        banking.getAllAccounts(anna).stream().forEach(System.out::println);
    }

    public static void workWithExistingClients(ApplicationContext context) {
        Banking banking = context.getBean(Banking.class);

        System.out.println("\n=======================================");
        System.out.println("\n===== Work with existing clients ======");

        Client jonny = banking.getClient(CLIENT_NAMES[0]);

        try {

            jonny.deposit(5_000);

        } catch (ActiveAccountNotSet e) {

            System.out.println(e.getMessage());

            jonny.setDefaultActiveAccountIfNotSet();
            jonny.deposit(5_000);
        }

        System.out.println(jonny);

        Client adam = banking.getClient(CLIENT_NAMES[1]);
        adam.setDefaultActiveAccountIfNotSet();

        adam.withdraw(1500);

        double balance = adam.getBalance();
        System.out.println("\n" + adam.getName() + ", current balance: " + balance);

        banking.transferMoney(jonny, adam, 1000);

        System.out.println("\n=======================================");
        banking.getClients().forEach(System.out::println);
    }

    /*
     * Method that creates a few clients and initializes them with sample values
     */
    public static Banking initialize(ApplicationContext context) {

//        Banking banking = new BankingImpl();
//        banking.setRepository(repository);
        Banking banking = (Banking) context.getBean(Banking.class);

//        Client client_1 = new Client(CLIENT_NAMES[0], Gender.MALE);
//
//        AbstractAccount savingAccount = new SavingAccount(1000);
//        client_1.addAccount(savingAccount);
//
//        AbstractAccount checkingAccount = new CheckingAccount(1000);
//        client_1.addAccount(checkingAccount);
        Client client_1 = (Client) context.getBean("client1");

//        Client client_2 = new Client(CLIENT_NAMES[1], Gender.MALE);
//
//        AbstractAccount checking = new CheckingAccount(1500);
//        client_2.addAccount(checking);
        Client client_2 = (Client) context.getBean("client2");

        banking.addClient(client_1);
        banking.addClient(client_2);

        return banking;
    }

    @Bean(name = "savingAccount1")
    public SavingAccount getDemoSavingAccount1()
    {
        return new SavingAccount(1000);
    }
    @Bean(name = "checkingAccount1")
    public CheckingAccount getDemoCheckingAccount1()
    {
        return new CheckingAccount(1000);
    }

    @Bean(name = "client1")
    public Client getDemoClient1()
    {
        String name = environment.getProperty("client1");

        Client client = new Client(name, Gender.MALE);
        client.setCity(environment.getProperty("client1.city"));

        AbstractAccount saving = (SavingAccount)
                applicationContext.getBean("savingAccount1");
        AbstractAccount checking = (CheckingAccount)
                applicationContext.getBean("checkingAccount1");

        client.addAccount(saving);
        client.addAccount(checking);

        return client;
    }

    @Bean(name = "checkingAccount2")
    public CheckingAccount getDemoCheckingAccount2()
    {
        return new CheckingAccount(1500);
    }

    @Bean(name = "client2")
    public Client getDemoClient2()
    {
        String name = environment.getProperty("client2");

        Client client = new Client(name, Gender.MALE);
        client.setCity(environment.getProperty("client2.city"));

        AbstractAccount checking = (CheckingAccount)
                applicationContext.getBean("checkingAccount2");

        client.addAccount(checking);

        return client;
    }
}
