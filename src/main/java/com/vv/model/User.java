package com.vv.model;

import java.util.List;

public class User {

	private int userId;
	private String handle;
	List<Long> followers;
	List<Long> following;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public List<Long> getFollowers() {
		return followers;
	}
	public void setFollowers(List<Long> followers) {
		this.followers = followers;
	}
	public List<Long> getFollowing() {
		return following;
	}
	public void setFollowing(List<Long> following) {
		this.following = following;
	}
	
	
}
