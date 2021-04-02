package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.religion.service.interfaces.IReligionService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ReligionDialogActionBean")
@ViewScoped
public class ReligionDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ReligionService}")
	private IReligionService religionService;

	public void setReligionService(IReligionService religionService) {
		this.religionService = religionService;
	}

	private List<Religion> religionList;

	@PostConstruct
	public void init() {
		religionList = religionService.findAllReligion();
	}

	public List<Religion> getReligionList() {
		return religionList;
	}

	public void selectReligion(Religion religion) {
		RequestContext.getCurrentInstance().closeDialog(religion);
	}

}
