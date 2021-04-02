package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.industry.service.interfaces.IIndustryService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "IndustryDialogActionBean")
@ViewScoped
public class IndustryDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{IndustryService}")
	private IIndustryService industryService;

	public void setIndustryService(IIndustryService industryService) {
		this.industryService = industryService;
	}

	private List<Industry> industryList;

	@PostConstruct
	public void init() {
		industryList = industryService.findAllIndustry();
	}

	public List<Industry> getIndustryList() {
		return industryList;
	}

	public void selectIndustry(Industry industry) {
		RequestContext.getCurrentInstance().closeDialog(industry);
	}
}
