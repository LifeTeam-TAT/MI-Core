package org.ace.insurance.web.manage.life.studentLife.proposal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdConditionType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.proposal.ClassificationOfHealth;
import org.ace.insurance.life.proposal.InsuredPersonAttachment;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.InsuredPersonPolicyHistoryRecord;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.gradeinfo.GradeInfo;
import org.ace.insurance.system.common.gradeinfo.service.interfaces.IGradeInfoService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.province.service.interfaces.IProvinceService;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.salesPoints.service.interfaces.ISalesPointsService;
import org.ace.insurance.system.common.school.School;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ContractorType;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.common.WebUtils;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonInfoDTO;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "AddNewStudentLifeProposalActionBean")
public class AddNewStudentLifeProposalActionBean extends BaseBean implements Serializable {
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

	@ManagedProperty(value = "#{SalesPointsService}")
	private ISalesPointsService salePointService;

	public void setSalePointService(ISalesPointsService salePointService) {
		this.salePointService = salePointService;
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

	@ManagedProperty(value = "#{GradeInfoService}")
	private IGradeInfoService gradeInfoService;

	public void setGradeInfoService(IGradeInfoService gradeInfoService) {
		this.gradeInfoService = gradeInfoService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private LifePolicy lifepolicy;
	private List<RelationShip> relationshipList;
	private List<GradeInfo> gradeInfoList;
	private boolean createNew;
	private String remark;
	private User responsiblePerson;
	private String userType;
	private Product product;
	private String productId;
	private List<String> provinceCodeList = new ArrayList<String>();
	private List<String> townshipCodeList = new ArrayList<String>();
	private List<String> fatherTownshipCodeList = new ArrayList<String>();
	private List<String> motherTownshipCodeList = new ArrayList<String>();
	private List<String> customerTownshipCodeList = new ArrayList<String>();
	private boolean createNewIsuredPersonInfo;
	private InsuredPersonPolicyHistoryRecord insuranceHistoryRecord;
	private Map<String, InsuredPersonPolicyHistoryRecord> insuranceHistoryRecordMap;

	private boolean isEnquiryEdit;
	private boolean isEditProposal;
	private boolean surveyAgain;

	private final String BIRTH_CERTIFICATE_DIR = "/upload/life-proposal/";
	private String temporyDir;
	private Map<String, String> birthCertificateUploadedFileMap;
	private String tempId;
	private boolean isAgeAbove7;
	private boolean editInsuHistRec;
	private int maxTerm;
	private boolean sameCustomerAdderess;

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifepolicy = (LifePolicy) getParam("lifePolicy");
		productId = KeyFactorChecker.getStudentLifeID();
		product = productService.findProductById(productId);
		if (isExistParam("editLifeProposal")) {
			isEditProposal = true;
			lifeProposal = (LifeProposal) getParam("lifeProposal");
			userType = lifeProposal.getUserType();
		} else if (isExistParam("enquiryEditLifeProposal")) {
			isEnquiryEdit = true;
			lifeProposal = (LifeProposal) getParam("lifeProposal");
			userType = lifeProposal.getUserType();
		}
		putParam("PRODUCT", product);
		maxTerm = product.getMaxTerm();
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
		removeParam("lifeProposal");
		removeParam("editLifeProposal");
		removeParam("enquiryEditLifeProposal");
		removeParam("isNew");
	}

	@PostConstruct
	public void init() {
		editInsuHistRec = false;
		insuranceHistoryRecord = new InsuredPersonPolicyHistoryRecord();
		insuranceHistoryRecordMap = new HashMap<String, InsuredPersonPolicyHistoryRecord>();
		surveyAgain = true;
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";
		initializeInjection();
		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();
		createNewInsuredPersonInfo();
		tempId = insuredPersonInfoDTO.getTempId();
		if (lifeProposal == null) {
			createNewLifeProposal();
		}

		if (isEditProposal || isEnquiryEdit) {
			List<InsuredPersonInfoDTO> insuredPersonDTOList = new ArrayList<InsuredPersonInfoDTO>();
			insuredPersonDTOList.add(new InsuredPersonInfoDTO(lifeProposal.getProposalInsuredPersonList().get(0)));

			if (lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment().size() > 0) {
				String srcPath = getUploadPath() + BIRTH_CERTIFICATE_DIR + lifeProposal.getId();
				String destPath = getUploadPath() + temporyDir + lifeProposal.getId() + "/";
				try {
					FileHandler.copyDirectory(srcPath, destPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			lifeProposal.getCustomer().setIdType(lifeProposal.getCustomer().getIdType());
			for (InsuredPersonInfoDTO person : insuredPersonDTOList) {
				insuredPersonInfoDTOMap.put(person.getTempId(), person);
			}
		}
		relationshipList = relationShipService.findAllRelationShip();
		gradeInfoList = gradeInfoService.findAllGradeInfo();
		provinceCodeList = provinceService.findAllProvinceNo();
	}

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

	public void changeGender() {
		RelationShip relationShip = new RelationShip();
		if (insuredPersonInfoDTO.getGender().equals(Gender.MALE)) {
			relationShip = relationShipService.findRelationShipById(KeyFactorChecker.getSonfromRelationShipTable());
			insuredPersonInfoDTO.setRelationship(relationShip);
		} else {
			relationShip = relationShipService.findRelationShipById(KeyFactorChecker.getDaughterfromRelationShipTable());
			insuredPersonInfoDTO.setRelationship(relationShip);
		}
	}

	public void changeFatherStateCode() {
		String stateCode;
		if (lifeProposal.getCustomer().getGender().equals(Gender.MALE) && lifeProposal.getCustomer().getStateCode() != null) {
			stateCode = lifeProposal.getCustomer().getStateCode();
		} else {
			stateCode = insuredPersonInfoDTO.getParentProvinceCode();
		}
		fatherTownshipCodeList = townshipService.findTspShortNameByProvinceNo(stateCode);
	}

	public void changeMotherStateCode() {
		String stateCode;
		if (lifeProposal.getCustomer().getGender().equals(Gender.FEMALE) && lifeProposal.getCustomer().getStateCode() != null) {
			stateCode = lifeProposal.getCustomer().getStateCode();
		} else {
			stateCode = insuredPersonInfoDTO.getParentProvinceCode();
		}
		motherTownshipCodeList = townshipService.findTspShortNameByProvinceNo(stateCode);
	}

	public void changeStateCode() {
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(insuredPersonInfoDTO.getProvinceCode());
	}

	public void changeCustomerStateCodeList() {
		if (lifeProposal.getCustomer().getStateCode() != null) {
			customerTownshipCodeList = townshipService.findTspShortNameByProvinceNo(lifeProposal.getCustomer().getStateCode());
		}
	}

	public IdType[] getIdTypes() {
		return IdType.values();
	}

	public List<IdType> getParentIdTypes() {
		return Arrays.asList(IdType.NRCNO, IdType.FRCNO, IdType.PASSPORTNO);
	}

	public Gender[] getGender() {
		return Gender.values();
	}

	public IdConditionType[] getIdConditionType() {
		return IdConditionType.values();
	}

	private Map<String, InsuredPersonInfoDTO> insuredPersonInfoDTOMap;
	private InsuredPersonInfoDTO insuredPersonInfoDTO;
	private Boolean isEdit = false;

	private void createNewInsuredPersonInfo() {
		birthCertificateUploadedFileMap = new HashMap<String, String>();
		createNewIsuredPersonInfo = true;
		insuranceHistoryRecord = new InsuredPersonPolicyHistoryRecord();
		insuredPersonInfoDTO = new InsuredPersonInfoDTO();
		insuredPersonInfoDTO.setStartDate(new Date());
		insuredPersonInfoDTO.setProduct(product);
		isEdit = false;
		insuranceHistoryRecordMap = new HashMap<String, InsuredPersonPolicyHistoryRecord>();
	}

	public InsuredPersonInfoDTO getInsuredPersonInfoDTO() {
		return insuredPersonInfoDTO;
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

	public void saveInsuredPersonInfo() {
		insuredPersonInfoDTO.setFullIdNo();
		if (insuredPersonInfoDTO.getParentIdNo() != null && !insuredPersonInfoDTO.getParentIdNo().isEmpty()) {
			insuredPersonInfoDTO.setParentFullIdNo();
		}
		if (validateInsuredPersonInfo()) {
			// Start LifeProposal
			Calendar cal = Calendar.getInstance();
			if (insuredPersonInfoDTO.getStartDate() == null) {
				lifeProposal.setStartDate(new Date());
			} else {
				lifeProposal.setStartDate(insuredPersonInfoDTO.getStartDate());
			}
			cal.setTime(lifeProposal.getStartDate());
			cal.add(Calendar.YEAR, insuredPersonInfoDTO.getPeriodOfYears());
			lifeProposal.setEndDate(cal.getTime());
			lifeProposal.setPeriodMonth(insuredPersonInfoDTO.getPeriodOfMonths());
			// End LifeProposal

			insuredPersonInfoDTO.setInsuredPersonPolicyHistoryRecordList(getInsuranceHistoryRecordList());
			insuredPersonInfoDTO.setBirthCertificateAttachments(new ArrayList<Attachment>());
			if (lifeProposal.getId() != null) {
				for (String fileName : birthCertificateUploadedFileMap.keySet()) {
					String filePath = temporyDir + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate/" + fileName;
					insuredPersonInfoDTO.addBirthCertificateAttachment(new Attachment(fileName, filePath));
				}
			} else {
				for (String fileName : birthCertificateUploadedFileMap.keySet()) {
					String filePath = temporyDir + tempId + "/" + fileName;
					insuredPersonInfoDTO.addBirthCertificateAttachment(new Attachment(fileName, filePath));
				}
			}
			insuredPersonInfoDTO.setAge(insuredPersonInfoDTO.getAgeForNextYear());
			setKeyFactorValue();
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			birthCertificateUploadedFileMap = new HashMap<String, String>();
			createNewInsuredPersonInfo();
			sameCustomerAdderess = false;
			if (createNewIsuredPersonInfo) {
				PrimeFaces.current().executeScript("PF('wiz').nextNav.show(); PF('wiz').cfg.showNavBar = true;");
			}
		}
	}

	public void prepareEditInsuredPersonInfo(InsuredPersonInfoDTO insuDTO) {
		String filePath = null;

		this.insuredPersonInfoDTO = new InsuredPersonInfoDTO(insuDTO);
		if (insuredPersonInfoDTO.getResidentAddress().getTownship().getId().equals(lifeProposal.getCustomer().getResidentAddress().getTownship().getId())
				&& insuredPersonInfoDTO.getResidentAddress().getResidentAddress().equalsIgnoreCase(lifeProposal.getCustomer().getResidentAddress().getResidentAddress())) {
			sameCustomerAdderess = true;
		}
		if (insuredPersonInfoDTO.getBirthCertificateAttachments().size() > 0) {
			for (Attachment att : insuredPersonInfoDTO.getBirthCertificateAttachments()) {
				filePath = att.getFilePath();
				filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
				att.setFilePath(filePath);
				birthCertificateUploadedFileMap.put(att.getName(), att.getFilePath());
			}
		}
		for (InsuredPersonPolicyHistoryRecord i : insuredPersonInfoDTO.getInsuredPersonPolicyHistoryRecordList()) {
			insuranceHistoryRecordMap.put(i.getTempId(), i);
		}
		birthCertificateUploadedFileMap.clear();
		this.insuredPersonInfoDTO.loadFullIdNo();
		if (insuredPersonInfoDTO.getParentFullIdNo() != null) {
			this.insuredPersonInfoDTO.loadParentFullIdNo();
		}
		changeStateCode();
		changeFatherStateCode();
		changeMotherStateCode();
		changeChildDateOfBirth();
		for (Attachment a : this.insuredPersonInfoDTO.getBirthCertificateAttachments()) {
			birthCertificateUploadedFileMap.put(a.getName(), a.getFilePath());
		}
		createNewIsuredPersonInfo = false;
		isEdit = true;
		if (!createNewIsuredPersonInfo) {
			PrimeFaces.current().executeScript("PF('wiz').nextNav.hide();");
		}
	}

	public void saveInsuranceHistoryRecord() {
		insuranceHistoryRecord.setProduct(product);
		insuranceHistoryRecordMap.put(insuranceHistoryRecord.getTempId(), insuranceHistoryRecord);
		resetInsuranceHistoryRecord();
	}

	public void removeInsuranceHistoryRecord(InsuredPersonPolicyHistoryRecord insuranceHistoryRecord) {
		insuranceHistoryRecordMap.remove(insuranceHistoryRecord.getTempId());
	}

	public void editInsuranceHistoryRecord(InsuredPersonPolicyHistoryRecord record) {
		this.insuranceHistoryRecord = record;
		editInsuHistRec = true;
	}

	public void resetInsuranceHistoryRecord() {
		insuranceHistoryRecord = new InsuredPersonPolicyHistoryRecord();
		editInsuHistRec = false;
	}

	public List<InsuredPersonPolicyHistoryRecord> getInsuranceHistoryRecordList() {
		List<InsuredPersonPolicyHistoryRecord> insuDTOList = new ArrayList<InsuredPersonPolicyHistoryRecord>();
		if (insuranceHistoryRecordMap == null || insuranceHistoryRecordMap.values() == null) {
			return new ArrayList<InsuredPersonPolicyHistoryRecord>();
		} else {
			for (InsuredPersonPolicyHistoryRecord dto : insuranceHistoryRecordMap.values()) {
				insuDTOList.add(dto);
			}
		}
		return insuDTOList;
	}

	public boolean validateInsuredPersonInfo() {
		boolean valid = true;
		// if (birthCertificateUploadedFileMap.isEmpty() &&
		// insuredPersonInfoDTO.getIdType().equals(IdType.STILL_APPLYING)) {
		// addErrorMessage("studentLifeProposalEntryForm:birthCertificateImagePanel",
		// MessageId.BIRTH_CERTIFICATION_ATTACH_IS_REQUIRED);
		// valid = false;
		// }
		boolean overMaxSI = lifeProposalService.findOverMaxSIByMotherNameAndNRCAndInsuNameAndNRC(lifeProposal, insuredPersonInfoDTO);
		if (overMaxSI) {
			addErrorMessage("studentLifeProposalEntryForm:sI", MessageId.TOTAL_SUMINSURED_OF_CHILD_MUST_BE_BETWEEN_10LAKH_1000LAKH);
			if (valid) {
				valid = false;
			}
		}
		if (insuredPersonInfoDTO.getDateOfBirth().after(new Date())) {
			addErrorMessage("studentLifeProposalEntryForm:dateOfBirth", MessageId.INVALID_INSURED_DOB);
			valid = false;
		} else {
			long childAgeByDay = (new Date().getTime() - insuredPersonInfoDTO.getDateOfBirth().getTime()) / (1000 * 60 * 60 * 24);
			if (insuredPersonInfoDTO.getAgeForNextYear() > 12 || childAgeByDay < 30) {
				addErrorMessage("studentLifeProposalEntryForm:dateOfBirth", MessageId.CHILD_AGE_MUST_BE_BETWEEN_30DAYS_AND_12YEARS);
				if (valid) {
					valid = false;
				}
			}
		}
		if (insuredPersonInfoDTO.getParentDOB() != null && insuredPersonInfoDTO.getParentDOB().after(insuredPersonInfoDTO.getDateOfBirth())) {
			if (lifeProposal.getCustomer().getGender().equals(Gender.MALE)) {
				addErrorMessage("studentLifeProposalEntryForm:parentDOB", MessageId.INVALID_MOTHER_DOB);
			} else {
				addErrorMessage("studentLifeProposalEntryForm:cusDOB", MessageId.INVALID_FATHER_DOB);
			}
			valid = false;
		}
		if (lifeProposal.getCustomer().getFullIdNo() != null && insuredPersonInfoDTO.getFullIdNo() != null
				&& lifeProposal.getCustomer().getFullIdNo().equals(insuredPersonInfoDTO.getFullIdNo())) {
			addErrorMessage("studentLifeProposalEntryForm:nrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			if (lifeProposal.getCustomer().getGender().equals(Gender.MALE)) {
				addErrorMessage("studentLifeProposalEntryForm:customerNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			} else {
				addErrorMessage("studentLifeProposalEntryForm:motherNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			}
			if (valid) {
				valid = false;
			}
		}
		if (lifeProposal.getCustomer().getFullIdNo() != null && insuredPersonInfoDTO.getParentFullIdNo() != null
				&& lifeProposal.getCustomer().getFullIdNo().equals(insuredPersonInfoDTO.getParentFullIdNo())) {
			addErrorMessage("studentLifeProposalEntryForm:customerNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			addErrorMessage("studentLifeProposalEntryForm:motherNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			if (valid) {
				valid = false;
			}
		}
		if (insuredPersonInfoDTO.getParentFullIdNo() != null && insuredPersonInfoDTO.getFullIdNo() != null
				&& insuredPersonInfoDTO.getFullIdNo().equals(insuredPersonInfoDTO.getParentFullIdNo())) {
			addErrorMessage("studentLifeProposalEntryForm:nrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			if (lifeProposal.getCustomer().getGender().equals(Gender.MALE)) {
				addErrorMessage("studentLifeProposalEntryForm:motherNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			} else {
				addErrorMessage("studentLifeProposalEntryForm:customerNrcNo", MessageId.IDNO_MUST_NOT_BE_DUPLICATE);
			}
			if (valid) {
				valid = false;
			}
		}

		if (insuredPersonInfoDTO.getResidentAddress().getTownship() == null) {
			valid = false;
			addErrorMessage("studentLifeProposalEntryForm:townShip", MessageId.REQUIRED_VALUES);

		}

		return valid;
	}

	public void removeInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
		createNewInsuredPersonInfo();
	}

	public List<ProposalInsuredPerson> getInsuredPersonInfoList() {
		List<ProposalInsuredPerson> result = new ArrayList<ProposalInsuredPerson>();
		if (insuredPersonInfoDTOMap.values() != null) {
			for (InsuredPersonInfoDTO insuredPersonInfoDTO : insuredPersonInfoDTOMap.values()) {
				ClassificationOfHealth classificationOfHealth = insuredPersonInfoDTO.getClassificationOfHealth();
				ProposalInsuredPerson proposalInsuredPerson = new ProposalInsuredPerson(insuredPersonInfoDTO, lifeProposal);
				proposalInsuredPerson.setInsuredPersonAddOnList(insuredPersonInfoDTO.getInsuredPersonAddOnList(proposalInsuredPerson));
				proposalInsuredPerson.setClsOfHealth(classificationOfHealth);
				proposalInsuredPerson.setTypesOfSport(insuredPersonInfoDTO.getTypesOfSport());
				proposalInsuredPerson.setUnit(insuredPersonInfoDTO.getUnit());

				List<InsuredPersonPolicyHistoryRecord> historyRecordList = insuredPersonInfoDTO.getInsuredPersonPolicyHistoryRecordList();
				if (historyRecordList != null) {
					for (InsuredPersonPolicyHistoryRecord record : historyRecordList) {
						proposalInsuredPerson.addInsuranceHistoryRecord(record);
					}
				}
				List<InsuredPersonAttachment> insuredPersonAttachments = insuredPersonInfoDTO.getPerAttachmentList();
				if (insuredPersonAttachments != null) {
					for (InsuredPersonAttachment attachment : insuredPersonAttachments) {
						proposalInsuredPerson.addAttachment(attachment);
					}
				}
				List<Attachment> birthCertificateAttachments = insuredPersonInfoDTO.getBirthCertificateAttachments();
				if (birthCertificateAttachments != null) {
					for (Attachment attachment : birthCertificateAttachments) {
						proposalInsuredPerson.addBirthCertificateAttachment(attachment);
					}
				}
				// for (InsuredPersonKeyFactorValue kfv :
				// insuredPersonInfoDTO.getKeyFactorValueList(proposalInsuredPerson))
				// {
				// if (KeyFactorChecker.isPaymentType(kfv.getKeyFactor())) {
				// kfv.setValue(lifeProposal.getPaymentType().getId() + "");
				// }
				// }
				List<SurveyQuestionAnswer> answerList = insuredPersonInfoDTO.getSurveyQuestionAnswerList();
				if (historyRecordList != null) {
					for (SurveyQuestionAnswer answer : answerList) {
						proposalInsuredPerson.addSurveyQuestionAnswer(answer);
					}
				}
				proposalInsuredPerson.setKeyFactorValueList(insuredPersonInfoDTO.getKeyFactorValueList(proposalInsuredPerson));
				result.add(proposalInsuredPerson);
			}
		}
		return result;
	}

	public void changeChildDateOfBirth() {
		insuredPersonInfoDTO.setStartDate(lifeProposal.getSubmittedDate());
		if (insuredPersonInfoDTO.getDateOfBirth() != null) {
			if (insuredPersonInfoDTO.getAgeForNextYear() > 7)
				isAgeAbove7 = true;
			else
				isAgeAbove7 = false;
			insuredPersonInfoDTO.setPeriodOfYears(maxTerm - insuredPersonInfoDTO.getAgeForNextYear() + 1);
		}
	}

	public void changeSaleEvent(AjaxBehaviorEvent event) {
		lifeProposal.setSaleBank(null);
		lifeProposal.setAgent(null);
	}

	public void createNewLifeProposal() {
		createNew = true;
		lifeProposal = new LifeProposal();
		resetCustomer();
		lifeProposal.setSubmittedDate(new Date());
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public boolean isDisableInsureInfo() {
		return getInsuredPersonInfoDTOList().size() > 0 && createNewIsuredPersonInfo;
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

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("proposalTab".equals(event.getOldStep()) && "customerTab".equals(event.getNewStep())) {
			lifeProposal.getCustomer().loadTransientIdNo();
			if (lifeProposal != null && lifeProposal.getCustomer().getStateCode() != null) {
				changeCustomerStateCodeList();
			}
		} else if ("customerTab".equals(event.getOldStep()) && "InsuredPersonInfo".equals(event.getNewStep())) {
			Customer customer = lifeProposal.getCustomer();
			changeChildDateOfBirth();
			int customerAge = customer.getAgeForNextYear();
			if (customerAge <= 18 || customerAge >= 55) {
				addErrorMessage("studentLifeProposalEntryForm:customerRegdob", MessageId.CUSTOMER_AGE_MUST_BE_BETWEEN, 18, 55);
				valid = false;
			}
			if (customer.getId() == null) {
				boolean isExit = true;
				isExit = customerService.checkExistingCustomer(customer);
				if (isExit) {
					String param = customer.getFullName();
					if (customer.getIdType() != IdType.STILL_APPLYING) {
						param += " (or) " + customer.getFullIdNo();
					}
					addErrorMessage("studentLifeProposalEntryForm:customerRegcustomer", MessageId.EXISTING_CUSTOMER_PLZ_SELECT, param);
					valid = false;
				}
			}
			if (valid) {
				if (!customer.getIdType().equals(IdType.STILL_APPLYING)) {
					changeFatherStateCode();
					changeMotherStateCode();
					lifeProposal.getCustomer().setFullIdNo();
				}
				changeStateCode();
			}
			PrimeFaces.current().resetInputs(":studentLifeProposalEntryForm:InsuredPersonInfo");
		} else if ("InsuredPersonInfo".equals(event.getOldStep()) && "showPremium".equals(event.getNewStep())) {
			if (getInsuredPersonInfoDTOList().size() < 1) {
				addErrorMessage("studentLifeProposalEntryForm:insuredPersonInfoTable", MessageId.REQUIRED_INSURED_PERSION);
				valid = false;
			} else {

				showPremiumCalculation();
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public LifeProposal showPremiumCalculation() {
		try {
			lifeProposal.setInsuredPersonList(getInsuredPersonInfoList());
			lifeProposal = lifeProposalService.calculatePremium(lifeProposal);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return lifeProposal;
	}

	public String addNewStudentLifeProposal() {
		String result = null;
		try {
			ReferenceType referenceType = ReferenceType.STUDENT_LIFE;
			WorkflowTask workflowTask = null;
			WorkFlowDTO workFlowDTO = null;
			ExternalContext extContext = getFacesContext().getExternalContext();
			if (isEditProposal) {
				String filePath = null;
				for (Attachment a : lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment()) {
					filePath = BIRTH_CERTIFICATE_DIR + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate" + "/"
							+ a.getName();
					a.setFilePath(filePath);
				}
				workflowTask = surveyAgain ? WorkflowTask.SURVEY : WorkflowTask.APPROVAL;
				workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
						responsiblePerson);
				lifeProposal = lifeProposalService.updateLifeProposal(lifeProposal, workFlowDTO);
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_PROPOSAL_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			} else if (isEnquiryEdit) {
				showPremiumCalculation();
				String filePath = null;
				for (Attachment a : lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment()) {
					filePath = BIRTH_CERTIFICATE_DIR + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate" + "/"
							+ a.getName();
					a.setFilePath(filePath);
				}
				lifeProposal = lifeProposalService.updateLifeProposal(lifeProposal);
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_PROPOSAL_PROCESS_SUCCESS);
			} else {
				workflowTask = WorkflowTask.SURVEY;
				workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
						responsiblePerson);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal = lifeProposalService.addNewLifeProposal(lifeProposal, workFlowDTO, RequestStatus.PROPOSED.name());
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			}
			if (lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment().size() > 0) {
				moveUploadedFiles();
			}
			createNewLifeProposal();
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	private void moveUploadedFiles() {
		try {
			if (isEnquiryEdit || isEditProposal) {
				FileHandler.moveFiles(getUploadPath(), temporyDir + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate",
						BIRTH_CERTIFICATE_DIR + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate");
			} else {
				FileHandler.moveFiles(getUploadPath(), temporyDir + tempId,
						BIRTH_CERTIFICATE_DIR + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleProposalAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");

		if (!birthCertificateUploadedFileMap.containsKey(fileName)) {
			String filePath = null;
			if (lifeProposal.getId() != null) {
				filePath = temporyDir + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate/" + fileName;
			} else {
				filePath = temporyDir + tempId + "/" + fileName;
			}

			birthCertificateUploadedFileMap.put(fileName, filePath);
			try {
				String physicalPath = getUploadPath() + filePath;

				FileHandler.createFile(new File(physicalPath), uploadedFile.getContents());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getBirthCertificateUploadedFileList() {
		return new ArrayList<String>(birthCertificateUploadedFileMap.values());
	}

	public void removeBirthCertificateUploadedFile(String filePath) {
		try {
			String fileName = FileHandler.getFileName(filePath);
			birthCertificateUploadedFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (birthCertificateUploadedFileMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void changeParentIdType() {
		checkIdType("P");
		PrimeFaces.current().resetInputs("studentLifeProposalEntryForm:motherIdNo");
	}

	public void changeInsuIdType() {
		checkIdType("I");
	}

	public void changeCusIdType() {
		checkIdType("C");
	}

	public void checkIdType(String customerType) {
		if ("P".equals(customerType)) {
			insuredPersonInfoDTO.setParentProvinceCode(null);
			insuredPersonInfoDTO.setParentTownshipCode(null);
			insuredPersonInfoDTO.setParentIdNo(null);
			insuredPersonInfoDTO.setParentIdConditionType(null);
			insuredPersonInfoDTO.setParentFullIdNo(null);
		} else if ("C".equals(customerType)) {
			lifeProposal.getCustomer().setStateCode(null);
			lifeProposal.getCustomer().setTownshipCode(null);
			lifeProposal.getCustomer().setIdNo(null);
			lifeProposal.getCustomer().setIdConditionType(null);
			lifeProposal.getCustomer().setFullIdNo(null);
		} else {
			insuredPersonInfoDTO.setProvinceCode(null);
			insuredPersonInfoDTO.setTownshipCode(null);
			insuredPersonInfoDTO.setIdNo(null);
			insuredPersonInfoDTO.setIdConditionType(null);
			insuredPersonInfoDTO.setFullIdNo(null);
		}
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

	private void setKeyFactorValue() {
		for (InsuredPersonKeyFactorValue vehKF : insuredPersonInfoDTO.getKeyFactorValueList()) {
			KeyFactor kf = vehKF.getKeyFactor();
			if (KeyFactorChecker.isPaymentType(kf)) {
				vehKF.setValue(lifeProposal.getPaymentType().getId() + "");
			} else if (KeyFactorChecker.isAge(kf)) {
				vehKF.setValue(insuredPersonInfoDTO.getAge() + "");
			} else if (KeyFactorChecker.isTerm(kf)) {
				vehKF.setValue(insuredPersonInfoDTO.getPeriodOfYears() + "");
			}
		}

	}

	public MaritalStatus[] getMaritalStatus() {
		return MaritalStatus.values();
	}

	public void selectUser() {
		WorkFlowType workFlowType = WorkFlowType.STUDENT_LIFE;
		WorkflowTask workflowTask = WorkflowTask.SURVEY;
		selectUser(workflowTask, workFlowType, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	public void selectSalePoint() {
		PrimeFaces.current().dialog().openDynamic("salePointDialog", WebUtils.getDialogOption(), null);
	}

	public List<PaymentType> getPaymentTypes() {
		if (product == null) {
			return new ArrayList<PaymentType>();
		} else {
			return product.getPaymentTypeList();
		}
	}

	public int getMaximumTerm() {
		int result = 0;
		if (product != null) {
			result = product.getMaxTerm();
		}
		return result;
	}

	public int getMinimumTerm() {
		int result = 0;
		if (product != null) {
			result = product.getMinTerm();
		}
		return result;
	}

	public double getMaximumSI() {
		double result = 0;
		if (product != null) {
			result = product.getMaxValue();
		}
		return result;
	}

	public void resetCustomer() {
		Customer customer = new Customer();
		lifeProposal.setCustomer(customer);
	}

	public double getMinimumSI() {
		double result = 0;
		if (product != null) {
			result = product.getMinValue();
		}
		return result;
	}

	public void checkCustomerSameAddress() {
		if (sameCustomerAdderess) {
			insuredPersonInfoDTO.getResidentAddress().setTownship(lifeProposal.getCustomer().getResidentAddress().getTownship());
			insuredPersonInfoDTO.getResidentAddress().setResidentAddress(lifeProposal.getCustomer().getResidentAddress().getResidentAddress());
		} else {
			insuredPersonInfoDTO.getResidentAddress().setTownship(null);
			insuredPersonInfoDTO.getResidentAddress().setResidentAddress(null);
		}
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		lifeProposal.setOrganization(organization);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		lifeProposal.setSalesPoints(salesPoints);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		customer.setInitialId(customer.getInitialId().replaceAll(" ", ""));
		customer.loadTransientIdNo();
		lifeProposal.setCustomer(customer);
		changeCustomerStateCodeList();
	}

	public void returnNationality(SelectEvent event) {
		Country nationality = (Country) event.getObject();
		lifeProposal.getCustomer().setCountry(nationality);
	}

	public void returnPaymentType(SelectEvent event) {
		PaymentType paymentType = (PaymentType) event.getObject();
		lifeProposal.setPaymentType(paymentType);
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salePoints = (SalesPoints) event.getObject();
		lifeProposal.setSalesPoints(salePoints);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		lifeProposal.setAgent(agent);
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		lifeProposal.setSaleBank(bankBranch);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		lifeProposal.setBranch(branch);
	}

	public void returnInsuredPersonTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		insuredPersonInfoDTO.getResidentAddress().setTownship(townShip);
	}

	public void returnSchool(SelectEvent event) {
		School school = (School) event.getObject();
		insuredPersonInfoDTO.setSchool(school);
	}

	public void returnCoinsuranceCompany(SelectEvent event) {
		CoinsuranceCompany coinsuranceCompany = (CoinsuranceCompany) event.getObject();
		insuranceHistoryRecord.setCompany(coinsuranceCompany);
	}

	public void returnResidentTownship(SelectEvent event) {
		Township residentTownship = (Township) event.getObject();
		lifeProposal.getCustomer().getResidentAddress().setTownship(residentTownship);
	}

	public void returnOccupation(SelectEvent event) {
		Occupation occupation = (Occupation) event.getObject();
		insuredPersonInfoDTO.setOccupation(occupation);
	}

	public void returnOccupationForCustomer(SelectEvent event) {
		Occupation occupation = (Occupation) event.getObject();
		lifeProposal.getCustomer().setOccupation(occupation);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnNationalityForInsuredPerson(SelectEvent event) {
		Country nationality = (Country) event.getObject();
		lifeProposal.getCustomer().setCountry(nationality);
	}

	public void returnOfficeTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		lifeProposal.getCustomer().getOfficeAddress().setTownship(township);
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public List<GradeInfo> getGradeInfoList() {
		return gradeInfoList;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public boolean isCreateNewIsuredPersonInfo() {
		return createNewIsuredPersonInfo;
	}

	public boolean isEnquiryEdit() {
		return isEnquiryEdit;
	}

	public boolean isEditProposal() {
		return isEditProposal;
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

	public boolean isSurveyAgain() {
		return surveyAgain;
	}

	public void setSurveyAgain(boolean surveyAgain) {
		this.surveyAgain = surveyAgain;
	}

	public boolean isAgeAbove7() {
		return isAgeAbove7;
	}

	public InsuredPersonPolicyHistoryRecord getInsuranceHistoryRecord() {
		return insuranceHistoryRecord;
	}

	public void setInsuranceHistoryRecord(InsuredPersonPolicyHistoryRecord insuranceHistoryRecord) {
		this.insuranceHistoryRecord = insuranceHistoryRecord;
	}

	public Product getProduct() {
		return product;
	}

	public boolean isEditInsuHistRec() {
		return editInsuHistRec;
	}

	public boolean isSameCustomerAdderess() {
		return sameCustomerAdderess;
	}

	public void setSameCustomerAdderess(boolean sameCustomerAdderess) {
		this.sameCustomerAdderess = sameCustomerAdderess;
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public List<String> getFatherTownshipCodeList() {
		return fatherTownshipCodeList;
	}

	public List<String> getMotherTownshipCodeList() {
		return motherTownshipCodeList;
	}

	public List<String> getCustomerTownshipCodeList() {
		return customerTownshipCodeList;
	}

}
