package cn.nottingham.uav.controller;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nottingham.uav.controller.expection.FileEmptyException;
import cn.nottingham.uav.controller.expection.FileIOException;
import cn.nottingham.uav.controller.expection.FileSizeException;
import cn.nottingham.uav.controller.expection.FileStateException;
import cn.nottingham.uav.controller.expection.FileTypeException;
import cn.nottingham.uav.controller.expection.FileUploadException;
import cn.nottingham.uav.service.exception.DeleteException;
import cn.nottingham.uav.service.exception.EnqueueException;
import cn.nottingham.uav.service.exception.FormIncompleteException;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.PasswordNotMatchException;
import cn.nottingham.uav.service.exception.PermissionOperationException;
import cn.nottingham.uav.service.exception.ReachMaximumSizeException;
import cn.nottingham.uav.service.exception.ServiceException;
import cn.nottingham.uav.service.exception.TeamNotFoundException;
import cn.nottingham.uav.service.exception.UpdateException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.exception.UsernameDuplicateException;
import cn.nottingham.uav.util.JsonResult;

/**
 * Initialize error classes, declare session and status codes
 * @author Xuhui Wei 
 *
 */
public class BaseController {
	
	protected static final String SESSION_USERID="userId";
	protected static final String SESSION_USERNAME="username";
	
	protected static final Integer SUCCESS = 20;
	protected static final Integer ERROR_USERNAME_DUPLICATE = 30;
	protected static final Integer ERROR_USER_NOTFOUND = 31;
	protected static final Integer ERROR_PASSWORD_NOTMATCH = 32;
	protected static final Integer ERROR_FORM_INCOMPLETE = 33;
	protected static final Integer ERROR_PERMISSION_OPERATION = 34;
	protected static final Integer ERROR_TEAM_NOTFOUND = 35;
	protected static final Integer ERROR_ENQUEUE = 36;
	protected static final Integer ERROR_REACH_MAXIMUM_SIZE = 37;
	protected static final Integer ERROR_INSERT = 40;
	protected static final Integer ERROR_UPDATE = 41;
	protected static final Integer ERROR_DELETE = 42;
	protected static final Integer ERROR_FILE_EMPTY = 50;
	protected static final Integer ERROR_FILE_SIZE = 51;
	protected static final Integer ERROR_FILE_TYPE = 52;
	protected static final Integer ERROR_FILE_STATE = 53;
	protected static final Integer ERROR_FILE_IO = 54;
	
	/*
	 * unified handling of controller exceptions
	 * @param e exception object
	 * @return JsonResult exception response information
	 */
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
    @ResponseBody
    public JsonResult<Void> handleException(Throwable e) {
		JsonResult<Void> jr = new JsonResult<Void>(e.getMessage());
		if(e instanceof UsernameDuplicateException) {
			jr.setState(ERROR_USERNAME_DUPLICATE);
        }else if(e instanceof UserNotFoundException) {
        	jr.setState(ERROR_USER_NOTFOUND);
        }else if(e instanceof PasswordNotMatchException) {
        	jr.setState(ERROR_PASSWORD_NOTMATCH);
        }else if(e instanceof FormIncompleteException) {
        	jr.setState(ERROR_FORM_INCOMPLETE);
        }else if(e instanceof PermissionOperationException) {
        	jr.setState(ERROR_PERMISSION_OPERATION);
        }else if(e instanceof TeamNotFoundException) {
        	jr.setState(ERROR_TEAM_NOTFOUND);
        }else if(e instanceof ReachMaximumSizeException) {
        	jr.setState(ERROR_REACH_MAXIMUM_SIZE);
        }else if(e instanceof EnqueueException) {
        	jr.setState(ERROR_ENQUEUE);
        }else if(e instanceof InsertException){
            jr.setState(ERROR_INSERT);
        }else if(e instanceof DeleteException){
            jr.setState(ERROR_DELETE);
        }else if(e instanceof UpdateException){
            jr.setState(ERROR_UPDATE);
        }else if(e instanceof FileEmptyException){
            jr.setState(ERROR_FILE_EMPTY);
        }else if(e instanceof FileSizeException){
            jr.setState(ERROR_FILE_SIZE);
        }else if(e instanceof FileTypeException){
            jr.setState(ERROR_FILE_TYPE);
        }else if(e instanceof FileStateException){
            jr.setState(ERROR_FILE_STATE);
        }else if(e instanceof FileIOException){
            jr.setState(ERROR_FILE_IO);
        }
		return jr;
	}
}
