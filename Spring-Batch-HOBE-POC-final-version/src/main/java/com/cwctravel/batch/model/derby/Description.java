package com.cwctravel.batch.model.derby;

/**
 * Description stores the detail description about an image for a hotel from Derby response.
 * @author chris.nie
 *
 */
public class Description {

	private String Caption;

	private String prefix;

	public String getCaption() {
		return Caption;
	}

	public void setCaption(String caption) {
		Caption = caption;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
