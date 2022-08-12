package cn.nottingham.uav.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.nottingham.uav.entity.Administrator;
import cn.nottingham.uav.service.interfaces.IAdmService;
import cn.nottingham.uav.util.JsonResult;
/**
 * The controller layer
 * Invoke the IAdmService method based on the HTTP request.
 * @author Xuhui Wei
 *
 */
@RestController
@RequestMapping("adms")
public class AdmController extends BaseController {
	@Autowired
	private IAdmService service;
		
	@PostMapping("login")
	public JsonResult<Administrator> login(String username, String password,HttpSession session){
		Administrator user = service.login(username, password);
		session.setAttribute(SESSION_USERID, user.getAdmId());
		session.setAttribute(SESSION_USERNAME, user.getUsername());
		return new JsonResult<Administrator>(SUCCESS,user);
	}
}
