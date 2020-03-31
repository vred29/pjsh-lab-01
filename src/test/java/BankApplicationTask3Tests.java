import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankReportServiceImpl;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.feed.BankFeedService;
import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;
import com.luxoft.bankapp.service.storage.ClientStorage;
import com.luxoft.bankapp.service.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = {"classpath:application-context.xml", "classpath:test-clients.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BankApplicationTask3Tests
{
    private static final String[] CLIENT_NAMES =
            { "Jonny Bravo", "Adam Budzinski", "Anna Smith" };

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Banking banking;

    @Autowired
    private Storage storage;

    @Autowired
    private BankReportService bankReport;

    @Autowired
    private BankFeedService bankFeedService;

    @BeforeEach
    public void init()
    {
        BankApplication.initialize(applicationContext);
    }

    @Test
    public void storageBeanConfiguration()
    {
        assertNotNull(storage, "storage bean should be configured");
        assertTrue((storage instanceof ClientStorage), "storage should be instantiated with ClientStorage class");
    }

    @Test
    public void bankingBeanConfiguration()
    {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void bankingBeanAnnotation()
    {
        Annotation annotation = null;

        try
        {
            annotation = BankingImpl.class.getDeclaredField("storage")
                    .getAnnotation(Autowired.class);
        }
        catch (NoSuchFieldException e)
        {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }

    @Test
    public void bankReportConfiguration()
    {
        assertNotNull(bankReport, "bankReport bean should be configured");
        assertTrue((bankReport instanceof BankReportServiceImpl), "bankReport should be instantiated with BankReportServiceImpl class");
    }

    @Test
    public void bankReportBeanAnnotation()
    {
        Annotation annotation = null;

        try
        {
            annotation = BankReportServiceImpl.class.getDeclaredField("storage")
                    .getAnnotation(Autowired.class);
        }
        catch (NoSuchFieldException e)
        {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }


    @Test
    public void bankFeedServiceConfiguration1()
    {
        assertNotNull(bankFeedService, "bankFeedService bean should be configured");
        assertTrue((bankFeedService instanceof BankFeedServiceImpl), "bankFeedService should be instantiated with BankFeedServiceImpl class");
    }

    @Test
    public void bankFeedServiceConfiguration2()
    {
        assertSame(banking, ((BankFeedServiceImpl) bankFeedService).getBanking(),
                "bankFeedService should use banking bean");
    }

    @Test
    public void bankFeedServiceBeanAnnotation()
    {
        Annotation annotation = null;

        try
        {
            annotation = BankFeedServiceImpl.class.getConstructor(Banking.class)
                    .getAnnotation(Autowired.class);
        }
        catch (NoSuchMethodException e)
        {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }


}
