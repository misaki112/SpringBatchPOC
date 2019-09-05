package com.cwctravel.batch.config.writer.csv;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;

/**
 * CsvRoomWriter is a JdbcBatchItemWriter writing room data from csv file 
 * to destination MySQL database with toleration to dead lock and lock wait timeout errors.
 * @author chris.nie
 *
 */
public class CsvRoomWriter extends JdbcBatchItemWriter<CategoryAndFeatureCombination> {

	private static final Logger log = LoggerFactory.getLogger(CsvRoomWriter.class);

	/**
	 * Write given list of items to MySQL database with 20 times fault toleration to deadlock and 
	 * lock wait timeout exception. Each time encountering exception, retry after 0.5 second.
	 */
	@Override
	public void write(List<? extends CategoryAndFeatureCombination> items) throws Exception {
		int count = 0;
		int maxTries = 20;
		while(true) {
			try {
				super.write(items);
				break;
			}
			catch(Exception e) {
				log.info("Lock exception, retry after 1 second");
				Thread.sleep(100);
				if(++count == maxTries) {
					throw e;
				}
			}
		}
	}
}
