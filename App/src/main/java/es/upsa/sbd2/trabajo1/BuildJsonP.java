package es.upsa.sbd2.trabajo1;


import javax.json.Json;
import javax.json.JsonObject;
import java.io.FileWriter;
import java.io.IOException;

public class BuildJsonP {
    public static void buildJsonP(String key, String value, String filePath) {
        String unescapedJsonString = value.replace("\n", "").replace("\"", "");

        JsonObject jsonObject = Json.createObjectBuilder()
                .add(key, unescapedJsonString)
                .build();

        String jsonString = formatJson(jsonObject.toString());

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatJson(String jsonString) {
        int indentLevel = 0;
        StringBuilder formattedJson = new StringBuilder();

        for (char character : jsonString.toCharArray()) {
            switch (character) {
                case '{':
                case '[':
                    formattedJson.append(character).append("\n").append(indent(++indentLevel));
                    break;
                case '}':
                case ']':
                    formattedJson.append("\n").append(indent(--indentLevel)).append(character);
                    break;
                case ',':
                    formattedJson.append(character).append("\n").append(indent(indentLevel));
                    break;
                default:
                    formattedJson.append(character);
            }
        }

        return formattedJson.toString();
    }

    private static String indent(int level) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indentation.append("    "); // Use 4 spaces for index
        }
        return indentation.toString();
    }
}
