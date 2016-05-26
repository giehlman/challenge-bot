package chatbot;

import org.openqa.selenium.WebElement;

/**
 * Created by Giehlman on 26.05.2016.
 */
public class Util {

    /**
     * Simulates typing in an DOM element by adding delays in between characters
     * @param element
     * @param message
     */
    public static void simulateTyping(WebElement element, String message) {
        assert element != null;
        assert message != null;
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(String.valueOf(chars[i]));
        }
    }

    /**
     * Simulates backspaces in an DOM element by adding delays in between characters
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
                Thread.sleep(125);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(String.valueOf('\b'));
        }
    }

}
