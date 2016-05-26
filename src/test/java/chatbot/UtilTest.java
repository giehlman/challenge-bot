package chatbot;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

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
            File created = Util.takeScreenshot(driver);
            Assert.assertNotNull(created);
            Assert.assertTrue(created.exists());
        } finally {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void testReadFile() throws Exception {

    }

    @Test
    public void testJsonFromFile() throws Exception {

    }
}