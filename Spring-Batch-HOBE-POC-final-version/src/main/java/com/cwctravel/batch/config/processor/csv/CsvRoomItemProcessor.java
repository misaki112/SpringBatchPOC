package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;
import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.RoomCategory;
import com.cwctravel.batch.model.csv.RoomFeature;
import com.cwctravel.batch.model.csv.RoomRowObject;

/**
 * CsvRoomItemProcessor process data from RoomRowObject to CategoryAndFeatureCombination
 * <p>
 * CsvRoomItemProcessor extract fields of RoomRowObject and generate new CategoryAndFeatureCombination using those fields.
 * @author chris.nie
 *
 */
public class CsvRoomItemProcessor implements ItemProcessor<RoomRowObject, CategoryAndFeatureCombination> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Constructor generating a CsvImageItemProcessor with a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public CsvRoomItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Extract data from fields in RoomRowObject and use them to build a new CategoryAndFeatureCombination
	 * 
	 * @param  row RoomRowObject representing a row of hotel room data in csv file to be processed
	 * @return CategoryAndFeatureCombination Intermediate class with information of RoomFeature and room that helps 
	 * writing to hotel_room_feature_type table and hotel_room table in destination MySQL database.
	 */
	public CategoryAndFeatureCombination process(RoomRowObject row) throws Exception {

		String hotelId = row.getHotelId();

		String name = row.getName();

		String description = row.getDescription();

		long id = row.getId();

		long featureId = row.getFeatureId();

		long categoryId = row.getCategoryId();

		Hotel hotel = hotelIdMap.get(hotelId);

		if(hotel == null) {
			hotel = new Hotel(hotelId);
			hotelIdMap.put(hotelId, hotel);
		}

		RoomCategory category = new RoomCategory(name, description, id, hotel);

		RoomFeature feature = new RoomFeature(featureId, categoryId, category);

		hotel.getCategorySet().add(category);

		category.getFeatureSet().add(feature);

		return new CategoryAndFeatureCombination(category, feature);
	}

}
