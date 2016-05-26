package chatbot.recipes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class TopformRecipe implements IRecipe {

    private final String _username;
    private final String _email;

    public TopformRecipe(String username, String email) {
        _username = username;
        _email = email;
    }

    public boolean rampUp(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        By qboxLocator = By
                .xpath("//iframe[@id='tawkchat-minified-iframe-element']");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(qboxLocator));
        driver.switchTo().frame("tawkchat-minified-iframe-element");

        WebElement chatbar = driver.findElement(By
                .xpath("//div[@id='tawkchat-minified-wrapper']"));

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + chatbar.getLocation().y + ")");

        chatbar.click();
//		actions.moveToElement(chatbar).click().perform();

        // 2. type in the fields: name, email and question/message
        By nameLocator = By
                .xpath("//div[contains(text(),'What is your name?')]/parent::div//input");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(nameLocator));

        driver.findElement(nameLocator).sendKeys(_username);

        By emailLocator = By
                .xpath("//div[contains(text(),'Email Address')]/parent::div//input");
        WebElement fldEmail = driver.findElement(emailLocator);
        fldEmail.sendKeys(_email);

        By questionLocator = By.xpath("//div[@id='LP_TextFieldQuestion_3']//input");
        WebElement fldQuestion = driver.findElement(questionLocator);
        fldQuestion.sendKeys("Test");

        return true;
    }

    public boolean talk(WebDriver driver) {
        return false;
    }

    public boolean shutdown(WebDriver driver) {
        return false;
    }
}
