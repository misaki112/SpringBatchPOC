package com.cwctravel.batch.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * TableCleanTasklet is a Tasklet step that delete all rows of a given table in destination MySQL database.
 * 
 * @author chris.nie
 */
@StepScope
public class TableCleanTasklet implements Tasklet {

	private String tableName;

	/**
	 * Constructor generating a TableCleanTasklet given the name of the table to be dropped
	 * 
	 * @param tableName
	 *                  String the name of the table to be dropped
	 */
	public TableCleanTasklet(String tableName) {
		super();
		this.tableName = tableName;
	}

	/**
	 * Delete all rows of the table with name as the field tableName
	 */
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String connectionURL = "jdbc:mysql://localhost:3306/versionedHoteldb?useSSL=false";
		Connection con = null;
		Statement stmt = null;
		con = DriverManager.getConnection(connectionURL, "root", "Costco2019");
		stmt = con.createStatement();
		String query = "DELETE FROM " + tableName;
		stmt.execute(query);
		stmt.close();
		con.close();
		return RepeatStatus.FINISHED;
	}
}
