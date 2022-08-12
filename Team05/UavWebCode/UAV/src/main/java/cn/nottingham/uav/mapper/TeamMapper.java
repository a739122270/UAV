package cn.nottingham.uav.mapper;

import java.util.List;

import cn.nottingham.uav.entity.Team;

public interface TeamMapper {
	/**
	 * establish a team
	 * @param team
	 * @return
	 */
	Integer establishTeam(Team team);
	/**
	 * find team info by owner name
	 * @param owner
	 * @return
	 */
	Team findTeamByOwner(String owner);
	/**
	 * find team info by teamId
	 * @param teamId
	 * @return
	 */
	Team findTeamByTeamId(Integer teamId);
	/**
	 * update Team By a new team info
	 * @param team
	 * @return
	 */
	Integer updateTeam(Team team);
	/**
	 * find teams by uavId
	 * @param uavId
	 * @param condition
	 * @param order
	 * @return
	 */
	List<Team> findTeamsByUavId(Integer uavId,String condition,String order);
	/**
	 * find teams by condition
	 * @param condition
	 * @param order
	 * @return
	 */
	List<Team> findTeamsByCondition(String condition,String order);
	/**
	 * find teams by team name
	 * @param teamName
	 * @return
	 */
	List<Team> findTeamsByTeamName(String teamName);
	/**
	 * delete team by teamId
	 * @param teamId
	 * @return
	 */
	Integer deleteTeam(Integer teamId);
		
}
