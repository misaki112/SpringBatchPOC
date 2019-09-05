package com.cwctravel.batch.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import com.cwctravel.batch.config.listener.ItemCountListener;
import com.cwctravel.batch.config.listener.StepCompletionListener;
import com.cwctravel.batch.config.processor.csv.CsvDCItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvHotelItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvImageItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvOTAItemProcessor;
import com.cwctravel.batch.config.processor.csv.CsvRoomItemProcessor;
import com.cwctravel.batch.config.processor.csv.HotelFeatureItemProcessor;
import com.cwctravel.batch.config.processor.csv.HotelPointOfInterestItemProcessor;
import com.cwctravel.batch.config.processor.derby.DerbyHotelItemProcessor;
import com.cwctravel.batch.config.reader.DerbyHotelReader;
import com.cwctravel.batch.config.writer.derbyresponse.DerbyHotelItemWriter;
import com.cwctravel.batch.model.csv.CategoryAndFeatureCombination;
import com.cwctravel.batch.model.csv.DCCombination;
import com.cwctravel.batch.model.csv.DCRowObject;
import com.cwctravel.batch.model.csv.Hotel;
import com.cwctravel.batch.model.csv.HotelAndRateCombination;
import com.cwctravel.batch.model.csv.HotelFeature;
import com.cwctravel.batch.model.csv.HotelRowObject;
import com.cwctravel.batch.model.csv.ImageAndUrlCombination;
import com.cwctravel.batch.model.csv.ImageRowObject;
import com.cwctravel.batch.model.csv.InterestFeature;
import com.cwctravel.batch.model.csv.OTACombination;
import com.cwctravel.batch.model.csv.OTARowObject;
import com.cwctravel.batch.model.csv.RoomRowObject;
import com.cwctravel.batch.model.derby.HotelDescriptiveContent;

/**
 * BatchConfiguration is a class configuring the Spring Batch job of importing hotel information.
 * <p>
 * BatchConfiguration contains hotel import job configuration, step configuration of reading from Derby response. and csv files, configuration for
 * most csv file readers and data source configuration which set output to MySQL database.
 * 
 * @author chris.nie
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Value("${hilton.url}")
	private String hiltonURL;

	@Value("${hyatt.url}")
	private String hyattURL;

	@Value("${fairmontOne.url}")
	private String fairmontOneURL;

	@Value("${fairmontTwo.url}")
	private String fairmontTwoURL;

	@Value("${hotelAndRate.csv.filename}")
	private String hotelFileName;

	@Value("${dcMap.csv.filename}")
	private String dcMapFileName;

	@Value("${hotelFeature.csv.filename}")
	private String hotelFeatureFileName;

	@Value("${hotelPointOfInterest.csv.filename}")
	private String hotelPointOfInterestFileName;

	@Value("${imageAndUrl.csv.filename}")
	private String imageAndUrlFileName;

	@Value("${room.csv.filename}")
	private String roomFileName;

	@Value("${otaFeatureType.csv.filename}")
	private String otaFeatureTypeFileName;

	@Value("${chunkSize}")
	private int chunk_size;

	@Value("${throttleLimit}")
	private int throttleLimit;

	@Value("${hotelTableName}")
	private String hotelTableName;

	@Value("${imageTableName}")
	private String imageTableName;

	@Value("${urlTableName}")
	private String urlTableName;

	@Value("${roomTableName}")
	private String roomTableName;

	@Value("${roomFeatureTableName}")
	private String roomFeatureTableName;

	@Value("${hotelFeatureTableName}")
	private String hotelFeatureTableName;

	@Value("${interestTableName}")
	private String interestTableName;

	@Value("${rateTableName}")
	private String rateTableName;

	@Value("${dcIdMappingTableName}")
	private String dcIdMappingTableName;

	@Value("${dcCategoryTableName}")
	private String dcCategoryTableName;

	@Value("${dcRateTableName}")
	private String dcRateTableName;

	@Value("${featureTypeTableName}")
	private String featureTypeTableName;

	@Value("${DerbyFeatureMappingTableName}")
	private String DerbyFeatureMappingTableName;

	@Value("${database.url}")
	private String databaseUrl;

	@Value("${database.username}")
	private String databaseUsername;

	@Value("${database.password}")
	private String databasePassword;

	private Map<String, Hotel> hotelIdMap = new HashMap<>();

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	/**
	 * @return DataSource a Datasource which has connection to the destination MySQL database
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
		ds.setUrl(databaseUrl);
		ds.setUsername(databaseUsername);
		ds.setPassword(databasePassword);
		return ds;
	}

	/**
	 * @return RestTemplate a RestTemplate for reading from Derby REST response
	 */
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * @return TaskExecutor a TaskExecutor for multi-threadings and partitioning
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	// tag::HotelReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in HotelAndRate2.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in HotelAndRate2.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of HotelAndRate2.csv and turn it into a
	 *         HotelRowObject
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.HotelRowObject} for class HotelRowObject
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<HotelRowObject> hotelRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<HotelRowObject>()//
				.name("HotelRowReader")//
				.resource(new ClassPathResource(hotelFileName))//
				.delimited()//
				.delimiter("|") //
				.includedFields(new Integer[] {1, 7, 39, 55, 61})//
				.names(new String[] {"hotelId", "hotelName", "extendedDescription", "description", "season"})//
				.targetType(HotelRowObject.class)//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.build();

	}

	/**
	 * @return CsvHotelItemProcessor a processor which process data from a HotelRowObject to a HotelAndRateCombination
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.HotelRowObject} for class HotelRowObject and
	 *         {@link com.cwctravel.batch.model.csv.HotelAndRateCombination} for class HotelAndRateCombination
	 */
	@StepScope
	@Bean
	public CsvHotelItemProcessor hotelProcessor() {
		return new CsvHotelItemProcessor(hotelIdMap);
	}
	// end::HotelReaderProcessor[]

	// tag::ImageReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in ImageAndUrl.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in ImageAndUrl.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of ImageAndUrl.csv and turn it into a
	 *         ImageRowObject
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.ImageRowObject} for class ImageRowObject
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<ImageRowObject> imageRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<ImageRowObject>()//
				.name("ImageRowReader")//
				.resource(new ClassPathResource(imageAndUrlFileName))//
				.delimited()//
				.delimiter("|")//
				.includedFields(new Integer[] {4, 8, 9, 17, 18})//
				.names(new String[] {"imageId", "url", "id", "title", "hotelId"})//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.targetType(ImageRowObject.class)//
				.build();
	}

	/**
	 * @return CsvImageItemProcessor a processor which process data from a ImageRowObject to a ImageAndUrlCombination
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.ImageRowObject} for class ImageRowObject and
	 *         {@link com.cwctravel.batch.model.csv.ImageAndUrlCombination} for class ImageAndUrlCombination
	 */
	@Bean
	public CsvImageItemProcessor imageProcessor() {
		return new CsvImageItemProcessor(hotelIdMap);
	}
	// end::ImageReaderProcessor[]

	// tag::RoomReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in Room.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in Room.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of Room.csv and turn it into a
	 *         RoomRowObject
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.RoomRowObject} for class RoomRowObject
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<RoomRowObject> roomRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<RoomRowObject>()//
				.name("RoomRowReader")//
				.resource(new ClassPathResource(roomFileName))//
				.delimited()//
				.includedFields(new Integer[] {0, 4, 6, 7, 16, 17}) //
				.names(new String[] {"id", "hotelId", "name", "description", "categoryId", "featureId"})//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.targetType(RoomRowObject.class)//
				.build();
	}

	/**
	 * @return CsvRoomItemProcessor a processor which process data from a RoomRowObject to a CategoryAndFeatureCombination
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.RoomRowObject} for class RoomRowObject and
	 *         {@link com.cwctravel.batch.model.csv.CategoryAndFeatureCombination} for class CategoryAndFeatureCombination
	 */
	@Bean
	public CsvRoomItemProcessor roomProcessor() {
		return new CsvRoomItemProcessor(hotelIdMap);
	}
	// end::RoomReaderProcessor[]

	// tag::HotelFeatureReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in HotelFeature.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in HotelFeature.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of HotelFeature.csv and turn it into a
	 *         HotelFeature
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.HotelFeature} for class HotelFeature
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<HotelFeature> featureRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<HotelFeature>()//
				.name("HotelFeature")//
				.resource(new ClassPathResource(hotelFeatureFileName))//
				.delimited()//
				.delimiter("|")//
				.includedFields(new Integer[] {4, 5}) //
				.names(new String[] {"hotelId", "featureId"})//
				.targetType(HotelFeature.class)//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.build();
	}

	/**
	 * @return HotelFeatureItemProcessor a processor which process data in a HotelFeature
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.HotelFeature} for class HotelFeature
	 */
	@Bean
	public HotelFeatureItemProcessor featureProcessor() {
		return new HotelFeatureItemProcessor(hotelIdMap);
	}
	// end::HotelFeatureReaderProcessor[]

	// tag::HotelPointOfInterestReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in HotelPointOfInterest.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in HotelPointOfInterest.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of HotelPointOfInterest.csv and turn it
	 *         into a InterestFeature
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.InterestFeature} for class InterestFeature
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<InterestFeature> interestRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<InterestFeature>()//
				.name("InterestFeature")//
				.resource(new ClassPathResource(hotelPointOfInterestFileName))//
				.delimited()//
				.delimiter("|")//
				.includedFields(new Integer[] {4, 5, 6}) //
				.names(new String[] {"hotelId", "featureId", "name"})//
				.targetType(InterestFeature.class)//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.build();
	}

	/**
	 * @return HotelPointOfInterestItemProcessor a processor which process data in a InterestFeature
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.InterestFeature} for class InterestFeature
	 */
	@Bean
	public HotelPointOfInterestItemProcessor interestProcessor() {
		return new HotelPointOfInterestItemProcessor(hotelIdMap);
	}
	// end::HotelPointOfInterestReaderProcessor[]

	// tag::DCMappingReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in DcMap.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in DcMap.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of DcMap.csv and turn it into a DCRowObject
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.DCRowObject} for class DCRowObject
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<DCRowObject> dcRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<DCRowObject>()//
				.name("DCRowObject")//
				.resource(new ClassPathResource(dcMapFileName))//
				.delimited()//
				.includedFields(new Integer[] {4, 5, 7, 16, 18, 31, 37, 39}) //
				.names(new String[] {"vendorId", "vendorHotelId", "cwcHotelId", "externalRateDisplay", "internalRateDisplay", "id", "vendorCategory", "cwcCategory"})//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.targetType(DCRowObject.class)//
				.build();
	}

	/**
	 * @return CsvDCItemProcessor a processor which process data from a DCRowObject to a DCCombination
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.DCRowObject} for class DCRowObject and
	 *         {@link com.cwctravel.batch.model.csv.DCCombination} for class DCCombination
	 */
	@Bean
	public CsvDCItemProcessor dcProcessor() {
		return new CsvDCItemProcessor(hotelIdMap);
	}
	// end::DCMappingReaderProcessor[]

	// tag::ReferenceTableReaderProcessor[]
	/**
	 * @param startIndex
	 *                   Integer the row index in OTAFeatureType.csv the reader should start reading from
	 * @param endIndex
	 *                   Integer the row index in OTAFeatureType.csv after which the reader should not be reading
	 * @return FlatFileItemReader a FlatFileItemReader which reads row from the startIndex to the endIndex of OTAFeatureType.csv and turn it into a
	 *         OTARowObject
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.OTARowObject} for class OTARowObject
	 */
	@StepScope
	@Bean
	public FlatFileItemReader<OTARowObject> otaRowReader(@Value("#{stepExecutionContext[startIndex]}") Integer startIndex, //
			@Value("#{stepExecutionContext[endIndex]}") Integer endIndex) {
		return new FlatFileItemReaderBuilder<OTARowObject>()//
				.name("OTARowObject")//
				.resource(new ClassPathResource(otaFeatureTypeFileName))//
				.delimited()//
				.includedFields(new Integer[] {0, 4, 5, 6}) //
				.names(new String[] {"featureId", "typeCode", "featureCode", "featureType"})//
				.linesToSkip(startIndex)//
				.maxItemCount(endIndex - startIndex + 1)//
				.targetType(OTARowObject.class)//
				.build();
	}

	/**
	 * @return CsvOTAItemProcessor a processor which process data from a OTARowObject to a OTACombination
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.csv.OTARowObject} for class OTARowObject and
	 *         {@link com.cwctravel.batch.model.csv.OTACombination} for class OTACombination
	 */
	@StepScope
	@Bean
	public CsvOTAItemProcessor otaProcessor() {
		return new CsvOTAItemProcessor();
	}
	// end::ReferenceTableReaderProcessor[]

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @return ItemReader a reader which read Json file with data of Hilton branch hotels from Mock Derby REST response and put data into
	 *         HotelDescriptiveContent
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent
	 */
	@Bean
	public ItemReader<HotelDescriptiveContent> restHotelReaderHilton(RestTemplate restTemplate) {
		return new DerbyHotelReader(hiltonURL, restTemplate);
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @return ItemReader a reader which read Json file with data of Hyatt branch hotels from Mock Derby REST response and put data into
	 *         HotelDescriptiveContent
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent
	 */
	@Bean
	public ItemReader<HotelDescriptiveContent> restHotelReaderHyatt(RestTemplate restTemplate) {
		return new DerbyHotelReader(hyattURL, restTemplate);
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @return ItemReader a reader which read Json file with data of Fairmont branch hotels from Mock Derby REST response and put data into
	 *         HotelDescriptiveContent
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent
	 */
	@Bean
	public ItemReader<HotelDescriptiveContent> restHotelReaderFairmontOne(RestTemplate restTemplate) {
		return new DerbyHotelReader(fairmontOneURL, restTemplate);
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @return ItemReader a reader which read Json file with data of Fairmont branch hotels from Mock Derby REST response and put data into
	 *         HotelDescriptiveContent
	 *         <p>
	 *         Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent
	 */
	@Bean
	public ItemReader<HotelDescriptiveContent> restHotelReaderFairmontTwo(RestTemplate restTemplate) {
		return new DerbyHotelReader(fairmontTwoURL, restTemplate);
	}

	// Sequential steps
	/**
	 * @param loadHotelHilton
	 *                                         Step a step loading Hilton branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelAndRateMaster
	 *                                         Step master step which load data from HotelAndRate2.csv to destination MySQL database
	 * @param loadImageAndUrlMaster
	 *                                         Step master step which load data from ImageAndUrl.csv to destination MySQL database
	 * @param loadRoomMaster
	 *                                         Step master step which load data from Room.csv to destination MySQL database
	 * @param loadHotelFeatureMaster
	 *                                         Step master step which load data from HotelFeature.csv to destination MySQL database
	 * @param loadDCMappingMaster
	 *                                         Step master step which load data from DcMap.csv to destination MySQL database
	 * @param loadReferenceTableMaster
	 *                                         Step master step which load data from OTAFeatureType.csv to destination MySQL database
	 * @param loadHotelHyatt
	 *                                         Step a step loading Hyatt branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontOne
	 *                                         Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontTwo
	 *                                         Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @param loadPointOfInterestFeatureMaster
	 *                                         Step master step which load data from HotelPointOfInterest.csv to destination MySQL database
	 * @return Job import hotel data job which run each step sequentially
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Job importHotelJob(Step loadHotelHilton, Step loadHotelAndRateMaster, Step loadImageAndUrlMaster, //
			Step loadRoomMaster, Step loadHotelFeatureMaster, Step loadDCMappingMaster, Step loadReferenceTableMaster, //
			Step loadHotelHyatt, Step loadHotelFairmontOne, Step loadHotelFairmontTwo, //
			Step loadPointOfInterestFeatureMaster) throws URISyntaxException, IOException {
		return jobBuilderFactory.get("SequentialJob")//
				.incrementer(new CustomizeIncrementer(dataSource()))//
				.start(cleanUrlTable())//
				.next(cleanImageTable())//
				.next(cleanRoomFeatureTable())//
				.next(cleanInterestFeatureTable())//
				.next(cleanHotelFeatureTable())//
				.next(cleanRoomTable())//
				.next(cleanRateTable())//
				.next(cleanDCRateMappingTable())//
				.next(cleanDCCategoryMappingTable())//
				.next(cleanDCIdMappingTable())//
				.next(cleanHotelTable())//
				.next(cleanFeatureTypeTable())//
				.next(cleanDerbyFeatureMappingTable())//
				.next(loadReferenceTableMaster)//
				.next(loadHotelHyatt)//
				.next(loadHotelHilton)//
				.next(loadHotelFairmontOne)//
				.next(loadHotelFairmontTwo)//
				.next(loadHotelAndRateMaster)//
				.next(loadDCMappingMaster)//
				.next(loadImageAndUrlMaster)//
				.next(loadRoomMaster)//
				.next(loadHotelFeatureMaster)//
				.next(loadPointOfInterestFeatureMaster)//
				.build();
	}

	// tag:: parallel steps
	/**
	 * @param listener
	 *                                         JobCompletionNotificationListener output log information when job is finished
	 * @param loadHotelAndRateMaster
	 *                                         Step master step which load data from HotelAndRate2.csv to destination MySQL database
	 * @param loadImageAndUrlMaster
	 *                                         Step master step which load data from ImageAndUrl.csv to destination MySQL database
	 * @param loadRoomMaster
	 *                                         Step master step which load data from Room.csv to destination MySQL database
	 * @param loadHotelFeatureMaster
	 *                                         Step master step which load data from HotelFeature.csv to destination MySQL database
	 * @param loadHotelHilton
	 *                                         Step a step loading Hilton branch hotels data from Derby response to destination MySQL database
	 * @param loadDCMappingMaster
	 *                                         Step master step which load data from DcMap.csv to destination MySQL database
	 * @param loadReferenceTableMaster
	 *                                         Step master step which load data from OTAFeatureType.csv to destination MySQL database
	 * @param loadHotelHyatt
	 *                                         Step a step loading Hyatt branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontOne
	 *                                         Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontTwo
	 *                                         Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @param loadPointOfInterestFeatureMaster
	 *                                         Step master step which load data from HotelPointOfInterest.csv to destination MySQL database
	 * @return Job import hotel data job which run steps without dependency in parallel way
	 */
	@Bean
	public Job importHotelDataJob(Step loadHotelAndRateMaster, //
			Step loadImageAndUrlMaster, Step loadRoomMaster, Step loadHotelFeatureMaster, //
			Step loadHotelHilton, Step loadDCMappingMaster, Step loadReferenceTableMaster, //
			Step loadHotelHyatt, Step loadHotelFairmontOne, Step loadHotelFairmontTwo, Step loadPointOfInterestFeatureMaster) {
		return jobBuilderFactory.get("ParallelJob")//
				.incrementer(new CustomizeIncrementer(dataSource()))//
				.start(cleanFirstLevelTableFlow())//
				.next(cleanSecondLevelTableFlow())//
				.next(cleanDCIdMappingTable())//
				.next(cleanHotelTable())//
				.next(loadReferenceTableMaster) //
				.next(loadCsvAndDerbyData(//
						loadCsvData(loadCsvChildData(loadImageAndUrlMaster, loadRoomMaster, loadHotelFeatureMaster, //
								loadDCMappingMaster, loadPointOfInterestFeatureMaster), loadHotelAndRateMaster), //
						loadDerbyData(loadHotelHilton, loadHotelHyatt, //
								loadHotelFairmontOne, loadHotelFairmontTwo)))//
				.build()//
				.build();
	}

	/**
	 * @param loadChildData
	 *                         Flow a flow that load child tables data of reading from csv file in parallel way
	 * @param loadHotelAndRate
	 *                         Step step which load data from HotelAndRate2.csv to destination MySQL database
	 * @return Flow a flow that loads data from reading csv files to destination MySQL database in parallel way
	 */
	private Flow loadCsvData(Flow loadChildData, Step loadHotelAndRate) {
		return new FlowBuilder<SimpleFlow>("csvDataFlow")//
				.start(loadHotelAndRate)//
				.next(loadChildData)//
				.build();
	}

	/**
	 * @param loadCsvData
	 *                      Flow a flow that loads data from reading csv files to destination MySQL database in parallel way
	 * @param loadDerbyData
	 *                      Flow flow that loads data from reading Derby response to destination MySQL database in parallel way
	 * @return Flow a flow that import hotel data in parallel way
	 */
	private Flow loadCsvAndDerbyData(Flow loadCsvData, Flow loadDerbyData) {
		return new FlowBuilder<SimpleFlow>("dataFlow")//
				.start(loadDerbyData)//
				.split(taskExecutor())//
				.add(loadCsvData)//
				.build();
	}

	/**
	 * @param loadHotelHilton
	 *                             Step a step loading Hilton branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelHyatt
	 *                             Step a step loading Hyatt branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontOne
	 *                             Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @param loadHotelFairmontTwo
	 *                             Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 * @return Flow flow that loads data from reading Derby response to destination MySQL database in parallel way
	 */
	private Flow loadDerbyData(Step loadHotelHilton, Step loadHotelHyatt, Step loadHotelFairmontOne, Step loadHotelFairmontTwo) {
		return new FlowBuilder<SimpleFlow>("derbyFlow")//
				.start(loadHotelHilton).split(taskExecutor())//
				.add(singleStepFlow(loadHotelHyatt), singleStepFlow(loadHotelFairmontOne), singleStepFlow(loadHotelFairmontTwo))//
				.build();
	}

	/**
	 * @param loadImageAndUrl
	 *                                   Step step which load data from ImageAndUrl.csv to destination MySQL database
	 * @param loadRoom
	 *                                   Step step which load data from Room.csv to destination MySQL database
	 * @param loadHotelFeature
	 *                                   Step step which load data from HotelFeature.csv to destination MySQL database
	 * @param loadPointOfInterestFeature
	 *                                   Step step which load data from HotelPointOfInterest.csv to destination MySQL database
	 * @param loadDCMapping
	 *                                   Step step which load data from DcMap.csv to destination MySQL database
	 * @return Flow a flow that load child tables data of reading from csv file in parallel way
	 */
	private Flow loadCsvChildData(Step loadImageAndUrl, Step loadRoom, Step loadHotelFeature, Step loadPointOfInterestFeature, Step loadDCMapping) {
		return new FlowBuilder<SimpleFlow>("splitFlow")//
				.start(loadImageAndUrl)//
				.split(taskExecutor())//
				.add(singleStepFlow(loadRoom), singleStepFlow(loadHotelFeature), //
						singleStepFlow(loadPointOfInterestFeature), singleStepFlow(loadDCMapping))//
				.build();
	}

	/**
	 * @param step
	 *             Step a Spring Batch's step
	 * @return Flow a flow with a single step inside
	 */
	private Flow singleStepFlow(Step step) {
		return new FlowBuilder<SimpleFlow>("singelStepFlow")//
				.start(step)//
				.build();
	}

	/**
	 * @return Flow a flow to clean tables with no child tables in parallel way
	 *         <p>
	 *         Namely DCRateMappingTable, RoomFeatureTable, RateTable, FeatureTypeTable, InterestFeatureTable, DerbyFeatureMappingTable,
	 *         HotelFeatureTable, UrlTable
	 */
	private Flow cleanFirstLevelTableFlow() {
		return new FlowBuilder<SimpleFlow>("cleanFirstLevelTableFlow")//
				.start(cleanUrlTable())//
				.split(taskExecutor())//
				.add(singleStepFlow(cleanDCRateMappingTable()), singleStepFlow(cleanRoomFeatureTable()), //
						singleStepFlow(cleanRateTable()), singleStepFlow(cleanFeatureTypeTable()), //
						singleStepFlow(cleanInterestFeatureTable()), //
						singleStepFlow(cleanDerbyFeatureMappingTable()), singleStepFlow(cleanHotelFeatureTable()))//
				.build();
	}

	/**
	 * @return Flow a flow of cleaning tables with dependency to ipm_hotel table and have child tables in parallel way
	 *         <p>
	 *         Namely ImageTable, DCCategoryMappingTable
	 */
	private Flow cleanSecondLevelTableFlow() {
		return new FlowBuilder<SimpleFlow>("cleanSecondLevelTableFlow")//
				.start(cleanRoomTable())//
				.split(taskExecutor())//
				.add(singleStepFlow(cleanImageTable()), singleStepFlow(cleanDCCategoryMappingTable()))//
				.build();
	}
	// end:: parallel steps

	/**
	 * @return Step a step to clean ipm_hotel table
	 */
	@Bean
	public Step cleanHotelTable() {
		TableCleanTasklet task = new TableCleanTasklet(hotelTableName);
		return stepBuilderFactory.get("cleanHotelTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean hotel_image table
	 */
	@Bean
	public Step cleanImageTable() {
		TableCleanTasklet task = new TableCleanTasklet(imageTableName);
		return stepBuilderFactory.get("cleanImageTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean hotel_image_url table
	 */
	@Bean
	public Step cleanUrlTable() {
		TableCleanTasklet task = new TableCleanTasklet(urlTableName);
		return stepBuilderFactory.get("cleanUrlTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean HOTEL_ROOM table
	 */
	@Bean
	public Step cleanRoomTable() {
		TableCleanTasklet task = new TableCleanTasklet(roomTableName);
		return stepBuilderFactory.get("cleanRoomTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean HOTEL_ROOM_FEATURE_TYPE table
	 */
	@Bean
	public Step cleanRoomFeatureTable() {
		TableCleanTasklet task = new TableCleanTasklet(roomFeatureTableName);
		return stepBuilderFactory.get("cleanRoomFeatureTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean HOTEL_FEATURE_TYPE table
	 */
	@Bean
	public Step cleanHotelFeatureTable() {
		TableCleanTasklet task = new TableCleanTasklet(hotelFeatureTableName);
		return stepBuilderFactory.get("cleanHotelFeatureTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean HOTEL_POINT_OF_INTERSET_FEATURE_TYPE table
	 */
	@Bean
	public Step cleanInterestFeatureTable() {
		TableCleanTasklet task = new TableCleanTasklet(interestTableName);
		return stepBuilderFactory.get("cleanInterestFeatureTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean ipm_hotel_rate table
	 */
	@Bean
	public Step cleanRateTable() {
		TableCleanTasklet task = new TableCleanTasklet(rateTableName);
		return stepBuilderFactory.get("cleanRateTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean dc_hotel_id_mapping table
	 */
	@Bean
	public Step cleanDCIdMappingTable() {
		TableCleanTasklet task = new TableCleanTasklet(dcIdMappingTableName);
		return stepBuilderFactory.get("cleanDCIdMappingTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean dc_hotel_category_mapping table
	 */
	@Bean
	public Step cleanDCCategoryMappingTable() {
		TableCleanTasklet task = new TableCleanTasklet(dcCategoryTableName);
		return stepBuilderFactory.get("cleanDCCategoryMappingTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean dc_hotel_rate_mapping table
	 */
	@Bean
	public Step cleanDCRateMappingTable() {
		TableCleanTasklet task = new TableCleanTasklet(dcRateTableName);
		return stepBuilderFactory.get("cleanDCRateMappingTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean FEATURE_TYPE table
	 */
	@Bean
	public Step cleanFeatureTypeTable() {
		TableCleanTasklet task = new TableCleanTasklet(featureTypeTableName);
		return stepBuilderFactory.get("cleanFeatureTypeTable").tasklet(task).build();
	}

	/**
	 * @return Step a step to clean DERBY_FEATURE_ID_MAPPING table
	 */
	@Bean
	public Step cleanDerbyFeatureMappingTable() {
		TableCleanTasklet task = new TableCleanTasklet(DerbyFeatureMappingTableName);
		return stepBuilderFactory.get("cleanDerbyFeatureMappingTable").tasklet(task).build();
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @param writer
	 *                     DerbyHotelItemWriter a writer which write processed HotelDescriptiveContent data to destination MySQL database
	 * @param processor
	 *                     DerbyHotelItemProcessor a processor which formats and fills in data in HotelDescriptiveContent
	 * @return Step a step loading Hilton branch hotels data from Derby response to destination MySQL database
	 */
	@Bean
	public Step loadHotelHilton(RestTemplate restTemplate, DerbyHotelItemWriter writer, DerbyHotelItemProcessor processor) {
		return stepBuilderFactory.get("loadHotelHilton").<HotelDescriptiveContent, HotelDescriptiveContent> chunk(chunk_size)//
				.reader(restHotelReaderHilton(restTemplate))//
				.processor(processor)//
				.writer(writer)//
				// uncomment for multi-threading
				.taskExecutor(taskExecutor())//
				.throttleLimit(throttleLimit)//
				.build();//
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @param writer
	 *                     DerbyHotelItemWriter a writer which write processed HotelDescriptiveContent data to destination MySQL database
	 * @param processor
	 *                     DerbyHotelItemProcessor a processor which formats and fills in data in HotelDescriptiveContent
	 * @return Step a step loading Hyatt branch hotels data from Derby response to destination MySQL database
	 */
	@Bean
	public Step loadHotelHyatt(RestTemplate restTemplate, DerbyHotelItemWriter writer, DerbyHotelItemProcessor processor) {
		return stepBuilderFactory.get("loadHotelHyatt").<HotelDescriptiveContent, HotelDescriptiveContent> chunk(chunk_size)//
				.reader(restHotelReaderHyatt(restTemplate))//
				.processor(processor)//
				.writer(writer)//
				// uncomment for multi-threading
				.taskExecutor(taskExecutor())//
				.throttleLimit(throttleLimit)//
				.build();//
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @param writer
	 *                     DerbyHotelItemWriter a writer which write processed HotelDescriptiveContent data to destination MySQL database
	 * @param processor
	 *                     DerbyHotelItemProcessor a processor which formats and fills in data in HotelDescriptiveContent
	 * @return Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 */
	@Bean
	public Step loadHotelFairmontOne(RestTemplate restTemplate, DerbyHotelItemWriter writer, DerbyHotelItemProcessor processor) {
		return stepBuilderFactory.get("loadHotelFairmontOne").<HotelDescriptiveContent, HotelDescriptiveContent> chunk(chunk_size)//
				.reader(restHotelReaderFairmontOne(restTemplate))//
				.processor(processor)//
				.writer(writer)//
				// uncomment for multi-threading
				.taskExecutor(taskExecutor())//
				.throttleLimit(throttleLimit)//
				.build();//
	}

	/**
	 * @param restTemplate
	 *                     RestTemplate a RestTemplate which helps reading from REST response
	 * @param writer
	 *                     DerbyHotelItemWriter a writer which write processed HotelDescriptiveContent data to destination MySQL database
	 * @param processor
	 *                     DerbyHotelItemProcessor a processor which formats and fills in data in HotelDescriptiveContent
	 * @return Step a step loading Fairmont branch hotels data from Derby response to destination MySQL database
	 */
	@Bean
	public Step loadHotelFairmontTwo(RestTemplate restTemplate, DerbyHotelItemWriter writer, DerbyHotelItemProcessor processor) {
		return stepBuilderFactory.get("loadHotelFairmontTwo").<HotelDescriptiveContent, HotelDescriptiveContent> chunk(chunk_size)//
				.reader(restHotelReaderFairmontTwo(restTemplate))//
				.processor(processor)//
				.writer(writer)//
				// uncomment for multi-threading
				.taskExecutor(taskExecutor())//
				.throttleLimit(throttleLimit)//
				.build();//
	}

	/**
	 * @param hotelRateWriter
	 *                        ItemWriter a writer that write data from HotelAndRateCombination to destination MySQL database
	 * @param hotelRowReader
	 *                        FlatFileItemReader a reader that read from csv file and load data into HotelRowObject
	 * @param hotelProcessor
	 *                        CsvHotelItemProcessor a processor which process data from a HotelRowObject to a HotelAndRateCombination
	 * @return Step a step which load hotel and hotel rate data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadHotelAndRate(ItemWriter<HotelAndRateCombination> hotelRateWriter, //
			FlatFileItemReader<HotelRowObject> hotelRowReader, CsvHotelItemProcessor hotelProcessor) {
		return stepBuilderFactory.get("loadHotelAndRate").<HotelRowObject, HotelAndRateCombination> chunk(chunk_size)//
				.reader(hotelRowReader)//
				.processor(hotelProcessor)//
				.writer(hotelRateWriter)//
				.listener(new StepCompletionListener<HotelAndRateCombination>("loadHotelAndRate"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param imageUrlWriter
	 *                       ItemWriter a writer that write data from ImageAndUrlCombination to destination MySQL database
	 * @param imageRowReader
	 *                       FlatFileItemReader a reader that read from csv file and load data into ImageRowObject
	 * @param imageProcessor
	 *                       CsvImageItemProcessor a processor which process data from a ImageRowObject to a ImageAndUrlCombination
	 * @return Step a step which load hotel image and url data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadImageAndUrl(ItemWriter<ImageAndUrlCombination> imageUrlWriter, FlatFileItemReader<ImageRowObject> imageRowReader, CsvImageItemProcessor imageProcessor) {
		return stepBuilderFactory.get("loadImageAndUrl").<ImageRowObject, ImageAndUrlCombination> chunk(chunk_size)//
				.reader(imageRowReader)//
				.processor(imageProcessor)//
				.writer(imageUrlWriter)//
				.listener(new StepCompletionListener<ImageAndUrlCombination>("loadImageAndUrl"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param roomWriter
	 *                      ItemWriter a writer that write data from CategoryAndFeatureCombination to destination MySQL database
	 * @param roomRowReader
	 *                      FlatFileItemReader a reader that read from csv file and load data into RoomRowObject
	 * @param roomProcessor
	 *                      CsvRoomItemProcessor a processor which process data from a RoomRowObject to a CategoryAndFeatureCombination
	 * @return Step a step which load hotel room and room features data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadRoom(ItemWriter<CategoryAndFeatureCombination> roomWriter, FlatFileItemReader<RoomRowObject> roomRowReader, CsvRoomItemProcessor roomProcessor) {
		return stepBuilderFactory.get("loadRoom").<RoomRowObject, CategoryAndFeatureCombination> chunk(chunk_size)//
				.reader(roomRowReader)//
				.processor(roomProcessor)//
				.writer(roomWriter)//
				.listener(new StepCompletionListener<CategoryAndFeatureCombination>("loadRoom"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param featureWriter
	 *                         ItemWriter a writer that write data from HotelFeature to destination MySQL database
	 * @param featureRowReader
	 *                         FlatFileItemReader a reader that read from csv file and load data into HotelFeature
	 * @param featureProcessor
	 *                         HotelFeatureItemProcessor a processor which format and fill in data in HotelFeature
	 * @return Step a step which load hotel features data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadHotelFeature(ItemWriter<HotelFeature> featureWriter, FlatFileItemReader<HotelFeature> featureRowReader, HotelFeatureItemProcessor featureProcessor) {
		return stepBuilderFactory.get("loadFeature").<HotelFeature, HotelFeature> chunk(chunk_size)//
				.reader(featureRowReader)//
				.processor(featureProcessor)//
				.writer(featureWriter)//
				.listener(new StepCompletionListener<HotelFeature>("loadHotelFeature"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param featureWriter
	 *                         ItemWriter a writer that write data from InterestFeature to destination MySQL database
	 * @param featureRowReader
	 *                         FlatFileItemReader a reader that read from csv file and load data into InterestFeature
	 * @param featureProcessor
	 *                         HotelPointOfInterestItemProcessor a processor which format and fill in data in InterestFeature
	 * @return Step a step which load hotel point of interest features data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadPointOfInterestFeature(ItemWriter<InterestFeature> featureWriter, FlatFileItemReader<InterestFeature> featureRowReader, HotelPointOfInterestItemProcessor featureProcessor) {
		return stepBuilderFactory.get("loadPointOfInterestFeature").<InterestFeature, InterestFeature> chunk(chunk_size)//
				.reader(featureRowReader)//
				.processor(featureProcessor)//
				.writer(featureWriter)//
				.listener(new StepCompletionListener<InterestFeature>("loadPointOfInterestFeature"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param dcMappingWriter
	 *                        ItemWriter a writer that write data from DCCombination to destination MySQL database
	 * @param dcRowReader
	 *                        FlatFileItemReader a reader that read from csv file and load data into DCRowObject
	 * @param dcProcessor
	 *                        CsvDCItemProcessor a processor which process data from DCRowObject to DCCombination
	 * @return Step a step which load hotel dc_id_mapping, dc_rate_mapping and dc_category_mapping data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadDCMapping(ItemWriter<DCCombination> dcMappingWriter, FlatFileItemReader<DCRowObject> dcRowReader, CsvDCItemProcessor dcProcessor) {
		return stepBuilderFactory.get("loadDCMapping").<DCRowObject, DCCombination> chunk(chunk_size)//
				.reader(dcRowReader)//
				.processor(dcProcessor)//
				.writer(dcMappingWriter)//
				.listener(new StepCompletionListener<DCCombination>("loadDCMapping"))//
				.listener(new ItemCountListener())//
				.build();//
	}

	/**
	 * @param otaWriter
	 *                     ItemWriter a writer that write data from OTACombination to destination MySQL database
	 * @param otaRowReader
	 *                     FlatFileItemReader a reader that read from csv file and load data into OTARowObject
	 * @param otaProcessor
	 *                     CsvOTAItemProcessor a processor which process data from OTARowObject to OTACombination
	 * @return Step a step which load hotel feature Id an feature type mapping data from csv file to destination MySQL database
	 */
	@Bean
	public Step loadReferenceTable(ItemWriter<OTACombination> otaWriter, FlatFileItemReader<OTARowObject> otaRowReader, CsvOTAItemProcessor otaProcessor) {
		return stepBuilderFactory.get("loadReferenceTable").<OTARowObject, OTACombination> chunk(chunk_size)//
				.reader(otaRowReader)//
				.processor(otaProcessor)//
				.writer(otaWriter)//
				.listener(new StepCompletionListener<DCCombination>("loadReferenceTable"))//
				.listener(new ItemCountListener())//
				.build();//
	}
}