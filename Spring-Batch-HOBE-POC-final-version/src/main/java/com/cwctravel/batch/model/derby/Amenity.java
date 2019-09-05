package com.cwctravel.batch.model.derby;

/**
 * Amenity stores information about facilities of a GuestRoom in a hotel from Derby response.
 * <p>
 * Amenity information includes code of the Amenity, detail description of the Amenity,
 * quantity of the Amenity, roomId of the GuestRoom the Amenity belongs to and
 * feature Id for the Amenity which map to a feature type description of the Amenity
 * @author chris.nie
 *
 */
public class Amenity {

	private int RoomAmenityCode;

	private String prefix;

	private String CodeDetail;

	private int Quantity;

	private long roomId;

	private long featureId;

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getRoomAmenityCode() {
		return RoomAmenityCode;
	}

	public String getCodeDetail() {
		return CodeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		CodeDetail = codeDetail;
	}

	public void setRoomAmenityCode(int roomAmenityCode) {
		RoomAmenityCode = roomAmenityCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
