package cn.nottingham.uav.entity;
/**
 * uav entity
 * @author XuhuiWei
 *
 */
public class Uav extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer pid;
	private String levelName;
	private Integer hierarchy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hierarchy == null) ? 0 : hierarchy.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
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
		Uav other = (Uav) obj;
		if (hierarchy == null) {
			if (other.hierarchy != null)
				return false;
		} else if (!hierarchy.equals(other.hierarchy))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Uav [id=" + id + ", pid=" + pid + ", levelName=" + levelName + ", hierarchy=" + hierarchy + "]";
	}
	
}
