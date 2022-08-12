package cn.nottingham.uav;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Uav;
import cn.nottingham.uav.mapper.UavMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UavMapperTests {
	
	@Autowired
	UavMapper mapper;
	
	@Test
	public void findUavsByPid() {
		List<Uav> list= mapper.findUavsByPid(4);
		for(Uav uav:list) {
			System.out.println(uav);
		}
		
	}
	@Test
	public void findUavById() {
		Uav uav= mapper.findUavById(25);
		System.out.println(uav);		
	}
	
	@Test
	public void findUavByUavType() {
		Uav uav= mapper.findUavByUavType("AIBAO");
		System.out.println(uav);		
	}
}
