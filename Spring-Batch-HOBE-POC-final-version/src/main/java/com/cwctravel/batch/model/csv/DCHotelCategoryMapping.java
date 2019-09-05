package com.cwctravel.batch.model.csv;

import java.util.HashSet;
import java.util.Set;

/**
 * DCHotelCategoryMapping stores information about rate mapping relationship 
 * for a hotel from csv file between vendors and Costco Travel
 * <p>
 * DCHotelCategoryMapping information includes category of the hotel provided by vendor,
 * category of the hotel by Costco Travel, vendorId of the vendor who provides the category,
 * mapping relationship for the id of the hotel the category belongs to
 * and a set of rate mapping relationship for that category.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.DCHotelRateMapping} for class DCHotelRateMapping
 * about rate mapping information.
 * @author chris.nie
 *
 */
public class DCHotelCategoryMapping {

	private String vendorCategory;

	private String cwcCategory;

	private String vendorId;

	private DCHotelIdMapping idMapping;

	private Set<DCHotelRateMapping> rateMappingSet;

	public DCHotelCategoryMapping() {

	}

	/**
	 * Construct a new DCHotelCategoryMapping
	 * @param vendorCategory String category of the hotel provided by vendor
	 * @param cwcCategory String category of the hotel by Costco Travel
	 * @param vendorId String vendorId of the vendor who provides the category
	 * @param idMapping DCHotelIdMapping mapping relationship for the id of the hotel the category belongs
	 */
	public DCHotelCategoryMapping(String vendorCategory, String cwcCategory, String vendorId,
			DCHotelIdMapping idMapping) {
		this.vendorCategory = vendorCategory;
		this.cwcCategory = cwcCategory;
		this.vendorId = vendorId;
		this.idMapping = idMapping;
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

	public DCHotelIdMapping getIdMapping() {
		return idMapping;
	}

	public void setIdMapping(DCHotelIdMapping idMapping) {
		this.idMapping = idMapping;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public Set<DCHotelRateMapping> getRateMappingSet() {
		if (rateMappingSet == null) {
			rateMappingSet = new HashSet<>();
		}
		return rateMappingSet;
	}

	public void setRateMappingSet(Set<DCHotelRateMapping> rateMappingSet) {
		this.rateMappingSet = rateMappingSet;
	}

}
