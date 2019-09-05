package com.cwctravel.batch.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemWriter;
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
import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelRate;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

import junit.framework.TestCase;

/**
 * This class tests if the writers are writing in a correct way with correct version entry to the destination database when input has repeat data.
 * 
 * @author chris.nie
 */
@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class VersionedWriterTest extends TestCase {

	@Value("${hotelTableName}")
	private String hotelTableName;

	@Value("${rateTableName}")
	private String rateTableName;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private ItemWriter<HotelAndRateCombination> hotelRateWriter;

	@Autowired
	private DerbyHotelItemWriter derbyHotelWriter;

	@Before
	@After
	public void cleanTables() {
		jobLauncherTestUtils.launchStep("cleanDCRateMappingTable");
		jobLauncherTestUtils.launchStep("cleanDCCategoryMappingTable");
		jobLauncherTestUtils.launchStep("cleanDCIdMappingTable");
		jobLauncherTestUtils.launchStep("cleanHotelFeatureTable");
		jobLauncherTestUtils.launchStep("cleanInterestFeatureTable");
		jobLauncherTestUtils.launchStep("cleanFeatureTypeTable");
		jobLauncherTestUtils.launchStep("cleanDerbyFeatureMappingTable");
		jobLauncherTestUtils.launchStep("cleanRoomFeatureTable");
		jobLauncherTestUtils.launchStep("cleanRoomTable");
		jobLauncherTestUtils.launchStep("cleanRateTable");
		jobLauncherTestUtils.launchStep("cleanUrlTable");
		jobLauncherTestUtils.launchStep("cleanImageTable");
		jobLauncherTestUtils.launchStep("cleanHotelTable");

	}

	@Test
	public void versionedWriteTest() throws Exception {
		HotelDescriptiveContent derbyMockInput = new HotelDescriptiveContent();
		derbyMockInput.setHotelName("Fancy Hotel");
		derbyMockInput.setHotelId("TestFancyHotel");
		derbyMockInput.setHotelCode("code");
		derbyMockInput.setCombinedId("season");

		HotelAndRateCombination csvMockInput = new HotelAndRateCombination();
		Hotel hotel = new Hotel("TestFancyHotel", "Fancy Hotel", "This is a fancy hotel!");
		HotelRate rate = new HotelRate("Summer2019", "ContractRate", hotel);
		csvMockInput.setHotel(hotel);
		csvMockInput.setRate(rate);

		List<HotelDescriptiveContent> derbyList = new ArrayList<>();
		derbyList.add(derbyMockInput);
		derbyHotelWriter.write(derbyList);

		List<HotelAndRateCombination> csvList = new ArrayList<>();
		csvList.add(csvMockInput);
		hotelRateWriter.write(csvList);

		String hotelCountQuery = "SELECT count(*) FROM " + hotelTableName;
		int hotelRowNumber = template.queryForObject(hotelCountQuery, Integer.class);
		assertEquals(hotelRowNumber, 1);

		String hotelQuery = "SELECT * FROM " + hotelTableName;
		Map<String, Object> hotelResultMap = template.queryForMap(hotelQuery);
		assertNotNull(hotelResultMap);
		assertEquals(hotelResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(hotelResultMap.get("hotel_name"), "Fancy Hotel");
		assertEquals(hotelResultMap.get("extended_description"), "This is a fancy hotel!");

		String rateCountQuery = "SELECT count(*) FROM " + rateTableName;
		int rateRowNumber = template.queryForObject(rateCountQuery, Integer.class);
		assertEquals(rateRowNumber, 2);
	}

}
