package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelFeature;

/**
 * HotelFeatureItemProcessor fill in data such as hotelId and hotel in HotelFeature.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.HotelFeature} for class HotelFeature.
 * @author chris.nie
 *
 */
public class HotelFeatureItemProcessor implements ItemProcessor<HotelFeature, HotelFeature> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Construct a new HotelFeatureItemProcessor given a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public HotelFeatureItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Fill in data such as hotelId and hotel in HotelFeature
	 */
	@Override
	public HotelFeature process(HotelFeature item) throws Exception {
		String hotelId = item.getHotelId();
		Hotel hotel = hotelIdMap.get(hotelId);
		if(hotel == null) {
			hotel = new Hotel(hotelId);
			hotelIdMap.put(hotelId, hotel);
		}
		item.setHotel(hotel);
		hotel.getFeatureSet().add(item);
		return item;

	}

}
