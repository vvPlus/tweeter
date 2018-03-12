package com.vv.engine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.dao.FollowerEdgesDAO;
import com.vv.dao.FollowerEdgesDAOImpl;
import com.vv.model.Tweet;
import com.vv.resource.FollowEdgeResource;

public class TweetDistributionEngine {

	private static final Logger LOG = LoggerFactory.getLogger(TweetDistributionEngine.class);

	private static TweetDistributionEngine engine = null;
	private ExecutorService executorService; 
	private Map<Integer, List<Integer>> feedCache;
	
	private TweetDistributionEngine() {
		feedCache = new ConcurrentHashMap<>();
	}

	public static TweetDistributionEngine getInstance() {
		if (engine == null) {
			engine = new TweetDistributionEngine();
		}
		return engine;
	}

	public void startEngine() {
	   // Setup thread pool and start polling the queue
		executorService = Executors.newFixedThreadPool(4);
	}
	
	public void stopEngine() {
		executorService.shutdown();
	}

	public void distribute(final Tweet tweetToDistribute) {
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				int creatorId = tweetToDistribute.getCreatorId();
				//get followers for this creator Id 
				List<Integer> followers;
				try {
					followers = getFollowers(creatorId);
					// Store in FeedCache. 
					for (Integer followerId : followers) {
						List<Integer> tweetsForFollower = feedCache.get(followerId);
						if (tweetsForFollower == null) {
							tweetsForFollower = new ArrayList<>();
						}
						LOG.debug("Tweet to distribute: ", tweetToDistribute.getTweetId());
						tweetsForFollower.add(tweetToDistribute.getTweetId());
						feedCache.put(followerId, tweetsForFollower);
					}
				} catch (SQLException e) {
					LOG.error(this.getClass().getName(), e.getMessage());
				}
			}			
		});
	}

	private List<Integer> getFollowers(int creatorId) throws SQLException {
		FollowerEdgesDAO followersDAO = new FollowerEdgesDAOImpl();
		return followersDAO.getFollowersForUser(creatorId);
	}

	public List<Integer> getTweetIdsForUser(int userId) {
		return feedCache.get(userId);
	}
}
