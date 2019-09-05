package com.cwctravel.batch.model.derby;

/**
 * Award stores information about the Rating of a hotel from Derby response.
 * @author chris.nie
 *
 */
public class Award {
	private String Rating;

	private String prefix;

	public String getRating() {
		return Rating;
	}

	public void setRating(String rating) {
		Rating = rating;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
