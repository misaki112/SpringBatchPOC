package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelRate;
import com.cwctravel.batch.model.csv.HotelRowObject;

/**
 * CsvHotelItemProcessor process data from HotelRowObject to HotelAndRateCombination
 * <p>
 * CsvHotelItemProcessor extract data from fields in HotelRowObject and use them to build a new HotelAndRateCombination
 * and add new Hotels with their hotelIds to the hotelIdMap.
 * @author chris.nie
 *
 */
public class CsvHotelItemProcessor implements ItemProcessor<HotelRowObject, HotelAndRateCombination> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Constructor generating a CsvHotelItemProcessor with a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public CsvHotelItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Extract data from fields in HotelRowObject and use them to build a new HotelAndRateCombination
	 * Add Hotels with their hotelIds to the hotelIdMap if the Hotel has never appear before.
	 * 
	 * @param  row HotelRowObject  representing a row of hotel and hotel rate data in csv file to be processed
	 * @return HotelAndRateCombination Intermediate class with information of Hotel and HotelRate that helps 
	 * writing to ipm_hotel table and ipm_hotel_rate table in destination MySQL database.
	 */
	public HotelAndRateCombination process(HotelRowObject row) throws Exception {

		String hotelId = row.getHotelId();

		String hotelName = row.getHotelName();

		String extendedDescription = row.getExtendedDescription();

		String season = row.getSeason();

		String description = row.getDescription();

		Hotel hotel = new Hotel(hotelId, hotelName, extendedDescription);

		HotelRate rate = new HotelRate(season, description, hotel);

		hotel.setRate(rate);

		hotelIdMap.put(hotelId, hotel);

		return new HotelAndRateCombination(hotel, rate);
	}

}
