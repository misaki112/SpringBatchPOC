package com.cwctravel.batch.model.csv;

import java.util.HashSet;
import java.util.Set;

/**
 * Hotel stores all information about a hotel from csv files.
 * <p>
 * Hotel information includes id, name description, 
 * rates, images, services for a hotel and places of interest around it.
 * @author chris.nie
 *
 */
public class Hotel {

	private String hotelId;

	private String hotelName;

	private String extendedDescription;

	private Set<HotelImage> imageSet;

	private Set<RoomCategory> categorySet;

	private Set<HotelFeature> featureSet;

	private Set<InterestFeature> interestSet;

	private Set <DCHotelIdMapping> idMappingSet;

	private HotelRate rate;

	public Hotel() {

	}

	public Hotel(String hotelId) {
		this.hotelId = hotelId;
	}


	public Hotel(String hotelId, String hotelName, String extendedDescription) {
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.extendedDescription = extendedDescription;
	}



	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getExtendedDescription() {
		return extendedDescription;
	}

	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

	public Set<HotelImage> getImageSet() {
		if (imageSet == null) {
			imageSet = new HashSet<>();
		}
		return imageSet;
	}

	public void setImageSet(Set<HotelImage> imageSet) {
		this.imageSet = imageSet;
	}

	public Set<RoomCategory> getCategorySet() {
		if (categorySet == null) {
			categorySet = new HashSet<>();
		}
		return categorySet;
	}

	public void setCategorySet(Set<RoomCategory> categorySet) {
		this.categorySet = categorySet;
	}

	public Set<HotelFeature> getFeatureSet() {
		if (featureSet == null) {
			featureSet = new HashSet<>();
		}
		return featureSet;
	}

	public void setFeatureSet(Set<HotelFeature> featureSet) {
		this.featureSet = featureSet;
	}

	public Set<InterestFeature> getInterestSet() {
		if (interestSet == null) {
			interestSet = new HashSet<>();
		}
		return interestSet;
	}

	public void setInterestSet(Set<InterestFeature> interestSet) {
		this.interestSet = interestSet;
	}

	public HotelRate getRate() {
		return rate;
	}

	public void setRate(HotelRate rate) {
		this.rate = rate;
	}

	public Set<DCHotelIdMapping> getIdMappingSet() {
		if (idMappingSet == null) {
			idMappingSet = new HashSet<>();
		}
		return idMappingSet;
	}

	public void setIdMappingSet(Set<DCHotelIdMapping> idMappingSet) {
		this.idMappingSet = idMappingSet;
	}


}
