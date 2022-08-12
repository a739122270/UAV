package cn.nottingham.uav.service.interfaces;

import java.util.List;

import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.exception.FormIncompleteException;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.PasswordNotMatchException;
import cn.nottingham.uav.service.exception.UpdateException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.exception.UsernameDuplicateException;

/**
 * The parent interface of the business layer user functionality
 * @author XuHui Wei
 *
 */
public interface IUserService {
	/**
	 * user register
	 * @param user
	 */
	public void register(User user) throws UsernameDuplicateException, InsertException, FormIncompleteException;
	/**
	 * user login
	 * @param username
	 * @param password
	 * @return user
	 */
	public User login(String username,String password) throws UserNotFoundException,PasswordNotMatchException;
	/**
	 * change user info
	 * @param uid
	 * @param username
	 * @param user
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 */
	public void changeInfo(Integer userId,String username,User user) throws UserNotFoundException,UpdateException;
	/**
	 * get user info by userId
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public User getUserInfoByUserId(Integer userId) throws UserNotFoundException;
	/**
	 * change avatar by upload file
	 * @param userId
	 * @param avatar
	 * @param modifiedUser
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 */
	public void changeAvatar (Integer userId,String avatar,String modifiedUser) throws UserNotFoundException, UpdateException;
	/**
	 * add uav type to one user.
	 * @param username
	 * @param userId
	 * @param uavId
	 */
	public void addUavToUser(String username,Integer userId,Integer uavId);
	/**
	 * get taskId by userId
	 * @param userId
	 * @return
	 */
	public Integer getTaskIdByUserId(Integer userId);
	/**
	 * find user list by Alphabet of their name 
	 * @param order
	 * @return
	 */
	public List<User> findUserByAlphabet(String order); 
	/**
	 * freeze or unfreeze user account (administrator's permission)
	 * @param username
	 * @param adName
	 * @param isFreeze
	 */
	public void freezeAccount(String username,String adName,Integer isFreeze);
}
