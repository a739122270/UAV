package cn.nottingham.uav.service.exception;

public class TeamNotFoundException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TeamNotFoundException() {
		super();
	}

	public TeamNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TeamNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TeamNotFoundException(String message) {
		super(message);
	}

	public TeamNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
