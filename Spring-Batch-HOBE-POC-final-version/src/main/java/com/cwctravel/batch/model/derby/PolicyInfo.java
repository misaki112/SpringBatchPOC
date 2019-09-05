package com.cwctravel.batch.model.derby;

/**
 * PolicyInfo stores information about the policy of a hotel from Derby Response.
 * <p>
 * PolicyInfo information includes CheckInTime, CheckOutTime of the Hotel, whether kids
 * need to pay for the stay and what is the max age for a kid, and the minimum age of guest accepted.
 * @author chris.nie
 *
 */
public class PolicyInfo {
	private String CheckInTime;
	private String CheckOutTime;
	private String KidsStayFree;
	private String MaxChildAge;
	private String MinGuestAge;
	private String prefix;


	public String getCheckInTime() {
		return CheckInTime;
	}

	public String getCheckOutTime() {
		return CheckOutTime;
	}

	public String getKidsStayFree() {
		return KidsStayFree;
	}

	public String getMaxChildAge() {
		return MaxChildAge;
	}

	public String getMinGuestAge() {
		return MinGuestAge;
	}

	public String getPrefix() {
		return prefix;
	}


	public void setCheckInTime( String CheckInTime ) {
		this.CheckInTime = CheckInTime;
	}

	public void setCheckOutTime( String CheckOutTime ) {
		this.CheckOutTime = CheckOutTime;
	}

	public void setKidsStayFree( String KidsStayFree ) {
		this.KidsStayFree = KidsStayFree;
	}

	public void setMaxChildAge( String MaxChildAge ) {
		this.MaxChildAge = MaxChildAge;
	}

	public void setMinGuestAge( String MinGuestAge ) {
		this.MinGuestAge = MinGuestAge;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
