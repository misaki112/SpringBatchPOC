package com.cwctravel.batch.config.util;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.config.processor.derby.DerbyHotelItemProcessor;
import com.cwctravel.batch.config.writer.derbyresponse.DerbyHotelItemWriter;
import com.cwctravel.batch.config.writer.derbyresponse.DerbyImageItemWriter;
import com.cwctravel.batch.config.writer.derbyresponse.DerbyRoomWriter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyFeatureMappingPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyHotelPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyIdMappingPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyImagePreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyRatePreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyRecreationPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyRefPointPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyRoomCategoryPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyRoomPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyUrlPreparedStatementSetter;
import com.cwctravel.batch.model.derby.Amenity;
import com.cwctravel.batch.model.derby.GuestRoom;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;
import com.cwctravel.batch.model.derby.ImageItem;
import com.cwctravel.batch.model.derby.Recreation;
import com.cwctravel.batch.model.derby.RefPoint;
import com.cwctravel.batch.model.derby.Service;

/**
 * DerbyWriterUtil is the util class configuring writers writing processed data from Derby response and processor processing data from Derby response.
 * <p>
 * Purpose of the util class is to shorten the length of BatchConfiguration class for readability. BatchConfiguration class depends on configurations
 * implemented in DerbyWriterUtil.
 * <p>
 * Please see {@link com.cwctravel.batch.config.BatchConfiguration} for class BatchConfiguration
 * 
 * @author chris.nie
 */
@Configuration
public class DerbyWriterUtil {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;

	/**
	 * @param dataSource
	 *                   DataSource the JdbcTemplate is connecting to
	 * @return JdbcTemplate template connecting to DataSource for derby hotel processor
	 */
	@Bean
	@Autowired
	public JdbcTemplate template(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * Return a processor which processes data from Derby response when requesting for hotel information.
	 * 
	 * @return DerbyHotelItemProcessor a processor which processes data from Derby response
	 */
	@Bean
	public DerbyHotelItemProcessor hotelItemProcessor(DataSource dataSource) {
		return new DerbyHotelItemProcessor(template(dataSource), namedTemplate);
	}

	/**
	 * derbyHotelWriter returns a writer writing to ipm_hotel table and trigger lower level writers for other tables.
	 * 
	 * @param dataSource
	 *                              DataSource Connection to destination MySQL database
	 * @param imageWriter
	 *                              DerbyImageItemWriter writer writing to hotel_image table and trigger writer for hotel_image_url table
	 * @param derbyIdMappingWriter
	 *                              ItemWriter<HotelDescriptiveContent> writer writing to dc_id_mapping table
	 * @param derbyRoomWriter
	 *                              DerbyRoomWriter writer writing to hotel_room table and trigger writer for hotel_room_feature_type table.
	 * @param derbyRateWriter
	 *                              ItemWriter<HotelDescriptiveContent> writer writing to ipm_hotel_rate table
	 * @param derbyServiceWriter
	 *                              ItemWriter<Service> writer writing to hotel_feature_type table
	 * @param derbyRefPointWriter
	 *                              ItemWriter<RefPoint> writer writing to hotel_point_of_interset_feature_type table
	 * @param derbyRecreationWriter
	 *                              ItemWriter<Recreation> writer writing to hotel_feature_type table
	 * @return DerbyHotelItemWriter a writer writing to ipm_hotel table and trigger lower level writers for other tables.
	 */
	@Bean
	public DerbyHotelItemWriter derbyHotelWriter(DataSource dataSource, DerbyImageItemWriter imageWriter, //
			ItemWriter<HotelDescriptiveContent> derbyIdMappingWriter, DerbyRoomWriter derbyRoomWriter, //
			ItemWriter<HotelDescriptiveContent> derbyRateWriter, ItemWriter<Service> derbyServiceWriter, //
			ItemWriter<RefPoint> derbyRefPointWriter, ItemWriter<Recreation> derbyRecreationWriter) {
		DerbyHotelItemWriter writer = new DerbyHotelItemWriter(imageWriter, derbyIdMappingWriter, derbyRoomWriter, derbyRateWriter, derbyRecreationWriter, derbyServiceWriter, derbyRefPointWriter);
		writer.setDataSource(dataSource);
		String query = "INSERT INTO ipm_hotel (hotel_id, hotel_name, extended_description)" + " VALUES (?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE hotel_id = ?";
		writer.setSql(query);
		ItemPreparedStatementSetter<HotelDescriptiveContent> valueSetter = new DerbyHotelPreparedStatementSetter();
		writer.setItemPreparedStatementSetter(valueSetter);
		return writer;
	}

	/**
	 * derbyImageWriter returns a writer writing to hotel_image table and trigger writer for hotel_image_url table
	 * 
	 * @param urlWriter
	 *                   ItemWriter<ImageItem> writer for hotel_image_url table
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return DerbyImageItemWriter a writer writing to hotel_image table and trigger writer for hotel_image_url table
	 */
	@Bean
	public DerbyImageItemWriter derbyImageWriter(ItemWriter<ImageItem> urlWriter, DataSource dataSource) {
		DerbyImageItemWriter writer = new DerbyImageItemWriter(urlWriter, dataSource);
		String query = "INSERT INTO hotel_image (hotel_id, title, id, source)" + "VALUES(?,?,?,?)";
		writer.setSql(query);
		ItemPreparedStatementSetter<ImageItem> valueSetter = new DerbyImagePreparedStatementSetter();
		writer.setItemPreparedStatementSetter(valueSetter);
		return writer;
	}

	/**
	 * derbyUrlWriter returns a writer for hotel_image_url table
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<ImageItem> a writer for hotel_image_url table
	 */
	@Bean
	public ItemWriter<ImageItem> derbyUrlWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_image_url (url, image_id)" + "VALUES(?, ?)";
		JdbcBatchItemWriter<ImageItem> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<ImageItem> valueSetter = new DerbyUrlPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyIdMappingWriter returns a writer writing to dc_hotel_id_mapping table
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<HotelDescriptiveContent> a writer writing to dc_hotel_id_mapping table
	 */
	@Bean
	public ItemWriter<HotelDescriptiveContent> derbyIdMappingWriter(DataSource dataSource) {
		String query = "INSERT INTO dc_hotel_id_mapping (hotel_id, vendorHotelID, source)" + "VALUES(?, ?, ?)";
		JdbcBatchItemWriter<HotelDescriptiveContent> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<HotelDescriptiveContent> valueSetter = new DerbyIdMappingPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyRoomWriter returns a writer writing to hotel_room table and trigger writer for hotel_room_feature_type table
	 * 
	 * @param derbyRoomCategoryWriter
	 *                                ItemWriter<Amenity> a writer for hotel_room_feature_type table
	 * @param dataSource
	 *                                DataSource Connection to destination MySQL database
	 * @return DerbyRoomWriter a writer writing to hotel_room table and trigger writer for hotel_room_feature_type table
	 */
	@Bean
	public DerbyRoomWriter derbyRoomWriter(ItemWriter<Amenity> derbyRoomCategoryWriter, DataSource dataSource) {
		DerbyRoomWriter writer = new DerbyRoomWriter(derbyRoomCategoryWriter, dataSource);
		String query = "INSERT INTO hotel_room (hotel_id, name, description, room_id)" + "VALUES(?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE hotel_id = ?";
		writer.setSql(query);
		ItemPreparedStatementSetter<GuestRoom> valueSetter = new DerbyRoomPreparedStatementSetter();
		writer.setItemPreparedStatementSetter(valueSetter);
		return writer;
	}

	/**
	 * derbyRateWriter returns a writer writing to ipm_hotel_rate table
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<HotelDescriptiveContent> a writer writing to ipm_hotel_rate table
	 */
	@Bean
	public ItemWriter<HotelDescriptiveContent> derbyRateWriter(DataSource dataSource) {
		String query = "INSERT INTO ipm_hotel_rate (hotel_id, season, description, source)" + " VALUES (?, ?, ?, ?)";
		JdbcBatchItemWriter<HotelDescriptiveContent> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<HotelDescriptiveContent> valueSetter = new DerbyRatePreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyRoomCategoryWriter returns a writer writing to HOTEL_ROOM_FEATURE_TYPE table
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<Amenity> a writer writing to HOTEL_ROOM_FEATURE_TYPE table
	 */
	@Bean
	public ItemWriter<Amenity> derbyRoomCategoryWriter(DataSource dataSource) {
		String query = "INSERT INTO HOTEL_ROOM_FEATURE_TYPE (feature_id, room_id, feature_type, source)" + "VALUES(?, ?, ?, ?)";
		JdbcBatchItemWriter<Amenity> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<Amenity> valueSetter = new DerbyRoomCategoryPreparedStatementSetter(namedTemplate);
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyServiceWriter returns a writer writing to hotel_feature_type table taking Service as input
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<Service> a writer writing to hotel_feature_type taking Service as input
	 */
	@Bean
	public ItemWriter<Service> derbyServiceWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_feature_type (hotel_id, feature_id, feature_type, location, source)" + "VALUES(?, ?, ?, ?, ?)";
		JdbcBatchItemWriter<Service> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<Service> valueSetter = new DerbyFeatureMappingPreparedStatementSetter(namedTemplate);
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyRefPointWriter returns a writer writing to hotel_point_of_interset_feature_type table
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<RefPoint> a writer writing to hotel_point_of_interset_feature_type table
	 */
	@Bean
	ItemWriter<RefPoint> derbyRefPointWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_point_of_interset_feature_type (hotel_id, feature_id, feature_type, source)" + "VALUES(?, ?, ?, ?)";
		JdbcBatchItemWriter<RefPoint> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<RefPoint> valueSetter = new DerbyRefPointPreparedStatementSetter(namedTemplate);
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyRecreationWriter returns a writer writing to hotel_feature_type table taking Recreation as input
	 * 
	 * @param dataSource
	 *                   DataSource Connection to destination MySQL database
	 * @return ItemWriter<Recreation> a writer writing to hotel_feature_type table taking Recreation as input
	 */
	@Bean
	ItemWriter<Recreation> derbyRecreationWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_feature_type (hotel_id, feature_id, feature_type, location, source)" + "VALUES(?, ?, ?, ?, ?)";
		JdbcBatchItemWriter<Recreation> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedTemplate);

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<Recreation> valueSetter = new DerbyRecreationPreparedStatementSetter(namedTemplate);
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

}
