package com.cwctravel.batch.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
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
import com.cwctravel.batch.model.csv.DCRowObject;
import com.cwctravel.batch.model.csv.HotelFeature;
import com.cwctravel.batch.model.csv.HotelRowObject;
import com.cwctravel.batch.model.csv.ImageRowObject;
import com.cwctravel.batch.model.csv.InterestFeature;
import com.cwctravel.batch.model.csv.OTARowObject;
import com.cwctravel.batch.model.csv.RoomRowObject;

import junit.framework.TestCase;

/**
 * This class tests all readers in csv files importing steps.
 * <p>
 * Generate temporary files with same format as actual input files with only one rwo of data for efficiency.
 * 
 * @author chris.nie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration(classes = {BatchConfiguration.class, TableCleanTasklet.class, //
		CsvPartitionUtil.class, CsvWriterUtil.class, DerbyWriterUtil.class})
@TestPropertySource(locations = "classpath:application.properties")
public class CsvReaderTests extends TestCase {

	@Autowired
	private FlatFileItemReader<HotelRowObject> hotelRowReader;

	@Autowired
	private FlatFileItemReader<ImageRowObject> imageRowReader;

	@Autowired
	private FlatFileItemReader<RoomRowObject> roomRowReader;

	@Autowired
	private FlatFileItemReader<HotelFeature> featureRowReader;

	@Autowired
	private FlatFileItemReader<InterestFeature> interestRowReader;

	@Autowired
	private FlatFileItemReader<DCRowObject> dcRowReader;

	@Autowired
	private FlatFileItemReader<OTARowObject> otaRowReader;

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Bean
	public StepExecution getStepExection() {
		StepExecution execution = MetaDataInstanceFactory.createStepExecution();
		execution.getExecutionContext().putString("startIndex", "0");
		execution.getExecutionContext().putString("endIndex", "1");
		return execution;
	}

	@Test
	public void csvHotelRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("﻿632667836|HyattRegencyBuffaloHotelAndConferenceCenter|301|2017-06-01 12:39:12.627|1|0|2019-07-09 07:02:37.667|Hyatt Regency Buffalo Hotel And Conference Center|Hyatt Regency|3.0|BUFFALO|TWO FOUNTAIN PLAZA|||NULL|1-716-856-1234|NULL|0|0|NULL|NULL|NULL|NULL|USA,CAN|NULL|NULL|BOOKABLE|14202|BUFFALO|New York|United States|42.888338000000|-78.874701000000|15.00|12.00|0|0|1|1|Historical landmark hotel located in the heart of the theater, financial and entertainment district of downtown Buffalo.  Featuring 396 recently renovated guestrooms,two unique restaurants and fitness room.  Hotel is connected to the Buffalo Convention Cen|2019-07-09 07:02:37.667|HYATT_HR|3|632667837|1706011239688-6408|301|2017-06-01 12:39:12.703|1|0|2017-06-01 12:39:12.703|HyattRegencyBuffaloHotelAndConferenceCenter|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|NULL|hobehotels.spam@costcotravel.com|Contracted Rate|1|9|8|NULL|NULL|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|0|0.00|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NET-NET|NULL|NULL|GROSS|0|0.00|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|HOHHRBUFFA|NULL|NULL|NULL|HBSIV2|HBSIV2|HyattRegencyBuffaloHotelAndConferenceCenter,57051,HOHHRBUFFA|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NET-NET|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA|NULL|NULL|NULL|NULL");
			writer.close();
			hotelRowReader.setResource(new FileSystemResource(testFile));
			hotelRowReader.open(getStepExection().getExecutionContext());

		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HotelRowObject item = hotelRowReader.read();
		assertNotNull(item);
		assertEquals(item.getHotelId(), "HyattRegencyBuffaloHotelAndConferenceCenter");
		assertEquals(item.getHotelName(), "Hyatt Regency Buffalo Hotel And Conference Center");
		assertEquals(item.getSeason(), "HyattRegencyBuffaloHotelAndConferenceCenter_HOHHRBUFFA");
		assertEquals(item.getExtendedDescription(), "Historical landmark hotel located in the heart of the theater, financial and entertainment district of downtown Buffalo.  Featuring 396 recently renovated guestrooms,two unique restaurants and fitness room.  Hotel is connected to the Buffalo Convention Cen");
		assertEquals(item.getDescription(), "Contracted Rate");

		hotelRowReader.close();
	}

	@Test
	public void csvImageRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("23584518|2019-04-04 07:45:12.380|2019-04-04 07:45:12.380|0|23584518|5|0|0|https://media.iceportal.com/121896/photos/64468059_L.jpg|23584518|2019-04-04 07:45:12.380|2019-04-04 07:45:12.380|0|DERBY|NULL|9|NULL|Suite|HyattGildHallAThompsonHotelLGATG");
			writer.close();
			imageRowReader.setResource(new FileSystemResource(testFile));
			imageRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ImageRowObject item = imageRowReader.read();
		assertNotNull(item);
		assertEquals(item.getHotelId(), "HyattGildHallAThompsonHotelLGATG");
		assertEquals(item.getImageId(), 23584518);
		assertEquals(item.getId(), 23584518);
		assertEquals(item.getTitle(), "Suite");
		assertEquals(item.getUrl(), "https://media.iceportal.com/121896/photos/64468059_L.jpg");

		imageRowReader.close();
	}

	@Test
	public void csvRoomRowReaderrTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("1406,23:18.6,23:18.6,0,HiltonHamptonInnAugustaWashingtonRdI20,EXGL,1 FULL BED EXTERIOR ENTRY NONSMO,1 FULL BED EXTERIOR ENTRY NONSMOKING...HDTV/FREE WI-FI/HOT BREAKFAST INCLUDED...WORK AREA,1,2,00:00.0,00:00.0,5876,23:18.6,23:18.6,0,1406,1425");
			writer.close();
			roomRowReader.setResource(new FileSystemResource(testFile));
			roomRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		RoomRowObject item = roomRowReader.read();

		assertNotNull(item);
		assertEquals(item.getCategoryId(), 1406);
		assertEquals(item.getId(), 1406);
		assertEquals(item.getHotelId(), "HiltonHamptonInnAugustaWashingtonRdI20");
		assertEquals(item.getFeatureId(), 1425);
		assertEquals(item.getName(), "1 FULL BED EXTERIOR ENTRY NONSMO");
		assertEquals(item.getDescription(), "1 FULL BED EXTERIOR ENTRY NONSMOKING...HDTV/FREE WI-FI/HOT BREAKFAST INCLUDED...WORK AREA");

		roomRowReader.close();
	}

	@Test
	public void csvFeatureRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("1266|2017-06-01 11:23:18.573|2017-06-01 11:23:18.573|0|HiltonHamptonInnAugustaWashingtonRdI20|1178|1");
			writer.close();
			featureRowReader.setResource(new FileSystemResource(testFile));
			featureRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HotelFeature item = featureRowReader.read();

		assertNotNull(item);
		assertEquals(item.getFeatureId(), 1178);
		assertEquals(item.getHotelId(), "HiltonHamptonInnAugustaWashingtonRdI20");

		featureRowReader.close();
	}

	@Test
	public void csvInterestRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("1036|2017-06-01 11:23:18.590|2017-06-01 11:23:18.590|0|HiltonHamptonInnAugustaWashingtonRdI20|1737|Bush Field|0");
			writer.close();
			interestRowReader.setResource(new FileSystemResource(testFile));
			interestRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		InterestFeature item = interestRowReader.read();

		assertNotNull(item);
		assertEquals(item.getFeatureId(), 1737);
		assertEquals(item.getHotelId(), "HiltonHamptonInnAugustaWashingtonRdI20");

		interestRowReader.close();
	}

	@Test
	public void csvDcRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("1026,06:17.3,06:17.3,0,OUTR,PWR,OUTRPWR,MauiOutriggerPalmsWailea,Outrigger Palms at Wailea,2405,15:22.4,15:22.4,0,OUTR,NALO,OUTRNALO,Aloha Rate,,Aloha Rate,0,NULL,PWR,0,1,NULL,NULL,\"USA,CAN\",NULL,NULL,0,NULL,1651,14:42.0,14:42.0,0,OUTR,PWR,1GV,OUTRPWR1GV,One-Bedroom Garden Villa");
			writer.close();
			dcRowReader.setResource(new FileSystemResource(testFile));
			dcRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		DCRowObject item = dcRowReader.read();

		assertNotNull(item);
		assertEquals(item.getCwcCategory(), "One-Bedroom Garden Villa");
		assertEquals(item.getCwcHotelId(), "MauiOutriggerPalmsWailea");
		assertEquals(item.getExternalRateDisplay(), "Aloha Rate");
		assertEquals(item.getId(), "1651");
		assertEquals(item.getExternalRateDisplay(), "Aloha Rate");
		assertEquals(item.getVendorCategory(), "1GV");
		assertEquals(item.getVendorHotelId(), "PWR");
		assertEquals(item.getVendorId(), "OUTR");

		dcRowReader.close();
	}

	@Test
	public void csvOtaRowReaderTest() throws Exception {
		try {
			File testFile = testFolder.newFile();
			PrintWriter writer = new PrintWriter(testFile, "UTF-8");
			writer.println("1001,33:38.3,33:38.3,0,1,1,24-hour front desk");
			writer.close();
			otaRowReader.setResource(new FileSystemResource(testFile));
			otaRowReader.open(getStepExection().getExecutionContext());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		OTARowObject item = otaRowReader.read();

		assertNotNull(item);
		assertEquals(item.getFeatureCode(), 1);
		assertEquals(item.getFeatureId(), 1001);
		assertEquals(item.getFeatureType(), "24-hour front desk");
		assertEquals(item.getTypeCode(), 1);

		otaRowReader.close();
	}

}
