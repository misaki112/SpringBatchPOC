package com.cwctravel.batch.model.csv;

/**
 * DCHotelRateMapping stores information about rate mapping relationship for hotels between vendors
 * and Costco Travel from cdv file.
 * <p>
 * DCHotelRateMapping information includes internal description of a hotel rate,
 * external description of a hotel rate, vendorId for the vendor who provides this hotel rate
 * and a unique id for the rate.
 * @author chris.nie
 *
 */
public class DCHotelRateMapping {

	private String internalRateDisplay;

	private String externalRateDisplay;

	private String vendorId;

	private long id;

	private DCHotelCategoryMapping categoryMapping;

	public DCHotelRateMapping() {

	}

	/**
	 * Construct a new DCHotelRateMapping
	 * @param internalRateDisplay String internal description of a hotel rate
	 * @param externalRateDisplay String external description of a hotel rate
	 * @param vendorId String vendorId for the vendor who provides this hotel rate
	 * @param id long unique id for the rate
	 * @param categoryMapping DCHotelCategoryMapping provides category mapping relationship 
	 * of the hotel the rate is from between vendor and Costco Travel
	 */
	public DCHotelRateMapping(String internalRateDisplay, String externalRateDisplay, String vendorId, long id,
			DCHotelCategoryMapping categoryMapping) {
		super();
		this.internalRateDisplay = internalRateDisplay;
		this.externalRateDisplay = externalRateDisplay;
		this.vendorId = vendorId;
		this.id = id;
		this.categoryMapping = categoryMapping;
	}

	public String getInternalRateDisplay() {
		return internalRateDisplay;
	}

	public void setInternalRateDisplay(String internalRateDisplay) {
		this.internalRateDisplay = internalRateDisplay;
	}

	public String getExternalRateDisplay() {
		return externalRateDisplay;
	}

	public void setExternalRateDisplay(String externalRateDisplay) {
		this.externalRateDisplay = externalRateDisplay;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DCHotelCategoryMapping getCategoryMapping() {
		return categoryMapping;
	}

	public void setCategoryMapping(DCHotelCategoryMapping categoryMapping) {
		this.categoryMapping = categoryMapping;
	}

}
