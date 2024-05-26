package common.exception;

;

/**
 * The MediaNotAvailableException notify user if the selected media is not available
 *
 */
public class MediaNotAvailableException extends RuntimeException{

    private static final long serialVersionUID = 1091337136123906298L;

    public MediaNotAvailableException() {

    }

    public MediaNotAvailableException(String message) {
        super(message);
    }

}