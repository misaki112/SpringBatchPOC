package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Recreations is a list of Recreation of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Recreation} for class Recreation.
 * @author chris.nie
 *
 */
public class Recreations {
	private List<Recreation> Recreation;
	private String prefix;

	public List<Recreation> getRecreation() {
		return Recreation;
	}

	public void setRecreation(List<Recreation> recreation) {
		Recreation = recreation;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
