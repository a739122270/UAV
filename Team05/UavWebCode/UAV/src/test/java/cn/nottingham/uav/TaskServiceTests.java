package cn.nottingham.uav;

import java.sql.Time;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Task;
import cn.nottingham.uav.service.interfaces.ITaskService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTests {
	@Autowired
	ITaskService service;

	@Test
	public void createTask() {
		try {
			Task task = new Task();
			task.setCaptain("mark");
			task.setFlightHeight(120);
			task.setFlightVelocity(5);
			task.setFormationType(1);
			task.setHeightLimit(120);
			task.setTotalDistance(120);
			task.setUavType("dajiang");
			task.setUavAmount(4);
			task.setUavDistance(5);
			String str = "11:12";
			task.setTotalTime(str);
			service.createTask(task, "mark",1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void String2Time() throws Exception{ 
		  String str="11:10:10"; 
		  
		  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");  
		     java.util.Date d = null;  
		     try {  
		         d = format.parse(str);  
		     } catch (Exception e) {  
		         e.printStackTrace();  
		     }  
		     Time time = new Time(d.getTime());  
		     System.out.println(time); 
	} 
}
