package com.cwctravel.batch.config.processor.derby;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * DerbyFeatureIdMapper is a helper object which maps to featureId for Derby features given
 * featrueCode and typeCode of the feature
 * @author chris.nie
 *
 */
public class DerbyFeatureIdMapper {

	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * Construct a new DerbyFeatureIdMapper
	 * @param jdbcTemplate NamedParameterJdbcTemplate template used to query for
	 * featureId from derby_feature_id_mapping table
	 */
	public DerbyFeatureIdMapper(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Get featureId given featureCode and typeCode of the feature by querying derby_feature_id_mapping table
	 * @param featureCode Integer featureCode of the feature for featuerId querying
	 * @param typeCode Integer typeCode of the feature for featuerId querying
	 * @return long featureId of the feature mapping to featureType which describe the feature
	 */
	public long derbyCodeToFeatureId (int featureCode, int typeCode) {
		String query = "SELECT feature_id FROM derby_feature_id_mapping" 
				+ " WHERE feature_code = :feature_code AND type_code = :type_code";
		Map<String, Integer> queryMap = new HashMap<>();
		queryMap.put("feature_code", featureCode);
		queryMap.put("type_code", typeCode);


		long featureId = jdbcTemplate.queryForObject(query, queryMap, Long.class);
		return featureId;
	}
}
