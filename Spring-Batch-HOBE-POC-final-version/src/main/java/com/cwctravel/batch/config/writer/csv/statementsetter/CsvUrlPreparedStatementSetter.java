package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.ImageAndUrlCombination;

/**
 * CsvUrlPreparedStatementSetter map fields in ImageAndUrlCombination to columns of hotel_image_url table
 * in the destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.ImageAndUrlCombination} for class ImageAndUrlCombination
 * @author chris.nie
 *
 */
public class CsvUrlPreparedStatementSetter implements ItemPreparedStatementSetter<ImageAndUrlCombination> {

	/**
	 * Map fields in ImageAndUrlCombination to columns of hotel_image_url table
	 */
	public void setValues(ImageAndUrlCombination item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getImageUrl().getUrl());
		ps.setLong(2, item.getImageUrl().getImageId());
	}

}
