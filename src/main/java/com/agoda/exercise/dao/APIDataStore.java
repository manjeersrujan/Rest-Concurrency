package com.agoda.exercise.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;

/**
 * @author Manjeer
 *
 *         Created on Mar 24, 2017
 * 
 *         Making default class. So that, it can be accessed only within the
 *         package.
 */
enum APIDataStore {

	INSTANCE;

	class APIKeyData {
		PriorityQueue<Long> requestTimiesQueue = new PriorityQueue<>();
		/*
		 * Default rate limit is 3 per 10 seconds
		 */
		Long apiKeyRateLimit = 3L;

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
				APIKeyData apiKeyData = new APIKeyData();
				apiKeyRequestTracker.put(prop.getProperty("list-of-api-keys-" + i), new APIKeyData());
			}

			for (String key : apiKeyRequestTracker.keySet()) {
				if (prop.getProperty(key) != null) {
					apiKeyRequestTracker.get(key).apiKeyRateLimit = Long.parseLong(prop.getProperty(key));
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
