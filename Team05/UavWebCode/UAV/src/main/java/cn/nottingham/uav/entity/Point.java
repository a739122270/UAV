package cn.nottingham.uav.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * point class
 * define every attributes of way points
 * @author XuhuiWei
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class Point extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer pointId;
	private Integer taskId;
	private double latitude;
	private double longitude;
	private Integer pointOrder;
	private Integer aircraftId;
	public Integer getPointId() {
		return pointId;
	}
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Integer getPointOrder() {
		return pointOrder;
	}
	public void setPointOrder(Integer pointOrder) {
		this.pointOrder = pointOrder;
	}
	public Integer getAircraftId() {
		return aircraftId;
	}
	public void setAircraftId(Integer aircraftId) {
		this.aircraftId = aircraftId;
	}
	
	@Override
	public String toString() {
		return "Point [pointId=" + pointId + ", taskId=" + taskId + ", latitude=" + latitude + ", longitude="
				+ longitude + ", pointOrder=" + pointOrder + ", aircraftId=" + aircraftId + "]";
	}
	
	
	
}

