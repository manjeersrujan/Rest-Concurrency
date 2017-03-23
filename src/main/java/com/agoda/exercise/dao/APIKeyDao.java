package com.agoda.exercise.dao;

import java.util.Date;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

import com.agoda.exercise.dao.APIDataStore.APIKeyData;
import com.agoda.exercise.interceptor.APIKeyValidationInterceptor.APIKeyStatus;

/**
 * @author Manjeer
 *
 *         Created on Mar 23, 2017
 */
@Component
public final class APIKeyDao {

	private static final int BLOCKING_PERIOD = 5 * 60 * 1000;
	private static final int TIME_UNIT_FOR_RATE_LIMIT_IN_SECONDS = 10;
	APIDataStore apiDataStore = APIDataStore.INSTANCE;

	public APIKeyStatus getAPIKeyStatus(String apiKey) {
		Map<String, APIKeyData> apiKeyRequestTracker = apiDataStore.getApiKeyRequestTracker();
		Map<String, Long> blockedApiKeys = apiDataStore.getBlockedApiKeys();
		if (apiKeyRequestTracker == null) {
			return APIKeyStatus.API_KEY_NOT_FOUND;
		} else {

			APIKeyData apiKeyData = apiKeyRequestTracker.get(apiKey);
			if (apiKeyData == null) {
				return APIKeyStatus.API_KEY_NOT_FOUND;
			}
			PriorityQueue<Long> apiKeyRequestTimes = apiKeyData.getRequestTimiesQueue();

			Date date = new Date();
			if (blockedApiKeys.get(apiKey) != null) {
				if (blockedApiKeys.get(apiKey) < date.getTime()) {
					blockedApiKeys.remove(apiKey);
				} else {
					return APIKeyStatus.API_KEY_BLOCKED;
				}
			}
			/*
			 * 
			 */
			synchronized (apiKeyRequestTimes) {
				if (apiKeyRequestTimes.size() < apiKeyData.apiKeyRateLimit) {
					apiKeyRequestTimes.add(date.getTime());
					return APIKeyStatus.API_KEY_ALLOWED;
				} else {
					Long lastNthRequestTime = 0L;
					while (apiKeyRequestTimes.size() >= apiKeyData.apiKeyRateLimit) {
						lastNthRequestTime = apiKeyRequestTimes.poll();
					}
					apiKeyRequestTimes.add(date.getTime());
					if (lastNthRequestTime > (date.getTime() - TIME_UNIT_FOR_RATE_LIMIT_IN_SECONDS * 1000)) {
						blockedApiKeys.put(apiKey, date.getTime() + BLOCKING_PERIOD);
						apiKeyRequestTimes.clear();
						return APIKeyStatus.API_KEY_BLOCKED;
					} else {
						return APIKeyStatus.API_KEY_ALLOWED;
					}
				}

			}

		}

	}

}