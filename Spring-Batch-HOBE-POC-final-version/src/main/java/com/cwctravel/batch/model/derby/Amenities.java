package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Amenities is a list with all Amenity of a GuestRoom in a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Amenity} for class Amenity
 * @author chris.nie
 *
 */

public class Amenities {

	private List<Amenity> Amenity;

	private String prefix;

	public List<Amenity> getAmenity() {
		return Amenity;
	}

	public void setAmenity(List<Amenity> amenity) {
		Amenity = amenity;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
