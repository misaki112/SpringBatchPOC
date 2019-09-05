package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.derby.Service;

/**
 * DerbyFeatureMappingPreparedStatementSetter maps fields in Service to columns of 
 * hotel_feature_type table and add "Derby" as source of data
 * <p>
 * DerbyFeatureMappingPreparedStatementSetter also query for featureType with featureId of Service 
 * in FEATUER_TYPE table to get information about featureType column in hotel_feature_type table
 * 
 * @author chris.nie
 *
 */
public class DerbyFeatureMappingPreparedStatementSetter implements ItemPreparedStatementSetter<Service> {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Construct a new DerbyFeatureMappingPreparedStatementSetter
	 * @param jdbcTemplate NamedParameterJdbcTemplate helps query in FEATUER_TYPE table
	 */
	public DerbyFeatureMappingPreparedStatementSetter(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Maps fields in Service to columns of hotel_feature_type table and add "Derby" as source of data
	 * Also query for featureType with featureId of Service in FEATUER_TYPE table to get information about featureType column
	 */
	public void setValues(Service item, PreparedStatement ps) throws SQLException {
		long featureId = item.getFeatureId();
		ps.setString(1, item.getHotelId());
		ps.setLong(2, featureId);
		ps.setString(4, item.getLocation());
		ps.setString(5, "Derby");

		String getFeatureTypeQuery = "SELECT feature_type FROM FEATURE_TYPE WHERE feature_id = :feature_id";

		Map<String,Long> queryMap = new HashMap<>();

		queryMap.put("feature_id", featureId);

		String featureType = jdbcTemplate.queryForObject(getFeatureTypeQuery, queryMap, String.class);

		ps.setString(3, featureType);
	}

}
