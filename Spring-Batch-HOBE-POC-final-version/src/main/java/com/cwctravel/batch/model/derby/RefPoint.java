package com.cwctravel.batch.model.derby;

/**
 * RefPoint stores information about place of interest around a hotel from Derby response.
 * <p>
 * RefPoint information includes the code and featureId of the place for
 * featureTyoe mapping, name of the place, hotelId of the hotel the place is close to.
 * @author chris.nie
 *
 */
public class RefPoint {
	private int IndexPointCode;

	private String RefPointName;

	private String prefix;

	private String hotelId;

	private long featureId;

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public int getIndexPointCode() {
		return IndexPointCode;
	}

	public void setIndexPointCode(int indexPointCode) {
		IndexPointCode = indexPointCode;
	}

	public String getRefPointName() {
		return RefPointName;
	}

	public void setRefPointName(String refPointName) {
		RefPointName = refPointName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
