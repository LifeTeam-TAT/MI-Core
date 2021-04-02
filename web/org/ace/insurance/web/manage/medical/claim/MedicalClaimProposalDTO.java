package org.ace.insurance.web.manage.medical.claim;

import org.ace.insurance.web.common.CommonDTO;

public class MedicalClaimProposalDTO extends CommonDTO {
	// TODO FIXME PSH Claim Case
	/*
	 * private String id; private String claimRequestId; private Date
	 * submittedDate; private String policyNo; private
	 * MedicalPolicyInsuredPersonDTO policyInsuredPersonDTO; private Branch
	 * branch; private SaleMan saleman; private Agent agent; private boolean
	 * hospitalized; private boolean medication; private boolean death; private
	 * boolean operation; private HospitalizedClaimDTO hospitalizedClaimDTO;
	 * private MedicationClaimDTO medicationClaimDTO; private DeathClaimDTO
	 * deathClaimDTO; private OperationClaimDTO operationClaimDTO; private
	 * List<MedicalClaimAttachmentDTO> medicalClaimAttachmentList; private
	 * List<MedicalClaimBeneficiaryDTO> medicalClaimBeneficiariesList; private
	 * MedicalClaimSurveyDTO medicalClaimSurvey; private List<MedicalClaimDTO>
	 * medicalClaimList; private int version; private boolean exitEntity;
	 * private double totalHospAmt; private double totalOperAmt; private double
	 * totalMediAmt; private double totalDeathAmt; private double
	 * totalAllBeneAmt; private int daysOfHospitalization;
	 * 
	 * private String claimReportNo;
	 * 
	 * public MedicalClaimProposalDTO() { }
	 * 
	 * public String getPolicyNo() { return policyNo; }
	 * 
	 * public void setPolicyNo(String policyNo) { this.policyNo = policyNo; }
	 * 
	 * public boolean isExitEntity() { return exitEntity; }
	 * 
	 * public List<MedicalClaimDTO> getMedicalClaimList() { return
	 * medicalClaimList; }
	 * 
	 * public void setMedicalClaimList(List<MedicalClaimDTO> medicalClaimList) {
	 * this.medicalClaimList = medicalClaimList; }
	 * 
	 * public void setExitEntity(boolean exitEntity) { this.exitEntity =
	 * exitEntity; }
	 * 
	 * public MedicalPolicyInsuredPersonDTO getPolicyInsuredPersonDTO() { return
	 * policyInsuredPersonDTO; }
	 * 
	 * public void setPolicyInsuredPersonDTO(MedicalPolicyInsuredPersonDTO
	 * policyInsuredPersonDTO) { this.policyInsuredPersonDTO =
	 * policyInsuredPersonDTO; }
	 * 
	 * public String getId() { return id; }
	 * 
	 * public String getClaimRequestId() { return claimRequestId; }
	 * 
	 * public Date getSubmittedDate() { return submittedDate; }
	 * 
	 * public Branch getBranch() { return branch; }
	 * 
	 * public SaleMan getSaleman() { return saleman; }
	 * 
	 * public Agent getAgent() { return agent; }
	 * 
	 * public List<MedicalClaimAttachmentDTO> getMedicalClaimAttachmentList() {
	 * return medicalClaimAttachmentList; }
	 * 
	 * public List<MedicalClaimBeneficiaryDTO>
	 * getMedicalClaimBeneficiariesList() { return
	 * medicalClaimBeneficiariesList; }
	 * 
	 * public int getVersion() { return version; }
	 * 
	 * public void setId(String id) { this.id = id; }
	 * 
	 * public void setClaimRequestId(String claimRequestId) {
	 * this.claimRequestId = claimRequestId; }
	 * 
	 * public void setSubmittedDate(Date submittedDate) { this.submittedDate =
	 * submittedDate; }
	 * 
	 * public void setBranch(Branch branch) { this.branch = branch; }
	 * 
	 * public void setSaleman(SaleMan saleman) { this.saleman = saleman; }
	 * 
	 * public void setAgent(Agent agent) { this.agent = agent; }
	 * 
	 * public HospitalizedClaimDTO getHospitalizedClaimDTO() { return
	 * hospitalizedClaimDTO; }
	 * 
	 * public void setHospitalizedClaimDTO(HospitalizedClaimDTO
	 * hospitalizedClaimDTO) { this.hospitalizedClaimDTO = hospitalizedClaimDTO;
	 * }
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
	 * public OperationClaimDTO getOperationClaimDTO() { return
	 * operationClaimDTO; }
	 * 
	 * public void setOperationClaimDTO(OperationClaimDTO operationClaimDTO) {
	 * this.operationClaimDTO = operationClaimDTO; }
	 * 
	 * public void setMedicalClaimAttachmentList(List<
	 * MedicalClaimAttachmentDTO> medicalClaimAttachmentList) {
	 * this.medicalClaimAttachmentList = medicalClaimAttachmentList; }
	 * 
	 * public void setMedicalClaimBeneficiariesList(List<
	 * MedicalClaimBeneficiaryDTO> medicalClaimBeneficiariesList) {
	 * this.medicalClaimBeneficiariesList = medicalClaimBeneficiariesList; }
	 * 
	 * public void setVersion(int version) { this.version = version; }
	 * 
	 * public void addClaimBeneficiary(MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiary) { if (this.medicalClaimBeneficiariesList ==
	 * null) { this.medicalClaimBeneficiariesList = new
	 * ArrayList<MedicalClaimBeneficiaryDTO>(); }
	 * medicalClaimBeneficiary.setMedicalClaimProposalDTO(this);
	 * this.medicalClaimBeneficiariesList.add( medicalClaimBeneficiary); }
	 * 
	 * public void addClaimAttachment(MedicalClaimAttachmentDTO claimAttachment)
	 * { if (this.medicalClaimAttachmentList == null) {
	 * this.medicalClaimAttachmentList = new
	 * ArrayList<MedicalClaimAttachmentDTO>(); } //
	 * claimAttachment.setMedicalClaimDTO(this);
	 * this.medicalClaimAttachmentList.add(claimAttachment); }
	 * 
	 * public boolean isHospitalized() { return hospitalized; }
	 * 
	 * public void setHospitalized(boolean hospitalized) { this.hospitalized =
	 * hospitalized; }
	 * 
	 * public boolean isMedication() { return medication; }
	 * 
	 * public void setMedication(boolean medication) { this.medication =
	 * medication; }
	 * 
	 * public boolean isDeath() { return death; }
	 * 
	 * public void setDeath(boolean death) { this.death = death; }
	 * 
	 * public boolean isOperation() { return operation; }
	 * 
	 * public void setOperation(boolean operation) { this.operation = operation;
	 * }
	 * 
	 * public MedicalClaimSurveyDTO getMedicalClaimSurvey() { return
	 * medicalClaimSurvey; }
	 * 
	 * public void setMedicalClaimSurvey(MedicalClaimSurveyDTO
	 * medicalClaimSurvey) { this.medicalClaimSurvey = medicalClaimSurvey; }
	 * 
	 * public void addMedicalClaim(MedicalClaimDTO medicalClaimDTO) { if
	 * (medicalClaimList == null) { medicalClaimList = new
	 * ArrayList<MedicalClaimDTO>(); } medicalClaimList.add(medicalClaimDTO); }
	 * 
	 * public double getTotalHospAmt() { totalMediAmt = 0.0; for
	 * (MedicalClaimBeneficiaryDTO dto : medicalClaimBeneficiariesList) {
	 * totalMediAmt += dto.getHospClaimAmount(); } return totalMediAmt; }
	 * 
	 * public void setTotalHospAmt(double totalHospAmt) { this.totalHospAmt =
	 * totalHospAmt; }
	 * 
	 * public double getTotalOperAmt() { totalMediAmt = 0.0; for
	 * (MedicalClaimBeneficiaryDTO dto : medicalClaimBeneficiariesList) {
	 * totalMediAmt += dto.getOperClaimAmount(); } return totalMediAmt; }
	 * 
	 * public void setTotalOperAmt(double totalOperAmt) { this.totalOperAmt =
	 * totalOperAmt; }
	 * 
	 * public double getTotalMediAmt() { totalMediAmt = 0.0; for
	 * (MedicalClaimBeneficiaryDTO dto : medicalClaimBeneficiariesList) {
	 * totalMediAmt += dto.getMediClaimAmount(); } return totalMediAmt; }
	 * 
	 * public void setTotalMediAmt(double totalMediAmt) { this.totalMediAmt =
	 * totalMediAmt; }
	 * 
	 * public double getTotalDeathAmt() { totalMediAmt = 0.0; for
	 * (MedicalClaimBeneficiaryDTO dto : medicalClaimBeneficiariesList) {
	 * totalMediAmt += dto.getDeathClaimAmount(); } return totalMediAmt; }
	 * 
	 * public void setTotalDeathAmt(double totalDeathAmt) { this.totalDeathAmt =
	 * totalDeathAmt; }
	 * 
	 * public double getTotalAllBeneAmt() { totalMediAmt = 0.0; for
	 * (MedicalClaimBeneficiaryDTO dto : medicalClaimBeneficiariesList) {
	 * totalMediAmt += dto.getClaimAmount(); } return totalMediAmt; }
	 * 
	 * public void setTotalAllBeneAmt(double totalAllBeneAmt) {
	 * this.totalAllBeneAmt = totalAllBeneAmt; }
	 * 
	 * public int getDaysOfHospitalization() { if
	 * (hospitalizedClaimDTO.getHospitalizedStartDate() != null &&
	 * hospitalizedClaimDTO.getHospitalizedEndDate() != null) {
	 * daysOfHospitalization = Utils.daysBetween(hospitalizedClaimDTO.
	 * getHospitalizedStartDate(),
	 * hospitalizedClaimDTO.getHospitalizedEndDate(), true, true); } return
	 * daysOfHospitalization; }
	 * 
	 * public void setDaysOfHospitalization(int daysOfHospitalization) {
	 * this.daysOfHospitalization = daysOfHospitalization; }
	 * 
	 * public String getClaimReportNo() { return claimReportNo; }
	 * 
	 * public void setClaimReportNo(String claimReportNo) { this.claimReportNo =
	 * claimReportNo; }
	 * 
	 * public String getSalePersonName() { String fullName = ""; if (this.agent
	 * != null) { fullName = fullName + this.agent.getFullName(); } else if
	 * (this.saleman != null) { fullName = fullName +
	 * this.saleman.getFullName(); }
	 * 
	 * return fullName; }
	 * 
	 * @Override public int hashCode() { final int prime = 31; int result =
	 * super.hashCode(); result = prime * result + ((agent == null) ? 0 :
	 * agent.hashCode()); result = prime * result + ((branch == null) ? 0 :
	 * branch.hashCode()); result = prime * result + ((claimReportNo == null) ?
	 * 0 : claimReportNo.hashCode()); result = prime * result + ((claimRequestId
	 * == null) ? 0 : claimRequestId.hashCode()); result = prime * result +
	 * daysOfHospitalization; result = prime * result + (death ? 1231 : 1237);
	 * result = prime * result + ((deathClaimDTO == null) ? 0 :
	 * deathClaimDTO.hashCode()); result = prime * result + (exitEntity ? 1231 :
	 * 1237); result = prime * result + (hospitalized ? 1231 : 1237); result =
	 * prime * result + ((hospitalizedClaimDTO == null) ? 0 :
	 * hospitalizedClaimDTO.hashCode()); result = prime * result + ((id == null)
	 * ? 0 : id.hashCode()); result = prime * result + ((medicalClaimSurvey ==
	 * null) ? 0 : medicalClaimSurvey.hashCode()); result = prime * result +
	 * (medication ? 1231 : 1237); result = prime * result +
	 * ((medicationClaimDTO == null) ? 0 : medicationClaimDTO.hashCode());
	 * result = prime * result + (operation ? 1231 : 1237); result = prime *
	 * result + ((operationClaimDTO == null) ? 0 :
	 * operationClaimDTO.hashCode()); result = prime * result +
	 * ((policyInsuredPersonDTO == null) ? 0 :
	 * policyInsuredPersonDTO.hashCode()); result = prime * result + ((saleman
	 * == null) ? 0 : saleman.hashCode()); result = prime * result +
	 * ((submittedDate == null) ? 0 : submittedDate.hashCode()); long temp; temp
	 * = Double.doubleToLongBits(totalAllBeneAmt); result = prime * result +
	 * (int) (temp ^ (temp >>> 32)); temp =
	 * Double.doubleToLongBits(totalDeathAmt); result = prime * result + (int)
	 * (temp ^ (temp >>> 32)); temp = Double.doubleToLongBits(totalHospAmt);
	 * result = prime * result + (int) (temp ^ (temp >>> 32)); temp =
	 * Double.doubleToLongBits(totalMediAmt); result = prime * result + (int)
	 * (temp ^ (temp >>> 32)); temp = Double.doubleToLongBits(totalOperAmt);
	 * result = prime * result + (int) (temp ^ (temp >>> 32)); result = prime *
	 * result + version; return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (!super.equals(obj)) return false; if (getClass() !=
	 * obj.getClass()) return false; MedicalClaimProposalDTO other =
	 * (MedicalClaimProposalDTO) obj; if (agent == null) { if (other.agent !=
	 * null) return false; } else if (!agent.equals(other.agent)) return false;
	 * if (branch == null) { if (other.branch != null) return false; } else if
	 * (!branch.equals(other.branch)) return false; if (claimReportNo == null) {
	 * if (other.claimReportNo != null) return false; } else if
	 * (!claimReportNo.equals(other.claimReportNo)) return false; if
	 * (claimRequestId == null) { if (other.claimRequestId != null) return
	 * false; } else if (!claimRequestId.equals(other.claimRequestId)) return
	 * false; if (daysOfHospitalization != other.daysOfHospitalization) return
	 * false; if (death != other.death) return false; if (deathClaimDTO == null)
	 * { if (other.deathClaimDTO != null) return false; } else if
	 * (!deathClaimDTO.equals(other.deathClaimDTO)) return false; if (exitEntity
	 * != other.exitEntity) return false; if (hospitalized !=
	 * other.hospitalized) return false; if (hospitalizedClaimDTO == null) { if
	 * (other.hospitalizedClaimDTO != null) return false; } else if
	 * (!hospitalizedClaimDTO.equals(other.hospitalizedClaimDTO) ) return false;
	 * if (id == null) { if (other.id != null) return false; } else if
	 * (!id.equals(other.id)) return false; if (medicalClaimSurvey == null) { if
	 * (other.medicalClaimSurvey != null) return false; } else if
	 * (!medicalClaimSurvey.equals(other.medicalClaimSurvey)) return false;
	 * 
	 * if (medication != other.medication) return false; if (medicationClaimDTO
	 * == null) { if (other.medicationClaimDTO != null) return false; } else if
	 * (!medicationClaimDTO.equals(other.medicationClaimDTO)) return false; if
	 * (operation != other.operation) return false; if (operationClaimDTO ==
	 * null) { if (other.operationClaimDTO != null) return false; } else if
	 * (!operationClaimDTO.equals(other.operationClaimDTO)) return false; if
	 * (policyInsuredPersonDTO == null) { if (other.policyInsuredPersonDTO !=
	 * null) return false; } else if (!policyInsuredPersonDTO.equals(other.
	 * policyInsuredPersonDTO)) return false; if (saleman == null) { if
	 * (other.saleman != null) return false; } else if
	 * (!saleman.equals(other.saleman)) return false; if (submittedDate == null)
	 * { if (other.submittedDate != null) return false; } else if
	 * (!submittedDate.equals(other.submittedDate)) return false; if
	 * (Double.doubleToLongBits(totalAllBeneAmt) !=
	 * Double.doubleToLongBits(other.totalAllBeneAmt)) return false;
	 * 
	 * if (Double.doubleToLongBits(totalDeathAmt) !=
	 * Double.doubleToLongBits(other.totalDeathAmt)) return false; if
	 * (Double.doubleToLongBits(totalHospAmt) !=
	 * Double.doubleToLongBits(other.totalHospAmt)) return false; if
	 * (Double.doubleToLongBits(totalMediAmt) !=
	 * Double.doubleToLongBits(other.totalMediAmt)) return false; if
	 * (Double.doubleToLongBits(totalOperAmt) !=
	 * Double.doubleToLongBits(other.totalOperAmt)) return false; if (version !=
	 * other.version) return false; return true; }
	 */
}