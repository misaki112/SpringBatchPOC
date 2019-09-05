package com.cwctravel.batch.model.derby;

/**
 * HotelInfo stores information about a Hotel from Derby response.
 * <p>
 * HotelInfo information includes description of the hotel, position of the hotel, services the
 * hotel provides and status of the hotel.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Descriptions} for class Descriptions, 
 * {@link com.cwctravel.batch.model.derby.Position} for class Position and 
 * {@link com.cwctravel.batch.model.derby.Services} for class Services
 * @author chris.nie
 *
 */
public class HotelInfo {

	Descriptions DescriptionsObject;
	Position PositionObject;
	Services ServicesObject;
	private String HotelStatusCode;
	private String prefix;

	public Descriptions getDescriptions() {
		return DescriptionsObject;
	}

	public Position getPosition() {
		return PositionObject;
	}

	public Services getServices() {
		return ServicesObject;
	}

	public String getHotelStatusCode() {
		return HotelStatusCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setDescriptions( Descriptions DescriptionsObject ) {
		this.DescriptionsObject = DescriptionsObject;
	}

	public void setPosition( Position PositionObject ) {
		this.PositionObject = PositionObject;
	}

	public void setServices( Services ServicesObject ) {
		this.ServicesObject = ServicesObject;
	}

	public void setHotelStatusCode( String HotelStatusCode ) {
		this.HotelStatusCode = HotelStatusCode;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
