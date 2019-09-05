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
import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;
import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCHotelCategoryMapping;
import com.cwctravel.batch.model.csv.DCHotelIdMapping;
import com.cwctravel.batch.model.csv.DCHotelRateMapping;
import com.cwctravel.batch.model.csv.DerbyFeatureMap;
import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelFeature;
import com.cwctravel.batch.model.csv.HotelImage;
import com.cwctravel.batch.model.csv.HotelRate;
import com.cwctravel.batch.model.csv.ImageAndUrlCombination;
import com.cwctravel.batch.model.csv.InterestFeature;
import com.cwctravel.batch.model.csv.OTACombination;
import com.cwctravel.batch.model.csv.RoomCategory;
import com.cwctravel.batch.model.csv.RoomFeature;
import com.cwctravel.batch.model.derby.Feature;
import com.cwctravel.batch.model.derby.ImageURL;

import junit.framework.TestCase;

/**
 * This class tests all writers in csv files importing steps.
 * <p>
 * Mock input objects for writers and check if writers write correct data to columns of tables in destination database
 * 
 * @author chris.nie
 */
@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class CsvWriterTests extends TestCase {

	@Value("${hotelTableName}")
	private String hotelTableName;

	@Value("${rateTableName}")
	private String rateTableName;

	@Value("${imageTableName}")
	private String imageTableName;

	@Value("${urlTableName}")
	private String urlTableName;

	@Value("${roomTableName}")
	private String roomTableName;

	@Value("${roomFeatureTableName}")
	private String roomFeatureTableName;

	@Value("${featureTypeTableName}")
	private String featureTypeTableName;

	@Value("${DerbyFeatureMappingTableName}")
	private String DerbyFeatureMappingTableName;

	@Value("${hotelFeatureTableName}")
	private String hotelFeatureTableName;

	@Value("${interestTableName}")
	private String interestTableName;

	@Value("${dcIdMappingTableName}")
	private String dcIdMappingTableName;

	@Value("${dcCategoryTableName}")
	private String dcCategoryTableName;

	@Value("${dcRateTableName}")
	private String dcRateTableName;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private ItemWriter<HotelAndRateCombination> hotelRateWriter;

	@Autowired
	private ItemWriter<ImageAndUrlCombination> imageUrlWriter;

	@Autowired
	private ItemWriter<OTACombination> otaWriter;

	@Autowired
	private ItemWriter<CategoryAndFeatureCombination> roomWriter;

	@Autowired
	private ItemWriter<HotelFeature> hotelFeatureWriter;

	@Autowired
	private ItemWriter<InterestFeature> interestFeatureWriter;

	@Autowired
	private ItemWriter<DCCombination> dcMappingWriter;

	private Hotel getHotel() {
		return new Hotel("TestFancyHotel", "Fancy Hotel", "This is a fancy hotel!");
	}

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
	public void csvHotelRateWriterTest() throws Exception {
		HotelAndRateCombination mockInput = new HotelAndRateCombination();
		Hotel hotel = getHotel();
		HotelRate rate = new HotelRate("Summer2019", "ContractRate", hotel);
		mockInput.setHotel(hotel);
		mockInput.setRate(rate);
		List<HotelAndRateCombination> items = new ArrayList<>();
		items.add(mockInput);

		hotelRateWriter.write(items);

		String hotelQuery = "SELECT * FROM " + hotelTableName;
		Map<String, Object> hotelResultMap = template.queryForMap(hotelQuery);

		assertNotNull(hotelResultMap);
		assertEquals(hotelResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(hotelResultMap.get("hotel_name"), "Fancy Hotel");
		assertEquals(hotelResultMap.get("extended_description"), "This is a fancy hotel!");

		String rateQuery = "SELECT * FROM " + rateTableName;
		Map<String, Object> rateResultMap = template.queryForMap(rateQuery);

		assertNotNull(rateResultMap);
		assertEquals(rateResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(rateResultMap.get("season"), "Summer2019");
		assertEquals(rateResultMap.get("description"), "ContractRate");
		assertEquals(rateResultMap.get("source"), "csvFile");

	}

	@Test
	public void csvHotelAndRateWriterRepeatRecordTest() throws Exception {
		HotelAndRateCombination mockInputOne = new HotelAndRateCombination();
		HotelAndRateCombination mockInputTwo = new HotelAndRateCombination();
		Hotel hotelOne = getHotel();
		HotelRate rateOne = new HotelRate("Summer2019", "ContractRate", hotelOne);
		Hotel hotelTwo = getHotel();
		hotelTwo.setHotelName("Fancy Hotel Two");
		HotelRate rateTwo = new HotelRate("Summer2019", "ContractRate", hotelTwo);

		mockInputOne.setHotel(hotelOne);
		mockInputOne.setRate(rateOne);
		mockInputTwo.setHotel(hotelTwo);
		mockInputTwo.setRate(rateTwo);

		List<HotelAndRateCombination> items = new ArrayList<>();
		items.add(mockInputOne);
		items.add(mockInputTwo);

		hotelRateWriter.write(items);

		String hotelCountQuery = "SELECT count(*) FROM " + hotelTableName;
		int hotelRowNumber = template.queryForObject(hotelCountQuery, Integer.class);
		assertEquals(hotelRowNumber, 1);

		String hotelQuery = "SELECT * FROM " + hotelTableName;
		Map<String, Object> hotelResultMap = template.queryForMap(hotelQuery);
		assertNotNull(hotelResultMap);
		assertEquals(hotelResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(hotelResultMap.get("hotel_name"), "Fancy Hotel Two");

		String rateCountQuery = "SELECT count(*) FROM " + rateTableName;
		int rateRowNumber = template.queryForObject(rateCountQuery, Integer.class);
		assertEquals(rateRowNumber, 1);
	}

	@Test
	public void csvImageAndUrlWriterTest() throws Exception {
		this.csvHotelRateWriterTest();

		ImageAndUrlCombination mockInput = new ImageAndUrlCombination();
		HotelImage image = new HotelImage("Lobby", 12345L, getHotel());
		ImageURL url = new ImageURL(12345L, "http://FancyHotel/Lobby", image);
		mockInput.setImage(image);
		mockInput.setImageUrl(url);
		List<ImageAndUrlCombination> items = new ArrayList<>();
		items.add(mockInput);

		imageUrlWriter.write(items);

		String imageQuery = "SELECT * FROM " + imageTableName;
		Map<String, Object> imageResultMap = template.queryForMap(imageQuery);

		assertNotNull(imageResultMap);
		assertEquals(imageResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(imageResultMap.get("title"), "Lobby");
		assertEquals(imageResultMap.get("id"), 12345L);
		assertEquals(imageResultMap.get("source"), "csvFile");

		String urlQuery = "SELECT * FROM " + urlTableName;
		Map<String, Object> urlResultMap = template.queryForMap(urlQuery);
		assertNotNull(urlResultMap);
		assertEquals(urlResultMap.get("url"), "http://FancyHotel/Lobby");
		assertEquals(urlResultMap.get("image_id"), 12345L);

	}

	@Test
	public void csvFeatureMapWriterTest() throws Exception {
		OTACombination mockInput = new OTACombination();
		Feature feature = new Feature(678L, "TestFeature");
		DerbyFeatureMap featureMap = new DerbyFeatureMap(678L, 1, 1);
		mockInput.setFeature(feature);
		mockInput.setFeatureMap(featureMap);
		List<OTACombination> items = new ArrayList<>();
		items.add(mockInput);

		otaWriter.write(items);

		String featureQuery = "SELECT * FROM " + featureTypeTableName;
		Map<String, Object> featuerTypeResultMap = template.queryForMap(featureQuery);

		assertNotNull(featuerTypeResultMap);
		assertEquals(featuerTypeResultMap.get("feature_id"), 678L);
		assertEquals(featuerTypeResultMap.get("feature_type"), "TestFeature");

		String derbyMapQuery = "SELECT * FROM " + DerbyFeatureMappingTableName;
		Map<String, Object> derbyResultMap = template.queryForMap(derbyMapQuery);

		assertNotNull(derbyResultMap);
		assertEquals(derbyResultMap.get("feature_code"), 1);
		assertEquals(derbyResultMap.get("feature_id"), 678L);
		assertEquals(derbyResultMap.get("type_code"), 1);

	}

	@Test
	public void csvRoomAndRoomFeatureWriterTest() throws Exception {
		this.csvHotelRateWriterTest();
		this.csvFeatureMapWriterTest();

		CategoryAndFeatureCombination mockInput = new CategoryAndFeatureCombination();
		RoomCategory category = new RoomCategory("President Suite", "This is a President Suite!", //
				567L, getHotel());
		RoomFeature feature = new RoomFeature(678L, 567L, category);
		mockInput.setCategory(category);
		mockInput.setFeature(feature);
		List<CategoryAndFeatureCombination> items = new ArrayList<>();
		items.add(mockInput);

		roomWriter.write(items);

		String roomQuery = "SELECT * FROM " + roomTableName;
		Map<String, Object> roomResultMap = template.queryForMap(roomQuery);

		assertNotNull(roomResultMap);
		assertEquals(roomResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(roomResultMap.get("name"), "President Suite");
		assertEquals(roomResultMap.get("description"), "This is a President Suite!");
		assertEquals(roomResultMap.get("room_id"), 567L);

		String roomFeatureQuery = "SELECT * FROM " + roomFeatureTableName;
		Map<String, Object> roomFeatureResultMap = template.queryForMap(roomFeatureQuery);

		assertNotNull(roomFeatureResultMap);
		assertEquals(roomFeatureResultMap.get("feature_id"), 678L);
		assertEquals(roomFeatureResultMap.get("room_id"), 567L);
		assertEquals(roomFeatureResultMap.get("feature_type"), "TestFeature");
		assertEquals(roomFeatureResultMap.get("source"), "csvFile");

	}

	@Test
	public void csvHotelFeatureWriterTest() throws Exception {
		this.csvHotelRateWriterTest();
		this.csvFeatureMapWriterTest();

		HotelFeature mockInput = new HotelFeature(678L, "TestFancyHotel");
		List<HotelFeature> items = new ArrayList<>();
		items.add(mockInput);

		hotelFeatureWriter.write(items);

		String featureQuery = "SELECT * FROM " + hotelFeatureTableName;
		Map<String, Object> hotelFeatureResultMap = template.queryForMap(featureQuery);

		assertNotNull(hotelFeatureResultMap);
		assertEquals(hotelFeatureResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(hotelFeatureResultMap.get("feature_id"), 678L);
		assertEquals(hotelFeatureResultMap.get("feature_type"), "TestFeature");
		assertEquals(hotelFeatureResultMap.get("source"), "csvFile");

	}

	@Test
	public void csvPointOfInterestWriterTest() throws Exception {
		this.csvHotelRateWriterTest();
		this.csvFeatureMapWriterTest();

		InterestFeature mockInput = new InterestFeature(678L, "TestFancyHotel");
		List<InterestFeature> items = new ArrayList<>();
		items.add(mockInput);

		interestFeatureWriter.write(items);

		String interestQuery = "SELECT * FROM " + interestTableName;
		Map<String, Object> interestResultMap = template.queryForMap(interestQuery);

		assertNotNull(interestResultMap);
		assertEquals(interestResultMap.get("hotel_id"), "TestFancyHotel");
		assertEquals(interestResultMap.get("feature_id"), 678L);
		assertEquals(interestResultMap.get("feature_type"), "TestFeature");
		assertEquals(interestResultMap.get("source"), "csvFile");

	}

	@Test
	public void csvDcMappingWriterTest() throws Exception {
		this.csvHotelRateWriterTest();

		DCCombination mockInput = new DCCombination();
		DCHotelIdMapping idMapping = new DCHotelIdMapping("vendorFancyHotel", getHotel());
		DCHotelCategoryMapping categoryMapping = new DCHotelCategoryMapping("vendorCatFancy", "cwcCatFancy", //
				"v1", idMapping);
		DCHotelRateMapping rateMapping = new DCHotelRateMapping("internalMemberRate", "externalContractRate", //
				"v1", 234L, categoryMapping);
		mockInput.setCategoryMapping(categoryMapping);
		mockInput.setIdMapping(idMapping);
		mockInput.setRateMapping(rateMapping);

		List<DCCombination> items = new ArrayList<>();
		items.add(mockInput);

		dcMappingWriter.write(items);

		String idMapQuery = "SELECT * FROM " + dcIdMappingTableName;
		Map<String, Object> idMapResult = template.queryForMap(idMapQuery);

		assertNotNull(idMapResult);
		assertEquals(idMapResult.get("hotel_id"), "TestFancyHotel");
		assertEquals(idMapResult.get("vendorHotelID"), "vendorFancyHotel");
		assertEquals(idMapResult.get("source"), "csvFile");

		String categoryMapQuery = "SELECT * FROM " + dcCategoryTableName;
		Map<String, Object> categoryMapResult = template.queryForMap(categoryMapQuery);

		assertNotNull(categoryMapResult);
		assertEquals(categoryMapResult.get("vendorHotelID"), "vendorFancyHotel");
		assertEquals(categoryMapResult.get("vendorCategory"), "vendorCatFancy");
		assertEquals(categoryMapResult.get("cwcCategory"), "cwcCatFancy");
		assertEquals(categoryMapResult.get("vendorID"), "v1");

		String rateMapQuery = "SELECT * FROM " + dcRateTableName;
		Map<String, Object> rateMapResult = template.queryForMap(rateMapQuery);

		assertNotNull(rateMapResult);
		assertEquals(rateMapResult.get("vendorHotelID"), "vendorFancyHotel");
		assertEquals(rateMapResult.get("internalRateDisplay"), "internalMemberRate");
		assertEquals(rateMapResult.get("externalRateDisplay"), "externalContractRate");
		assertEquals(rateMapResult.get("vendorId"), "v1");
		assertEquals(rateMapResult.get("id"), 234L);

	}
}
