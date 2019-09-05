package com.cwctravel.batch.model.derby;

/**
 * Feature stores mapping relation of featureId and featureType from csv file.
 * @author chris.nie
 *
 */
public class Feature {

	private long featureId;

	private String featureType;

	public Feature() {

	}

	/**
	 * Construct a new Feature
	 * @param featureId long featureId for a feature
	 * @param featureType String description about that feature which maps to the featureId
	 */
	public Feature(long featureId, String featureType) {
		this.featureId = featureId;
		this.featureType = featureType;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}


}
