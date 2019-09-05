package com.cwctravel.batch.model.derby;

/**
 * PostalCode stores information about the postal code of a hotel from Derby response.
 * @author chris.nie
 *
 */
public class PostalCode {
	private String prefix;

	private String text;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


}
