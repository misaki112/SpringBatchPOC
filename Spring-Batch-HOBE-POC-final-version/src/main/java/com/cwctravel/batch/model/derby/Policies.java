package com.cwctravel.batch.model.derby;

/**
 * Policies is a list of Policy for a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.Policy} for class Policy.
 * @author chris.nie
 *
 */
public class Policies {
	Policy PolicyObject;
	private String prefix;

	public Policy getPolicy() {
		return PolicyObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPolicy( Policy PolicyObject ) {
		this.PolicyObject = PolicyObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
