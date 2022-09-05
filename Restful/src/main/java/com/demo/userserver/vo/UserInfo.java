package com.demo.userserver.vo;

import java.io.Serializable;
import java.util.List;

import com.demo.userserver.bo.UserBo;

/**
 * 
 * @author Administrator
 *
 */
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2017699225378639113L;
	
	
	private List<Long> updateIds;
	private UserBo userInfo;
	
	public List<Long> getUpdateIds() {
		return updateIds;
	}
	public void setUpdateIds(List<Long> updateIds) {
		this.updateIds = updateIds;
	}
	public UserBo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserBo userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
