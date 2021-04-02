package org.ace.insurance.web.manage.medical.claim;

import org.ace.insurance.web.common.CommonDTO;

public class MedicalClaimBeneficiaryDTO extends CommonDTO {
	// TODO FIXME PSH Claim Case
	/*
	 * private String id; private String beneficiaryNo; private String name;
	 * private String nrc; private boolean death; private float percentage;
	 * private double claimAmount; private double hospClaimAmount; private
	 * double operClaimAmount; private double mediClaimAmount; private double
	 * deathClaimAmount; private double deficitPremium; private String
	 * beneficiaryRole; private ResidentAddress residentAddress; private int
	 * noOfHospDays; private int unit; private double total; // TODO FIXME PSH
	 * // private ClaimStatus claimStatus; private Payment payment; private
	 * MedicalClaimProposalDTO medicalClaimProposalDTO; private
	 * MedicalPolicyInsuredPersonDTO medicalPolicyInsuredPersonDTO; private
	 * Customer customer; private MedicalPolicyInsuredPersonBeneficiaryDTO
	 * medPolicyInsuBeneDTO; private PolicyGuardianDTO medPolicyGuardianDTO;
	 * private RelationShip relationShip; private String relationShipName;
	 * private int version; private boolean exitEntity;
	 * 
	 * public MedicalClaimBeneficiaryDTO() { }
	 * 
	 * public String getId() { return id; }
	 * 
	 * public void setId(String id) { this.id = id; }
	 * 
	 * public String getBeneficiaryNo() { return beneficiaryNo; }
	 * 
	 * public void setBeneficiaryNo(String beneficiaryNo) { this.beneficiaryNo =
	 * beneficiaryNo; }
	 * 
	 * public String getName() { if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.INSURED_PERSON)) { name =
	 * medicalPolicyInsuredPersonDTO.getFullName(); } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.BENEFICIARY)) { name =
	 * medPolicyInsuBeneDTO.getName().getFullName(); } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.GUARDIAN)) { name =
	 * medPolicyGuardianDTO.getCustomerDTO().getName().getFullName(); } return
	 * name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 * 
	 * public String getNrc() { if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.INSURED_PERSON)) { nrc =
	 * medicalPolicyInsuredPersonDTO.getIdNo(); } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.BENEFICIARY)) { nrc =
	 * medPolicyInsuBeneDTO.getIdNo(); } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.GUARDIAN)) { nrc =
	 * medPolicyGuardianDTO.getCustomerDTO().getIdNo(); } return nrc; }
	 * 
	 * public void setNrc(String nrc) { this.nrc = nrc; }
	 * 
	 * public ResidentAddress getResidentAddress() { if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.INSURED_PERSON)) {
	 * residentAddress = medicalPolicyInsuredPersonDTO.getResidentAddress(); }
	 * else if (beneficiaryRole.equals(MedicalBeneficiaryRole.BENEFICIARY)) {
	 * residentAddress = medPolicyInsuBeneDTO.getResidentAddress(); } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.GUARDIAN)) {
	 * residentAddress =
	 * medPolicyGuardianDTO.getCustomerDTO().getResidentAddress(); } return
	 * residentAddress; }
	 * 
	 * public void setResidentAddress(ResidentAddress residentAddress) {
	 * this.residentAddress = residentAddress; }
	 * 
	 * public boolean isDeath() { return death; }
	 * 
	 * public void setDeath(boolean death) { this.death = death; }
	 * 
	 * public float getPercentage() { return percentage; }
	 * 
	 * public void setPercentage(float percentage) { this.percentage =
	 * percentage; }
	 * 
	 * public double getClaimAmount() { return claimAmount; }
	 * 
	 * public void setClaimAmount(double claimAmount) { this.claimAmount =
	 * claimAmount; }
	 * 
	 * public double getHospClaimAmount() { return hospClaimAmount; }
	 * 
	 * public void setHospClaimAmount(double hospClaimAmount) {
	 * this.hospClaimAmount = hospClaimAmount; }
	 * 
	 * public double getOperClaimAmount() { return operClaimAmount; }
	 * 
	 * public void setOperClaimAmount(double operClaimAmount) {
	 * this.operClaimAmount = operClaimAmount; }
	 * 
	 * public double getMediClaimAmount() { return mediClaimAmount; }
	 * 
	 * public void setMediClaimAmount(double mediClaimAmount) {
	 * this.mediClaimAmount = mediClaimAmount; }
	 * 
	 * public double getDeathClaimAmount() { return deathClaimAmount; }
	 * 
	 * public void setDeathClaimAmount(double deathClaimAmount) {
	 * this.deathClaimAmount = deathClaimAmount; }
	 * 
	 * public double getDeficitPremium() { return deficitPremium; }
	 * 
	 * public void setDeficitPremium(double deficitPremium) {
	 * this.deficitPremium = deficitPremium; }
	 * 
	 * public String getBeneficiaryRole() { return beneficiaryRole; }
	 * 
	 * public void setBeneficiaryRole(String beneficiaryRole) {
	 * this.beneficiaryRole = beneficiaryRole; }
	 * 
	 * public int getNoOfHospDays() { return noOfHospDays; }
	 * 
	 * public void setNoOfHospDays(int noOfHospDays) { this.noOfHospDays =
	 * noOfHospDays; }
	 * 
	 * public int getUnit() { return unit; }
	 * 
	 * public void setUnit(int unit) { this.unit = unit; }
	 * 
	 * public double getTotal() { return total; }
	 * 
	 * public void setTotal(double total) { this.total = total; }
	 * 
	 * public ClaimStatus getClaimStatus() { return claimStatus; }
	 * 
	 * public void setClaimStatus(ClaimStatus claimStatus) { this.claimStatus =
	 * claimStatus; }
	 * 
	 * public Payment getPayment() { return payment; }
	 * 
	 * public void setPayment(Payment payment) { this.payment = payment; }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public MedicalPolicyInsuredPersonDTO getMedicalPolicyInsuredPersonDTO() {
	 * return medicalPolicyInsuredPersonDTO; }
	 * 
	 * public void
	 * setMedicalPolicyInsuredPersonDTO(MedicalPolicyInsuredPersonDTO
	 * medicalPolicyInsuredPersonDTO) { this.medicalPolicyInsuredPersonDTO =
	 * medicalPolicyInsuredPersonDTO; }
	 * 
	 * public Customer getCustomer() { return customer; }
	 * 
	 * public void setCustomer(Customer customer) { this.customer = customer; }
	 * 
	 * public MedicalPolicyInsuredPersonBeneficiaryDTO getMedPolicyInsuBeneDTO()
	 * { return medPolicyInsuBeneDTO; }
	 * 
	 * public void
	 * setMedPolicyInsuBeneDTO(MedicalPolicyInsuredPersonBeneficiaryDTO
	 * medPolicyInsuBeneDTO) { this.medPolicyInsuBeneDTO = medPolicyInsuBeneDTO;
	 * }
	 * 
	 * public PolicyGuardianDTO getMedPolicyGuardianDTO() { return
	 * medPolicyGuardianDTO; }
	 * 
	 * public void setMedPolicyGuardianDTO(PolicyGuardianDTO
	 * medPolicyGuardianDTO) { this.medPolicyGuardianDTO = medPolicyGuardianDTO;
	 * }
	 * 
	 * public RelationShip getRelationShip() { return relationShip; }
	 * 
	 * public void setRelationShip(RelationShip relationShip) {
	 * this.relationShip = relationShip; }
	 * 
	 * public String getRelationShipName() { if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.INSURED_PERSON)) {
	 * relationShipName = "SELF"; } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.BENEFICIARY)) { if
	 * (medPolicyInsuBeneDTO.getRelationship() == null) { relationShipName = "";
	 * } else { relationShipName =
	 * medPolicyInsuBeneDTO.getRelationship().getName(); } } else if
	 * (beneficiaryRole.equals(MedicalBeneficiaryRole.GUARDIAN)) { if
	 * (medPolicyGuardianDTO.getRelationship() == null) { relationShipName = "";
	 * } else { relationShipName =
	 * medPolicyGuardianDTO.getRelationship().getName(); } } else {
	 * relationShipName = this.getRelationShip().getName(); } return
	 * relationShipName; }
	 * 
	 * public void setRelationShipName(String relationShipName) {
	 * this.relationShipName = relationShipName; }
	 * 
	 * public int getVersion() { return version; }
	 * 
	 * public void setVersion(int version) { this.version = version; }
	 * 
	 * public boolean isExitEntity() { return exitEntity; }
	 * 
	 * public void setExitEntity(boolean exitEntity) { this.exitEntity =
	 * exitEntity; }
	 * 
	 * @Override public int hashCode() { final int prime = 31; int result =
	 * super.hashCode(); result = prime * result + ((beneficiaryNo == null) ? 0
	 * : beneficiaryNo.hashCode()); result = prime * result + ((beneficiaryRole
	 * == null) ? 0 : beneficiaryRole.hashCode()); long temp; temp =
	 * Double.doubleToLongBits(claimAmount); result = prime * result + (int)
	 * (temp ^ (temp >>> 32)); result = prime * result + ((claimStatus == null)
	 * ? 0 : claimStatus.hashCode()); result = prime * result + ((customer ==
	 * null) ? 0 : customer.hashCode()); result = prime * result + (death ? 1231
	 * : 1237); temp = Double.doubleToLongBits(deathClaimAmount); result = prime
	 * * result + (int) (temp ^ (temp >>> 32)); temp =
	 * Double.doubleToLongBits(deficitPremium); result = prime * result + (int)
	 * (temp ^ (temp >>> 32)); result = prime * result + (exitEntity ? 1231 :
	 * 1237); temp = Double.doubleToLongBits(hospClaimAmount); result = prime *
	 * result + (int) (temp ^ (temp >>> 32)); result = prime * result + ((id ==
	 * null) ? 0 : id.hashCode()); result = prime * result +
	 * ((medPolicyGuardianDTO == null) ? 0 : medPolicyGuardianDTO.hashCode());
	 * result = prime * result + ((medPolicyInsuBeneDTO == null) ? 0 :
	 * medPolicyInsuBeneDTO.hashCode()); temp =
	 * Double.doubleToLongBits(mediClaimAmount); result = prime * result + (int)
	 * (temp ^ (temp >>> 32)); result = prime * result +
	 * ((medicalClaimProposalDTO == null) ? 0 :
	 * medicalClaimProposalDTO.hashCode()); result = prime * result +
	 * ((medicalPolicyInsuredPersonDTO == null) ? 0 :
	 * medicalPolicyInsuredPersonDTO.hashCode()); result = prime * result +
	 * ((name == null) ? 0 : name.hashCode()); result = prime * result +
	 * noOfHospDays; result = prime * result + ((nrc == null) ? 0 :
	 * nrc.hashCode()); temp = Double.doubleToLongBits(operClaimAmount); result
	 * = prime * result + (int) (temp ^ (temp >>> 32)); result = prime * result
	 * + ((payment == null) ? 0 : payment.hashCode()); result = prime * result +
	 * Float.floatToIntBits(percentage); result = prime * result +
	 * ((relationShip == null) ? 0 : relationShip.hashCode()); result = prime *
	 * result + ((relationShipName == null) ? 0 : relationShipName.hashCode());
	 * result = prime * result + ((residentAddress == null) ? 0 :
	 * residentAddress.hashCode()); temp = Double.doubleToLongBits(total);
	 * result = prime * result + (int) (temp ^ (temp >>> 32)); result = prime *
	 * result + unit; result = prime * result + version; return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (!super.equals(obj)) return false; if (getClass() !=
	 * obj.getClass()) return false; MedicalClaimBeneficiaryDTO other =
	 * (MedicalClaimBeneficiaryDTO) obj; if (beneficiaryNo == null) { if
	 * (other.beneficiaryNo != null) return false; } else if
	 * (!beneficiaryNo.equals(other.beneficiaryNo)) return false; if
	 * (beneficiaryRole == null) { if (other.beneficiaryRole != null) return
	 * false; } else if (!beneficiaryRole.equals(other.beneficiaryRole)) return
	 * false; if (Double.doubleToLongBits(claimAmount) !=
	 * Double.doubleToLongBits(other.claimAmount)) return false; if (claimStatus
	 * != other.claimStatus) return false; if (customer == null) { if
	 * (other.customer != null) return false; } else if
	 * (!customer.equals(other.customer)) return false; if (death !=
	 * other.death) return false; if (Double.doubleToLongBits(deathClaimAmount)
	 * != Double.doubleToLongBits(other.deathClaimAmount)) return false; if
	 * (Double.doubleToLongBits(deficitPremium) !=
	 * Double.doubleToLongBits(other.deficitPremium)) return false; if
	 * (exitEntity != other.exitEntity) return false; if
	 * (Double.doubleToLongBits(hospClaimAmount) !=
	 * Double.doubleToLongBits(other.hospClaimAmount)) return false; if (id ==
	 * null) { if (other.id != null) return false; } else if
	 * (!id.equals(other.id)) return false; if (medPolicyGuardianDTO == null) {
	 * if (other.medPolicyGuardianDTO != null) return false; } else if
	 * (!medPolicyGuardianDTO.equals(other.medPolicyGuardianDTO)) return false;
	 * if (medPolicyInsuBeneDTO == null) { if (other.medPolicyInsuBeneDTO !=
	 * null) return false; } else if
	 * (!medPolicyInsuBeneDTO.equals(other.medPolicyInsuBeneDTO)) return false;
	 * if (Double.doubleToLongBits(mediClaimAmount) !=
	 * Double.doubleToLongBits(other.mediClaimAmount)) return false; if
	 * (medicalClaimProposalDTO == null) { if (other.medicalClaimProposalDTO !=
	 * null) return false; } else if
	 * (!medicalClaimProposalDTO.equals(other.medicalClaimProposalDTO)) return
	 * false; if (medicalPolicyInsuredPersonDTO == null) { if
	 * (other.medicalPolicyInsuredPersonDTO != null) return false; } else if
	 * (!medicalPolicyInsuredPersonDTO.equals(other.
	 * medicalPolicyInsuredPersonDTO)) return false; if (name == null) { if
	 * (other.name != null) return false; } else if (!name.equals(other.name))
	 * return false; if (noOfHospDays != other.noOfHospDays) return false; if
	 * (nrc == null) { if (other.nrc != null) return false; } else if
	 * (!nrc.equals(other.nrc)) return false; if
	 * (Double.doubleToLongBits(operClaimAmount) !=
	 * Double.doubleToLongBits(other.operClaimAmount)) return false; if (payment
	 * == null) { if (other.payment != null) return false; } else if
	 * (!payment.equals(other.payment)) return false; if
	 * (Float.floatToIntBits(percentage) !=
	 * Float.floatToIntBits(other.percentage)) return false; if (relationShip ==
	 * null) { if (other.relationShip != null) return false; } else if
	 * (!relationShip.equals(other.relationShip)) return false; if
	 * (relationShipName == null) { if (other.relationShipName != null) return
	 * false; } else if (!relationShipName.equals(other.relationShipName))
	 * return false; if (residentAddress == null) { if (other.residentAddress !=
	 * null) return false; } else if
	 * (!residentAddress.equals(other.residentAddress)) return false; if
	 * (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
	 * return false; if (unit != other.unit) return false; if (version !=
	 * other.version) return false; return true; }
	 * 
	 */}
