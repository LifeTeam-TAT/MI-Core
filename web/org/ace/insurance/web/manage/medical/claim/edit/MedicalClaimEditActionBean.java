package org.ace.insurance.web.manage.medical.claim.edit;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

/***************************************************************************************
 * @author TZA
 * @Date 2015-01-12
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalClaim</code> edit process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "MedicalClaimEditActionBean")
public class MedicalClaimEditActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH Claim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * private MedicalClaimProposalDTO medicalClaimProposalDTO; private User
	 * user; private boolean saleMan; private boolean disablelinkBtn; private
	 * boolean other; private boolean child; private boolean deathGuardian;
	 * private boolean insuredPerson; private boolean beneficiary; private
	 * boolean guardian; private boolean successor; private boolean
	 * existBeneficiary; private String remark; private User responsiblePerson;
	 * private WorkflowTask workFlowTask; private List<String>
	 * selectedClaimTypes; private MedicationClaimDTO medicationClaimDTO;
	 * private HospitalizedClaimDTO hospitalizedClaimDTO; private
	 * OperationClaimDTO operationClaimDTO; private DeathClaimDTO deathClaimDTO;
	 * private List<RelationShip> relationShipList; private
	 * MedicalPolicyInsuredPersonBeneficiaryDTO selectedBeneficiaryDTO; private
	 * MedicalClaimBeneficiaryDTO medicalClaimBeneficiaryDTO; private
	 * List<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * policyInsuredPersonBeneficiaryList; private List<WorkFlowHistory>
	 * workFlowHistoryList; private List<String> items; private Map<String,
	 * MedicalClaimDTO> map = new HashMap<String, MedicalClaimDTO>();
	 * 
	 * private Map<String, Boolean> checkMap = new HashMap<String, Boolean>();
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimProposalService}") private
	 * IMedicalClaimProposalService medicalClaimProposalService;
	 * 
	 * public void setMedicalClaimProposalService(IMedicalClaimProposalService
	 * medicalClaimProposalService) { this.medicalClaimProposalService =
	 * medicalClaimProposalService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimRequestFrontService}") private
	 * IMedicalClaimRequestFrontService medicalClaimRequestFrontService;
	 * 
	 * public void
	 * setMedicalClaimRequestFrontService(IMedicalClaimRequestFrontService
	 * medicalClaimRequestFrontService) { this.medicalClaimRequestFrontService =
	 * medicalClaimRequestFrontService; }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * @ManagedProperty(value = "#{DeathBeneficiaryValidator}") private
	 * DTOValidator<MedicalPolicyInsuredPersonBeneficiaryDTO>
	 * deathBeneInfoValidator;
	 * 
	 * public void setDeathBeneInfoValidator(DTOValidator<
	 * MedicalPolicyInsuredPersonBeneficiaryDTO> deathBeneInfoValidator) {
	 * this.deathBeneInfoValidator = deathBeneInfoValidator; }
	 * 
	 * @ManagedProperty(value = "#{RelationShipService}") private
	 * IRelationShipService relationShipService;
	 * 
	 * public void setRelationShipService(IRelationShipService
	 * relationShipService) { this.relationShipService = relationShipService; }
	 * 
	 * @PostConstruct public void init() { initializeInjection(); items = new
	 * ArrayList<String>(); selectedClaimTypes = new ArrayList<String>();
	 * 
	 * selectedBeneficiaryDTO = new MedicalPolicyInsuredPersonBeneficiaryDTO();
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO();
	 * relationShipList = relationShipService.findAllRelationShip();
	 * 
	 * if (medicalClaimProposalDTO.isHospitalized()) {
	 * items.add("Hospitalization"); selectedClaimTypes.add("Hospitalization");
	 * } if (medicalClaimProposalDTO.isOperation()) { items.add("Operation");
	 * selectedClaimTypes.add("Operation"); } if
	 * (medicalClaimProposalDTO.isMedication()) { items.add("Medication");
	 * selectedClaimTypes.add("Medication"); } if
	 * (medicalClaimProposalDTO.isDeath()) { items.add("Death");
	 * selectedClaimTypes.add("Death"); } for (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) { if
	 * (mcb.getName() != null) {
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(MedicalBeneficiaryRole.
	 * SUCCESSOR); medicalClaimBeneficiaryDTO.setName(mcb.getName());
	 * medicalClaimBeneficiaryDTO.setNrc(mcb.getNrc()); System.out.println("Pol"
	 * + mcb.getRelationShip());
	 * medicalClaimBeneficiaryDTO.setRelationShip(mcb.getRelationShip());
	 * medicalClaimBeneficiaryDTO.setPercentage(mcb.getPercentage()); } if
	 * (mcb.getMedPolicyInsuBeneDTO() != null) {
	 * mcb.getMedPolicyInsuBeneDTO().setClaimBeneficiary(true); } if
	 * (mcb.getMedPolicyInsuBeneDTO() != null) { existBeneficiary = true; } } }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalClaimProposalDTO =
	 * (medicalClaimProposalDTO == null) ? (MedicalClaimProposalDTO)
	 * getParam("medicalClaimProposalDTO") : medicalClaimProposalDTO; WorkFlow
	 * workFlow =
	 * workFlowService.findWorkFlowByRefNo(medicalClaimProposalDTO.getId());
	 * policyInsuredPersonBeneficiaryList = new
	 * ArrayList<MedicalPolicyInsuredPersonBeneficiaryDTO>(); for
	 * (MedicalPolicyInsuredPersonBeneficiaryDTO mpb :
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO().
	 * getPolicyInsuredPersonBeneficiariesDtoList()) { if (mpb.isDeath()) {
	 * mpb.setClaimBeneficiary(true); }
	 * policyInsuredPersonBeneficiaryList.add(mpb); } if (workFlow != null) {
	 * responsiblePerson = workFlow.getResponsiblePerson(); workFlowTask =
	 * workFlow.getWorkflowTask();
	 * 
	 * } if (medicalClaimProposalDTO.getDeathClaimDTO() != null) {
	 * map.put("Death", new
	 * MedicalClaimDTO(medicalClaimProposalDTO.getDeathClaimDTO())); } if
	 * (medicalClaimProposalDTO.getOperationClaimDTO() != null) {
	 * map.put("Operation", new
	 * OperationClaimDTO(medicalClaimProposalDTO.getOperationClaimDTO())); } if
	 * (medicalClaimProposalDTO.getDeathClaimDTO() != null) {
	 * map.put("Medication", new
	 * DeathClaimDTO(medicalClaimProposalDTO.getMedicationClaimDTO())); } if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null) {
	 * map.put("Hospitalization", new
	 * HospitalizedClaimDTO(medicalClaimProposalDTO.getHospitalizedClaimDTO()));
	 * } }
	 * 
	 * public boolean getSaleMan() { if (medicalClaimProposalDTO.getAgent() !=
	 * null) { saleMan = false; } else if (medicalClaimProposalDTO.getSaleman()
	 * != null) { saleMan = true; } return saleMan; }
	 * 
	 * public void changeSaleMan(AjaxBehaviorEvent event) { if (saleMan) {
	 * medicalClaimProposalDTO.setAgent(null); } else {
	 * medicalClaimProposalDTO.setSaleman(null); } }
	 * 
	 * public void changeGuardion(AjaxBehaviorEvent event) { if (deathGuardian)
	 * { beneficiary = true; } else { beneficiary = false; } }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_CLAIM_SURVEY,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public void setSaleMan(boolean saleMan) { this.saleMan = saleMan; }
	 * 
	 * public String onFlowProcess(FlowEvent event) { String formID =
	 * "medicalClaimRequestForm"; boolean valid = true; if
	 * ("generalInfo".equals(event.getOldStep()) &&
	 * "beneficiaryClaimInfo".equals(event.getNewStep())) { if
	 * (medicalClaimProposalDTO.isHospitalized()) { if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getHospitalizedStartDate().after(medicalClaimProposalDTO.
	 * getHospitalizedClaimDTO().getHospitalizedEndDate())) {
	 * addErrorMessage(formID + ":startDate", MessageId.HOSPITALIZED_DATE_INFO);
	 * } } if (medicalClaimProposalDTO.isOperation() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (medicalClaimProposalDTO.getOperationClaimDTO().getOperationDate().after(
	 * medicalClaimProposalDTO.getDeathClaimDTO().getDeathDate())) {
	 * addErrorMessage(formID + ":operationDate",
	 * MessageId.OPERATION_DATE_INFO); } } if
	 * (medicalClaimProposalDTO.isHospitalized() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getHospitalizedStartDate().after(medicalClaimProposalDTO.getDeathClaimDTO
	 * ().getDeathDate())) { addErrorMessage(formID + ":startDate",
	 * MessageId.HOSPITALIZED_STARTDATE_INFO); } if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO().getHospitalizedEndDate
	 * ().after(medicalClaimProposalDTO.getDeathClaimDTO().getDeathDate())) {
	 * addErrorMessage(formID + ":endDate",
	 * MessageId.HOSPITALIZED_ENDDATE_INFO); } } if
	 * (medicalClaimProposalDTO.isMedication() &&
	 * medicalClaimProposalDTO.isDeath()) { if
	 * (medicalClaimProposalDTO.getMedicationClaimDTO().getReceivedDate().after(
	 * medicalClaimProposalDTO.getDeathClaimDTO().getDeathDate())) {
	 * addErrorMessage(formID + ":receivedDate", MessageId.RECEIVED_DATE_INFO);
	 * } }
	 * 
	 * } return valid ? event.getNewStep() : event.getOldStep();
	 * 
	 * }
	 * 
	 * public String submitClaimInfo() { String result = null; try { WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO("", remark, workFlowTask,
	 * ReferenceType.MEDICAL_CLAIM, user, responsiblePerson);
	 * medicalClaimProposalDTO.addClaimBeneficiary(medicalClaimBeneficiaryDTO);
	 * medicalClaimProposalDTO =
	 * medicalClaimRequestFrontService.updatMedicalClaim(
	 * medicalClaimProposalDTO, workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.MEDICAL_ClAIM_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalClaimProposalDTO.getClaimRequestId()); result = "dashboard"; }
	 * catch (SystemException ex) { handelSysException(ex); } return result; }
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
	 * // public void returnHospitalizedReasonDialog(SelectEvent event) { //
	 * ICD10 icd10 = (ICD10) event.getObject(); //
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().setHospitalizedReason(
	 * icd10); // }
	 * 
	 * public void returnMedicalPlaceDialog(SelectEvent event) { Hospital
	 * medicalPlace = (Hospital) event.getObject();
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().setMedicalPlace(
	 * medicalPlace); }
	 * 
	 * public void returnOperationDialog(SelectEvent event) { Operation
	 * operation = (Operation) event.getObject();
	 * medicalClaimProposalDTO.getOperationClaimDTO().setOperation(operation); }
	 * 
	 * public void returnOperationReasonDialog(SelectEvent event) { ICD10 icd10
	 * = (ICD10) event.getObject();
	 * medicalClaimProposalDTO.getOperationClaimDTO().setOperationReason(icd10);
	 * }
	 * 
	 * public void returnMedicationReasonDialog(SelectEvent event) { ICD10 icd10
	 * = (ICD10) event.getObject();
	 * medicalClaimProposalDTO.getMedicationClaimDTO().setMedicationReason(icd10
	 * ); }
	 * 
	 * // public void returnDeathReasonDialog(SelectEvent event) { // ICD10
	 * icd10 = (ICD10) event.getObject(); //
	 * medicalClaimProposalDTO.getDeathClaimDTO().setDeathReason(icd10); // }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public MedicalClaimBeneficiaryDTO getMedicalClaimBeneficiaryDTO() {
	 * return medicalClaimBeneficiaryDTO; }
	 * 
	 * public void setMedicalClaimBeneficiaryDTO(MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiaryDTO) { this.medicalClaimBeneficiaryDTO =
	 * medicalClaimBeneficiaryDTO; }
	 * 
	 * public List<String> getItems() { return items; }
	 * 
	 * public void setItems(List<String> items) { this.items = items; }
	 * 
	 * public Map<String, Boolean> getCheckMap() { return checkMap; }
	 * 
	 * public void setCheckMap(Map<String, Boolean> checkMap) { this.checkMap =
	 * checkMap; }
	 * 
	 * public boolean isDisablelinkBtn() { return disablelinkBtn; }
	 * 
	 * public void setDisablelinkBtn(boolean disablelinkBtn) {
	 * this.disablelinkBtn = disablelinkBtn; }
	 * 
	 * public void selectedDeathPlace() { if
	 * (medicalClaimProposalDTO.getDeathClaimDTO().getDeathPlace().
	 * equalsIgnoreCase("Other")) { other = true; } else other = false; }
	 * 
	 * public boolean isOther() { return other; }
	 * 
	 * public void setOther(boolean other) { this.other = other; }
	 * 
	 * public boolean isBeneficiary() { if (medicalClaimProposalDTO.isDeath()) {
	 * beneficiary = true; } return beneficiary; }
	 * 
	 * public void setBeneficiary(boolean beneficiary) { this.beneficiary =
	 * beneficiary; }
	 * 
	 * public boolean isGuardian() { for (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) if
	 * (mcb.getMedPolicyGuardianDTO() != null) { guardian = true; } return
	 * guardian; }
	 * 
	 * public void setGuardian(boolean guardian) { this.guardian = guardian; }
	 * 
	 * public boolean isSuccessor() { for (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) { if
	 * (mcb.getBeneficiaryRole().equals(MedicalBeneficiaryRole.SUCCESSOR)) {
	 * successor = true; } } return successor; }
	 * 
	 * public void setSuccessor(boolean successor) { this.successor = successor;
	 * }
	 * 
	 * public boolean isInsuredPerson() { for (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) if
	 * (mcb.getMedicalPolicyInsuredPersonDTO() != null) { insuredPerson = true;
	 * } return insuredPerson; }
	 * 
	 * public void setInsuredPerson(boolean insuredPerson) { this.insuredPerson
	 * = insuredPerson; }
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
	 * public List<RelationShip> getRelationShipList() { return
	 * relationShipList; }
	 * 
	 * public void setRelationShipList(List<RelationShip> relationShipList) {
	 * this.relationShipList = relationShipList; }
	 * 
	 * public MedicationClaimDTO getMedicationClaimDTO() { return
	 * medicationClaimDTO; }
	 * 
	 * public void setMedicationClaimDTO(MedicationClaimDTO medicationClaimDTO)
	 * { this.medicationClaimDTO = medicationClaimDTO; }
	 * 
	 * public DeathClaimDTO getDeathClaimDTO() { return deathClaimDTO; }
	 * 
	 * public void setDeathClaimDTO(DeathClaimDTO deathClaimDTO) {
	 * this.deathClaimDTO = deathClaimDTO; }
	 * 
	 * public void
	 * prepareEditDeathBeneficiary(MedicalPolicyInsuredPersonBeneficiaryDTO
	 * medPolInsPerBene) { this.selectedBeneficiaryDTO = medPolInsPerBene; }
	 * 
	 * public void changeClaimType() { if (selectedClaimTypes != null) { if
	 * (!selectedClaimTypes.contains("Hospitalization")) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(null);
	 * medicalClaimProposalDTO.setHospitalized(false); } else { if
	 * (map.get("Hospitalization") != null) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO((HospitalizedClaimDTO)
	 * map.get("Hospitalization")); } else {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(new
	 * HospitalizedClaimDTO()); } medicalClaimProposalDTO.setHospitalized(true);
	 * }
	 * 
	 * if (!selectedClaimTypes.contains("Operation")) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(null);
	 * medicalClaimProposalDTO.setOperation(false); } else { if
	 * (map.get("Operation") != null) {
	 * medicalClaimProposalDTO.setOperationClaimDTO((OperationClaimDTO)
	 * map.get("Operation")); } else {
	 * medicalClaimProposalDTO.setOperationClaimDTO(new OperationClaimDTO()); }
	 * medicalClaimProposalDTO.setOperation(true); } if
	 * (!selectedClaimTypes.contains("Medication")) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(null);
	 * medicalClaimProposalDTO.setMedication(false); } else { if
	 * (map.get("Medication") != null) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO((MedicationClaimDTO)
	 * map.get("Medication")); } else {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(new MedicationClaimDTO());
	 * } medicalClaimProposalDTO.setMedication(true); }
	 * 
	 * if (!selectedClaimTypes.contains("Death")) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(null);
	 * medicalClaimProposalDTO.setDeath(false); } else { if (map.get("Death") !=
	 * null) { medicalClaimProposalDTO.setDeathClaimDTO((DeathClaimDTO)
	 * map.get("Death")); } else { medicalClaimProposalDTO.setDeathClaimDTO(new
	 * DeathClaimDTO()); } medicalClaimProposalDTO.setDeath(true); } } }
	 * 
	 * public void updateDeathBeneficiary() { if
	 * (selectedBeneficiaryDTO.isDeath()) { ValidationResult result =
	 * deathBeneInfoValidator.validate(selectedBeneficiaryDTO); if
	 * (!result.isVerified()) { for (ErrorMessage message :
	 * result.getErrorMeesages()) { addErrorMessage(message); } } else {
	 * RequestContext.getCurrentInstance().execute(
	 * "beneficiaryDeathDialog.hide()"); } } else {
	 * RequestContext.getCurrentInstance().execute(
	 * "beneficiaryDeathDialog.hide()"); } }
	 * 
	 * public List<String> getSelectedClaimTypes() { return selectedClaimTypes;
	 * }
	 * 
	 * public void setSelectedClaimTypes(List<String> selectedClaimTypes) {
	 * this.selectedClaimTypes = selectedClaimTypes; }
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
	 * public void setOperationClaimDTO(OperationClaimDTO operationClaimDTO) {
	 * this.operationClaimDTO = operationClaimDTO; }
	 * 
	 * public boolean isChild() { if
	 * (medicalClaimProposalDTO.getPolicyInsuredPersonDTO().getGuardian() !=
	 * null) { child = true; } return child; }
	 * 
	 * public void setChild(boolean child) { this.child = child; }
	 * 
	 * public boolean isDeathGuardian() { if
	 * (medicalClaimProposalDTO.getPolicyInsuredPersonDTO().getGuardian() !=
	 * null &&
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList().get(0).
	 * getMedPolicyInsuBeneDTO() != null) { deathGuardian = true; } return
	 * deathGuardian; }
	 * 
	 * public void setDeathGuardian(boolean deathGuardian) { this.deathGuardian
	 * = deathGuardian; }
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
	 * public boolean isExistBeneficiary() { return existBeneficiary; }
	 * 
	 * public void setExistBeneficiary(boolean existBeneficiary) {
	 * this.existBeneficiary = existBeneficiary; }
	 * 
	 * public WorkflowTask getWorkFlowTask() { return workFlowTask; }
	 * 
	 * public void setWorkFlowTask(WorkflowTask workFlowTask) {
	 * this.workFlowTask = workFlowTask; }
	 */}
