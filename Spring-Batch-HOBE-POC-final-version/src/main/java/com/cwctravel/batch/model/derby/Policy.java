package com.cwctravel.batch.model.derby;

/**
 * Policy stores PolicyInfo of a hotel from Derby response.
 * <p>
 * Please see {@link com.cwctravel.batch.model.derby.PolicyInfo} for class PolicyInfo.
 * @author chris.nie
 *
 */
public class Policy {
	PolicyInfo PolicyInfoObject;
	private String prefix;

	public PolicyInfo getPolicyInfo() {
		return PolicyInfoObject;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPolicyInfo( PolicyInfo PolicyInfoObject ) {
		this.PolicyInfoObject = PolicyInfoObject;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}
}
