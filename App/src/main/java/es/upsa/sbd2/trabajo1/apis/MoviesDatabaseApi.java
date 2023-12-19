package es.upsa.sbd2.trabajo1.apis;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder(setterPrefix = "with")
class MoviesDatabaseApi extends RapidApiHttpClient {
    private String rapidApiKey;
    public static final String X_RapidAPI_Host = "moviesdatabase.p.rapidapi.com";

    String findSeriesByExactTitle(String title) throws RapidApiException {
        Map<String, String> parameters = Map.of("exact", "true",
                "titleType", "tvSeries");
        return findByTitle(title, parameters);
    }

    private String findByTitle(String title, Map<String, String> parameters) throws RapidApiException {
        List<String> paths = List.of(title);
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        return get("https://moviesdatabase.p.rapidapi.com/titles/search/title", paths, parameters, headers);
    }

    String findEpisodes(String seriesId) throws RapidApiException {
        List<String> paths = List.of(seriesId);
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        return get("https://moviesdatabase.p.rapidapi.com/titles/series", paths, null, headers);
    }

    String findRating(String id) throws RapidApiException {
        List<String> paths = List.of(id, "ratings");
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        return get("https://moviesdatabase.p.rapidapi.com/titles", paths, null, headers);
    }


    String findEpisodesByIds(List<String> ids) throws RapidApiException {
        Map<String, String> queryParams = Map.of("idsList", ids.stream().collect(Collectors.joining(",")));
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        return get("https://moviesdatabase.p.rapidapi.com/titles/x/titles-by-ids", null, queryParams, headers);
    }


}
