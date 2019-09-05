package com.cwctravel.batch.model.csv;

import java.util.HashSet;
import java.util.Set;

/**
 * RoomCategory stores information about a room of a hotel from csv file to be written to 
 * destination MySQL database.
 * <p>
 * RoomCategory information includes the name, description and id of the room, 
 * a set of facilities inside the room and the hotel the room is in
 * @author chris.nie
 *
 */
public class RoomCategory {

	private String name;

	private String description;

	private long id;

	private Set<RoomFeature> featureSet;

	private Hotel hotel;

	public RoomCategory() {

	}

	/**
	 * Construct a new RoomCategory
	 * @param name String the name of the room
	 * @param description String detail description of the room
	 * @param id long unique id of the room
	 * @param hotel Hotel the hotel the room is in
	 */
	public RoomCategory(String name, String description, long id, Hotel hotel) {
		this.name = name;
		this.description = description;
		this.id = id;
		this.hotel = hotel;
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

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<RoomFeature> getFeatureSet() {
		if(featureSet == null) {
			featureSet = new HashSet<>();
		}
		return featureSet;
	}

	public void setFeatureSet(Set<RoomFeature> featureSet) {
		this.featureSet = featureSet;
	}

}
