package cn.nottingham.uav.service.interfaces;

import java.util.List;

import cn.nottingham.uav.entity.Team;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.TeamNotFoundException;
import cn.nottingham.uav.service.exception.UpdateException;
import cn.nottingham.uav.service.exception.UserNotFoundException;

public interface ITeamService {
	/**
	 * create a team
	 * @param team
	 * @param username
	 * @param userId
	 * @throws InsertException
	 * @throws UserNotFoundException
	 * @throws TeamNotFoundException
	 * @throws UpdateException
	 */
	public void establishTeam(Team team,String username,Integer userId) 
			throws InsertException,UserNotFoundException,TeamNotFoundException,UpdateException;
	/**
	 * transfer ownership to one member
	 * @param username
	 * @param userId
	 * @param newOwnerName
	 */
	public void transferOwnership(String username,Integer userId,String newOwnerName);
	
	/**
	 * quit the team
	 * @param username
	 * @param userId
	 */
	public void quitTeam(String username,Integer userId);
	/**
	 * join a team
	 * @param username
	 * @param userId
	 * @param teamId
	 */
	public void addTeam(String username,Integer userId,Integer teamId);
	
	/**
	 * display the members who are in the specific team according to teamId
	 * @param teamId
	 * @return
	 */
	public List<User> displayMembers(Integer teamId);
	
	/**
	 * get team info
	 * @param username
	 * @param userId
	 * @return
	 */
	public Team getTeamInfo(String username,Integer userId);
	/**
	 * check if someone has the ownership of the specific team
	 * @param username
	 * @param userId
	 * @return
	 */
	public Boolean checkOwnership(String username,Integer userId);
	/**
	 * delete one member from the team.(owner's permission)
	 * @param username
	 * @param userId
	 * @param deleteUserId
	 */
	public void deleteMember(String username,Integer userId,Integer deleteUserId);
	/**
	 * add one member to the team.(owner's permission)
	 * @param username
	 * @param userId
	 * @param newMemberName
	 */
	public void addMember(String username,Integer userId,String newMemberName);
	/**
	 * update the team info
	 * @param username
	 * @param userId
	 * @param team
	 */
	public void updateTeamInfo(String username,Integer userId,Team team);
	/**
	 * According to uav type to return a list of teams
	 * @param userId
	 * @param uavType
	 * @param condition
	 * @param order
	 * @return
	 */
	public List<Team> findTeamsByUavType(Integer userId, String uavType, String condition,String order);
	/**
	 * According to specific condition to return a list of teams
	 * @param userId
	 * @param condition
	 * @param order
	 * @return
	 */
	public List<Team> findTeamsByCondition(Integer userId, String condition, String order);
	
	/**
	 * disband the team (owner's permission)
	 * @param username
	 * @param userId
	 */
	public void disbandTeam(String username,Integer userId);
	
	/**
	 * get team By team Id
	 * @param teamId
	 * @param userId
	 * @return
	 */
	public List<Team> findTeamByTeamId(Integer teamId, Integer userId);
	
	/**
	 * find teams by teamName
	 * @param teamName
	 * @param userId
	 * @return
	 */
	public List<Team> findTeamsByTeamName(String teamName, Integer userId); 
	
}
