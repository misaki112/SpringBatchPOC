package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * GuestRooms is a list of GuestRoom for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.GuestRoom} for class GuestRoom.
 * @author chris.nie
 *
 */
public class GuestRooms {
	private List<GuestRoom> GuestRoom;
	private String prefix;


	public List<GuestRoom> getGuestRoom() {
		return GuestRoom;
	}

	public void setGuestRoom(List<GuestRoom> guestRoom) {
		GuestRoom = guestRoom;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
