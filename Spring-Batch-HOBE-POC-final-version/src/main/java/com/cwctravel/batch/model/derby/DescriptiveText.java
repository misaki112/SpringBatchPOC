package com.cwctravel.batch.model.derby;

/**
 * DescriptiveText stores the detail description for a hotel from Derby response.
 * @author chris.nie
 *
 */
public class DescriptiveText {
	private String prefix;
	private String text;

	public String getPrefix() {
		return prefix;
	}

	public String getText() {
		return text;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}

	public void setText( String text ) {
		this.text = text;
	}
}
