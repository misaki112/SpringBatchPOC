package com.cwctravel.batch.model.csv;

/**
 * HotelAndRateCombination stores data about a hotel and its rate to be written 
 * to destination MySQL database from a csv file.
 * <p>
 * HotelAndRateCombination is an intermediate class that helps writing to ipm_hotel table 
 * and ipm_hotel_rate table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.Hotel} for class Hotel 
 * and {@link com.cwctravel.batch.model.csv.HotelRate} for class HotelRate
 * @author chris.nie
 *
 */
public class HotelAndRateCombination {

	private Hotel hotel;

	private HotelRate rate;

	public HotelAndRateCombination() {

	}

	/**
	 * Construct a HotelAndRateCombination
	 * @param hotel Hotel hotel information to be written to ipm_hotel table
	 * @param rate HotelRate rate information about the hotel to be written to ipm_hotel_rate table
	 */
	public HotelAndRateCombination(Hotel hotel, HotelRate rate) {
		this.hotel = hotel;
		this.rate = rate;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public HotelRate getRate() {
		return rate;
	}

	public void setRate(HotelRate rate) {
		this.rate = rate;
	}

}
