package cn.nottingham.uav;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Uav;
import cn.nottingham.uav.service.interfaces.IUavService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UavServiceTests {
	@Autowired
	IUavService service;

	@Test
	public void findSuperiorsList() {
		try {
			List<Uav> list = service.findUavSuperiorsByUser(25);
			for(Uav uav:list) {
				System.out.println(uav);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void findUavByUserId() {
		try {
			Uav uav = service.findUavByUserId(1);
			System.out.println(uav);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	

}
