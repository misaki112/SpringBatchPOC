package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCHotelRateMapping;

/**
 * RateMappingItemPreparedStatementSetter map fields of DCCombination to columns of 
 * dc_rate_mapping table in destination MySQL database.
 * @author chris.nie
 *
 */
public class RateMappingItemPreparedStatementSetter implements ItemPreparedStatementSetter<DCCombination> {

	/**
	 * Map fields of DCCombination to columns of dc_rate_mapping table
	 */
	public void setValues(DCCombination item, PreparedStatement ps) throws SQLException {
		DCHotelRateMapping rateMapping = item.getRateMapping();
		ps.setString(1, rateMapping.getCategoryMapping().getIdMapping().getVendorHotelId());
		ps.setString(2, rateMapping.getInternalRateDisplay());
		ps.setString(3, rateMapping.getExternalRateDisplay());
		ps.setString(4, rateMapping.getVendorId());
		ps.setLong(5, rateMapping.getId());
		ps.setLong(6, rateMapping.getId());

	}

}
