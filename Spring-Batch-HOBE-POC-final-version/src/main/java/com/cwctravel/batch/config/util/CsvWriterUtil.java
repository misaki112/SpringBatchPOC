package com.cwctravel.batch.config.util;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.config.writer.csv.CsvHotelWriter;
import com.cwctravel.batch.config.writer.csv.CsvRoomWriter;
import com.cwctravel.batch.config.writer.csv.DcIdWriter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CategoryMappingItemPreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CategoryPreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvHotelFeaturePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvHotelPreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvImagePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvInterestFeaturePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvRatePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvRoomFeaturePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.CsvUrlPreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.IdMappingItemPreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.OTAFeatureTypePreparedStatementSetter;
import com.cwctravel.batch.config.writer.csv.statementsetter.RateMappingItemPreparedStatementSetter;
import com.cwctravel.batch.config.writer.derbyresponse.statementsetter.DerbyFeatureMapPreparedStatementSetter;
import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;
import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelFeature;
import com.cwctravel.batch.model.csv.ImageAndUrlCombination;
import com.cwctravel.batch.model.csv.InterestFeature;
import com.cwctravel.batch.model.csv.OTACombination;

/**
 * CsvWriterUtil is the util class configuring writers writing processed data from csv files.
 * <p>
 * Purpose of the util class is to shorten the length of BatchConfiguration class for readability. BatchConfiguration class depends on configurations
 * implemented in CsvWriterUtil.
 * <p>
 * Please see {@link com.cwctravel.batch.config.BatchConfiguration} for class BatchConfiguration
 * 
 * @author chris.nie
 */
@Configuration
public class CsvWriterUtil {

	/**
	 * @param dataSource
	 *                   DataSource the NamedParameterJdbcTemplate is connecting to
	 * @return NamedParameterJdbcTemplate template connected to DataSource to query for reference tables when writing to other tables
	 */
	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	// tag::HotelWriter[]

	/**
	 * hotelRateWriter returns a composite writer combining writers writing to ipm_hotel table and ipm_hotel_rate table to follow the restriction of
	 * Spring Batch's Item Writer of taking only one class type while writing to 2 MySQL tables in the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<HotelAndRateCombination> a composite writer combining writers writing to ipm_hotel table and ipm_hotel_rate table
	 */
	@StepScope
	@Bean
	public ItemWriter<HotelAndRateCombination> hotelRateWriter(DataSource dataSource) {

		CompositeItemWriter<HotelAndRateCombination> compositeItemWriter = new CompositeItemWriter<>();

		compositeItemWriter.setDelegates(Arrays.asList(hotelWriter(dataSource), rateWriter(dataSource)));

		return compositeItemWriter;
	}

	/**
	 * hotelWriter returns a writer writing to ipm_hotel table with given DataSource connection to the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<HotelAndRateCombination> a writer writing to ipm_hotel table
	 */
	@StepScope
	@Bean
	public ItemWriter<HotelAndRateCombination> hotelWriter(DataSource dataSource) {
		String query = "INSERT INTO ipm_hotel (hotel_id, hotel_name, extended_description)" + " VALUES (?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE hotel_name = ?, extended_description = ?";

		CsvHotelWriter writer = new CsvHotelWriter();
		writer.setDataSource(dataSource);
		writer.setJdbcTemplate(namedJdbcTemplate(dataSource));

		writer.setSql(query);

		ItemPreparedStatementSetter<HotelAndRateCombination> valueSetter = new CsvHotelPreparedStatementSetter();
		writer.setItemPreparedStatementSetter(valueSetter);

		return writer;
	}

	/**
	 * rateWriter returns a writer writing to ipm_hotel_rate table with given DataSource connection to the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<HotelAndRateCombination> a writer writing to ipm_hotel_rate table
	 */
	@StepScope
	@Bean
	public ItemWriter<HotelAndRateCombination> rateWriter(DataSource dataSource) {
		String query = "INSERT INTO ipm_hotel_rate (hotel_id, season, description, source)" + " VALUES (?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE hotel_id = ?";

		JdbcBatchItemWriter<HotelAndRateCombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<HotelAndRateCombination> valueSetter = new CsvRatePreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::HotelWriter[]

	// tag::ImageWriter[]

	/**
	 * imageUrlWriter returns a composite writer combining writers writing to hotel_image table and hotel_image_url table to follow the restriction of
	 * Spring Batch's Item Writer of taking only one class type while writing to 2 MySQL tables in the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<ImageAndUrlCombination> a composite writer combining writers writing to hotel_image table and hotel_image_url table
	 */
	@Bean
	public ItemWriter<ImageAndUrlCombination> imageUrlWriter(DataSource dataSource) {

		CompositeItemWriter<ImageAndUrlCombination> compositeItemWriter = new CompositeItemWriter<>();

		compositeItemWriter.setDelegates(Arrays.asList(imageWriter(dataSource), urlWriter(dataSource)));

		return compositeItemWriter;
	}

	/**
	 * imageWriter returns a writer writing to hotel_image table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<ImageAndUrlCombination> a writer writing to hotel_image table
	 */
	@Bean
	public ItemWriter<ImageAndUrlCombination> imageWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_image (hotel_id, title, id, source)" + " VALUES (?, ?, ?, ?)";

		JdbcBatchItemWriter<ImageAndUrlCombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<ImageAndUrlCombination> valueSetter = new CsvImagePreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * urlWriter returns a writer writing to hotel_image_url table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<ImageAndUrlCombination> a writer writing to hotel_image_url table
	 */
	@Bean
	public ItemWriter<ImageAndUrlCombination> urlWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_image_url (url, image_id)" + " VALUES (?, ?)";

		JdbcBatchItemWriter<ImageAndUrlCombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<ImageAndUrlCombination> valueSetter = new CsvUrlPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::ImageWriter[]

	// tag::RoomWriter[]

	/**
	 * roomWriter returns a composite writer combining writers writing to hotel_room table and hotel_room_feature_type table to follow the restriction
	 * of Spring Batch's Item Writer of taking only one class type while writing to 2 MySQL tables in the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return a composite writer combining writers writing to hotel_room table and hotel_room_feature_type table
	 */
	@Bean
	public ItemWriter<CategoryAndFeatureCombination> roomWriter(DataSource dataSource) {

		CompositeItemWriter<CategoryAndFeatureCombination> compositeItemWriter = //
				new CompositeItemWriter<>();

		compositeItemWriter.setDelegates(Arrays.asList(categoryWriter(dataSource), roomFeatureWriter(dataSource)));

		return compositeItemWriter;
	}

	/**
	 * categoryWriter returns a writer writing to hotel_room table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return CsvRoomWriter a writer writing to hotel_room table
	 */
	@Bean
	public CsvRoomWriter categoryWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_room (hotel_id, name, description, room_id)" + " VALUES (?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE room_id = ?";

		CsvRoomWriter databaseItemWriter = new CsvRoomWriter();

		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<CategoryAndFeatureCombination> valueSetter = new CategoryPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * roomFeatureWriter returns a writer writing to hotel_room_feature_type table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<CategoryAndFeatureCombination> a writer writing to hotel_room_feature_type table
	 */
	@Bean
	public ItemWriter<CategoryAndFeatureCombination> roomFeatureWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_room_feature_type (feature_id, room_id, feature_type, source)" + " VALUES (?, ?, ?, ?)";

		JdbcBatchItemWriter<CategoryAndFeatureCombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<CategoryAndFeatureCombination> valueSetter = new CsvRoomFeaturePreparedStatementSetter(namedJdbcTemplate(dataSource));
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::RoomWriter[]

	// tag::FeatureWriter[]

	/**
	 * hotelFeatureWriter returns a writer writing to hotel_feature_type table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<HotelFeature> a writer writing to hotel_feature_type table
	 */
	@Bean
	public ItemWriter<HotelFeature> hotelFeatureWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_feature_type (hotel_id, feature_id, feature_type, source)" + " VALUES (?, ?, ?, ?)";

		JdbcBatchItemWriter<HotelFeature> databaseItemWriter = new JdbcBatchItemWriter<>();

		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<HotelFeature> valueSetter = new CsvHotelFeaturePreparedStatementSetter(namedJdbcTemplate(dataSource));

		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::FeatureWriter[]

	// tag::PointOfInterestFeatureWriter[]
	/**
	 * interestFeatureWriter returns a writer writing to hotel_point_of_interset_feature_type table given DataSource connection to destination
	 * database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<InterestFeature> a writer writing to hotel_point_of_interset_feature_type table
	 */
	@Bean
	public ItemWriter<InterestFeature> interestFeatureWriter(DataSource dataSource) {
		String query = "INSERT INTO hotel_point_of_interset_feature_type (hotel_id, feature_id, feature_type, source)" + " VALUES (?, ?, ?, ?)";

		JdbcBatchItemWriter<InterestFeature> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<InterestFeature> valueSetter = new CsvInterestFeaturePreparedStatementSetter(namedJdbcTemplate(dataSource));
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::PointOfInterestFeatureWriter[]

	// tag::DCMappingWriter[]

	/**
	 * dcMappingWriter returns a composite writer combining writers writing to dc_hotel_id_mapping table, dc_hotel_category_mapping table and
	 * dc_hotel_rate_mapping table to follow the restriction of Spring Batch's Item Writer of taking only one class type while writing to 3 MySQL
	 * tables in the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<DCCombination> a composite writer combining writers writing to dc_hotel_id_mapping table, dc_hotel_category_mapping table
	 *         and dc_hotel_rate_mapping table
	 */
	@Bean
	public ItemWriter<DCCombination> dcMappingWriter(DataSource dataSource) {

		CompositeItemWriter<DCCombination> compositeItemWriter = //
				new CompositeItemWriter<>();

		compositeItemWriter.setDelegates(Arrays.asList(idMappingWriter(dataSource), categoryMappingWriter(dataSource), rateMappingWriter(dataSource)));

		return compositeItemWriter;
	}

	/**
	 * idMappingWriter returns a writer writing to dc_hotel_id_mapping table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return DcIdWriter a writer writing to dc_hotel_id_mapping table
	 */
	@Bean
	public DcIdWriter idMappingWriter(DataSource dataSource) {
		String query = "INSERT INTO dc_hotel_id_mapping (hotel_id, vendorHotelID, source)" + " VALUES (?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE hotel_id = ?";

		DcIdWriter writer = new DcIdWriter();
		writer.setDataSource(dataSource);
		writer.setSql(query);

		ItemPreparedStatementSetter<DCCombination> valueSetter = new IdMappingItemPreparedStatementSetter();

		writer.setItemPreparedStatementSetter(valueSetter);
		return writer;
	}

	/**
	 * categoryMappingWriter returns a writer writing to dc_hotel_category_mapping table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<DCCombination> a writer writing to dc_hotel_category_mapping table
	 */
	@Bean
	public ItemWriter<DCCombination> categoryMappingWriter(DataSource dataSource) {
		String query = "INSERT INTO dc_hotel_category_mapping (vendorHotelID, vendorCategory, cwcCategory, vendorID)" + " VALUES (?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE vendorHotelID = ?";

		JdbcBatchItemWriter<DCCombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<DCCombination> valueSetter = new CategoryMappingItemPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * rateMappingWriter returns a writer writing to dc_hotel_rate_mapping table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<DCCombination> a writer writing to dc_hotel_rate_mapping table
	 */
	@Bean
	public ItemWriter<DCCombination> rateMappingWriter(DataSource dataSource) {
		String query = "INSERT INTO dc_hotel_rate_mapping " + "(vendorHotelID, internalRateDisplay, externalRateDisplay, vendorId, id)"
				+ " VALUES (?, ?, ?, ? ,?)" + " ON DUPLICATE KEY UPDATE id = ?";

		JdbcBatchItemWriter<DCCombination> databaseItemWriter = new JdbcBatchItemWriter<>();

		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<DCCombination> valueSetter = new RateMappingItemPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::DCMappingWriter[]

	// tag::ReferenceTableWriter[]

	/**
	 * otaWriter returns a composite writer combining writers writing to FEATURE_TYPE table and DERBY_FEATURE_ID_MAPPING table to follow the
	 * restriction of Spring Batch's Item Writer of taking only one class type while writing to 2 MySQL tables in the destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<OTACombination> a composite writer combining writers writing to FEATURE_TYPE table and DERBY_FEATURE_ID_MAPPING table
	 */
	@StepScope
	@Bean
	public ItemWriter<OTACombination> otaWriter(DataSource dataSource) {

		CompositeItemWriter<OTACombination> compositeItemWriter = //
				new CompositeItemWriter<>();

		compositeItemWriter.setDelegates(Arrays.asList(featureTypeWriter(dataSource), derbyFeatureMapWriter(dataSource)));

		return compositeItemWriter;
	}

	/**
	 * featureTypeWriter returns a writer writing to FEATURE_TYPE table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<OTACombination> a writer writing to FEATURE_TYPE table
	 */
	@StepScope
	@Bean
	public ItemWriter<OTACombination> featureTypeWriter(DataSource dataSource) {
		String query = "INSERT INTO FEATURE_TYPE (feature_id, feature_type)" + " VALUES (?, ?)"//
				+ " ON DUPLICATE KEY UPDATE feature_type = ?";

		JdbcBatchItemWriter<OTACombination> databaseItemWriter = new JdbcBatchItemWriter<>();

		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<OTACombination> valueSetter = new OTAFeatureTypePreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}

	/**
	 * derbyFeatureMapWriter returns a writer writing to DERBY_FEATURE_ID_MAPPING table given DataSource connection to destination database
	 * 
	 * @param dataSource
	 *                   DataSource Connection to the destination MySQL database
	 * @return ItemWriter<OTACombination> a writer writing to DERBY_FEATURE_ID_MAPPING table
	 */
	@StepScope
	@Bean
	public ItemWriter<OTACombination> derbyFeatureMapWriter(DataSource dataSource) {
		String query = "INSERT INTO DERBY_FEATURE_ID_MAPPING (feature_code, feature_id, type_code)" + " VALUES (?, ?, ?)"//
				+ " ON DUPLICATE KEY UPDATE type_code = ?";

		JdbcBatchItemWriter<OTACombination> databaseItemWriter = new JdbcBatchItemWriter<>();
		databaseItemWriter.setDataSource(dataSource);
		databaseItemWriter.setJdbcTemplate(namedJdbcTemplate(dataSource));

		databaseItemWriter.setSql(query);

		ItemPreparedStatementSetter<OTACombination> valueSetter = new DerbyFeatureMapPreparedStatementSetter();
		databaseItemWriter.setItemPreparedStatementSetter(valueSetter);

		return databaseItemWriter;
	}
	// end::ReferenceTableWriter[]

}
