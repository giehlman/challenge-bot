import chatbot.WitHandler;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Giehlman on 25.05.2016.
 */
public class WitHandlerTest {

    WitHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new WitHandler("BUNU44ZAVK2IIS2JUQN7CC6XEUVW7UHC", null);
    }

    @Test
    public void testAsk() throws Exception {
        String q = "How are you?";
        String response = handler.ask(q);
        Assert.assertNotNull("Response should be not null", response);
        try {
            JSONObject obj = new JSONObject(response);

            Assert.assertEquals("Messages should be equal", q, obj.get("_text"));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}