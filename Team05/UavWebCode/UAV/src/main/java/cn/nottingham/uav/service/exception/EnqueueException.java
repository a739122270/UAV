package cn.nottingham.uav.service.exception;

public class EnqueueException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public EnqueueException() {
		super();
	}

	public EnqueueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnqueueException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnqueueException(String message) {
		super(message);
	}

	public EnqueueException(Throwable cause) {
		super(cause);
	}
	
	
}
