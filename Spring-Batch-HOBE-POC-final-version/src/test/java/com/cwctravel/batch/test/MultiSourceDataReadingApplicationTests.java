package com.cwctravel.batch.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwctravel.batch.config.BatchConfiguration;
import com.cwctravel.batch.config.TableCleanTasklet;
import com.cwctravel.batch.config.util.CsvPartitionUtil;
import com.cwctravel.batch.config.util.CsvWriterUtil;
import com.cwctravel.batch.config.util.DerbyWriterUtil;

import junit.framework.TestCase;

/**
 * This class is an end-to-end test for the hotel data import Spring Batch application and runs the whoel application.
 * <p>
 * Loading data from sources set in application.properties and check the exit status of the application.
 * 
 * @author chris.nie
 */
@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class MultiSourceDataReadingApplicationTests extends TestCase {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	/**
	 * End to end test
	 * 
	 * @throws Exception
	 *                   throw exception if any exception encountered when running the application
	 */
	@Test
	public void endToEndTest() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

}
