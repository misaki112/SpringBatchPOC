package com.cwctravel.batch.model.derby;

/**
 * Envelope is the most outer level of Json file return by Derby response.
 * <p>
 * All information returned is inside Envelope.
 * @author chris.nie
 *
 */
public class Envelope {

	Body BodyObject;

	private String xmlnsS;

	private String xmlnsSOAPENV;

	private String prefix;

	public Body getBody() {
		return BodyObject;
	}

	public String getXmlnsS() {
		return xmlnsS;
	}

	public String getXmlnsSOAPENV() {
		return xmlnsSOAPENV;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setBody( Body BodyObject ) {
		this.BodyObject = BodyObject;
	}

	public void setXmlnsS( String xmlnsS ) {
		this.xmlnsS = xmlnsS;
	}

	public void setXmlnsSOAPENV( String xmlnsSOAPENV ) {
		this.xmlnsSOAPENV = xmlnsSOAPENV;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
