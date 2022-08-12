package cn.nottingham.uav;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.interfaces.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	@Autowired
	IUserService service;

	@Test
	public void register() {
		try {
			User user = new User();
			user.setUsername("BoyWithLuv");
			user.setPassword("1234");
			service.register(user);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void login() {
		try {
			User user = service.login("BoyWithLuv", "1234");
			System.err.println(user);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void changeInfo() {
		try {
			User user = new User();
			user.setEmail("wxh@163.com");
			user.setGender(0);
			service.changeInfo(1,"jack",user);
			System.err.println(user);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar() {
		try {
			Integer userId=1;
			String avatar="/ccc/3.png";
			String modifiedUser="管理员";
			service.changeAvatar(userId, avatar, modifiedUser);
			System.err.println("over");
		}catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}
