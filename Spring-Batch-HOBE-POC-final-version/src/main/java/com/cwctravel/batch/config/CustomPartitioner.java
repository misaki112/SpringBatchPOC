package com.cwctravel.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

/**
 * CustomPartitioner is a Partitioner which tells a master step how to assign work of
 * loading data from a csv file evenly to its slave steps.
 * @author chris.nie
 *
 */
public class CustomPartitioner implements Partitioner {

	private long fileLength;

	/**
	 * Construct a CustomPartitioner given length of the csv file the master step is reading from
	 * @param fileLength long length of the csv file the master step is reading from
	 */
	public CustomPartitioner(long fileLength) {
		super();
		this.fileLength = fileLength;
	}

	/**
	 * Assign work of loading data from a csv file evenly to slave steps and put the startIndex 
	 * and endIndex of each chunk into step execution context so that slave steps' readers know 
	 * which chunk of the csv file they should process by using that META-DATA 
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitionMap = new HashMap<>();

		int chunkSize = (int)(fileLength / gridSize) + 1;

		int startIndex = 0;
		int endIndex = chunkSize;

		for(int i = 0; i < gridSize; i++) {
			ExecutionContext context = new ExecutionContext();
			context.putInt("startIndex", startIndex);
			context.putInt("endIndex", (int)Math.min(endIndex, fileLength));

			startIndex = endIndex + 1;
			endIndex += chunkSize;

			partitionMap.put("Thread:-" + i, context);
		}

		return partitionMap;
	}

}
