package com.cwctravel.batch.model.derby;

/**
 * AddressLine stores information about the street and door number of a hotel from Derby response.
 * @author chris.nie
 * 
 */
public class AddressLine {
	private String text;

	private String prefix;

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
