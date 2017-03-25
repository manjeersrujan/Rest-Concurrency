package com.agoda.exercise.service;

import com.agoda.exercise.exception.AgodaServiceException;
import com.agoda.exercise.model.GetHotelsResponse;

/**
 * @author Manjeer
 *
 * Created on Mar 26, 2017
 */
public interface HotelService {

	GetHotelsResponse getHotels(String cityId, String sortType) throws AgodaServiceException;

}