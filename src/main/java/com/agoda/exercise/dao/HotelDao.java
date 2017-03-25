package com.agoda.exercise.dao;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.agoda.exercise.exception.AgodaServiceException;
import com.agoda.exercise.model.Hotel;

/**
 * @author Manjeer
 *
 *         Created on Mar 23, 2017
 *         
 *         Provides all actions to get info related to Hotels. 
 */
@Component
public class HotelDao {

	public Map<String, List<Hotel>> hotelsByCity = new HashMap<>();

	/**
	 * @throws IOException
	 */
	public HotelDao() throws IOException {

		ICsvMapReader mapReader = null;
		try {
			mapReader = new CsvMapReader(
					new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("hoteldb.csv")),
					CsvPreference.STANDARD_PREFERENCE);

			// the header columns are used as the keys to the Map
			String[] header = mapReader.getHeader(true);

			Map<String, String> customerMap;
			while ((customerMap = mapReader.read(header)) != null) {
				Hotel hotel = new Hotel();
				hotel.setId(customerMap.get("HOTELID"));
				hotel.setPrice(Long.parseLong(customerMap.get("PRICE")));
				hotel.setRoom(customerMap.get("ROOM"));
				hotel.setCity(customerMap.get("CITY"));

				if (hotelsByCity.get(customerMap.get("CITY")) == null) {
					hotelsByCity.put(customerMap.get("CITY"), new ArrayList<Hotel>());
				}
				hotelsByCity.get(customerMap.get("CITY")).add(hotel);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (mapReader != null) {
				mapReader.close();
			}
		}

	}

	/**
	 * @param cityId
	 * @return
	 * @throws AgodaServiceException
	 */
	public List<Hotel> getHotelsByCityName(String cityId) throws AgodaServiceException {
		if (StringUtils.isEmpty(cityId)) {
			throw new AgodaServiceException("CITY_REQUIRED_TO_GET_HOTELS");
		}

		List<Hotel> hotels = new ArrayList<>();
		hotels.addAll(hotelsByCity.get(cityId));
		return hotels;
	}

}
