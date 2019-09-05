package com.cwctravel.batch.model.csv;

/**
 * HotelFeature stores information about a feature or service offer by a hotel from csv file.
 * <p>
 * HotelFeature information includes id for the feature, the hotel the feature is for and its hotelId.
 * @author chris.nie
 *
 */
public class HotelFeature {

	private long featureId;

	private String hotelId;

	private Hotel hotel;

	public HotelFeature() {

	}

	/**
	 * Construct a new HotelFeature
	 * @param featureId long featureId for the feature which helps mapping for featureType
	 * @param hotelId String unique Id of the hotel the feature is for
	 */
	public HotelFeature(long featureId, String hotelId) {
		super();
		this.featureId = featureId;
		this.hotelId = hotelId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
