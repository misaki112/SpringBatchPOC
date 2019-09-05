package com.cwctravel.batch.model.derby;

/**
 * Service stores information about a Service provided by a hotel from Derby response.
 * <p>
 * Service information includes code, featureId, location of the Service and the hotelId of the Hotel
 * that provide this Service.
 * @author chris.nie
 *
 */
public class Service {
	private String Code;

	private String CodeDetail;

	private String Prefix;

	private String ProximityCode;

	private String BusinessServiceCode;

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

	public String getBusinessServiceCode() {
		return BusinessServiceCode;
	}

	public void setBusinessServiceCode(String businessServiceCode) {
		BusinessServiceCode = businessServiceCode;
	}

	public String getProximityCode() {
		return ProximityCode;
	}

	public void setProximityCode(String proximityCode) {
		ProximityCode = proximityCode;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getCodeDetail() {
		return CodeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		CodeDetail = codeDetail;
	}

	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}
}
