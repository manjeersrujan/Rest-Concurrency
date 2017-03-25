package com.agoda.exercise.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;

/**
 * @author Manjeer
 *
 *         Created on Mar 24, 2017
 * 
 *         This is a singleton class(enum) which contains data of each APIKey.
 *         Making it default. So that, it can be accessed only within the
 *         package (APIKeyDAo class needs access specifically).
 */
enum APIDataStore {

	INSTANCE;

	class APIKeyData {
		private PriorityQueue<Long> requestTimiesQueue = new PriorityQueue<>();
		/*
		 * Default rate limit is 3 per 10 seconds
		 */
		private Long apiKeyRateLimit = 3L;

		public PriorityQueue<Long> getRequestTimiesQueue() {
			return requestTimiesQueue;
		}

		public void setRequestTimiesQueue(PriorityQueue<Long> requestTimiesQueue) {
			this.requestTimiesQueue = requestTimiesQueue;
		}

		public Long getApiKeyRateLimit() {
			return apiKeyRateLimit;
		}

		public void setApiKeyRateLimit(Long apiKeyRateLimit) {
			this.apiKeyRateLimit = apiKeyRateLimit;
		}
	}

	private final Map<String, APIKeyData> apiKeyRequestTracker = new HashMap<>();
	private final Map<String, Long> blockedApiKeys = new HashMap<>();

	public Map<String, APIKeyData> getApiKeyRequestTracker() {
		return apiKeyRequestTracker;
	}

	public Map<String, Long> getBlockedApiKeys() {
		return blockedApiKeys;
	}

	private APIDataStore() {
		try {
			/*
			 * Load API Keys into apiKeyRequestTracker
			 */
			Properties prop = new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream("agoda-api-key.properties"));
			int coutOfApiKeys = Integer.parseInt(prop.getProperty("count-of-api-keys"));
			for (int i = 1; i <= coutOfApiKeys; i++) {
				if (prop.getProperty("list-of-api-keys-" + i) != null) {
					apiKeyRequestTracker.put(prop.getProperty("list-of-api-keys-" + i), new APIKeyData());
				}
			}

			for (String key : apiKeyRequestTracker.keySet()) {
				if (prop.getProperty(key + "-rate-limit") != null) {
					apiKeyRequestTracker.get(key)
							.setApiKeyRateLimit(Long.parseLong(prop.getProperty(key + "-rate-limit")));
				}
			}

		} catch (Exception e) {
			/*
			 * Need to handle this. Server should not start in this case
			 */
			e.printStackTrace();
		}
	}
}
