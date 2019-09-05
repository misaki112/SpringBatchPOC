package com.cwctravel.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.retry.annotation.EnableRetry;

/**
 * MultiSourceDataReadingApplication is the main application class of the batch process.
 * <p>
 * Run this class to run the hotel import application.
 * 
 * @author chris.nie
 */
@EnableTask
@EnableRetry
@SpringBootApplication
@EnableBatchProcessing
public class MultiSourceDataReadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiSourceDataReadingApplication.class, args);
	}

}
