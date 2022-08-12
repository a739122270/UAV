package cn.nottingham.uav.service.exception;
/**
 * throw when number over the maximum size.
 * @author lxwzf
 *
 */
public class ReachMaximumSizeException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ReachMaximumSizeException() {
		super();
	}

	public ReachMaximumSizeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReachMaximumSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReachMaximumSizeException(String message) {
		super(message);
	}

	public ReachMaximumSizeException(Throwable cause) {
		super(cause);
	}
	
	
}
