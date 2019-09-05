package com.cwctravel.batch.model.derby;

/**
 * Application is a wrapper for Envelope which is the most outer level of Json response from Derby.
 * <p>
 * Application helps Spring Batch's restTemplate to read Json file. 
 * Please see {@link com.cwctravel.batch.model.derby.Envelope} for class Envelope
 * @author chris.nie
 *
 */

public class Application {

	private Envelope Envelope;

	public Envelope getEnvelope() {
		return Envelope;
	}

	public void setEnvelope(Envelope envelope) {
		Envelope = envelope;
	}
}
