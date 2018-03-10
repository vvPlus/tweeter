package com.vv.engine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.vv.model.Tweet;

public class TweetDistributionEngine {
	
	private TweetDistributionEngine engine = null;
	private BlockingQueue<Tweet> queue = new LinkedBlockingQueue<>();
	private TweetDistributionEngine() {
		
	}
	
	public TweetDistributionEngine getInstance() {
		if (engine == null) {
			engine = new TweetDistributionEngine();
		}
		return engine;
	}
	
	 
	
}
