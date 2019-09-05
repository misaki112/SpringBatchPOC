package com.cwctravel.batch.model.csv;

/**
 * DerbyFeatureMap stores mapping relationship between featureCode, typeCode and fatureId for Derby 
 * features. The data is from csv file, namely OTAFeatureType.csv
 * <p>
 * DerbyFeatureMap helps writing to DERBY_FEATURE_ID_MAPPING table which is being used for querying
 * featureId with featureCode and typeCode of Derby features such as Service, Amenity and Recreation.
 * @author chris.nie
 *
 */
public class DerbyFeatureMap {

	private long featureId;

	private int featureCode;

	private int typeCode;

	public DerbyFeatureMap() {

	}

	/**
	 * Construct a new DerbyFeatureMap
	 * @param featureId long featureId of the feature mapping to featureType which describe the feature
	 * @param featureCode Integer featureCode of the feature
	 * @param typeCode Integer typeCode of the feature
	 */
	public DerbyFeatureMap(long featureId, int featureCode, int typeCode) {
		this.featureId = featureId;
		this.featureCode = featureCode;
		this.typeCode = typeCode;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public int getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(int featureCode) {
		this.featureCode = featureCode;
	}

}
