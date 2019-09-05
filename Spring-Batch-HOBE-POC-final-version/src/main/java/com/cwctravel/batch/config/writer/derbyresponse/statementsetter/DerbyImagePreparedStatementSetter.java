package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.ImageItem;

/**
 * DerbyImagePreparedStatementSetter maps fields in ImageItem to columns of hotel_image table 
 * and add "Derby" as source of data.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.ImageItem} for class ImageItem.
 * @author chris.nie
 *
 */
public class DerbyImagePreparedStatementSetter implements ItemPreparedStatementSetter<ImageItem> {

	public void setValues(ImageItem item, PreparedStatement ps) throws SQLException {

		ps.setString (1, item.getHotelId());
		ps.setString (2, item.getDescription().getCaption());
		ps.setLong (3, item.getImageId());
		ps.setString (4, "Derby");

	}

}
