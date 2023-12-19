package es.upsa.sbd2.trabajo1;

import es.upsa.sbd2.trabajo1.apis.RapidApiException;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws RapidApiException, IOException {
        // Request all data
        boolean debug = true;
        String series = "The Sopranos";
        String apiKey = "Key Here";
        boolean works = RequestData.RequestData(series, apiKey);

        if (works) {
            BuildJson.BuildJson(series);
            if (debug) {
                DeleteFiles.deleteFiles();
            }
        } else {
            System.out.println("Insert a Valid series");
        }



    }
}
