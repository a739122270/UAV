package cn.nottingham.uav.service.interfaces;

import java.util.List;

import cn.nottingham.uav.entity.Task;
import cn.nottingham.uav.entity.User;

public interface ITaskService {
	/**
	 * create a task 
	 * @param task
	 * @param username
	 * @param userId
	 * @return
	 */
	public Integer createTask(Task task,String username,Integer userId);
	/**
	 * get task by taskId
	 * @param taskId
	 * @return
	 */
	public Task getTaskById(Integer taskId);
	/**
	 * delete task
	 * @param taskId
	 * @param username
	 */
	public void deleteTask(Integer taskId,String username);
	/**
	 * display all the users who joined specific task by taskId
	 * @param taskId
	 * @return
	 */
	public List<User> displayJoinedMembers(Integer taskId);
}
