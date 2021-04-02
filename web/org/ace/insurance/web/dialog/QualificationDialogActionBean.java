package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.qualification.service.interfaces.IQualificationService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "QualificationDialogActionBean")
@ViewScoped
public class QualificationDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{QualificationService}")
	private IQualificationService qualificationService;

	public void setQualificationService(IQualificationService qualificationService) {
		this.qualificationService = qualificationService;
	}

	private List<Qualification> qualificationList;

	@PostConstruct
	public void init() {
		qualificationList = qualificationService.findAllQualification();
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void selectQualification(Qualification qualification) {
		RequestContext.getCurrentInstance().closeDialog(qualification);
	}
}
