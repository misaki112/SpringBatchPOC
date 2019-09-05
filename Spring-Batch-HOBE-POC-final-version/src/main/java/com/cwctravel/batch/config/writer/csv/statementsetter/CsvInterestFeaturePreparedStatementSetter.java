package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.csv.InterestFeature;

/**
 * CsvInterestFeaturePreparedStatementSetter map fields in InterestFeature to columns of 
 * hotel_point_of_interset_feature_type table and add "csvFile" as source of the data
 * <p>
 * CsvInterestFeaturePreparedStatementSetter also query for featureType with featureId of InterestFeature 
 * in FEATUER_TYPE table to get information about featureType column in hotel_point_of_interset_feature_type table
 * @author chris.nie
 *
 */
public class CsvInterestFeaturePreparedStatementSetter implements ItemPreparedStatementSetter<InterestFeature> {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Constructor generating CsvInterestFeaturePreparedStatementSetter with a NamedParameterJdbcTemplate
	 * @param jdbcTemplate NamedParameterJdbcTemplate helps query in FEATUER_TYPE table
	 */
	public CsvInterestFeaturePreparedStatementSetter(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Map fields in InterestFeature to columns of hotel_point_of_interset_feature_type table and query for 
	 * featureType with featureId of InterestFeature in FEATUER_TYPE table to get information about 
	 * featureType column in hotel_point_of_interset_feature_type table.
	 * <p>
	 * Also add "csvFile" as source of the data
	 */
	@Override
	public void setValues(InterestFeature item, PreparedStatement ps) throws SQLException {
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
