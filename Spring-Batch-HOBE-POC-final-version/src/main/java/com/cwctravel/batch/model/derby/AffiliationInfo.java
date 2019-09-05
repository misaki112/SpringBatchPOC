package com.cwctravel.batch.model.derby;

/**
 * AffiliationInfo stores Awards and prefix of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Awards} for class Awards.
 * @author chris.nie
 *
 */

public class AffiliationInfo {
	Awards AwardsObject;
	private String prefix;

	public Awards getAwards() {
		return AwardsObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setAwards( Awards AwardsObject ) {
		this.AwardsObject = AwardsObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
