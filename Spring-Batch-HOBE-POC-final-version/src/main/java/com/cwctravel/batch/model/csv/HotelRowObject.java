package com.cwctravel.batch.model.csv;

/**
 * HotelRowObject stores data of a row from HotelAndRate2.csv representing a hotel and its rate from csv file.
 * @author chris.nie
 *
 */
public class HotelRowObject {

	private String hotelId;

	private String hotelName;

	private String extendedDescription;

	private String season;

	private String description;

	public HotelRowObject() {

	}

	/**
	 * Construct a new HotelRowObject
	 * @param hotelId String unique hotelId for the hotel
	 * @param hotelName String name of the hotel
	 * @param extendedDescription String detail description about the hotel
	 * @param season String the season a certain hotel rate is for including year and season information
	 * @param description String detail description about a certain rate of the hotel including 
	 * room type and booking time
	 */
	public HotelRowObject(String hotelId, String hotelName, String extendedDescription, 
			String season, String description) {
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.extendedDescription = extendedDescription;
		this.season = season;
		this.description = description;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getExtendedDescription() {
		return extendedDescription;
	}

	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
