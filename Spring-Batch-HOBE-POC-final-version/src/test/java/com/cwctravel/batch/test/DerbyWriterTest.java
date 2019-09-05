package com.cwctravel.batch.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.cwctravel.batch.config.BatchConfiguration;
import com.cwctravel.batch.config.TableCleanTasklet;
import com.cwctravel.batch.config.util.CsvPartitionUtil;
import com.cwctravel.batch.config.util.CsvWriterUtil;
import com.cwctravel.batch.config.util.DerbyWriterUtil;
import com.cwctravel.batch.config.writer.derbyresponse.DerbyHotelItemWriter;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

import junit.framework.TestCase;

/**
 * This class test if writer of web service response import step is writing fields correctly to columns of tables in destination database.
 * 
 * @author chris.nie
 */
@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class DerbyWriterTest extends TestCase {

	@Value("${hotelTableName}")
	private String hotelTableName;

	@Value("${rateTableName}")
	private String rateTableName;

	@Value("${dcIdMappingTableName}")
	private String dcIdMappingTableName;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private DerbyHotelItemWriter derbyHotelWriter;

	@Before
	@After
	public void cleanTables() {
		jobLauncherTestUtils.launchStep("cleanDCIdMappingTable");
		jobLauncherTestUtils.launchStep("cleanRateTable");
		jobLauncherTestUtils.launchStep("cleanHotelTable");
	}

	@Test
	public void test() throws Exception {
		HotelDescriptiveContent mockInput = new HotelDescriptiveContent();
		mockInput.setHotelName("Fancy Hotel");
		mockInput.setHotelId("TestFancyHotel");
		mockInput.setHotelCode("code");
		mockInput.setCombinedId("season");

		List<HotelDescriptiveContent> items = new ArrayList<>();
		items.add(mockInput);

		derbyHotelWriter.write(items);

		String hotelQuery = "SELECT * FROM " + hotelTableName;
		Map<String, Object> hotelResultMap = template.queryForMap(hotelQuery);

		assertNotNull(hotelResultMap);
		assertEquals(hotelResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(hotelResultMap.get("hotel_name"), "Fancy Hotel");

		String rateQuery = "SELECT * FROM " + rateTableName;
		Map<String, Object> rateResultMap = template.queryForMap(rateQuery);

		assertNotNull(rateResultMap);
		assertEquals(rateResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(rateResultMap.get("season"), "season");
		assertEquals(rateResultMap.get("description"), "Contracted Rate");
		assertEquals(rateResultMap.get("source"), "Derby");

		String idMappingQuery = "SELECT * FROM " + dcIdMappingTableName;
		Map<String, Object> idResultMap = template.queryForMap(idMappingQuery);

		assertNotNull(idResultMap);
		assertEquals(idResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(idResultMap.get("vendorHotelID"), "code");
		assertEquals(idResultMap.get("source"), "Derby");

	}

}
