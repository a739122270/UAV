package cn.nottingham.uav;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.nottingham.uav.entity.Team;
import cn.nottingham.uav.mapper.TeamMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamMapperTests {
	
	@Autowired
	TeamMapper mapper;
	
	@Test
	public void establishTeam() {
		Team team=new Team();
		team.setTeamName("team 1");
		team.setOwner("Jack");
		System.err.println("before teamId="+team.getTeamId());
		Integer row=mapper.establishTeam(team);
		System.err.println("row="+row);
		System.err.println("after teamId="+team.getTeamId());
		
	}
	@Test
	public void updateTeam() {
		Team team = mapper.findTeamByOwner("123");
		team.setTeamName("butterfly");
		Integer row=mapper.updateTeam(team);
		System.err.println("row="+row);
		System.err.println("after teamId="+team.getTeamId());
		
	}
	@Test
	public void findTeams() {
		List<Team> teams = mapper.findTeamsByUavId(38,"max_member_number","DESC");
		for(Team team :teams) {
			System.out.println(team);
		}
		
	}
	@Test
	public void findTeams2() {
		List<Team> teams = mapper.findTeamsByCondition("max_member_number","ASC");
		for(Team team :teams) {
			System.out.println(team);
		}
		
	}
	
	@Test
	public void findTeamByTeamName() {
		List<Team> teams = mapper.findTeamsByTeamName("X");
		for(Team team :teams) {
			System.out.println(team);
		}
		
	}
	
	@Test
	public void deleteTeam() {
		mapper.deleteTeam(2023);
	}
}
