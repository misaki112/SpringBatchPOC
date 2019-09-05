package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCHotelCategoryMapping;

/**
 * CategoryMappingItemPreparedStatementSetter map fields in DCCombination to columns of dc_category_mapping table  in destination MySQL database
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.DCCombination} for class DCCombination
 * @author chris.nie
 *
 */
public class CategoryMappingItemPreparedStatementSetter implements ItemPreparedStatementSetter<DCCombination> {

	/**
	 * Map fields in DCCombination to columns of dc_category_mapping table in destination MySQL database
	 */
	public void setValues(DCCombination item, PreparedStatement ps) throws SQLException {
		DCHotelCategoryMapping categpryMapping = item.getCategoryMapping();
		ps.setString(1, categpryMapping.getIdMapping().getVendorHotelId());
		ps.setString(2, categpryMapping.getVendorCategory());
		ps.setString(3, categpryMapping.getCwcCategory());
		ps.setString(4, categpryMapping.getVendorId());
		ps.setString(5, categpryMapping.getIdMapping().getVendorHotelId());

	}

}
