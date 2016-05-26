package chatbot.recipes;

import chatbot.ChallengeBot;
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
    private String _xpath_message = null;
    private String _xpath_submit = null;

    public JSONRecipe(JSONObject recipe) {
        _recipe = recipe;
        if (_recipe.has("config")) {
            if (_recipe.getJSONObject("config").has("xpath_message")) {
                _xpath_message = _recipe.getJSONObject("config").getString("xpath_message");
            }
            if (_recipe.getJSONObject("config").has("xpath_submit")) {
                _xpath_submit = _recipe.getJSONObject("config").getString("xpath_submit");
            }
        }
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

    /**
     * Processes the commands given in the JSON recipe and acts accordingly
     * @param driver
     * @param instructions
     * @return
     */
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
                        // in this mode, the conversation is transmitted to Wit. If Wit has no answer, the fallback conversation continues according to the JSON recipe

                        String witReply = null;
                        do {
                            int cnt = driver.findElements(By.xpath(instr.getString("args"))).size();
                            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(instr.getString("args")), cnt));

                            // get the posed question
                            List<WebElement> replies = driver.findElements(By.xpath(instr.getString("args")));
                            String reply = replies.get(replies.size() - 1).getText();
                            System.out.println(reply);

                            // now chat with WIT!
                            if (ChallengeBot.witHandler != null) {
                                witReply = ChallengeBot.witHandler.talkToWit(ChallengeBot.witHandler.ask(reply));

                                if (witReply != null && _xpath_message != null) {
                                    Util.simulateTyping(driver.findElement(By.xpath(_xpath_message)), witReply);
                                    driver.findElement(By.xpath(_xpath_submit)).click();
                                }
                            }
                        } while (witReply != null);

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
