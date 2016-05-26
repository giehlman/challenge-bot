package chatbot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
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
            FileUtils.copyFile(scrFile, new File(ChallengeBot.screenshotOutputTo + File.separator + new SimpleDateFormat("yyyyMMddhhmm'.png'").format(new Date())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
