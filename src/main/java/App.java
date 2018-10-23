import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * NTNU ID203012 2018 Assignment 5 - WebService REST API client
 */
public class App {
    public static void main(String[] args) {
        App app = new App();
        int sessionId = app.auth();
        System.out.println(sessionId);
    }

    /**
     * Authorize a student at NTNU and return a session ID that will
     * be used for the remaining requests.
     *
     * @return The session ID.
     */
    private int auth() {
        int sessionId = 0;
        Properties properties = new Properties();
        String fileName = "src/main/resources/auth.config";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to load file: " + e.getMessage());
        }
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                System.out.println("Failed to load file: " + e.getMessage());
            }

            JSONObject data = new JSONObject();
            data.put("email", properties.get("auth.email"));
            data.put("phone", properties.get("auth.phone"));
            Request request = new Request();
            String result = request.post("auth", data);
            JSONObject resultData = new JSONParser().toJSON(result);
            System.out.println(resultData.toString());


            if (result.length() > 0 && resultData.has("success")) {
                if (resultData.getBoolean("success")) {
                    sessionId = resultData.getInt("sessionId");
                }
            }
        }
        return sessionId;
    }
}
