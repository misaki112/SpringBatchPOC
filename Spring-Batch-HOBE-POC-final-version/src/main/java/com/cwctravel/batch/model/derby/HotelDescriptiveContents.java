package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * HotelDescriptiveContents is a list of HotelDescriptiveContent for hotels from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.HotelDescriptiveContent} for class HotelDescriptiveContent.
 * @author chris.nie
 *
 */
public class HotelDescriptiveContents {
	private List<HotelDescriptiveContent> HotelDescriptiveContent;
	private String prefix;


	public List<HotelDescriptiveContent> getHotelDescriptiveContent() {
		return HotelDescriptiveContent;
	}

	public void setHotelDescriptiveContent(List<HotelDescriptiveContent> hotelDescriptiveContent) {
		HotelDescriptiveContent = hotelDescriptiveContent;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
