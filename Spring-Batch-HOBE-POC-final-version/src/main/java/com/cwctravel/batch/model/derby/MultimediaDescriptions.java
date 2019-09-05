package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * MultimediaDescriptions is a list of MultimediaDescription for a Hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.MultimediaDescription} for class MultimediaDescription.
 * @author chris.nie
 *
 */
public class MultimediaDescriptions {
	private List<MultimediaDescription> MultimediaDescription;
	private String prefix;

	public List<MultimediaDescription> getMultimediaDescription() {
		return MultimediaDescription;
	}

	public void setMultimediaDescription(List<MultimediaDescription> multimediaDescription) {
		MultimediaDescription = multimediaDescription;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
