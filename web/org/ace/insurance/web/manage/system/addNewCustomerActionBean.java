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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.CustomerStatus;
import org.ace.insurance.common.FamilyInfo;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.medical.proposal.CustomerInfoStatus;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "addNewCustomerActionBean")
public class addNewCustomerActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{CustomerService}")
	private ICustomerService customerService;

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	private boolean createNew;
	private boolean createNewFamilyInfo;
	private Customer customer;
	private Map<String, FamilyInfoDTO> familyInfoDTOMap;
	private FamilyInfoDTO familyInfoDTO;
	private List<RelationShip> relationShipList;
	private List<String> stateCodeList;
	private List<String> townshipCodeList;

	@PostConstruct
	public void init() {
		initialization();
		relationShipList = relationShipService.findAllRelationShip();
		townshipCodeList = new ArrayList<String>();
		familyInfoDTOMap = new HashMap<String, FamilyInfoDTO>();
		loadProvinceNo();
		loadCustomer();
	}

	private void loadCustomer() {
		if (customer != null) {
			createNew = false;
			customer.loadTransientIdNo();
			if (customer.getStateCode() != null)
				changeStateCode();

			prepareAddNewFamilyInfo();
			FamilyInfoDTO familyInfoDTO = null;
			for (FamilyInfo familyInfo : customer.getFamilyInfo()) {
				familyInfoDTO = new FamilyInfoDTO(familyInfo);
				familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
			}

		} else {
			createNewCustomer();
		}
	}

	private void initialization() {
		customer = (Customer) getParam("customer");
	}

	@PreDestroy
	private void destroy() {
		removeParam("customer");
	}

	public void changeCustomerIdType(AjaxBehaviorEvent e) {
		if (IdType.NRCNO.equals(customer.getIdType())) {
			customer.setFullIdNo(null);
			customer.setIdNo(null);
		} else {
			customer.setFullIdNo(null);
			customer.setIdNo(null);
			customer.setStateCode(null);
			customer.setTownshipCode(null);
			customer.setIdConditionType(null);
		}
	}

	public void changeFamilyIdType(AjaxBehaviorEvent e) {
		if (IdType.NRCNO.equals(familyInfoDTO.getIdType())) {
			familyInfoDTO.setFullIdNo(null);
			familyInfoDTO.setIdNo(null);
		} else {
			familyInfoDTO.setFullIdNo(null);
			familyInfoDTO.setIdNo(null);
			familyInfoDTO.setStateCode(null);
			familyInfoDTO.setTownshipCode(null);
			familyInfoDTO.setIdConditionType(null);
		}
	}

	public void loadProvinceNo() {
		stateCodeList = provinceService.findAllProvinceNo();
	}

	public void changeStateCode() {
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(customer.getStateCode());
	}

	public void changeStateCodeForFamilyInfo() {
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(familyInfoDTO.getStateCode());
	}

	public List<String> getStateCodeList() {
		return stateCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public boolean isCreateNewFamilyInfo() {
		return createNewFamilyInfo;
	}

	private void createNewFamilyInfo() {
		createNewFamilyInfo = true;
		familyInfoDTO = new FamilyInfoDTO();
		familyInfoDTO.setIdType(IdType.NRCNO);
	}

	public FamilyInfoDTO getFamilyInfoDTO() {
		return familyInfoDTO;
	}

	public void setFamilyInfoDTO(FamilyInfoDTO familyInfoDTO) {
		this.familyInfoDTO = familyInfoDTO;
	}

	public List<FamilyInfoDTO> getFamilyInfoDTOList() {
		if (familyInfoDTOMap.values() != null) {
			return new ArrayList<FamilyInfoDTO>(familyInfoDTOMap.values());
		}
		return new ArrayList<FamilyInfoDTO>();
	}

	public void prepareAddNewFamilyInfo() {
		createNewFamilyInfo();
	}

	public void prepareEditFamilyInfo(FamilyInfoDTO familyInfoDTO) {
		familyInfoDTO.loadTransientIdNo();
		this.familyInfoDTO = familyInfoDTO;
		if (familyInfoDTO.getStateCode() != null)
			changeStateCodeForFamilyInfo();
		this.createNewFamilyInfo = false;
	}

	public void saveFamilyInfo() {
		familyInfoDTO.setFullIdNo();
		familyInfoDTOMap.put(familyInfoDTO.getTempId(), familyInfoDTO);
		createNewFamilyInfo();
	}

	public void removeFamilyInfo(FamilyInfoDTO familyInfoDTO) {
		familyInfoDTOMap.remove(familyInfoDTO.getTempId());
	}

	public List<FamilyInfo> getFamilyInfoList() {
		List<FamilyInfo> result = new ArrayList<FamilyInfo>();
		if (familyInfoDTOMap.values() != null) {
			for (FamilyInfoDTO familyInfoDTO : familyInfoDTOMap.values()) {
				result.add(new FamilyInfo(familyInfoDTO.getInitialId(), familyInfoDTO.getFullIdNo(), familyInfoDTO.getIdType(), familyInfoDTO.getDateOfBirth(),
						familyInfoDTO.getName(), familyInfoDTO.getRelationShip(), familyInfoDTO.getIndustry(), familyInfoDTO.getOccupation()));
			}
		}
		return result;
	}

	public Gender[] getGenderSelectItemList() {
		return Gender.values();
	}

	public IdType[] getIdTypeSelectItemList() {
		return IdType.values();
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
	}

	public MaritalStatus[] getMaritalStatusSelectItemList() {
		return MaritalStatus.values();
	}

	public void createNewCustomer() {
		createNew = true;
		customer = new Customer();
		customer.setIdType(IdType.NRCNO);
		familyInfoDTOMap = new HashMap<String, FamilyInfoDTO>();
		prepareAddNewFamilyInfo();
	}  
	
	private boolean checkExistingCustomer() {
		boolean result = false;
		customer.setFullIdNo(setCustomerFullIdNo());
		result = customerService.isExistingCustomer(customer);
		return result;
	}
	
	private String setCustomerFullIdNo() {
		String result = "";
		if (customer.getIdType().equals(IdType.NRCNO)) {
			result = customer.getStateCode()+ "/" + customer.getTownshipCode()+ "(" + customer.getIdConditionType()+ ")" + customer.getIdNo();
		} else if (customer.getIdType().equals(IdType.FRCNO) || customer.getIdType().equals(IdType.PASSPORTNO)) {
			result = customer.getIdNo();
		}
		return result;
	}

	public String addNewCustomer() {
		String result = null;
		try {
			if(!checkExistingCustomer()) {
			customer.setFamilyInfo(getFamilyInfoList());
			customer.setFullIdNo();
			CustomerInfoStatus status = new CustomerInfoStatus();
			customer.addCustomerInfoStatus(status);
			status.setStatusName(CustomerStatus.CONTRACTOR);
			customerService.addNewCustomer(customer);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INSERT_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, customer.getFullName());
			result = "manageCustomer";
			}else {
				addErrorMessage(null, MessageId.EXISTING_CUSTOMER, customer.getFullName());
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}

		return result;
	}

	public String updateCustomer() {
		String result = null;
		try {
			customer.setFamilyInfo(getFamilyInfoList());
			customerService.updateCustomer(customer);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UPDATE_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, customer.getFullName());
			result = "manageCustomer";
		} catch (SystemException ex) {
			handelSysException(ex);
		}

		return result;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("personalInfo".equals(event.getOldStep()) && "generalInfo".equals(event.getNewStep())) {
			customer.setFullIdNo();
			boolean isExistingCustomer = customerService.isExistingCustomer(customer);
			if (isExistingCustomer) {
				valid = false;
				addInfoMessage(null, MessageId.EXISTING_CUSTOMER, customer.getFullName());
				PrimeFaces.current().ajax().update("customerEntryForm:growl");
			}

		} else if ("generalInfo".equals(event.getOldStep()) && "personalInfo".equals(event.getNewStep())) {
			if (customer.getStateCode() != null)
				changeStateCode();
		}

		return valid ? event.getNewStep() : event.getOldStep();
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public void returnNationality(SelectEvent event) {
		Country nationality = (Country) event.getObject();
		customer.setCountry(nationality);
	}

	public void returnReligion(SelectEvent event) {
		Religion religion = (Religion) event.getObject();
		customer.setReligion(religion);
	}

	public void returnQualification(SelectEvent event) {
		Qualification qualification = (Qualification) event.getObject();
		customer.setQualification(qualification);
	}

	public void returnBankBranch(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		customer.setBankBranch(bankBranch);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		customer.setBranch(branch);
	}

	public void returnIndustry(SelectEvent event) {
		Industry industry = (Industry) event.getObject();
		customer.setIndustry(industry);
	}

	public void returnOccupation(SelectEvent event) {
		Occupation occupation = (Occupation) event.getObject();
		customer.setOccupation(occupation);
	}

	public void returnResidentTownship(SelectEvent event) {
		Township residentTownship = (Township) event.getObject();
		customer.getResidentAddress().setTownship(residentTownship);
	}

	public void returnPermanentTownship(SelectEvent event) {
		Township permanentTownship = (Township) event.getObject();
		customer.getPermanentAddress().setTownship(permanentTownship);
	}

	public void returnOfficeTownship(SelectEvent event) {
		Township officeTownship = (Township) event.getObject();
		customer.getOfficeAddress().setTownship(officeTownship);
	}

	public void returnFamilyOccupation(SelectEvent event) {
		Occupation familyOccupation = (Occupation) event.getObject();
		familyInfoDTO.setOccupation(familyOccupation);
	}

	public void returnFamilyIndustry(SelectEvent event) {
		Industry familyIndustry = (Industry) event.getObject();
		familyInfoDTO.setIndustry(familyIndustry);
	}

}
