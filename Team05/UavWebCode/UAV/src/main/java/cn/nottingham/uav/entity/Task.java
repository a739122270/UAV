package cn.nottingham.uav.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * task data entity
 * @author 幻想~天空
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class Task extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private int taskId;
	private int heightLimit;
	private String captain;
	private String uavType;
	private int uavAmount;
	private double uavDistance;
	private double flightHeight;
	private double flightVelocity;
	private String origin;
	private String destination;
	private int formationType;
	private double totalDistance;
	private String totalTime;
	private Date startTime;
	private Date finishTime;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getHeightLimit() {
		return heightLimit;
	}
	public void setHeightLimit(int heightLimit) {
		this.heightLimit = heightLimit;
	}
	public String getCaptain() {
		return captain;
	}
	public void setCaptain(String captain) {
		this.captain = captain;
	}
	public String getUavType() {
		return uavType;
	}
	public void setUavType(String uavType) {
		this.uavType = uavType;
	}
	public int getUavAmount() {
		return uavAmount;
	}
	public void setUavAmount(int uavAmount) {
		this.uavAmount = uavAmount;
	}
	public double getUavDistance() {
		return uavDistance;
	}
	public void setUavDistance(double uavDistance) {
		this.uavDistance = uavDistance;
	}
	public double getFlightHeight() {
		return flightHeight;
	}
	public void setFlightHeight(double flightHeight) {
		this.flightHeight = flightHeight;
	}
	public double getFlightVelocity() {
		return flightVelocity;
	}
	public void setFlightVelocity(double flightVelocity) {
		this.flightVelocity = flightVelocity;
	}
	public int getFormationType() {
		return formationType;
	}
	public void setFormationType(int formationType) {
		this.formationType = formationType;
	}
	public double gettotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(double distance) {
		this.totalDistance = distance;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", heightLimit=" + heightLimit + ", captain=" + captain + ", uavType="
				+ uavType + ", uavAmount=" + uavAmount + ", uavDistance=" + uavDistance + ", flightHeight="
				+ flightHeight + ", flightVelocity=" + flightVelocity + ", origin=" + origin + ", destination="
				+ destination + ", formationType=" + formationType + ", totalDistance=" + totalDistance + ", totalTime="
				+ totalTime + ", startTime=" + startTime + ", finishTime=" + finishTime + "]";
	}
		
}

