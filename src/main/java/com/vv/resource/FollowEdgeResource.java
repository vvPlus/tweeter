package com.vv.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.vv.dao.FollowerEdgesDAO;
import com.vv.dao.FollowerEdgesDAOImpl;
import com.vv.model.FollowEdge;

@Path("followEdges")
public class FollowEdgeResource {

	private FollowerEdgesDAO followersDAO;

	public FollowEdgeResource() {
		this.followersDAO = new FollowerEdgesDAOImpl();
	}

	// get all followers for a user
	// get all users that a user follows

	// let this user follow a user
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void followUser(FollowEdge followEdge) {
		try {
			followersDAO.follow(followEdge.getUserId(), followEdge.getTargetUserId());
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}

	// unfollow
	@DELETE
	public void unfollowUser(@PathParam("userId") int callerId, @PathParam("targetUserId") int targetUserId) {
		try {
			followersDAO.unfollow(callerId, targetUserId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
