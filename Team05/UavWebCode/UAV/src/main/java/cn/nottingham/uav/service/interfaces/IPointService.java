package cn.nottingham.uav.service.interfaces;

import cn.nottingham.uav.entity.Point;

public interface IPointService {
	/**
	 * add point to database
	 * @param point
	 * @param username
	 * @param userId
	 * @return
	 */
	public Integer addPoint(Point point,String username,Integer userId);
}
