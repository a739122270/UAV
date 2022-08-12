package cn.nottingham.uav.mapper;

import java.util.List;

import cn.nottingham.uav.entity.Uav;

public interface UavMapper {
	/**
	 * Find the subset region information based on pid
	 * @param pid
	 * @return
	 */
	List<Uav> findUavsByPid(Integer pid);
	/**
	 * find  specific uav info by Id
	 * @param id
	 * @return
	 */
	Uav findUavById(Integer id);
	/**
	 * find uav info by Uav type
	 * @param uavType
	 * @return
	 */
	Uav findUavByUavType(String uavType);
}
