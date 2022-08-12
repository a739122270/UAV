package cn.nottingham.uav.service.interfaces;

import java.util.List;

import cn.nottingham.uav.entity.Uav;

public interface IUavService {
	/**
	 * find Uavs by their parent id
	 * @param pid
	 * @return
	 */
	public List<Uav> findUavsByPid(Integer pid);
	/**
	 * find Uav superiors By uavId
	 * @param uavId
	 * @return
	 */
	public List<Uav> findUavSuperiorsByUser(Integer uavId);
	/**
	 * find uav by Id
	 * @param id
	 * @return
	 */
	public Uav findUavById(Integer id);
	/**
	 * find uav by userId
	 * @param userId
	 * @return
	 */
	public Uav findUavByUserId(Integer userId);
}
