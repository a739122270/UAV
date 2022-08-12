package cn.nottingham.uav.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.nottingham.uav.entity.Uav;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.UavMapper;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.interfaces.IUavService;

@Service
public class UavService implements IUavService{
	
	@Autowired
	public UavMapper uavMapper;
	@Autowired
	public UserMapper userMapper;
	@Override
	public List<Uav> findUavsByPid(Integer pid) {
		return uavMapper.findUavsByPid(pid);
	}
	
	@Override
	public List<Uav> findUavSuperiorsByUser(Integer uavId) {
		List<Uav> list = new ArrayList<Uav>();
		Uav type = uavMapper.findUavById(uavId);
		list.add(type);
		Uav series =  uavMapper.findUavById(type.getPid());
		list.add(series);
		Uav brand = uavMapper.findUavById(series.getPid());		
		list.add(brand);
		return list;
	}
	@Override
	public Uav findUavById(Integer id) {
		Uav uav = uavMapper.findUavById(id);
		return uav;
	}
	
	@Override
	public Uav findUavByUserId(Integer userId) {
		User user = userMapper.findUserByUserId(userId);
		Uav uav = uavMapper.findUavById(user.getUavId());
		if(uav == null) {
			return null;
		}
		return uav;
	}

}
