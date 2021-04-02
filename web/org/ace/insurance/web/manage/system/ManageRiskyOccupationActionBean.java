package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;
import org.ace.insurance.system.common.riskyOccupation.service.interfaces.IRiskyOccupationService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name="ManageRiskyOccupationActionBean")
public class ManageRiskyOccupationActionBean extends BaseBean implements Serializable{	

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{RiskyOccupationService}")
	private IRiskyOccupationService riskyOccupationService;

	public void setRiskyOccupationService(IRiskyOccupationService riskyOccupationService) {
		this.riskyOccupationService = riskyOccupationService;
	}
	
	private RiskyOccupation riskyOccupation;
	private List<RiskyOccupation> riskyOccupationList;
	private boolean createNew;
	
	@PostConstruct
	public void init() {
		createNewRiskyOccuption();
		loadAllRiskyOccupation();
	}
	
	public void createNewRiskyOccuption() {
		riskyOccupation=new RiskyOccupation();
		createNew=true;
	}
	
	public void loadAllRiskyOccupation() {
		riskyOccupationList=riskyOccupationService.findAllRiskyOccupation();
	}
	
	public void addNewRiskyOccupation() {
		try {
			riskyOccupationService.addNewRiskyOccupation(riskyOccupation);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, riskyOccupation.getName());
			loadAllRiskyOccupation();
			createNewRiskyOccuption();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		
	}
	
	public void prepareUpdateRiskyOccupation(RiskyOccupation riskyOccupation) {
		this.riskyOccupation=riskyOccupation;
		createNew=false;
	}
	
	public void updateRiskyOccupation() {
		try {
			riskyOccupationService.updateRiskyOccupation(riskyOccupation);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, riskyOccupation.getName());
			loadAllRiskyOccupation();
			createNewRiskyOccuption();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}
	
	public void deleteRiskyOccupation() {
		try {
			riskyOccupationService.deleteRiskyOccupation(riskyOccupation);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, riskyOccupation.getName());
			loadAllRiskyOccupation();
			createNewRiskyOccuption();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}
	
	

	public RiskyOccupation getRiskyOccupation() {
		return riskyOccupation;
	}

	public void setRiskyOccupation(RiskyOccupation riskyOccupation) {
		this.riskyOccupation = riskyOccupation;
	}

	public boolean getIsCreateNew() {
		return createNew;
	}

	public List<RiskyOccupation> getRiskyOccupationList() {
		return riskyOccupationList;
	}
	
	
	
	
	
	

}
