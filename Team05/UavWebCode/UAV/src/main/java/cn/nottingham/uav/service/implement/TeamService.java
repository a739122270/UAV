package cn.nottingham.uav.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nottingham.uav.entity.Team;
import cn.nottingham.uav.entity.Uav;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.TeamMapper;
import cn.nottingham.uav.mapper.UavMapper;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.exception.DeleteException;
import cn.nottingham.uav.service.exception.EnqueueException;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.PermissionOperationException;
import cn.nottingham.uav.service.exception.ReachMaximumSizeException;
import cn.nottingham.uav.service.exception.TeamNotFoundException;
import cn.nottingham.uav.service.exception.UpdateException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.interfaces.ITeamService;

@Service
public class TeamService implements ITeamService{

	@Autowired
	public TeamMapper teamMapper;
	@Autowired
	public UserMapper userMapper;
	@Autowired
	public UavMapper uavMapper;
	@Override
	@Transactional
	public void establishTeam(Team team,String username,Integer userId) 
			throws InsertException,UserNotFoundException,TeamNotFoundException, UpdateException{	

		checkPermission(userId);
		
		Date now = new Date();
		team.setOwner(username);
		team.setCreatedTime(now);
		team.setCreatedUser(username);
		team.setModifiedTime(now);
		team.setModifiedUser(username);	
		team.setIsDelete(0);
		
		Integer row=teamMapper.establishTeam(team);
		if(!row.equals(1)) {
			throw new InsertException("Establish failure！please contact with administrator");
		}
		
		Team newTeam =findTeamByOwner(username);
		
		Integer row2 = userMapper.updateTeamId(userId,newTeam.getTeamId(),username,now);	
		if(!row2.equals(1)) {
			throw new UpdateException("Modify exception! Please contact the administrator");
		}	
		
	}
	
	/**
	 * transfer ownership of team must satisfy following conditions:
	 * 1. user is team's current owner
	 * 2. the target user must be in same team.
	 */
	@Override
	@Transactional
	public void transferOwnership(String username, Integer userId, String newOwnerName) {
		
		User oldOwner = checkPermission(userId);
		
		User newOwner = userMapper.findUserByTeamIdAndUserName(oldOwner.getTeamId(),newOwnerName);
		if(newOwner == null || newOwner.getIsDelete().equals(1)) {
			throw new UserNotFoundException("transfer failure, the user is not in this team.");
		}
		
		Team team =findTeamByOwner(username);
		
		Date now = new Date();
		team.setOwner(newOwnerName);
		team.setModifiedTime(now);
		team.setModifiedUser(username);	

		updateTeam(team);	

	}
	
	@Override
	public void quitTeam(String username, Integer userId) {
		
		User user = checkPermission(userId);
		Team team = teamMapper.findTeamByOwner(username);
		
		if(team != null) {
			throw new PermissionOperationException("Quit failure, please transfer team ownership before quitting.");
		}
		Integer myTeamId = user.getTeamId();
		
		if(myTeamId == null) {
			throw new TeamNotFoundException("Team does not exist.");
		}
		
		findTeamByTeamId(myTeamId);
		
		Date now = new Date();	
		userMapper.updateTeamId(userId, null, username, now);
	}
	
	@Override
	public void addTeam(String username, Integer userId, Integer teamId) {
		
		checkPermission(userId);
		
		Team team = findTeamByTeamId(teamId);
		
		if(team == null || team.getIsDelete().equals(1)) {
			throw new TeamNotFoundException("Join error! Team does not exist.");
		}
		
		List<User> list = displayMembers(teamId);
		if(list.size() == team.getMaxMemberNumber()) {
			throw new ReachMaximumSizeException("Join error! The maximun size has been reached.");
		}	
		Date now = new Date();	
		userMapper.updateTeamId(userId, teamId, username, now);
	}
	
	@Override
	public List<User> displayMembers(Integer teamId) {
		List<User> list = userMapper.findUsersByTeamId(teamId);
		return list;
	}
	
	@Override
	public Team getTeamInfo(String username, Integer userId) {
		User user = checkPermission(userId);
		
		Integer myTeamId = user.getTeamId();
		Team team = findTeamByTeamId(myTeamId);
		
		return team;
	}
	
	@Override
	public Boolean checkOwnership(String username, Integer userId) {
		Team team = getTeamInfo(username,userId);
		if(team == null) {
			return false;
		}
		if(username.equals(team.getOwner())) {
			return true;
		}
		return false;
	}
	
	@Override
	public void deleteMember(String username, Integer userId, Integer deleteUserId) {
		boolean b = checkOwnership(username, userId);
		if(b) {
			Date now = new Date();
			userMapper.updateTeamId(deleteUserId, null, username, now);
		}else{
			throw new PermissionOperationException("Delete error! you don't have this permission.");
		}
		
	}
	@Override
	public void addMember(String username, Integer userId, String newMemberName) {
		boolean b = checkOwnership(username, userId);
		if(b) {
			Team team = getTeamInfo(username,userId);
			User newMember = userMapper.findUserByName(newMemberName);
			if(newMember == null ||newMember.getIsDelete().equals(1)) {
				throw new UserNotFoundException("Add error! User does not exist.");
			}
			if(newMember.getTeamId() != null) {
				throw new EnqueueException("Error! This user already has a team.");
			}
			
			List<User> list = displayMembers(team.getTeamId());
			if(list.size() == team.getMaxMemberNumber()) {
				throw new ReachMaximumSizeException("Error! The maximun size has been reached.");
			}	
			
			Date now = new Date();
			userMapper.updateTeamId(newMember.getUserId(),team.getTeamId(), username, now);
		}else{
			throw new PermissionOperationException("Error! you don't have this permission.");
		}
		
	}
	
	@Override
	public void updateTeamInfo(String username, Integer userId, Team newTeam) {
		boolean b = checkOwnership(username, userId);
		if(b) {
			Team oldTeam = getTeamInfo(username,userId);	
			
			oldTeam.setTeamName(newTeam.getTeamName());
			oldTeam.setFlightArea(newTeam.getFlightArea());
			oldTeam.setIsPrivate(newTeam.getIsPrivate());
			
			List<User> list = displayMembers(oldTeam.getTeamId());
			if(list.size() > newTeam.getMaxMemberNumber()) {
				throw new ReachMaximumSizeException("Modify error! The Number of members can't be larger than maximum");
			}
			oldTeam.setMaxMemberNumber(newTeam.getMaxMemberNumber());
			
			oldTeam.setUavId(newTeam.getUavId());
			
			Date now = new Date();
			oldTeam.setModifiedTime(now);
			oldTeam.setModifiedUser(username);	
			updateTeam(oldTeam);
		}else{
			throw new PermissionOperationException("Add error! you don't have this permission.");
		}
	}
	
	@Override
	public List<Team> findTeamsByUavType(Integer userId, String uavType, String condition,
			String order) {
		
		checkPermission(userId);
		Uav uav = uavMapper.findUavByUavType(uavType);
		List<Team> teams = teamMapper.findTeamsByUavId(uav.getId(), condition, order);	
		return checkPrivacy(teams);
	}
	
	@Override
	public List<Team> findTeamsByCondition(Integer userId, String condition, String order) {
		
		checkPermission(userId);
		List<Team> teams = teamMapper.findTeamsByCondition(condition, order);	
		return checkPrivacy(teams);
		
	}
	
	@Override
	@Transactional
	public void disbandTeam(String username, Integer userId) {
		boolean b = checkOwnership(username, userId);
		if(b) {
			Team team = teamMapper.findTeamByOwner(username);
			List<User> users= userMapper.findUsersByTeamId(team.getTeamId());
			for(User user:users) {
				Date now = new Date();
				userMapper.updateTeamId(user.getUserId(), null, username, now);
			}
			int row = teamMapper.deleteTeam(team.getTeamId());
			if(row != 1) {
				throw new DeleteException("Disband error! please contact with administrator.");
			}
		}
	}
	
	@Override
	public List<Team> findTeamByTeamId(Integer teamId,Integer userId) {
		checkPermission(userId);
		List<Team> list = new ArrayList<Team>();
		Team team = findTeamByTeamId(teamId);
		if(team == null) {
			return null;
		}
		list.add(team);
		return checkPrivacy(list);	
	}
	
	@Override
	public List<Team> findTeamsByTeamName(String teamName, Integer userId) {
		checkPermission(userId);
		List<Team> list = teamMapper.findTeamsByTeamName(teamName);
		return checkPrivacy(list);
	}
	
	private User checkPermission(Integer userId) {
		User userInfo = userMapper.findUserByUserId(userId);
		if(userInfo == null || userInfo.getIsDelete().equals(1)) {
			throw new UserNotFoundException("No permission, user information is invalid");
		}
		return userInfo;
	}
	
	private List<Team> checkPrivacy(List<Team> teams){
		if(!teams.isEmpty()) {
			Iterator<Team> iterator=teams.iterator();
			//Remove private teams
			while(iterator.hasNext()) {
				Team team = iterator.next();
				if(team.getIsPrivate() == 1) {
					iterator.remove();
				}
			}
			if(!teams.isEmpty()) {
				return teams;
			}else {
				return null;
			}
		}
		return null;
	}
	
	private Team findTeamByOwner(String owner) {
		Team team = teamMapper.findTeamByOwner(owner);
		if(team == null || team.getIsDelete().equals(1)) {
			throw new TeamNotFoundException("队伍数据不存在！");
		}
		return team;
	}
	
	private Team findTeamByTeamId(Integer teamId) {	
		Team team = teamMapper.findTeamByTeamId(teamId);
		if(team == null || team.getIsDelete().equals(1)) {
			return null;
		}
		return team;
	}
	
	private void updateTeam(Team team) {			
		Integer row = teamMapper.updateTeam(team);
		if(!row.equals(1)) {
			throw new UpdateException("修改队伍信息异常！请联系管理员");
		}	
	}

}
