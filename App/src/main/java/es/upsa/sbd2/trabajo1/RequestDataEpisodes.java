package es.upsa.sbd2.trabajo1;

import es.upsa.sbd2.trabajo1.apis.RapidApi;
import es.upsa.sbd2.trabajo1.apis.RapidApiException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestDataEpisodes {

    public static void requestDataEpisodes(RapidApi rapidApi, String getId) throws RapidApiException {
        String jsonDataEpisodes = rapidApi.findEpisodes(getId);

        JsonUtils.writeIndentedJsonToFile(jsonDataEpisodes, "Series.json", 4);

        List<String> episodeInfoList = extractEpisodeInfo(jsonDataEpisodes);

        System.out.println("Requesting Data for each specific episode");
        StringBuilder combinedJsonEpisodes = new StringBuilder();

        for (String episodeInfo : episodeInfoList) {
            String[] episode = episodeInfo.split(",");
            String jsonEpisode = rapidApi.findEpisodesByIds(Collections.singletonList(episode[0]));

            String rating = rapidApi.findRating(episode[0]);

            // Extract episode details
            List<String> episodeDetailsList = extractEpisodeDetails(jsonEpisode);

            int seasonNumber = Integer.parseInt(episode[1]);
            int episodeNumber = Integer.parseInt(episode[2]);

            String combinedEpisodeJson = createCombinedEpisodeJson(episodeDetailsList, rating, seasonNumber, episodeNumber);

            // Append the JSON episode data to the StringBuilder
            combinedJsonEpisodes.append(combinedEpisodeJson);
        }


        // Write the combined JSON data to the Episodes.json file
        JsonUtils.writeIndentedJsonToFile(String.valueOf(combinedJsonEpisodes), "Episodes.json", 4);
    }

    private static List<String> extractEpisodeInfo(String jsonData) {
        List<String> episodeInfoList = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(jsonData))) {
            JsonObject jsonObject = reader.readObject();
            JsonArray resultsArray = jsonObject.getJsonArray("results");

            for (int i = 0; i < resultsArray.size(); i++) {
                JsonObject resultObject = resultsArray.getJsonObject(i);
                String tconst = resultObject.getString("tconst");
                int seasonNumber = resultObject.getInt("seasonNumber");
                int episodeNumber = resultObject.getInt("episodeNumber");

                String episodeInfo = String.format("%s,%d,%d", tconst, seasonNumber, episodeNumber);

                episodeInfoList.add(episodeInfo);
            }
        }

        return episodeInfoList;
    }

    private static List<String> extractEpisodeDetails(String jsonData) {
        List<String> episodeDetailsList = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(jsonData))) {
            JsonObject jsonObject = reader.readObject();
            JsonArray resultsArray = jsonObject.getJsonArray("results");

            for (int i = 0; i < resultsArray.size(); i++) {
                JsonObject resultObject = resultsArray.getJsonObject(i);

                String id = resultObject.getString("id");
                JsonObject titleTextObject = resultObject.getJsonObject("titleText");
                String titleText = titleTextObject.getString("text");
                String url = extractImageUrl(resultObject);

                JsonObject releaseDateObject = resultObject.getJsonObject("releaseDate");
                int day = releaseDateObject.getInt("day");
                int month = releaseDateObject.getInt("month");
                int year = releaseDateObject.getInt("year");

                String releaseDate = String.format("%d-%02d-%02d", year, month, day);

                // Create a string with the extracted information
                String episodeDetails = String.format("{ \"id\": \"%s\", \"title\": \"%s\", \"urlImage\": \"%s\", \"releaseDate\": \"%s\" }", id, titleText, url, releaseDate);

                episodeDetailsList.add(episodeDetails);
            }
        }

        return episodeDetailsList;
    }

    private static String extractImageUrl(JsonObject resultObject) {
        JsonObject primaryImageObject = resultObject.getJsonObject("primaryImage");
        return primaryImageObject.getString("url");
    }

    private static String createCombinedEpisodeJson(List<String> episodeDetailsList, String rating, int seasonNumber, int episodeNumber) {
        StringBuilder combinedEpisodeJson = new StringBuilder("{ \"seasonNumber\": ").append(seasonNumber).append(", \"episodes\": [");

        JsonObject ratingObject = Json.createReader(new StringReader(rating)).readObject();

        JsonObject episodeDetailsObject = Json.createReader(new StringReader(episodeDetailsList.get(0))).readObject();

        // Create a JSON object for the episode
        JsonObject episodeObject = Json.createObjectBuilder()
                .add("id", ratingObject.getJsonObject("results").getString("tconst"))
                .add("episodeNumber", episodeNumber)
                .add("title", episodeDetailsObject.getString("title"))
                .add("urlImage", episodeDetailsObject.getString("urlImage"))
                .add("releaseDate", episodeDetailsObject.getString("releaseDate"))
                .add("rating", Json.createObjectBuilder()
                        .add("average", ratingObject.getJsonObject("results").getJsonNumber("averageRating").doubleValue())
                        .add("numVotes", ratingObject.getJsonObject("results").getInt("numVotes"))
                        .build())
                .build();

        combinedEpisodeJson.append(episodeObject.toString());

        combinedEpisodeJson.append("]},");

        return combinedEpisodeJson.toString();
    }


}
