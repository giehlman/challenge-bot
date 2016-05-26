package chatbot;

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

    @Test
    public void talkToWit() throws Exception {
        String[] qs = new String[]{"How many users do you have?",
                "Are you currently using another live chat platform on the site?",
                "Could you please give me a rough estimate of the number of monthly visits on your website?",
                "How many agents will you have on your side, handling your incoming chats?",
                "May I please ask for your website URL?",
                "Hello, I'm here to answer your questions about LivePerson services.",
                "How can I help you today?"};

        String response;
        for (int i = 0; i < qs.length; i++) {
            response = handler.talkToWit(qs[i]);
            if (response != null) {
                System.out.println(response);
            }
        }
    }
}