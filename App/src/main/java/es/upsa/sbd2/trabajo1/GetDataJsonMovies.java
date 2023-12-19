package es.upsa.sbd2.trabajo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDataJsonMovies {

    public static List<MovieData> getDataFromJson(String Movietitle) {
        List<MovieData> movieDataList = new ArrayList<>();

        try {
            // Read the content of the JSON file
            String content = new String(Files.readAllBytes(Paths.get("Movies.json")));

            // Extract the 'results' array content using a simple regex
            Pattern pattern = Pattern.compile("\"results\":\\s*\\[([\\s\\S]+?)\\]");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                String resultsArrayContent = matcher.group(1);

                // Split the 'results' array content into individual movie objects
                String[] movieObjects = resultsArrayContent.split("\\},\\s*\\{");

                for (String movieObject : movieObjects) {
                    // Add back the curly braces to make each string a valid JSON object
                    String jsonMovieObject = "{" + movieObject + "}";

                    // Extract data from the JSON object
                    String id = extractField(jsonMovieObject, "id");
                    String imageUrl = extractNestedField(jsonMovieObject, "url");
                    String releaseDate = extractReleaseDate(jsonMovieObject);

                    // Create MovieData object and add to the list
                    MovieData movieData = new MovieData(id, Movietitle, imageUrl, releaseDate);
                    movieDataList.add(movieData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieDataList;
    }

    private static String extractField(String json, String... fieldPath) {
        for (String field : fieldPath) {
            int fieldIndex = json.indexOf("\"" + field + "\":");
            if (fieldIndex != -1) {
                int startIndex = json.indexOf("\"", fieldIndex + field.length() + 3) + 1;
                int endIndex = json.indexOf("\"", startIndex);
                return json.substring(startIndex, endIndex);
            }
        }
        return null; // Field not found
    }

    private static String extractNestedField(String json, String fieldName) {
        int fieldIndex = json.indexOf("\"" + fieldName + "\":");
        if (fieldIndex != -1) {
            int startIndex = json.indexOf("\"", fieldIndex + fieldName.length() + 3) + 1;
            int endIndex = json.indexOf("\"", startIndex);
            return json.substring(startIndex, endIndex);
        }
        return null; // Field not found
    }


    private static String extractReleaseDate(String json) {
        // Use a regular expression to directly match the "releaseDate" values
        Pattern pattern = Pattern.compile("\"releaseDate\":\\s*\\{\\s*\"day\":(\\d+),\\s*\"month\":(\\d+),\\s*\"year\":(\\d+)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String day = matcher.group(1);
            String month = matcher.group(2);
            String year = matcher.group(3);

            // Handle the case where any of the values are not numeric
            try {
                int dayValue = Integer.parseInt(day);
                int monthValue = Integer.parseInt(month);
                int yearValue = Integer.parseInt(year);

                return String.format("%04d-%02d-%02d", yearValue, monthValue, dayValue);
            } catch (NumberFormatException e) {
                // Handle the case where one or more values are not numeric
                return String.format("%s-%s-%s", year, month, day);
            }
        }

        return null;
    }


}

