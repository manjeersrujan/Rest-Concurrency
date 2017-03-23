package com.agoda.exercise.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Manjeer
 *
 *         Created on Mar 23, 2017
 */
public class GetHotelsResponse implements Serializable {

	public GetHotelsResponse() {
		super();
	}

	List<Hotel> hotels;

	private static final long serialVersionUID = 1L;

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotels == null) ? 0 : hotels.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetHotelsResponse other = (GetHotelsResponse) obj;
		if (hotels == null) {
			if (other.hotels != null)
				return false;
		} else if (!hotels.equals(other.hotels))
			return false;
		return true;
	}
}
