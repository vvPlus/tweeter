package com.vv.resource;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.dao.TweetsDAO;
import com.vv.dao.TweetsDAOImpl;
import com.vv.engine.TweetDistributionEngine;
import com.vv.exception.ResourceNotFoundException;
import com.vv.model.Tweet;

@Path("feed")
public class FeedResource {

	private static final Logger LOG = LoggerFactory.getLogger(FeedResource.class);

	private TweetsDAO tweetsDAO;

	public FeedResource() {
		this.tweetsDAO = new TweetsDAOImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tweet> getFeedForUser(@QueryParam("userId") int userId) throws ResourceNotFoundException {
		List<Integer> listOfTweetsForUser = TweetDistributionEngine.getInstance().getTweetIdsForUser(userId);
		try {
			return getTweetsForUser(listOfTweetsForUser);
		} catch (SQLException e) {
			LOG.error("Couldn't get feed for user {}",userId, e);
			throw new RuntimeException(e);
		}
	}

	private List<Tweet> getTweetsForUser(List<Integer> tweetIds) throws SQLException {
		return tweetsDAO.getTweetsForUser(tweetIds);
	}
}
