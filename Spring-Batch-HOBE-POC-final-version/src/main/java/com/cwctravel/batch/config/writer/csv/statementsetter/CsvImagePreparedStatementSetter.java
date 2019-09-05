package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.ImageAndUrlCombination;

/**
 * CsvImagePreparedStatementSetter map fields in ImageAndUrlCombination to columns of hotel_image table and
 * add "csvFile" as source of the data
 * 
 * @author chris.nie
 */
public class CsvImagePreparedStatementSetter implements ItemPreparedStatementSetter<ImageAndUrlCombination> {

	public void setValues(ImageAndUrlCombination item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getImage().getHotel().getHotelId());
		ps.setString(2, item.getImage().getTitle());
		ps.setLong(3, item.getImage().getId());
		ps.setString(4, "csvFile");
		// ps.setString(5, "Derby and csvFile");
	}

}
