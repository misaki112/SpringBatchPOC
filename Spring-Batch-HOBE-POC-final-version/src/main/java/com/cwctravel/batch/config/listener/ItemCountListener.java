package com.cwctravel.batch.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * ItemCountListener output information about how many records are being read, processed and written
 * into a log file every time a chunk of the step using the listener is finished
 * @author chris.nie
 *
 */
public class ItemCountListener implements ChunkListener {

	private static final Logger log = LoggerFactory.getLogger(ItemCountListener.class);

	@Override
	public void beforeChunk(ChunkContext context) {
	}

	/**
	 * Output information about how many records are being read, processed and written into a log file
	 */
	@Override
	public void afterChunk(ChunkContext context) {

		int count = context.getStepContext().getStepExecution().getWriteCount();
		log.info("ItemCount: " + count);
	}

	@Override
	public void afterChunkError(ChunkContext context) {
	}
}
