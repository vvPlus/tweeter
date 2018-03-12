package com.vv.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.dao.TweetsDAO;
import com.vv.dao.TweetsDAOImpl;
import com.vv.engine.TweetDistributionEngine;
import com.vv.exception.ResourceNotFoundException;
import com.vv.model.Tweet;

@Path("tweets")
public class TweetResource {
	private static final Logger LOG = LoggerFactory.getLogger(TweetResource.class);

	private TweetsDAO tweetsDao;

	public TweetResource() {
		this.tweetsDao = new TweetsDAOImpl();
	}	

	@GET
	@Path("/{tweetId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tweet getTweet(@PathParam("tweetId") int tweetId) throws ResourceNotFoundException {
		try {
			return tweetsDao.getTweet(tweetId);
		} catch (SQLException e) {
			LOG.error("Failed to get the tweet with id {}", tweetId, e);
			throw new RuntimeException(e);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createTweet(Tweet tweet) {
		String timeStamp = "" + System.currentTimeMillis();
		tweet.setCreatedAt(timeStamp);

		try {
			// Save Tweet in DB
			tweetsDao.createTweet(tweet);
			int tweetId = tweetsDao.getLastTweetId();
			System.out.println("Tweet created");
			tweet.setTweetId(tweetId);
			// Give it to the Tweet Distribution engine to do the distribution to the followers
			TweetDistributionEngine.getInstance().distribute(tweet);
		} catch (SQLException e) {
			LOG.error("Failed to create new tweet", e);
			throw new RuntimeException(e);
		} 
	} 
}
