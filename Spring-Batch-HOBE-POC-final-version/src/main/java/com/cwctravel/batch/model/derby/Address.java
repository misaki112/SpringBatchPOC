package com.cwctravel.batch.model.derby;

/**
 * Address is used to store geographical information of a hotel from Derby response.<p>
 * Address information includes country, state or province, city, postal code and AddressLine.
 * @author chris.nie
 * 
 */
public class Address {

	private AddressLine[] AddressLine;

	private CityName CityName;

	private PostalCode PostalCode;

	private StateProv StateProv;

	private CountryName CountryName;

	private County County;

	private String prefix;

	public County getCounty() {
		return County;
	}

	public void setCounty(County county) {
		County = county;
	}

	public AddressLine[] getAddressLine() {
		return AddressLine;
	}

	public void setAddressLine(AddressLine[] addressLine) {
		AddressLine = addressLine;
	}

	public CityName getCityName() {
		return CityName;
	}

	public void setCityName(CityName cityName) {
		CityName = cityName;
	}

	public PostalCode getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		PostalCode = postalCode;
	}

	public StateProv getStateProv() {
		return StateProv;
	}

	public void setStateProv(StateProv stateProv) {
		StateProv = stateProv;
	}

	public CountryName getCountryName() {
		return CountryName;
	}

	public void setCountryName(CountryName countryName) {
		CountryName = countryName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
