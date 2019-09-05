package com.cwctravel.batch.model.derby;

/**
 * Position stores the position information of a hotel from Derby response including 
 * latitude and longitude.
 * @author chris.nie
 *
 */
public class Position {
	private String Latitude;
	private String Longitude;
	private String prefix;

	public String getLatitude() {
		return Latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setLatitude( String Latitude ) {
		this.Latitude = Latitude;
	}

	public void setLongitude( String Longitude ) {
		this.Longitude = Longitude;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
