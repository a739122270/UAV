package cn.nottingham.uav.mapper;

import cn.nottingham.uav.entity.Point;

public interface PointMapper {
	/**
	 * add way point
	 * @param point
	 * @return
	 */
	Integer addPoint(Point point);
	/**
	 * delete way point
	 * @param taskId
	 * @return
	 */
	Integer deletePoint(Integer taskId);
}
