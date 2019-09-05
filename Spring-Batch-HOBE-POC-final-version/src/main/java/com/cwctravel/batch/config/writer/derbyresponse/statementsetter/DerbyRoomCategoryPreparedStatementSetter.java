package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.derby.Amenity;

/**
 * DerbyRoomCategoryPreparedStatementSetter maps fields in Amenity to columns of hotel_room_feature_type table,
 * add "Derby" as source of data.
 * <p>
 * DerbyRoomCategoryPreparedStatementSetter also query for featureType with featureId of Amenity 
 * in FEATUER_TYPE table to get information about featureType column in hotel_room_feature_type table
 * @author chris.nie
 *
 */
public class DerbyRoomCategoryPreparedStatementSetter implements ItemPreparedStatementSetter<Amenity> {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Construct a new DerbyRoomCategoryPreparedStatementSetter
	 * @param jdbcTemplate NamedParameterJdbcTemplate helps query in FEATUER_TYPE table
	 */
	public DerbyRoomCategoryPreparedStatementSetter(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Maps fields in Amenity to columns of hotel_room_feature_type table,
	 * add "Derby" as source of data. Also query for featureType with featureId of Amenity 
	 * in FEATUER_TYPE table to get information about featureType column
	 */
	public void setValues(Amenity item, PreparedStatement ps) throws SQLException {
		long featureId = item.getFeatureId();
		ps.setLong (1, featureId);
		ps.setLong (2, item.getRoomId());
		ps.setString(4, "Derby");
		String getFeatureTypeQuery = "SELECT feature_type FROM FEATURE_TYPE WHERE feature_id = :feature_id";

		Map<String,Long> queryMap = new HashMap<>();

		queryMap.put("feature_id", featureId);

		String featureType = jdbcTemplate.queryForObject(getFeatureTypeQuery, queryMap, String.class);

		ps.setString(3, featureType);
	}

}
