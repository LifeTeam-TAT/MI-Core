package org.ace.insurance.web.dialog;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;
import org.ace.insurance.system.common.riskyOccupation.service.interfaces.IRiskyOccupationService;
import org.primefaces.context.RequestContext;

@ManagedBean(name="RiskyOccupationDialogActionBean")
@ViewScoped
public class RiskyOccupationDialogActionBean {
	
	@ManagedProperty(value="#{RiskyOccupationService}")
	private IRiskyOccupationService riskyOccupationService;

	public void setRiskyOccupationService(IRiskyOccupationService riskyOccupationService) {
		this.riskyOccupationService = riskyOccupationService;
	}
	
	private List<RiskyOccupation> riskyOccupationList;
	
	@PostConstruct
	public void init() {
		loadRiskyOccupation();
		
	}
	
	public void loadRiskyOccupation() {
		riskyOccupationList=riskyOccupationService.findAllRiskyOccupation();
	}

	public List<RiskyOccupation> getRiskyOccupationList() {
		return riskyOccupationList;
	}
	
	public void selectRiskyOccupation(RiskyOccupation riskyOccupation) {
		RequestContext.getCurrentInstance().closeDialog(riskyOccupation);
	}
	
	

}
