package es.upsa.sbd2.trabajo1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JsonUtils {

    public static void writeIndentedJsonToFile(String jsonData, String filePath, int spaces) {
        try {
            // Manually add indentation
            String indentedJsonData = addIndentation(jsonData, spaces);

            // Create a FileWriter object
            FileWriter fileWriter = new FileWriter(filePath);

            // Write the indented JSON data to the file
            fileWriter.write(indentedJsonData);

            // Close the FileWriter
            fileWriter.close();

            System.out.println("Indented JSON data has been written to the file: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String addIndentation(String jsonString, int spaces) {
        StringBuilder indentedJson = new StringBuilder();

        int currentIndentation = 0;
        boolean insideString = false;

        for (char c : jsonString.toCharArray()) {
            if (c == '"' && (indentedJson.length() == 0 || indentedJson.charAt(indentedJson.length() - 1) != '\\')) {
                insideString = !insideString;
            }

            if (!insideString) {
                if (c == '{' || c == '[') {
                    currentIndentation += spaces;
                    appendNewLineAndIndentation(indentedJson, currentIndentation);
                } else if (c == '}' || c == ']') {
                    currentIndentation -= spaces;
                    appendNewLineAndIndentation(indentedJson, currentIndentation);
                }

                if (indentedJson.length() == 0) {
                    appendNewLineAndIndentation(indentedJson, currentIndentation);
                }

                indentedJson.append(c);

                if (c == ',' || c == '{' || c == '[') {
                    appendNewLineAndIndentation(indentedJson, currentIndentation);
                }
            } else {
                indentedJson.append(c);
            }
        }

        return indentedJson.toString();
    }

    private static void appendNewLineAndIndentation(StringBuilder stringBuilder, int spaces) {
        stringBuilder.append("\n");
        for (int i = 0; i < spaces; i++) {
            stringBuilder.append(" ");
        }
    }

    public static String extractIdFromJsonFile(String jsonFilePath) {
        try {
            String jsonData = readFileAsString(jsonFilePath);

            String idValue = extractIdFromJson(jsonData);

            if (idValue != null) {
                return idValue;
            } else {
                System.out.println("ID not found in the JSON data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if error
    }

    private static String readFileAsString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (Scanner scanner = new Scanner(path)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private static String extractIdFromJson(String jsonData) {

        int idIndex = jsonData.indexOf("\"id\":\"");

        if (idIndex != -1) {
            idIndex += 6;
            int endIndex = jsonData.indexOf("\"", idIndex);
            if (endIndex != -1) {
                return jsonData.substring(idIndex, endIndex);
            }
        }

        return null;
    }

    public static List<String> extractTop5Ids(String jsonFilePath) {
        try {
            String jsonData = readFileAsString(jsonFilePath);

            List<String> top5Ids = extractTop5IdsFromJson(jsonData);

            if (!top5Ids.isEmpty()) {
                return top5Ids;
            } else {
                System.out.println("IDs not found in the JSON data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Return an empty list if there's an error or if the "id" values are not found
    }

    private static List<String> extractTop5IdsFromJson(String jsonData) {
        List<String> top5Ids = new ArrayList<>();

        int index = jsonData.indexOf("\"data\":");
        while (index != -1 && top5Ids.size() < 5) {
            int idIndex = jsonData.indexOf("\"id\":\"", index);

            if (idIndex != -1) {
                idIndex += 6;

                int endIndex = jsonData.indexOf("\"", idIndex);
                if (endIndex != -1) {
                    top5Ids.add(jsonData.substring(idIndex, endIndex));
                    index = endIndex + 1; // Move the index to the next position to search for the next ID
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return top5Ids;
    }

}
