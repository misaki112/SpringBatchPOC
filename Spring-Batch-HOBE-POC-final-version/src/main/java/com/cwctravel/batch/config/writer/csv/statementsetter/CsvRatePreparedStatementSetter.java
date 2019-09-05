package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.HotelAndRateCombination;

/**
 * CsvRatePreparedStatementSetter maps fields in HotelAndRateCombination to columns of ipm_hotel_rate table 
 * in the destination MySQL database and add "csvFile" as source of the data
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.HotelAndRateCombination} for class HotelAndRateCombination
 * @author chris.nie
 *
 */
public class CsvRatePreparedStatementSetter implements ItemPreparedStatementSetter<HotelAndRateCombination> {

	public void setValues(HotelAndRateCombination comb, PreparedStatement ps) throws SQLException {
		String hotelId = comb.getRate().getHotel().getHotelId();
		ps.setString(1, hotelId);
		ps.setString(2, comb.getRate().getSeason());
		ps.setString(3, comb.getRate().getDescription());
		ps.setString(4, "csvFile");
		ps.setString(5, hotelId);

	}

}
