package cn.nottingham.uav.mapper;

import cn.nottingham.uav.entity.Administrator;

public interface AdmMapper {
	/**
	 * find administrator's info by admininsrator's name
	 * @param username
	 * @return
	 */
	Administrator findUserByName(String username);
}
