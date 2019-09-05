package com.cwctravel.batch.model.csv;

/**
 * CategoryAndFeatureCombination stores information about category and facilities of a room from csv file.
 * <p>
 * CategoryAndFeatureCombination is an intermediate class that helps writing to HOTEL_ROOM table and HOTEL_ROOM_FEATURE_TYPE table in destination
 * MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.RoomCategory} for class RoomCategory about room information such as description and room type
 * <p>
 * and {@link com.cwctravel.batch.model.csv.RoomFeature} for class RoomFeature about room facilities information
 * 
 * @author chris.nie
 */

public class CategoryAndFeatureCombination {

	private RoomCategory category;

	private RoomFeature feature;

	public CategoryAndFeatureCombination() {

	}

	/**
	 * Constructor generating a CategoryAndFeatureCombination with a RoomCategory and a RoomFeature
	 * 
	 * @param category
	 *                 RoomCategory with information about the room
	 * @param feature
	 *                 RoomFeature with information about the facilities of the room
	 */
	public CategoryAndFeatureCombination(RoomCategory category, RoomFeature feature) {
		this.category = category;
		this.feature = feature;
	}

	public RoomCategory getCategory() {
		return category;
	}

	public void setCategory(RoomCategory category) {
		this.category = category;
	}

	public RoomFeature getFeature() {
		return feature;
	}

	public void setFeature(RoomFeature feature) {
		this.feature = feature;
	}

}
