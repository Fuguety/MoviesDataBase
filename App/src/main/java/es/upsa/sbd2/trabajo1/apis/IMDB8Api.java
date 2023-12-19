package es.upsa.sbd2.trabajo1.apis;

import lombok.Builder;

import java.util.Map;

@Builder(setterPrefix = "with")
class IMDB8Api extends RapidApiHttpClient {
    private String rapidApiKey;
    public static final String X_RapidAPI_Host = "imdb8.p.rapidapi.com";


    String findActorById(String id) throws RapidApiException {
        Map<String, String> headers = Map.of("X-RapidAPI-Key", rapidApiKey,
                "X-RapidAPI-Host", X_RapidAPI_Host);
        Map<String, String> parameters = Map.of("nconst", id);
        return get("https://imdb8.p.rapidapi.com/actors/get-bio", null, parameters, headers);
    }

}
