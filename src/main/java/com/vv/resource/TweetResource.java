package com.vv.resource;

import java.sql.SQLException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vv.dao.TweetsDAO;
import com.vv.dao.TweetsDAOImpl;
import com.vv.model.Tweet;

@Path("tweets")
public class TweetResource {
	
	private TweetsDAO tweetsDao;

	public TweetResource() {
		this.tweetsDao = new TweetsDAOImpl();
	}
	
	@GET
	@Path("/{tweetId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tweet getTweet(@PathParam("tweetId") long tweetId) {
		Tweet tweet = new Tweet();
		tweet.setTweetId(1);
		tweet.setTweetText("Hello, World1!");
		return tweet;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createTweet(Tweet tweet) {
		String timeStamp = "" + System.currentTimeMillis();
		tweet.setCreatedAt(timeStamp);
		
		try {
			tweetsDao.createTweet(tweet);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	} 
}
