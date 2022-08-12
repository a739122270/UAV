package cn.nottingham.uav.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Administrator class
 * define the attribute of the administrator
 * @author XuhuiWei
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class Administrator extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer admId;
	private String username;
	private String password;
	private String salt;
	public Integer getAdmId() {
		return admId;
	}
	public void setAdmId(Integer admId) {
		this.admId = admId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Override
	public String toString() {
		return "Administrator [admId=" + admId + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ "]";
	}
	
	
	
}
