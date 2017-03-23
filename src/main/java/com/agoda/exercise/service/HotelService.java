package com.agoda.exercise.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agoda.exercise.dao.HotelDao;
import com.agoda.exercise.exception.AgodaServiceException;
import com.agoda.exercise.model.GetHotelsResponse;
import com.agoda.exercise.model.Hotel;

@Component
public class HotelService {

	@Autowired
	HotelDao hotelDao;

	public GetHotelsResponse getHotels(String cityId, String sortType) throws AgodaServiceException {
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
