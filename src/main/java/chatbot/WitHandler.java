package chatbot;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Giehlman on 25.05.2016.
 */
public class WitHandler {
    private String _token = "BUNU44ZAVK2IIS2JUQN7CC6XEUVW7UHC";
    private String _apiversion = "20160525";
    private HashMap<String, String> _lookup;

    /**
     *
     * @param token
     * @param apiversion
     */
    public WitHandler(String token, String apiversion) {
        if (token != null) _token = token;
        if (apiversion != null ) _apiversion = apiversion;

        // I wish I could get all this from the Wit API as well...
        _lookup = new HashMap<String, String>();
        _lookup.put("How many users", "Currently we are 5 users");
        _lookup.put("number of monthly visits", "Around 1000");
        _lookup.put("another live chat", "Currently we use tawk.to");
        _lookup.put("confirm", "Yes");
        _lookup.put("general", "Alright cool");
        _lookup.put("howareyou", "Cool how are you?");
        _lookup.put("numberusers", "Around 5");
        _lookup.put("otherplatform", "Currently we use tawk.to");
        _lookup.put("website", "The website is www.briefmaus.de");
    }

    /**
     *
     * @param q
     * @return
     * @throws Exception
     */
    public String ask(String q) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder uri = new URIBuilder();
        uri.setScheme("https").setHost("api.wit.ai").setPath("/message").setParameter("v", _apiversion).setParameter("q", q);
        HttpGet get = new HttpGet(uri.build());
        get.addHeader("Authorization", "Bearer " + _token);
        CloseableHttpResponse response = client.execute(get);

        String content = null;
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            content = getStringFromStream(entity.getContent());

            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return content;
    }

    public static String getStringFromStream(InputStream content) {
        String inputLine ;
        BufferedReader br = new BufferedReader(new InputStreamReader(content));
        StringBuilder builder = new StringBuilder();
        try {
            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                builder.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * Transforms a Wit response into a reply sentence
     * @param ask
     * @return
     */
    public String talkToWit(String ask) {
        try {
            JSONObject response = new JSONObject(ask(ask));

            if (!response.has("entities") || response.getJSONObject("entities").keySet().size() == 0) return "Ok cool";

            JSONObject entities = response.getJSONObject("entities");

            if (entities.has("help_question")) {
                String help_question_value = entities.getJSONArray("help_question").getJSONObject(0).getString("value");

                if (_lookup.containsKey(help_question_value)) {
                    return _lookup.get(help_question_value);
                } else {
                    return "Could you explain that?";
                }
            } else if (ask.contains("website")){
                return "Website is www.briefmaus.de";
            } else if (ask.contains("30 day")) {
                return "That's awesome";
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
