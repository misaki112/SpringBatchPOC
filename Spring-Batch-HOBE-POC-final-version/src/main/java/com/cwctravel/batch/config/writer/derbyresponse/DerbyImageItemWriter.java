package com.cwctravel.batch.config.writer.derbyresponse;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.cwctravel.batch.model.derby.ImageItem;

/**
 * DerbyImageItemWriter is a JdbcBatchItemWriter which write to hotel_image table and trigger
 * writer for hotel_image_url table.
 * <p>
 * DerbyImageItemWriter takes ImageItem as input and write to hotel_image table using JdbcTemplate.
 * @author chris.nie
 *
 */
public class DerbyImageItemWriter extends JdbcBatchItemWriter<ImageItem> {

	private ItemWriter<ImageItem> urlWriter;

	/**
	 * Construct a new DerbyImageItemWriter
	 * @param urlWriter ItemWriter<ImageItem> writer writing to hotel_image_url table
	 * @param dataSource DataSource Connection to the destination MySQl database.
	 */
	public DerbyImageItemWriter(ItemWriter<ImageItem> urlWriter, DataSource dataSource) {
		super();
		this.urlWriter = urlWriter;
		this.setDataSource(dataSource);
	}

	/**
	 * Write to hotel_image table and trigger writer for hotel_image_url table.
	 */
	@Override
	public void write(List<? extends ImageItem> items) throws Exception {
		for(ImageItem item: items) {
			List<ImageItem> list = new ArrayList<>();
			list.add(item);
			super.write(list);
			urlWriter.write(list);
		}

	}

}
