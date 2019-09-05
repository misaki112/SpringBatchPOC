package com.cwctravel.batch.model.csv;

/**
 * HotelRate stores information about the rate of a hotel from csv file.
 * <p>
 * HotelRate information includes the season that rate is for, detail description of the rate and 
 * the hotel the rate is for.
 * @author chris.nie
 *
 */
public class HotelRate {

	private String season;

	private String description;

	private Hotel hotel;

	public HotelRate() {

	}

	/**
	 * Construct a new HotelRate
	 * @param season String the season that rate is for
	 * @param description String detail description of the rate
	 * @param hotel Hotel the hotel the rate of for
	 */
	public HotelRate(String season, String description, Hotel hotel) {
		this.season = season;
		this.description = description;
		this.hotel = hotel;
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

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
