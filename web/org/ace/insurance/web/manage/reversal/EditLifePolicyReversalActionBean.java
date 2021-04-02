package org.ace.insurance.web.manage.reversal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.EndorsementUtil;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PeriodType;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.occupation.service.interfaces.IOccupationService;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.system.common.township.service.interfaces.ITownshipService;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.life.proposal.BeneficiariesInfoDTO;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonAddOnDTO;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonInfoDTO;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.csvreader.CsvReader;

@ViewScoped
@ManagedBean(name = "EditLifePolicyReversalActionBean")
public class EditLifePolicyReversalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{TownshipService}")
	private ITownshipService townShipService;

	public void setTownShipService(ITownshipService townShipService) {
		this.townShipService = townShipService;
	}

	@ManagedProperty(value = "#{OccupationService}")
	private IOccupationService occupationService;

	public void setOccupationService(IOccupationService occupationService) {
		this.occupationService = occupationService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	private LifePolicy lifePolicy;
	private boolean createNew;
	List<RelationShip> relationShipList;
	private String userType;
	private boolean isAgent = true;
	private String productName;
	private Map<String, InsuredPersonInfoDTO> insuredPersonInfoDTOMap;

	@PostConstruct
	public void init() {
		lifePolicy = (LifePolicy) getParam("lifePolicy");
		if (lifePolicy.getCustomer() == null) {
			organization = true;
		}
		initializeInstance();
		for (PolicyInsuredPerson pv : lifePolicy.getPolicyInsuredPersonList()) {
			InsuredPersonInfoDTO vhDTO = new InsuredPersonInfoDTO(pv);
			insuredPersonInfoDTOMap.put(vhDTO.getTempId(), vhDTO);
		}
		productName = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getName();
		relationShipList = relationShipService.findAllRelationShip();
		organization = lifePolicy.getCustomer() == null ? true : false;
		isAgent = lifePolicy.getAgent() != null ? true : false;
		createNewInsuredPersonInfo();
		createNewBeneficiariesInfoDTOMap();

	}

	private void initializeInstance() {
		insuredPersonInfoDTOMap = new HashMap<String, InsuredPersonInfoDTO>();
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
	}

	public void changeSaleEvent(AjaxBehaviorEvent event) {
		lifePolicy.setSaleBank(null);
		lifePolicy.setAgent(null);
	}

	public PeriodType[] getPeriodType() {
		return new PeriodType[] { PeriodType.MONTH, PeriodType.YEAR };
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.BANK, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	/*************************************************
	 * Start Beneficiary Manage
	 *********************************************************/
	private boolean createNewBeneficiariesInfo;
	private Map<String, BeneficiariesInfoDTO> beneficiariesInfoDTOMap;
	private BeneficiariesInfoDTO beneficiariesInfoDTO;
	private RelationShip selectedRelationShip;

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
		this.createNewBeneficiariesInfo = false;
	}

	public void saveBeneficiariesInfo() {
		beneficiariesInfoDTO.setRelationship(selectedRelationShip);
		beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(), beneficiariesInfoDTO);
		insuredPersonInfoDTO.setBeneficiariesInfoDTOList(new ArrayList<BeneficiariesInfoDTO>(sortByValue(beneficiariesInfoDTOMap).values()));
		createNewBeneficiariesInfo();
		PrimeFaces.current().executeScript("PF('beneficiariesInfoEntryDialog').hide()");
	}

	public Map<String, BeneficiariesInfoDTO> sortByValue(Map<String, BeneficiariesInfoDTO> map) {
		List<Map.Entry<String, BeneficiariesInfoDTO>> list = new LinkedList<Map.Entry<String, BeneficiariesInfoDTO>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, BeneficiariesInfoDTO>>() {
			public int compare(Map.Entry<String, BeneficiariesInfoDTO> o1, Map.Entry<String, BeneficiariesInfoDTO> o2) {
				long l1 = 0l;
				long l2 = 0l;
				try {
					l1 = Long.parseLong(o1.getKey());
					l2 = Long.parseLong(o2.getKey());
				} catch (NumberFormatException e) {
					try {
						l1 = Long.parseLong(o1.getKey().substring(11, 20));
						l2 = Long.parseLong(o2.getKey().substring(11, 20));
					} catch (StringIndexOutOfBoundsException exp) {
						l1 = Long.parseLong(o1.getKey().substring(11));
						l2 = Long.parseLong(o2.getKey().substring(11));
					}
				}
				if (l1 > l2) {
					return 1;
				} else if (l1 < l2) {
					return -1;
				} else {
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
		insuredPersonInfoDTO.addInsuredPersonAddOn(insuredPersonAddOnDTO);
		createInsuredPersonAddOnDTO();
	}

	public void removeAddOn(InsuredPersonAddOnDTO insuredPersonAddOnDTO) {
		insuredPersonInfoDTO.removeInsuredPersonAddOn(insuredPersonAddOnDTO);
		createInsuredPersonAddOnDTO();
	}

	/*************************************************
	 * End AddOn Manage
	 *********************************************************/

	/*************************************************
	 * Start InsuredPerson Manage
	 ******************************************************/
	private InsuredPersonInfoDTO insuredPersonInfoDTO;
	private Boolean isEdit = false;
	private Boolean isReplace = false;

	private void createNewInsuredPersonInfo() {
		insuredPersonInfoDTO = new InsuredPersonInfoDTO();
		beneficiariesInfoDTO = new BeneficiariesInfoDTO();
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
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
		if (insuredPersonInfoDTO.getBeneficiariesInfoDTOList() != null) {
			createNewBeneficiariesInfoDTOMap();
			for (BeneficiariesInfoDTO bdto : insuredPersonInfoDTO.getBeneficiariesInfoDTOList()) {
				beneficiariesInfoDTOMap.put(bdto.getTempId(), bdto);
			}
		}
		insuredPersonInfoDTO.setInsuredPersonAddOnDTOList(new ArrayList<>(insuredPersonInfoDTO.getInsuredPersonAddOnDTOMap().values()));
		isEdit = true;
	}

	public void prepareReplaceInsuredPersonInfo(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		this.insuredPersonInfoDTO = insuredPersonInfoDTO;
		createNewBeneficiariesInfoDTOMap();
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
						Township t = townShipService.findTownshipById(data);
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
						lifePolicy.setPeriodMonth(Integer.parseInt(data));
					}
				}
				insuredPersonInfoDTO.setResidentAddress(ra);
				insuredPersonInfoDTO.setName(name);

				for (InsuredPersonKeyFactorValue insKFV : insuredPersonInfoDTO.getKeyFactorValueList()) {
					KeyFactor kf = insKFV.getKeyFactor();
					if (KeyFactorChecker.isSumInsured(kf)) {
						insKFV.setValue(insuredPersonInfoDTO.getSumInsuredInfo() + "");
					} else if (KeyFactorChecker.isTerm(kf)) {
						insKFV.setValue(lifePolicy.getPeriodMonth() + "");
					} else if (KeyFactorChecker.isAge(kf)) {
						insKFV.setValue(insuredPersonInfoDTO.getAgeForNextYear() + "");
					}
				}
				insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			}
		} catch (IOException e1) {
			FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Invalid data is occured in Uploaded File!"));
		}
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage("Successful Customer Information Uploaded!"));
	}

	public void saveInsuredPersonInfo() {
		if (lifePolicy != null) {
			PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
			if (isEdit) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.EDIT);
			} else if (isReplace) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.REPLACE);
			} else if (policyInsuPerson == null) {
				insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.NEW);
			}
		}

		if (validInsuredPerson(insuredPersonInfoDTO)) {
			if (lifePolicy == null) {
				setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifePolicy.getPeriodMonth());
			} else {
				PolicyInsuredPerson policyInsuPerson = lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo());
				if (policyInsuPerson == null) {
					insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.NEW);
				} else {
					if (isEdit && insuredPersonInfoDTO.getEndorsementStatus() != EndorsementStatus.REPLACE) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.EDIT);
					} else if (isReplace == true) {
						insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.REPLACE);
					}
				}
				if (checkPublicLife()) {
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(lifePolicy.getInsuredPersonInfo().get(0).getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()),
								lifePolicy.getPeriodMonth());
					} else {
						if (isIncreasedSIAmount(insuredPersonInfoDTO)) {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifePolicy.getPeriodMonth());
						} else {
							setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()), lifePolicy.getPeriodMonth());
						}
					}
				} else {
					if (insuredPersonInfoDTO.getEndorsementStatus() == EndorsementStatus.TERMINATE) {
						setKeyFactorValue(policyInsuPerson.getSumInsured(), getAgeForOldYear(insuredPersonInfoDTO.getDateOfBirth()),
								policyInsuPerson.getLifePolicy().getPeriodMonth());
					} else {
						setKeyFactorValue(insuredPersonInfoDTO.getSumInsuredInfo(), insuredPersonInfoDTO.getAgeForNextYear(), lifePolicy.getPeriodMonth());
					}
				}
			}
			insuredPersonInfoDTOMap.put(insuredPersonInfoDTO.getTempId(), insuredPersonInfoDTO);
			createNewInsuredPersonInfo();
			createNewBeneficiariesInfoDTOMap();
		}
	}

	private boolean validInsuredPerson(InsuredPersonInfoDTO insuredPersonInfoDTO) {
		boolean valid = true;
		String formID = "lifePolicyEntryForm";
		if (KeyFactorChecker.isPublicLife(insuredPersonInfoDTO.getProduct()) && insuredPersonInfoDTO.getDateOfBirth() != null) {
			if (insuredPersonInfoDTO.getAgeForNextYear() < 10) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, 10);
				valid = false;
			} else if (lifePolicy.getPeriodMonth() < 5) {
				addErrorMessage(formID + ":periodOfInsurance", MessageId.MINIMUN_INSURED_PERIOD, 5);
				valid = false;
			} else if (insuredPersonInfoDTO.getAgeForNextYear() + lifePolicy.getPeriodMonth() > 65) {
				int availablePeriod = 65 - insuredPersonInfoDTO.getAgeForNextYear();
				addErrorMessage(formID + ":periodOfInsurance", MessageId.MAXIMUM_INSURED_YEARS, availablePeriod < 0 ? 0 : availablePeriod);
				valid = false;
			}
		}
		if (KeyFactorChecker.isGroupLife(insuredPersonInfoDTO.getProduct()) && insuredPersonInfoDTO.getDateOfBirth() != null) {
			if (insuredPersonInfoDTO.getAgeForNextYear() < 10) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, 10);
				valid = false;
			} else if (insuredPersonInfoDTO.getAgeForNextYear() > 60) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, 60);
				valid = false;
			} else if (lifePolicy.getPeriodMonth() != 1) {
				addErrorMessage(formID + ":periodOfInsurance", MessageId.AVAILABLE_INSURED_PERIOD, 1);
				valid = false;
			} else if (insuredPersonInfoDTO.getAgeForNextYear() + lifePolicy.getPeriodMonth() > 65) {
				int availablePeriod = 60 - insuredPersonInfoDTO.getAgeForNextYear();
				addErrorMessage(formID + ":periodOfInsurance", MessageId.MAXIMUM_INSURED_YEARS, availablePeriod < 0 ? 0 : availablePeriod);
				valid = false;
			}
		}
		// new Product PA
		if (KeyFactorChecker.isPersonalAccident(insuredPersonInfoDTO.getProduct())) {
			if (insuredPersonInfoDTO.getAgeForNextYear() < 16) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MINIMUN_INSURED_PERSON_AGE, 16);
				valid = false;
			}
			if (insuredPersonInfoDTO.getAgeForNextYear() > 65) {
				addErrorMessage(formID + ":dateOfBirth", MessageId.MAXIMUM_INSURED_PERSON_AGE, 65);
				valid = false;
			}
			if (lifePolicy.getPeriodMonth() != 3 && lifePolicy.getPeriodMonth() != 6 && lifePolicy.getPeriodMonth() != 12) {
				addErrorMessage(formID + ":periodOfInsurance", MessageId.AVAILABLE_INSURED_PERIOD, "3 or 6 or 12 Months");
				valid = false;
			}

			if (getInsuredPersonInfoDTOList().size() >= 1 && !isEdit) {
				addErrorMessage("lifeProposalEntryForm:insuredPersonInfoDTOTable", MessageId.PA_MAX_INSURED_PERSON, 1);
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
		if (lifePolicy != null && checkPublicLife()) {
			if (getPassedMonths() > lifePolicy.getPeriodMonth()) {
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
		if (lifePolicy != null && lifePolicyService.findInsuredPersonByCodeNo(insuredPersonInfoDTO.getInsPersonCodeNo()) != null) {
			insuredPersonInfoDTO.setEndorsementStatus(EndorsementStatus.TERMINATE);
		} else {
			insuredPersonInfoDTOMap.remove(insuredPersonInfoDTO.getTempId());
		}
		createNewInsuredPersonInfo();
	}

	public String backLifePolicy() {
		createNewInsuredPersonInfo();
		return "lifePolicy";
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
			lifePolicy.setCustomer(null);
		} else {
			lifePolicy.setOrganization(null);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public LifePolicy getLifePolicy() {
		if (lifePolicy == null) {
			if (lifePolicy.getCustomer() != null) {
				Customer customer = lifePolicy.getCustomer();
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
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public String updateLifePolicy() {
		String result = null;
		try {
			lifePolicy.setPolicyInsuredPersonList(getInsuredPersonInfoList());
			lifePolicyService.overwriteLifePolicy(lifePolicy);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_POLICY_PROCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public List<PolicyInsuredPerson> getInsuredPersonInfoList() {
		List<PolicyInsuredPerson> result = new ArrayList<PolicyInsuredPerson>();
		if (insuredPersonInfoDTOMap.values() != null) {
			for (InsuredPersonInfoDTO insuredPersonInfoDTO : insuredPersonInfoDTOMap.values()) {
				PolicyInsuredPerson policyInsuredPerson = new PolicyInsuredPerson(insuredPersonInfoDTO);
				policyInsuredPerson.setLifePolicy(lifePolicy);

				result.add(policyInsuredPerson);
			}
		}
		return result;
	}

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		if ("InsuredPersonInfo".equals(event.getOldStep()) && "workflow".equals(event.getNewStep())) {
			if (getInsuredPersonInfoDTOList().isEmpty()) {
				addErrorMessage("lifePolicyEntryForm:insuredPersonInfoDTOTable", MessageId.REQUIRED_DRIVER);
				valid = false;
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public String getPublicLifeId() {
		return KeyFactorIDConfig.getPublicLifeId();
	}

	public Boolean isIncreasedSIAmount(InsuredPersonInfoDTO dto) {
		if (dto.getSumInsuredInfo() <= lifePolicy.getPolicyInsuredPersonList().get(0).getSumInsured()) {
			return false;
		}
		return true;
	}

	public boolean checkPublicLife() {
		Boolean isPublicLife = true;
		String productId = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getId();
		if (productId.trim().equals(getPublicLifeId())) {
			isPublicLife = true;
		} else {
			isPublicLife = false;
		}
		return isPublicLife;
	}

	public int getAgeForOldYear(Date dob) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(lifePolicy.getCommenmanceDate());
		int currentYear = cal_1.get(Calendar.YEAR);

		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dob);
		cal_2.set(Calendar.YEAR, currentYear);
		if (lifePolicy.getCommenmanceDate().after(cal_2.getTime())) {
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

	public boolean getIsEndorse() {
		return (EndorsementUtil.isEndorsementProposal(lifePolicy));
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
				int period = lifePolicy.getPeriodMonth();
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
		DateTime vDate = new DateTime(lifePolicy.getActivedPolicyEndDate());
		DateTime cDate = new DateTime(lifePolicy.getCommenmanceDate());
		int paymentType = lifePolicy.getPaymentType().getMonth();
		int passedMonths = Months.monthsBetween(cDate, vDate).getMonths();
		if (paymentType != 0) {
			if (passedMonths % paymentType != 0) {
				passedMonths = passedMonths + 1;
			}
		}
		return passedMonths;
	}

	public int getPassedYears() {
		return getPassedMonths() / 12;
	}

	public void showPaidUpDialog() {
		RequestContext rc = RequestContext.getCurrentInstance();
		if (lifePolicy != null && isEdit == true && isDecreasedSIAmount() && checkPublicLife()) {
			rc.execute("paidPremiumConfirmationDialog.show()");
		} else {
			saveInsuredPersonInfo();
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

	public String getUserType() {
		if (lifePolicy.getAgent() != null) {
			userType = SaleChannelType.AGENT.toString();
		}
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isAgent() {
		return isAgent;
	}

	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		lifePolicy.setSaleBank(bankBranch);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		lifePolicy.setAgent(agent);
	}

	public void returnTownShip(SelectEvent event) {
		Township townShip = (Township) event.getObject();
		beneficiariesInfoDTO.getResidentAddress().setTownship(townShip);
	}

	public void returnOccupation(SelectEvent event) {
		Occupation occupation = (Occupation) event.getObject();
		insuredPersonInfoDTO.setOccupation(occupation);
	}

	public void selectAddOn() {
		selectAddOn(insuredPersonInfoDTO.getProduct());
	}

	// TODO FIXME PSH modify method for same names
	public void selectTownShip() {
		selectTownship();
	}

	public void returnInsuPersonTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		insuredPersonInfoDTO.getResidentAddress().setTownship(township);
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

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

	public RelationShip getSelectedRelationShip() {
		return selectedRelationShip;
	}

	public void setSelectedRelationShip(RelationShip selectedRelationShip) {
		this.selectedRelationShip = selectedRelationShip;
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
