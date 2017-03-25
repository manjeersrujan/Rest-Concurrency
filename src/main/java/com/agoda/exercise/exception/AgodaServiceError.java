package com.agoda.exercise.exception;

import java.io.Serializable;

/**
 * @author Manjeer
 *
 *         Created on Mar 26, 2017
 *         
 *         Agoda Service Error format.
 */
public class AgodaServiceError implements Serializable {

	public AgodaServiceError() {
		super();
	}

	public AgodaServiceError(int status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9161253911517761782L;
	int status;
	String errorMessage;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static AgodaServiceError getGenericError() {
		return new AgodaServiceError(500, "Internal Server Error");
	}

}
