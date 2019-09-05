package com.cwctravel.batch.model.derby;

/**
 * StateProv stores information about a State or Province form Derby response.
 * <p>
 * StateProv information includes the name and code of the State or Province.
 * @author chris.nie
 *
 */
public class StateProv {
	private String StateCode;

	private String prefix;

	private String text;

	public String getStateCode() {
		return StateCode;
	}

	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


}
