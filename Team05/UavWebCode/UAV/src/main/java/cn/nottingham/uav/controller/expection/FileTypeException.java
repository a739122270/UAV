package cn.nottingham.uav.controller.expection;
/**
 * File format exception
 * @author XuhuiWei
 *
 */
public class FileTypeException extends FileUploadException {

	private static final long serialVersionUID = 1L;

	public FileTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileTypeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public FileTypeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public FileTypeException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public FileTypeException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
}
