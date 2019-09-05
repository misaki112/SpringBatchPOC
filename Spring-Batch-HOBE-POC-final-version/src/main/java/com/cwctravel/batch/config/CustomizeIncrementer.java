package com.cwctravel.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * CustomizeIncrementer is a JobParametersIncrementer which enable restarting failed job from the step of failure and create new job execution if
 * previous job succeed.
 * <p>
 * CustomizeIncrementer will increase job parameter, namely runId, and start a new job execution if the previous job execution has ExitStatus of
 * COMPLETE or UNKNOWN and keep runId the same if the previous job has ExitStatus of FAILED.
 * 
 * @author chris.nie
 */
public class CustomizeIncrementer implements JobParametersIncrementer {

	private JdbcTemplate template;

	/**
	 * Construct a CustomizeIncrementer given a DataSource
	 * 
	 * @param dataSource
	 *                   DataSource the destination DataSource where META-DATA of the batch process is in
	 */
	public CustomizeIncrementer(DataSource dataSource) {
		super();
		this.template = new JdbcTemplate(dataSource);
	}

	/**
	 * Use JdbcTemplate with the connection to DataSource with META-DATA of the batch process, query for the ExitStatus of the previous job execution.
	 * Increase unId if the previous job execution has ExitStatus of COMPLETE or UNKNOWN and keep runId the same if the previous job has ExitStatus of
	 * FAILED
	 */
	@Override
	public JobParameters getNext(JobParameters parameters) {
		if(parameters == null || parameters.isEmpty()) {
			return new JobParametersBuilder().addLong("run.id", 1L).toJobParameters();
		}
		String getExitCode = "SELECT EXIT_CODE FROM batch_job_execution ORDER BY JOB_EXECUTION_ID DESC LIMIT 1";
		String exitCode = template.queryForObject(getExitCode, String.class);
		long id = parameters.getLong("run.id", 1L);
		if(exitCode.equals("COMPLETED") || exitCode.equals("UNKNOWN")) {
			id++;
		}
		return new JobParametersBuilder().addLong("run.id", id).toJobParameters();
	}

}
