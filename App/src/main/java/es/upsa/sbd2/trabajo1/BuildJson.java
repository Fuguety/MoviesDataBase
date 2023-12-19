package es.upsa.sbd2.trabajo1;

import java.io.IOException;
import java.util.List;

import static es.upsa.sbd2.trabajo1.GetDataJsonMovies.getDataFromJson;

public class BuildJson {
    public static void BuildJson(String title) throws IOException {
        // Movie Data
        List<MovieData> movieDataList = getDataFromJson(title);
        String movieDataListString = String.valueOf(movieDataList);

        // Rating
        RatingData ratingData = GetRatingJson.getRatingFromJson();
        String ratingDataString = String.valueOf(ratingData);

        // All episodes
        String episodeData = ExtractEpisodeInfo.extractEpisodeInfo("Episodes.json");


        // Actor Info
        String actorId = null;
        String actorName = null;
        List<String> actorInfoList = GetActorInfoJson.getActorInfoList(actorId, actorName);
        String actorInfoListString = String.valueOf(actorInfoList);

        String allData = movieDataListString + ratingDataString + episodeData + actorInfoListString;


        JsonUtils.writeIndentedJsonToFile(allData, "Results.json", 2);
        //BuildJsonB.buildJsonB(allData, "Results-JSON-B.json"); // Json B not working
        BuildJsonP.buildJsonP("results:", allData, "Results-JSON-P.json");

    }

}
