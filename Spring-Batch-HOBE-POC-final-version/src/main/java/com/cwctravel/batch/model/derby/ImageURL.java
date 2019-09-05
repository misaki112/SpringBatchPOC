package com.cwctravel.batch.model.derby;

import com.cwctravel.batch.model.csv.HotelImage;

/**
 * ImageURL stores URL of an Image of a hotel from Derby response
 * <p>
 * ImageURL information includes the url String, the id of the image the url is for
 * @author chris.nie
 *
 */

public class ImageURL {

	private long imageId;

	private String url;

	private HotelImage image;

	public ImageURL() {

	}

	/**
	 * Construct a new ImageURL
	 * @param imageId long the id of the image the url is for
	 * @param url String the url String 
	 * @param image HotelImage the image the url is for
	 */
	public ImageURL(long imageId, String url, HotelImage image) {
		this.imageId = imageId;
		this.url = url;
		this.image = image;
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

	public HotelImage getImage() {
		return image;
	}

	public void setImage(HotelImage image) {
		this.image = image;
	}
}
