package com.cwctravel.batch.model.derby;

/**
 * URL stores URL of an Image from Derby response.
 * <p>
 * URL information includes the imageId of the Image and a URL of it.
 * @author chris.nie
 *
 */
public class URL {

	private long imageId;

	private String text;

	private String prefix;


	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
