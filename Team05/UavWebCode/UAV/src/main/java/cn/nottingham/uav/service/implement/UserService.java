package cn.nottingham.uav.service.implement;

import java.util.UUID;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.exception.FormIncompleteException;
import cn.nottingham.uav.service.exception.InsertException;
import cn.nottingham.uav.service.exception.PasswordNotMatchException;
import cn.nottingham.uav.service.exception.UpdateException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.exception.UsernameDuplicateException;
import cn.nottingham.uav.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	public UserMapper mapper;
	
	@Override
	public void register(User user) throws UsernameDuplicateException, InsertException,FormIncompleteException {
		//acquire user name and password
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		String phone = user.getPhone();
		if(username == "" || password == "" || email =="" || phone =="") {
			throw new FormIncompleteException("Registration error! Form is incomplete.");
		}
		User result = mapper.findUserByName(username);
		//judge whether user is existed.
		//exist: throw exception.
		if(result != null) {
			throw new UsernameDuplicateException("Registration error！username is existed.");
		}
		//MD5 encryption
		String salt = UUID.randomUUID().toString();
		String md5Password = createMD5Password(password,salt);
		//add log data
		Date now = new Date();
		user.setPassword(md5Password);
		user.setSalt(salt);
		user.setIsDelete(0);
		user.setAvatar("../images/default-avatar.jpg");
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifiedUser(username);
		user.setModifiedTime(now);
		
		Integer row=mapper.addUser(user);
		//if the number of rows returned is not 1
	    if(!row.equals(1)) {
	        throw new InsertException("registration error！please contact with administrator");
	    }	            
	}
	/**
	 * MD5 encryption
	 * @param password
	 * @param salt
	 * @return
	 */
	public String createMD5Password(String password,String salt) {
		String passwordWithSalt = salt+password+salt;
		String md5Password = DigestUtils.md5DigestAsHex(passwordWithSalt.getBytes()); 
		return md5Password;
	}

	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User user = mapper.findUserByName(username);
		if(user==null) {
			throw new UserNotFoundException("Login exception! Wrong username or password!");
		}
		if(user.getIsDelete().equals(1)) {
			throw new UserNotFoundException("Login error! your account is freezing!");
		}
		String salt = user.getSalt();
		String md5Password = createMD5Password(password,salt);

		if(!md5Password.equals(user.getPassword())) {
			throw new PasswordNotMatchException("Login exception! Wrong username or password!");
		}
		
		user.setSalt(null);
		user.setPassword(null);
		user.setIsDelete(null);
		
		return user;
	}
	
	@Override
	public User getUserInfoByUserId(Integer userId) throws UserNotFoundException {
		User user = checkUserIsValid(userId);
		return user;
	}
	
	@Override
	public void changeInfo(Integer userId, String username, User newUserInfo) throws UserNotFoundException, UpdateException {
		User user = checkUserIsValid(userId);	
		Date now = new Date();
		
		user.setGender(newUserInfo.getGender());
		user.setEmail(newUserInfo.getEmail());
		user.setPhone(newUserInfo.getPhone());
		user.setModifiedUser(username);
		user.setModifiedTime(now);

		int row = mapper.updateInfo(user);
		
		if(row !=1) {
			throw new UpdateException("Modify the exception! Please contact the administrator");
		}	
	}
	@Override
	public void changeAvatar(Integer userId, String avatar, String modifiedUser)
			throws UserNotFoundException, UpdateException {
		checkUserIsValid(userId);
		Integer row=mapper.updateAvatar(userId, avatar, modifiedUser, new Date());		
		if(!row.equals(1)) {
			throw new UpdateException("Upload abnormal! Upload failed!");
		}
	}
	
	@Override
	public void addUavToUser(String username, Integer userId, Integer uavId) {
		User user = checkUserIsValid(userId);
		
		Date now = new Date();
		
		user.setUavId(uavId);
		user.setUserId(userId);
		user.setModifiedUser(username);
		user.setModifiedTime(now);
		
		int row = mapper.updateUavId(userId, uavId, username, now);	
		if(row !=1) {
			throw new UpdateException("Modify the exception! Please contact the administrator");
		}	
	}
	
	@Override
	public Integer getTaskIdByUserId(Integer userId) {
		Integer taskId = mapper.getTaskIdByUserId(userId);
		return taskId;
	}
	
	@Override
	public List<User> findUserByAlphabet(String order) {
		List<User> list = mapper.findUsersByAlphabet(order);
		return list;
	}
	
	@Override
	public void freezeAccount(String username, String admName,Integer isFreeze) {
		Date now = new Date();
		Integer row= mapper.freezeAccount(username,admName,isFreeze, now);
		if(row !=1) {
			throw new UpdateException("Modify the exception! Please contact the administrator");
		}	
		
	}
		
	private User checkUserIsValid(Integer userId) {
		User userInfo = mapper.findUserByUserId(userId);
		if(userInfo == null || userInfo.getIsDelete().equals(1)) {
			throw new UserNotFoundException("Invalid or nonexistent user information");
		}
		return userInfo;
	}

}
