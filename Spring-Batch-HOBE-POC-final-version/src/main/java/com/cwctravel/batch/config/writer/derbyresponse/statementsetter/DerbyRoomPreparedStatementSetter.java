package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.derby.GuestRoom;

/**
 * DerbyRoomPreparedStatementSetter maps room related fields in GuestRoom into columns in
 * hotel_room table.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.GuestRoom} for class GuestRoom.
 * @author chris.nie
 *
 */
public class DerbyRoomPreparedStatementSetter implements ItemPreparedStatementSetter<GuestRoom> {

	public void setValues(GuestRoom item, PreparedStatement ps) throws SQLException {
		ps.setString (1, item.getHotelId());
		ps.setString (2, item.getTypeRoom().getName());
		ps.setString (3, item.getDescriptiveText().getText());
		ps.setLong (4, item.getRoomId());
		ps.setString (5, item.getHotelId());
	}

}
