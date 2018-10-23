import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Request is used to send GET and POST requests.
 */
public class Request {
    /**
     * Create and return a URL object.
     *
     * @param baseUrl The base url (website).
     * @param path The API endpoint.
     * @param params Key value pairs for parameters.
     * @return URL object.
     */
    private URL makeUrl(String baseUrl, String path, JSONObject params) {
        URL url = null;
        String urlString = baseUrl + path;
        if (params != null) {
            urlString += "?";
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(key).append("=").append(params.getString(key));
            }
            urlString += stringBuilder.toString();
        }
        System.out.println(urlString);
        try {
            url = new URL(urlString);
        } catch (IOException e) {
            System.out.println("Something went wrong with the URL: " + e.getMessage());
        }
        return url;
    }
}
