package atf.tests;

import atf.config.ConfigReader;
import atf.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {

    protected Logger logger;
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.INFO);
        logger.info("Running test in " + browser);
//        switch (browser.toLowerCase()) {
//            case "chrome":
//                driver = new ChromeDriver();
//                break;
//            default:
//                logger.warning("Configuration for " + browser + " is missing, running tests in Chrome.");
//                driver = new ChromeDriver();
//                break;
//        }
//        driver.get(ConfigReader.get("base_url"));
        driver = DriverFactory.createDriver("chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //if used to avoid null pointer exception and close the browser only when driver is actually created
        if (driver != null) {
            driver.quit();
            logger.info("Browser is closed.");
        }
    }
}
