package es.upsa.sbd2.trabajo1.apis;

public class RapidApiException extends Exception {
    public RapidApiException(String message) {
        super(message);
    }

    public RapidApiException(Throwable cause) {
        super(cause);
    }
}
