package chatbot;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class UtilTest {

    WebDriver driver;

    @Test
    public void testSimulateTyping() throws Exception {

    }

    @Test
    public void testSimulateBackspace() throws Exception {

    }

    @Test
    public void testTakeScreenshot() throws Exception {
        try {
            driver = new FirefoxDriver();
            driver.get("http://www.google.de");
            Util.takeScreenshot(driver);
        } finally {
            driver.close();
            driver.quit();
        }

    }
}