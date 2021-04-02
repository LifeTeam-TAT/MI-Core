package org.ace.insurance.web.manage.life.proposal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PeriodType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
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
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.csvreader.CsvReader;

@ViewScoped
@ManagedBean(name = "EditLifeProposalActionBean")
public class EditLifeProposalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{OccupationService}")
	private IOccupationService occupationService;

	public void setOccupationService(IOccupationService occupationService) {
		this.occupationService = occupationService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townshipService;

	public void setTownshipService(ITownshipService townshipService) {
		this.townshipService = townshipService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

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

	private User user;
	private LifeProposal lifeProposal;
	private LifePolicy lifePolicy;
	private User responsiblePerson;
	private String userType;
	private String remark;
	private boolean createNew;
	private boolean isSportMan;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSnakeBite;
	private boolean isShortTermEndownment;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isMonthBase;
	private boolean isEndorse;
	private boolean isNewInsuredRecord;
	private Product product;
	private InsuredPersonPolicyHistoryRecord insuredRecord;
	private Map<String, InsuredPersonPolicyHistoryRecord> insuredRecordMap;
	private List<Product> productList;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PeriodType[] getPeriodType() {
		return new PeriodType[] { PeriodType.MONTH, PeriodType.YEAR };
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		InsuranceType insuranceType = (InsuranceType) getParam("insuranceType");
		productList = productService.findProductByInsuranceType(insuranceType);
		this.product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
		removeParam("insuranceType");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		isEndorse = lifeProposal.getProposalType().equals(ProposalType.ENDORSEMENT);
		isPersonalAccident = (KeyFactorChecker.isPersonalAccident(product));
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		isMonthBase = KeyFactorChecker.isPublicLife(product) || KeyFactorChecker.isGroupLife(product) ? false : true;
		isSportMan = KeyFactorChecker.isSportMan(product);
		organization = lifeProposal.getCustomer() == null ? true : false;
		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			InsuredPersonInfoDTO vhDTO = new InsuredPersonInfoDTO(pv);
			insuredPersonInfoDTOMap.put(vhDTO.getTempId(), vhDTO);
		}
		createNewInsuredPersonInfo();
		createNewBeneficiariesInfoDTOMap();
		createInsuredPersonAddOnDTO();
		lifePolicy = lifeProposal.getLifePolicy();
	}

	/*************************************************
	 * Start Beneficiary Manage
	 *********************************************************/
	private boolean createNewBeneficiariesInfo;
	private Map<String, BeneficiariesInfoDTO> beneficiariesInfoDTOMap;
	private BeneficiariesInfoDTO beneficiariesInfoDTO;

	public IdType[] getIdTypes() {
		return IdType.values();
	}

	public Gender[] getGender() {
		return Gender.values();
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.BANK, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipService.findAllRelationShip();
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
		this.createNewBeneficiariesInfo = false;
	}

	public void saveBeneficiariesInfo() {
		beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(), beneficiariesInfoDTO);
		insuredPersonInfoDTO.setBeneficiariesInfoDTOList(new ArrayList<BeneficiariesInfoDTO>(beneficiariesInfoDTOMap.values()));
		createNewBeneficiariesInfo();
		RequestContext.getCurrentInstance().execute("beneficiariesInfoEntryDialog.hide()");
	}

	public void removeBeneficiariesInfo(BeneficiariesInfoDTO beneficiariesInfoDTO) {
		beneficiariesInfoDTOMap.remove(beneficiariesInfoDTO.getTempId());
		insuredPersonInfoDTO.getBeneficiariesInfoDTOList().remove(beneficiariesInfoDTO);
		createNewBeneficiariesInfo();
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
	private Map<String, InsuredPersonInfoDTO> insuredPersonInfoDTOMap;
	private InsuredPersonInfoDTO insuredPersonInfoDTO;
	private boolean createNewIsuredPersonInfo;
	private Boolean isEdit = false;
	private Boolean isReplace = false;

	public boolean isCreateNewInsuredPersonInfo() {
		return createNewIsuredPersonInfo;
	}

	private void createNewInsuredPersonInfo() {
		createNewIsuredPersonInfo = true;
		insuredPersonInfoDTO = new InsuredPersonInfoDTO();
		beneficiariesInfoDTOMap = new HashMap<String, BeneficiariesInfoDTO>();
		beneficiariesInfoDTO = new BeneficiariesInfoDTO();
		createNewInsuredRecordList();
		createNewInsuredRecord();
		isEdit = false;
		isReplace = false;
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
					if (!dto.getInsuredPersonAddOnDTOMap().values().isEmpty()) {
						dto.setInsuredPersonAddOnDTOList(new ArrayList<InsuredPersonAddOnDTO>(dto.getInsuredPersonAddOnDTOMap().values()));
					}
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

	public void createNewBeneficiariesInfoDTOMap() {
		beneficiariesInfoDTOMap = new HashMap<String, BeneficiariesInfoDTO>();
	}

	public void prepareEditInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		this.product = insuredPersonInfoDTO.getProduct();
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
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

		if (KeyFactorChecker.isSportMan(insuredPersonInfoDTO.getProduct())) {
			isSportMan = true;
		}

		createNewIsuredPersonInfo = false;
		isEdit = true;
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
			List<InsuredPersonInfoDTO> insuredPersonInfoDTOList = new ArrayList<InsuredPersonInfoDTO>();
			InputStream inputStream = uploadedFile.getInputstream();
			CsvReader reader = new CsvReader(inputStream, Charset.forName("UTF-8"));
			reader.readHeaders();
			while (reader.readRecord()) {
				InsuredPersonInfoDTO insuredPersonInfoDTO = new InsuredPersonInfoDTO();
				Name name = new Name();
				ResidentAddress ra = new ResidentAddress();
				insuredPersonInfoDTOList.add(insuredPersonInfoDTO);
				for (int i = 0; i < reader.getHeaders().length; i++) {
					String headerName[] = reader.getHeaders();
					String data = reader.get(reader.getHeader(i));
					if (headerName[i].trim().equalsIgnoreCase("INITIALID")) {
						insuredPersonInfoDTO.setInitialId(data);
					} else if (headerName[i].trim().equalsIgnoreCase("FIRSTNAME")) {
						name.setFirstName(data);
					} else if (headerName[i].trim().equalsIgnoreCase("MIDDLENAME")) {
						name.setMiddleName(data);
					} else if (headerName[i].trim().equalsIgnoreCase("LASTNAME")) {
						name.setLastName(data);
					} else if (headerName[i].trim().equalsIgnoreCase("FATHERNAME")) {
						insuredPersonInfoDTO.setFatherName(data);
					} else if (headerName[i].trim().equalsIgnoreCase("IDNO")) {
						insuredPersonInfoDTO.setIdNo(data);
					} else if (headerName[i].trim().equalsIgnoreCase("IDTYPE")) {
						insuredPersonInfoDTO.setIdType(IdType.valueOf(data));
					} else if (headerName[i].trim().equalsIgnoreCase("DATEOFBIRTH")) {
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						try {
							Date date = df.parse(data);
							insuredPersonInfoDTO.setDateOfBirth(date);
						} catch (Exception e) {
							FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Invalid date format is occured in Uploaded File!"));
						}
					} else if (headerName[i].trim().equalsIgnoreCase("SUMINSURED")) {
						insuredPersonInfoDTO.setSumInsuredInfo(Double.parseDouble(data));
					} else if (headerName[i].trim().equalsIgnoreCase("RESIDENTADDRESS")) {
						ra.setResidentAddress(data);
					} else if (headerName[i].trim().equalsIgnoreCase("RESIDENTTOWNSHIPID")) {
						Township t = townshipService.findTownshipById(data);
						ra.setTownship(t);
					} else if (headerName[i].trim().equalsIgnoreCase("OCCUPATIONID")) {
						Occupation oc = occupationService.findOccupationById(data);
						insuredPersonInfoDTO.setOccupation(oc);
					} else if (headerName[i].trim().equalsIgnoreCase("GENDER")) {
						insuredPersonInfoDTO.setGender(Gender.valueOf(data));
					} else if (headerName[i].trim().equalsIgnoreCase("PRODUCTID")) {
						Product t = productService.findProductById(data);
						insuredPersonInfoDTO.setProduct(t);
					} else if (headerName[i].trim().equalsIgnoreCase("PERIOD_OF_INSURANCE_YEAR")) {
						lifeProposal.setPeriodMonth(Integer.parseInt(data));
					}
				} // End For
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
			} // End While
		} catch (IOException e1) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Invalid data is occured in Uploaded File!"));
		}
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Successful Customer Information Uploaded!"));
	}

	public void saveInsuredPersonInfo() {
		insuredPersonInfoDTO.setProduct(product);
		if (isEndorse) {
			PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
			if (isEdit == true) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.EDIT);
			} else if (isReplace == true) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.REPLACE);
			} else if (policyInsuPerson == null) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.NEW);
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(lifeProposal.getStartDate());
		if (isMonthBase) {
			cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
		} else {
			cal.add(Calendar.YEAR, lifeProposal.getPeriodMonth());
		}
		if (validInsuredPerson(insuredPersonInfoDTO)) {
			if (!isEndorse) {
				setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getPeriodMonth());
				// For Endorsement
			} else {
				// Set InsuredPerson Endorsement Status
				PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
				if (policyInsuPerson == null) {
					insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.NEW);
				} else {
					if (isEdit == true && insuredPersonInfoDTO.getEndorsementStatus() != EndorsementStatus.REPLACE) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.EDIT);
					} else if (isReplace == true) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.REPLACE);
					}
				}
				// Set Key Factor For Public Life
				if (checkPublicLife()) {
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(lifeProposal.getLifePolicy().getInsuredPersonInfo().get(0).getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()),
								lifeProposal.getLifePolicy().getPeriodMonth());
					} else {
						if (isIncreasedSIAmount(insuredPersonInfoDTO)) {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getLifePolicy().getPeriodMonth());
						} else {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()), lifeProposal.getPeriodMonth());
						}
					}
					// Set Key Factor For Group Life
				} else {
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(policyInsuPerson.getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()), lifeProposal.getLifePolicy().getPeriodMonth());
					} else {
						setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifeProposal.getPeriodMonth());
					}
				}
			}
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);

			InsuredPersonInfoDTO tempDTO = new InsuredPersonInfoDTO();
			for (InsuredPersonInfoDTO in : insuredPersonInfoDTOMap.values()) {
				tempDTO = in;
			}

			if (KeyFactorChecker.isSportMan(tempDTO.getProduct())) {
				surveyAgain = false;
			}

			createNewInsuredPersonInfo();
			createNewBeneficiariesInfoDTOMap();
		}
	}

	public List<PaymentType> getPaymentTypes() {
		if (product == null) {
			return new ArrayList<PaymentType>();
		} else {
			return product.getPaymentTypeList();
		}
	}

	public int getMaximumTrem() {
		int result = 0;
		if (product != null) {
			result = isMonthBase ? product.getMaxTerm() : product.getMaxTerm() / 12;
		}
		return result;
	}

	public int getMunimumTrem() {
		int result = 0;
		if (product != null) {
			result = isMonthBase ? product.getMinTerm() : product.getMinTerm() / 12;
		}
		return result;
	}

	public boolean getIsPersonalAccident() {
		return isPersonalAccident;
	}

	public boolean getIsFarmer() {
		return isFarmer;
	}

	public boolean getIsSportMan() {
		return isSportMan;
	}

	public boolean getIsMonthBase() {
		return isMonthBase;
	}

	public List<Integer> getPeriodMonths() {
		return Arrays.asList(3, 6, 12);
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
		// FIXME CHANGED MAXIMUMSUMINSRED TO MAXVLAUE
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
		// FIXME CHANGED MAXIMUMSUMINSRED TO MAXVLAUE
		return sumInsured > insuredPersonInfoDTO.getProduct().getMaxValue() ? true : false;
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
		int periodofYear = lifeProposal.getPeriodMonth();
		if (personAge < minAge) {
			addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, minAge);
			valid = false;
		} else if (personAge > maxAge) {
			addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, maxAge);
			valid = false;
		} else if (personAge + periodofYear > maxAge) {
			addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_YEARS, maxAge - personAge);
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

		if (lifeProposal.getLifePolicy() != null && checkPublicLife()) {
			if (getPassedMonths() > lifeProposal.getPeriodMonth()) {
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

	public void removeInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		if (lifeProposal.getLifePolicy() != null && lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo()) != null) {
			insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.TERMINATE);
		} else {
			insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
		}
		createNewInsuredPersonInfo();
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
	private boolean organization;

	public boolean isOrganization() {
		return organization;
	}

	public void setOrganization(boolean organization) {
		this.organization = organization;
	}

	public void changeOrgEvent(AjaxBehaviorEvent event) {
		if (organization) {
			lifeProposal.setCustomer(null);
		} else {
			lifeProposal.setOrganization(null);
		}
	}

	public List<Product> getProductList() {
		return productList;
	}

	/*************************************************
	 * Proposal Manage
	 **********************************************/
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

	private boolean surveyAgain;

	public boolean isSurveyAgain() {
		return surveyAgain;
	}

	public void setSurveyAgain(boolean surveyAgain) {
		this.surveyAgain = surveyAgain;

	}

	public boolean isNewInsuredRecord() {
		return isNewInsuredRecord;
	}

	public void setNewInsuredRecord(boolean isNewInsuredRecord) {
		this.isNewInsuredRecord = isNewInsuredRecord;
	}

	public void changeEvent(AjaxBehaviorEvent event) {
		responsiblePerson = null;
	}

	public void selectSurveyUser() {
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT : isFarmer ? WorkFlowType.FARMER : WorkFlowType.LIFE;
		WorkflowTask workflowTask = null;
		if (surveyAgain) {
			workflowTask = isEndorse ? WorkflowTask.SURVEY : WorkflowTask.SURVEY;
		} else {
			workflowTask = isEndorse ? WorkflowTask.APPROVAL : WorkflowTask.APPROVAL;
		}
		selectUser(workflowTask, workFlowType, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public String updateLifeProposal() {
		String result = null;
		try {
			lifeProposal.setInsuredPersonList(getInsuredPersonInfoList());
			WorkflowTask workflowTask = surveyAgain ? WorkflowTask.SURVEY : WorkflowTask.APPROVAL;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), getLoginBranchId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			if (isEndorse) {
				lifeEndorsementService.updateLifeProposal(lifeProposal, null, workFlowDTO);
			} else {
				lifeProposalService.updateLifeProposal(lifeProposal, workFlowDTO);
			}
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public List<ProposalInsuredPerson> getInsuredPersonInfoList() {
		List<ProposalInsuredPerson> result = new ArrayList<ProposalInsuredPerson>();
		if (insuredPersonInfoDTOMap.values() != null) {
			for (InsuredPersonInfoDTO insuredPersonInfoDTO : insuredPersonInfoDTOMap.values()) {
				Customer customer = customerService.findCustomerByInsuredPerson(insuredPersonInfoDTO.getName(), insuredPersonInfoDTO.getIdNo(),
						insuredPersonInfoDTO.getFatherName(), insuredPersonInfoDTO.getDateOfBirth());

				ProposalInsuredPerson proposalInsuredPerson = new ProposalInsuredPerson(insuredPersonInfoDTO);
				proposalInsuredPerson.setCustomer(customer);
				proposalInsuredPerson.setLifeProposal(lifeProposal);

				result.add(proposalInsuredPerson);
			}
		}
		return result;
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("InsuredPersonInfo".equals(event.getOldStep()) && "workflow".equals(event.getNewStep())) {
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
				} else if (!(KeyFactorChecker.isPersonalAccident(product))) {
					if (personSize > 1) {
						addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable", MessageId.INVALID_INSURED_PERSON, product.getName());
						valid = false;
					}
				}
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public String getPublicLifeId() {
		return KeyFactorIDConfig.getPublicLifeId();
	}

	public Boolean isIncreasedSIAmount(InsuredPersonInfoDTO dto) {
		if (dto.getSumInsuredInfo() <= lifeProposal.getLifePolicy().getPolicyInsuredPersonList().get(0).getSumInsured()) {
			return false;
		}
		return true;
	}

	public boolean checkPublicLife() {
		Boolean isPublicLife = true;
		String productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId();
		if (productId.equals(getPublicLifeId())) {
			isPublicLife = true;
		} else {
			isPublicLife = false;
		}
		return isPublicLife;
	}

	public int getAgeForOldYear(Date dob) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(lifeProposal.getLifePolicy().getCommenmanceDate());
		int currentYear = cal_1.get(Calendar.YEAR);

		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dob);
		cal_2.set(Calendar.YEAR, currentYear);
		if (lifeProposal.getLifePolicy().getCommenmanceDate().after(cal_2.getTime())) {
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

	/*
	 * public boolean isCustomer() { if(lifeProposal.getCustomer() != null) {
	 * return true; } else { return false; } }
	 */

	public boolean getIsEndorse() {
		return isEndorse;
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

	private boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
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

	public int getPassedMonths() {
		DateTime vDate = new DateTime(lifeProposal.getLifePolicy().getActivedPolicyEndDate());
		DateTime cDate = new DateTime(lifeProposal.getLifePolicy().getCommenmanceDate());
		int paymentType = lifeProposal.getLifePolicy().getPaymentType().getMonth();
		int passedMonths = Months.monthsBetween(cDate, vDate).getMonths();
		if (passedMonths % paymentType != 0) {
			passedMonths = passedMonths + 1;
		}
		return passedMonths;
	}

	public int getPassedYears() {
		return getPassedMonths() / 12;
	}

	public void addNewInsuredPersonInfo() {
		if (createNewIsuredPersonInfo) {
			saveInsuredPersonInfo();
		} else {
			if (isEndorse && isEdit && isDecreasedSIAmount() && checkPublicLife()) {
				RequestContext.getCurrentInstance().execute("paidPremiumConfirmationDialog.show()");
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

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		lifeProposal.setSaleBank(bankBranch);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		lifeProposal.setBranch(branch);
	}

	public void returnTownShip(SelectEvent event) {
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

	public void returnApprover(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void changeSaleEvent(AjaxBehaviorEvent event) {
		lifeProposal.setSaleBank(null);
		lifeProposal.setAgent(null);
	}

	public void returnCoinsurCompany(SelectEvent event) {
		CoinsuranceCompany coinsurCompany = (CoinsuranceCompany) event.getObject();
		insuredRecord.setCompany(coinsurCompany);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		insuredRecord.setProduct(product);
	}

	public void selectAddOn() {
		selectAddOn(product);
	}

	public void returnInsuredPersonTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		insuredPersonInfoDTO.getResidentAddress().setTownship(townShip);
	}

	private Map<String, InsuredPersonAddOnDTO> insuredPersonAddOnDTOMap = new HashMap<>();

	public void returnAddOn(SelectEvent event) {
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

	public String getPageHeader() {
		return "Edit " + (isEndorse ? "Life Endorsement" : isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : "Life") + " Proposal";
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
		insuredPersonInfoDTO.setInsuredPersonPolicyHistoryRecordList(new ArrayList<InsuredPersonPolicyHistoryRecord>(insuredRecordMap.values()));
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

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}

}
