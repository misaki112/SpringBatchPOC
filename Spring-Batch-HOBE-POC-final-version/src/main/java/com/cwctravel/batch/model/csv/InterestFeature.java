package com.cwctravel.batch.model.csv;

/**
 * InterestFeature stores information of place of interest around a hotel from csv file 
 * to be written to destination MySQL database.
 * <p>
 * InterestFeature information includes featureId of the place, hotel and hotelId the place is close to
 * @author chris.nie
 *
 */
public class InterestFeature {

	private long featureId;

	private Hotel hotel;

	private String hotelId;

	public InterestFeature() {

	}

	/**
	 * Construct a new InterestFeature
	 * @param featureId long featureId of the place for featureType mapping
	 * @param hotelId String hotelId of the hotel the place is close to
	 */
	public InterestFeature(long featureId, String hotelId) {
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
