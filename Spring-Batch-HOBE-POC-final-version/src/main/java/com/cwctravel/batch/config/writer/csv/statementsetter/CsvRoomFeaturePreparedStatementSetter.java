package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;

/**
 * CsvRoomFeaturePreparedStatementSetter map fields in CategoryAndFeatureCombination to columns of 
 * hotel_room_feature_type table and add "csvFile" as source of the data
 * <p>
 * CsvRoomFeaturePreparedStatementSetter also query for featureType with featureId of CategoryAndFeatureCombination 
 * in FEATUER_TYPE table to get information about featureType column in hotel_room_feature_type table
 * @author chris.nie
 *
 */
public class CsvRoomFeaturePreparedStatementSetter implements ItemPreparedStatementSetter<CategoryAndFeatureCombination> {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Constructor generating CsvRoomFeaturePreparedStatementSetter with a NamedParameterJdbcTemplate
	 * @param jdbcTemplate NamedParameterJdbcTemplate helps query in FEATUER_TYPE table
	 */
	public CsvRoomFeaturePreparedStatementSetter(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Map fields in CategoryAndFeatureCombination to columns of hotel_room_feature_type table and query for 
	 * featureType with featureId of CategoryAndFeatureCombination in FEATUER_TYPE table to get information about 
	 * featureType column in hotel_room_feature_type table
	 * <p>
	 * Also add "csvFile" as source of the data
	 */
	public void setValues(CategoryAndFeatureCombination item, PreparedStatement ps) throws SQLException {
		long featureId = item.getFeature().getFeatureId();
		ps.setLong (1, featureId);
		ps.setLong (2, item.getFeature().getCategoryId());
		ps.setString (4, "csvFile");

		String getFeatureTypeQuery = "SELECT feature_type FROM FEATURE_TYPE WHERE feature_id = :feature_id";

		Map<String,Long> queryMap = new HashMap<>();

		queryMap.put("feature_id", featureId);

		String featureType = jdbcTemplate.queryForObject(getFeatureTypeQuery, queryMap, String.class);

		ps.setString(3, featureType);
	}

}
