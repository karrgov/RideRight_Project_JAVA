package itinerary.exception;

public class CityNotKnownException extends RuntimeException {

    public CityNotKnownException(String message) {
        super(message);
    }

    public CityNotKnownException(String message, Throwable cause) {
        super(message, cause);
    }

}
