package cn.nottingham.uav;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.UserMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
	
	@Autowired
	UserMapper mapper;
	
	@Test
	public void addUser() {
		User user=new User();
		user.setUsername("BoyWithLuv");
		user.setPassword("1234");
		System.err.println("before userId="+user.getUserId());
		Integer row=mapper.addUser(user);
		System.err.println("row="+row);
		System.err.println("after userId="+user.getUserId());
		
	}
	@Test
	public void findByUsername() {
		User user=mapper.findUserByName("BoyWithLuv");
		System.err.println(user);
	}
	@Test
	public void updateUserInfo() {
		User user = mapper.findUserByUserId(1);
		System.err.println(user);
		user.setGender(0);
		Integer a = mapper.updateInfo(user);
		System.err.println(a);
	}
	@Test
	public void findUsersByTeamId() {
		List<User> list = mapper.findUsersByTeamId(1);
		for(User user:list) {
			System.out.println(user);
		}
	}
	@Test
	public void updateTaskId() {
		User user = mapper.findUserByUserId(1);
		System.err.println(user);
		Date now = new Date();
		mapper.updateTaskId(1, 1, user.getUsername(), now);
		System.err.println(user);
	}
	
	@Test
	public void getUserId() {
		Integer userId = mapper.getUserIdByUsername("jack");
		System.out.println(userId);
	}

}
