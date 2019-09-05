package com.cwctravel.batch.config.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * StepCompletionListener is a ChunkListener which will output log information when a chunk of 
 * the step is finished.
 * @author chris.nie
 *
 * @param <T> Class of the writer input of the step.
 */
public class StepCompletionListener<T> implements ChunkListener{

	private String stepName;

	private static final Logger log = LoggerFactory.getLogger(StepCompletionListener.class);

	/**
	 * Constructor generating a new StepCompletionListener given the name of the step it is auditing to.
	 * @param stepName String the name of the step the listener is auditing to.
	 */
	public StepCompletionListener(String stepName) {
		super();
		this.stepName = stepName;
	}


	/**
	 * output information to the log file after chunk in the step is finished
	 */
	public void afterChunk(ChunkContext context) {
		log.info("Finish loading a chunk in step " + stepName);		
	}

	public void beforeChunk(ChunkContext context) {

	}


	public void afterChunkError(ChunkContext context) {

	}

}
