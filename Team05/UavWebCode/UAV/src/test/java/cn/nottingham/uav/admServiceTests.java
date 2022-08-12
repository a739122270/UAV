package cn.nottingham.uav;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Administrator;
import cn.nottingham.uav.mapper.AdmMapper;
import cn.nottingham.uav.service.interfaces.IAdmService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class admServiceTests {
	@Autowired
	IAdmService service;

	@Autowired
	AdmMapper mapper;
	@Test
	public void login() {
		try {
			Administrator user = service.login("BoyWithLuv", "1234");
			System.err.println(user);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void login2() {
		try {
			Administrator user = mapper.findUserByName("root");
			System.err.println(user);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

}
