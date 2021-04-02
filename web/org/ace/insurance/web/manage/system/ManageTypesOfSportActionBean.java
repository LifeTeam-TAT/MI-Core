/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.system.common.typesOfSport.service.interfaces.ITypesOfSportService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageTypesOfSportActionBean")
public class ManageTypesOfSportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{TypesOfSportService}")
	private ITypesOfSportService typesOfSportService;

	public void setTypesOfSportService(ITypesOfSportService typesOfSportService) {
		this.typesOfSportService = typesOfSportService;
	}

	private boolean createNew;
	private TypesOfSport typesOfSport;
	private List<TypesOfSport> typesOfSportList;
	private String criteria;

	@PostConstruct
	public void init() {
		createNewTypesOfSport();
		loadTypeOfSport();
	}

	private void loadTypeOfSport() {
		typesOfSportList = typesOfSportService.findAllTypesOfSport();
	}

	public void createNewTypesOfSport() {
		createNew = true;
		typesOfSport = new TypesOfSport();
	}

	public void prepareUpdateTypesOfSport(TypesOfSport typesOfSport) {
		createNew = false;
		this.typesOfSport = typesOfSport;
	}

	public void addNewTypesOfSport() {
		try {
			typesOfSportService.addNewTypesOfSport(typesOfSport);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, typesOfSport.getName());
			createNewTypesOfSport();
			loadTypeOfSport();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateTypesOfSport() {
		try {
			typesOfSportService.updateTypesOfSport(typesOfSport);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, typesOfSport.getName());
			createNewTypesOfSport();
			loadTypeOfSport();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteTypesOfSport() {
		try {
			typesOfSportService.deleteTypesOfSport(typesOfSport);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, typesOfSport.getName());
			createNewTypesOfSport();
			loadTypeOfSport();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public List<TypesOfSport> getTypesOfSportList() {
		return typesOfSportList;
	}

	public void setTypesOfSportList(List<TypesOfSport> typesOfSportList) {
		this.typesOfSportList = typesOfSportList;
	}

}
