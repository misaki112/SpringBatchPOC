package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.InterestFeature;

/**
 * HotelPointOfInterestItemProcessor fill in data such as hotelId and hotel of InterestFeature.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.InterestFeature} for class InterestFeature
 * @author chris.nie
 *
 */
public class HotelPointOfInterestItemProcessor implements ItemProcessor<InterestFeature, InterestFeature> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Construct a new InterestFeature given a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public HotelPointOfInterestItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Fill in data such as hotelId and hotel of InterestFeature
	 */
	@Override
	public InterestFeature process(InterestFeature item) throws Exception {
		String hotelId = item.getHotelId();
		Hotel hotel = hotelIdMap.get(hotelId);

		if(hotel == null) {
			hotel = new Hotel(hotelId);
			hotelIdMap.put(hotelId, hotel);
		}

		item.setHotel(hotel);
		hotel.getInterestSet().add(item);

		return item;

	}

}
