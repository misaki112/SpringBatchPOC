package com.cwctravel.batch.model.csv;

/**
 * ImageRowObject stores data of a row from ImageAndUrl.csv representing an image and its url of a hotel
 * from csv files.
 * @author chris.nie
 *
 */
public class ImageRowObject {

	private String hotelId;

	private String title;

	private long id;

	private long imageId;

	private String url;

	public ImageRowObject() {

	}

	/**
	 * Construct a new ImageRowObject
	 * @param hotelId String hotelId of the hotel the image belongs to
	 * @param title String title and description of the image
	 * @param id long unique id for the image
	 * @param imageId long unique id for the image
	 * @param url String url for the image
	 */
	public ImageRowObject(String hotelId, String title, long id, long imageId, String url) {
		this.hotelId = hotelId;
		this.title = title;
		this.id = id;
		this.imageId = imageId;
		this.url = url;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
