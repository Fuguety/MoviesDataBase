package es.upsa.sbd2.trabajo1.apis;

import lombok.Builder;

import java.util.List;


public class RapidApi {
    private IMDB8Api imdb8APi;
    private MoviesDatabaseApi moviesApi;
    private IMDBcomApi imdbComApi;

    @Builder(setterPrefix = "with")
    public RapidApi(String rapidApiKey) {
        imdb8APi = IMDB8Api.builder().withRapidApiKey(rapidApiKey).build();
        imdbComApi = IMDBcomApi.builder().withRapidApiKey(rapidApiKey).build();
        moviesApi = MoviesDatabaseApi.builder().withRapidApiKey(rapidApiKey).build();
    }


    public String searchSeriesByExactTitle(String title) throws RapidApiException {
        return moviesApi.findSeriesByExactTitle(title);
    }

    public String findRating(String id) throws RapidApiException {
        return moviesApi.findRating(id);
    }


    public String findEpisodes(String seriesId) throws RapidApiException {
        return moviesApi.findEpisodes(seriesId);
    }


    public String findEpisodesByIds(List<String> ids) throws RapidApiException {
        return moviesApi.findEpisodesByIds(ids);
    }


    public String findTopCast(String id) throws RapidApiException {
        return imdbComApi.findTopCast(id);
    }

    public String findActorById(String id) throws RapidApiException {
        return imdb8APi.findActorById(id);
    }

}
