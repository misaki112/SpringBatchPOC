package com.cwctravel.batch.model.derby;

/**
 * Return stores the information about the return response of calling Derby web service for hotel information.
 * <p>
 * Return information includes language, target, time and version of the response and data about all Hotels
 * requested in the request to Derby.
 * @author chris.nie
 *
 */

public class Return {
	Success SuccessObject;
	HotelDescriptiveContents HotelDescriptiveContentsObject;
	private String PrimaryLangID;
	private String Target;
	private String TimeStamp;
	private String Version;

	public Success getSuccess() {
		return SuccessObject;
	}

	public HotelDescriptiveContents getHotelDescriptiveContents() {
		return HotelDescriptiveContentsObject;
	}

	public String getPrimaryLangID() {
		return PrimaryLangID;
	}

	public String getTarget() {
		return Target;
	}

	public String getTimeStamp() {
		return TimeStamp;
	}

	public String getVersion() {
		return Version;
	}

	public void setSuccess( Success SuccessObject ) {
		this.SuccessObject = SuccessObject;
	}

	public void setHotelDescriptiveContents( HotelDescriptiveContents HotelDescriptiveContentsObject ) {
		this.HotelDescriptiveContentsObject = HotelDescriptiveContentsObject;
	}

	public void setPrimaryLangID( String PrimaryLangID ) {
		this.PrimaryLangID = PrimaryLangID;
	}

	public void setTarget( String Target ) {
		this.Target = Target;
	}

	public void setTimeStamp( String TimeStamp ) {
		this.TimeStamp = TimeStamp;
	}

	public void setVersion( String Version ) {
		this.Version = Version;
	}
}
