package es.upsa.sbd2.trabajo1;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class BuildJsonB {
    public static void buildJsonB(Object yourObject, String filePath) {
        Jsonb jsonb = JsonbBuilder.create();

        String jsonString = jsonb.toJson(yourObject);

        // Write the JSON string to a file
        writeJsonToFile(jsonString, filePath);
    }

    private static void writeJsonToFile(String jsonString, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

