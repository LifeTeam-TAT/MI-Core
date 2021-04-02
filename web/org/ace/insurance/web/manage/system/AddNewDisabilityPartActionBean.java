package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


import org.ace.insurance.disabilitypart.DisabilityPart;
import org.ace.insurance.disabilitypart.service.IDisabilityPartService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AddNewDisabilityPartActionBean")
public class AddNewDisabilityPartActionBean extends BaseBean{
	
	@ManagedProperty(value = "#{DisabilityPartService}")
	private IDisabilityPartService disabilityPartService;


	public IDisabilityPartService getDisabilityPartService() {
		return disabilityPartService;
	}

	public void setDisabilityPartService(IDisabilityPartService disabilityPartService) {
		this.disabilityPartService = disabilityPartService;
	}

	private DisabilityPart disabilitypart;
	private boolean createNew;
	private List<DisabilityPart> disabilitypartList;
	private List<DisabilityPart> filtereddisabilitypartList;

	@PostConstruct
	public void init() {
		createNewDisabilitypart();
		loadDisabilitypart();
	}

	public void loadDisabilitypart(){
		disabilitypartList = disabilityPartService.findAllDisabilityPart();
	}

	public void createNewDisabilitypart() {
		createNew = true;
		disabilitypart = new DisabilityPart();
	}


	public void addNewDisabilitypart() {
		try {
			
				disabilityPartService.addNewDisabilityPart(disabilitypart);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, disabilitypart.getName());
				createNewDisabilitypart();
				loadDisabilitypart();
			

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadDisabilitypart();
	}

	public void updateDisabilitypart() {
		try {
			
				disabilityPartService.updateDisabilityPart(disabilitypart);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, disabilitypart.getName());
				createNewDisabilitypart();
				loadDisabilitypart();
			

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadDisabilitypart();
	}

	public void deleteDisabilitypart(DisabilityPart disabilitypart) {
		try {
			disabilityPartService.deleteDisabilityPart(disabilitypart);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, disabilitypart.getName());
			createNewDisabilitypart();
			loadDisabilitypart();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadDisabilitypart();
	}


	public void prepareUpdateDisabilityPart(DisabilityPart disabilityPart) {
		this.disabilitypart = disabilityPart;
		createNew = false;
	}


	public boolean isCreateNew() {
		return createNew;
	}

	public DisabilityPart getDisabilitypart() {
		return disabilitypart;
	}

	public void setDisabilitypart(DisabilityPart disabilitypart) {
		this.disabilitypart = disabilitypart;
	}

	public List<DisabilityPart> getDisabilitypartList() {
		return disabilitypartList;
	}

	public void setDisabilitypartList(List<DisabilityPart> disabilitypartList) {
		this.disabilitypartList = disabilitypartList;
	}

	public List<DisabilityPart> getFiltereddisabilitypartList() {
		return filtereddisabilitypartList;
	}

	public void setFiltereddisabilitypartList(List<DisabilityPart> filtereddisabilitypartList) {
		this.filtereddisabilitypartList = filtereddisabilitypartList;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}



}
