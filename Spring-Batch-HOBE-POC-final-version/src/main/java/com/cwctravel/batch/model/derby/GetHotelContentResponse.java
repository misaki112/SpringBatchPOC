package com.cwctravel.batch.model.derby;

/**
 * GetHotelContentResponse stores information about response of calling Derby web service for hotel information.
 * <p>
 * GetHotelContentResponse includes the return information of the request.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Return} for class Return
 * @author chris.nie
 *
 */
public class GetHotelContentResponse {

	Return ReturnObject;

	private String xmlnsns2;

	private String xmlnsns3;

	private String prefix;


	// Getter Methods 

	public Return getReturn() {
		return ReturnObject;
	}

	public String getXmlnsns2() {
		return xmlnsns2;
	}

	public String getXmlnsns3() {
		return xmlnsns3;
	}

	public String getPrefix() {
		return prefix;
	}

	// Setter Methods 

	public void setReturn(Return returnObject ) {
		this.ReturnObject = returnObject;
	}

	public void setXmlnsns2( String xmlnsns2 ) {
		this.xmlnsns2 = xmlnsns2;
	}

	public void setXmlnsns3( String xmlnsns3 ) {
		this.xmlnsns3 = xmlnsns3;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
