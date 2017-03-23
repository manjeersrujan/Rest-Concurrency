package com.agoda.exercise.model;

import java.io.Serializable;


/**
 * @author Manjeer
 *
 * Created on Mar 23, 2017
 */
public class GenericServiceResponse<T> implements Serializable {
	
	public GenericServiceResponse() {
		super();
	}
	public GenericServiceResponse(String status, String statusMessage, T payload) {
		super();
		this.status = status;
		this.statusMessage = statusMessage;
		this.payload = payload;
	}

	private static final long serialVersionUID = 8923237327134793040L;
	String status;
	String statusMessage;
	T payload;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}

}
