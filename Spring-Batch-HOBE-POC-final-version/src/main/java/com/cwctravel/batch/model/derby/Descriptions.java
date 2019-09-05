package com.cwctravel.batch.model.derby;

/**
 * Descriptions stores the DescriptiveText for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.DescriptiveText} for class DescriptiveText.
 * @author chris.nie
 *
 */
public class Descriptions {
	DescriptiveText DescriptiveTextObject;
	private String prefix;

	public DescriptiveText getDescriptiveText() {
		return DescriptiveTextObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setDescriptiveText( DescriptiveText DescriptiveTextObject ) {
		this.DescriptiveTextObject = DescriptiveTextObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
