package com.cwctravel.batch.model.derby;

/**
 * CountryName stores the name and code of a country from Derby response.
 * @author chris.nie
 *
 */
public class CountryName {
	private String Code;

	private String prefix;

	private String text;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
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
