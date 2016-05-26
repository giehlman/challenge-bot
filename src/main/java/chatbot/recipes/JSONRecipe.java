package chatbot.recipes;

import chatbot.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class JSONRecipe implements IRecipe {

    private final JSONObject _recipe;

    public JSONRecipe(JSONObject recipe) {
        _recipe = recipe;
    }

    public boolean rampUp(WebDriver driver) {
        if (!_recipe.has("rampUp")) {
            System.out.println("No 'rampUp'! JSON recipe might be corrupted. Exiting...");
            return false;
        }

        JSONArray rampUp = _recipe.getJSONArray("rampUp");

        return interpretRecipe(driver, rampUp);
    }

    public boolean talk(WebDriver driver) {
        JSONArray rampUp = _recipe.getJSONArray("talk");

        return interpretRecipe(driver, rampUp);
    }

    public boolean shutdown(WebDriver driver) {
        if (!_recipe.has("shutdown")) {
            System.out.println("No 'shutdown'! JSON recipe might be corrupted. Exiting...");
            return false;
        }

        JSONArray shutdown = _recipe.getJSONArray("shutdown");

        return interpretRecipe(driver, shutdown);
    }

    private boolean interpretRecipe(WebDriver driver, JSONArray instructions) {
        JSONObject instr;
        for (int i = 0; i < instructions.length(); i++) {
            instr = instructions.getJSONObject(i);
            try {
                By locator = By
                        .xpath(instr.getString("xpath"));

                int waitInSec = instr.getInt("wait");

                if ("".equals(instr.getString("xpath"))) {
                    // quick hack for adding Thread.sleep support on empty xpath
                    Thread.sleep(waitInSec * 1000);
                    continue;
                } else if (waitInSec > 0) {
                    WebDriverWait wait = new WebDriverWait(driver, waitInSec);

                    if ("waitForNewChatline".equals(instr.getString("xpath"))) {
                        // hack for detecting reply
                        int cnt = driver.findElements(By.xpath(instr.getString("args"))).size();
                        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(instr.getString("args")), cnt));
                        
                        // get the posed question
                        List<WebElement> replies = driver.findElements(By.xpath(instr.getString("args")));
                        System.out.println(replies.get(replies.size()-1).getText());

                        // now think of if we should use WIT...
                        
                        continue;
                    } else {
                        wait.until(ExpectedConditions
                                .visibilityOfElementLocated(locator));
                    }
                }

                WebElement element = driver.findElement(locator);

                String action = instr.getString("action");
                if (action.trim().length() > 0) {
                    if ("click".equals(action)) {
                        element.click();
                    } else if ("return".equals(action)) {
                        return false;
                    } else if ("sendKeys".equals(action)) {
                        //element.sendKeys(instr.getString("args"));
                        Util.simulateTyping(element, instr.getString("args"));
//                        chatbot.Util.simulateBackspace(element, instr.getString("args"));
//                        chatbot.Util.simulateTyping(element, instr.getString("args"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
