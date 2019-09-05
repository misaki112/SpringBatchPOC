package com.cwctravel.batch.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
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

import junit.framework.TestCase;

/**
 * This test tests each single step in importing csv files reading from a smaller set of data for efficiency and writing the actual database the
 * application is connecting to.
 * <p>
 * Name of file reading from can be set in value of TestPropertySource annotation
 * 
 * @author chris.nie
 */
@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties", //
		properties = {"hotelAndRate.csv.filename=HotelAndRateSmall.csv", "dcMap.csv.filename=DcMapSmall.csv", //
				"hotelFeature.csv.filename=FeatureSmall.csv", "hotelPointOfInterest.csv.filename=POISmall.csv", //
				"imageAndUrl.csv.filename=ImageAndUrlSmall.csv", "room.csv.filename=RoomSmall.csv", //
				"chunkSize=100", "gridSize=2"})
public class CsvImportStepTests extends TestCase {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	/**
	 * Clean all tables in destination database
	 */
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

	/**
	 * Test importing csv file with hotel and hotel rate data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void hotelAndRateImportTest() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with featureId and feature description mapping data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void referenceTableImportTest() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadReferenceTable.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with room and room features data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void roomImportTest() throws Exception {
		jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		jobLauncherTestUtils.launchStep("loadReferenceTable.master");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadRoom.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with image and image url data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void imageAndUrlImportTest() throws Exception {
		jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadImageAndUrl.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with hotel feature data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void hotelFeatureImportTest() throws Exception {
		jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		jobLauncherTestUtils.launchStep("loadReferenceTable.master");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadFeature.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with points of interest around hotels data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void pointOfInterestImportTest() throws Exception {
		jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		jobLauncherTestUtils.launchStep("loadReferenceTable.master");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadPointOfInterestFeature.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

	/**
	 * Test importing csv file with dc mapping data
	 * 
	 * @throws Exception
	 *                   throw exception encountered when running the step
	 */
	@Test
	public void dcMapImportTest() throws Exception {
		jobLauncherTestUtils.launchStep("loadHotelAndRate.master");
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadDCMapping.master");
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}
}
