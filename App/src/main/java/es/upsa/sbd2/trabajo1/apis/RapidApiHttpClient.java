package es.upsa.sbd2.trabajo1.apis;

import org.apache.http.util.EncodingUtils;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


class RapidApiHttpClient {
    String get(String urlAddress, List<String> paths, Map<String, String> queryParams, Map<String, String> headers) throws RapidApiException {
        try {
            String theURL;
            if (Objects.nonNull(paths) && !paths.isEmpty()) {
                String additionalPaths = paths.stream()
                        .collect(Collectors.joining("/"));
                theURL = "%s/%s".formatted(urlAddress, additionalPaths);
            } else {
                theURL = urlAddress;
            }

            if (Objects.nonNull(queryParams) && !queryParams.isEmpty()) {
                String params = queryParams.entrySet()
                        .stream()
                        .map(entry -> "%s=%s".formatted(encodeUTF8(entry.getKey()), encodeUTF8(entry.getValue())))
                        .collect(Collectors.joining("&"));

                theURL = "%s?%s".formatted(theURL, params);
            }

            URL url = new URL(theURL);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

            HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().uri(uri);

            if (Objects.nonNull(headers) && !headers.isEmpty()) {
                headers.forEach(httpRequestBuilder::header);
            }

            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest httpRequest = httpRequestBuilder.GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            switch (httpResponse.statusCode()) {
                case 200:
                    return httpResponse.body();
                default:
                    throw new RapidApiException(httpResponse.body());
            }
        } catch (Exception exception) {
            throw new RapidApiException(exception);
        }

    }


    private String encodeUTF8(String value) {
        return EncodingUtils.getString(value.getBytes(), "UTF-8");
    }


}
