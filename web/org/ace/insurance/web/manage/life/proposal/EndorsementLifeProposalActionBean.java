package org.ace.insurance.web.manage.life.proposal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.RiskyOccupation;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.InsuredPersonPolicyHistoryRecord;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.bmiChart.service.interfaces.IBMIService;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ContractorType;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "EndorsementLifeProposalActionBean")
public class EndorsementLifeProposalActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	@ManagedProperty(value = "#{OrganizationService}")
	private IOrganizationService organizationService;

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@ManagedProperty(value = "#{CustomerService}")
	private ICustomerService customerService;

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@ManagedProperty(value = "#{OccupationService}")
	private IOccupationService occupationService;

	public void setOccupationService(IOccupationService occupationService) {
		this.occupationService = occupationService;
	}

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	@ManagedProperty(value = "#{BMIService}")
	private IBMIService bmiService;

	public void setBmiService(IBMIService bmiService) {
		this.bmiService = bmiService;
	}

	private User user;
	private User responsiblePerson;
	private LifeProposal lifeProposal;
	private LifeEndorseInfo lifeEndorseInfo;
	private LifePolicy lifepolicy;
	private Product product;
	private BeneficiariesInfoDTO beneficaryCloneDTO;
	private InsuredPersonPolicyHistoryRecord insuredRecord;

	private Map<String, InsuredPersonPolicyHistoryRecord> insuredRecordMap;
	private List<Product> productsList;
	private List<RelationShip> relationshipList;
	private List<String> provinceCodeList;
	private List<String> townshipCodeList;
	private List<String> iPersonTownshipCodeList;

	private String remark;

	private boolean createNew;
	private boolean isSportMan;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSnakeBite;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isMonthBase;
	private boolean isEndorse;
	private boolean isShortTermEndowment;
	private boolean isSTELendorse;
	private boolean isConfirmEdit;
	private boolean isSurveyAgain;
	private boolean printEndorseLetter;
	private boolean isNewInsuredRecord;
	private boolean isEnquiryEdit;
	private boolean isSelfRelation;

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifeProposal = (LifeProposal) getParam("lifeProposal");
		lifepolicy = (lifepolicy == null) ? (LifePolicy) getParam("lifePolicy") : lifepolicy;
		isEnquiryEdit = getParam("isEnquiryEdit") == null ? false : true;
		isConfirmEdit = isEnquiryEdit ? false : lifeProposal != null ? true : false;
		isEndorse = (lifepolicy != null) ? true : lifeProposal != null ? lifeProposal.getProposalType().equals(ProposalType.ENDORSEMENT) : false;
		destroy();

	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
		removeParam("lifeProposal");
		removeParam("isEnquiryEdit");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		productsList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		relationshipList = relationShipService.findAllRelationShip();
		provinceCodeList = provinceService.findAllProvinceNo();
		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();

		if (isEndorse) {
			if (isConfirmEdit) {
				lifeProposal.setPeriodMonth(lifeProposal.getPeriodOfYears());
				lifeProposal.setSubmittedDate(lifeProposal.getSubmittedDate());
			} else {
				lifeProposal = new LifeProposal(lifepolicy);
				lifeProposal.setSubmittedDate(new Date());
			}
			product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			loadRenderValues();
			isSTELendorse = KeyFactorChecker.isShortTermEndowment(product.getId());
		}

		initDataList();
		createNewInsuredPersonInfo();
		createNewBeneficiariesInfoDTOMap();
		createNewBeneficiariesInfo();
		createInsuredPersonAddOnDTO();
	}

	private void initDataList() {
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			insuredPersonInfoDTO = new InsuredPersonInfoDTO(pv);
			setRiskyOccupationForView(pv);
			this.product = insuredPersonInfoDTO.getProduct();
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
		}
		loadRenderValues();
	}

	private void setRiskyOccupationForView(ProposalInsuredPerson pv) {
		for (InsuredPersonKeyFactorValue InsuPersonKF : pv.getKeyFactorValueList()) {
			if (KeyFactorChecker.isRiskyOccupation(InsuPersonKF.getKeyFactor()) && InsuPersonKF.getValue().trim().equals("YES")) {
				insuredPersonInfoDTO.setIsRiskyOccupation(true);
				break;
			}
		}
	}

	/*************************************************
	 * Start Beneficiary Manage
	 *********************************************************/
	private boolean createNewBeneficiariesInfo;
	private BeneficiariesInfoDTO beneficiariesInfoDTO;
	private Map<String, BeneficiariesInfoDTO> beneficiariesInfoDTOMap;

	public IdType[] getIdTypes() {
		return IdType.values();
	}

	public Gender[] getGender() {
		return Gender.values();
	}

	public boolean isCreateNewBeneficiariesInfo() {
		return createNewBeneficiariesInfo;
	}

	private void createNewBeneficiariesInfo() {
		createNewBeneficiariesInfo = true;
		beneficiariesInfoDTO = new BeneficiariesInfoDTO();
		beneficiariesInfoDTO.setIdType(IdType.NRCNO);
	}

	public BeneficiariesInfoDTO getBeneficiariesInfoDTO() {
		return beneficiariesInfoDTO;
	}

	public void setBeneficiariesInfoDTO(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		this.beneficiariesInfoDTO = beneficiariesInfoDTO;
	}

	public void prepareAddNewBeneficiariesInfo() {
		createNewBeneficiariesInfo();
		if (beneficaryCloneDTO != null) {
			beneficiariesInfoDTO.setTempId(System.nanoTime() + "");
			beneficiariesInfoDTO.setExistEntity(false);
			beneficiariesInfoDTO.setAge(beneficaryCloneDTO.getAge());
			beneficiariesInfoDTO.setPercentage(beneficaryCloneDTO.getPercentage());
			beneficiariesInfoDTO.setInitialId(beneficaryCloneDTO.getInitialId());
			beneficiariesInfoDTO.setFullIdNo(beneficaryCloneDTO.getFullIdNo());
			beneficiariesInfoDTO.setGender(beneficaryCloneDTO.getGender());
			beneficiariesInfoDTO.setIdType(beneficaryCloneDTO.getIdType());
			ResidentAddress address = new ResidentAddress();
			address.setTownship(beneficaryCloneDTO.getResidentAddress().getTownship());
			address.setResidentAddress(beneficaryCloneDTO.getResidentAddress().getResidentAddress());
			beneficiariesInfoDTO.setResidentAddress(address);
			Name name = new Name();
			name.setFirstName(beneficaryCloneDTO.getName().getFirstName());
			name.setMiddleName(beneficaryCloneDTO.getName().getMiddleName());
			name.setLastName(beneficaryCloneDTO.getName().getLastName());
			beneficiariesInfoDTO.setName(name);
			beneficiariesInfoDTO.setRelationship(beneficaryCloneDTO.getRelationship());
			beneficiariesInfoDTO.loadFullIdNo();
			changeBenefiProvinceCode();
		}
	}

	public void prepareEditBeneficiariesInfo(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		beneficiariesInfoDTO.loadFullIdNo();
		this.beneficiariesInfoDTO = new BeneficiariesInfoDTO(beneficiariesInfoDTO);
		if (insuredPersonInfoDTO.getProvinceCode() != null)
			changeBenefiProvinceCode();
		this.createNewBeneficiariesInfo = false;
	}

	public void clearNRC() {
		beneficiariesInfoDTO.setProvinceCode(null);
		beneficiariesInfoDTO.setTownshipCode(null);
		beneficiariesInfoDTO.setIdConditionType(null);
		beneficiariesInfoDTO.setIdNo(null);
	}

	public Map<String, BeneficiariesInfoDTO> sortByValue(Map<String, BeneficiariesInfoDTO> map) {
		List<Map.Entry<String, BeneficiariesInfoDTO>> list = new LinkedList<Map.Entry<String, BeneficiariesInfoDTO>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, BeneficiariesInfoDTO>>() {
			public int compare(Map.Entry<String, BeneficiariesInfoDTO> o1, Map.Entry<String, BeneficiariesInfoDTO> o2) {
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

		Map<String, BeneficiariesInfoDTO> result = new LinkedHashMap<String, BeneficiariesInfoDTO>();
		for (Map.Entry<String, BeneficiariesInfoDTO> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public Map<String, InsuredPersonPolicyHistoryRecord> sortByValues(Map<String, InsuredPersonPolicyHistoryRecord> map) {
		List<Map.Entry<String, InsuredPersonPolicyHistoryRecord>> list = new LinkedList<Map.Entry<String, InsuredPersonPolicyHistoryRecord>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, InsuredPersonPolicyHistoryRecord>>() {
			public int compare(Map.Entry<String, InsuredPersonPolicyHistoryRecord> o1, Map.Entry<String, InsuredPersonPolicyHistoryRecord> o2) {
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

		Map<String, InsuredPersonPolicyHistoryRecord> result = new LinkedHashMap<String, InsuredPersonPolicyHistoryRecord>();
		for (Map.Entry<String, InsuredPersonPolicyHistoryRecord> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public void saveBeneficiariesInfo() {
		beneficiariesInfoDTO.setIdNo(beneficiariesInfoDTO.setBenefFullIdNo());
		beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(), beneficiariesInfoDTO);
		insuredPersonInfoDTO.setBeneficiariesInfoDTOList(new ArrayList<BeneficiariesInfoDTO>(sortByValue(beneficiariesInfoDTOMap).values()));
		createNewBeneficiariesInfo();
		RequestContext.getCurrentInstance().execute("PF('beneficiariesInfoEntryDialog').hide()");
	}

	public void removeBeneficiariesInfo(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		beneficiariesInfoDTOMap.remove(beneficiariesInfoDTO.getTempId());
		insuredPersonInfoDTO.getBeneficiariesInfoDTOList().remove(beneficiariesInfoDTO);
		createNewBeneficiariesInfo();
	}

	private boolean createNewAddOn;
	private InsuredPersonAddOnDTO insuredPersonAddOnDTO;
	private Map<String, InsuredPersonAddOnDTO> insuredPersonAddOnDTOMap;

	public boolean isCreateNewAddOn() {
		return createNewAddOn;
	}

	private void createInsuredPersonAddOnDTO() {
		createNewAddOn = true;
		insuredPersonAddOnDTO = new InsuredPersonAddOnDTO();
		insuredPersonAddOnDTOMap = new HashMap<String, InsuredPersonAddOnDTO>();
	}

	public InsuredPersonAddOnDTO getInsuredPersonAddOnDTO() {
		return insuredPersonAddOnDTO;
	}

	public void setInsuredPersonAddOnDTO(InsuredPersonAddOnDTO insuredPersonAddOnDTO) {
		this.insuredPersonAddOnDTO = insuredPersonAddOnDTO;
	}

	public void prepareInsuredPersonAddOnDTO() {
		createInsuredPersonAddOnDTO();
	}

	public void prepareEditInsuredPersonAddOnDTO(InsuredPersonAddOnDTO insuredPersonAddOnDTO) {
		this.insuredPersonAddOnDTO = insuredPersonAddOnDTO;
		this.createNewAddOn = false;
	}

	public void saveInsuredPersonAddOnDTO() {
		if (validInsuredPersonAddOn()) {
			insuredPersonInfoDTO.addInsuredPersonAddOn(insuredPersonAddOnDTO);
			createInsuredPersonAddOnDTO();
		}
	}

	public void removeAddOn(InsuredPersonAddOnDTO insuredPersonAddOnDTO) {
		insuredPersonInfoDTO.removeInsuredPersonAddOn(insuredPersonAddOnDTO);
		createInsuredPersonAddOnDTO();
	}

	@SuppressWarnings("deprecation")
	private boolean validInsuredPersonAddOn() {
		boolean valid = true;
		if (insuredPersonAddOnDTO.getAddOn() == null) {
			addErrorMessage("addOnEntryForm" + ":addOn", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (valid) {
			RequestContext.getCurrentInstance().execute("addOnEntryDialog.hide()");
		}
		return valid;
	}

	/*************************************************
	 * End AddOn Manage
	 *********************************************************/

	/*************************************************
	 * Start InsuredPerson Manage
	 ******************************************************/
	private InsuredPersonInfoDTO insuredPersonInfoDTO;
	private Map<String, InsuredPersonInfoDTO> insuredPersonInfoDTOMap;
	private boolean createNewIsuredPersonInfo;
	private Boolean isEdit = false;

	public boolean isCreateNewInsuredPersonInfo() {
		return createNewIsuredPersonInfo;
	}

	public void createNewInsuredPersonInfoDTOMap() {
		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();
	}

	public void changeProduct(AjaxBehaviorEvent e) {
		loadRenderValues();
	}

	private void loadRenderValues() {
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		isPersonalAccident = (KeyFactorChecker.isPersonalAccident(product));
		isShortTermEndowment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isMonthBase = isPersonalAccident;
	}

	public void changeRelationship(AjaxBehaviorEvent e) {
		if (getSelfRelationship().equals(insuredPersonInfoDTO.getRelationship())) {
			boolean isSelfRSAlreadyContain = false;
			for (InsuredPersonInfoDTO insuPInfoDTO : getInsuredPersonInfoDTOList()) {
				if (getSelfRelationship().equals(insuPInfoDTO.getRelationship())) {
					isSelfRSAlreadyContain = true;
					break;
				}
			}
			if (isSelfRSAlreadyContain) {
				insuredPersonInfoDTO.setRelationship(null);
				addErrorMessage("lifeProposalEntryForm" + ":insuredPersonRs", MessageId.ALREADY_ADD_SELF_RELATION);
			} else {
				changeCusToInsuredPerson(lifeProposal.getCustomer());
				insuredPersonInfoDTO.setRelationship(getSelfRelationship());
				isSelfRelation = true;
			}
		}

	}

	private void changeCusToInsuredPerson(Customer customer) {
		insuredPersonInfoDTO.setCustomer(customer);
		insuredPersonInfoDTO.setInitialId(customer.getInitialId());
		insuredPersonInfoDTO.setName(customer.getName());
		insuredPersonInfoDTO.setFatherName(customer.getFatherName());
		insuredPersonInfoDTO.setIdType(customer.getIdType());
		insuredPersonInfoDTO.setFullIdNo(IdType.STILL_APPLYING.equals(customer.getIdType()) ? "" : customer.getFullIdNo());
		insuredPersonInfoDTO.loadFullIdNo();
		if (insuredPersonInfoDTO.getProvinceCode() != null)
			changeIPersonProvinceCode();
		insuredPersonInfoDTO.setDateOfBirth(customer.getDateOfBirth());
		insuredPersonInfoDTO.setResidentAddress(customer.getResidentAddress());
		insuredPersonInfoDTO.setOccupation(customer.getOccupation());
		insuredPersonInfoDTO.setGender(customer.getGender());
	}

	private void createNewInsuredPersonInfo() {
		createNewIsuredPersonInfo = true;
		insuredPersonInfoDTO = new InsuredPersonInfoDTO();
		insuredPersonInfoDTO.setStartDate(lifeProposal.getStartDate());
		beneficiariesInfoDTOMap = new HashMap<String, BeneficiariesInfoDTO>();
		createNewInsuredRecordList();
		createNewInsuredRecord();
		isEdit = false;
		isSelfRelation = false;
	}

	public boolean getIsSportMan() {
		return isSportMan;
	}

	public boolean getIsMonthBase() {
		return isMonthBase;
	}

	public boolean getIsEndorse() {
		return isEndorse;
	}

	public boolean getIsPersonalAccident() {
		return isPersonalAccident;
	}

	public List<Integer> getPeriodMonths() {
		return Arrays.asList(3, 6, 12);
	}

	/* Short Term Endowment Life */
	public List<Integer> getSePeriodYears() {
		return Arrays.asList(5, 7, 10);
	}

	public InsuredPersonInfoDTO getInsuredPersonInfoDTO() {
		return insuredPersonInfoDTO;
	}

	public void setInsuredPersonInfoDTO(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
	}

	public List<InsuredPersonInfoDTO> getInsuredPersonInfoDTOList() {
		List<InsuredPersonInfoDTO> insuDTOList = new ArrayList<InsuredPersonInfoDTO>();
		if (insuredPersonInfoDTOMap == null || insuredPersonInfoDTOMap.values() == null) {
			return new ArrayList<InsuredPersonInfoDTO>();
		} else {
			for (InsuredPersonInfoDTO dto : insuredPersonInfoDTOMap.values()) {
				if (dto.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
					insuDTOList.add(dto);
				}
			}
		}
		return insuDTOList;
	}

	public void prepareAddNewInsuredPersonInfo() {
		createNewInsuredPersonInfo();
		createNewBeneficiariesInfoDTOMap();
	}

	public void prepareEditInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		insuredPersonInfoDTO.loadFullIdNo();
		this.insuredPersonInfoDTO = new InsuredPersonInfoDTO(insuredPersonInfoDTO);
		if (insuredPersonInfoDTO.getProvinceCode() != null)
			changeIPersonProvinceCode();
		if (insuredPersonInfoDTO.getBeneficiariesInfoDTOList() != null) {
			createNewBeneficiariesInfoDTOMap();
			for (BeneficiariesInfoDTO bdto : insuredPersonInfoDTO.getBeneficiariesInfoDTOList()) {
				beneficiariesInfoDTOMap.put(bdto.getTempId(), bdto);
			}
		}
		if (insuredPersonInfoDTO.getInsuredPersonPolicyHistoryRecordList() != null) {
			createNewInsuredRecordList();
			for (InsuredPersonPolicyHistoryRecord record : insuredPersonInfoDTO.getInsuredPersonPolicyHistoryRecordList()) {
				insuredRecordMap.put(record.getTempId(), record);
			}
		}
		if (lifeProposal.getCustomer() != null) {
			if (lifeProposal.getCustomer().equals(insuredPersonInfoDTO.getCustomer()))
				isSelfRelation = true;
			else
				isSelfRelation = false;
		}
		createNewIsuredPersonInfo = false;
		isEdit = true;

	}

	// public void prepareReplaceInsuredPersonInfo(InsuredPersonInfoDTO
	// insuredPersonInfoDTO) {
	// this.insuredPersonInfoDTO = insuredPersonInfoDTO;
	// createNewBeneficiariesInfoDTOMap();
	// createNewIsuredPersonInfo = false;
	// isReplace = true;
	// }

	private UploadedFile uploadedFile;

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void upload(ActionEvent event) {
		try {
			InputStream inputStream = uploadedFile.getInputstream();
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			boolean readAgain = true;
			int i = 1;
			while (readAgain) {
				InsuredPersonInfoDTO insuredPersonInfoDTO = new InsuredPersonInfoDTO();
				ResidentAddress ra = new ResidentAddress();
				Name name = new Name();
				Row row = sheet.getRow(i);
				if (row == null) {
					readAgain = false;
					break;
				}
				String initialId = row.getCell(0).getStringCellValue();
				if (initialId == null || initialId.isEmpty()) {
					readAgain = false;
					break;
				} else {
					insuredPersonInfoDTO.setInitialId(initialId);
					String firstName = row.getCell(1).getStringCellValue();
					name.setFirstName(firstName);
					String middleName = row.getCell(2).getStringCellValue();
					name.setMiddleName(middleName);
					String lastName = row.getCell(3).getStringCellValue();
					name.setLastName(lastName);

					String fatherName = row.getCell(4).getStringCellValue();
					insuredPersonInfoDTO.setFatherName(fatherName);

					String idNo = row.getCell(5).getStringCellValue();
					insuredPersonInfoDTO.setIdNo(idNo);

					IdType idType = IdType.valueOf(row.getCell(6).getStringCellValue());
					insuredPersonInfoDTO.setIdType(idType);

					Date dateOfBirth = row.getCell(7).getDateCellValue();
					insuredPersonInfoDTO.setDateOfBirth(dateOfBirth);

					double suminsured = row.getCell(8).getNumericCellValue();
					insuredPersonInfoDTO.setSumInsuredInfo(suminsured);

					String resAddress = row.getCell(9).getStringCellValue();
					ra.setResidentAddress(resAddress);
					String resTownshipId = row.getCell(10).getStringCellValue();
					Township township = townshipService.findTownshipById(resTownshipId);
					ra.setTownship(township);

					String occupationId = row.getCell(11).getStringCellValue();
					if (occupationId == null || occupationId.isEmpty()) {
						Occupation occupation = occupationService.findOccupationById(occupationId);
						insuredPersonInfoDTO.setOccupation(occupation);
					}

					Gender gender = Gender.valueOf(row.getCell(12).getStringCellValue());
					insuredPersonInfoDTO.setGender(gender);

					String productId = row.getCell(13).getStringCellValue();
					Product product = productService.findProductById(productId);
					insuredPersonInfoDTO.setProduct(product);

					int periodOfYears = (int) row.getCell(14).getNumericCellValue();
					lifeProposal.setPeriodMonth(periodOfYears);
					insuredPersonInfoDTO.setResidentAddress(ra);
					insuredPersonInfoDTO.setName(name);

					lifeProposal.setStartDate(new Date());
					Calendar cal = Calendar.getInstance();
					cal.setTime(lifeProposal.getStartDate());
					cal.add(Calendar.YEAR, lifeProposal.getPeriodOfYears());
					lifeProposal.setEndDate(cal.getTime());
					for (InsuredPersonKeyFactorValue insKFV : insuredPersonInfoDTO.getKeyFactorValueList()) {
						KeyFactor kf = insKFV.getKeyFactor();
						boolean isRiskyOccupation = insuredPersonInfoDTO.getIsRiskyOccupation();
						if (KeyFactorChecker.isSumInsured(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getSumInsuredInfo() + "");
						} else if (KeyFactorChecker.isTerm(kf)) {
							insKFV.setValue(lifeProposal.getPeriodOfYears() + "");
						} else if (KeyFactorChecker.isAge(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getAgeForNextYear() + "");
						} else if (KeyFactorChecker.isPound(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getPounds() + "");
						} else if (KeyFactorChecker.isDangerousOccupation(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getRiskyOccupation().getExtraRate() + "");
						} else if (KeyFactorChecker.isRiskyOccupation(kf)) {
							if (isRiskyOccupation)
								insKFV.setValue(RiskyOccupation.YES + "");
							else
								insKFV.setValue(RiskyOccupation.NO + "");
						}
					}
					insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
				}
				i++;
			}
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Failed to upload the file!"));
		} catch (InvalidFormatException e) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Invalid data is occured in Uploaded File!"));
		}
	}

	private void saveInsuredPersonInfo() {
		insuredPersonInfoDTO.setProduct(product);
		insuredPersonInfoDTO.setIdNo(insuredPersonInfoDTO.setFullIdNo());
		insuredPersonInfoDTO.setStartDate(lifeProposal.getStartDate());
		insuredPersonInfoDTO.setAge(insuredPersonInfoDTO.getAgeForNextYear());
		if (isEndownmentLife) {
			calculatePounds();
		}
		if (validInsuredPerson(insuredPersonInfoDTO)) {
			beneficaryCloneDTO = insuredPersonInfoDTO.getBeneficiariesInfoDTOList().get(0);
			if (!isEndorse) {
				setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getPeriodMonth(),
						insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(),
						insuredPersonInfoDTO.getRiskyOccupation() == null ? 0 : insuredPersonInfoDTO.getRiskyOccupation().getExtraRate());
				// For Endorsement
			} else {
				// Set InsuredPerson Endorsement Status
				PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
				if (policyInsuPerson == null) {
					insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.NEW);
				} else {
					InsuredPersonInfoDTO oldInsuInfoDTO = insuredPersonInfoDTOMap.get(insuredPersonInfoDTO.getTempId());
					if (isEdit == true && insuredPersonInfoDTO.getEndorsementStatus() != EndorsementStatus.REPLACE) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.EDIT);
					} else if (isEdit == true
							&& (oldInsuInfoDTO.getFullIdNo() != insuredPersonInfoDTO.getFullIdNo() || oldInsuInfoDTO.getFullName() != insuredPersonInfoDTO.getFullName())) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.REPLACE);
					}
				}
				// Set Key Factor For Public Life
				if (checkPublicLife()) {
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(lifepolicy.getInsuredPersonInfo().get(0).getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()),
								lifepolicy.getPeriodOfYears(), insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(),
								insuredPersonInfoDTO.getRiskyOccupation().getExtraRate());
					} else {
						if (isIncreasedSIAmount(insuredPersonInfoDTO)) {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifepolicy.getPeriodOfYears(),
									insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(), insuredPersonInfoDTO.getRiskyOccupation().getExtraRate());
						} else {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()), lifeProposal.getPeriodMonth(),
									insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(), insuredPersonInfoDTO.getRiskyOccupation().getExtraRate());
						}
					}

				}
				// Set Key Factor For Group Life
				else {
					int extraRate = insuredPersonInfoDTO.getRiskyOccupation() != null ? insuredPersonInfoDTO.getRiskyOccupation().getExtraRate() : 0;
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(policyInsuPerson.getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()),
								policyInsuPerson.getLifePolicy().getPeriodOfYears(), insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(), extraRate);
					} else {
						setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getPeriodMonth(),
								insuredPersonInfoDTO.getIsRiskyOccupation(), insuredPersonInfoDTO.getPounds(), extraRate);
					}
				}
			}
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			createNewInsuredPersonInfo();
			createNewBeneficiariesInfoDTOMap();
		}

	}

	public void calculatePounds() {
		insuredPersonInfoDTO.setHeight(insuredPersonInfoDTO.getFeets() * 12 + insuredPersonInfoDTO.getInches());
		if (insuredPersonInfoDTO.getHeight() < 58) {
			insuredPersonInfoDTO.setHeight(58);
		} else if (insuredPersonInfoDTO.getHeight() > 72) {
			insuredPersonInfoDTO.setHeight(72);
		}
		if (insuredPersonInfoDTO.getAgeForNextYear() < 20) {
			insuredPersonInfoDTO.setAge(20);
		} else if (insuredPersonInfoDTO.getAgeForNextYear() > 45) {
			insuredPersonInfoDTO.setAge(45);
		} else {
			insuredPersonInfoDTO.setAge(insuredPersonInfoDTO.getAgeForNextYear());
		}
		int bmiWeight = bmiService.findPoundByAgeAndHeight(insuredPersonInfoDTO.getAge(), insuredPersonInfoDTO.getHeight());
		insuredPersonInfoDTO.setPounds(bmiWeight);
	}

	public void createNewBeneficiariesInfoDTOMap() {
		beneficiariesInfoDTOMap = new HashMap<String, BeneficiariesInfoDTO>();
	}

	/**
	 * 
	 * This method is used to retrieve the available SI amount for an insured
	 * person.
	 * 
	 * @param insuredPersonInfoDTO
	 * @return double
	 */
	public double getAvailableSISportMan(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		double availableSI = 0.0;
		// FIXME CHANGED MAXIMUMSUMINSURE TO MAXVALUE
		double maxSi = insuredPersonInfoDTO.getProduct().getMaxValue();
		availableSI = maxSi - (getTotalSISportMan(insuredPersonInfoDTO));

		return availableSI;
	}

	/**
	 * 
	 * This method is used to retrieve the total SI amount of an insured
	 * person's active policies.
	 * 
	 * @param insuredPersonInfoDTO
	 * @return double
	 * 
	 */
	public double getTotalSISportMan(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		List<PolicyInsuredPerson> people = lifeProposalService.findPolicyInsuredPersonByParameters(insuredPersonInfoDTO.getName(), insuredPersonInfoDTO.getIdNo(),
				insuredPersonInfoDTO.getFatherName(), insuredPersonInfoDTO.getDateOfBirth());
		double sumInsured = 0.0;
		Date endDate;

		if (people != null) {
			for (PolicyInsuredPerson p : people) {
				endDate = p.getLifePolicy().getActivedPolicyEndDate();
				if (endDate.after(new Date()) && KeyFactorChecker.isSportMan(p.getProduct())) {
					sumInsured += p.getSumInsured();
				}
			}
		}

		return sumInsured;
	}

	/**
	 * 
	 * This method is used to decide whether an sport man insuredPerson's
	 * sumInsured amount is over sport man product's maximum SI or not in all of
	 * his/her active policies.
	 * 
	 * @param insuredPersonInfoDTO
	 * @return boolean true if SI is over.
	 */
	public boolean isExcessSISportMan(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		double sumInsured = getTotalSISportMan(insuredPersonInfoDTO) + insuredPersonInfoDTO.getSumInsuredInfo();
		boolean flag = false;

		if (sumInsured > insuredPersonInfoDTO.getProduct().getMaxValue()) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 
	 * This method is used to validate Insured Person's Information according to
	 * selected product type.
	 * 
	 * @param insuredPersonInfoDTO
	 * @return
	 */
	private boolean validInsuredPerson(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		boolean valid = true;
		String formID = "lifeProposalEntryForm";
		int maxAge = product.getMaxAge();
		int minAge = product.getMinAge();
		int personAge = insuredPersonInfoDTO.getAgeForNextYear();
		int periodofYear = isMonthBase ? lifeProposal.getPeriodMonth() / 12 : lifeProposal.getPeriodMonth();
		if (personAge < minAge) {
			addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, minAge);
			valid = false;
		} else if (personAge > maxAge) {
			if (isShortTermEndowment)
				addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, maxAge - 5);
			else
				addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, maxAge);
			valid = false;

		} else if (personAge + periodofYear > maxAge) {
			addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_YEARS, maxAge - personAge);
			valid = false;
		} else if (insuredPersonInfoDTO.getPounds() > 25) {
			addErrorMessage(formID + ":weight", MessageId.BMI_MAXIMUM_POUNDS_LIMATION);
			valid = false;
		}

		if (insuredPersonInfoDTO.getBeneficiariesInfoDTOList().isEmpty()) {
			addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.REQUIRED_BENEFICIARY_PERSON);
			valid = false;
		} else {
			float totalPercent = 0.0f;
			for (BeneficiariesInfoDTO beneficiary : insuredPersonInfoDTO.getBeneficiariesInfoDTOList()) {
				totalPercent += beneficiary.getPercentage();
			}
			if (totalPercent > 100) {
				addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.OVER_BENEFICIARY_PERCENTAGE);
				valid = false;
			}
			if (totalPercent < 100) {
				addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.LOWER_BENEFICIARY_PERCENTAGE);
				valid = false;
			}
		}

		if (lifepolicy != null && checkPublicLife()) {
			if (getPassedMonths() > lifepolicy.getPeriodOfMonths()) {
				int availablePeriod = getPassedYears();
				if (getPassedMonths() % 12 != 0) {
					availablePeriod = availablePeriod + 1;
				}
				addErrorMessage(formID + ":periodOfInsurance", MessageId.MINIMUN_INSURED_PERIOD, availablePeriod);
				valid = false;
			}
		}

		return valid;
	}

	private boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public void removeInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		if (!isEmpty(insuredPersonInfoDTO.getInsPersonCodeNo())) {
			insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.TERMINATE);
		} else {
			insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
		}
		createNewInsuredPersonInfo();
	}

	/**
	 * 
	 * This method is used to retrieve ProposalInsuredPerson instances from DTO
	 * Map.
	 * 
	 * @return A {@link List} of {@link ProposalInsuredPerson} instances
	 */
	public List<ProposalInsuredPerson> getInsuredPersonInfoList() {
		List<ProposalInsuredPerson> result = new ArrayList<ProposalInsuredPerson>();
		if (insuredPersonInfoDTOMap.values() != null) {
			for (InsuredPersonInfoDTO insuredPersonInfoDTO : insuredPersonInfoDTOMap.values()) {
				ProposalInsuredPerson proposalInsuredPerson = new ProposalInsuredPerson(insuredPersonInfoDTO);
				for (InsuredPersonKeyFactorValue kfv : proposalInsuredPerson.getKeyFactorValueList()) {
					if (KeyFactorChecker.isTerm(kfv.getKeyFactor())) {
						kfv.setValue(lifeProposal.getPeriodMonth() + "");
					}
				}
				proposalInsuredPerson.setLifeProposal(lifeProposal);
				proposalInsuredPerson.setAge(proposalInsuredPerson.getAgeForNextYear());
				result.add(proposalInsuredPerson);

			}

		}
		return result;
	}

	public String backLifeProposal() {
		createNewInsuredPersonInfo();
		return "lifeProposal";
	}

	/*************************************************
	 * End InsuredPerson Manage
	 ********************************************************/
	/*************************************************
	 * Proposal Manage
	 ********************************************/
	private String contractorType;

	public String getContractorType() {
		if (lifeProposal.getOrganization() != null) {
			contractorType = ContractorType.ORGANIZATION.toString();
		} else if (lifeProposal.getCustomer() != null) {
			contractorType = ContractorType.CUSTOMER.toString();
		}
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public void changeOrgEvent(AjaxBehaviorEvent event) {
		if (contractorType.equals(ContractorType.CUSTOMER.toString())) {
			lifeProposal.setOrganization(null);
		} else if (contractorType.equals(ContractorType.ORGANIZATION.toString())) {
			lifeProposal.setCustomer(null);
		}
	}

	public void changeSaleEvent(AjaxBehaviorEvent event) {
		lifeProposal.setSaleBank(null);
		lifeProposal.setAgent(null);
	}

	/*************************************************
	 * Proposal Manage
	 **********************************************/

	public void createNewLifeProposal() {
		createNew = true;
		lifeProposal = new LifeProposal();
		lifeProposal.setSubmittedDate(new Date());
		lifeProposal.setStartDate(new Date());
		lifeProposal.setBranch(user.getLoginBranch());
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public String addNewLifeProposal() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = null;
			WorkflowTask workflowTask = null;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndowment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			lifeProposal.setPeriodMonth(getPeriodOfMonths());
			workflowTask = isSurveyAgain ? WorkflowTask.SURVEY : WorkflowTask.APPROVAL;
			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.ENDORSEMENT, user,
					responsiblePerson);
			lifeProposal.setProposalType(ProposalType.ENDORSEMENT);
			if (isConfirmEdit) {
				lifeEndorsementService.updateLifeProposal(lifeProposal, lifeEndorseInfo, workFlowDTO);
			} else if (isEnquiryEdit) {
				lifeEndorsementService.updateLifeProposal(lifeProposal, lifeEndorseInfo, null);
			} else {
				if (lifeProposal.isNonFinancialEndorse()) {
					lifeProposal = lifeEndorsementService.addNewNonFinancialLifeProposal(lifeProposal, lifeEndorseInfo, RequestStatus.FINISHED.name());
					printEndorseLetter = true;
				} else if (isSTELendorse) {
					lifeProposal = lifeEndorsementService.addNewShortEndowLifeProposal(lifeProposal, workFlowDTO, RequestStatus.PROPOSED.name(), lifeEndorseInfo);
				} else {
					lifeProposal = lifeEndorsementService.addNewLifeProposal(lifeProposal, lifeEndorseInfo, workFlowDTO, RequestStatus.PROPOSED.name());
				}
			}
			result = "dashboard";
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			createNewLifeProposal();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public int getPeriodOfMonths() {
		if (KeyFactorChecker.isPersonalAccident(product)) {
			return lifeProposal.getPeriodMonth();
		} else {
			return lifeProposal.getPeriodMonth() * 12;
		}
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("proposalInfo".equals(event.getOldStep()) && "InsuredPersonInfo".equals(event.getNewStep())) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(lifeProposal.getStartDate());
			if (isMonthBase) {
				cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
			} else {
				cal.add(Calendar.YEAR, lifeProposal.getPeriodMonth());
			}
			lifeProposal.setEndDate(cal.getTime());
		}
		if ("InsuredPersonInfo".equals(event.getOldStep()) && "showPremium".equals(event.getNewStep())) {
			int personSize = getInsuredPersonInfoDTOList().size();
			if (personSize < 1) {
				addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable", MessageId.REQUIRED_INSURED_PERSION);
				valid = false;
			} else {
				if (KeyFactorChecker.isGroupLife(product)) {
					if (personSize < 5) {
						addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable", MessageId.REQUIRED_GROUPLIFE_INSURED_PERSON, 5);
						valid = false;
					}
				} else if (!(KeyFactorChecker.isPersonalAccident(product) || KeyFactorChecker.isPersonalAccidentUSD(product))) {
					if (personSize > 1) {
						addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable", MessageId.INVALID_INSURED_PERSON, product.getName());
						valid = false;
					}
				}

			}
			// if (isEdit) {
			// insuredPersonInfoDTO.setIdNo(insuredPersonInfoDTO.getFullIdNo());
			// insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
			// insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(),
			// insuredPersonInfoDTO);
			// }
			if (valid) {
				//
				// for (PolicyInsuredPerson oldIns :
				// lifeProposal.getLifePolicy().getPolicyInsuredPersonList())
				// for (PolicyInsuredPersonBeneficiaries oldBene :
				// oldIns.getPolicyInsuredPersonBeneficiariesList())
				// System.out.println("BEFORE " + oldBene.getFullName() + " Old
				// BENE NAME");

				lifeProposal.setInsuredPersonList(getInsuredPersonInfoList());
				//
				// for (ProposalInsuredPerson newIns :
				// lifeProposal.getProposalInsuredPersonList())
				// for (InsuredPersonBeneficiaries newBene :
				// newIns.getInsuredPersonBeneficiariesList())
				// System.out.println("AFter " + newBene.getFullName() + " New
				// BENE NAME");
				//
				// for (PolicyInsuredPerson oldIns :
				// lifeProposal.getLifePolicy().getPolicyInsuredPersonList())
				// for (PolicyInsuredPersonBeneficiaries oldBene :
				// oldIns.getPolicyInsuredPersonBeneficiariesList())
				// System.out.println("AFter " + oldBene.getFullName() + " Old
				// BENE NAME");
				lifeEndorseInfo = lifeEndorsementService.preCalculatePremium(lifeProposal);
			}

		}
		if ("showPremium".equals(event.getOldStep()) && "workflow".equals(event.getNewStep()))
			valid = true;
		return valid ? event.getNewStep() : event.getOldStep();

	}

	public Map<String, BeneficiariesInfoDTO> getBeneficiariesInfoDTOMap() {
		return beneficiariesInfoDTOMap;
	}

	public String getPublicLifeId() {
		return KeyFactorIDConfig.getPublicLifeId();
	}

	public String getGroupLifeId() {
		return KeyFactorIDConfig.getGroupLifeId();
	}

	public String getShortTermLifeId() {
		return KeyFactorIDConfig.getShortEndowLifeId();
	}

	/**
	 * Remove Later
	 * 
	 * @PSH
	 */
	private boolean checkShortTermLife() {
		Boolean isPublicLife = true;
		String productId = lifepolicy.getInsuredPersonInfo().get(0).getProduct().getId();
		if (productId.equals(getShortTermLifeId())) {
			isPublicLife = true;
		} else {
			isPublicLife = false;
		}
		return isPublicLife;
	}

	private boolean checkPublicLife() {
		Boolean isPublicLife = true;
		String productId = "";
		if (isConfirmEdit) {
			productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId();
		} else {
			productId = lifepolicy.getInsuredPersonInfo().get(0).getProduct().getId();
		}
		if (productId.equals(getPublicLifeId())) {
			isPublicLife = true;
		} else {
			isPublicLife = false;
		}
		return isPublicLife;
	}

	public int getAgeForOldYear(Date dob) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(lifepolicy.getCommenmanceDate());
		int currentYear = cal_1.get(Calendar.YEAR);

		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dob);
		cal_2.set(Calendar.YEAR, currentYear);
		if (lifepolicy.getCommenmanceDate().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dob);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dob);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	public Boolean isIncreasedSIAmount(InsuredPersonInfoDTO dto) {
		if (dto.getSumInsuredInfo() <= lifeProposal.getProposalInsuredPersonList().get(0).getProposedSumInsured()) {
			return false;
		}
		return true;
	}

	public Boolean isReplaceColumn() {
		if (lifepolicy != null && checkPublicLife() == false) {
			return true;
		}
		return false;
	}

	public Boolean isDecreasedSIAmount() {
		if (insuredPersonInfoDTO.getInsPersonCodeNo() != null) {
			PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
			if (insuredPersonInfoDTO.getSumInsuredInfo() < policyInsuPerson.getSumInsured()) {
				int passedMonths = getPassedMonths();
				int passedYear = passedMonths / 12;
				int period = lifeProposal.getPeriodOfYears();
				if ((period <= 12 && passedYear >= 2) || period > 12 && passedYear >= 3) {
					if (passedMonths % 12 > 5) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void addNewInsuredPersonInfo() {
		if (createNewIsuredPersonInfo) {
			saveInsuredPersonInfo();
		} else {
			if (isEndorse && isEdit && isDecreasedSIAmount() && checkPublicLife()) {
				PrimeFaces.current().executeScript("paidPremiumConfirmationDialog.show()");
			} else {
				saveInsuredPersonInfo();
			}
		}
	}

	public void paidPremiumForPaidUp(Boolean isPaid) {
		if (isPaid) {
			insuredPersonInfoDTO.setIsPaidPremiumForPaidup(true);
		} else {
			insuredPersonInfoDTO.setIsPaidPremiumForPaidup(false);
		}
		saveInsuredPersonInfo();
	}

	public int getPassedMonths() {
		DateTime vDate = new DateTime(lifeProposal.getLifePolicy().getActivedPolicyEndDate());
		DateTime cDate = new DateTime(lifeProposal.getLifePolicy().getCommenmanceDate());
		int paymentType = lifeProposal.getLifePolicy().getPaymentType().getMonth();
		int passedMonths = Months.monthsBetween(cDate, vDate).getMonths();
		if (paymentType > 0) {
			if (passedMonths % paymentType != 0) {
				passedMonths = passedMonths + 1;
			} // if paymentType is Lumpsum
		} else if (paymentType == 0) {
			if (passedMonths % 12 != 0) {
				passedMonths = passedMonths + 1;
			}
		}
		return passedMonths;
	}

	public int getPassedYears() {
		return getPassedMonths() / 12;
	}

	private void setKeyFactorValue(double sumInsured, int age, int period, boolean isRiskyOccupation, int pounds, int dangerousOccupation) {
		for (InsuredPersonKeyFactorValue vehKF : insuredPersonInfoDTO.getKeyFactorValueList()) {
			KeyFactor kf = vehKF.getKeyFactor();
			if (KeyFactorChecker.isSumInsured(kf)) {
				vehKF.setValue(sumInsured + "");
			} else if (KeyFactorChecker.isAge(kf) || KeyFactorChecker.isMedicalAge(kf)) {
				vehKF.setValue(age + "");
			} else if (KeyFactorChecker.isTerm(kf)) {
				vehKF.setValue(period + "");
			} else if (KeyFactorChecker.isRiskyOccupation(kf)) {
				if (isRiskyOccupation)
					vehKF.setValue(RiskyOccupation.YES + "");
				else
					vehKF.setValue(RiskyOccupation.NO + "");

			} else if (KeyFactorChecker.isPound(kf)) {
				vehKF.setValue(pounds + "");
			} else if (KeyFactorChecker.isDangerousOccupation(kf)) {
				vehKF.setValue(dangerousOccupation + "");
			}
		}
	}

	public int getAgeForNextYearEndose() {
		Calendar cal_1 = Calendar.getInstance();
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(insuredPersonInfoDTO.getDateOfBirth());
		cal_2.set(Calendar.YEAR, currentYear);
		if (lifepolicy == null) {
			cal_1.get(Calendar.YEAR);
		} else {
			cal_1.setTime(lifepolicy.getCommenmanceDate());
		}
		if (new Date().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(insuredPersonInfoDTO.getDateOfBirth());
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(insuredPersonInfoDTO.getDateOfBirth());
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	public void selectUser() {
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isSnakeBite ? WorkFlowType.SNAKE_BITE : isFarmer ? WorkFlowType.FARMER : isShortTermEndowment ? WorkFlowType.SHORT_ENDOWMENT : WorkFlowType.LIFE;
		WorkflowTask workflowTask = isEndorse ? WorkflowTask.SURVEY
				: (isSportMan || isSnakeBite || isPersonalAccident) ? WorkflowTask.APPROVAL : (isConfirmEdit && !isSurveyAgain) ? WorkflowTask.APPROVAL : WorkflowTask.SURVEY;
		selectUser(workflowTask, workFlowType, isEndorse ? TransactionType.ENDORSEMENT : TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	public void selectProduct() {
		selectProduct(InsuranceType.LIFE);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<PaymentType> getPaymentTypes() {
		if (product == null) {
			return new ArrayList<PaymentType>();
		} else {
			return product.getPaymentTypeList();
		}
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public int getMaximumTrem() {
		int result = 0;
		if (product != null) {
			result = product.getMaxTerm();
		}
		return result;
	}

	public int getMunimumTrem() {
		int result = 0;
		if (product != null) {
			result = product.getMinTerm();
		}
		return result;
	}

	public void selectAddOn() {
		selectAddOn(product);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		lifeProposal.setCustomer(customer);
		createNewInsuredPersonInfo();
		changeCusToInsuredPerson(lifeProposal.getCustomer());
		insuredPersonInfoDTO.setRelationship(getSelfRelationship());
		isSelfRelation = true;
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		lifeProposal.setOrganization(organization);
	}

	public void returnInsuredPerson(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		boolean IsCusAlreadyContain = false;
		for (InsuredPersonInfoDTO insurPInfoDTO : getInsuredPersonInfoDTOList()) {
			if (insurPInfoDTO.getCustomer() != null && insurPInfoDTO.getCustomer().getId() != null && customer.getId().equals(insurPInfoDTO.getCustomer().getId())) {
				IsCusAlreadyContain = true;
				break;
			}
		}
		if (IsCusAlreadyContain) {
			insuredPersonInfoDTO.setRelationship(null);
			addErrorMessage("lifeProposalEntryForm" + ":existingCustomer", MessageId.ALREADY_ADD_INSUREDPERSON, customer.getFullName());
		} else {
			changeCusToInsuredPerson(customer);
			if (customer.equals(lifeProposal.getCustomer())) {
				insuredPersonInfoDTO.setRelationship(getSelfRelationship());
				isSelfRelation = true;
			} else {
				insuredPersonInfoDTO.setRelationship(null);
				isSelfRelation = false;
			}
		}
	}

	public void resetInsuredPersonDTO() {
		insuredPersonInfoDTO.setInitialId(null);
		insuredPersonInfoDTO.setName(null);
		insuredPersonInfoDTO.setFatherName(null);
		insuredPersonInfoDTO.setDateOfBirth(null);
		insuredPersonInfoDTO.setIdType(null);
		insuredPersonInfoDTO.setIdNo(null);
		insuredPersonInfoDTO.setResidentAddress(null);
		insuredPersonInfoDTO.setOccupation(null);
		insuredPersonInfoDTO.setGender(null);
		insuredPersonInfoDTO.setRelationship(null);
		createNewInsuredPersonInfo();
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		lifeProposal.setAgent(agent);
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		lifeProposal.setSaleBank(bankBranch);
	}

	public void returnInsuredPersonTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		insuredPersonInfoDTO.getResidentAddress().setTownship(townShip);
	}

	public void returnBeneficiariesTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		beneficiariesInfoDTO.getResidentAddress().setTownship(townShip);
	}

	public void returnOccupation(SelectEvent event) {
		Occupation occupation = (Occupation) event.getObject();
		insuredPersonInfoDTO.setOccupation(occupation);
	}

	public void returnRiskyOccupation(SelectEvent event) {
		org.ace.insurance.system.common.riskyOccupation.RiskyOccupation riskyOccupation = (org.ace.insurance.system.common.riskyOccupation.RiskyOccupation) event.getObject();
		insuredPersonInfoDTO.setRiskyOccupation(riskyOccupation);
	}

	public void returnTypesOfSport(SelectEvent event) {
		TypesOfSport typesOfSport = (TypesOfSport) event.getObject();
		insuredPersonInfoDTO.setTypesOfSport(typesOfSport);
	}

	public void returnCoinsurCompany(SelectEvent event) {
		CoinsuranceCompany coinsurCompany = (CoinsuranceCompany) event.getObject();
		insuredRecord.setCompany(coinsurCompany);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		lifeProposal.setSalesPoints(salesPoints);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		insuredRecord.setProduct(product);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnAddOn(SelectEvent event) {
		@SuppressWarnings("unchecked")
		List<AddOn> addOnList = (List<AddOn>) event.getObject();
		if (!addOnList.isEmpty()) {
			for (AddOn addOn : addOnList) {
				insuredPersonAddOnDTO.setAddOn(addOn);
				boolean flag = true;
				addon: for (InsuredPersonAddOnDTO fa : insuredPersonAddOnDTOMap.values()) {
					if (fa.getAddOn().getId().equals(insuredPersonAddOnDTO.getAddOn().getId())) {
						flag = false;
						break addon;
					}
				}
				if (flag) {
					insuredPersonAddOnDTOMap.put(insuredPersonAddOnDTO.getTempId(), insuredPersonAddOnDTO);
				}
				createNewInsurePersonAddOnDTO();
			}
		}

		if (!insuredPersonAddOnDTOMap.isEmpty()) {
			insuredPersonInfoDTO.setInsuredPersonAddOnDTOList(new ArrayList<InsuredPersonAddOnDTO>(insuredPersonAddOnDTOMap.values()));
		}
	}

	public void createNewInsurePersonAddOnDTO() {
		insuredPersonAddOnDTO = new InsuredPersonAddOnDTO();
	}

	/*************************************************
	 * Start Insured Record
	 *********************************************************/

	public void createNewInsuredRecord() {
		insuredRecord = new InsuredPersonPolicyHistoryRecord();
		isNewInsuredRecord = true;
	}

	private void createNewInsuredRecordList() {
		insuredRecordMap = new HashMap<>();
	}

	public void applyInsuredRecord() {
		insuredRecordMap.put(insuredRecord.getTempId(), insuredRecord);
		insuredPersonInfoDTO.setInsuredPersonPolicyHistoryRecordList(new ArrayList<InsuredPersonPolicyHistoryRecord>(sortByValues(insuredRecordMap).values()));
		isNewInsuredRecord = true;
		createNewInsuredRecord();
	}

	public List<InsuredPersonPolicyHistoryRecord> getInsuredRecordList() {
		return new ArrayList<>(insuredRecordMap.values());
	}

	public void prepareUpdateInsuredRecord(InsuredPersonPolicyHistoryRecord insuredRecord) {
		isNewInsuredRecord = false;
		this.insuredRecord = insuredRecord;
	}

	public void deleteInsuredRecord(InsuredPersonPolicyHistoryRecord insuredRecord) {
		this.insuredRecordMap.remove(insuredRecord.getTempId());
		insuredPersonInfoDTO.getInsuredPersonPolicyHistoryRecordList().remove(insuredRecord);
		createNewInsuredRecord();
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public boolean getIsShortTermEndowment() {
		return isShortTermEndowment;
	}

	public boolean isSTELendorse() {
		return isSTELendorse;
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public List<String> getiPersonTownshipCodeList() {
		return iPersonTownshipCodeList;
	}

	public boolean getIsConfirmEdit() {
		return isConfirmEdit;
	}

	public boolean isSurveyAgain() {
		return isSurveyAgain;
	}

	public void setSurveyAgain(boolean isSurveyAgain) {
		this.isSurveyAgain = isSurveyAgain;
	}

	public boolean isPrintEndorseLetter() {
		return printEndorseLetter;
	}

	public InsuredPersonPolicyHistoryRecord getInsuredRecord() {
		return insuredRecord;
	}

	public void setInsuredRecord(InsuredPersonPolicyHistoryRecord insuredRecord) {
		this.insuredRecord = insuredRecord;
	}

	public void changeIPersonProvinceCode() {
		iPersonTownshipCodeList = new ArrayList<>();
		iPersonTownshipCodeList = townshipService.findTspShortNameByProvinceNo(insuredPersonInfoDTO.getProvinceCode());
	}

	public void changeBenefiProvinceCode() {
		townshipCodeList = new ArrayList<>();
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(beneficiariesInfoDTO.getProvinceCode());
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
	}

	public void setTownshipCodeList(List<String> townshipCodeList) {
		this.townshipCodeList = townshipCodeList;
	}

	public boolean getIsSnakeBite() {
		return isSnakeBite;
	}

	public boolean getIsFarmer() {
		return isFarmer;
	}

	public boolean getIsGroupLife() {
		return isGroupLife;
	}

	public boolean getIsEndownmentLife() {
		return isEndownmentLife;
	}

	public Boolean getIsEnquiryEdit() {
		return isEnquiryEdit;
	}

	public boolean isNewInsuredRecord() {
		return isNewInsuredRecord;
	}

	public void setNewInsuredRecord(boolean isNewInsuredRecord) {
		this.isNewInsuredRecord = isNewInsuredRecord;
	}

	public boolean isSelfRelation() {
		return isSelfRelation;
	}

	private RelationShip getSelfRelationship() {
		RelationShip result = null;
		for (RelationShip rs : relationshipList) {
			if ("SELF".equalsIgnoreCase(rs.getName().trim())) {
				result = rs;
				break;
			}
		}
		return result;
	}

	public String getPageHeader() {
		return (isEndorse ? "Life Endorsement" : isFarmer ? "Add New Farmer" : isPersonalAccident ? "Add New Personal Accident" : "Add New Life") + " Proposal";
	}

	public User getUser() {
		return user;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
