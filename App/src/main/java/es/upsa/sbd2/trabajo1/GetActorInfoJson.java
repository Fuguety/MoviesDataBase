package es.upsa.sbd2.trabajo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetActorInfoJson {

    public static List<String> getActorInfoList(String actorId, String actorName) {
        List<String> actorInfoList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String actorJsonFileName = "Actor" + i + ".json";

            try {
                String content = new String(Files.readAllBytes(Paths.get(actorJsonFileName)));

                String birthDate = extractNestedField(content, "birthDate");
                String gender = extractField(content, "gender");
                String urlImage = extractNestedField(content, "image.url");
                String minibios = extractMiniBiosText(content);

                // Move these lines inside the loop
                actorId = extractField(content, "id");
                actorName = extractField(content, "name");

                // Create a formatted string with the extracted data
                String actorInfo = String.format("ID: %s, Name: %s, Birth Date: %s, Gender: %s, URL Image: %s, Mini Bios: %s",
                        actorId, actorName, birthDate, gender, urlImage, minibios);

                actorInfoList.add(actorInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return actorInfoList;
    }

    private static String extractField(String json, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    private static String extractNestedObject(String json, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\":\\s*\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


    private static String extractNestedField(String json, String... fieldPath) {
        for (String field : fieldPath) {
            if (field.contains(".")) {
                String[] nestedFields = field.split("\\.");
                json = extractNestedObject(json, nestedFields[0]);
                field = nestedFields[1];
            }

            Pattern pattern = Pattern.compile("\"" + field + "\":\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(json);

            if (matcher.find()) {
                json = matcher.group(1);
            } else {
                return null;  // Return null if the field is not found
            }
        }
        return json;
    }

    private static String extractMiniBiosText(String json) {
        Pattern pattern = Pattern.compile("\"miniBios\"\\s*:\\s*\\[\\s*\\{[^}]*\"text\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


}
