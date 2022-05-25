package isaproject.exception;

public class ReservedServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ReservedServiceException(final String msg) {
        super(msg);
    }

}
