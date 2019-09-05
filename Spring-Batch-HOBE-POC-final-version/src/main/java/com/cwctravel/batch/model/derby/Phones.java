package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Phones is a list of Phone of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Phone} for class Phone.
 * @author chris.nie
 *
 */
public class Phones {
	private List<Phone> Phone;

	private String prefix;

	public List<Phone> getPhone() {
		return Phone;
	}

	public void setPhone(List<Phone> phone) {
		Phone = phone;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
