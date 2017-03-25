package com.agoda.exercise.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.agoda.exercise.dao.APIKeyDao;
import com.agoda.exercise.exception.AgodaServiceError;
import com.agoda.exercise.exception.AgodaServiceException;

/**
 * @author Manjeer
 *
 *         Created on Mar 23, 2017
 * 
 *         This will handle all the requests that comes to service. It checks
 *         the API key and its status. It will not allow in case of any
 *         issue(Like blocked etc) with API key.
 */
public class APIKeyValidationInterceptor implements HandlerInterceptor {

	@Autowired
	APIKeyDao apiKeyDao;

	public enum APIKeyStatus {
		API_KEY_ALLOWED, API_KEY_NOT_FOUND, API_KEY_BLOCKED, FAILED_TO_GET_API_KEY_STATUS, API_KEY_REQUIRED;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request != null) {
			String apiKey = request.getParameter("apiKey");
			if (apiKey != null && !apiKey.trim().equals("")) {
				APIKeyStatus apiKeyStatus = apiKeyDao.getAPIKeyStatus(apiKey);
				if (apiKeyStatus != null && apiKeyStatus.equals(APIKeyStatus.API_KEY_ALLOWED)) {
					return true;
				} else if (apiKeyStatus != null) {
					throw new AgodaServiceException(apiKeyStatus.toString());
				} else {
					throw new AgodaServiceException(APIKeyStatus.FAILED_TO_GET_API_KEY_STATUS.toString());
				}
			} else {
				throw new AgodaServiceException(APIKeyStatus.API_KEY_REQUIRED.toString());
			}
		}
		/*
		 * Some Issue with the framework/code if it comes here.
		 */
		throw new AgodaServiceException(AgodaServiceError.getGenericError());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*
		 * No Action
		 */
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		/*
		 * No Action
		 */
	}

}
