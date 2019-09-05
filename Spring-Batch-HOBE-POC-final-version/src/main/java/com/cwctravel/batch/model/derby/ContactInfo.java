package com.cwctravel.batch.model.derby;

/**
 * ContactInfo stores contact information of a hotel from Derby response.
 * <p>
 * ContactInfo infomration includes address, phone number, website url and email of a hotel.
 * @author chris.nie
 *
 */
public class ContactInfo {

	private Addresses Addresses;

	private Phones Phones;

	private URLs URLs;

	private Emails Emails;

	private String prefix;

	public Emails getEmails() {
		return Emails;
	}

	public void setEmails(Emails emails) {
		Emails = emails;
	}

	public Addresses getAddresses() {
		return Addresses;
	}

	public void setAddresses(Addresses addresses) {
		Addresses = addresses;
	}

	public Phones getPhones() {
		return Phones;
	}

	public void setPhones(Phones phones) {
		Phones = phones;
	}

	public URLs getURLs() {
		return URLs;
	}

	public void setURLs(URLs uRLs) {
		URLs = uRLs;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
