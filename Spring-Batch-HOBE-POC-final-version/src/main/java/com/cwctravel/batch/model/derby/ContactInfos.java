package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * ContactInfos is a list of ContactInfo of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.ContactInfo} for class ContactInfo.
 * @author chris.nie
 *
 */
public class ContactInfos {
	private List<ContactInfo> ContactInfo;
	private String prefix;

	public List<ContactInfo> getContactInfo() {
		return ContactInfo;
	}

	public void setContactInfo(List<ContactInfo> contactInfo) {
		ContactInfo = contactInfo;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
