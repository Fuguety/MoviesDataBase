package es.upsa.sbd2.trabajo1;

import es.upsa.sbd2.trabajo1.apis.RapidApi;
import es.upsa.sbd2.trabajo1.apis.RapidApiException;

import java.util.ArrayList;
import java.util.List;


public class RequestData {

    public static boolean RequestData(String series, String ApiKey) throws RapidApiException {
        // Rapid Api
        RapidApi rapidApi = RapidApi.builder()
                .withRapidApiKey(ApiKey)
                .build();

        // Search Movie/Series
        String jsonDataSeries = rapidApi.searchSeriesByExactTitle(series);

        // Check if the request worked
        boolean containsNextNull = jsonDataSeries.contains("\"results\":[]");
        if (containsNextNull) {
            System.out.println("Error, please insert a valid Series/Movie");
            return false;
        } else {
            JsonUtils.writeIndentedJsonToFile(jsonDataSeries, "Movies.json", 4);

            String getId = JsonUtils.extractIdFromJsonFile("Movies.json");


            // Get the specific data
            RequestDataEpisodes.requestDataEpisodes(rapidApi, getId); // Get data from all episodes
            String jsonDataRating = rapidApi.findRating(getId);
            String jsonDataTopCast = rapidApi.findTopCast(getId);

            // Format jsons
            JsonUtils.writeIndentedJsonToFile(jsonDataRating, "Rating.json", 4);
            JsonUtils.writeIndentedJsonToFile(jsonDataTopCast, "TopCast.json", 4);


            // Get the Top 5 Actors
            List<String> top5Ids = JsonUtils.extractTop5Ids("TopCast.json");

            // Show if ID list is empty
            if (top5Ids.isEmpty()) {
                System.out.println("No IDs found or an error occurred.");
            }


            // Gets the Actors and put them on different Jsons
            for (int i = 0; i < top5Ids.size(); i++) {
                String idActor = top5Ids.get(i);
                List<String> actorDataList = new ArrayList<>();
                String jsonDataActorId = rapidApi.findActorById(idActor);
                actorDataList.add(jsonDataActorId);

                String fileName = String.format("Actor%d.json", i + 1);

                JsonUtils.writeIndentedJsonToFile(actorDataList.toString(), fileName, 4);

            }
            return true;
        }
    }

}
