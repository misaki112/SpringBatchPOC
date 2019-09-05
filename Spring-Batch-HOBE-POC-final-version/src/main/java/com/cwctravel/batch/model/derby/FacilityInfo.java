package com.cwctravel.batch.model.derby;

/**
 * FacilityInfo stores GuestRooms information for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.GuestRooms} for class GuestRooms
 * @author chris.nie
 *
 */
public class FacilityInfo {
	GuestRooms GuestRoomsObject;
	private String prefix;

	public GuestRooms getGuestRooms() {
		return GuestRoomsObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setGuestRooms( GuestRooms GuestRoomsObject ) {
		this.GuestRoomsObject = GuestRoomsObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
