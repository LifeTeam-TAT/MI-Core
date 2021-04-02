package org.ace.insurance.web.manage.reversal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.common.PassportType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.bankBranch.service.interfaces.IBankBranchService;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.religion.service.interfaces.IReligionService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.common.Validator;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyDTO;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonBeneficiaryDTO;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonDTO;
import org.ace.insurance.web.manage.medical.claim.factory.MedicalPolicyFactory;
import org.ace.insurance.web.manage.medical.claim.factory.MedicalPolicyInsuredPersonFactory;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.MedProInsuAddOnDTO;
import org.ace.insurance.web.manage.medical.proposal.PolicyGuardianDTO;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "EditMedicalPolicyActionBean")
public class EditMedicalPolicyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{PolicyBeneficiariesValidator}")
	private Validator<MedicalPolicyInsuredPersonBeneficiaryDTO> beneficiariesValidator;

	@ManagedProperty(value = "#{PolicyInsuredPersonValidator}")
	private Validator<MedicalPolicyInsuredPersonDTO> insuredpersonValidator;

	@ManagedProperty(value = "#{CustomerValidator}")
	private DTOValidator<CustomerDTO> customerValidator;

	@ManagedProperty(value = "#{GuardianPolicyPersonValidator}")
	private Validator<PolicyGuardianDTO> guardianValidator;

	public void setBeneficiariesValidator(Validator<MedicalPolicyInsuredPersonBeneficiaryDTO> beneficiariesValidator) {
		this.beneficiariesValidator = beneficiariesValidator;
	}

	public void setInsuredpersonValidator(Validator<MedicalPolicyInsuredPersonDTO> insuredpersonValidator) {
		this.insuredpersonValidator = insuredpersonValidator;
	}

	public void setCustomerValidator(DTOValidator<CustomerDTO> customerValidator) {
		this.customerValidator = customerValidator;
	}

	public void setGuardianValidator(Validator<PolicyGuardianDTO> guardianValidator) {
		this.guardianValidator = guardianValidator;
	}

	@ManagedProperty(value = "#{ReligionService}")
	protected IReligionService religionService;

	public void setReligionService(IReligionService religionService) {
		this.religionService = religionService;
	}

	@ManagedProperty(value = "#{BankBranchService}")
	protected IBankBranchService bankBranchService;

	public void setBankBranchService(IBankBranchService bankBranchService) {
		this.bankBranchService = bankBranchService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{CoinsuranceCompanyService}")
	private ICoinsuranceCompanyService coinsuranceCompanyService;

	public void setCoinsuranceCompanyService(ICoinsuranceCompanyService coinsuranceCompanyService) {
		this.coinsuranceCompanyService = coinsuranceCompanyService;
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

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	private String remark;

	private boolean createNewIsuredPersonInfo;
	private boolean isOrganization;
	private boolean guardianInfoDisable;
	private boolean createNewBeneficiariesInfo;

	private Product product;
	private User user;
	private MedicalPolicy medicalPolicy;
	private MedicalPolicyDTO medicalPolicyDTO;
	private MedicalPolicyInsuredPersonBeneficiaryDTO beneficiariesInfoDTO;
	private MedicalPolicyInsuredPersonDTO insuredPersonDTO;
	private List<BankBranch> bankBranchList;
	private List<String> stateCodeList = new ArrayList<>();
	private List<String> townshipCodeList = new ArrayList<>();
	private Map<String, MedicalPolicyInsuredPersonBeneficiaryDTO> beneficiariesInfoDTOMap;
	private Map<String, MedicalPolicyInsuredPersonDTO> insuredPersonDTOMap;
	private MedicalPolicyInsuredPersonBeneficiaryDTO beneficaryCloneDTO;
	private List<MedProInsuAddOnDTO> insuredPersonAddOnList;
	private List<RelationShip> relationshipList;

	@PreDestroy
	public void destroy() {
		removeParam("medicalPolicy");
	}

	/************ init ************/
	@PostConstruct
	public void init() {
		insuredPersonDTOMap = new HashMap<String, MedicalPolicyInsuredPersonDTO>();
		initializeInjection();
		loadData();
		createNewBeneficiariesInfoDTOMap();
	}

	public void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		medicalPolicy = (medicalPolicy == null) ? (MedicalPolicy) getParam("medicalPolicy") : medicalPolicy;
		medicalPolicyDTO = MedicalPolicyFactory.createMedicalPolicyDTO(medicalPolicy);
		product = medicalPolicy.getPolicyInsuredPersonList().get(0).getProduct();
		createNewIsuredPersonInfo = true;
		guardianInfoDisable = true;
		loadCustomerIdNo(medicalPolicyDTO.getCustomer());
		initDataList();
		initAddOnsConfig();
		if (medicalPolicyDTO.getOrganization() != null)
			isOrganization = true;
	}

	private void initAddOnsConfig() {
		insuredPersonAddOnList = new ArrayList<>();
		MedProInsuAddOnDTO medAddOnDTO;
		if (product != null)
			for (AddOn addOn : product.getAddOnList()) {
				medAddOnDTO = new MedProInsuAddOnDTO(addOn);
				insuredPersonAddOnList.add(medAddOnDTO);
			}
	}

	private void initDataList() {
		for (MedicalPolicyInsuredPersonDTO personDTO : medicalPolicyDTO.getPolicyInsuredPersonDtoList()) {
			insuredPersonDTOMap.put(personDTO.getTempId(), personDTO);
		}
	}

	private void loadIdNo(CustomerDTO cusDTO) {
		cusDTO.loadTransientIdNo();
		loadTownshipCodeByStateCode(cusDTO.getStateCode());
	}

	private void loadCustomerIdNo(Customer customer) {
		if (customer != null) {
			customer.loadTransientIdNo();
			loadTownshipCodeByStateCode(customer.getStateCode());
		}
	}

	private void loadData() {
		relationshipList = relationShipService.findAllRelationShip();
		bankBranchList = bankBranchService.findAllBankBranch();
		stateCodeList = provinceService.findAllProvinceNo();
	}

	/************ init end ************/

	/************ UI event ************/
	public void changeStateCode(AjaxBehaviorEvent e) {
		String stateCode = (String) ((UIOutput) e.getSource()).getValue();
		loadTownshipCodeByStateCode(stateCode);
	}

	/************ UI event end ************/
	public void handleInsuredPersonAge() {
		guardianInfoDisable = true;
		Date dateOfBirth = insuredPersonDTO.getCustomerDTO().getDateOfBirth();
		Date startDate = medicalPolicyDTO.getActivedPolicyStartDate();
		if (dateOfBirth != null && Utils.getAgeForNextYear(dateOfBirth, startDate) < 18) {
			guardianInfoDisable = false;
		} else {
			insuredPersonDTO.setGuardianDTO(null);
		}
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("InsuredPersonInfo".equals(event.getOldStep()) && "customerTab".equals(event.getNewStep())) {
			handleInsuredPersonAge();
			if (medicalPolicyDTO.getCustomer() != null) {
				loadCustomerIdNo(medicalPolicyDTO.getCustomer());
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	private List<MedicalPolicyInsuredPerson> getPolicyInsuredPersonList() {
		List<MedicalPolicyInsuredPerson> insuredPersonList = new ArrayList<MedicalPolicyInsuredPerson>();
		for (MedicalPolicyInsuredPersonDTO dto : getMedicalInsuredPersonDTOList()) {
			insuredPersonList.add(MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPerson(dto));
		}
		return insuredPersonList;
	}

	public void saveMedicalInsuredPerson() {
		insuredPersonDTO.setMedicalPolicyDTO(medicalPolicyDTO);
		insuredPersonDTO.setPolicyInsuredPersonBeneficiariesDtoList(getProposalInsuredPersonBeneficiariesList());
		ValidationResult result = insuredpersonValidator.validate(insuredPersonDTO);
		if (result.isVerified()) {
			beneficaryCloneDTO = getProposalInsuredPersonBeneficiariesList().get(0);
			Date dateOfBirth = insuredPersonDTO.getCustomerDTO().getDateOfBirth();
			Date startDate = medicalPolicyDTO.getActivedPolicyStartDate();
			insuredPersonDTO.setAge(Utils.getAgeForNextYear(dateOfBirth, startDate));
			insuredPersonDTO.setPolicyInsuredPersonBeneficiariesDtoList(getProposalInsuredPersonBeneficiariesList());
			insuredPersonDTOMap.put(insuredPersonDTO.getTempId(), insuredPersonDTO);
			createNewInsuredPerson();
			createNewBeneficiariesInfoDTOMap();
			createNewIsuredPersonInfo = true;
		} else if (!result.isVerified()) {
			for (ErrorMessage message : result.getErrorMeesages()) {
				addErrorMessage(message);
			}
		}
	}

	private void createNewInsuredPerson() {
		insuredPersonDTO = new MedicalPolicyInsuredPersonDTO();
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setGender(Gender.MALE);
		customerDTO.setIdType(IdType.NRCNO);
		customerDTO.setMaritalStatus(MaritalStatus.SINGLE);
		customerDTO.setpType(PassportType.WORKING);
		insuredPersonDTO.setCustomerDTO(customerDTO);
		initAddOnsConfig();
	}

	public void prepareEditInsuredPersonInfo(MedicalPolicyInsuredPersonDTO insuredPersonInfo) {
		this.createNewIsuredPersonInfo = false;
		this.insuredPersonDTO = new MedicalPolicyInsuredPersonDTO(insuredPersonInfo);
		if (insuredPersonDTO.getPolicyInsuredPersonBeneficiariesDtoList() != null) {
			createNewBeneficiariesInfoDTOMap();
			for (MedicalPolicyInsuredPersonBeneficiaryDTO bdto : insuredPersonDTO.getPolicyInsuredPersonBeneficiariesDtoList()) {
				beneficiariesInfoDTOMap.put(bdto.getTempId(), bdto);
			}
		}
		if (insuredPersonDTO.getGuardianDTO() != null) {
			guardianInfoDisable = false;
		}
		loadIdNo(insuredPersonDTO.getCustomerDTO());
		loadTownshipCodeByStateCode(insuredPersonDTO.getCustomerDTO().getStateCode());
	}

	public void createNewBeneficiariesInfoDTOMap() {
		beneficiariesInfoDTOMap = new HashMap<String, MedicalPolicyInsuredPersonBeneficiaryDTO>();
	}

	public void hideGuradianDialog() {
		PrimeFaces.current().executeScript("PF('guradianInfoEntryDialog').hide()");
	}

	public void prepareAddNewBeneficiariesInfo() {
		createNewBeneficiariesInfo();
		createNewBeneficiariesInfo = true;
		if (beneficaryCloneDTO != null) {
			beneficiariesInfoDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO(beneficaryCloneDTO);
			loadTownshipCodeByStateCode(beneficaryCloneDTO.getStateCode());
		}
	}

	public void saveBeneficiariesInfo() {
		ValidationResult result = validBeneficiary(beneficiariesInfoDTO);
		if (result.isVerified()) {
			beneficiariesInfoDTO.setFullIdNo();
			beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(), beneficiariesInfoDTO);
			createNewBeneficiariesInfo();
			hideBeneficiaryDialog();
		} else {
			for (ErrorMessage message : result.getErrorMeesages()) {
				addErrorMessage(message);
			}
		}
	}

	public void hideBeneficiaryDialog() {
		loadTownshipCodeByStateCode(insuredPersonDTO.getCustomerDTO().getStateCode());
		PrimeFaces.current().executeScript("PF('beneficiariesInfoEntryDialog').hide()");
	}

	public void prepareEditBeneficiariesInfo(MedicalPolicyInsuredPersonBeneficiaryDTO beneficiariesInfo) {
		beneficiariesInfo.loadTransientIdNo();
		this.beneficiariesInfoDTO = beneficiariesInfo;
		createNewBeneficiariesInfo = false;
		loadTownshipCodeByStateCode(beneficiariesInfo.getStateCode());
	}

	public void removeBeneficiariesInfo(MedicalPolicyInsuredPersonBeneficiaryDTO beneficiariesInfo) {
		beneficiariesInfoDTOMap.remove(beneficiariesInfo.getTempId());
		createNewBeneficiariesInfo();
	}

	private ValidationResult validBeneficiary(MedicalPolicyInsuredPersonBeneficiaryDTO beneficiariesInfo) {
		ValidationResult result = beneficiariesValidator.validate(beneficiariesInfo);
		if (beneficiariesInfoDTOMap.size() > 0) {
			int percentage = 0;
			for (MedicalPolicyInsuredPersonBeneficiaryDTO beneficiary : beneficiariesInfoDTOMap.values()) {
				percentage += beneficiary.getPercentage();
			}
			if (!beneficiariesInfoDTOMap.containsKey(beneficiariesInfo.getTempId())) {
				percentage += beneficiariesInfo.getPercentage();
			}
			if (percentage > 100) {
				result.addErrorMessage("beneficiaryInfoEntryForm" + ":percentage", MessageId.OVER_BENEFICIARY_PERCENTAGE);
			}
		}
		return result;
	}

	public List<MedicalPolicyInsuredPersonBeneficiaryDTO> getProposalInsuredPersonBeneficiariesList() {
		return new ArrayList<MedicalPolicyInsuredPersonBeneficiaryDTO>(sortByValue(beneficiariesInfoDTOMap).values());
	}

	private Map<String, MedicalPolicyInsuredPersonBeneficiaryDTO> sortByValue(Map<String, MedicalPolicyInsuredPersonBeneficiaryDTO> map) {
		List<Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO>> list = new LinkedList<Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO>>() {
			public int compare(Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO> o1, Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO> o2) {
				try {
					Long l1 = Long.parseLong(o1.getKey());
					Long l2 = Long.parseLong(o2.getKey());
					if (l1 > l2) {
						return 1;
					} else if (l1 < l2) {
						return -1;
					} else {
						return 0;
					}
				} catch (NumberFormatException e) {
					return 0;
				}
			}
		});

		Map<String, MedicalPolicyInsuredPersonBeneficiaryDTO> result = new LinkedHashMap<String, MedicalPolicyInsuredPersonBeneficiaryDTO>();
		for (Map.Entry<String, MedicalPolicyInsuredPersonBeneficiaryDTO> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	private void createNewBeneficiariesInfo() {
		beneficiariesInfoDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();
		beneficiariesInfoDTO.setIdType(IdType.NRCNO);
		beneficiariesInfoDTO.setPercentage(100.0f);
	}

	/************ beneficiary info end **************/

	public String updateMedicalPolicy() {
		String result = null;
		try {
			medicalPolicyDTO.setPolicyInsuredPersonDtoList(getMedicalInsuredPersonDTOList());
			MedicalPolicy medicalPolicy = MedicalPolicyFactory.createMedicalPolicy(medicalPolicyDTO);
			medicalPolicyService.overwriteMedicalPolicy(medicalPolicy);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_POLICY_PROCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	private void loadTownshipCodeByStateCode(String stateCode) {
		if (stateCode != null)
			townshipCodeList = townshipService.findTspShortNameByProvinceNo(stateCode);
	}

	public List<PolicyGuardianDTO> getGuradianPersonList() {
		if (insuredPersonDTO.getGuardianDTO() != null) {
			return Arrays.asList(insuredPersonDTO.getGuardianDTO());
		} else {
			return null;
		}
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public List<MedProInsuAddOnDTO> getInsuredPersonAddOnList() {
		return insuredPersonAddOnList;
	}

	public MaritalStatus[] getMaritalStatus() {
		return MaritalStatus.values();
	}

	public List<String> getStateCodeList() {
		return stateCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public IdType[] getIdTypes() {
		return IdType.values();
	}

	public IdConditionType[] getIdConditionType() {
		return IdConditionType.values();
	}

	public PassportType[] getPassportTypes() {
		return PassportType.values();
	}

	public Gender[] getGender() {
		return Gender.values();
	}

	public MedicalPolicyInsuredPersonBeneficiaryDTO getBeneficiariesInfoDTO() {
		return beneficiariesInfoDTO;
	}

	public void setBeneficiariesInfoDTO(MedicalPolicyInsuredPersonBeneficiaryDTO beneficiariesInfoDTO) {
		this.beneficiariesInfoDTO = beneficiariesInfoDTO;
	}

	public boolean isCreateNewIsuredPersonInfo() {
		return createNewIsuredPersonInfo;
	}

	public boolean getGuardianInfoDisable() {
		return guardianInfoDisable;
	}

	public void checkBeneficiariesIDNo(AjaxBehaviorEvent e) {
		IdType idType = (IdType) ((UIOutput) e.getSource()).getValue();
		resetIdNoInfo(idType, "B");
	}

	public void resetIdNoInfo(IdType idType, String customerType) {
		switch (idType) {
			case STILL_APPLYING:
				if ("C".equals(customerType)) {
					medicalPolicyDTO.getCustomer().setFullIdNo(null);
					medicalPolicyDTO.getCustomer().setStateCode(null);
					medicalPolicyDTO.getCustomer().setTownshipCode(null);
					medicalPolicyDTO.getCustomer().setIdConditionType(null);
					medicalPolicyDTO.getCustomer().setIdNo("");
				} else if ("I".equals(customerType)) {
					insuredPersonDTO.getCustomerDTO().setFullIdNo(null);
					insuredPersonDTO.getCustomerDTO().setStateCode(null);
					insuredPersonDTO.getCustomerDTO().setTownshipCode(null);
					insuredPersonDTO.getCustomerDTO().setIdConditionType(null);
					insuredPersonDTO.getCustomerDTO().setIdNo("");
				} else if ("B".equals(customerType)) {
					beneficiariesInfoDTO.setFullIdNo(null);
					beneficiariesInfoDTO.setStateCode(null);
					beneficiariesInfoDTO.setTownshipCode(null);
					beneficiariesInfoDTO.setIdConditionType(null);
					beneficiariesInfoDTO.setIdNo("");
				}
				break;
			default:
				break;
		}
	}

	public boolean isDisableInsureInfo() {
		if (HealthType.MICROHEALTH.equals(medicalPolicyDTO.getHealthType())) {
			return false;
		} else {
			return isIndividual() && getMedicalInsuredPersonDTOList().size() > 0 && createNewIsuredPersonInfo;
		}
	}

	private boolean isIndividual() {
		if (CustomerType.INDIVIDUALCUSTOMER.equals(medicalPolicyDTO.getCustomerType())) {
			return true;
		}
		return false;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MedicalPolicyInsuredPersonDTO getInsuredPersonDTO() {
		return insuredPersonDTO;
	}

	public void setInsuredPersonDTO(MedicalPolicyInsuredPersonDTO insuredPersonDTO) {
		this.insuredPersonDTO = insuredPersonDTO;
	}

	public List<MedicalPolicyInsuredPersonDTO> getMedicalInsuredPersonDTOList() {
		return new ArrayList<MedicalPolicyInsuredPersonDTO>(insuredPersonDTOMap.values());
	}

	public CustomerType[] getCustomerTypes() {
		return CustomerType.values();
	}

	public List<BankBranch> getBankBranchList() {
		return bankBranchList;
	}

	public boolean isOrganization() {
		return isOrganization;
	}

	public boolean isCreateNewBeneficiariesInfo() {
		return createNewBeneficiariesInfo;
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		medicalPolicy.setAgent(agent);
	}

	public void returnBeneficiariesTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		beneficiariesInfoDTO.getResidentAddress().setTownship(townShip);
	}

	public MedicalPolicyDTO getMedicalPolicyDTO() {
		return medicalPolicyDTO;
	}

	public void setMedicalPolicyDTO(MedicalPolicyDTO medicalPolicyDTO) {
		this.medicalPolicyDTO = medicalPolicyDTO;
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

}
