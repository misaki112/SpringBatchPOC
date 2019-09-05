package com.cwctravel.batch.config.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.cwctravel.batch.config.CustomPartitioner;

/**
 * CsvPartitionUtil is the util class configuring master steps and partition handler for partitioning the batch process.
 * <p>
 * Purpose of the util class is to shorten the length of BatchConfiguration class for readability. BatchConfiguration class depends on configurations
 * implemented in CsvPartitionUtil.
 * <p>
 * Please see {@link com.cwctravel.batch.config.BatchConfiguration} for class BatchConfiguration
 * 
 * @author chris.nie
 */
@Configuration
public class CsvPartitionUtil {

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${referencetable.location}")
	private String referenceTableLocation;

	@Value("${hotelAndRate.location}")
	private String hotelAndRateTableLocation;

	@Value("${dcMap.location}")
	private String dcMapTableLocation;

	@Value("${imageAndUrl.location}")
	private String imageAndUrlTableLocation;

	@Value("${room.location}")
	private String roomTableLocation;

	@Value("${feature.location}")
	private String featureTableLocation;

	@Value("${pointOfInterest.location}")
	private String pointOfInterestTableLocation;

	@Value("${gridSize}")
	private int gridSize;

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	// tag::master steps

	/**
	 * loadReferenceTableMaster return master step of processing feature mapping related data from csv file, namely OTAFeatureType.csv, given the
	 * slave step which process feature mapping related data
	 * 
	 * @param loadReferenceTable
	 *                           Step Slave step processing feature mapping related data
	 * @return Step Master Step which assign work of loading feature mapping related data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadReferenceTableMaster(Step loadReferenceTable) throws URISyntaxException, IOException {
		Path path = Paths.get(referenceTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadReferenceTable.getName() + ".master")//
				.partitioner(loadReferenceTable.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadReferenceTable))//
				.build();
	}

	/**
	 * loadHotelAndRateMaster return master step of processing hotel and hotel rate data from csv file, namely HotelAndRate2.csv, given the slave step
	 * which process hotel and hotel rate data
	 * 
	 * @param loadHotelAndRate
	 *                         Step Slave step processing hotel and hotel rate data
	 * @return Step Master Step which assign work of loading hotel and hotel rate data from csv file to slave steps
	 * @throws URISyntaxExceptionthrows
	 *                                  exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                                  throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadHotelAndRateMaster(Step loadHotelAndRate) throws URISyntaxException, IOException {
		Path path = Paths.get(hotelAndRateTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadHotelAndRate.getName() + ".master")//
				.partitioner(loadHotelAndRate.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadHotelAndRate))//
				.build();
	}

	/**
	 * loadDCMappingMaster return master step of processing dc_mapping data from csv file, namely DcMap.csv, given the slave step which process hotel
	 * and hotel rate data
	 * 
	 * @param loadDCMapping
	 *                      Step Slave step processing dc_mapping data
	 * @return Step Master Step which assign work of loading dc_mapping data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadDCMappingMaster(Step loadDCMapping) throws URISyntaxException, IOException {
		Path path = Paths.get(dcMapTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadDCMapping.getName() + ".master")//
				.partitioner(loadDCMapping.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadDCMapping))//
				.build();
	}

	/**
	 * loadImageAndUrlMaster return master step of processing hotel image and url data from csv file, namely ImageAndUrl.csv, given the slave step
	 * which process image and url data
	 * 
	 * @param loadImageAndUrl
	 *                        Step Slave step processing image and url data
	 * @return Step Master Step which assign work of loading image and url data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadImageAndUrlMaster(Step loadImageAndUrl) throws URISyntaxException, IOException {
		Path path = Paths.get(imageAndUrlTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadImageAndUrl.getName() + ".master")//
				.partitioner(loadImageAndUrl.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadImageAndUrl))//
				.build();
	}

	/**
	 * loadRoomMaster return master step of processing room data from csv file, namely Room.csv, given the slave step which process room data
	 * 
	 * @param loadRoom
	 *                 Step Slave step processing room data
	 * @return Step Master Step which assign work of loading room data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadRoomMaster(Step loadRoom) throws URISyntaxException, IOException {
		Path path = Paths.get(roomTableLocation);
		long fileLength = Files.lines(path, Charset.forName("Cp1252")).count();
		return stepBuilderFactory.get(loadRoom.getName() + ".master")//
				.partitioner(loadRoom.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadRoom))//
				.build();
	}

	/**
	 * loadHotelFeatureMaster return master step of processing services offer by hotels data from csv file, namely HotelFeature.csv, given the slave
	 * step which process services offer by hotels data
	 * 
	 * @param loadHotelFeature
	 *                         Step Slave step processing services offer by hotels data
	 * @return Step Master Step which assign work of loading services offer by hotels data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadHotelFeatureMaster(Step loadHotelFeature) throws URISyntaxException, IOException {
		Path path = Paths.get(featureTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadHotelFeature.getName() + ".master")//
				.partitioner(loadHotelFeature.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadHotelFeature))//
				.build();
	}

	/**
	 * loadPointOfInterestFeatureMaster return master step of processing places of interest around hotels data from csv file, namely
	 * HotelPointOfInterest.csv, given the slave step which process places of interest around hotels data
	 * 
	 * @param loadPointOfInterestFeature
	 *                                   Step Slave step processing places of interest around hotels data
	 * @return Step Master Step which assign work of loading places of interest around hotels data from csv file to slave steps
	 * @throws URISyntaxException
	 *                            throws exception if the URI syntax of csv files is bad
	 * @throws IOException
	 *                            throws exception if error occurs when reading from csv files
	 */
	@Bean
	public Step loadPointOfInterestFeatureMaster(Step loadPointOfInterestFeature) throws URISyntaxException, IOException {
		Path path = Paths.get(pointOfInterestTableLocation);
		long fileLength = Files.lines(path).count();
		return stepBuilderFactory.get(loadPointOfInterestFeature.getName() + ".master")//
				.partitioner(loadPointOfInterestFeature.getName(), new CustomPartitioner(fileLength))//
				.partitionHandler(handler(loadPointOfInterestFeature))//
				.build();
	}

	// end::master steps

	/**
	 * handler returns a handler which is used in master steps for setting thread number when partitioning.
	 * 
	 * @param slave
	 *              Step Slave step which will be assigned work by master step of loading a chunk of data
	 * @return PartitionHandler handler which is used in master steps for partitioning
	 */
	public PartitionHandler handler(Step slave) {
		TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
		handler.setGridSize(gridSize);
		handler.setTaskExecutor(taskExecutor());
		handler.setStep(slave);
		try {
			handler.afterPropertiesSet();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

}
