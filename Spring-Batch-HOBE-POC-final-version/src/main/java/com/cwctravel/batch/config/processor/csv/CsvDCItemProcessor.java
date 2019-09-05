package com.cwctravel.batch.config.processor.csv;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCHotelCategoryMapping;
import com.cwctravel.batch.model.csv.DCHotelIdMapping;
import com.cwctravel.batch.model.csv.DCHotelRateMapping;
import com.cwctravel.batch.model.csv.DCRowObject;
import com.cwctravel.batch.model.csv.Hotel;

/**
 * CsvDCItemProcessor process data from a DCRowObject to a DCCombination.
 * <p>
 * CsvDCItemProcessor extract data from fields in DCRowObject and use them to build a new DCCombination.
 * @author chris.nie
 *
 */
public class CsvDCItemProcessor implements ItemProcessor<DCRowObject, DCCombination> {

	private Map<String, Hotel> hotelIdMap;

	/**
	 * Constructor generating a CsvDCItemProcessor with a map 
	 * @param hotelIdMap Map a map mapping a Hotel to its hotelId
	 */
	public CsvDCItemProcessor(Map<String, Hotel> hotelIdMap) {
		super();
		this.hotelIdMap = hotelIdMap;
	}

	/**
	 * Extract data from fields in DCRowObject and use them to build a new DCCombination
	 * @param  row DCRowObject representing a row of dc_mapping data in csv file to be processed
	 * @return DCCombination Intermediate class with information of DCHotelIdMapping, DCHotelCategoryMapping
	 * and DCHotelRateMapping that helps writing to dc_hotel_id_mapping table, dc_hotel_category_mapping table
	 * and dc_hotel_rate_mapping table in destination MySQL database.
	 */
	public DCCombination process(DCRowObject row) throws Exception {

		String hotelId = row.getCwcHotelId();

		String vendorHotelId = row.getVendorHotelId();

		String vendorCategory = row.getVendorCategory();

		String cwcCategory = row.getCwcCategory();

		String vendorId = row.getVendorId();

		String internalRateDisplay = row.getInternalRateDisplay();

		String externalRateDisplay = row.getExternalRateDisplay();

		Long id = (long)Integer.parseInt(row.getId());

		Hotel hotel = hotelIdMap.get(hotelId);

		if(hotel == null) {
			hotel = new Hotel(hotelId);
			hotelIdMap.put(hotelId, hotel);
		}

		DCHotelIdMapping idMapping = new DCHotelIdMapping(vendorHotelId, hotel);

		hotel.getIdMappingSet().add(idMapping);

		DCHotelCategoryMapping categoryMapping = new DCHotelCategoryMapping(vendorCategory, cwcCategory, vendorId, idMapping);

		idMapping.getCategoryMappingSet().add(categoryMapping);

		DCHotelRateMapping rateMapping = new DCHotelRateMapping(internalRateDisplay, externalRateDisplay, vendorId, id, categoryMapping);

		categoryMapping.getRateMappingSet().add(rateMapping);

		DCCombination result = new DCCombination(idMapping, categoryMapping, rateMapping);

		return result;
	}

}
