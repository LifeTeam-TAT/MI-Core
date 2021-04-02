package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.insurance.system.common.classofinsurance.service.interfaces.IClassOfInsuranceService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ClassOfInsuranceDialogActionBean")
@ViewScoped
public class ClassOfInsuranceDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ClassOfInsuranceService}")
	private IClassOfInsuranceService classOfInsuranceService;

	public void setClassOfInsuranceService(IClassOfInsuranceService classOfInsuranceService) {
		this.classOfInsuranceService = classOfInsuranceService;
	}

	private List<ClassOfInsurance> classOfInsuranceList;

	public List<ClassOfInsurance> getClassOfInsuranceList() {
		return classOfInsuranceList;
	}

	@PostConstruct
	public void init() {
		classOfInsuranceList = classOfInsuranceService.findAllClassOfInsurance();
	}

	public void selectClassOfInsurance(ClassOfInsurance classOfInsurance) {
		RequestContext.getCurrentInstance().closeDialog(classOfInsurance);
	}
}
