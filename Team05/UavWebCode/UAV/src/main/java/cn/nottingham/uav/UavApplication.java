package cn.nottingham.uav;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * class for project starting up 
 * @author xhHuiWei
 *
 */
@SpringBootApplication
@MapperScan("cn.nottingham.uav.mapper")
public class UavApplication {

	public static void main(String[] args) {
		SpringApplication.run(UavApplication.class, args);
	}
}
