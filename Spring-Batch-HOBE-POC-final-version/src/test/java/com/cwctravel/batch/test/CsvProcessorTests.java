package com.cwctravel.batch.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.util.Assert;

import com.cwctravel.batch.config.BatchConfiguration;
import com.cwctravel.batch.config.TableCleanTasklet;
import com.cwctravel.batch.config.processor.csv.CsvDCItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvHotelItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvImageItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvOTAItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvRoomItemProcessor;
import com.cwctravel.batch.config.processor.csv.HotelFeatureItemProcessor;
import com.cwctravel.batch.config.processor.csv.HotelPointOfInterestItemProcessor;
import com.cwctravel.batch.config.util.CsvPartitionUtil;
import com.cwctravel.batch.config.util.CsvWriterUtil;
import com.cwctravel.batch.config.util.DerbyWriterUtil;
import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;
import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCRowObject;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelFeature;
import com.cwctravel.batch.model.csv.HotelRowObject;
import com.cwctravel.batch.model.csv.ImageAndUrlCombination;
import com.cwctravel.batch.model.csv.ImageRowObject;
import com.cwctravel.batch.model.csv.InterestFeature;
import com.cwctravel.batch.model.csv.OTACombination;
import com.cwctravel.batch.model.csv.OTARowObject;
import com.cwctravel.batch.model.csv.RoomRowObject;

import junit.framework.TestCase;

/**
 * This class tests all processors in csv files importing steps
 * <p>
 * Mock objects being used as input, check if processors turn input to correct type of output and map data in each field correctly.
 * 
 * @author chris.nie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class CsvProcessorTests extends TestCase {

	@Autowired
	private CsvHotelItemProcessor hotelProcessor;

	@Autowired
	private CsvImageItemProcessor imageProcessor;

	@Autowired
	private CsvRoomItemProcessor roomProcessor;

	@Autowired
	private HotelFeatureItemProcessor featureProcessor;

	@Autowired
	private HotelPointOfInterestItemProcessor interestProcessor;

	@Autowired
	private CsvDCItemProcessor dcProcessor;

	@Autowired
	private CsvOTAItemProcessor otaProcessor;

	@Test
	public void csvHotelProcessorTest() throws Exception {

		HotelRowObject mockRow = new HotelRowObject("TestFancyHotel", "FancyHotel", //
				"This is a fancy hotel!", "Summer2019", "ContractRate");

		HotelAndRateCombination resultItem = hotelProcessor.process(mockRow);

		assertNotNull(resultItem);
		assertEquals(resultItem.getHotel().getHotelId(), "TestFancyHotel");
		assertEquals(resultItem.getHotel().getHotelName(), "FancyHotel");
		assertEquals(resultItem.getHotel().getExtendedDescription(), "This is a fancy hotel!");
		assertNotNull(resultItem.getHotel().getRate());
		assertEquals(resultItem.getRate().getSeason(), "Summer2019");
		assertEquals(resultItem.getRate().getDescription(), "ContractRate");
	}

	@Test
	public void csvImageItemProcessorTest() throws Exception {
		ImageRowObject mockRow = new ImageRowObject("TestFancyHotel", "Lobby", 12345L, //
				12345L, "http://FancyHotel/Lobby");

		ImageAndUrlCombination resultItem = imageProcessor.process(mockRow);
		assertNotNull(resultItem);
		assertEquals(resultItem.getImage().getHotel().getHotelId(), "TestFancyHotel");
		assertEquals(resultItem.getImage().getId(), 12345L);
		assertEquals(resultItem.getImage().getTitle(), "Lobby");
		assertEquals(resultItem.getImageUrl().getUrl(), "http://FancyHotel/Lobby");
		assertEquals(resultItem.getImageUrl().getImageId(), 12345L);
		Assert.notEmpty(resultItem.getImage().getUrlSet(), "Image Url not sucessfully loaded");
	}

	@Test
	public void csvRoomItemProcessorTest() throws Exception {
		RoomRowObject mockRow = new RoomRowObject("TestFancyHotel", "President Suite", //
				"Fancy Presodent Suite", 123L, 2345L, 123L);

		CategoryAndFeatureCombination resultItem = roomProcessor.process(mockRow);
		assertNotNull(resultItem);
		assertEquals(resultItem.getCategory().getHotel().getHotelId(), "TestFancyHotel");
		assertEquals(resultItem.getCategory().getDescription(), "Fancy Presodent Suite");
		assertEquals(resultItem.getCategory().getId(), 123L);
		assertEquals(resultItem.getFeature().getCategoryId(), 123L);
		assertEquals(resultItem.getFeature().getFeatureId(), 2345L);
		Assert.notEmpty(resultItem.getCategory().getFeatureSet(), "Room features not sucessfully loaded");
	}

	@Test
	public void csvHotelFeatureItemProcessorTest() throws Exception {
		HotelFeature mockRow = new HotelFeature(2345L, "TestFancyHotel");
		assertNull(mockRow.getHotel());

		HotelFeature resultItem = featureProcessor.process(mockRow);

		assertNotNull(resultItem);
		assertNotNull(resultItem.getHotel());
		assertEquals(resultItem.getHotel().getHotelId(), "TestFancyHotel");
		assertEquals(resultItem.getFeatureId(), 2345L);
	}

	@Test
	public void csvHotelPointOfInterestItemProcessorTest() throws Exception {
		InterestFeature mockRow = new InterestFeature(3456L, "TestFancyHotel");
		assertNull(mockRow.getHotel());

		InterestFeature resultItem = interestProcessor.process(mockRow);
		assertNotNull(resultItem);
		assertNotNull(resultItem.getHotel());
		assertEquals(resultItem.getHotel().getHotelId(), "TestFancyHotel");
		assertEquals(resultItem.getFeatureId(), 3456L);
	}

	@Test
	public void csvDCItemProcessorTest() throws Exception {
		DCRowObject mockRow = new DCRowObject("Vendor Id Fancy", "vendorFancyTest", "cwcFancyTest", //
				"TestFancyHotel", "vendor1", "InternalMemberRate", //
				"ExternalContractRate", "3456");

		DCCombination resultItem = dcProcessor.process(mockRow);

		assertNotNull(resultItem);

		assertEquals(resultItem.getIdMapping().getVendorHotelId(), "Vendor Id Fancy");
		Assert.notEmpty(resultItem.getIdMapping().getCategoryMappingSet(), "Category Mapping not successfully loaded");
		assertEquals(resultItem.getIdMapping().getHotel().getHotelId(), "TestFancyHotel");

		assertEquals(resultItem.getCategoryMapping().getCwcCategory(), "cwcFancyTest");
		assertEquals(resultItem.getCategoryMapping().getVendorId(), "vendor1");
		assertNotNull(resultItem.getCategoryMapping().getIdMapping());
		Assert.notEmpty(resultItem.getCategoryMapping().getRateMappingSet(), "Rate Mapping not successfully loaded");

		assertEquals(resultItem.getRateMapping().getExternalRateDisplay(), "ExternalContractRate");
		assertEquals(resultItem.getRateMapping().getInternalRateDisplay(), "InternalMemberRate");
		assertEquals(resultItem.getRateMapping().getId(), 3456L);
		assertNotNull(resultItem.getRateMapping().getCategoryMapping());
	}

	@Test
	public void csvOTAItemProcessorTest() throws Exception {
		OTARowObject mockRow = new OTARowObject(234L, 1, 2, "FeatureTypeAirport");

		OTACombination resultItem = otaProcessor.process(mockRow);

		assertNotNull(resultItem);
		assertEquals(resultItem.getFeature().getFeatureId(), 234L);
		assertEquals(resultItem.getFeature().getFeatureType(), "FeatureTypeAirport");
		assertEquals(resultItem.getFeatureMap().getFeatureCode(), 1);
		assertEquals(resultItem.getFeatureMap().getTypeCode(), 2);
		assertEquals(resultItem.getFeatureMap().getFeatureId(), 234L);
	}
}
