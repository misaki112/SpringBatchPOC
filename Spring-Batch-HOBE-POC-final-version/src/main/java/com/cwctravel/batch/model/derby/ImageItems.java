package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * ImageItems is a list of ImageItem for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.ImageItem} for class ImageItem.
 * @author chris.nie
 *
 */
public class ImageItems {
	private List<ImageItem> ImageItem;

	private String prefix;

	public List<ImageItem> getImageItem() {
		return ImageItem;
	}

	public void setImageItem(List<ImageItem> imageItem) {
		ImageItem = imageItem;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
