package com.cwctravel.batch.config.writer.derbyresponse.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.OTACombination;

/**
 * DerbyFeatureMapPreparedStatementSetter maps Derby feature map related fields in OTACombination
 * to columns of DERBY_FEATURE_ID_MAPPING table.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.OTACombination} for class OTACombination.
 * @author chris.nie
 *
 */
public class DerbyFeatureMapPreparedStatementSetter implements ItemPreparedStatementSetter<OTACombination> {

	public void setValues(OTACombination item, PreparedStatement ps) throws SQLException {
		ps.setInt(1, item.getFeatureMap().getFeatureCode());
		ps.setLong(2, item.getFeatureMap().getFeatureId());
		ps.setInt(3, item.getFeatureMap().getTypeCode());
		ps.setInt(4, item.getFeatureMap().getTypeCode());
	}

}
