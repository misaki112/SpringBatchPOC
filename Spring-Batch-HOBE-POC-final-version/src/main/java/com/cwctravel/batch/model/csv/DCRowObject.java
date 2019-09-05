package com.cwctravel.batch.model.csv;

/**
 * DCRowObject stores information representing a row in DcMap.csv
 * about dc mapping information for hotel from csv files.
 * <p>
 * DCRowObject information includes vendorHotelId, vendorCategory, Costco's hotelId, Costco's category,
 * internalRateDisplay, externalRateDisplay for the hotel.
 * @author chris.nie
 *
 */
public class DCRowObject {
	private String vendorHotelId;

	private String vendorCategory;

	private String cwcCategory;

	private String cwcHotelId;

	private String vendorId;

	private String internalRateDisplay;

	private String externalRateDisplay;

	private String id;

	public DCRowObject() {

	}

	/**
	 * Construct a new DCRowObject
	 * @param vendorHotelId String vendor's Id for the hotel.
	 * @param vendorCategory String cendor's category for the hotel
	 * @param cwcCategory String Costco Travel's category for the hotel
	 * @param cwcHotelId String Costco Travel's Id for the hotel
	 * @param vendorId String Id of the vendor the hotel is from 
	 * @param internalRateDisplay String internal description of rate of the hotel
	 * @param externalRateDisplay String external description of rate of the hotel 
	 * @param id String Id for the rate of the hotel
	 */
	public DCRowObject(String vendorHotelId, String vendorCategory, String cwcCategory, String cwcHotelId,
			String vendorId, String internalRateDisplay, String externalRateDisplay, String id) {
		super();
		this.vendorHotelId = vendorHotelId;
		this.vendorCategory = vendorCategory;
		this.cwcCategory = cwcCategory;
		this.cwcHotelId = cwcHotelId;
		this.vendorId = vendorId;
		this.internalRateDisplay = internalRateDisplay;
		this.externalRateDisplay = externalRateDisplay;
		this.id = id;
	}

	public String getVendorHotelId() {
		return vendorHotelId;
	}

	public void setVendorHotelId(String vendorHotelId) {
		this.vendorHotelId = vendorHotelId;
	}

	public String getVendorCategory() {
		return vendorCategory;
	}

	public void setVendorCategory(String vendorCategory) {
		this.vendorCategory = vendorCategory;
	}

	public String getCwcCategory() {
		return cwcCategory;
	}

	public void setCwcCategory(String cwcCategory) {
		this.cwcCategory = cwcCategory;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCwcHotelId() {
		return cwcHotelId;
	}

	public void setCwcHotelId(String cwcHotelId) {
		this.cwcHotelId = cwcHotelId;
	}

}
