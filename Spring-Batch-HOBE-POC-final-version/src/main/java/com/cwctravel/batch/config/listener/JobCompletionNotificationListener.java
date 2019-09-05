package com.cwctravel.batch.config.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * JobCompletionNotificationListener is a listener that will print out information if
 * the import hotel job finished and has ExitStatus of COMPLETED
 * @author chris.nie
 *
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	/**
	 * Print out information if the import hotel job finished and has ExitStatus of COMPLETED
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("!!! JOB FINISHED!");
		}
	}
}