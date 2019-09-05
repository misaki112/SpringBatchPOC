package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

/**
 * DerbyRatePreparedStatementSetter maps rate related fields in 
 * HotelDescriptiveContent to columns of ipm_hotel_rate table and add "Derby" as source of data.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent.
 * @author chris.nie
 *
 */
public class DerbyRatePreparedStatementSetter implements ItemPreparedStatementSetter<HotelDescriptiveContent> {

	public void setValues(HotelDescriptiveContent item, PreparedStatement ps) throws SQLException {
		String hotelId = item.getHotelId();
		ps.setString (1, hotelId);
		ps.setString (2, item.getCombinedId());
		ps.setString (3, "Contracted Rate");
		ps.setString (4, "Derby");

	}

}
