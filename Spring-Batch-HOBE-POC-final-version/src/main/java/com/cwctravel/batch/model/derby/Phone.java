package com.cwctravel.batch.model.derby;

/**
 * Phone stores information about how to call a hotel from Derby response.
 * <p>
 * Phone information includes AreaCode, CountryCode,PhoneTechType and PhoneNumber of the hotel.
 * @author chris.nie
 *
 */
public class Phone {
	private String AreaCityCode;

	private String CountryAccessCode;

	private String PhoneTechType;

	private String PhoneNumber;

	private String prefix;

	public String getAreaCityCode() {
		return AreaCityCode;
	}

	public void setAreaCityCode(String areaCityCode) {
		AreaCityCode = areaCityCode;
	}

	public String getCountryAccessCode() {
		return CountryAccessCode;
	}

	public void setCountryAccessCode(String countryAccessCode) {
		CountryAccessCode = countryAccessCode;
	}

	public String getPhoneTechType() {
		return PhoneTechType;
	}

	public void setPhoneTechType(String phoneTechType) {
		PhoneTechType = phoneTechType;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
