package org.ace.insurance.web.common;

public class ErrorMessage {

	private String id;
	private String errorcode;
	private Object[] params;

	public ErrorMessage(String id, String errorcode, Object... params) {
		this.id = id;
		this.errorcode = errorcode;
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public Object[] getParams() {
		return params;
	}

	public void concatFormId(String formID) {
		this.id = formID + this.id;
	}

}
