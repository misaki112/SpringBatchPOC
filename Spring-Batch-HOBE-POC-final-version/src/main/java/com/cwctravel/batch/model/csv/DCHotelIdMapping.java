package com.cwctravel.batch.model.csv;

import java.util.HashSet;
import java.util.Set;

/**
 * DCHotelIdMapping stores information about id mapping and category mapping relationship 
 * for a hotel from csv file between vendors and Costco Travel
 * <p>
 * DCHotelIdMapping information includes a hotel, vendorHotelId for that hotel and a set of category mapping
 * relationship for the hotel.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.DCHotelCategoryMapping} for alss DCHotelCategoryMapping
 * about information of category mapping.
 * @author chris.nie
 *
 */
public class DCHotelIdMapping {

	private String vendorHotelId;

	private Hotel hotel;

	private Set<DCHotelCategoryMapping> categoryMappingSet;

	public DCHotelIdMapping() {

	}

	/**
	 * Construct a new DCHotelIdMapping 
	 * @param vendorHotelId String hotelId for a hotel provided by vendor 
	 * @param hotel Hotel a hotel
	 */
	public DCHotelIdMapping(String vendorHotelId, Hotel hotel) {
		this.vendorHotelId = vendorHotelId;
		this.hotel = hotel;
	}

	public String getVendorHotelId() {
		return vendorHotelId;
	}

	public void setVendorHotelId(String vendorHotelId) {
		this.vendorHotelId = vendorHotelId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<DCHotelCategoryMapping> getCategoryMappingSet() {
		if (categoryMappingSet == null) {
			categoryMappingSet = new HashSet<>();
		}
		return categoryMappingSet;
	}

	public void setCategoryMappingSet(Set<DCHotelCategoryMapping> categoryMappingSet) {
		this.categoryMappingSet = categoryMappingSet;
	}



}
