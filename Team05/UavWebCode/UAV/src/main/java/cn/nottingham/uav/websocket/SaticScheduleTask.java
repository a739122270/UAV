package cn.nottingham.uav.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description :webSocket regularly sends message classes
 * @statement: sent to websocket connected objects at a frequency of <60s to prevent the 60s timeout limit of the reverse proxy
 */
@Configuration      //1. It is mainly used to mark the configuration class, with the effect of Component.
@EnableScheduling  	// 2. Start regular tasks
public class SaticScheduleTask {

	//3. Add regular tasks
	// or simply specify the time interval, for example, 5 seconds
    @Scheduled(fixedRate=58*1000)
    private void configureTasks() throws Exception{
    	WebSocketServer.sendMessageToApp("Connection success!");
    }
}
