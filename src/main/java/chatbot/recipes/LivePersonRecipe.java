package chatbot.recipes;

import chatbot.Util;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Giehlman on 25.05.2016.
 */
public class LivePersonRecipe extends JSONRecipe {

    public LivePersonRecipe(JSONObject recipe) {
        super(recipe);
    }

    public boolean rampUp(WebDriver driver) {
        boolean success = super.rampUp(driver);

        if (success) {
            try {
                // extract the name
                String text = driver.findElement(By.xpath("//div[contains(text(),'You are now chatting with')]")).getText();
                Pattern p = Pattern.compile("^You are now chatting with ([\\w]*).$");
                Matcher m = p.matcher(text);

                String extractedName = "";
                if (m.find()) {
                    extractedName = ", " + m.group(1);
                }

                Util.simulateTyping(driver.findElement(By.xpath("//div[@class='lp_input_area']//textarea")), String.format("Hi%s!", extractedName));
                driver.findElement(By.xpath("//button[@class='lp_send_button']")).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    @Override
    public boolean shutdown(WebDriver driver) {
        try {
            Util.simulateTyping(driver.findElement(By.xpath("//div[@class='lp_input_area']//textarea")), String.format("You Liveperson guys are awesome! I appreciate!"));
            driver.findElement(By.xpath("//button[@class='lp_send_button']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.shutdown(driver);
    }

}
