package com.cwctravel.batch.model.derby;

import java.util.List;

/**
 * URLs is a list of URL of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.URL} for class URL.
 * @author chris.nie
 *
 */
public class URLs {
	private List<URL> URL;

	private String prefix;

	public List<URL> getURL() {
		return URL;
	}

	public void setURL(List<URL> uRL) {
		URL = uRL;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


}
