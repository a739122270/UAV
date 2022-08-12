package cn.nottingham.uav.service.implement;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.nottingham.uav.entity.Point;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.PointMapper;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.interfaces.IPointService;

@Service
public class PointService implements IPointService{
	@Autowired
	PointMapper pointMapper;
	@Autowired
	UserMapper userMapper;
	@Override
	public Integer addPoint(Point point, String username, Integer userId) {
		checkPermission(userId);
		Date now = new Date();
		point.setCreatedTime(now);
		point.setCreatedUser(username);
		point.setModifiedTime(now);
		point.setModifiedUser(username);
		Integer row = pointMapper.addPoint(point);
		if(!row.equals(1)) {
			throw new InsertException("Add way point failure！please contact with administrator");
		}
		return row;

	}
	
	private User checkPermission(Integer userId) {
		User userInfo = userMapper.findUserByUserId(userId);
		if(userInfo == null || userInfo.getIsDelete().equals(1)) {
			throw new UserNotFoundException("No permission, user information is invalid");
		}
		return userInfo;
	}
	
}
