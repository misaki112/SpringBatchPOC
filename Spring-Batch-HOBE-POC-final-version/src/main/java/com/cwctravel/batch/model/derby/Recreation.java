package com.cwctravel.batch.model.derby;

/**
 * Recreation stores information about recreation of a hotel from Derby response.
 * <p>
 * Recreation information includes code and featureId of the Recreation for featureType mapping,
 * name and location of the recreation, the hotelId of the hotel the Recreation belongs to and
 * whether the hotel has that Recreation or not.
 * @author chris.nie
 *
 */
public class Recreation {

	private String Code;

	private boolean Included;

	private String ProximityCode;

	private String CodeDetail;

	private String prefix;

	private String Name;

	private String hotelId;

	private long featureId;

	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public boolean isIncluded() {
		return Included;
	}

	public void setIncluded(boolean included) {
		Included = included;
	}

	public String getProximityCode() {
		return ProximityCode;
	}

	public void setProximityCode(String proximityCode) {
		ProximityCode = proximityCode;
	}

	public String getCodeDetail() {
		return CodeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		CodeDetail = codeDetail;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
