package cn.nottingham.uav.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nottingham.uav.entity.Team;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.interfaces.ITeamService;
import cn.nottingham.uav.util.CityCoordinate;
import cn.nottingham.uav.util.JsonResult;

/**
 * The controller layer
 * @author Xuhui Wei 
 *
 */
@RestController
@RequestMapping("teams")
public class TeamController extends BaseController{
	@Autowired
	private ITeamService service;
	
	@PostMapping("establish")
	public JsonResult<Void> establishTeam(Team team, HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.establishTeam(team, username, userId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("transfer_ownership")
	public JsonResult<Void> transferOwnership(String newOwnerName, HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.transferOwnership(username, userId, newOwnerName);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("quit")
	public JsonResult<Void> quitTeam(HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.quitTeam(username, userId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("join")
	public JsonResult<Void> joinTeam(Integer teamId,HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.addTeam(username, userId,teamId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@GetMapping("get_team_info")
	public JsonResult<Team> getTeamInfo(HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		Team team = service.getTeamInfo(username, userId);
		return new JsonResult<Team>(SUCCESS,team);
	}
	
	@GetMapping("get_users_list")
	public JsonResult<List<User>> getUserList(Integer teamId){
		List<User> list = service.displayMembers(teamId);
		return new JsonResult<List<User>>(SUCCESS,list);
	}
	
	@GetMapping("check_ownership")
	public JsonResult<Boolean> checkOwnership(HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		boolean b = service.checkOwnership(username, userId);
		return new JsonResult<Boolean>(SUCCESS,b);
	}
	
	@PostMapping("delete_member")
	public JsonResult<Void> deleteMember(Integer deleteUserId,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.deleteMember(username, userId, deleteUserId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("add_member")
	public JsonResult<Void> addMember(String newMemberName,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.addMember(username, userId, newMemberName);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("disband_team")
	public JsonResult<Void> disbandTeam(String newMemberName,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.disbandTeam(username, userId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("modify")
	public JsonResult<Void> modifyTeam(Team team,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.updateTeamInfo(username, userId, team);
		return new JsonResult<Void>(SUCCESS);
	} 
	
	@GetMapping("search_teams_by_type")
	public JsonResult<List<Team>> searchTeamByUavType(String uavType,String condition,String order,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		List<Team> list = service.findTeamsByUavType(userId, uavType, condition, order);
		return new JsonResult<List<Team>>(SUCCESS,list);
	} 
	
	@GetMapping("search_teams_by_condition")
	public JsonResult<List<Team>> searchTeamByCondition(String condition,String order,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		List<Team> list = service.findTeamsByCondition(userId, condition, order);
		return new JsonResult<List<Team>>(SUCCESS,list);
	} 
	
	@GetMapping("search_team_by_teamId")
	public JsonResult<List<Team>> searchTeamByTeamId(Integer teamId, HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		List<Team> list = service.findTeamByTeamId(teamId, userId);
		return new JsonResult<List<Team>>(SUCCESS,list);
	} 
	
	@GetMapping("search_teams_by_teamName")
	public JsonResult<List<Team>> searchTeamByTeamName(String teamName, HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		List<Team> list = service.findTeamsByTeamName(teamName, userId);
		return new JsonResult<List<Team>>(SUCCESS,list);
	}
	
	@GetMapping("get_city_coordinate")
	public JsonResult<String[]> getCityCoordinate(String cityName) throws IOException{	
		String[] result = CityCoordinate.getCityCoordinate(cityName);
		return new JsonResult<String[]>(SUCCESS,result);
	}
}
