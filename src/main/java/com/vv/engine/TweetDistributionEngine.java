package com.vv.engine;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.dao.FollowerEdgesDAO;
import com.vv.dao.FollowerEdgesDAOImpl;
import com.vv.model.Tweet;

public class TweetDistributionEngine {

	private static final Logger LOG = LoggerFactory.getLogger(TweetDistributionEngine.class);

	private static final int FEED_SIZE = 100;

	private static final int NUM_OF_THREADS = 4;

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
		executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
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
						LOG.debug("Distributing tweet {} to user {}", tweetToDistribute.getTweetId(), followerId);

						List<Integer> tweetsForFollower = feedCache.get(followerId);
						if (tweetsForFollower == null) {
							tweetsForFollower = Collections.synchronizedList(new LinkedList<Integer>());
							feedCache.put(followerId, tweetsForFollower);
						}

						synchronized (TweetDistributionEngine.this) {
							tweetsForFollower.add(tweetToDistribute.getTweetId());
							// Remove the first tweet if the feed size is greater than FEED_SIZE
							if (tweetsForFollower.size() > FEED_SIZE) {
								tweetsForFollower.remove(0);
							}
						}
					}
				} catch (SQLException e) {
					LOG.error("Failed to distribute the tweet with id {}", tweetToDistribute.getTweetId(), e);
				}
			}			
		});
	}

	private List<Integer> getFollowers(int creatorId) throws SQLException {
		FollowerEdgesDAO followersDAO = new FollowerEdgesDAOImpl();
		return followersDAO.getFollowersForUser(creatorId);
	}

	public List<Integer> getTweetIdsForUser(int userId) {
		return Collections.unmodifiableList(feedCache.get(userId));
	}
}
