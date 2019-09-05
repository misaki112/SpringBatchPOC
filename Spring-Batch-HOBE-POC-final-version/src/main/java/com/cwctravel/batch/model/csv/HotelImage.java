package com.cwctravel.batch.model.csv;

import java.util.HashSet;
import java.util.Set;

import com.cwctravel.batch.model.derby.ImageURL;

/**
 * HotelImage stores the information about an image for a hotel from csv file.
 * <p>
 * HotelImage information includes the title which describe the image, a unique id for the image,
 * the hotel the image is for, and a set of urls for the image.
 * @author chris.nie
 *
 */
public class HotelImage {

	private String title;

	private long id;

	private Hotel hotel;

	private Set<ImageURL> urlSet;

	public HotelImage() {

	}

	/**
	 * Construct a new HotelImage
	 * @param title String detail description about the image 
	 * @param id long unique id for the image
	 * @param hotel Hotel the hotel the image is for
	 */
	public HotelImage(String title, long id, Hotel hotel) {
		this.title = title;
		this.id = id;
		this.hotel = hotel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<ImageURL> getUrlSet() {
		if(urlSet == null) {
			urlSet = new HashSet<>();
		}
		return urlSet;
	}

	public void setUrlList(Set<ImageURL> urlSet) {
		this.urlSet = urlSet;
	}

}
