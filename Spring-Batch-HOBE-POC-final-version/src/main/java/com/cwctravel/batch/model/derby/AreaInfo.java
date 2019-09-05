package com.cwctravel.batch.model.derby;

/**
 * AreaInfo contains information about surrounding areas of a hotel from Derby response.
 * <p>
 * AreaInfo information includes RefPoints which are places near the hotel
 * and Recreations near the Hotel.
 * Please see {@link com.cwctravel.batch.model.derby.RefPoints} for class RefPoints 
 * and {@link com.cwctravel.batch.model.derby.Recreations} for class Recreations.
 * @author chris.nie
 *
 */

public class AreaInfo {
	RefPoints RefPointsObject;
	Recreations RecreationsObject;
	private String prefix;

	public RefPoints getRefPoints() {
		return RefPointsObject;
	}

	public Recreations getRecreations() {
		return RecreationsObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setRefPoints( RefPoints RefPointsObject ) {
		this.RefPointsObject = RefPointsObject;
	}

	public void setRecreations( Recreations RecreationsObject ) {
		this.RecreationsObject = RecreationsObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
