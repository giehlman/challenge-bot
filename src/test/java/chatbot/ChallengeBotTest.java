package chatbot;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class ChallengeBotTest {
    WebDriver driver;

    @Test
    public void testSeleniumDrivers() {
        ArrayList<WebDriver> drivers = new ArrayList<WebDriver>();
        try {
            driver = new FirefoxDriver();
            drivers.add(driver);
            // NOTE: add system specific path here if test fails!
            System.setProperty("webdriver.chrome.driver", "D:\\Downloads\\chromedriver_win32\\chromedriver.exe");
            driver = new ChromeDriver();
            drivers.add(driver);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue("Unable to open at least one driver!", false);
        } finally {
            for (WebDriver driver : drivers) {
                driver.close();
                driver.quit();
            }
        }
    }

}