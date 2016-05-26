package chatbot.recipes;

import org.openqa.selenium.WebDriver;

/**
 * Created by Giehlman on 26.05.2016.
 */
public interface IRecipe {

    /**
     * Starts the chat window and initiates a conversation. Ramping up ends as soon as we receive the first reply message.
     *
     * @param driver
     * @return
     */
    public boolean rampUp(WebDriver driver);

    /**
     * @param driver
     * @return
     */
    public boolean talk(WebDriver driver);

    /**
     * Closes the conversation window and shuts down instances
     *
     * @param driver
     * @return
     */
    public boolean shutdown(WebDriver driver);

}