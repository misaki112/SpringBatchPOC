package com.cwctravel.batch.model.derby;

/**
 * ImageItem stores information of an image for a hotel from Derby response.
 * <p>
 * ImageItem information includes the hotelId of the hotel the image is for, unique id for the image,
 * format of the image and description about the image.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.ImageFormat} for class ImageFormat and 
 * {@link com.cwctravel.batch.model.derby.Description} for class Description
 * @author chris.nie
 *
 */
public class ImageItem {

	private String hotelId;

	private long imageId;

	private ImageFormat ImageFormat;

	private Description Description;

	private int Category;

	private String prefix;

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public ImageFormat getImageFormat() {
		return ImageFormat;
	}

	public void setImageFormat(ImageFormat imageFormat) {
		ImageFormat = imageFormat;
	}

	public Description getDescription() {
		return Description;
	}

	public void setDescription(Description description) {
		Description = description;
	}

	public int getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
