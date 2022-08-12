package cn.nottingham.uav.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.nottingham.uav.controller.expection.FileEmptyException;
import cn.nottingham.uav.controller.expection.FileIOException;
import cn.nottingham.uav.controller.expection.FileSizeException;
import cn.nottingham.uav.controller.expection.FileStateException;
import cn.nottingham.uav.controller.expection.FileTypeException;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.interfaces.IUserService;
import cn.nottingham.uav.util.JsonResult;

/**
 * One controller layer
 * @author Xuhui Wei
 *
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
	@Autowired
	private IUserService service;
	
	@PostMapping("reg")
	public JsonResult<Void> register(User user){
		service.register(user);
		return new JsonResult<Void>(SUCCESS);
	}
	@PostMapping("login")
	public JsonResult<User> login(String username, String password,HttpSession session){
		User user = service.login(username, password);
		session.setAttribute(SESSION_USERID, user.getUserId());
		session.setAttribute(SESSION_USERNAME, user.getUsername());
		return new JsonResult<User>(SUCCESS,user);
	}

	@GetMapping("get_user_jsonp") //get userId and send as jsonp type
	  public void getUser(HttpSession session,HttpServletResponse response,@RequestParam String callbackName) {
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());
		User user = service.getUserInfoByUserId(userId);
	    response.setContentType("text/javascript");
	    Writer writer = null;
	    try {
	    	writer = response.getWriter();
	    	writer.write(callbackName + "({");
	    	writer.write("'username':'"+user.getUsername()+"',");
	    	writer.write("'avatar':'"+user.getAvatar()+"'");
	    	writer.write("});");
	    } catch (IOException e) {
	    	System.err.print("jsonp response write failure！");
	    } finally {
	    	if (writer != null) {
	    		try {
	    			writer.close();
	    		} catch (IOException e) {
	    			System.err.print("Output stream close exception!");
	    		}
	    		writer = null;
	    	}
	    }
	  }
	
	
	@GetMapping("get_by_userId")
	public JsonResult<User> getByUid(HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());
		User user = service.getUserInfoByUserId(userId);
		return new JsonResult<User>(SUCCESS,user);
		
	}
	
	
	@PostMapping("change_info")
	public JsonResult<Void> changeInfo(User user,HttpSession session){	
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.changeInfo(userId, username, user);
		return new JsonResult<Void>(SUCCESS);
	}
	
	private static final long AVATAR_MAX_SIZE=500*1024L;  	//limited size of avatar
	private static final List<String> AVATAR_TYPES = new ArrayList<String>(); 	//limited type of avatar.
	static {
		AVATAR_TYPES.add("image/jpeg");
		AVATAR_TYPES.add("image/png");
	}
	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file,
			    HttpServletRequest request,HttpSession session){
		if(file.isEmpty()) {
			throw new FileEmptyException(" Upload abnormal! The file does not exist.");
		}
		if(file.getSize()>AVATAR_MAX_SIZE) {
			throw new FileSizeException(" Upload abnormal! File size exceeded the upper limit. The maximum limit is:"+(AVATAR_MAX_SIZE/1024)+"KB");
		}
		if(!AVATAR_TYPES.contains(file.getContentType())) {
			throw new FileTypeException(" Upload abnormal! File format error. The supported format is:"+AVATAR_TYPES);
		}
		
		String originalFilename=file.getOriginalFilename();
		Integer index=originalFilename.lastIndexOf(".");
		String suffix="";
		if(index!=-1) {
			suffix=originalFilename.substring(index);
		}
		String filename=UUID.randomUUID().toString()+suffix;

		String filePath=request.getServletContext().getRealPath("upload");
		File parent=new File(filePath);
		if(!parent.exists()) {
			parent.mkdirs();
		}		
		File dest=new File(parent,filename);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileStateException("Upload abnormal!"+e.getMessage(),e);
		} catch (IOException e) {
			throw new FileIOException("Upload abnormal!"+e.getMessage(),e);
		}

		String avatar="/upload/"+filename;
		Integer uid = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.changeAvatar(uid,avatar,username);
		return new JsonResult<String>(SUCCESS, avatar);
	}
	
	@PostMapping("add_uav")
	public JsonResult<Void> addUavByUavId(@RequestParam("uavId") Integer uavId,HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		String username = session.getAttribute(SESSION_USERNAME).toString();
		service.addUavToUser(username, userId, uavId);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@GetMapping("get_taskId")
	public JsonResult<Integer> getTaskIdByUserId(HttpSession session){
		Integer userId = Integer.valueOf(session.getAttribute(SESSION_USERID).toString());		
		Integer taskId = service.getTaskIdByUserId(userId);
		return new JsonResult<Integer>(SUCCESS,taskId);
	}
	
	@GetMapping("search_users_by_alphabet")
	public JsonResult<List<User>> findUsersByAlphabet(String order){	
		List<User> list = service.findUserByAlphabet(order);
		return new JsonResult<List<User>>(SUCCESS,list);
	}	
	
	@PostMapping("freeze_account")
	public JsonResult<Void> freezeAccount(@RequestParam("username") String username,HttpSession session){		
		String admName = session.getAttribute(SESSION_USERNAME).toString();
		service.freezeAccount(username,admName,1);
		return new JsonResult<Void>(SUCCESS);
	}
	
	@PostMapping("unfreeze_account")
	public JsonResult<Void> unfreezeAccount(@RequestParam("username") String username,HttpSession session){		
		String admName = session.getAttribute(SESSION_USERNAME).toString();
		service.freezeAccount(username,admName,0);
		return new JsonResult<Void>(SUCCESS);
	}
}
