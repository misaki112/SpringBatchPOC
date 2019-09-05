package com.cwctravel.batch.model.csv;

/**
 * RoomFeature stores information about a facility inside a room from csv file to be written to 
 * destination MySQL database.
 * <p>
 * RoomFeature information includes featureId of the facility, 
 * and RoomCategory which represent the room the facility is in and id of that room.
 * @author chris.nie
 *
 */
public class RoomFeature {

	private long featureId;

	private long categoryId;

	private RoomCategory category;

	public RoomFeature() {

	}

	/**
	 * 
	 * @param featureId long id of the facility for feature type mapping
	 * @param categoryId long id of the room the facility is in
	 * @param category RoomCategory the room the facility is in
	 */
	public RoomFeature(long featureId, long categoryId, RoomCategory category) {
		this.featureId = featureId;
		this.categoryId = categoryId;
		this.category = category;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public RoomCategory getCategory() {
		return category;
	}

	public void setCategory(RoomCategory category) {
		this.category = category;
	}

}
