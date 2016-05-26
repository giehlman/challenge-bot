package chatbot.recipes;

import chatbot.Util;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class JSONRecipeTest {

    WebDriver driver;

    @Test
    public void testOpenLivepersonChat() throws Exception {
        try {
            driver = new FirefoxDriver();

            String testfile = "C:\\Users\\test1\\Desktop\\challenge-bot\\src\\main\\resources\\recipe_liveperson_regressiontest.json";
            JSONRecipe recipe = new JSONRecipe(Util.jsonFromFile(testfile));

            boolean rampUpSuccess = recipe.rampUp(driver);
            Assert.assertTrue("Should be able to open chat window", rampUpSuccess);

            boolean shutdownSuccess = recipe.shutdown(driver);
            Assert.assertTrue("Should be able to close the chat window", shutdownSuccess);
        } finally {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void testInterpretRecipe() throws Exception {

    }
}