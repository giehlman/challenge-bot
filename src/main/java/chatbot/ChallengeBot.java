package chatbot;

import chatbot.recipes.IRecipe;
import chatbot.recipes.LivePersonRecipe;
import chatbot.recipes.TopformRecipe;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Giehlman on 24.05.2016.
 */
public class ChallengeBot {

    public static WebDriver driver;
    public static String frontpage = "http://liveperson.com";
    public static String chromeWebdriverPath = "D:\\Downloads\\chromedriver_win32\\chromedriver.exe";
    public static String useDriver = "chrome";
    public static String username = "Chris";
    public static String email = "c.giehl@gmail.com";
    public static String pathToJsonRecipe = null;
    public static WitHandler witHandler = null;
    public static String screenshotOutputTo = ChallengeBot.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public static void main(String[] args) {

        parseArgs(args);

        System.out.println("Starting selenium web driver...");
        if ("firefox".equals(useDriver)) {
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", chromeWebdriverPath);
            driver = new ChromeDriver();
        }

        try {
            IRecipe recipe;
            if (pathToJsonRecipe != null) {
                // use the liveperson recipe as default
                recipe = new LivePersonRecipe(Util.jsonFromFile(pathToJsonRecipe));
            } else {
                // fallback example with credentials from args
                recipe = new TopformRecipe(username, email);
            }

            if (!recipe.rampUp(driver)) {
                System.out.println("Unable to ramp up the conversation. Exiting...");
                return;
            }

            if (!recipe.talk(driver)) {
                System.out.println("Talking was unsuccessful! Exiting...");
                return;
            }

            recipe.shutdown(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
            driver.quit();
        }
    }

    private static void parseArgs(String[] args) {
        if (args == null)
            return;

        for (int i = 0; i < args.length; i++) {
            try {
                if ("-frontpage".equals(args[i])) {
                    frontpage = args[i + 1];
                    i++;
                    System.out.println(String.format("\tfrontpage:\t%s",
                            frontpage));
                } else if ("-chromeDriverPath".equals(args[i])) {
                    chromeWebdriverPath = args[i + 1];
                    i++;
                    System.out.println(String.format(
                            "\tchromeWebDriverPath:\t%s", chromeWebdriverPath));
                } else if ("-useDriver".equals(args[i])) {
                    useDriver = args[i + 1];
                    i++;
                    System.out.println(String.format("\tuse driver:\t%s",
                            useDriver));
                } else if ("-username".equals(args[i])) {
                    username = args[i + 1];
                    i++;
                    System.out.println(String.format("\tuser nick:\t%s",
                            username));
                } else if ("-useWit".equals(args[i])) {
                    witHandler = new WitHandler(null, null);
                } else if ("-email".equals(args[i])) {
                    email = args[i + 1];
                    i++;
                    System.out.println(String
                            .format("\tuser email:\t%s", email));
                } else if ("-jsonRecipe".equals(args[i])) {
                    pathToJsonRecipe = args[i + 1];
                    i++;
                    System.out.println(String.format("\tJSON recipe:\t%s", pathToJsonRecipe));
                } else if ("-screenshotOutputTo".equals(args[i])) {
                    screenshotOutputTo = args[i+1];
                    i++;
                    System.out.println(String.format("\tScreenshots to:\t%s", screenshotOutputTo));
                } else if ("-help".equals(args[i])) {
                    StringBuilder b = new StringBuilder();
                    b.append(String.format("-frontpage\t"));
                    b.append(String.format("-chromeDriverPath\t"));
                    b.append(String.format("-useDriver\t"));
                    b.append(String.format("-useWit\t"));
                    b.append(String.format("-jsonRecipe\t"));
                    b.append(String.format("-help\t"));
                    b.append(String.format("-screenshotOutputTo\t"));
                    System.out.println(b.toString());
                    return;
                } else {
                    System.out
                            .println(String
                                    .format("WARN Unknown args '%s'. Skipping! Type '-help' for info.",
                                            args[i]));
                }
            } catch (Exception e) {
                System.out.println("ERR Don't mess with me! Bad arguments!");
                e.printStackTrace();
            }
        }
    }
}
