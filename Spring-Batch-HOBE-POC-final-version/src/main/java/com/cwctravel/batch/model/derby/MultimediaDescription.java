package com.cwctravel.batch.model.derby;

/**
 * MultimediaDescription stores detail code and images for a Hotel from Derby response.
 * <p>
 * Please See {@link com.cwctravel.batch.model.derby.ImageItems} for class ImageItem.
 * @author chris.nie
 *
 */
public class MultimediaDescription {

	private int AdditionalDetailCode;

	private String prefix;

	private ImageItems ImageItems;

	public int getAdditionalDetailCode() {
		return AdditionalDetailCode;
	}

	public void setAdditionalDetailCode(int additionalDetailCode) {
		AdditionalDetailCode = additionalDetailCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ImageItems getImageItems() {
		return ImageItems;
	}

	public void setImageItems(ImageItems imageItems) {
		ImageItems = imageItems;
	}

}
