package Utils.DataReader;

import Utils.Logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class JsonReader {
    private String jsonReader;
    private final String jsonFileName;
    private static final String TEST_DATA_PATH =
            System.getProperty("user.dir") + "/src/test/resources/test-data/";

    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;

        try {
            File file = new File(TEST_DATA_PATH + jsonFileName + ".json");
            System.out.println("🔍 Trying to read JSON from: " + file.getAbsolutePath());

            if (!file.exists()) {
                throw new RuntimeException("❌ JSON file not found at: " + file.getAbsolutePath());
            }

            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(file));
            jsonReader = data.toJSONString();

            LogsManager.info("✅ JSON file loaded successfully: " + jsonFileName);

        } catch (Exception e) {
            LogsManager.error("❌ Error while reading JSON file: ", jsonFileName, e.getMessage());
            jsonReader = "{}"; // fallback to empty JSON
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            LogsManager.error("⚠️ Error while reading JSON path " + jsonPath + " from file " + jsonFileName + ": ", e.getMessage());
            return null;
        }
    }
}
