package cn.nottingham.uav.service.interfaces;

import cn.nottingham.uav.entity.Administrator;
import cn.nottingham.uav.service.exception.PasswordNotMatchException;
import cn.nottingham.uav.service.exception.UserNotFoundException;


public interface IAdmService {
	/**
	 * administrator can login in  his(her) account by inputing right user name and password
	 * @param username
	 * @param password
	 * @return
	 * @throws UserNotFoundException
	 * @throws PasswordNotMatchException
	 */
	public Administrator login(String username,String password) throws UserNotFoundException,PasswordNotMatchException;
}
