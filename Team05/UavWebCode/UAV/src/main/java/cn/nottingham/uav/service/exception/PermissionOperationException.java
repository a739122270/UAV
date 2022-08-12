package cn.nottingham.uav.service.exception;
/**
 * throw when permission is limited
 * @author lxwzf
 *
 */
public class PermissionOperationException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public PermissionOperationException() {
		super();
	}

	public PermissionOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PermissionOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionOperationException(String message) {
		super(message);
	}

	public PermissionOperationException(Throwable cause) {
		super(cause);
	}
	
	
}
