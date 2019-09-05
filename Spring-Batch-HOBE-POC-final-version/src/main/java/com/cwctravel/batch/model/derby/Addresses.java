package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Addresses stores a list containing all possible Address for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Address} class for Address
 * @author chris.nie
 * 
 */
public class Addresses {
	private List<Address> Address;

	private String prefix;


	public List<Address> getAddress() {
		return Address;
	}

	public void setAddress(List<Address> address) {
		Address = address;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
