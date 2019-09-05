package com.cwctravel.batch.model.csv;

/**
 * RoomRowObject stores data of a row from Room.csv representing a room of a hotel.
 * <p>
 * RoomRowObject information includes the name, description, id of a room, featureId of 
 * a facility equipped in that room and the hotelId of the hotel the room is in.
 * @author chris.nie
 *
 */
public class RoomRowObject {

	private String hotelId;

	private String name;

	private String description;

	private long id;

	private long featureId;

	private long categoryId;

	public RoomRowObject() {

	}

	/**
	 * Construct a new RoomRowObject
	 * @param hotelId String hotelId of the hotel the room is in
	 * @param name String name of the room
	 * @param description String description of the room
	 * @param id long unique id of the room
	 * @param featureId long feeatureId of a facility in the room for feature type mapping
	 * @param categoryId long id of the room the facility is in
	 */
	public RoomRowObject(String hotelId, String name, String description, long id, long featureId, long categoryId) {
		this.hotelId = hotelId;
		this.name = name;
		this.description = description;
		this.id = id;
		this.featureId = featureId;
		this.categoryId = categoryId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
