package com.vv.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.dao.FollowerEdgesDAO;
import com.vv.dao.FollowerEdgesDAOImpl;
import com.vv.model.FollowEdge;

@Path("followEdges")
public class FollowEdgeResource {

	private static final Logger LOG = LoggerFactory.getLogger(FollowEdgeResource.class);

	private FollowerEdgesDAO followersDAO;

	public FollowEdgeResource() {
		this.followersDAO = new FollowerEdgesDAOImpl();
	}

	// let this user follow a user
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void followUser(FollowEdge followEdge) {
		try {
			followersDAO.follow(followEdge.getUserId(), followEdge.getTargetUserId());
		} catch (SQLException e) {
			LOG.error("Exception while adding follower from {} to {}",
					followEdge.getUserId(), followEdge.getTargetUserId(), e);
			throw new RuntimeException(e);
		}
	}

	// unfollow
	@DELETE
	public void unfollowUser(@PathParam("userId") int userId,
			@PathParam("targetUserId") int targetUserId) {
		try {
			followersDAO.unfollow(userId, targetUserId);
		} catch (SQLException e) {
			LOG.error("Exception while removing follower from {} to {}", userId, targetUserId, e);
			throw new RuntimeException(e);
		}
	}
	
}
