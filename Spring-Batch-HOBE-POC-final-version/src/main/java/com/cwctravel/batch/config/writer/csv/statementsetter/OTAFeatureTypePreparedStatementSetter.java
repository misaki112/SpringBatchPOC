package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.OTACombination;

/**
 * OTAFeatureTypePreparedStatementSetter map fields of OTACombination to columns in feature_type
 * table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.OTACombination} for class OTACombination.
 * @author chris.nie
 *
 */
public class OTAFeatureTypePreparedStatementSetter implements ItemPreparedStatementSetter<OTACombination> {

	/**
	 * Map fields of OTACombination to columns in feature_type table
	 */
	public void setValues(OTACombination item, PreparedStatement ps) throws SQLException {
		ps.setLong(1, item.getFeature().getFeatureId());
		ps.setString(2, item.getFeature().getFeatureType());
		ps.setString(3, item.getFeature().getFeatureType());
	}

}
