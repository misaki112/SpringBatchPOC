package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.csv.HotelFeature;

/**
 * CsvHotelFeaturePreparedStatementSetter map fields in HotelFeature to columns of hotel_feature_type table in destination MySQL database.
 * <p>
 * CsvHotelFeaturePreparedStatementSetter also query for featureType with featureId of HotelFeature 
 * in FEATUER_TYPE table to get information about featureType column in hotel_feature_type table
 * @author chris.nie
 *
 */
public class CsvHotelFeaturePreparedStatementSetter implements ItemPreparedStatementSetter<HotelFeature> {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Constructor generating CsvHotelFeaturePreparedStatementSetter with a NamedParameterJdbcTemplate
	 * @param jdbcTemplate NamedParameterJdbcTemplate helps query in FEATUER_TYPE table
	 */
	public CsvHotelFeaturePreparedStatementSetter(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Map fields in HotelFeature to columns of hotel_feature_type table and query for featureType with featureId of HotelFeature 
	 * in FEATUER_TYPE table to get information about featureType column in hotel_feature_type table
	 */
	@Override
	public void setValues(HotelFeature item, PreparedStatement ps) throws SQLException {
		long featureId = item.getFeatureId();
		ps.setString(1, item.getHotelId());
		ps.setLong(2, featureId);
		ps.setString(4, "csvFile");

		String getFeatureTypeQuery = "SELECT feature_type FROM FEATURE_TYPE WHERE feature_id = :feature_id";

		Map<String, Long> queryMap = new HashMap<>();

		queryMap.put("feature_id", featureId);

		String featureType = jdbcTemplate.queryForObject(getFeatureTypeQuery, queryMap, String.class);

		ps.setString(3, featureType);

	}

}
