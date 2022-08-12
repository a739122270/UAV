package cn.nottingham.uav.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Team entity
 * @author lxwzf
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class Team extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer teamId;
	private String teamName;
	private String owner;
	private Integer uavId;
	private Integer maxMemberNumber;
	private String flightArea;
	private Integer isPrivate;
	private Integer isDelete;
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Integer getUavId() {
		return uavId;
	}
	public void setUavId(Integer uavId) {
		this.uavId = uavId;
	}
	public Integer getMaxMemberNumber() {
		return maxMemberNumber;
	}
	public void setMaxMemberNumber(Integer maxMemberNumber) {
		this.maxMemberNumber = maxMemberNumber;
	}
	public String getFlightArea() {
		return flightArea;
	}
	public void setFlightArea(String flightArea) {
		this.flightArea = flightArea;
	}
	public Integer getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightArea == null) ? 0 : flightArea.hashCode());
		result = prime * result + ((isDelete == null) ? 0 : isDelete.hashCode());
		result = prime * result + ((isPrivate == null) ? 0 : isPrivate.hashCode());
		result = prime * result + ((maxMemberNumber == null) ? 0 : maxMemberNumber.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		result = prime * result + ((uavId == null) ? 0 : uavId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (flightArea == null) {
			if (other.flightArea != null)
				return false;
		} else if (!flightArea.equals(other.flightArea))
			return false;
		if (isDelete == null) {
			if (other.isDelete != null)
				return false;
		} else if (!isDelete.equals(other.isDelete))
			return false;
		if (isPrivate == null) {
			if (other.isPrivate != null)
				return false;
		} else if (!isPrivate.equals(other.isPrivate))
			return false;
		if (maxMemberNumber == null) {
			if (other.maxMemberNumber != null)
				return false;
		} else if (!maxMemberNumber.equals(other.maxMemberNumber))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		if (uavId == null) {
			if (other.uavId != null)
				return false;
		} else if (!uavId.equals(other.uavId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamName=" + teamName + ", owner=" + owner + ", uavId=" + uavId
				+ ", maxMemberNumber=" + maxMemberNumber + ", flightArea=" + flightArea + ", isPrivate=" + isPrivate
				+ ", isDelete=" + isDelete + "]";
	}
	
	
	
}
