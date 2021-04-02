package org.ace.insurance.web.dialog;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "SurveyQuestionDialogActionBean")
@ViewScoped
public class SurveyQuestionDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
	}
}
