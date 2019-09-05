package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Emails is a list of all possible Email for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Email} for class Email.
 * @author chris.nie
 *
 */
public class Emails {
	private List<Email> Email;

	private String prefix;

	public List<Email> getEmail() {
		return Email;
	}

	public void setEmail(List<Email> email) {
		Email = email;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
