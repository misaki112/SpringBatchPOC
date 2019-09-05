package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

/**
 * DerbyIdMappingPreparedStatementSetter maps fields in HotelDescriptiveContent to 
 * columns of dc_id_mapping table and add "Derby" as source of data.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent.
 * @author chris.nie
 *
 */
public class DerbyIdMappingPreparedStatementSetter implements ItemPreparedStatementSetter<HotelDescriptiveContent> {

	public void setValues(HotelDescriptiveContent item, PreparedStatement ps) throws SQLException {
		ps.setString (1, item.getHotelId());
		ps.setString (2, item.getHotelCode());
		ps.setString (3, "Derby");
	}

}
