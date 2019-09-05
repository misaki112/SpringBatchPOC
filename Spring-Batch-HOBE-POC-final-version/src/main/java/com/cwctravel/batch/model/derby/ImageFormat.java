package com.cwctravel.batch.model.derby;

/**
 * ImageFormat stores information about the format of an ImageItem for a hotel from Derby response.
 * <p>
 * ImageFormat information includes URL, height and width of the image.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.URL} for class URL.
 * @author chris.nie
 *
 */
public class ImageFormat {

	private URL URL;

	private int Height;

	private int Width;

	private String prefix;

	public URL getURL() {
		return URL;
	}

	public void setURL(URL uRL) {
		URL = uRL;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
