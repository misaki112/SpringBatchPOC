package com.cwctravel.batch.model.derby;

/**
 * GuestRoom stores information about a room of a hotel from Derby response.
 * <p>
 * GuestRoom information includes type, description, unique id, max occupancy of the room
 * and amenities inside the room.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.TypeRoom} for class TypeRoom for type information,
 * {@link com.cwctravel.batch.model.derby.DescriptiveText} for class DescriptiveText,
 * {@link com.cwctravel.batch.model.derby.Amenities} for class Amenities
 * @author chris.nie
 *
 */
public class GuestRoom {

	private TypeRoom typeRoom;

	private DescriptiveText descriptiveText;

	private String ID;

	private int MaxOccupancy;

	private String RoomTypeName;

	private String prefix;

	private Amenities Amenities;

	private String hotelId;

	private long roomId;

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public Amenities getAmenities() {
		return Amenities;
	}

	public void setAmenities(Amenities amenities) {
		Amenities = amenities;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getRoomTypeName() {
		return RoomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		RoomTypeName = roomTypeName;
	}

	public int getMaxOccupancy() {
		return MaxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		MaxOccupancy = maxOccupancy;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public TypeRoom getTypeRoom() {
		return typeRoom;
	}

	public void setTypeRoom(TypeRoom typeRoom) {
		this.typeRoom = typeRoom;
	}

	public DescriptiveText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(DescriptiveText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}
}
