package com.cwctravel.batch.model.derby;

/**
 * CityName stores the name of a city from Derby response
 * @author chris.nie
 *
 */
public class CityName {
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
