package com.cwctravel.batch.model.csv;

/**
 * DCCombination stores information about dc mapping relationship from csv file.
 * <p>
 * DCCombination is an intermediate class that helps writing to dc_id_mapping table, 
 * dc_rate_mapping table and dc_category_mapping table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.DCHotelIdMapping} for class DCHotelIdMapping about 
 * id mapping information 
 * <p>
 * {@link com.cwctravel.batch.model.csv.DCHotelCategoryMapping} for class DCHotelCategoryMapping about 
 * category mapping information
 * <p>
 * and {@link com.cwctravel.batch.model.csv.DCHotelRateMapping} for class DCHotelRateMapping about 
 * rate mapping information
 * @author chris.nie
 *
 */
public class DCCombination {

	private DCHotelIdMapping idMapping;

	private DCHotelCategoryMapping categoryMapping;

	private DCHotelRateMapping rateMapping;

	public DCCombination() {

	}

	/**
	 * Construct a new DCCombination
	 * @param idMapping DCHotelIdMapping information about id mapping relationship for a hotel between 
	 * vendors and Costco Travel
	 * @param categoryMapping DCHotelCategoryMapping information about category mapping relationship 
	 * for a hotel between vendors and Costco Travel
	 * @param rateMapping DCHotelRateMapping information about rate mapping relationship for a hotel between 
	 * vendors and Costco Travel
	 */
	public DCCombination(DCHotelIdMapping idMapping, DCHotelCategoryMapping categoryMapping,
			DCHotelRateMapping rateMapping) {
		this.idMapping = idMapping;
		this.categoryMapping = categoryMapping;
		this.rateMapping = rateMapping;
	}

	public DCHotelIdMapping getIdMapping() {
		return idMapping;
	}

	public void setIdMapping(DCHotelIdMapping idMapping) {
		this.idMapping = idMapping;
	}

	public DCHotelCategoryMapping getCategoryMapping() {
		return categoryMapping;
	}

	public void setCategoryMapping(DCHotelCategoryMapping categoryMapping) {
		this.categoryMapping = categoryMapping;
	}

	public DCHotelRateMapping getRateMapping() {
		return rateMapping;
	}

	public void setRateMapping(DCHotelRateMapping rateMapping) {
		this.rateMapping = rateMapping;
	}

}
