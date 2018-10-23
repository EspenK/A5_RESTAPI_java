import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONParser has methods for parsing strings and making JSON objects.
 */
public class JSONParser {
    /**
     * Parse a string and return a JSONObject.
     * @param string The string to parse.
     * @return A JSONObject.
     */
    public JSONObject toJSON(String string) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
        } catch (JSONException e) {
            System.out.println("Something went wrong when paring JSON: " + e.getMessage());
        }
        return jsonObject;
    }
}
