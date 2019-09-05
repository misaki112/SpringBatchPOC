package com.cwctravel.batch.model.csv;

import com.cwctravel.batch.model.derby.ImageURL;

/**
 * ImageAndUrlCombination stores information about an image and its url of a room from csv file.
 * <p>
 * ImageAndUrlCombination is an intermediate class that helps writing to hotel_image table and hotel_image_url
 * table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.HotelImage} for class HotelImage about image information such as description 
 * <p>
 * and {@link com.cwctravel.batch.model.derby.ImageURL} for class ImageURL about image url information
 * @author chris.nie
 *
 */
public class ImageAndUrlCombination {

	private HotelImage image;

	private ImageURL imageUrl;

	public ImageAndUrlCombination() {

	}

	/**
	 * Construct a new ImageAndUrlCombination
	 * @param image HotelImage the image the information in ImageAndUrlCombination is for
	 * @param imageUrl ImageURL url for that image
	 */
	public ImageAndUrlCombination(HotelImage image, ImageURL imageUrl) {
		this.image = image;
		this.imageUrl = imageUrl;
	}

	public HotelImage getImage() {
		return image;
	}

	public void setImage(HotelImage image) {
		this.image = image;
	}

	public ImageURL getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(ImageURL imageUrl) {
		this.imageUrl = imageUrl;
	}

}
