package com.cwctravel.batch.model.csv;

import com.cwctravel.batch.model.derby.Feature;

/**
 * OTACombination stores information about featureId and featureType mapping from csv file.
 * <p>
 * OTACombination is an intermediate class that helps writing to featuer_type table and DERBY_FEATURE_ID_MAPPING
 * table in destination MySQL database.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Feature} for class Feature 
 * and {@link com.cwctravel.batch.model.csv.DerbyFeatureMap} for class DerbyFeatureMap.
 * @author chris.nie
 *
 */
public class OTACombination {

	private Feature feature;

	private DerbyFeatureMap featureMap;

	public OTACombination() {
	}

	/**
	 * Construct a new OTACombination
	 * @param feature Feature represent feature of a facility or service a hotel provide
	 * @param featureMap DerbyFeatureMap represent mapping relationship of featureId and featureType
	 * for a Derby Hotel feature
	 */
	public OTACombination(Feature feature, DerbyFeatureMap featureMap) {
		this.feature = feature;
		this.featureMap = featureMap;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public DerbyFeatureMap getFeatureMap() {
		return featureMap;
	}

	public void setFeatureMap(DerbyFeatureMap featureMap) {
		this.featureMap = featureMap;
	}


}
