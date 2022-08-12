package cn.nottingham.uav.mapper;

import cn.nottingham.uav.entity.Task;

public interface TaskMapper {
	/**
	 * create a task
	 * @param task
	 * @return
	 */
	Integer createTask(Task task);
	/**
	 * get task info by taskId
	 * @param taskId
	 * @return
	 */
	Task getTaskById(Integer taskId);
	
	/**
	 * delete task By taskId
	 * @param taskId
	 * @return
	 */
	Integer deleteTask(Integer taskId);
}
