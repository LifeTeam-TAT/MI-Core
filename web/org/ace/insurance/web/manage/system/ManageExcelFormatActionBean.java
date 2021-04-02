/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.country.service.interfaces.ICountryService;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.manage.report.medical.GroupLifeExcelTemplate;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "ManageExcelFormatActionBean")
public class ManageExcelFormatActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OccupationService}")
	private IOccupationService occupationService;

	public void setOccupationService(IOccupationService occupationService) {
		this.occupationService = occupationService;
	}
	
	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationshipService;
	
	public void setRelationshipService(IRelationShipService relationshipService) {
		this.relationshipService = relationshipService;
	}
	
	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;
	
	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}
	
	@ManagedProperty(value = "#{CountryService}")
	private ICountryService countryService;
	
	public void setCountryService(ICountryService countryService) {
		this.countryService = countryService;
	}

	private User user;
	private List<Occupation> occupationList;
	private List<RelationShip> relationshipList;
	private List<Country> countryList;
	private List<Township> townshipList;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		createNew();
		loadData();
	}
	
	private void createNew() {
		relationshipList = new ArrayList<RelationShip>();
		townshipList = new ArrayList<Township>();
		occupationList = new ArrayList<Occupation>();
		countryList = new ArrayList<Country>();
	}
	
	private void loadData() {
		relationshipList = relationshipService.findAllRelationShip();
		townshipList = townshipService.findAllTownship();
		occupationList = occupationService.findAllOccupation();
		countryList = countryService.findAllCountry();
	}
	
	public void exportExcelTemplate() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "Group_Health_InsuredPerson_Beneficiary_Template.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			GroupLifeExcelTemplate excelTemplate = new GroupLifeExcelTemplate();
			excelTemplate.generate(op, relationshipList, townshipList, occupationList, countryList);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Life_Daily_Income_Report.xlsx", e);
		}
	}
	

	public List<Occupation> getOccupationList() {
		return occupationList;
	}

	public void setOccupationList(List<Occupation> occupationList) {
		this.occupationList = occupationList;
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public void setRelationshipList(List<RelationShip> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public List<Township> getTownshipList() {
		return townshipList;
	}

	public void setTownshipList(List<Township> townshipList) {
		this.townshipList = townshipList;
	}
	
	
}
