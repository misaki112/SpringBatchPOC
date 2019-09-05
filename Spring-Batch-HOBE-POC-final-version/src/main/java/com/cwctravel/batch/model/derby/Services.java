package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * Services is a list of Service that is provided by a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Service} for class Service.
 * @author chris.nie
 *
 */
public class Services {
	private List<Service> Service;
	private String prefix;


	public List<Service> getService() {
		return Service;
	}

	public void setService(List<Service> service) {
		Service = service;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
