package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Awards is a list of Award of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Award} for class Award
 * @author chris.nie
 *
 */

public class Awards {
	private List<Award> Award;
	private String prefix;

	public List<Award> getAward() {
		return Award;
	}

	public void setAward(List<Award> award) {
		Award = award;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
