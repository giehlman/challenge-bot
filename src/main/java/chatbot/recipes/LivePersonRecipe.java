package chatbot.recipes;

import chatbot.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Giehlman on 25.05.2016.
 */
public class LivePersonRecipe implements chatbot.recipes.IRecipe {

    private final String _username;
    private final String _email;

    public LivePersonRecipe(String username, String email) {
        _username = username;
        _email = email;
    }

    public boolean rampUp(WebDriver driver) {
        // 1. find the question box and click it!
        WebDriverWait wait = new WebDriverWait(driver, 60);
        By containerLocator = By
                .xpath("//div[starts-with(@id, 'LPMcontainer')]");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(containerLocator));
        driver.findElement(containerLocator).click();

        // 2. wait until the survey container is up
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='lp_survey_container']")));

        // 3. check if chat is available or under maintenance
        try {
            Boolean chatUnavailable = driver.findElements(By.xpath("//*[contains(text(),'representatives will be back shortly')]")).size() > 0;
            if (chatUnavailable) {
                System.out.println("Chat is currently unavailable! Exiting...");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. type in the fields: name, email and question/message
        By nameLocator = By
                .xpath("//div[contains(text(),'What is your name?')]/parent::div//input");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(nameLocator));

        //driver.findElement(nameLocator).sendKeys(_username);
        Util.simulateTyping(driver.findElement(nameLocator), _username);

        By emailLocator = By
                .xpath("//div[contains(text(),'Email Address')]/parent::div//input");
        WebElement fldEmail = driver.findElement(emailLocator);
        //fldEmail.sendKeys(_email);
        Util.simulateTyping(fldEmail, _email);

//        By questionLocator = By.xpath("//div[@id='LP_TextFieldQuestion_3']//input");
//        WebElement fldQuestion = driver.findElement(questionLocator);
//        fldQuestion.sendKeys("Hi, is anyone there? I have question");

        try {
            Boolean noReply = driver.findElements(By.xpath("//*[contains(text(),'representatives will be back shortly')]")).size() > 0;
            if (noReply) {
                System.out.println("No one is replying to our chat request. Exiting...");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean talk(WebDriver driver) {
        return false;
    }

    public boolean shutdown(WebDriver driver) {
        return false;
    }

}
