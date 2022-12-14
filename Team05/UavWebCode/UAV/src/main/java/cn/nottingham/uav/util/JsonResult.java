package cn.nottingham.uav.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * self-defined data type as a tool of data transform 
 * @author XuhuiWei
 *
 * @param <T>
 */
public class JsonResult<T> {
	
	@JsonInclude(value=Include.ALWAYS)
	private Integer state;
	@JsonInclude(value=Include.NON_NULL)
	private String message;
	@JsonInclude(value=Include.ALWAYS)
	private T data;
	public JsonResult() {
		
	}
	public JsonResult(Integer state) {
		super();
		this.state = state;
	}
	
	public JsonResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}
	public JsonResult(String message) {
		this.message = message;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	 
}
