package cn.stroe.xiaowu.entity;

import java.io.Serializable;
import java.util.Date;
 
public class Token implements Serializable{
	private static final long serialVersionUID = 5429426081001903911L;
	private String username;//用户名 
	private String tokenId;//token码
	private Date createTime;//创建时间
	private Date activeTime;//操作时间
	private Date failureTime;//时效时间
	private String isActive;//是否激活，1可用，0不可用
	public Token() {}
	public Token(String username, String tokenId, Date createTime, Date activeTime, Date failureTime, String isActive) {
		super();
		this.username = username;
		this.tokenId = tokenId;
		this.createTime = createTime;
		this.activeTime = activeTime;
		this.failureTime = failureTime;
		this.isActive = isActive;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public Date getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeTime == null) ? 0 : activeTime.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((failureTime == null) ? 0 : failureTime.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((tokenId == null) ? 0 : tokenId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Token other = (Token) obj;
		if (activeTime == null) {
			if (other.activeTime != null)
				return false;
		} else if (!activeTime.equals(other.activeTime))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (failureTime == null) {
			if (other.failureTime != null)
				return false;
		} else if (!failureTime.equals(other.failureTime))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (tokenId == null) {
			if (other.tokenId != null)
				return false;
		} else if (!tokenId.equals(other.tokenId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Token [username=" + username + ", tokenId=" + tokenId + ", createTime=" + createTime + ", activeTime="
				+ activeTime + ", failureTime=" + failureTime + ", isActive=" + isActive + "]";
	}
	
}
