package com.cwctravel.batch.config.writer.csv.statementsetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;

/**
 * CategoryPreparedStatementSetter map fields in CategoryAndFeatureCombination to columns in hotel_room table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.csv.CategoryAndFeatureCombination} for class CategoryAndFeatureCombination
 * @author chris.nie
 *
 */
public class CategoryPreparedStatementSetter implements ItemPreparedStatementSetter<CategoryAndFeatureCombination> {

	/**
	 * Map fields in CategoryAndFeatureCombination to columns in hotel_room table in destination MySQL database
	 */
	public void setValues(CategoryAndFeatureCombination item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getCategory().getHotel().getHotelId());
		ps.setString(2, item.getCategory().getName());
		ps.setString(3, item.getCategory().getDescription());
		ps.setLong(4, item.getCategory().getId());
		ps.setLong(5, item.getCategory().getId());
	}

}
