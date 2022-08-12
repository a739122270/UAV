package cn.nottingham.uav.service.implement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nottingham.uav.entity.Task;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.PointMapper;
import cn.nottingham.uav.mapper.TaskMapper;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.interfaces.ITaskService;

/**
 * the service layer of task
 * @author XuhuiWei
 *
 */
@Service
public class TaskService implements ITaskService{
	@Autowired
	TaskMapper taskMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	PointMapper pointMapper;
	@Override
	public Integer createTask(Task task, String username, Integer userId) {
		checkPermission(userId);
		Date now = new Date();
		task.setCaptain(username);
		task.setCreatedTime(now);
		task.setCreatedUser(username);
		task.setModifiedTime(now);
		task.setModifiedUser(username);

		Integer row = taskMapper.createTask(task);
		if(!row.equals(1)) {
			throw new InsertException("Create failure！please contact with administrator");
		}
		
		userMapper.updateTaskId(userId, task.getTaskId(), username, now);
		return task.getTaskId();
	}
	
	@Override
	public Task getTaskById(Integer taskId) {
		Task task = taskMapper.getTaskById(taskId);
		return task;
	}
	
	@Override
	@Transactional
	public void deleteTask(Integer taskId, String username) {
		
		List<User> users= userMapper.findUsersByTaskId(taskId);
		for(User user:users) {
			Date now = new Date();
			userMapper.updateTaskId(user.getUserId(), null, username, now);
		}
		Integer row = taskMapper.deleteTask(taskId);
		pointMapper.deletePoint(taskId);
		if(!row.equals(1)) {
			throw new InsertException("Delete failure！please contact with administrator");
		}
	}
	
	@Override
	public List<User> displayJoinedMembers(Integer taskId) {
		return userMapper.findUsersByTaskId(taskId);
	}
	
	private User checkPermission(Integer userId) {
		User userInfo = userMapper.findUserByUserId(userId);
		if(userInfo == null || userInfo.getIsDelete().equals(1)) {
			throw new UserNotFoundException("No permission, user information is invalid");
		}
		return userInfo;
	}
	
}
