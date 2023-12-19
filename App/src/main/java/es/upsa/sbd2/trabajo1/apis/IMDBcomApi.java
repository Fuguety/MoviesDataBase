package es.upsa.sbd2.trabajo1.apis;

import lombok.Builder;

import java.util.Map;

@Builder(setterPrefix = "with")
public class IMDBcomApi extends RapidApiHttpClient {
    private String rapidApiKey;
    public static final String X_RapidAPI_Host = "imdb-com.p.rapidapi.com";

    String findTopCast(String id) throws RapidApiException {
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        Map<String, String> parameters = Map.of("tconst", id);
        return get("https://imdb-com.p.rapidapi.com/title/top-cast", null, parameters, headers);
    }
}
