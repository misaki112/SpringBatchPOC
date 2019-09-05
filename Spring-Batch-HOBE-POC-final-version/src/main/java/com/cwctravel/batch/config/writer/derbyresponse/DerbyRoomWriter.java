package com.cwctravel.batch.config.writer.derbyresponse;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.cwctravel.batch.model.derby.Amenity;
import com.cwctravel.batch.model.derby.GuestRoom;

/**
 * DerbyRoomWriter is a JdbcBatchItemWriter which write to hotel_room table and trigger
 * writer for hotel_room_feature_type table.
 * <p>
 * DerbyRoomWriter takes GuestRoom as input and write to hotel_room table using JdbcTemplate.
 * @author chris.nie
 *
 */
public class DerbyRoomWriter extends JdbcBatchItemWriter<GuestRoom>{
	private ItemWriter<Amenity> featureWriter;

	/**
	 * Construct a new DerbyRoomWriter
	 * @param featureWriter ItemWriter<Amenity> writer writing to hotel_room_feature_type table
	 * @param dataSource DataSource Connection to the destination MySQl database.
	 */
	public DerbyRoomWriter(ItemWriter<Amenity> featureWriter, DataSource dataSource) {
		super();
		this.featureWriter = featureWriter;
		this.setDataSource(dataSource);
	}

	/**
	 * Write items to hotel_room table and trigger writer for hotel_room_feature_type table
	 */
	@Override
	public void write(List<? extends GuestRoom> items) throws Exception {
		for (GuestRoom item: items) {
			List<GuestRoom> list = new ArrayList<>();
			list.add(item);
			super.write(list);
			if (item.getAmenities() != null) {
				List<Amenity> features = item.getAmenities().getAmenity();
				featureWriter.write(features);
			}
		}

	}
}
