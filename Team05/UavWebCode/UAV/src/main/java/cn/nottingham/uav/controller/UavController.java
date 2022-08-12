package cn.nottingham.uav.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nottingham.uav.entity.Uav;
import cn.nottingham.uav.service.interfaces.IUavService;
import cn.nottingham.uav.util.JsonResult;

/**
 * The controller layer
 * @author Xuhui Wei
 *
 */
@RestController
@RequestMapping("uavs")
public class UavController extends BaseController{
	@Autowired
	public IUavService service;
	@GetMapping("get_uav")
	public JsonResult<List<Uav>> getUavListByPid(Integer pid){
		List<Uav> list = service.findUavsByPid(pid);
		return new JsonResult<List<Uav>>(SUCCESS,list);
	}
	@GetMapping("get_uav_superiors")
	public JsonResult<List<Uav>> getUavSuperiorsById(Integer uavId){
		List<Uav> list = service.findUavSuperiorsByUser(uavId);
		return new JsonResult<List<Uav>>(SUCCESS,list);
	}
	@GetMapping("get_uav_type")
	public JsonResult<Uav> getUavById(Integer id){
		Uav uav = service.findUavById(id);
		return new JsonResult<Uav>(SUCCESS,uav);
	}
	@GetMapping("get_uav_type_by_userId")
	public JsonResult<Uav> getUavById(HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());
		Uav uav = service.findUavByUserId(userId);
		return new JsonResult<Uav>(SUCCESS,uav);
	}
}
