package com.cwctravel.batch.model.derby;

/**
 * HotelDescriptiveContent stores all the information about a hotel from Derby response.
 * @author chris.nie
 *
 */
public class HotelDescriptiveContent {
	HotelInfo HotelInfoObject;
	FacilityInfo FacilityInfoObject;
	Policies PoliciesObject;
	AreaInfo AreaInfoObject;
	AffiliationInfo AffiliationInfoObject;
	MultimediaDescriptions MultimediaDescriptionsObject;
	ContactInfos ContactInfosObject;
	private String BrandCode;
	private String BrandName;
	private String ChainCode;
	private String CurrencyCode;
	private String HotelCityCode;
	private String HotelCode;
	private String HotelCodeContext;
	private String HotelName;
	private String prefix;
	private String hotelId;
	private String combinedId;

	public String getCombinedId() {
		return combinedId;
	}

	public void setCombinedId(String combinedId) {
		this.combinedId = combinedId;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public HotelInfo getHotelInfo() {
		return HotelInfoObject;
	}

	public FacilityInfo getFacilityInfo() {
		return FacilityInfoObject;
	}

	public Policies getPolicies() {
		return PoliciesObject;
	}

	public AreaInfo getAreaInfo() {
		return AreaInfoObject;
	}

	public AffiliationInfo getAffiliationInfo() {
		return AffiliationInfoObject;
	}

	public MultimediaDescriptions getMultimediaDescriptions() {
		return MultimediaDescriptionsObject;
	}

	public ContactInfos getContactInfos() {
		return ContactInfosObject;
	}

	public String getBrandCode() {
		return BrandCode;
	}

	public String getBrandName() {
		return BrandName;
	}

	public String getChainCode() {
		return ChainCode;
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public String getHotelCityCode() {
		return HotelCityCode;
	}

	public String getHotelCode() {
		return HotelCode;
	}

	public String getHotelCodeContext() {
		return HotelCodeContext;
	}

	public String getHotelName() {
		return HotelName;
	}

	public String getPrefix() {
		return prefix;
	}

	// Setter Methods 

	public void setHotelInfo( HotelInfo HotelInfoObject ) {
		this.HotelInfoObject = HotelInfoObject;
	}

	public void setFacilityInfo( FacilityInfo FacilityInfoObject ) {
		this.FacilityInfoObject = FacilityInfoObject;
	}

	public void setPolicies( Policies PoliciesObject ) {
		this.PoliciesObject = PoliciesObject;
	}

	public void setAreaInfo( AreaInfo AreaInfoObject ) {
		this.AreaInfoObject = AreaInfoObject;
	}

	public void setAffiliationInfo( AffiliationInfo AffiliationInfoObject ) {
		this.AffiliationInfoObject = AffiliationInfoObject;
	}

	public void setMultimediaDescriptions( MultimediaDescriptions MultimediaDescriptionsObject ) {
		this.MultimediaDescriptionsObject = MultimediaDescriptionsObject;
	}

	public void setContactInfos( ContactInfos ContactInfosObject ) {
		this.ContactInfosObject = ContactInfosObject;
	}

	public void setBrandCode( String BrandCode ) {
		this.BrandCode = BrandCode;
	}

	public void setBrandName( String BrandName ) {
		this.BrandName = BrandName;
	}

	public void setChainCode( String ChainCode ) {
		this.ChainCode = ChainCode;
	}

	public void setCurrencyCode( String CurrencyCode ) {
		this.CurrencyCode = CurrencyCode;
	}

	public void setHotelCityCode( String HotelCityCode ) {
		this.HotelCityCode = HotelCityCode;
	}

	public void setHotelCode( String HotelCode ) {
		this.HotelCode = HotelCode;
	}

	public void setHotelCodeContext( String HotelCodeContext ) {
		this.HotelCodeContext = HotelCodeContext;
	}

	public void setHotelName( String HotelName ) {
		this.HotelName = HotelName;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
