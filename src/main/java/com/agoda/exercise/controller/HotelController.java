package com.agoda.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.exercise.exception.AgodaServiceException;
import com.agoda.exercise.model.GenericServiceResponse;
import com.agoda.exercise.model.GetHotelsResponse;
import com.agoda.exercise.service.HotelService;

/**
 * @author Manjeer
 *
 *         Created on Mar 26, 2017
 */
@RestController
public class HotelController {

	@Autowired
	HotelService hotelService;

	/**
	 * @param cityId
	 * @param sortType
	 * @return
	 * @throws AgodaServiceException
	 */
	@RequestMapping(value = "/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericServiceResponse<GetHotelsResponse> getHotelsByCity(
			@RequestParam(name = "cityId", required = false) String cityId,
			@RequestParam(name = "sortType", required = false, defaultValue = "ASC") String sortType)
			throws AgodaServiceException {
		GenericServiceResponse<GetHotelsResponse> genericServiceResponse = new GenericServiceResponse<GetHotelsResponse>();
		GetHotelsResponse getHotelsResponse = hotelService.getHotels(cityId, sortType);
		genericServiceResponse.setPayload(getHotelsResponse);
		genericServiceResponse.setStatus("SUCCESS");
		return genericServiceResponse;
	}

}
