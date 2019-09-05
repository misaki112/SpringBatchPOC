package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCHotelIdMapping;

/**
 * IdMappingItemPreparedStatementSetter map fields from DCCombination to columns of dc_hotel_id_mapping table
 * in destination MySQL database and add "csv File" as source of the data.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.DCCombination} for class DCCombination.
 * @author chris.nie
 *
 */
public class IdMappingItemPreparedStatementSetter implements ItemPreparedStatementSetter<DCCombination> {

	public void setValues(DCCombination item, PreparedStatement ps) throws SQLException {
		DCHotelIdMapping idMapping = item.getIdMapping();
		ps.setString(2, idMapping.getVendorHotelId());
		ps.setString(1, idMapping.getHotel().getHotelId());
		ps.setString(3, "csvFile");
		ps.setString(4, idMapping.getHotel().getHotelId());

	}

}
