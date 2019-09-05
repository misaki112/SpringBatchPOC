package com.cwctravel.batch.model.derby;

/**
 * TypeRoom stores information about a GuestRoom of a hotel from Derby response.
 * <p>
 * TypeRoom information includes name and RoomTypeCode of a GuestRoom
 * @author chris.nie
 *
 */
public class TypeRoom {

	private String Name;

	private String RoomTypeCode;

	private String prefix;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRoomTypeCode() {
		return RoomTypeCode;
	}

	public void setRoomTypeCode(String roomTypeCode) {
		RoomTypeCode = roomTypeCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
