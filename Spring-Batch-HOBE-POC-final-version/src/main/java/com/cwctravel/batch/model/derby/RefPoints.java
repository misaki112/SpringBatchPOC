package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * RefPoints is a list of RefPoint which represent place of interest around a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.RefPoint} for class RefPoint
 * @author chris.nie
 *
 */
public class RefPoints {
	private List<RefPoint> RefPoint;
	private String prefix;

	public List<RefPoint> getRefPoint() {
		return RefPoint;
	}

	public void setRefPoint(List<RefPoint> refPoint) {
		RefPoint = refPoint;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
