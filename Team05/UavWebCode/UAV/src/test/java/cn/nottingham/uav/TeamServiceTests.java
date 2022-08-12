package cn.nottingham.uav;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Team;
import cn.nottingham.uav.entity.User;
import cn.nottingham.uav.service.interfaces.ITeamService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTests {
	@Autowired
	ITeamService teamService;
	@Test
	public void establish() {
		try {
			Team team = new Team();
			team.setTeamName("team 1");
			teamService.establishTeam(team,"jack", 1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void transfeOwnerShip() {
		try {
			teamService.transferOwnership("jack", 1,"123");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void QuitTeam() {
		try {
			teamService.quitTeam("jack",1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void addTeam() {
		try {
			teamService.addTeam("jack",1,2);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void displayMembers() {
		try {
			List<User> list = teamService.displayMembers(1);
			for(User user:list) {
				System.out.println(user);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void checkOwnership() {
		try {
			boolean a = teamService.checkOwnership("123", 2);
			System.out.println(a);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void deleteMember() {
		try {
			teamService.deleteMember("123", 2, 1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void addMember() {
		try {
			teamService.addMember("123", 2, "jack");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void updateTeam(){
		try {
			Team team = new Team();
			team.setMaxMemberNumber(0);
			teamService.updateTeamInfo("123", 2, team);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void searchTeam(){
		try {
			List<Team> teams = teamService.findTeamsByUavType(1, "VITUS", "max_member_number", "DESC");
			if(teams.isEmpty()) {
				System.out.println("empty");
			}
			for(Team team :teams) {
				System.out.println(team);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void searchTeam2(){
		try {
			List<Team> teams = teamService.findTeamsByCondition(1, "max_member_number", "DESC");
			for(Team team :teams) {
				System.out.println(team);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	public void disbandTeam() {
		try {
			teamService.disbandTeam("jack", 1);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
		
}
