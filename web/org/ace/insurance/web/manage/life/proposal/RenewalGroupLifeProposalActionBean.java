package org.ace.insurance.web.manage.life.proposal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.ace.insurance.common.PeriodType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.InsuredPersonPolicyHistoryRecord;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifePolicyService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifeProposalService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
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
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "RenewalGroupLifeProposalActionBean")
public class RenewalGroupLifeProposalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{RenewalGroupLifeProposalService}")
	private IRenewalGroupLifeProposalService renewalGroupLifeProposalService;

	public void setRenewalGroupLifeProposalService(IRenewalGroupLifeProposalService renewalGroupLifeProposalService) {
		this.renewalGroupLifeProposalService = renewalGroupLifeProposalService;
	}

	@ManagedProperty(value = "#{RenewalGroupLifePolicyService}")
	private IRenewalGroupLifePolicyService renewalGroupLifePolicyService;

	public void setRenewalGroupLifePolicyService(IRenewalGroupLifePolicyService renewalGroupLifePolicyService) {
		this.renewalGroupLifePolicyService = renewalGroupLifePolicyService;
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

	@ManagedProperty(value = "#{ProvinceService}")
	private IProvinceService provinceService;

	public void setProvinceService(IProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private LifePolicy lifepolicy;
	private List<Product> productsList;
	private List<RelationShip> relationshipList;
	private boolean createNew;
	private boolean isNewInsuredRecord;
	private String remark;
	private User responsiblePerson;
	private String userType;
	private Date startDate;
	private Date endDate;
	private InsuredPersonPolicyHistoryRecord insuredRecord;
	private Map<String, InsuredPersonPolicyHistoryRecord> insuredRecordMap;
	private List<String> provinceCodeList;
	private List<String> iPersonTownshipCodeList;
	private List<String> townshipCodeList;

	private boolean isConfirmEdit;
	private boolean isEnquiryEdit;

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

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifepolicy = (LifePolicy) getParam("lifePolicy");
		lifeProposal = (LifeProposal) getParam("lifeProposal");
		isEnquiryEdit = (Boolean) getParam("isEnquiryEdit") == null ? false : true;
		isConfirmEdit = isEnquiryEdit ? false : lifeProposal != null ? true : false;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
		removeParam("lifeProposal");
		removeParam("isEnquiryEdit");
	}

	@PostConstruct
	public void init() {
		productsList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		relationshipList = relationShipService.findAllRelationShip();
		this.product = productsList.get(3);
		provinceCodeList = provinceService.findAllProvinceNo();
		initializeInjection();
		isConfirmEdit = (lifeProposal == null) ? false : true;

		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();
		if (lifepolicy != null) {
			lifeProposal = new LifeProposal(lifepolicy);
			lifeProposal.setSubmittedDate(new Date());
			Date oldEndDate = lifepolicy.getActivedPolicyEndDate();
			int periodOfMonth = lifepolicy.getPeriodMonth();
			Date currentDate = new Date();
			if (oldEndDate.after(currentDate)) {
				startDate = oldEndDate;
			} else {
				startDate = currentDate;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, periodOfMonth);
			endDate = cal.getTime();

			lifeProposal.getProposalInsuredPersonList().clear();
			lifepolicy.setActivedPolicyStartDate(startDate);
			lifepolicy.setActivedPolicyEndDate(endDate);
			for (PolicyInsuredPerson policyInsuredPerson : lifepolicy.getPolicyInsuredPersonList()) {
				lifeProposal.addInsuredPerson(new ProposalInsuredPerson(policyInsuredPerson));
			}
			for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
				insuredPersonInfoDTO = new InsuredPersonInfoDTO(pv);
				insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			}
		} else if (isConfirmEdit || isEnquiryEdit) {
			for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
				insuredPersonInfoDTO = new InsuredPersonInfoDTO(pv);
				insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			}
		}
		lifeProposal.setBranch(user.getLoginBranch());
		lifeProposal.setPeriodMonth(lifeProposal.getPeriodOfInsurance());
		lifeProposal.setProposalType(ProposalType.RENEWAL);

		createNewInsuredPersonInfo();
		createNewBeneficiariesInfoDTOMap();
		createNewBeneficiariesInfo();
		outjectLifeProposal(lifeProposal);
	}

	public List<String> getProvinceCodeList() {
		return provinceCodeList;
	}

	public void setProvinceCodeList(List<String> provinceCodeList) {
		this.provinceCodeList = provinceCodeList;
	}

	public void changeIPersonProvinceCode() {
		iPersonTownshipCodeList = new ArrayList<>();
		iPersonTownshipCodeList = townshipService.findTspShortNameByProvinceNo(insuredPersonInfoDTO.getProvinceCode());
	}

	public List<String> getiPersonTownshipCodeList() {
		return iPersonTownshipCodeList;
	}

	public void setiPersonTownshipCodeList(List<String> iPersonTownshipCodeList) {
		this.iPersonTownshipCodeList = iPersonTownshipCodeList;
	}

	public IdConditionType[] getIdConditionTypeSelectItemList() {
		return IdConditionType.values();
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
	}

	public BeneficiariesInfoDTO getBeneficiariesInfoDTO() {
		return beneficiariesInfoDTO;
	}

	public void setBeneficiariesInfoDTO(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		this.beneficiariesInfoDTO = beneficiariesInfoDTO;
	}

	public void prepareAddNewBeneficiariesInfo() {
		createNewBeneficiariesInfo();
	}

	public void prepareEditBeneficiariesInfo(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		this.beneficiariesInfoDTO = beneficiariesInfoDTO;
		this.beneficiariesInfoDTO.loadFullIdNo();
		if (beneficiariesInfoDTO.getProvinceCode() != null) {
			changeBenefiProvinceCode();
		}

		this.createNewBeneficiariesInfo = false;
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

	public void saveBeneficiariesInfo() {
		if (validBeneficiary(beneficiariesInfoDTO)) {
			beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(), beneficiariesInfoDTO);
			insuredPersonInfoDTO.setBeneficiariesInfoDTOList(new ArrayList<BeneficiariesInfoDTO>(sortByValue(beneficiariesInfoDTOMap).values()));
			createNewBeneficiariesInfo();
			PrimeFaces.current().executeScript("PF('beneficiariesInfoEntryDialog').hide()");
		}
	}

	public void removeBeneficiariesInfo(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		beneficiariesInfoDTOMap.remove(beneficiariesInfoDTO.getTempId());
		insuredPersonInfoDTO.getBeneficiariesInfoDTOList().remove(beneficiariesInfoDTO);
		createNewBeneficiariesInfo();
	}

	public void changeBenefiProvinceCode() {
		townshipCodeList = new ArrayList<>();
		townshipCodeList = townshipService.findTspShortNameByProvinceNo(beneficiariesInfoDTO.getProvinceCode());
	}

	public List<String> getTownshipCodeList() {
		return townshipCodeList;
	}

	public void setTownshipCodeList(List<String> townshipCodeList) {
		this.townshipCodeList = townshipCodeList;
	}

	/*************************************************
	 * End Beneficiary Manage
	 *********************************************************/

	/*************************************************
	 * Start AddOn Manage
	 *********************************************************/
	private boolean createNewAddOn;
	private InsuredPersonAddOnDTO insuredPersonAddOnDTO;

	public boolean isCreateNewAddOn() {
		return createNewAddOn;
	}

	private void createInsuredPersonAddOnDTO() {
		createNewAddOn = true;
		insuredPersonAddOnDTO = new InsuredPersonAddOnDTO();
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

	private boolean validInsuredPersonAddOn() {
		boolean valid = true;
		if (insuredPersonAddOnDTO.getAddOn() == null) {
			addErrorMessage("addOnEntryForm" + ":addOn", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (valid) {
			PrimeFaces.current().executeScript("PF('addOnEntryDialog').hide()");
		}
		return valid;
	}

	/*************************************************
	 * End AddOn Manage
	 *********************************************************/

	/*************************************************
	 * Start InsuredPerson Manage
	 ******************************************************/
	private Map<String, InsuredPersonInfoDTO> insuredPersonInfoDTOMap;
	private InsuredPersonInfoDTO insuredPersonInfoDTO;
	private boolean createNewIsuredPersonInfo;
	private Boolean isReplace = false;
	private Product product;

	public boolean isCreateNewInsuredPersonInfo() {
		return createNewIsuredPersonInfo;
	}

	private void createNewInsuredPersonInfo() {
		createNewIsuredPersonInfo = true;
		insuredPersonInfoDTO = new InsuredPersonInfoDTO();
		insuredPersonInfoDTO.setProduct(product);
		createNewInsuredRecordList();
		createNewInsuredRecord();
		isReplace = false;
	}

	public PeriodType[] getPeriodType() {
		return new PeriodType[] { PeriodType.MONTH, PeriodType.YEAR };
	}

	public Boolean getIsReplace() {
		return isReplace;
	}

	public void setIsReplace(Boolean isReplace) {
		this.isReplace = isReplace;
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
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
		product = insuredPersonInfoDTO.getProduct();
		if (insuredPersonInfoDTO.getProvinceCode() != null) {
			changeIPersonProvinceCode();
		}
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
		createNewIsuredPersonInfo = false;
	}

	public void prepareReplaceInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
		createNewBeneficiariesInfoDTOMap();
		createNewIsuredPersonInfo = false;
		isReplace = true;
	}

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

					insuredPersonInfoDTO.setResidentAddress(ra);
					insuredPersonInfoDTO.setName(name);

					for (InsuredPersonKeyFactorValue insKFV : insuredPersonInfoDTO.getKeyFactorValueList()) {
						KeyFactor kf = insKFV.getKeyFactor();
						if (KeyFactorChecker.isSumInsured(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getSumInsuredInfo() + "");
						} else if (KeyFactorChecker.isTerm(kf)) {
							insKFV.setValue(lifeProposal.getPeriodMonth() + "");
						} else if (KeyFactorChecker.isAge(kf)) {
							insKFV.setValue(insuredPersonInfoDTO.getAgeForNextYear() + "");
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

	public void saveInsuredPersonInfo() {
		insuredPersonInfoDTO.setProduct(product);
		if (validInsuredPerson(insuredPersonInfoDTO)) {
			setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getPeriodMonth());
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			createNewInsuredPersonInfo();
			createNewBeneficiariesInfoDTOMap();
		}
	}

	public void createNewBeneficiariesInfoDTOMap() {
		beneficiariesInfoDTOMap = new HashMap<String, BeneficiariesInfoDTO>();
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

		if (isEmpty(insuredPersonInfoDTO.getInitialId())) {
			addErrorMessage(formID + ":initialId", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (isEmpty(insuredPersonInfoDTO.getName().getFirstName())) {
			addErrorMessage(formID + ":firstName", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (isEmpty(insuredPersonInfoDTO.getFatherName())) {
			addErrorMessage(formID + ":fatherName", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (!insuredPersonInfoDTO.getIdType().equals(IdType.STILL_APPLYING) && isEmpty(insuredPersonInfoDTO.getIdNo())) {
			addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (insuredPersonInfoDTO.getDateOfBirth() == null) {
			addErrorMessage(formID + ":dateOfBirth", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}

		if (insuredPersonInfoDTO.getSumInsuredInfo() <= 0) {
			addErrorMessage(formID + ":sumInsuredInfo", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (isEmpty(insuredPersonInfoDTO.getResidentAddress().getResidentAddress())) {
			addErrorMessage(formID + ":resident", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (insuredPersonInfoDTO.getResidentAddress().getTownship() == null) {
			addErrorMessage(formID + ":townShip", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (insuredPersonInfoDTO.getGender() == null) {
			addErrorMessage(formID + ":gender", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (insuredPersonInfoDTO.getProduct() == null) {
			addErrorMessage(formID + ":product", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (KeyFactorChecker.isGroupLife(insuredPersonInfoDTO.getProduct()) && insuredPersonInfoDTO.getDateOfBirth() != null) {
			if (insuredPersonInfoDTO.getAgeForNextYear() < 20) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, 20);
				valid = false;
			}
			if (insuredPersonInfoDTO.getAgeForNextYear() > 60) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, 60);
				valid = false;
			} else if (insuredPersonInfoDTO.getAgeForNextYear() + lifeProposal.getPeriodMonth() > 65) {
				int availablePeriod = 65 - insuredPersonInfoDTO.getAgeForNextYear();
				addErrorMessage(formID + ":periodOfInsurance", MessageId.MAXIMUM_INSURED_YEARS, availablePeriod < 0 ? 0 : availablePeriod);
				valid = false;
			}
		}

		if (insuredPersonInfoDTO.getBeneficiariesInfoDTOList() != null && insuredPersonInfoDTO.getBeneficiariesInfoDTOList().size() != 0) {
			float totalPercent = 0.0f;
			for (BeneficiariesInfoDTO bfDTO : insuredPersonInfoDTO.getBeneficiariesInfoDTOList()) {
				totalPercent = totalPercent + bfDTO.getPercentage();
			}
			if (totalPercent > 100) {
				addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.OVER_BENEFICIARY_PERCENTAGE);
				valid = false;
			}
			if (totalPercent < 100) {
				addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.LOWER_BENEFICIARY_PERCENTAGE);
				valid = false;
			}
			for (BeneficiariesInfoDTO benfInfoDTO : insuredPersonInfoDTO.getBeneficiariesInfoDTOList()) {
				if (!benfInfoDTO.isValidBeneficiaries()) {
					addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.INVALID_BENEFICIARY_PERSON);
					valid = false;
					break;
				}
			}
		} else {
			addErrorMessage(formID + ":beneficiariesInfoTablePanel", MessageId.REQUIRED_BENEFICIARY_PERSON);
			valid = false;

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
		insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
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
				proposalInsuredPerson.setLifeProposal(lifeProposal);
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
	// private boolean organization;
	//
	// public boolean isOrganization() {
	// return organization;
	// }
	//
	// public void setOrganization(boolean organization) {
	// this.organization = organization;
	// }
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

	// public void changeOrgEvent(AjaxBehaviorEvent event) {
	// if (organization) {
	// lifeProposal.setCustomer(null);
	// insuredPersonInfoDTO = new InsuredPersonInfoDTO();
	// } else {
	// lifeProposal.setOrganization(null);
	// }
	// }

	public void changeOrgEvent(AjaxBehaviorEvent event) {
		if (contractorType.equals(ContractorType.CUSTOMER.toString())) {
			lifeProposal.setOrganization(null);
		} else if (contractorType.equals(ContractorType.ORGANIZATION.toString())) {
			lifeProposal.setCustomer(null);

		}
	}

	// public void changeSaleEvent(AjaxBehaviorEvent event) {
	//
	// }

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.BANK, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public List<PaymentType> getPaymentTypes() {
		if (product == null) {
			return new ArrayList<PaymentType>();
		} else {
			return product.getPaymentTypeList();
		}
	}

	public List<Product> getProducts() {
		if (product == null) {
			return new ArrayList<Product>();
		} else {
			return getProductsList();
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
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public LifeProposal getLifeProposal() {
		/* Populate data into InsuredPersonInfoDTO when customer is selected. */
		if (lifepolicy == null) {
			if (lifeProposal.getCustomer() != null) {
				Customer customer = lifeProposal.getCustomer();
				insuredPersonInfoDTO.setInitialId(customer.getInitialId());
				insuredPersonInfoDTO.setName(customer.getName());
				insuredPersonInfoDTO.setIdNo(customer.getIdNo());
				insuredPersonInfoDTO.setIdType(customer.getIdType());
				insuredPersonInfoDTO.setGender(customer.getGender());
				insuredPersonInfoDTO.setDateOfBirth(customer.getDateOfBirth());
				insuredPersonInfoDTO.setResidentAddress(customer.getResidentAddress());
				insuredPersonInfoDTO.setFatherName(customer.getFatherName());
				insuredPersonInfoDTO.setOccupation(customer.getOccupation());
			}

		}
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public String addNewLifeProposal() {
		String result = null;
		try {
			String formID = "lifeProposalEntryForm";
			if (responsiblePerson == null) {
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				return null;
			}
			WorkflowTask workflowTask = WorkflowTask.SURVEY;
			// FIXME CHECK REFTYPE
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, ReferenceType.GROUP_LIFE,
					TransactionType.RENEWAL, user, responsiblePerson);
			lifeProposal.setInsuredPersonList(getInsuredPersonInfoList());
			lifeProposal.setPeriodMonth(getPeriodOfMonths());
			lifeProposal.setLifePolicy(null);
			if (isConfirmEdit) {
				lifeProposalService.updateLifeProposal(lifeProposal, workFlowDTO);
			} else if (isEnquiryEdit) {
				lifeProposalService.updateLifeProposal(lifeProposal, null);
			} else {
				lifeProposal = renewalGroupLifeProposalService.renewalGroupLifeProposal(lifeProposal, workFlowDTO, RequestStatus.PROPOSED.name());
			}
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			createNewLifeProposal();
			result = "dashboard";
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
		String formID = "lifeProposalEntryForm";
		// customerList = customerService.findAllCustomer();
		if ("proposalInfo".equals(event.getOldStep())) {

			if (lifeProposal.getStartDate() == null) {
				lifeProposal.setStartDate(new Date());
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(lifeProposal.getStartDate());
			cal.add(Calendar.YEAR, lifeProposal.getPeriodMonth());
			lifeProposal.setEndDate(cal.getTime());

			if (lifeProposal.getSubmittedDate() == null) {
				addErrorMessage(formID + ":submittedDate", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (lifeProposal.getCustomer() == null && lifeProposal.getOrganization() == null) {
				addErrorMessage(formID + ":customer", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (lifeProposal.getPaymentType() == null) {
				addErrorMessage(formID + ":paymentType", MessageId.REQUIRED_PAYMENTTYPE);
				valid = false;
			}
			if (lifeProposal.getBranch() == null) {
				addErrorMessage(formID + ":branch", MessageId.REQUIRED_BRANCH);
				valid = false;
			}
			if (lifeProposal.getPeriodMonth() != 1) {
				addErrorMessage(formID + ":periodOfInsurance", MessageId.AVAILABLE_INSURED_PERIOD, 1);
				valid = false;
			}
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
				}
			}
			if (valid)
				showPremiumCalculation();
		}

		if ("showPremium".equals(event.getOldStep()) && "workflow".equals(event.getNewStep()))
			valid = true;

		// if (lifeProposal.getLifePolicy() == null) {
		// if ("InsuredPersonInfo".equals(event.getOldStep()) &&
		// "workflow".equals(event.getNewStep())) {
		// if (getInsuredPersonInfoDTOList().isEmpty()) {
		// addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable",
		// MessageId.REQUIRED_INSURED_PERSION);
		// valid = false;
		// }
		// }
		// }
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public Map<String, BeneficiariesInfoDTO> getBeneficiariesInfoDTOMap() {
		return beneficiariesInfoDTOMap;
	}

	public void setBeneficiariesInfoDTOMap(Map<String, BeneficiariesInfoDTO> beneficiariesInfoDTOMap) {
		this.beneficiariesInfoDTOMap = beneficiariesInfoDTOMap;
	}

	public String getPublicLifeId() {
		return KeyFactorIDConfig.getPublicLifeId();
	}

	public String getGroupLifeId() {
		return KeyFactorIDConfig.getGroupLifeId();
	}

	public LifeProposal showPremiumCalculation() {
		try {
			lifeProposal.setInsuredPersonList(getInsuredPersonInfoList());

			lifeProposal = renewalGroupLifeProposalService.calculatePremium(lifeProposal);

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return lifeProposal;
	}

	public String getUserType() {
		if (lifeProposal.getAgent() != null) {
			userType = SaleChannelType.AGENT.toString();
		}
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
		if (lifepolicy != null) {
			return true;
		}
		return false;
	}

	public Boolean isDecreasedSIAmount() {
		if (insuredPersonInfoDTO.getInsPersonCodeNo() != null) {
			PolicyInsuredPerson policyInsuPerson = renewalGroupLifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
			if (insuredPersonInfoDTO.getSumInsuredInfo() < policyInsuPerson.getSumInsured()) {
				int passedMonths = getPassedMonths();
				int passedYear = passedMonths / 12;
				int period = lifeProposal.getPeriodMonth();
				if ((period <= 12 && passedYear >= 2) || period > 12 && passedYear >= 3) {
					if (passedMonths % 12 > 5) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void showPaidUpDialog() {
		saveInsuredPersonInfo();
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

	private void setKeyFactorValue(double sumInsured, int age, int period) {
		for (InsuredPersonKeyFactorValue vehKF : insuredPersonInfoDTO.getKeyFactorValueList()) {
			KeyFactor kf = vehKF.getKeyFactor();
			if (KeyFactorChecker.isSumInsured(kf)) {
				vehKF.setValue(sumInsured + "");
			} else if (KeyFactorChecker.isAge(kf)) {
				vehKF.setValue(age + "");
			} else if (KeyFactorChecker.isTerm(kf)) {
				vehKF.setValue(period + "");
			}
		}
	}

	private boolean validBeneficiary(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		boolean valid = true;
		String formID = "beneficiaryInfoEntryForm";
		if (isEmpty(beneficiariesInfoDTO.getName().getFirstName())) {
			addErrorMessage(formID + ":firstName", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (!beneficiariesInfoDTO.getIdType().equals(IdType.STILL_APPLYING) && isEmpty(beneficiariesInfoDTO.getIdNo())) {
			addErrorMessage(formID + ":idNo", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}

		if (isEmpty(beneficiariesInfoDTO.getResidentAddress().getResidentAddress())) {
			addErrorMessage(formID + ":address", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}

		if (beneficiariesInfoDTO.getResidentAddress().getTownship() == null) {
			addErrorMessage(formID + ":townShip", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		if (beneficiariesInfoDTO.getPercentage() < 1 || beneficiariesInfoDTO.getPercentage() > 100) {
			addErrorMessage(formID + ":percentage", MessageId.BENEFICIARY_PERCENTAGE);
			valid = false;
		}
		return valid;
	}

	public void selectUser() {
		selectUser(WorkflowTask.SURVEY, WorkFlowType.LIFE, TransactionType.RENEWAL, user.getLoginBranch().getId(), null);
	}

	private void outjectLifeProposal(LifeProposal lifeProposal) {
		putParam("lifeProposal", lifeProposal);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void selectAddOn() {
		selectAddOn(product);
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		lifeProposal.setSaleBank(bankBranch);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		lifeProposal.setCustomer(customer);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		lifeProposal.setOrganization(organization);
	}

	public void returnPaymentType(SelectEvent event) {
		PaymentType paymentType = (PaymentType) event.getObject();
		lifeProposal.setPaymentType(paymentType);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		lifeProposal.setAgent(agent);
	}

	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		lifeProposal.setSalesPoints(salesPoints);
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

	public void returnTypesOfSport(SelectEvent event) {
		TypesOfSport typesOfSport = (TypesOfSport) event.getObject();
		insuredPersonInfoDTO.setTypesOfSport(typesOfSport);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnAddOn(SelectEvent event) {
		AddOn addOn = (AddOn) event.getObject();
		insuredPersonAddOnDTO.setAddOn(addOn);
	}

	public void returnCoinsurCompany(SelectEvent event) {
		CoinsuranceCompany coinsurCompany = (CoinsuranceCompany) event.getObject();
		insuredRecord.setCompany(coinsurCompany);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		insuredRecord.setProduct(product);
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

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public boolean isNewInsuredRecord() {
		return isNewInsuredRecord;
	}

	public void setNewInsuredRecord(boolean isNewInsuredRecord) {
		this.isNewInsuredRecord = isNewInsuredRecord;
	}

	public InsuredPersonPolicyHistoryRecord getInsuredRecord() {
		return insuredRecord;
	}

	public void setInsuredRecord(InsuredPersonPolicyHistoryRecord insuredRecord) {
		this.insuredRecord = insuredRecord;
	}

}
