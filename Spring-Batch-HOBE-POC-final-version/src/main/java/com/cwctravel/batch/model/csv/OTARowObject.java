package com.cwctravel.batch.model.csv;

/**
 * OTARowObject stores data of a row from OTAFeatureType.csv representing mapping relationship
 * between featureId and featureType and Derby feature mapping with typeCode and featureId to featureType.
 * @author chris.nie
 *
 */
public class OTARowObject {

	private long featureId;

	private int featureCode;

	private int typeCode;

	private String featureType;

	public OTARowObject() {
		super();
	}

	/**
	 * Construct a new OTARowObject
	 * @param featureId long featureId
	 * @param featureCode Integer featureCode for Derby featureType mapping 
	 * @param typeCode Integer typeCode for Derby featureType mapping 
	 * @param featureType String featureType corresponding to featureId
	 */
	public OTARowObject(long featureId, int featureCode, int typeCode, String featureType) {
		super();
		this.featureId = featureId;
		this.featureCode = featureCode;
		this.typeCode = typeCode;
		this.featureType = featureType;
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

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

}
