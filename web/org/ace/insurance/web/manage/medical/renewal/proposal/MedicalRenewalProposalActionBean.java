package org.ace.insurance.web.manage.medical.renewal.proposal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "MedicalRenewalProposalActionBean")
public class MedicalRenewalProposalActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case
	/*
	 * 
	 * @ManagedProperty(value = "#{ProposalBeneficiariesValidator}") private
	 * DTOValidator<MedProInsuBeneDTO> beneficiariesValidator;
	 * 
	 * public void setBeneficiariesValidator(DTOValidator<MedProInsuBeneDTO>
	 * beneficiariesValidator) { this.beneficiariesValidator =
	 * beneficiariesValidator; }
	 * 
	 * @ManagedProperty(value = "#{ProposalValidator}") private
	 * DTOValidator<MedProDTO> proposalValidator;
	 * 
	 * public void setProposalValidator(DTOValidator<MedProDTO>
	 * proposalValidator) { this.proposalValidator = proposalValidator; }
	 * 
	 * @ManagedProperty(value = "#{ProposalInsuredPersonValidator}") private
	 * DTOValidator<MedProInsuDTO> insuredpersonValidator;
	 * 
	 * public void setInsuredpersonValidator(DTOValidator<MedProInsuDTO>
	 * insuredpersonValidator) { this.insuredpersonValidator =
	 * insuredpersonValidator; }
	 * 
	 * @ManagedProperty(value = "#{CustomerValidator}") private
	 * DTOValidator<CustomerDTO> customerValidator;
	 * 
	 * public void setCustomerValidator(DTOValidator<CustomerDTO>
	 * customerValidator) { this.customerValidator = customerValidator; }
	 * 
	 * @ManagedProperty(value = "#{ReligionService}") protected IReligionService
	 * religionService;
	 * 
	 * public void setReligionService(IReligionService religionService) {
	 * this.religionService = religionService; }
	 * 
	 * @ManagedProperty(value = "#{BankBranchService}") protected
	 * IBankBranchService bankBranchService;
	 * 
	 * public void setBankBranchService(IBankBranchService bankBranchService) {
	 * this.bankBranchService = bankBranchService; }
	 * 
	 * @ManagedProperty(value = "#{RelationShipService}") private
	 * IRelationShipService relationShipService;
	 * 
	 * public void setRelationShipService(IRelationShipService
	 * relationShipService) { this.relationShipService = relationShipService; }
	 * 
	 * @ManagedProperty(value = "#{ProductService}") private IProductService
	 * productService;
	 * 
	 * public void setProductService(IProductService productService) {
	 * this.productService = productService; }
	 * 
	 * @ManagedProperty(value = "#{BranchService}") private IBranchService
	 * branchService;
	 * 
	 * public void setBranchService(IBranchService branchService) {
	 * this.branchService = branchService; }
	 * 
	 * @ManagedProperty(value = "#{StateCodeService}") private IStateCodeService
	 * stateCodeService;
	 * 
	 * public void setStateCodeService(IStateCodeService stateCodeService) {
	 * this.stateCodeService = stateCodeService; }
	 * 
	 * @ManagedProperty(value = "#{TownshipCodeService}") private
	 * ITownshipCodeService townshipCodeService;
	 * 
	 * public void setTownshipCodeService(ITownshipCodeService
	 * townshipCodeService) { this.townshipCodeService = townshipCodeService; }
	 * 
	 * @ManagedProperty(value = "#{CoinsuranceCompanyService}") private
	 * ICoinsuranceCompanyService coinsuranceCompanyService;
	 * 
	 * public void setCoinsuranceCompanyService(ICoinsuranceCompanyService
	 * coinsuranceCompanyService) { this.coinsuranceCompanyService =
	 * coinsuranceCompanyService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalProposalService}") private
	 * IMedicalProposalService proposalService;
	 * 
	 * public void setProposalService(IMedicalProposalService proposalService) {
	 * this.proposalService = proposalService; }
	 * 
	 * @ManagedProperty(value = "#{AddNewMedicalProposalFrontService}") private
	 * IAddNewMedicalProposalFrontService proposalFrontService;
	 * 
	 * public void setProposalFrontService(IAddNewMedicalProposalFrontService
	 * proposalFrontService) { this.proposalFrontService = proposalFrontService;
	 * }
	 * 
	 * private int unit; private int periodYear; private String remark; private
	 * String userType; private String insuredPersonFullName; private static
	 * final String formID = "medicalProposalEntryForm";
	 * 
	 * private boolean createNewAddOn; private boolean createEdit; private
	 * boolean createNewBeneficiariesInfo; private boolean
	 * createNewIsuredPersonInfo; private boolean existingCustomer; private
	 * boolean existingGuardian; private boolean haveTownship; private boolean
	 * isConfirmEdit; private boolean isEnquiryEdit; private boolean
	 * surveyAgain; private boolean isEditHistoryRecord;
	 * 
	 * private User user; private User responsiblePerson; private MedProInsuDTO
	 * insuredPersonDTO; private MedicalPersonHistoryRecord historyRecord;
	 * private Unit selectedUnit; private Product product; private MedProDTO
	 * medicalProposalDTO; private MedicalProposal medicalProposal; private
	 * MedProInsuBeneDTO beneficiariesInfoDTO; private MedProInsuAddOnDTO
	 * insuredPersonAddOnDTO;
	 * 
	 * private List<RelationShip> relationshipList; private List<Product>
	 * productList; private List<Religion> religionList; private
	 * List<BankBranch> bankBranchList; private List<CoinsuranceCompany>
	 * companyList; private List<StateCode> stateCodeList = new
	 * ArrayList<StateCode>(); private List<TownshipCode> townshipCodeList = new
	 * ArrayList<TownshipCode>(); private List<MedProInsuAddOnDTO>
	 * selectedAddOnLists; private List<MedProInsuAddOnDTO> addOnList; private
	 * List<Product> selectedProduct;
	 * 
	 * private Map<String, MedProInsuDTO> insuredPersonDTOMap; private
	 * Map<String, MedProInsuBeneDTO> beneficiariesInfoDTOMap; private
	 * Map<String, MedProInsuAddOnDTO> insuredPersonAddOnDTOMap; private
	 * Map<String, MedicalPersonHistoryRecord> historyRecordMap; private
	 * Map<Integer, Unit> unitsMap;
	 * 
	 * private void loadData() { productList =
	 * productService.findProductByInsuranceType(InsuranceType.MEDICAL);
	 * relationshipList = relationShipService.findAllRelationShip(); product =
	 * productService.findProductById(KeyFactorIDConfig.getMedicalProductId());
	 * religionList = religionService.findAllReligion(); bankBranchList =
	 * bankBranchService.findAllBankBranch(); stateCodeList =
	 * stateCodeService.findAllStateCode(); companyList =
	 * coinsuranceCompanyService.findAll(); }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; if
	 * (isExistParam("editMedicalRenewalProposal")) { medicalProposal =
	 * (MedicalProposal) getParam("editMedicalRenewalProposal"); isConfirmEdit =
	 * true; } else if (isExistParam("editEnquiryMedicalRenewalProposal")) {
	 * medicalProposal = (MedicalProposal)
	 * getParam("editEnquiryMedicalRenewalProposal"); isEnquiryEdit = true; }
	 * else { MedicalPolicy policy = (MedicalPolicy) getParam("medicalPolicy");
	 * medicalProposal = new MedicalProposal(policy);
	 * medicalProposal.setOldMedicalPolicy(policy);
	 * medicalProposal.setSubmittedDate(policy.getPolicyInsuredPersonList().get(
	 * 0).getEndDate()); checkProduct(); } medicalProposalDTO =
	 * MedicalProposalFactory.getMedProDTO(medicalProposal); periodYear = 1; if
	 * (medicalProposalDTO.getAgent() != null) { userType =
	 * UserType.AGENT.toString(); } else if (medicalProposalDTO.getSaleMan() !=
	 * null) { userType = UserType.SALEMAN.toString(); } else { userType =
	 * UserType.REFERRAL.toString(); }
	 * checkIdType(medicalProposalDTO.getCustomer().getIdType(), "C");
	 * loadTownshipCodeByStateCode(medicalProposalDTO.getCustomer().getStateCode
	 * ()); }
	 * 
	 * @PostConstruct public void init() { loadData(); initializeInjection();
	 * createNewMedicalInsuredPerson(); createNewBeneficiariesInfo();
	 * insuredPersonDTO = new MedProInsuDTO(); insuredPersonDTOMap = new
	 * HashMap<String, MedProInsuDTO>(); beneficiariesInfoDTOMap = new
	 * HashMap<String, MedProInsuBeneDTO>(); insuredPersonAddOnDTOMap = new
	 * LinkedHashMap<String, MedProInsuAddOnDTO>(); prepareInsuredPersonAddOn();
	 * historyRecordMap = new HashMap<String, MedicalPersonHistoryRecord>();
	 * historyRecord = new MedicalPersonHistoryRecord(); isEditHistoryRecord =
	 * false; unitsMap = new HashMap<Integer, Unit>(); unitsMap =
	 * Utils.getUnits(product.getMaxUnit(), true); List<MedProInsuDTO>
	 * insuredPersonDTOList = medicalProposalDTO.getMedProInsuDTOList(); for
	 * (MedProInsuDTO insuDto : insuredPersonDTOList) { //
	 * insuDto.setInsuredPersonAddOnList(new //
	 * ArrayList<MedProInsuAddOnDTO>()); List<MedProInsuAddOnDTO> removeList =
	 * new ArrayList<MedProInsuAddOnDTO>(); for (MedProInsuAddOnDTO insuAddOn :
	 * insuDto.getInsuredPersonAddOnList()) { if
	 * (!insuDto.getProduct().getAddOnList().contains(insuAddOn.getAddOn())) {
	 * removeList.add(insuAddOn); } } for (MedProInsuAddOnDTO insAddOn :
	 * removeList) { if (insuDto.getInsuredPersonAddOnList().contains(insAddOn))
	 * { insuDto.getInsuredPersonAddOnList().remove(insAddOn); } } if
	 * (medicalProposal.getOldMedicalPolicy() != null) { if
	 * (insuDto.getCustomer().getId().equals(medicalProposal.getOldMedicalPolicy
	 * ().getCustomer().getId())) { insuDto.setSameInsuredPerson(true); } }
	 * insuredPersonDTOMap.put(insuDto.getTempId(), insuDto);
	 * insuredPersonDTO.setPeriodYear(insuDto.getPeriodYear()); selectedUnit =
	 * new Unit(); selectedUnit.setValue(insuDto.getUnit());
	 * selectedUnit.setKey(insuDto.getUnit() + ""); }
	 * 
	 * for (MedProInsuDTO insuredPersonDTO :
	 * medicalProposalDTO.getMedProInsuDTOList()) { existingGuardian =
	 * insuredPersonDTO.getGuardianDTO() == null ? false : true; } }
	 * 
	 * public void changeStateCode(AjaxBehaviorEvent e) { StateCode stateCode =
	 * (StateCode) ((UIOutput) e.getSource()).getValue();
	 * loadTownshipCodeByStateCode(stateCode); haveTownship = true; }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalPolicy");
	 * removeParam("editMedicalRenewalProposal");
	 * removeParam("editEnquiryMedicalRenewalProposal"); }
	 * 
	 *//************************
		 * for onFlow Process ( next button at p:tab )
		 *************************/
	/*
	 * public String onFlowProcess(FlowEvent event) { boolean valid = true; if
	 * ("customerTab".equals(event.getOldStep()) &&
	 * "InsuredPersonInfo".equals(event.getNewStep())) { ValidationResult result
	 * = customerValidator.validate(medicalProposalDTO.getCustomer()); if
	 * (!result.isVerified()) { for (ErrorMessage message :
	 * result.getErrorMeesages()) { addErrorMessage(message); valid = false; } }
	 * else {
	 * loadTownshipCodeByStateCode(insuredPersonDTO.getCustomer().getStateCode()
	 * ); } } if ("InsuredPersonInfo".equals(event.getOldStep()) &&
	 * "workFlow".equals(event.getNewStep())) { if (insuredPersonDTOMap.size() <
	 * 1) { if (CustomerType.INDIVIDUALCUSTOMER.equals(medicalProposalDTO.
	 * getCustomerType())) { addErrorMessage(formID + ":insuredPersonInfoTable",
	 * MessageId.REQUIRE_INSUREDPERSON); } else { addErrorMessage(formID +
	 * ":insuredPersonInfoTable", MessageId.REQUIRE_TWO_INSUREDPERSON); } valid
	 * = false; } } if ("InsuredPersonInfo".equals(event.getOldStep()) &&
	 * "customerTab".equals(event.getNewStep())) {
	 * loadTownshipCodeByStateCode(medicalProposalDTO.getCustomer().getStateCode
	 * ()); } return valid ? event.getNewStep() : event.getOldStep(); }
	 * 
	 * public List<Product> getSelectedProduct() { return selectedProduct; }
	 * 
	 * public void setSelectedProduct(List<Product> selectedProduct) {
	 * this.selectedProduct = selectedProduct; }
	 * 
	 * public List<CoinsuranceCompany> getCompanyList() { return companyList; }
	 * 
	 * public void setCompanyList(List<CoinsuranceCompany> companyList) {
	 * this.companyList = companyList; }
	 * 
	 * public boolean isExistingGuardian() { return existingGuardian; }
	 * 
	 * public void setExistingGuardian(boolean existingGuardian) {
	 * this.existingGuardian = existingGuardian; }
	 * 
	 * public List<StateCode> getStateCodeList() { return stateCodeList; }
	 * 
	 * public void setStateCodeList(List<StateCode> stateCodeList) {
	 * this.stateCodeList = stateCodeList; }
	 * 
	 * public List<TownshipCode> getTownshipCodeList() { return
	 * townshipCodeList; }
	 * 
	 * public void setTownshipCodeList(List<TownshipCode> townshipCodeList) {
	 * this.townshipCodeList = townshipCodeList; }
	 * 
	 * public boolean isHaveTownship() { return haveTownship; }
	 * 
	 * public void setHaveTownship(boolean haveTownship) { this.haveTownship =
	 * haveTownship; }
	 * 
	 * public IdType[] getIdTypes() { return IdType.values(); }
	 * 
	 * public Gender[] getGender() { return Gender.values(); }
	 * 
	 * public IdConditionType[] getIdConditionType() { return
	 * IdConditionType.values(); }
	 * 
	 * public MaritalStatus[] getMaritalStatus() { return
	 * MaritalStatus.values(); }
	 * 
	 * public MedProDTO getMedicalProposalDTO() { return medicalProposalDTO; }
	 * 
	 * public void setMedicalProposalDTO(MedProDTO medicalProposalDTO) {
	 * this.medicalProposalDTO = medicalProposalDTO; }
	 * 
	 * public MedProInsuBeneDTO getBeneficiariesInfoDTO() { return
	 * beneficiariesInfoDTO; }
	 * 
	 * public void setBeneficiariesInfoDTO(MedProInsuBeneDTO
	 * beneficiariesInfoDTO) { this.beneficiariesInfoDTO = beneficiariesInfoDTO;
	 * }
	 * 
	 * public boolean isCreateNewBeneficiariesInfo() { return
	 * createNewBeneficiariesInfo; }
	 * 
	 * public void setCreateNewBeneficiariesInfo(boolean
	 * createNewBeneficiariesInfo) { this.createNewBeneficiariesInfo =
	 * createNewBeneficiariesInfo; }
	 * 
	 * public boolean isCreateNewIsuredPersonInfo() { return
	 * createNewIsuredPersonInfo; }
	 * 
	 * public void setCreateNewIsuredPersonInfo(boolean
	 * createNewIsuredPersonInfo) { this.createNewIsuredPersonInfo =
	 * createNewIsuredPersonInfo; }
	 * 
	 * public Product getProduct() { return product; }
	 * 
	 * public void setProduct(Product product) { this.product = product; }
	 * 
	 * public boolean isCreateNewAddOn() { return createNewAddOn; }
	 * 
	 * public void setCreateNewAddOn(boolean createNewAddOn) {
	 * this.createNewAddOn = createNewAddOn; }
	 * 
	 * public List<RelationShip> getRelationshipList() { return
	 * relationshipList; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public void setmedicalProposalDTO(MedProDTO medicalProposalDTO) {
	 * this.medicalProposalDTO = medicalProposalDTO; }
	 * 
	 * public MedicalPersonHistoryRecord getHistoryRecord() { return
	 * historyRecord; }
	 * 
	 * public void setHistoryRecord(MedicalPersonHistoryRecord historyRecord) {
	 * this.historyRecord = historyRecord; }
	 * 
	 * public String getUserType() { if (userType == null) { userType =
	 * UserType.AGENT.toString(); } return userType; }
	 * 
	 * public void setUserType(String userType) { this.userType = userType; }
	 * 
	 * public MedProDTO getmedicalProposalDTO() { return medicalProposalDTO; }
	 * 
	 * public MedProInsuAddOnDTO getInsuredPersonAddOnDTO() { return
	 * insuredPersonAddOnDTO; }
	 * 
	 * public void setInsuredPersonAddOnDTO(MedProInsuAddOnDTO
	 * insuredPersonAddOnDTO) { this.insuredPersonAddOnDTO =
	 * insuredPersonAddOnDTO; }
	 * 
	 * public void createNewmedicalProposalDTO() { medicalProposalDTO = new
	 * MedProDTO(); resetCustomer(); resetInsuredPerson();
	 * medicalProposalDTO.setSubmittedDate(new Date());
	 * medicalProposalDTO.setCustomerType(CustomerType.INDIVIDUALCUSTOMER); }
	 * 
	 * private boolean isNrcCustomer = true; private boolean
	 * isStillApplyCustomer = true;
	 * 
	 * public void changeIdType(AjaxBehaviorEvent e) { IdType idType = (IdType)
	 * ((UIOutput) e.getSource()).getValue(); if (idType.equals(IdType.NRCNO)) {
	 * isNrcCustomer = true; isStillApplyCustomer = true; } else if
	 * (idType.equals(IdType.STILL_APPLYING)) { haveTownship = false;
	 * isNrcCustomer = false; isStillApplyCustomer = false; } else {
	 * haveTownship = false; isNrcCustomer = false; isStillApplyCustomer = true;
	 * } }
	 * 
	 * public boolean isNrcCustomer() { return isNrcCustomer; }
	 * 
	 * public void setNrcCustomer(boolean isNrcCustomer) { this.isNrcCustomer =
	 * isNrcCustomer; }
	 * 
	 * public boolean isStillApplyCustomer() { return isStillApplyCustomer; }
	 * 
	 * public void setStillApplyCustomer(boolean isStillApplyCustomer) {
	 * this.isStillApplyCustomer = isStillApplyCustomer; }
	 * 
	 *//********************************* customer *********************/
	/*
	 * private boolean isNrcInsuredPerson = true; private boolean
	 * isStillApplyInsuredPerson = true;
	 * 
	 * public void changeIdTypeInsuredPerson(AjaxBehaviorEvent e) { IdType
	 * idType = (IdType) ((UIOutput) e.getSource()).getValue(); if
	 * (idType.equals(IdType.NRCNO)) { isNrcInsuredPerson = true;
	 * isStillApplyInsuredPerson = true; } else if
	 * (idType.equals(IdType.STILL_APPLYING)) { haveTownship = false;
	 * isNrcInsuredPerson = false; isStillApplyInsuredPerson = false; } else {
	 * haveTownship = false; isNrcInsuredPerson = false;
	 * isStillApplyInsuredPerson = true; } }
	 * 
	 * public boolean isNrcInsuredPerson() { return isNrcInsuredPerson; }
	 * 
	 * public void setNrcInsuredPerson(boolean isNrcInsuredPerson) {
	 * this.isNrcInsuredPerson = isNrcInsuredPerson; }
	 * 
	 * public boolean isStillApplyInsuredPerson() { return
	 * isStillApplyInsuredPerson; }
	 * 
	 * public void setStillApplyInsuredPerson(boolean isStillApplyInsuredPerson)
	 * { this.isStillApplyInsuredPerson = isStillApplyInsuredPerson; }
	 * 
	 *//**************** insured person ********************/
	/*
	 * private boolean isNrcBene = true; private boolean isStillApplyBene =
	 * true;
	 * 
	 * public void checkIdType(IdType idType, String customerType) { switch
	 * (idType) { case NRCNO: if ("C".equals(customerType)) { isNrcCustomer =
	 * true; isStillApplyCustomer = true; } else if ("I".equals(customerType)) {
	 * isNrcInsuredPerson = true; isStillApplyInsuredPerson = true; } else if
	 * ("B".equals(customerType)) { isNrcBene = true; isStillApplyBene = true; }
	 * break; case STILL_APPLYING: if ("C".equals(customerType)) { haveTownship
	 * = false; isNrcCustomer = false; isStillApplyCustomer = false;
	 * medicalProposalDTO.getCustomer().setFullIdNo(null);
	 * medicalProposalDTO.getCustomer().setStateCode(null);
	 * medicalProposalDTO.getCustomer().setTownshipCode(null);
	 * medicalProposalDTO.getCustomer().setIdConditionType(null);
	 * medicalProposalDTO.getCustomer().setIdNo(""); } else if
	 * ("I".equals(customerType)) { haveTownship = false; isNrcInsuredPerson =
	 * false; isStillApplyInsuredPerson = false;
	 * insuredPersonDTO.getCustomer().setFullIdNo(null);
	 * insuredPersonDTO.getCustomer().setStateCode(null);
	 * insuredPersonDTO.getCustomer().setTownshipCode(null);
	 * insuredPersonDTO.getCustomer().setIdConditionType(null);
	 * insuredPersonDTO.getCustomer().setIdNo(""); } else if
	 * ("B".equals(customerType)) { haveTownship = false; isNrcBene = false;
	 * isStillApplyBene = false; beneficiariesInfoDTO.setFullIdNo(null);
	 * beneficiariesInfoDTO.setStateCode(null);
	 * beneficiariesInfoDTO.setTownshipCode(null);
	 * beneficiariesInfoDTO.setIdConditionType(null);
	 * beneficiariesInfoDTO.setIdNo(""); } break; default: haveTownship = false;
	 * if ("C".equals(customerType)) { isNrcCustomer = false;
	 * isStillApplyCustomer = true; } else if ("I".equals(customerType)) {
	 * isNrcInsuredPerson = false; isStillApplyInsuredPerson = true; } else if
	 * ("B".equals(customerType)) { isNrcBene = false; isStillApplyBene = true;
	 * } break; } }
	 * 
	 * public void changeIdTypeBene(AjaxBehaviorEvent e) { IdType idType =
	 * (IdType) ((UIOutput) e.getSource()).getValue(); checkIdType(idType, "B");
	 * }
	 * 
	 * public boolean isNrcBene() { return isNrcBene; }
	 * 
	 * public void setNrcBene(boolean isNrcBene) { this.isNrcBene = isNrcBene; }
	 * 
	 * public boolean isStillApplyBene() { return isStillApplyBene; }
	 * 
	 * public void setStillApplyBene(boolean isStillApplyBene) {
	 * this.isStillApplyBene = isStillApplyBene; }
	 * 
	 * public String submitRenewalProposal() { String result = null; try {
	 * WorkflowTask workflowTask = isConfirmEdit ? surveyAgain ?
	 * WorkflowTask.RENEWAL_SURVEY : WorkflowTask.RENEWAL_APPROVAL :
	 * WorkflowTask.RENEWAL_SURVEY;
	 * medicalProposalDTO.setProposalType(ProposalType.RENEWAL); WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO(medicalProposalDTO.getId(), remark,
	 * workflowTask, ReferenceType.MEDICAL_RENEWAL_PROPOSAL, user,
	 * responsiblePerson);
	 * medicalProposalDTO.setMedProInsuDTOList(getInsuredPersonDtoList());
	 * ExternalContext extContext = getFacesContext().getExternalContext(); if
	 * (isConfirmEdit) { medicalProposal =
	 * proposalFrontService.updateMedicalProposal(medicalProposalDTO,
	 * workFlowDTO); extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.EDIT_POLICY_RENEWAL_PROCESS_SUCCESS); } else if (isEnquiryEdit)
	 * { medicalProposal =
	 * proposalFrontService.updateMedicalProposal(medicalProposalDTO, null);
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.EDIT_POLICY_RENEWAL_PROCESS_SUCCESS); } else { medicalProposal
	 * = proposalFrontService.addNewMedicalProposal(medicalProposalDTO,
	 * workFlowDTO); extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.RENEWAL_PROCESS_SUCCESS); }
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalProposal.getProposalNo()); result = "dashboard"; } catch
	 * (SystemException ex) { handelSysException(ex); } return result; }
	 * 
	 *//******************** Start Customer Entry **************************/
	/*
	 * 
	 * public boolean isExistingCustomer() { return existingCustomer; }
	 * 
	 * public void resetCustomer() { CustomerDTO customerDTO = new
	 * CustomerDTO(); customerDTO.setGender(Gender.MALE);
	 * customerDTO.setIdType(IdType.NRCNO);
	 * customerDTO.setMaritalStatus(MaritalStatus.SINGLE);
	 * medicalProposalDTO.setCustomer(customerDTO); existingCustomer = false; }
	 * 
	 * public void resetInsuredPerson() { String tempId =
	 * insuredPersonDTO.getTempId(); insuredPersonDTO = new MedProInsuDTO();
	 * insuredPersonDTO.setRelationship(null); insuredPersonFullName = ""; if
	 * (createEdit) { insuredPersonDTO.setTempId(tempId); } }
	 * 
	 *//********************
		 * Start InsuredPerson Entry
		 **************************/
	/*
	 * 
	 * public void changeCusToInsuredPerson() { CustomerDTO customer =
	 * medicalProposalDTO.getCustomer();
	 * insuredPersonDTO.setRelationship(getSelfRelationship());
	 * insuredPersonDTO.setSameInsuredPerson(true);
	 * insuredPersonDTO.setCustomer(customer); if (customer.getId() != null) {
	 * insuredPersonFullName = customer.getFullName(); }
	 * loadTownshipCodeByStateCode(insuredPersonDTO.getCustomer().getStateCode()
	 * ); createInsuredPersonAddOn();
	 * checkIdType(insuredPersonDTO.getCustomer().getIdType(), "I");
	 * 
	 * }
	 * 
	 * public void handleInsuredPersonRelationship() { if
	 * (insuredPersonDTO.getRelationship() != null) { boolean isContainSelfRS =
	 * false; if
	 * (getSelfRelationship().equals(insuredPersonDTO.getRelationship())) { if
	 * (insuredPersonDTOMap.size() != 0) { for (MedProInsuDTO dto :
	 * getInsuredPersonDtoList()) { if
	 * (getSelfRelationship().equals(dto.getRelationship())) { isContainSelfRS =
	 * true; } } if (isContainSelfRS) { insuredPersonDTO.setRelationship(null);
	 * addErrorMessage(formID + ":beneficiaryRelationShip",
	 * MessageId.ALREADY_ADD_SELF_RELATION); } else {
	 * changeCusToInsuredPerson(); } } else { changeCusToInsuredPerson(); }
	 * RequestContext.getCurrentInstance().update(formID +
	 * ":insuredPersonInfoWizardPanel"); } } }
	 * 
	 * public void changeCustomerGuardian() { if (existingGuardian) {
	 * CustomerDTO customer = medicalProposalDTO.getCustomer();
	 * MedProGuardianDTO guardianDTO = new MedProGuardianDTO(null, customer,
	 * insuredPersonDTO.getRelationship());
	 * insuredPersonDTO.setGuardianDTO(guardianDTO);
	 * insuredPersonDTO.getGuardianDTO().getCustomer().setExistsEntity(true); }
	 * else { insuredPersonDTO.setGuardianDTO(null); }
	 * 
	 * }
	 * 
	 * public void changeSelectedCusToInsuredPerson(CustomerDTO customer) {
	 * insuredPersonDTO.setCustomer(customer);
	 * insuredPersonDTO.getCustomer().setExistsEntity(true); if
	 * (getSelfRelationship().equals(insuredPersonDTO.getRelationship()))
	 * insuredPersonDTO.setRejectReason(null);
	 * checkIdType(insuredPersonDTO.getCustomer().getIdType(), "I");
	 * createInsuredPersonAddOn(); }
	 * 
	 *//********************
		 * Start InsuredPerson AddON Entry
		 **************************/
	/*
	 * public void prepareInsuredPersonAddOn() { createInsuredPersonAddOn(); }
	 * 
	 * public List<MedProInsuAddOnDTO> getInsuredPersonAddOnList() { return new
	 * ArrayList<MedProInsuAddOnDTO>(insuredPersonAddOnDTOMap.values()); }
	 * 
	 * private void createInsuredPersonAddOn() { insuredPersonAddOnDTO = new
	 * MedProInsuAddOnDTO(); }
	 * 
	 * public void saveInsuredPersonAddOn() { if
	 * (validInsuredPersonAddOn(insuredPersonAddOnDTO)) {
	 * insuredPersonAddOnDTOMap.put(insuredPersonAddOnDTO.getAddOn().getId(),
	 * insuredPersonAddOnDTO); createInsuredPersonAddOn();
	 * RequestContext.getCurrentInstance().execute("addOnEntryDialog.hide()"); }
	 * }
	 * 
	 * public void removeAddOn(MedProInsuAddOnDTO insuredPersonAddOnDTO) {
	 * removeInsuredPersonAddOn(insuredPersonAddOnDTO);
	 * createInsuredPersonAddOn(); }
	 * 
	 * public void removeInsuredPersonAddOn(MedProInsuAddOnDTO insuPersonAddOn)
	 * { insuredPersonAddOnDTOMap.remove(insuPersonAddOn.getAddOn().getId());
	 * createInsuredPersonAddOn(); }
	 * 
	 * private boolean validInsuredPersonAddOn(MedProInsuAddOnDTO
	 * insuredPersonAddOn) { boolean valid = true; if
	 * (insuredPersonAddOn.getAddOn() == null) {
	 * addErrorMessage("addOnEntryForm" + ":addOn",
	 * UIInput.REQUIRED_MESSAGE_ID); valid = false; } return valid; }
	 * 
	 *//********************
		 * Start InsuredPerson Beneficiaries Entry
		 **************************/
	/*
	 * 
	 * public void prepareAddNewBeneficiariesInfo() {
	 * createNewBeneficiariesInfo(); createEdit = false; saveBene = false; }
	 * 
	 * private void createNewBeneficiariesInfo() { createNewBeneficiariesInfo =
	 * true; beneficiariesInfoDTO = new MedProInsuBeneDTO();
	 * beneficiariesInfoDTO.setPercentage(100.0f); }
	 * 
	 * public boolean isSaveBene() { return saveBene; }
	 * 
	 * private boolean saveBene;
	 * 
	 * public void saveBeneficiariesInfo() { ValidationResult result =
	 * validBeneficiary(beneficiariesInfoDTO); if (result.isVerified()) {
	 * beneficiariesInfoDTOMap.put(beneficiariesInfoDTO.getTempId(),
	 * beneficiariesInfoDTO); createNewBeneficiariesInfo();
	 * hideBeneficiaryDialog(); saveBene = true; } else { for (ErrorMessage
	 * message : result.getErrorMeesages()) { addErrorMessage(message); } } }
	 * 
	 * public void hideBeneficiaryDialog() {
	 * loadTownshipCodeByStateCode(insuredPersonDTO.getCustomer().getStateCode()
	 * ); RequestContext.getCurrentInstance().execute(
	 * "beneficiariesInfoEntryDialog.hide()"); }
	 * 
	 * public List<MedProInsuBeneDTO>
	 * getProposalInsuredPersonBeneficiariesList() { return new
	 * ArrayList<MedProInsuBeneDTO>(sortByValue(beneficiariesInfoDTOMap).values(
	 * )); }
	 * 
	 * public Map<String, MedProInsuBeneDTO> sortByValue(Map<String,
	 * MedProInsuBeneDTO> map) { List<Map.Entry<String, MedProInsuBeneDTO>> list
	 * = new LinkedList<Map.Entry<String, MedProInsuBeneDTO>>(map.entrySet());
	 * Collections.sort(list, new Comparator<Map.Entry<String,
	 * MedProInsuBeneDTO>>() { public int compare(Map.Entry<String,
	 * MedProInsuBeneDTO> o1, Map.Entry<String, MedProInsuBeneDTO> o2) { try {
	 * Long l1 = Long.parseLong(o1.getKey()); Long l2 =
	 * Long.parseLong(o2.getKey()); if (l1 > l2) { return 1; } else if (l1 < l2)
	 * { return -1; } else { return 0; } } catch (NumberFormatException e) {
	 * return 0; } } });
	 * 
	 * Map<String, MedProInsuBeneDTO> result = new LinkedHashMap<String,
	 * MedProInsuBeneDTO>(); for (Map.Entry<String, MedProInsuBeneDTO> entry :
	 * list) { result.put(entry.getKey(), entry.getValue()); } return result; }
	 * 
	 * public void prepareEditBeneficiariesInfo(MedProInsuBeneDTO
	 * beneficiariesInfo) { this.beneficiariesInfoDTO = beneficiariesInfo;
	 * this.createNewBeneficiariesInfo = false; saveBene = true; createEdit =
	 * true; loadTownshipCodeByStateCode(beneficiariesInfo.getStateCode());
	 * checkIdType(beneficiariesInfo.getIdType(), "B"); }
	 * 
	 * public void removeBeneficiariesInfo(MedProInsuBeneDTO beneficiariesInfo)
	 * { beneficiariesInfoDTOMap.remove(beneficiariesInfo.getTempId());
	 * createNewBeneficiariesInfo(); }
	 * 
	 * private ValidationResult validBeneficiary(MedProInsuBeneDTO
	 * beneficiariesInfo) { ValidationResult result =
	 * beneficiariesValidator.validate(beneficiariesInfo); if
	 * (beneficiariesInfoDTOMap.size() > 0) { int percentage = 0; for
	 * (MedProInsuBeneDTO beneficiary : beneficiariesInfoDTOMap.values()) {
	 * percentage += beneficiary.getPercentage(); } if
	 * (!beneficiariesInfoDTOMap.containsKey(beneficiariesInfo.getTempId())) {
	 * percentage += beneficiariesInfo.getPercentage(); } if (percentage > 100)
	 * { result.addErrorMessage("beneficiaryInfoEntryForm" + ":percentage",
	 * MessageId.OVER_BENEFICIARY_PERCENTAGE); } } return result; }
	 * 
	 *//*************************
		 * for p:ajax event
		 *****************************/
	/*
	 * 
	 * public void changeSaleEvent(AjaxBehaviorEvent event) { if
	 * (userType.equals(UserType.AGENT.toString())) {
	 * medicalProposalDTO.setSaleMan(null);
	 * medicalProposalDTO.setReferral(null); } else if
	 * (userType.equals(UserType.SALEMAN.toString())) {
	 * medicalProposalDTO.setAgent(null); medicalProposalDTO.setReferral(null);
	 * } else if (userType.equals(UserType.REFERRAL.toString())) {
	 * medicalProposalDTO.setSaleMan(null); medicalProposalDTO.setAgent(null); }
	 * }
	 * 
	 *//**********************
		 * for pop up data to show in their respective fields
		 **********************/
	/*
	 * 
	 * public void returnAddOn(SelectEvent event) { AddOn addOn = (AddOn)
	 * event.getObject(); insuredPersonAddOnDTO.setAddOn(addOn); }
	 * 
	 * public void returnCustomer(SelectEvent event) { Customer customer =
	 * (Customer) event.getObject();
	 * medicalProposalDTO.getCustomer().setExistsEntity(true);
	 * medicalProposalDTO.setCustomer(CustomerFactory.getCustomerDTO(customer));
	 * loadTownshipCodeByStateCode(medicalProposalDTO.getCustomer().getStateCode
	 * ()); changeCustomerGuardian();
	 * checkIdType(medicalProposalDTO.getCustomer().getIdType(), "C");
	 * checkInsuredPerson(); }
	 * 
	 * public void checkInsuredPerson() { for (MedProInsuDTO insuDTO :
	 * getInsuredPersonDtoList()) { if
	 * (medicalProposalDTO.getCustomer().getId().equals(insuDTO.getCustomer().
	 * getId())) { insuDTO.setRelationship(getSelfRelationship()); } else if
	 * (getSelfRelationship().equals(insuDTO.getRelationship())) {
	 * insuDTO.setRelationship(null); } } createNewMedicalInsuredPerson();
	 * createEdit = false; }
	 * 
	 * public void returnInsuredPersonCustomer(SelectEvent event) { Customer
	 * customer = (Customer) event.getObject(); boolean isAlreadyHave = false;
	 * for (MedProInsuDTO insuDTO : getInsuredPersonDtoList()) { if
	 * (customer.getId().equals(insuDTO.getCustomer().getId())) {
	 * addErrorMessage(formID + ":customerRegInsu",
	 * MessageId.ALREADY_ADD_INSUREDPERSON); isAlreadyHave = true; } } if
	 * (!isAlreadyHave) { if
	 * (customer.getId().equals(medicalProposalDTO.getCustomer().getId())) {
	 * changeCusToInsuredPerson(); } else
	 * changeSelectedCusToInsuredPerson(CustomerFactory.getCustomerDTO(customer)
	 * );
	 * loadTownshipCodeByStateCode(insuredPersonDTO.getCustomer().getStateCode()
	 * ); insuredPersonFullName = insuredPersonDTO.getCustomer().getFullName();
	 * } }
	 * 
	 * public void returnPaymentType(SelectEvent event) { PaymentType
	 * paymentType = (PaymentType) event.getObject();
	 * medicalProposalDTO.setPaymentType(paymentType); }
	 * 
	 * public void returnSaleMan(SelectEvent event) { SaleMan saleMan =
	 * (SaleMan) event.getObject(); medicalProposalDTO.setSaleMan(saleMan); }
	 * 
	 * public void returnAgent(SelectEvent event) { Agent agent = (Agent)
	 * event.getObject(); medicalProposalDTO.setAgent(agent); }
	 * 
	 * public void returnReferral(SelectEvent event) { CustomerDTO referral =
	 * CustomerFactory.getCustomerDTO((Customer) event.getObject());
	 * medicalProposalDTO.setReferral(referral); }
	 * 
	 * public void returnBranch(SelectEvent event) { Branch branch = (Branch)
	 * event.getObject(); medicalProposalDTO.setBranch(branch); if
	 * (!existingCustomer) { medicalProposalDTO.getCustomer().setBranch(branch);
	 * } }
	 * 
	 * public void returnInsuredPersonTownShip(SelectEvent event) { Township
	 * townShip = (Township) event.getObject();
	 * insuredPersonDTO.getCustomer().getResidentAddress().setTownship(townShip)
	 * ; }
	 * 
	 * public void returnBeneficiariesTownShip(SelectEvent event) { Township
	 * townShip = (Township) event.getObject();
	 * beneficiariesInfoDTO.getResidentAddress().setTownship(townShip); }
	 * 
	 * public void returnOccupation(SelectEvent event) { Occupation occupation =
	 * (Occupation) event.getObject();
	 * insuredPersonDTO.getCustomer().setOccupation(occupation); }
	 * 
	 * public void returnOccupationForCustomer(SelectEvent event) { Occupation
	 * occupation = (Occupation) event.getObject();
	 * medicalProposalDTO.getCustomer().setOccupation(occupation); }
	 * 
	 * public void returnNationalityForCustomer(SelectEvent event) { Country
	 * nationality = (Country) event.getObject();
	 * medicalProposalDTO.getCustomer().setCountry(nationality); }
	 * 
	 * public void returnNationalityForInsuredPerson(SelectEvent event) {
	 * Country nationality = (Country) event.getObject();
	 * insuredPersonDTO.getCustomer().setCountry(nationality); }
	 * 
	 * public void returnEmployeeOccupation(SelectEvent event) { Occupation
	 * occupation = (Occupation) event.getObject();
	 * medicalProposalDTO.getCustomer().setOccupation(occupation); }
	 * 
	 * public void returnQualification(SelectEvent event) { Qualification
	 * qualification = (Qualification) event.getObject();
	 * medicalProposalDTO.getCustomer().setQualification(qualification); }
	 * 
	 * public void returnResidentTownship(SelectEvent event) { Township township
	 * = (Township) event.getObject();
	 * medicalProposalDTO.getCustomer().getResidentAddress().setTownship(
	 * township);
	 * 
	 * }
	 * 
	 * public void returnPermanentTownship(SelectEvent event) { Township
	 * township = (Township) event.getObject();
	 * medicalProposalDTO.getCustomer().getPermanentAddress().setTownship(
	 * township); }
	 * 
	 * public void returnOfficeTownship(SelectEvent event) { Township township =
	 * (Township) event.getObject();
	 * medicalProposalDTO.getCustomer().getOfficeAddress().setTownship(township)
	 * ; }
	 * 
	 * public MaritalStatus[] getMaritalStatusList() { return
	 * MaritalStatus.values(); }
	 * 
	 * public List<Religion> getReligionList() { return religionList; }
	 * 
	 * public List<BankBranch> getBankBranchList() { return bankBranchList; }
	 * 
	 * public Map<Integer, Unit> getUnitsMap() { return unitsMap; }
	 * 
	 * public void setUnitsMap(Map<Integer, Unit> unitsMap) { this.unitsMap =
	 * unitsMap; }
	 * 
	 * public Unit getSelectedUnit() { return selectedUnit; }
	 * 
	 * public void setSelectedUnit(Unit selectedUnit) { this.selectedUnit =
	 * selectedUnit; }
	 * 
	 * public boolean isCreateEdit() { return createEdit; }
	 * 
	 * public void setCreateEdit(boolean createEdit) { this.createEdit =
	 * createEdit; }
	 * 
	 * public int getPeriodYear() { return periodYear; }
	 * 
	 * public void setPeriodYear(int periodYear) { this.periodYear = periodYear;
	 * }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public void selectSurveyUser() { selectUser(WorkflowTask.SURVEY,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void selectApprovalUser() {
	 * selectUser(WorkflowTask.MEDICAL_APPROVAL,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void resetResponsiblePerson() { responsiblePerson = null;
	 * FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().
	 * add("medicalProposalEntryForm:responsiblePersonPanel"); }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public List<MedProInsuAddOnDTO> getAddOnList() { return addOnList; }
	 * 
	 * public void setAddOnList(List<MedProInsuAddOnDTO> addOnList) {
	 * this.addOnList = addOnList; }
	 * 
	 * public MedProInsuDTO getDto() { return insuredPersonDTO; }
	 * 
	 * public void setInsuredPersonDTO(MedProInsuDTO insuredPersonDTO) {
	 * this.insuredPersonDTO = insuredPersonDTO; addOnfig(); }
	 * 
	 * public CustomerType[] getCustomerTypes() { return CustomerType.values();
	 * }
	 * 
	 * public void changeCustomerType(AjaxBehaviorEvent event) {
	 * medicalProposalDTO.setCorporate(medicalProposalDTO.getCustomerType().
	 * equals(CustomerType.CORPORATECUSTOMER)); }
	 * 
	 * private void setKeyFactorValue(PaymentType paymentType, int age, Gender
	 * gender) { for (MedicalProposalInsuredPersonKeyFactorValue vehKF :
	 * insuredPersonDTO.getKeyFactorValueList()) { KeyFactor kf =
	 * vehKF.getKeyFactor(); if (KeyFactorChecker.isGender(kf)) {
	 * vehKF.setValue(gender + ""); } else if
	 * (KeyFactorChecker.isMedicalAge(kf)) { vehKF.setValue(age + ""); } else {
	 * vehKF.setValue(paymentType.getId() + ""); } } }
	 * 
	 * // This method is only for old policy (before April 2016) // old policy
	 * has only one keyfactor public void checkProduct() { for
	 * (MedicalProposalInsuredPerson insuredPerson :
	 * medicalProposal.getMedicalProposalInsuredPersonList()) { if
	 * (insuredPerson.getKeyFactorValueList().size() < 3) {
	 * insuredPerson.loadKeyFactor(product); } } }
	 * 
	 * private void createNewMedicalInsuredPerson() { createNewIsuredPersonInfo
	 * = true; insuredPersonDTO = new MedProInsuDTO();
	 * insuredPersonDTO.setUnit(1); insuredPersonDTO.setStartDate(new Date());
	 * isNrcInsuredPerson = true; isStillApplyInsuredPerson = true; createEdit =
	 * false; insuredPersonFullName = ""; createNewBeneficiariesInfoDTOMap(); }
	 * 
	 * public void createNewBeneficiariesInfoDTOMap() { beneficiariesInfoDTOMap
	 * = new HashMap<String, MedProInsuBeneDTO>(); }
	 * 
	 * private RelationShip getSelfRelationship() { RelationShip result = null;
	 * for (RelationShip rs : relationshipList) { if
	 * ("SELF".equalsIgnoreCase(rs.getName())) { result = rs; } } return result;
	 * }
	 * 
	 * public void saveMedicalInsuredPerson() {
	 * insuredPersonDTO.setProduct(product);
	 * insuredPersonDTO.setInsuredPersonBeneficiariesList(
	 * getProposalInsuredPersonBeneficiariesList()); ValidationResult result =
	 * insuredpersonValidator.validate(insuredPersonDTO); if
	 * (result.isVerified() && checkBenePercentage() &&
	 * checkInsuredPersonByCustomerType()) { Calendar cal =
	 * Calendar.getInstance(); if (insuredPersonDTO.getStartDate() == null) {
	 * insuredPersonDTO.setStartDate(new Date()); }
	 * cal.setTime(insuredPersonDTO.getStartDate()); cal.add(Calendar.YEAR,
	 * insuredPersonDTO.getPeriodYear());
	 * setKeyFactorValue(medicalProposalDTO.getPaymentType(),
	 * Utils.getAgeForNextYear(insuredPersonDTO.getCustomer().getDateOfBirth()),
	 * insuredPersonDTO.getCustomer().getGender());
	 * insuredPersonDTO.setAge(Utils.getAgeForNextYear(insuredPersonDTO.
	 * getCustomer().getDateOfBirth()));
	 * insuredPersonDTO.setEndDate(cal.getTime());
	 * insuredPersonDTOMap.put(insuredPersonDTO.getTempId(), insuredPersonDTO);
	 * createNewMedicalInsuredPerson(); createNewBeneficiariesInfoDTOMap(); }
	 * else if (!result.isVerified()) { for (ErrorMessage message :
	 * result.getErrorMeesages()) { addErrorMessage(message); } } }
	 * 
	 * private boolean checkInsuredPersonByCustomerType() { int
	 * noOfInsuredPerson = insuredPersonDTOMap.size(); boolean valid = true;
	 * boolean isAlreadySelfRs = false; if (noOfInsuredPerson != 0) { for
	 * (MedProInsuDTO insuDTO : getInsuredPersonDtoList()) { if
	 * (getSelfRelationship().equals(insuDTO.getRelationship())) isAlreadySelfRs
	 * = true; } if (CustomerType.INDIVIDUALCUSTOMER.equals(medicalProposalDTO.
	 * getCustomerType()) && !createEdit) { if (noOfInsuredPerson == 1) {
	 * addErrorMessage(formID + ":insuredPersonInfoTable",
	 * MessageId.INVALID_INDIVIDUAL_CUSTOMER); valid = false; } } if
	 * (isAlreadySelfRs && !createEdit &&
	 * getSelfRelationship().equals(insuredPersonDTO.getRelationship()) &&
	 * !createEdit) { addErrorMessage(formID + ":beneficiaryRelationShip",
	 * MessageId.ALREADY_ADD_SELF_RELATION); valid = false; } } return valid; }
	 * 
	 * private boolean checkBenePercentage() { if (beneficiariesInfoDTOMap !=
	 * null && beneficiariesInfoDTOMap.size() != 0) { float totalPercent = 0.0f;
	 * for (MedProInsuBeneDTO bfDTO : beneficiariesInfoDTOMap.values()) {
	 * totalPercent = totalPercent + bfDTO.getPercentage(); } if (totalPercent >
	 * 100) { return false; } else if (totalPercent < 100) { return false; }
	 * else { return true; } } else {
	 * addErrorMessage("medicalProposalEntryForm:beneficiariesInfoTablePanel",
	 * MessageId.REQUIRED_BENEFICIARY_PERSON); } return false; }
	 * 
	 * public List<MedProInsuDTO> getInsuredPersonDtoList() { return new
	 * ArrayList<MedProInsuDTO>(insuredPersonDTOMap.values()); }
	 * 
	 * public List<MedProInsuAddOnDTO> getSelectedAddOnLists() { return
	 * selectedAddOnLists; }
	 * 
	 * public void setSelectedAddOnLists(List<MedProInsuAddOnDTO>
	 * selectedAddOnLists) { this.selectedAddOnLists = selectedAddOnLists; }
	 * 
	 * public int getUnit() { return unit; }
	 * 
	 * public void setUnit(int unit) { this.unit = unit; }
	 * 
	 * public void onRowSelect(SelectEvent event) { insuredPersonAddOnDTO =
	 * (MedProInsuAddOnDTO) event.getObject(); RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnUnitDialog').show();");
	 * 
	 * }
	 * 
	 * public void onRowUnSelect(MedProInsuAddOnDTO addOnDTO) {
	 * insuredPersonAddOnDTO.setUnit(0); }
	 * 
	 * public void applyProduct() {
	 * insuredPersonDTO.setInsuredPersonAddOnList(selectedAddOnLists);
	 * insuredPersonDTOMap.put(insuredPersonDTO.getTempId(), insuredPersonDTO);
	 * createNewMedicalInsuredPerson(); RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnConfigDialog').hide();"); }
	 * 
	 * public void addUnit() { if (unit < 1) {
	 * addErrorMessage("addOnUnitDialogForm:value",
	 * UIInput.REQUIRED_MESSAGE_ID); } else if (unit >
	 * insuredPersonDTO.getUnit()) {
	 * addErrorMessage("addOnUnitDialogForm:value",
	 * MessageId.INVALID_ADDON_UNIT, insuredPersonDTO.getUnit()); } else {
	 * insuredPersonAddOnDTO.setUnit(unit); unit = 0; RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnUnitDialog').hide();"); } }
	 * 
	 * public void addOnfig() { selectedAddOnLists = new
	 * ArrayList<MedProInsuAddOnDTO>(); if
	 * (!insuredPersonDTO.getInsuredPersonAddOnList().isEmpty()) {
	 * selectedAddOnLists.addAll(insuredPersonDTO.getInsuredPersonAddOnDTOList()
	 * ); } addOnList = new ArrayList<MedProInsuAddOnDTO>(); MedProInsuAddOnDTO
	 * addOnDto = null; for (AddOn addOn :
	 * insuredPersonDTO.getProduct().getAddOnList()) { addOnDto = new
	 * MedProInsuAddOnDTO(addOn); if
	 * (!KeyFactorIDConfig.getMedAddOn1().equals(addOn.getId())) {
	 * addOnList.add(addOnDto); } } for (MedProInsuAddOnDTO selectedAddOn :
	 * selectedAddOnLists) { for (MedProInsuAddOnDTO addOn : addOnList) { if
	 * (addOn.getAddOn().equals(selectedAddOn.getAddOn())) {
	 * addOn.setUnit(selectedAddOn.getUnit()); } } } }
	 * 
	 * public void prepareEditInsuredPersonInfo(MedProInsuDTO insuDto) {
	 * this.insuredPersonDTO = new MedProInsuDTO(insuDto); insuredPersonFullName
	 * = insuredPersonDTO.getCustomer().getId() != null ?
	 * insuredPersonDTO.getCustomer().getFullName() : ""; this.product =
	 * insuredPersonDTO.getProduct(); if
	 * (insuredPersonDTO.getInsuredPersonBeneficiariesList() != null) {
	 * createNewBeneficiariesInfoDTOMap(); for (MedProInsuBeneDTO bdto :
	 * insuredPersonDTO.getInsuredPersonBeneficiariesList()) {
	 * beneficiariesInfoDTOMap.put(bdto.getTempId(), bdto); } }
	 * this.createNewIsuredPersonInfo = false; createEdit = true;
	 * loadTownshipCodeByStateCode(insuredPersonDTO.getCustomer().getStateCode()
	 * ); checkIdType(insuredPersonDTO.getCustomer().getIdType(), "I"); }
	 * 
	 * private void loadTownshipCodeByStateCode(StateCode stateCode) { if
	 * (stateCode != null) townshipCodeList =
	 * townshipCodeService.findByStateCode(stateCode); }
	 * 
	 * public void removeInsuredPersonInfo(MedProInsuDTO insuDto) {
	 * insuredPersonDTOMap.remove(insuDto.getTempId());
	 * createNewMedicalInsuredPerson(); }
	 * 
	 * public void prepareAddNewInsuredPersonInfo() {
	 * createNewMedicalInsuredPerson(); createNewBeneficiariesInfoDTOMap(); }
	 * 
	 * public void handleAddOnDialogClose() { createNewMedicalInsuredPerson();
	 * RequestContext context = RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnConfigDialog').hide();"); }
	 * 
	 * public void handleHistoryRecordDialog() { newHistoryRecord();
	 * createNewMedicalInsuredPerson(); RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnConfigDialog1').hide();"); }
	 * 
	 * public void handleAddOnUnitDialogClose() { unit = 0;
	 * selectedAddOnLists.remove(insuredPersonAddOnDTO); RequestContext context
	 * = RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnUnitDialog').hide();"); }
	 * 
	 * public void saveHistoryRecord() { List<PersonProductHistory>
	 * productHistoryList = new ArrayList<>(); for (Product product :
	 * selectedProduct) { PersonProductHistory productHisotry = new
	 * PersonProductHistory(); productHisotry.setProduct(product);
	 * historyRecord.addPersonProductHistory(productHisotry);
	 * productHistoryList.add(productHisotry); }
	 * historyRecordMap.put(historyRecord.getTempId(), historyRecord);
	 * selectedProduct.clear(); newHistoryRecord(); }
	 * 
	 * public void newHistoryRecord() { isEditHistoryRecord = false;
	 * historyRecord = new MedicalPersonHistoryRecord(); }
	 * 
	 * public void addHistoryRecord() { if (getHistoryRecordList() == null ||
	 * getHistoryRecordList().isEmpty()) {
	 * addErrorMessage("addOnConfigForm1:hitoryRecordTable",
	 * MessageId.REQUIRE_HISTORY_RECORD); } else {
	 * insuredPersonDTO.setHistoryRecordList(getHistoryRecordList());
	 * insuredPersonDTOMap.put(insuredPersonDTO.getTempId(), insuredPersonDTO);
	 * createNewMedicalInsuredPerson(); RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('addOnConfigDialog1').hide();"); } }
	 * 
	 * public void updateHistoryRecord() { List<PersonProductHistory>
	 * productHistoryList = new ArrayList<>(); for (Product product :
	 * selectedProduct) { PersonProductHistory productHisotry = new
	 * PersonProductHistory(); productHisotry.setProduct(product);
	 * productHistoryList.add(productHisotry); }
	 * historyRecord.setProductList(productHistoryList);
	 * historyRecordMap.put(historyRecord.getTempId(), historyRecord);
	 * selectedProduct.clear(); newHistoryRecord(); }
	 * 
	 * public void prepareEditHistoryRecord(MedicalPersonHistoryRecord
	 * historyRecord) { isEditHistoryRecord = true; this.historyRecord =
	 * historyRecord; for (PersonProductHistory productHistory :
	 * historyRecord.getProductList()) {
	 * selectedProduct.add(productHistory.getProduct()); } }
	 * 
	 * public void removeHistoryRecord(MedicalPersonHistoryRecord historyRecord)
	 * { historyRecordMap.remove(historyRecord.getTempId()); newHistoryRecord();
	 * }
	 * 
	 * public void prepareHistoryRecord(MedProInsuDTO insuredPersonDTO) {
	 * this.insuredPersonDTO = insuredPersonDTO; createEdit = false;
	 * historyRecordMap = new HashMap<String, MedicalPersonHistoryRecord>(); if
	 * (insuredPersonDTO.getHistoryRecordList() != null) { for
	 * (MedicalPersonHistoryRecord record :
	 * insuredPersonDTO.getHistoryRecordList()) {
	 * historyRecordMap.put(record.getTempId(), record); } } }
	 * 
	 * public int getMaxUnit() { return
	 * productService.findProductById(KeyFactorChecker.getMEDPRO1ID()).
	 * getMaxUnit(); }
	 * 
	 * public String getInsuOccupation() { return
	 * this.insuredPersonDTO.getCustomer().getOccupation() == null ? "" :
	 * this.insuredPersonDTO.getCustomer().getOccupation().getName(); }
	 * 
	 * public String getInsuCountry() { return
	 * this.insuredPersonDTO.getCustomer().getCountry() == null ? "" :
	 * this.insuredPersonDTO.getCustomer().getCountry().getName(); }
	 * 
	 * public String getInsuResiTownship() { return
	 * this.insuredPersonDTO.getCustomer().getResidentAddress().getTownship() ==
	 * null ? "" :
	 * this.insuredPersonDTO.getCustomer().getResidentAddress().getTownship().
	 * getName(); }
	 * 
	 * public String getPageHeader() { return (isConfirmEdit ? "Confirm Edit" :
	 * isEnquiryEdit ? "Enquiry Edit" : "Add New") + " Health Renewal Proposal";
	 * }
	 * 
	 * public boolean isConfirmEdit() { return isConfirmEdit; }
	 * 
	 * public boolean isEnquiryEdit() { return isEnquiryEdit; }
	 * 
	 * public boolean isSurveyAgain() { return surveyAgain; }
	 * 
	 * public void setSurveyAgain(boolean surveyAgain) { this.surveyAgain =
	 * surveyAgain; }
	 * 
	 * public List<Product> getProductList() { return productList; }
	 * 
	 * public String getInsuredPersonFullName() { return insuredPersonFullName;
	 * }
	 * 
	 * public boolean isEditHistoryRecord() { return isEditHistoryRecord; }
	 * 
	 * public ArrayList<MedicalPersonHistoryRecord> getHistoryRecordList() {
	 * return new
	 * ArrayList<MedicalPersonHistoryRecord>(historyRecordMap.values()); }
	 * 
	 * public boolean isSelfRelation() { if (insuredPersonDTO.getRelationship()
	 * != null &&
	 * "SELF".equalsIgnoreCase(insuredPersonDTO.getRelationship().getName()))
	 * return true; else return false; }
	 * 
	 */}
