package com.cwctravel.batch.config.processor.csv;

import org.springframework.batch.item.ItemProcessor;

import com.cwctravel.batch.model.csv.DerbyFeatureMap;
import com.cwctravel.batch.model.csv.OTACombination;
import com.cwctravel.batch.model.csv.OTARowObject;
import com.cwctravel.batch.model.derby.Feature;

/**
 * CsvOTAItemProcessor process data from OTARowObject to OTACombination.
 * <p>
 * CsvOTAItemProcessor extract fields of OTARowObject and generate new OTACombination using those fields.
 * @author chris.nie
 *
 */
public class CsvOTAItemProcessor implements ItemProcessor<OTARowObject, OTACombination> {

	/**
	 * Extract data from fields in OTARowObject and use them to build a new OTACombination
	 * 
	 * @param  row OTARowObject representing a row of feature mapping data in csv file to be processed
	 * @return OTACombination Intermediate class with information of Feature and DerbyFeatureMap that helps 
	 * writing to FEATURE_TYPE table and DERBY_FEATURE_ID_MAPPING table in destination MySQL database.
	 */
	public OTACombination process(OTARowObject row) throws Exception {

		long featureId = row.getFeatureId();
		int featureCode = row.getFeatureCode();
		String featureType = row.getFeatureType();
		int typeCode = row.getTypeCode();

		Feature feature = new Feature(featureId, featureType);

		DerbyFeatureMap featureMap = new DerbyFeatureMap(featureId, featureCode, typeCode);

		OTACombination otaCombination = new OTACombination(feature, featureMap);

		return otaCombination;
	}

}
