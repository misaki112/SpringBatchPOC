package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.ImageItem;

/**
 * DerbyUrlPreparedStatementSetter maps url related fields in ImageItem into columns of hotel_image_url table
 * <p>
 * Please see {@link com.multiSourceDataReading.ImageItem> for class ImageItem
 * @author chris.nie
 *
 */
public class DerbyUrlPreparedStatementSetter implements ItemPreparedStatementSetter<ImageItem> {

	/**
	 * Maps url related fields in ImageItem into columns of hotel_image_url table
	 */
	public void setValues(ImageItem item, PreparedStatement ps) throws SQLException {
		ps.setString (1, item.getImageFormat().getURL().getText());
		ps.setLong (2, item.getImageId());
	}
}
