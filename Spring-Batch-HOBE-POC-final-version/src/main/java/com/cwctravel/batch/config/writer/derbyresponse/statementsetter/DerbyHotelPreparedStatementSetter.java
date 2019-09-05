package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

/**
 * DerbyHotelPreparedStatementSetter maps hotel information related fields in HotelDescriptiveContent to columns of ipm_hotel table.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent.
 * 
 * @author chris.nie
 */
public class DerbyHotelPreparedStatementSetter implements ItemPreparedStatementSetter<HotelDescriptiveContent> {

	public void setValues(HotelDescriptiveContent item, PreparedStatement ps) throws SQLException {
		String hotelName = item.getHotelName();
		String hotelId = item.getHotelId();
		String description = null;

		if(item.getHotelInfo() != null) {
			description = item.getHotelInfo().getDescriptions().getDescriptiveText().getText();
		}

		ps.setString(1, hotelId);
		ps.setString(2, hotelName);
		ps.setString(3, description);
		ps.setString(4, hotelId);

	}

}
