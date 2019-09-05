package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.HotelAndRateCombination;

/**
 * CsvHotelPreparedStatementSetter maps fields in HotelAndRateCombination to columns of ipm_hotel table in the destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.HotelAndRateCombination} for class HotelAndRateCombination
 * 
 * @author chris.nie
 */
public class CsvHotelPreparedStatementSetter implements ItemPreparedStatementSetter<HotelAndRateCombination> {

	/**
	 * Maps fields in HotelAndRateCombination to columns of ipm_hotel table
	 */
	public void setValues(HotelAndRateCombination comb, PreparedStatement ps) throws SQLException {
		ps.setString(1, comb.getHotel().getHotelId());
		ps.setString(2, comb.getHotel().getHotelName());
		ps.setString(3, comb.getHotel().getExtendedDescription());
		ps.setString(4, comb.getHotel().getHotelName());
		ps.setString(5, comb.getHotel().getExtendedDescription());
	}
}
