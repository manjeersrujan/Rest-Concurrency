package com.agoda.exercise.exception;

/**
 * @author Manjeer
 *
 *         Created on Mar 26, 2017
 * 
 *         Costomized Exception to contain any exception within this application
 */
public class AgodaServiceException extends Exception {

	int statusCode;

	public AgodaServiceException(String message) {
		super(message);
	}

	public AgodaServiceException(AgodaServiceError error) {
		super(error.errorMessage);
	}

	public AgodaServiceException(Throwable ex) {
		super(ex);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651790075167593475L;

}
