package com.cwctravel.batch.model.derby;

/**
 * County stores the name of a county from Derby response.
 * @author chris.nie
 *
 */
public class County {
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
