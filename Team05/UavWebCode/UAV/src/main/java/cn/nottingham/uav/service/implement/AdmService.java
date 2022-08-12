package cn.nottingham.uav.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.nottingham.uav.entity.Administrator;
import cn.nottingham.uav.mapper.AdmMapper;
import cn.nottingham.uav.service.exception.PasswordNotMatchException;
import cn.nottingham.uav.service.exception.UserNotFoundException;
import cn.nottingham.uav.service.interfaces.IAdmService;
/**
 * implement the functions defined in administrator's interface
 * @author XuhuiWei
 *
 */
@Service
public class AdmService implements IAdmService{
	@Autowired
	public AdmMapper mapper;

	@Override
	public Administrator login(String username, String password)
			throws UserNotFoundException, PasswordNotMatchException {
			Administrator user = mapper.findUserByName(username);
			if(user==null) {
				throw new UserNotFoundException("Login exception! Wrong username or password!");
			}

			String salt = user.getSalt();
			String md5Password = createMD5Password(password,salt);

			if(!md5Password.equals(user.getPassword())) {
				throw new PasswordNotMatchException("Login exception! Wrong username or password!！");
			}
			
			user.setSalt(null);
			user.setPassword(null);
			
			return user;
	}

	public String createMD5Password(String password,String salt) {
		String passwordWithSalt = salt+password+salt;
		String md5Password = DigestUtils.md5DigestAsHex(passwordWithSalt.getBytes()); 
		return md5Password;
	}
	
}
