package com.cwctravel.batch.model.derby;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Body stores information about the Derby response when requesting for hotel data
 * <p>
 * Please see  {@link com.cwctravel.batch.model.derby.GetHotelContentResponse} for class GetHotelContentResponse
 * @author chris.nie
 *
 */

public class Body {

	@JsonProperty("getHotelResponse")
	GetHotelContentResponse GetHotelContentResponseObject;

	private String prefix;


	// Getter Methods 

	public GetHotelContentResponse getGetHotelContentResponse() {
		return GetHotelContentResponseObject;
	}

	public String getPrefix() {
		return prefix;
	}

	// Setter Methods 

	public void setGetHotelContentResponse( GetHotelContentResponse getHotelContentResponseObject ) {
		this.GetHotelContentResponseObject = getHotelContentResponseObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
