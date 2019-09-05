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

import com.cwctravel.batch.config.BatchConfiguration;
import com.cwctravel.batch.config.TableCleanTasklet;
import com.cwctravel.batch.config.processor.derby.DerbyHotelItemProcessor;
import com.cwctravel.batch.config.util.CsvPartitionUtil;
import com.cwctravel.batch.config.util.CsvWriterUtil;
import com.cwctravel.batch.config.util.DerbyWriterUtil;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

import junit.framework.TestCase;

/**
 * This class does basic test on processor for hotel web service response.
 * <p>
 * Checked if data about hotel is correctly formatted after processed.
 * 
 * @author chris.nie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class DerbyHotelProcessorTest extends TestCase {

	@Autowired
	private DerbyHotelItemProcessor hotelItemProcessor;

	@Test
	public void test() throws Exception {
		HotelDescriptiveContent mockInput = new HotelDescriptiveContent();
		mockInput.setHotelName("& Fancy Hotel &");
		mockInput.setHotelCodeContext("TEST");
		mockInput.setHotelCode("code");

		HotelDescriptiveContent outputHotel = hotelItemProcessor.process(mockInput);

		assertNotNull(outputHotel);
		assertEquals(outputHotel.getHotelName(), "Fancy Hotel");
		assertEquals(outputHotel.getHotelId(), "TestFancyHotel");
	}
}
