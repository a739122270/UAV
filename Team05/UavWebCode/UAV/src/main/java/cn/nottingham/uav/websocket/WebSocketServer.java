package cn.nottingham.uav.websocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.mapper.UserMapper;
import cn.nottingham.uav.service.interfaces.ITeamService;

@Component
//the url address of the access server
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketServer {

	private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

	// a connection session with a client through which data is sent to the client
	private static Logger log = LogManager.getLogger(WebSocketServer.class);

	private Session session;
	private String userId; // userId_app or userId_web

	static ITeamService teamService;
	static UserMapper userMapper;
	
	@Autowired
	public void setITeamService(ITeamService teamService) {
		WebSocketServer.teamService = teamService;
	}
	@Autowired
	public void setIUserService(UserMapper userMapper) {
		WebSocketServer.userMapper = userMapper;
	}

	/**
	 * Connection establishment method called successfully
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "userId") String userId, Session session) {
		this.session = session;
		this.userId = userId;
		webSocketSet.put(userId, this);
		log.info("User" + userId + "join! The current online population is" + webSocketSet.size());

	}

	/**
	 * Connection closes the called method
	 */
	@OnClose
	public void onClose() {
		try {
			webSocketSet.remove(this.userId); // delete from webSocketSet
			log.info("There is a connection closed! The current online population is" + webSocketSet.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method called upon receipt of a client message
	 *
	 * @param message Messages sent from the client (message instruction set)
	 * @throws IOException
	 */

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		log.info("Message from client:" + message);

		String[] s1 = message.split(";");

		switch (s1[0]) {
		case ("1"): // 1; ownerName; ownerId; applicantName ; applicantId ; message ; true/false
					// apply for joining Team
			try {
				if (s1[s1.length - 1].equals("true")) {
					teamService.addMember(s1[1], Integer.parseInt(s1[2]), s1[3]);
					webSocketSet.get(s1[4] + "_app").sendMessage("join successfully!");
				} else if (s1[s1.length - 1].equals("false")) {
					webSocketSet.get(s1[4] + "_app").sendMessage("your application is rejected.");
				} else {
					if (!s1[3].equals(s1[1])) {
						applyForJoinTeam(message, s1[3], s1[2]); // avoid to send self.
					} else {
						webSocketSet.get(s1[4] + "_app").sendMessage("you have been in this team");
					}
				}
			} catch (Exception e) {
				sendMessage(e.getMessage());
				webSocketSet.get(s1[4] + "_app").sendMessage(e.getMessage());
			}
			break;		
		case ("4"):// 4;captainName;memberId  //After joining the team, the web side refreshes the page
			Integer userId = userMapper.getUserIdByUsername(s1[1]);
			webSocketSet.get(userId + "_web").sendMessage(message);
			break;
			
		case ("5")://5;taskId; captain,process,aircraftId,battery,latitude,longitude
			List<User> list= userMapper.findUsersByTaskId(Integer.parseInt(s1[1]));
			for(int i=0; i<list.size();i++) {
				String id = list.get(i).getUserId().toString();				
				if (webSocketSet.get(id + "_web") != null) {
					webSocketSet.get( id + "_web").sendMessage(message+";"+list.size());
				}
			}
		default:
			System.out.println("Connected to Client");
		}

	}

	/**
	 *
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("An error occurred");
		error.printStackTrace();
	}

	/**
	 * 
	 * @param message instruction set
	 * @param fromName applicant's name
	 * @param ownerId  队长Id
	 * @throws IOException
	 */
	public void applyForJoinTeam(String message, String fromName, String ownerId) throws IOException {
		if (webSocketSet.get(ownerId + "_web") != null) {
			webSocketSet.get(ownerId + "_web").sendMessage(message + fromName + " apply for joining your team");// Instruction set + information
		} else {
			// If the user is not online, the message is returned to the user
			sendMessage("The current user is not online");
		}
	}
	
	/**
	 * Send messages to all App ends
	 * 
	 * @param message
	 * @throws IOException
	 */

	public static void sendMessageToApp(String message) throws IOException {
		for (String key : webSocketSet.keySet()) {
			try {
				if (webSocketSet.get(key).session.isOpen() && key.indexOf("_app") != -1) {
					webSocketSet.get(key).sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Send a message to app end user
	 * @param message
	 * @param key
	 * @throws IOException
	 */
	public static void sendMessageToOneApp(String message, String key) throws IOException {
		if (webSocketSet.get(key) != null && webSocketSet.get(key).session.isOpen() && key.indexOf("_app") != -1) {
			webSocketSet.get(key).sendMessage(message);
		}
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}
}
