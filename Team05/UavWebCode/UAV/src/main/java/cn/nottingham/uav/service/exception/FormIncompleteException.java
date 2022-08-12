package cn.nottingham.uav.service.exception;

public class FormIncompleteException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public FormIncompleteException() {
		super();
	}

	public FormIncompleteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FormIncompleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormIncompleteException(String message) {
		super(message);
	}

	public FormIncompleteException(Throwable cause) {
		super(cause);
	}
	
	
}
