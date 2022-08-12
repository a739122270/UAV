package cn.nottingham.uav.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nottingham.uav.entity.Task;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.interfaces.ITaskService;
import cn.nottingham.uav.service.interfaces.ITeamService;
import cn.nottingham.uav.util.JsonResult;
import cn.nottingham.uav.websocket.WebSocketServer;
/**
 * The controller layer
 * @author Xuhui Wei 
 *
 */
@RestController
@RequestMapping("tasks")
public class TaskController extends BaseController{
	
	@Autowired
	public ITaskService service;
	
	@Autowired
	private ITeamService teamService;
	
	@PostMapping("create_task")
	public JsonResult<Integer> createTask(Task task, HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		Integer taskId = service.createTask(task, username, userId);
		return new JsonResult<Integer>(SUCCESS,taskId);
	}
	@GetMapping("get_task_by_id")
	public JsonResult<Task> displayTask(Integer taskId){
		Task task = service.getTaskById(taskId);
		return new JsonResult<Task>(SUCCESS,task);
	}
	@GetMapping("delete_task")
	public JsonResult<Void> deleteTask(Integer taskId,HttpSession session){
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.deleteTask(taskId,username);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@GetMapping("send_taskId")
	public JsonResult<Void> sendTaskId(Integer taskId, Integer teamId) throws IOException{
		List<User> list = teamService.displayMembers(teamId);
		for(int i=0; i<list.size();i++) {
			String userId=list.get(i).getUserId().toString();
			String key = userId+"_app";
			String msg = "2;" + taskId.toString();		
			WebSocketServer.sendMessageToOneApp(msg, key);
		}	
		return new JsonResult<Void>(SUCCESS);
	}
	
	@GetMapping("get_joined_members")
	public JsonResult<List<User>> getUserList (Integer taskId){
		List<User> list= service.displayJoinedMembers(taskId);
		return new JsonResult<List<User>>(SUCCESS,list);
	}
	
	@GetMapping("send_start")
	public JsonResult<Void> sendStartCommand(Integer taskId) throws IOException{
		List<User> list = service.displayJoinedMembers(taskId);
		for(int i=0; i<list.size();i++) {
			String userId=list.get(i).getUserId().toString();
			String key = userId+"_app";
			String msg = "3";		
			WebSocketServer.sendMessageToOneApp(msg, key);
		}	
		return new JsonResult<Void>(SUCCESS);
	}
}
