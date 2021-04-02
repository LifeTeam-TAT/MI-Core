package org.ace.insurance.web.manage.medical.claim.request;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

/***************************************************************************************
 * @author MPPA
 * @Date 2015-01-12
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalClaim</code> request process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "MedicalClaimRequestActionBean")
public class MedicalClaimRequestActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH CLaim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * private User user; private MedicalClaimProposalDTO
	 * medicalClaimProposalDTO; private HospitalizedClaimDTO
	 * hospitalizedClaimDTO; private OperationClaimDTO operationClaimDTO;
	 * private MedicationClaimDTO medicationClaimDTO; private DeathClaimDTO
	 * deathClaimDTO; private MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiaryDTO; private
	 * List<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * policyInsuredPersonBeneficiaryList; private
	 * MedicalPolicyInsuredPersonBeneficiaryDTO selectedBeneficiaryDTO; private
	 * String[] selectedClaimTypes; private List<RelationShip> relationShipList;
	 * private User responsiblePerson; private MedicalClaimInitialReportDTO
	 * medicalClaimInitialReportDTO; private String remark; private boolean
	 * saleMan; private boolean disablelinkBtn; private boolean death; private
	 * boolean accident; private boolean other; private boolean child; private
	 * boolean changeFlag; private boolean finish;
	 * 
	 * private List<ICD10> deathReasonList; private List<ICD10>
	 * selectedDeathList;
	 * 
	 * private List<ICD10> hospitalizedReasonList; private List<ICD10>
	 * selectedHospitalizedList;
	 * 
	 * private List<ICD10> disabilityReasonList; private List<ICD10>
	 * selectedDisabilityList;
	 * 
	 * private List<ICD10> operationReasonList; private List<ICD10>
	 * selectedOperationList;
	 * 
	 * private List<ICD10> icd10List;
	 * 
	 * @ManagedProperty(value = "#{ICD10Service}") private IICD10Service
	 * iCD10Service;
	 * 
	 * public void setiCD10Service(IICD10Service iCD10Service) {
	 * this.iCD10Service = iCD10Service; }
	 * 
	 * @ManagedProperty(value = "#{RelationShipService}") private
	 * IRelationShipService relationShipService;
	 * 
	 * @ManagedProperty(value = "#{DeathBeneficiaryValidator}") private
	 * DTOValidator<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * deathBeneInfoValidator;
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimRequestFrontService}") private
	 * IMedicalClaimRequestFrontService medicalClaimRequestFrontService;
	 * 
	 * public void
	 * setMedicalClaimRequestFrontService(IMedicalClaimRequestFrontService
	 * medicalClaimRequestFrontService) { this.medicalClaimRequestFrontService =
	 * medicalClaimRequestFrontService; }
	 * 
	 * @PreDestroy public void destroy() {
	 * removeParam("medicalClaimProposalDTO");
	 * removeParam("medicalClaimInitialReportDTO"); }
	 * 
	 * @PostConstruct public void init() { initializeInjection();
	 * medicalClaimProposalDTO = new MedicalClaimProposalDTO();
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * selectedBeneficiaryDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();
	 * relationShipList = relationShipService.findAllRelationShip();
	 * rebindInitialReportData(); }
	 * 
	 * private void rebindInitialReportData() { disablelinkBtn = true; death =
	 * false; child = false; other = false; finish = false; saleMan = false;
	 * icd10List = iCD10Service.findAllICD10();
	 * 
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * selectedBeneficiaryDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();
	 * medicalClaimProposalDTO = new MedicalClaimProposalDTO();
	 * medicalClaimProposalDTO.setClaimReportNo(medicalClaimInitialReportDTO.
	 * getClaimReportNo());
	 * medicalClaimProposalDTO.setPolicyNo(medicalClaimInitialReportDTO.
	 * getPolicyNo()); medicalClaimProposalDTO.setPolicyInsuredPersonDTO(
	 * MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPersonDTO(
	 * medicalClaimInitialReportDTO .getPolicyInsuredPerson()));
	 * 
	 * if (medicalClaimInitialReportDTO.getPolicyInsuredPerson().getGuardian()
	 * != null) { child = true; } medicalClaimProposalDTO.setBranch(null); if
	 * (medicalClaimInitialReportDTO.getAgent() != null) { saleMan = true;
	 * medicalClaimProposalDTO.setAgent(medicalClaimInitialReportDTO.getAgent())
	 * ; } else if (medicalClaimInitialReportDTO.getSaleMan() != null) { saleMan
	 * = false; medicalClaimProposalDTO.setSaleman(medicalClaimInitialReportDTO.
	 * getSaleMan()); } medicalClaimProposalDTO.setSubmittedDate(new Date());
	 * policyInsuredPersonBeneficiaryList = new
	 * ArrayList<MedicalPolicyInsuredPersonBeneficiaryDTO>(); for
	 * (MedicalPolicyInsuredPersonBeneficiaries mpb :
	 * medicalClaimInitialReportDTO.getPolicyInsuredPerson().
	 * getPolicyInsuredPersonBeneficiariesList()) {
	 * policyInsuredPersonBeneficiaryList.add(
	 * MedicalPolicyInsuredPersonBeneficiaryFactory.
	 * createMedicalPolicyInsuredPersonBeneficiaryDTO(mpb)); }
	 * 
	 * if (medicalClaimInitialReportDTO.getClaimInitialReportICDList() != null)
	 * { if (hospitalizedReasonList == null) { hospitalizedReasonList = new
	 * ArrayList<ICD10>(); } if (deathReasonList == null) { deathReasonList =
	 * new ArrayList<ICD10>(); } if (operationReasonList == null) {
	 * operationReasonList = new ArrayList<ICD10>(); } if (disabilityReasonList
	 * == null) { disabilityReasonList = new ArrayList<ICD10>(); }
	 * 
	 * for (ClaimInitialReportICD temp :
	 * medicalClaimInitialReportDTO.getClaimInitialReportICDList()) {
	 * hospitalizedReasonList.add(temp.getIcd10()); } }
	 * 
	 * selectedClaimTypes = null; }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalClaimInitialReportDTO =
	 * (medicalClaimInitialReportDTO == null) ? (MedicalClaimInitialReportDTO)
	 * getParam("medicalClaimInitialReportDTO") : medicalClaimInitialReportDTO;
	 * }
	 * 
	 * public String onFlowProcess(FlowEvent event) { String formID =
	 * "medicalClaimRequestForm"; boolean valid = true; if
	 * ("claimProposal".equals(event.getOldStep()) &&
	 * "generalInfo".equals(event.getNewStep())) {
	 * 
	 * } else if ("generalInfo".equals(event.getOldStep()) &&
	 * "beneficiaryClaimInfo".equals(event.getNewStep())) { if
	 * (medicalClaimProposalDTO.isHospitalized()) { if
	 * (hospitalizedClaimDTO.getHospitalizedStartDate().after(
	 * hospitalizedClaimDTO.getHospitalizedEndDate())) { addErrorMessage(formID
	 * + ":startDate", MessageId.HOSPITALIZED_DATE_INFO); valid = false; } if
	 * (Utils.daysBetween(hospitalizedClaimDTO.getHospitalizedStartDate(),
	 * hospitalizedClaimDTO.getHospitalizedEndDate(), false, true) >
	 * medicalClaimInitialReportDTO
	 * .getPolicyInsuredPerson().getProduct().getMaxHospDays()) {
	 * hospitalizedClaimDTO.setHospitalizedEndDate(Utils.plusDays(
	 * hospitalizedClaimDTO.getHospitalizedStartDate(),
	 * medicalClaimInitialReportDTO
	 * .getPolicyInsuredPerson().getProduct().getMaxHospDays() - 1)); } } if
	 * (medicalClaimProposalDTO.isOperation() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (operationClaimDTO.getOperationDate().after(deathClaimDTO.getDeathDate())
	 * ) { addErrorMessage(formID + ":operationDate",
	 * MessageId.OPERATION_DATE_INFO); valid = false; } } if
	 * (medicalClaimProposalDTO.isHospitalized() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (hospitalizedClaimDTO.getHospitalizedStartDate().after(deathClaimDTO.
	 * getDeathDate())) { addErrorMessage(formID + ":startDate",
	 * MessageId.HOSPITALIZED_STARTDATE_INFO); valid = false; } if
	 * (hospitalizedClaimDTO.getHospitalizedEndDate().after(deathClaimDTO.
	 * getDeathDate())) { addErrorMessage(formID + ":endDate",
	 * MessageId.HOSPITALIZED_ENDDATE_INFO); valid = false; } } if
	 * (medicalClaimProposalDTO.isMedication() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (medicationClaimDTO.getReceivedDate().after(deathClaimDTO.getDeathDate())
	 * ) { addErrorMessage(formID + ":disabilityDate",
	 * MessageId.RECEIVED_DATE_INFO); valid = false; } }
	 * 
	 * float percentage = 0.0f;
	 * 
	 * for (MedicalPolicyInsuredPersonBeneficiaries mpb :
	 * medicalClaimInitialReportDTO.getPolicyInsuredPerson().
	 * getPolicyInsuredPersonBeneficiariesList()) { if (mpb.isDeath()) {
	 * percentage += mpb.getPercentage();
	 * medicalClaimBeneficiaryDTO.setDeath(true);
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * SUCCESSOR); medicalClaimBeneficiaryDTO.setPercentage(percentage); } } }
	 * else if ("beneficiaryClaimInfo".equals(event.getOldStep()) &&
	 * "workFlow".equals(event.getNewStep())) { if
	 * (policyInsuredPersonBeneficiaryList != null) { boolean hasBen = false;
	 * for (MedicalPolicyInsuredPersonBeneficiaryDTO insBen :
	 * policyInsuredPersonBeneficiaryList) { if (insBen.isClaimBeneficiary()) {
	 * hasBen = true; } } if
	 * (medicalClaimInitialReportDTO.getPolicyInsuredPerson().getGuardian() !=
	 * null && !hasBen &&
	 * medicalClaimInitialReportDTO.getPolicyInsuredPerson().getGuardian().
	 * isDeath()) { addErrorMessage(formID +
	 * ":policyInsuredPersonBeneficiariesTable",
	 * MessageId.REQUIRED_BENEFICIRY_INFO); valid = false; } else if (!hasBen &&
	 * medicalClaimProposalDTO.isDeath()) { addErrorMessage(formID +
	 * ":policyInsuredPersonBeneficiariesTable",
	 * MessageId.REQUIRED_BENEFICIRY_INFO); valid = false; } } }
	 * 
	 * return valid ? event.getNewStep() : event.getOldStep(); }
	 * 
	 * public List<MedicalPolicyInsuredPerson> getPolicyInsuredPersonList() {
	 * return
	 * Arrays.asList(medicalClaimInitialReportDTO.getPolicyInsuredPerson()); }
	 * 
	 * public void changeGuardian(AjaxBehaviorEvent event) { if
	 * (!medicalClaimInitialReportDTO.getPolicyInsuredPerson().getGuardian().
	 * isDeath()) { if (medicalClaimBeneficiaryDTO != null) { if
	 * (medicalClaimBeneficiaryDTO.getPercentage() >= 0) {
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * selectedBeneficiaryDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();
	 * policyInsuredPersonBeneficiaryList = new
	 * ArrayList<MedicalPolicyInsuredPersonBeneficiaryDTO>(); for
	 * (MedicalPolicyInsuredPersonBeneficiaries mpb :
	 * medicalClaimInitialReportDTO.getPolicyInsuredPerson().
	 * getPolicyInsuredPersonBeneficiariesList()) {
	 * policyInsuredPersonBeneficiaryList.add(
	 * MedicalPolicyInsuredPersonBeneficiaryFactory.
	 * createMedicalPolicyInsuredPersonBeneficiaryDTO(mpb)); } } } else {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * BENEFICIARY); } } }
	 * 
	 * public void changeClaimType() { if (selectedClaimTypes != null) {
	 * medicalClaimProposalDTO.setHospitalized(false);
	 * medicalClaimProposalDTO.setOperation(false);
	 * medicalClaimProposalDTO.setMedication(false);
	 * medicalClaimProposalDTO.setDeath(false); death = false; for (String
	 * claimtype : selectedClaimTypes) { if
	 * (claimtype.equalsIgnoreCase("Hospitalization")) { hospitalizedClaimDTO =
	 * new HospitalizedClaimDTO();
	 * medicalClaimProposalDTO.setHospitalized(true);
	 * hospitalizedClaimDTO.setHospitalizedStartDate(
	 * medicalClaimInitialReportDTO.getHospitalizedStartDate());
	 * hospitalizedClaimDTO.setMedicalPlace(medicalClaimInitialReportDTO.
	 * getMedicalPlace()); } else if (claimtype.equalsIgnoreCase("Operation")) {
	 * operationClaimDTO = new OperationClaimDTO();
	 * medicalClaimProposalDTO.setOperation(true); } else if
	 * (claimtype.equalsIgnoreCase("Disability")) {
	 * medicalClaimProposalDTO.setMedication(true); medicationClaimDTO = new
	 * MedicationClaimDTO(); } else if (claimtype.equalsIgnoreCase("Death")) {
	 * if (medicalClaimInitialReportDTO.getPolicyInsuredPerson().getGuardian()
	 * != null) {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * GUARDIAN); } else {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * BENEFICIARY); } medicalClaimProposalDTO.setDeath(true); deathClaimDTO =
	 * new DeathClaimDTO();
	 * deathClaimDTO.setDeathClaimType(DeathClaimType.OTHER); death = true; } }
	 * } }
	 * 
	 * public void changeSelectedBeneficiaryEvent(AjaxBehaviorEvent e) { UIData
	 * data = (UIData)
	 * e.getComponent().findComponent("policyInsuredPersonBeneficiariesTable");
	 * int rowIndex = data.getRowIndex();
	 * MedicalPolicyInsuredPersonBeneficiaryDTO beneficiary =
	 * policyInsuredPersonBeneficiaryList.get(rowIndex); if
	 * (!beneficiary.isClaimBeneficiary()) { if (beneficiary.isDeath()) { float
	 * percentage = medicalClaimBeneficiaryDTO.getPercentage();
	 * medicalClaimBeneficiaryDTO.setPercentage(percentage -
	 * beneficiary.getPercentage()); } beneficiary.setDeath(false);
	 * beneficiary.setDeathDate(null); beneficiary.setDeathReason(null);
	 * beneficiary.setExit(false); if
	 * (medicalClaimBeneficiaryDTO.getPercentage() <= 0.0) {
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * medicalClaimBeneficiaryDTO.setDeath(false); } } }
	 * 
	 * public void resetDeathBeneficiaryEvent(AjaxBehaviorEvent e) { if
	 * (!selectedBeneficiaryDTO.isDeath()) {
	 * selectedBeneficiaryDTO.setDeath(false);
	 * selectedBeneficiaryDTO.setDeathDate(null);
	 * selectedBeneficiaryDTO.setDeathReason(null);
	 * selectedBeneficiaryDTO.setExit(false); validDeathBeneficiary = false; }
	 * else { selectedBeneficiaryDTO.setExit(true); } }
	 * 
	 * private boolean validDeathBeneficiary;
	 * 
	 * public boolean isValidDeathBeneficiary() { return validDeathBeneficiary;
	 * }
	 * 
	 * public boolean isAccident() { return accident; }
	 * 
	 * public void setAccident(boolean accident) { this.accident = accident; }
	 * 
	 * public void updateDeathBeneficiary() { if
	 * (selectedBeneficiaryDTO.isDeath()) {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * SUCCESSOR); ValidationResult result =
	 * deathBeneInfoValidator.validate(selectedBeneficiaryDTO); if
	 * (!result.isVerified()) { for (ErrorMessage message :
	 * result.getErrorMeesages()) { addErrorMessage(message); } } else {
	 * RequestContext.getCurrentInstance().execute(
	 * "beneficiaryDeathDialog.hide()"); resetSuccessorInfo(); }
	 * validDeathBeneficiary = true; } else {
	 * RequestContext.getCurrentInstance().execute(
	 * "beneficiaryDeathDialog.hide()"); resetSuccessorInfo(); }
	 * 
	 * }
	 * 
	 * public List<ICD10> getIcd10List() { return icd10List; }
	 * 
	 * public void setIcd10List(List<ICD10> icd10List) { this.icd10List =
	 * icd10List; }
	 * 
	 * private void resetSuccessorInfo() { boolean claimSuccessor = false; for
	 * (MedicalPolicyInsuredPersonBeneficiaryDTO mpipb :
	 * policyInsuredPersonBeneficiaryList) { if (mpipb.isDeath()) {
	 * claimSuccessor = true; break; } }
	 * medicalClaimBeneficiaryDTO.setDeath(claimSuccessor);
	 * 
	 * boolean transform = false; if (changeFlag !=
	 * selectedBeneficiaryDTO.isExit()) { transform = true; }
	 * 
	 * if (transform) { float percentage =
	 * medicalClaimBeneficiaryDTO.getPercentage(); if
	 * (selectedBeneficiaryDTO.isDeath()) {
	 * medicalClaimBeneficiaryDTO.setPercentage(percentage +
	 * selectedBeneficiaryDTO.getPercentage()); } else {
	 * medicalClaimBeneficiaryDTO.setPercentage(percentage -
	 * selectedBeneficiaryDTO.getPercentage()); }
	 * 
	 * } else { float percentage = medicalClaimBeneficiaryDTO.getPercentage();
	 * medicalClaimBeneficiaryDTO.setPercentage(percentage -
	 * selectedBeneficiaryDTO.getPercentage()); if
	 * (medicalClaimBeneficiaryDTO.getPercentage() <= 0.0) {
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO(); } } }
	 * 
	 * public void
	 * prepareEditDeathBeneficiary(MedicalPolicyInsuredPersonBeneficiaryDTO
	 * medPolInsPerBene) { changeFlag = medPolInsPerBene.isExit();
	 * this.selectedBeneficiaryDTO = medPolInsPerBene; }
	 * 
	 * public void changeSaleMan(AjaxBehaviorEvent event) { if (!saleMan) {
	 * medicalClaimProposalDTO.setAgent(null);
	 * medicalClaimProposalDTO.setSaleman(null); } else {
	 * medicalClaimProposalDTO.setSaleman(null);
	 * medicalClaimProposalDTO.setAgent(null); } }
	 * 
	 * public void returnSaleMan(SelectEvent event) { SaleMan saleMan =
	 * (SaleMan) event.getObject(); medicalClaimProposalDTO.setSaleman(saleMan);
	 * }
	 * 
	 * public void returnAgent(SelectEvent event) { Agent agent = (Agent)
	 * event.getObject(); medicalClaimProposalDTO.setAgent(agent); }
	 * 
	 * public void returnBranch(SelectEvent event) { Branch branch = (Branch)
	 * event.getObject(); medicalClaimProposalDTO.setBranch(branch); }
	 * 
	 * public void returnMedicalPlaceDialog(SelectEvent event) { Hospital
	 * medicalPlace = (Hospital) event.getObject();
	 * this.hospitalizedClaimDTO.setMedicalPlace(medicalPlace); }
	 * 
	 * public void returnOperationDialog(SelectEvent event) { Operation
	 * operation = (Operation) event.getObject();
	 * this.operationClaimDTO.setOperation(operation); }
	 * 
	 * public void returnOperationReasonDialog(SelectEvent event) { ICD10 icd10
	 * = (ICD10) event.getObject();
	 * this.operationClaimDTO.setOperationReason(icd10); }
	 * 
	 * public void returnMedicationReasonDialog(SelectEvent event) { ICD10 icd10
	 * = (ICD10) event.getObject();
	 * this.medicationClaimDTO.setMedicationReason(icd10); }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_CLAIM_SURVEY,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public String submitClaimInfo() { String result = null;
	 * List<MedicalPolicyInsuredPersonBeneficiaries> beneficiaryList = new
	 * ArrayList<MedicalPolicyInsuredPersonBeneficiaries>(); WorkflowTask
	 * workflowTask = WorkflowTask.MEDICAL_CLAIM_SURVEY; try { if
	 * (medicalClaimBeneficiaryDTO.getPercentage() > 0.0) {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * SUCCESSOR); medicalClaimBeneficiaryDTO.setDeath(true);
	 * medicalClaimProposalDTO.addClaimBeneficiary(medicalClaimBeneficiaryDTO);
	 * } else if
	 * (medicalClaimProposalDTO.getPolicyInsuredPersonDTO().getGuardian() !=
	 * null &&
	 * !medicalClaimProposalDTO.getPolicyInsuredPersonDTO().getGuardian().
	 * isDeath()) { medicalClaimBeneficiaryDTO = new
	 * MedicalClaimBeneficiaryDTO();
	 * medicalClaimBeneficiaryDTO.setMedPolicyGuardianDTO(new
	 * PolicyGuardianDTO(medicalClaimInitialReportDTO.getPolicyInsuredPerson().
	 * getGuardian()));
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * GUARDIAN);
	 * medicalClaimProposalDTO.addClaimBeneficiary(medicalClaimBeneficiaryDTO);
	 * } else if (!isChild() && !isDeath()) { MedicalPolicyInsuredPersonDTO
	 * medicalPolicyInsuredPersonDTO =
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO();
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * medicalClaimBeneficiaryDTO.setMedicalPolicyInsuredPersonDTO(
	 * medicalPolicyInsuredPersonDTO);
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * INSURED_PERSON);
	 * medicalClaimProposalDTO.addClaimBeneficiary(medicalClaimBeneficiaryDTO);
	 * }
	 * 
	 * for (MedicalPolicyInsuredPersonBeneficiaryDTO mpInsuPerBene :
	 * policyInsuredPersonBeneficiaryList) {
	 * beneficiaryList.add(MedicalPolicyInsuredPersonBeneficiaryFactory.
	 * createMedicalPolicyInsuredPersonBeneficiary(mpInsuPerBene)); if
	 * (mpInsuPerBene.isClaimBeneficiary() && !mpInsuPerBene.isDeath()) {
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * BENEFICIARY); medicalClaimBeneficiaryDTO.setBeneficiaryNo(mpInsuPerBene.
	 * getBeneficiaryNo());
	 * medicalClaimBeneficiaryDTO.setMedPolicyInsuBeneDTO(mpInsuPerBene);
	 * medicalClaimProposalDTO.addClaimBeneficiary(medicalClaimBeneficiaryDTO);
	 * } } if (medicalClaimProposalDTO.isHospitalized()) { for (ICD10 temp :
	 * hospitalizedReasonList) { HospitalizedClaimICD10 hospICD10 = new
	 * HospitalizedClaimICD10(); hospICD10.setIcd10(temp);
	 * hospitalizedClaimDTO.addHospitalizedClaimICD10(hospICD10); }
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(hospitalizedClaimDTO); }
	 * if (medicalClaimProposalDTO.isOperation()) { for (ICD10 temp :
	 * operationReasonList) { OperationClaimICD10 icd10 = new
	 * OperationClaimICD10(); icd10.setIcd10(temp);
	 * operationClaimDTO.addOperationClaimICD10(icd10); }
	 * medicalClaimProposalDTO.setOperationClaimDTO(operationClaimDTO);
	 * 
	 * } if (medicalClaimProposalDTO.isMedication()) { for (ICD10 temp :
	 * disabilityReasonList) { DisabilityClaimICD10 icd10 = new
	 * DisabilityClaimICD10(); icd10.setIcd10(temp);
	 * medicationClaimDTO.addDisabilityClaimICD10(icd10); }
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicationClaimDTO); } if
	 * (medicalClaimProposalDTO.isDeath()) { for (ICD10 temp : deathReasonList)
	 * { DeathClaimICD10 deathICD10 = new DeathClaimICD10();
	 * deathICD10.setIcd10(temp); deathClaimDTO.addDeathClaimICD10(deathICD10);
	 * } if (accident) {
	 * deathClaimDTO.setDeathClaimType(DeathClaimType.ACCIDENT_DEATH); }
	 * medicalClaimProposalDTO.setDeathClaimDTO(deathClaimDTO);
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO().setDeath(true); }
	 * WorkFlowDTO workFlowDTO = new WorkFlowDTO("", remark, workflowTask,
	 * ReferenceType.MEDICAL_CLAIM, user, responsiblePerson);
	 * medicalClaimProposalDTO =
	 * medicalClaimRequestFrontService.addNewMedicalClaim(
	 * medicalClaimProposalDTO, medicalClaimInitialReportDTO, beneficiaryList,
	 * workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.MEDICAL_ClAIM_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalClaimProposalDTO.getClaimRequestId()); result = "dashboard"; }
	 * catch (SystemException ex) { handelSysException(ex); } return result; }
	 * 
	 * public void removeHospitalizedList(ICD10 icd) {
	 * hospitalizedReasonList.remove(icd); }
	 * 
	 * public void removeOperationList(ICD10 icd) {
	 * operationReasonList.remove(icd); }
	 * 
	 * public void removeDisabilityList(ICD10 icd) {
	 * disabilityReasonList.remove(icd); }
	 * 
	 * public void removeDeathList(ICD10 icd) { deathReasonList.remove(icd); }
	 * 
	 * public void addNewHospitalizedICD10(ICD10 icd) { if
	 * (hospitalizedReasonList == null) { hospitalizedReasonList = new
	 * ArrayList<ICD10>(); }
	 * 
	 * for (ICD10 var : selectedHospitalizedList) { if
	 * (!hospitalizedReasonList.contains(var)) {
	 * hospitalizedReasonList.add(var); } } }
	 * 
	 * public void addNewOperationICD10(ICD10 icd) { if (operationReasonList ==
	 * null) { operationReasonList = new ArrayList<ICD10>(); }
	 * 
	 * for (ICD10 var : selectedOperationList) { if
	 * (!operationReasonList.contains(var)) { operationReasonList.add(var); } }
	 * }
	 * 
	 * public void addNewDisabilityICD10(ICD10 icd) { if (disabilityReasonList
	 * == null) { disabilityReasonList = new ArrayList<ICD10>(); }
	 * 
	 * for (ICD10 var : selectedDisabilityList) { if
	 * (!disabilityReasonList.contains(var)) { disabilityReasonList.add(var); }
	 * } }
	 * 
	 * public void addNewDeathICD10(ICD10 icd) { if (deathReasonList == null) {
	 * deathReasonList = new ArrayList<ICD10>(); }
	 * 
	 * for (ICD10 var : selectedDeathList) { if (!deathReasonList.contains(var))
	 * { deathReasonList.add(var); } } }
	 * 
	 * public List<ICD10> getDisabilityReasonList() { return
	 * disabilityReasonList; }
	 * 
	 * public void setDisabilityReasonList(List<ICD10> disabilityReasonList) {
	 * this.disabilityReasonList = disabilityReasonList; }
	 * 
	 * public List<ICD10> getOperationReasonList() { return operationReasonList;
	 * }
	 * 
	 * public void setOperationReasonList(List<ICD10> operationReasonList) {
	 * this.operationReasonList = operationReasonList; }
	 * 
	 * public DTOValidator<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * getDeathBeneInfoValidator() { return deathBeneInfoValidator; }
	 * 
	 * public void setDeathBeneInfoValidator(DTOValidator<
	 * MedicalPolicyInsuredPersonBeneficiaryDTO> deathBeneInfoValidator) {
	 * this.deathBeneInfoValidator = deathBeneInfoValidator; }
	 * 
	 * public void setRelationShipService(IRelationShipService
	 * relationShipService) { this.relationShipService = relationShipService; }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public HospitalizedClaimDTO getHospitalizedClaimDTO() { return
	 * hospitalizedClaimDTO; }
	 * 
	 * public void setHospitalizedClaimDTO(HospitalizedClaimDTO
	 * hospitalizedClaimDTO) { this.hospitalizedClaimDTO = hospitalizedClaimDTO;
	 * }
	 * 
	 * public OperationClaimDTO getOperationClaimDTO() { return
	 * operationClaimDTO; }
	 * 
	 * public MedicationClaimDTO getMedicationClaimDTO() { return
	 * medicationClaimDTO; }
	 * 
	 * public void setMedicationClaimDTO(MedicationClaimDTO medicationClaimDTO)
	 * { this.medicationClaimDTO = medicationClaimDTO; }
	 * 
	 * public void setOperationClaimDTO(OperationClaimDTO operationClaimDTO) {
	 * this.operationClaimDTO = operationClaimDTO; }
	 * 
	 * public DeathClaimDTO getDeathClaimDTO() { return deathClaimDTO; }
	 * 
	 * public void setDeathClaimDTO(DeathClaimDTO deathClaimDTO) {
	 * this.deathClaimDTO = deathClaimDTO; }
	 * 
	 * public String[] getSelectedClaimTypes() { return selectedClaimTypes; }
	 * 
	 * public void setSelectedClaimTypes(String[] selectedClaimTypes) {
	 * this.selectedClaimTypes = selectedClaimTypes; }
	 * 
	 * public List<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * getPolicyInsuredPersonBeneficiaryList() { return
	 * policyInsuredPersonBeneficiaryList; }
	 * 
	 * public void setPolicyInsuredPersonBeneficiaryList(List<
	 * MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * policyInsuredPersonBeneficiaryList) {
	 * this.policyInsuredPersonBeneficiaryList =
	 * policyInsuredPersonBeneficiaryList; }
	 * 
	 * public MedicalPolicyInsuredPersonBeneficiaryDTO
	 * getSelectedBeneficiaryDTO() { return selectedBeneficiaryDTO; }
	 * 
	 * public void
	 * setSelectedBeneficiaryDTO(MedicalPolicyInsuredPersonBeneficiaryDTO
	 * selectedBeneficiaryDTO) { this.selectedBeneficiaryDTO =
	 * selectedBeneficiaryDTO; }
	 * 
	 * public MedicalClaimBeneficiaryDTO getMedicalClaimBeneficiaryDTO() {
	 * return medicalClaimBeneficiaryDTO; }
	 * 
	 * public void setMedicalClaimBeneficiaryDTO(MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiaryDTO) { this.medicalClaimBeneficiaryDTO =
	 * medicalClaimBeneficiaryDTO; }
	 * 
	 * public void setSaleMan(boolean saleMan) { this.saleMan = saleMan; }
	 * 
	 * public boolean isSaleMan() { return saleMan; }
	 * 
	 * public void setDisablelinkBtn(boolean disablelinkBtn) {
	 * this.disablelinkBtn = disablelinkBtn; }
	 * 
	 * public boolean isDisablelinkBtn() { return disablelinkBtn; }
	 * 
	 * public boolean isDeath() { return death; }
	 * 
	 * public void setDeath(boolean death) { this.death = death; }
	 * 
	 * public boolean isOther() { return other; }
	 * 
	 * public void setOther(boolean other) { this.other = other; }
	 * 
	 * public List<RelationShip> getRelationShipList() { return
	 * relationShipList; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * public boolean isChild() { return child; }
	 * 
	 * public boolean isFinish() { return finish; }
	 * 
	 * public void selectedDeathPlace() { if
	 * (deathClaimDTO.getDeathPlace().equalsIgnoreCase("Other")) { other = true;
	 * } else other = false; }
	 * 
	 * public List<ICD10> getSelectedHospitalizedList() { return
	 * selectedHospitalizedList; }
	 * 
	 * public void setSelectedHospitalizedList(List<ICD10>
	 * selectedHospitalizedList) { this.selectedHospitalizedList =
	 * selectedHospitalizedList; }
	 * 
	 * public List<ICD10> getSelectedDisabilityList() { return
	 * selectedDisabilityList; }
	 * 
	 * public void setSelectedDisabilityList(List<ICD10> selectedDisabilityList)
	 * { this.selectedDisabilityList = selectedDisabilityList; }
	 * 
	 * public List<ICD10> getSelectedOperationList() { return
	 * selectedOperationList; }
	 * 
	 * public void setSelectedOperationList(List<ICD10> selectedOperationList) {
	 * this.selectedOperationList = selectedOperationList; }
	 * 
	 * public List<ICD10> getSelectedDeathList() { return selectedDeathList; }
	 * 
	 * public void setSelectedDeathList(List<ICD10> selectedDeathList) {
	 * this.selectedDeathList = selectedDeathList; }
	 * 
	 * public List<ICD10> getHospitalizedReasonList() { return
	 * hospitalizedReasonList; }
	 * 
	 * public List<ICD10> getDeathReasonList() { return deathReasonList; }
	 * 
	 */}
