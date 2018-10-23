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
     * Make a GET request.
     *
     * @param path The API endpoint.
     * @param params The data to send in a POST request.
     * @return The body of the response as a string.
     */
    public String get(String path, JSONObject params) {
        String baseUrl = "http://104.248.47.74/dkrest/";
        URL url = makeUrl(baseUrl, path, params);
        return send("GET", url, null);
    }

    /**
     * Make a POST request.
     *
     * @param path The API endpoint.
     * @param data Key value pairs for parameters.
     * @return The body of the response as a string.
     */
    public String post(String path, JSONObject data) {
        String baseUrl = "http://104.248.47.74/dkrest/";
        URL url = makeUrl(baseUrl, path, null);
        return send("POST", url, data);
    }

    /**
     * Send a HTTP request to a webservice and return the body of the response.
     *
     * @param method The HTTP method (GET or POST).
     * @param url The URL object for the webservice.
     * @param data The data to send in a POST request.
     * @return The body of the response as a string.
     */
    private String send(String method, URL url, JSONObject data) {
        String result = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (method.toUpperCase().equals("POST")) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data.toString().getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                result = convertToString(inputStream);
                inputStream.close();
            } else {
                InputStream inputStream = connection.getErrorStream();
                result = convertToString(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with the connection: " + e.getMessage());
        }
        return result;
    }

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

    /**
     * Convert a input stream to string.
     *
     * @param inputStream The input stream.
     * @return The content of the input stream as a string.
     */
    private String convertToString(InputStream inputStream) {
        // Make a iterator out of the input stream by creating a scanner
        // and splitting on the beginning of each string.
        // Add each element of the scanner to the return string.
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
