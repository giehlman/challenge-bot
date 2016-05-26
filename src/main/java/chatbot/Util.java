package chatbot;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class Util {

    /**
     * Simulates typing in an DOM element by adding delays in between characters
     *
     * @param element
     * @param message
     */
    public static void simulateTyping(WebElement element, String message) {
        assert element != null;
        assert message != null;
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(String.valueOf(chars[i]));
        }
    }

    /**
     * Simulates backspaces in an DOM element by adding delays in between characters
     *
     * @param element
     * @param message
     */
    public static void simulateBackspace(WebElement element, String message) {
        assert element != null;
        assert message != null;
        assert message.equals(element.getText());
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(String.valueOf('\b'));
        }
    }

    /**
     * Takes a screenshot using Selenium and stores it (with timestamp appendinx) to folder specified in ChallengeBot.java (see also: arg -screenshotOutputTo)
     *
     * @param driver
     */
    public static void takeScreenshot(WebDriver driver) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(ChallengeBot.screenshotOutputTo + File.separator + new SimpleDateFormat("'screenshot_'yyyyMMddhhmm'.png'").format(new Date())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a file from given filename and returns its content as String
     * @param filename
     * @return
     */
    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Convenience method for reading a JSONObject from a file
     * @param pathToJsonRecipe
     * @return
     */
    public static JSONObject jsonFromFile(String pathToJsonRecipe) {
        String jsonData = Util.readFile(pathToJsonRecipe);
        return new JSONObject(jsonData);
    }
}
