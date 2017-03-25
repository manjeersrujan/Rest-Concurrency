package com.agoda.exercise.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.agoda.exercise.dao.HotelDao;
import com.agoda.exercise.exception.AgodaServiceException;
import com.agoda.exercise.model.GetHotelsResponse;
import com.agoda.exercise.model.Hotel;
import com.agoda.exercise.service.HotelService;

/**
 * @author Manjeer
 *
 *         Created on Mar 26, 2017
 */
@Component
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelDao hotelDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.agoda.exercise.service.impl.HotelService#getHotels(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public GetHotelsResponse getHotels(String cityId, String sortType) throws AgodaServiceException {

		if (StringUtils.isEmpty(cityId)) {
			throw new AgodaServiceException("CITY_ID_IS_REQUIRED");
		}
		List<Hotel> hotels = hotelDao.getHotelsByCityName(cityId);

		if (sortType != null && sortType.equals("DESC")) {
			Collections.sort(hotels, new Comparator<Hotel>() {

				@Override
				public int compare(Hotel o1, Hotel o2) {
					if (o1 != null && o2 != null) {
						return (int) (o2.getPrice() - o1.getPrice());
					} else {
						/*
						 * Not comparable. So returning 0 (Equal). Or we can
						 * define whatever functionality we want.
						 */
						return 0;
					}
				}
			});
		} else {

			Collections.sort(hotels, new Comparator<Hotel>() {

				@Override
				public int compare(Hotel o1, Hotel o2) {
					if (o1 != null && o2 != null) {
						return (int) (o1.getPrice() - o2.getPrice());
					} else {
						/*
						 * Not comparable. So returning 0 (Equal). Or we can
						 * define whatever functionality we want.
						 */
						return 0;
					}
				}
			});

		}

		GetHotelsResponse getHotelsResponse = new GetHotelsResponse();
		getHotelsResponse.setHotels(hotels);
		return getHotelsResponse;
	}

}
