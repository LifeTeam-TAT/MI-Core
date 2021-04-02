package org.ace.insurance.web.common;

import java.util.ArrayList;
import java.util.List;

import org.ace.java.web.common.BaseBean;

public class ValidationResult {

	private List<ErrorMessage> errormessages;

	public ValidationResult() {
		errormessages = new ArrayList<ErrorMessage>();
	}

	public boolean isVerified() {
		return errormessages.isEmpty();
	}

	public void addErrorMessage(String id, String errorcode, Object... params) {
		errormessages.add(new ErrorMessage(id, errorcode, params));
	}

	public void addErrorMessage(ErrorMessage errMsg) {
		errormessages.add(errMsg);
	}

	public List<ErrorMessage> getErrorMeesages() {
		return errormessages;
	}

	public String getMessages() {
		String msgs = "";
		for (ErrorMessage errm : errormessages) {
			msgs += BaseBean.getMessage(errm.getErrorcode(), errm.getParams());
		}
		return msgs;
	}

}
