package es.upsa.sbd2.trabajo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRatingJson {

    public static RatingData getRatingFromJson() {
        try {
            // Read the content of the JSON file
            String content = new String(Files.readAllBytes(Paths.get("Rating.json")));

            // Extract the "results" object content using a simple regex
            Pattern pattern = Pattern.compile("\"results\":\\s*\\{([^}]+)\\}");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                String resultsObjectContent = matcher.group(1);

                // Add back the curly braces to make the string a valid JSON object
                String jsonResultsObject = "{" + resultsObjectContent + "}";

                // Extract data from the JSON object
                String tconst = extractField(jsonResultsObject, "tconst");
                double averageRating = extractDoubleField(jsonResultsObject, "averageRating");
                int numVotes = extractIntField(jsonResultsObject, "numVotes");

                return new RatingData(averageRating, numVotes);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String extractField(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + field + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private static double extractDoubleField(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + field + "\":(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        return 0.0;
    }

    private static int extractIntField(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + field + "\":(\\d+)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }
}
