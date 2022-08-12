package cn.nottingham.uav.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.nottingham.uav.entity.User;

public interface UserMapper {
	/**
	 * register user
	 * @param user user information
	 * @return The number of rows that were executed successfully
	 */
	Integer addUser(User user);
	
	/**
	 * query information by user name
	 * @param username user name
	 * @return user information
	 */
	User findUserByName(String username);
	
	User findUserByTeamIdAndUserName(Integer teamId, String username);
	
	List<User> findUsersByTeamId(Integer teamId);
	
	List<User> findUsersByTaskId(Integer taskId);
	
	Integer getTaskIdByUserId(Integer userId);
	
	/**
	 * search user info by uid
	 * @param uid
	 * @return
	 */
	User findUserByUserId(Integer userId);
	
	/**
	 * update user info
	 * @param user
	 * @return
	 */
	Integer updateInfo(User user);
	
	/**
	 * update avatar
	 * @param userId
	 * @param avatar
	 * @param modifiedUser
	 * @param modifiedTime
	 * @return
	 */
	Integer updateAvatar(
			@Param("userId") Integer userId,
			@Param("avatar") String avatar,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	Integer updateUavId(
			@Param("userId") Integer userId,
			@Param("uavId") Integer uavId,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	Integer updateTeamId(
			@Param("userId") Integer userId,
			@Param("teamId") Integer teamId,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	Integer updateTaskId(
			@Param("userId") Integer userId,
			@Param("taskId") Integer taskId,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	Integer getUserIdByUsername(String username);
	
	List<User> findUsersByAlphabet(String order);
	
	Integer freezeAccount(
			@Param("username")String username,
			@Param("modifiedUser") String admname,
			@Param("isFreeze")Integer isFreeze,
			@Param("modifiedTime") Date modifiedTime);
}
