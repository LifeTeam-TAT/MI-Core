package org.ace.insurance.web.manage.medical.claim.factory;

public class MedicalClaimProposalFactory {
	// TODO FIXME PSH Claim Case
	/*
	 * public static MedicalClaimProposal
	 * createMedicalClaimProposal(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { MedicalClaimProposal medicalClaimProposal =
	 * new MedicalClaimProposal(); if (medicalClaimProposalDTO.isExitEntity()) {
	 * medicalClaimProposal.setVersion(medicalClaimProposalDTO.getVersion());
	 * medicalClaimProposal.setId(medicalClaimProposalDTO.getId()); }
	 * medicalClaimProposal.setClaimRequestId(medicalClaimProposalDTO.
	 * getClaimRequestId());
	 * medicalClaimProposal.setClaimReportNo(medicalClaimProposalDTO.
	 * getClaimReportNo());
	 * medicalClaimProposal.setBranch(medicalClaimProposalDTO.getBranch());
	 * medicalClaimProposal.setSubmittedDate(medicalClaimProposalDTO.
	 * getSubmittedDate());
	 * medicalClaimProposal.setSaleman(medicalClaimProposalDTO.getSaleman());
	 * medicalClaimProposal.setAgent(medicalClaimProposalDTO.getAgent());
	 * medicalClaimProposal.setHospitalized(medicalClaimProposalDTO.
	 * isHospitalized());
	 * medicalClaimProposal.setMedication(medicalClaimProposalDTO.isMedication()
	 * );
	 * medicalClaimProposal.setOperation(medicalClaimProposalDTO.isOperation());
	 * medicalClaimProposal.setDeath(medicalClaimProposalDTO.isDeath());
	 * medicalClaimProposal.setPolicyNo(medicalClaimProposalDTO.getPolicyNo());
	 * medicalClaimProposal.setMedicalClaimList(null);
	 * medicalClaimProposal.setPolicyInsuredPerson(
	 * MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPerson(
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO())); if
	 * (medicalClaimProposalDTO.getMedicalClaimSurvey() != null) {
	 * medicalClaimProposal.setMedicalClaimSurvey(MedicalClaimSurveyFactory.
	 * createMedicalClaimSurvey(medicalClaimProposalDTO.getMedicalClaimSurvey())
	 * ); }
	 * 
	 * if (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null) {
	 * medicalClaimProposal.addMedicalClaim(HospitalizedClaimFactory.
	 * createHospitalizedClaim(medicalClaimProposalDTO.getHospitalizedClaimDTO()
	 * )); } if (medicalClaimProposalDTO.getDeathClaimDTO() != null) {
	 * medicalClaimProposal.addMedicalClaim(DeathClaimFactory.createDeathClaim(
	 * medicalClaimProposalDTO.getDeathClaimDTO())); } if
	 * (medicalClaimProposalDTO.getOperationClaimDTO() != null) {
	 * medicalClaimProposal.addMedicalClaim(OperationClaimFactory.
	 * createOperationClaim(medicalClaimProposalDTO.getOperationClaimDTO())); }
	 * if (medicalClaimProposalDTO.getMedicationClaimDTO() != null) {
	 * medicalClaimProposal.addMedicalClaim(MedicationClaimFactory.
	 * createMedicationClaim(medicalClaimProposalDTO.getMedicationClaimDTO()));
	 * } if (medicalClaimProposalDTO.getMedicalClaimAttachmentList() != null &&
	 * medicalClaimProposalDTO.getMedicalClaimAttachmentList().size() != 0) {
	 * for (MedicalClaimAttachmentDTO mca :
	 * medicalClaimProposalDTO.getMedicalClaimAttachmentList()) {
	 * medicalClaimProposal.addClaimAttachment(MedicalClaimAttachmentDTOFactory.
	 * getMedicalClaimAttachment(mca)); } } if
	 * (medicalClaimProposalDTO.getMedicalClaimBeneficiariesList() != null &&
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList().size() != 0) {
	 * for (MedicalClaimBeneficiaryDTO b :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) {
	 * medicalClaimProposal.addClaimBeneficiary(MedicalClaimBeneFactoy.
	 * createMedicalClaimBeneficiary(b)); } } if
	 * (medicalClaimProposalDTO.getRecorder() != null) {
	 * medicalClaimProposal.setRecorder(medicalClaimProposalDTO.getRecorder());
	 * } return medicalClaimProposal; }
	 * 
	 * public static MedicalClaimProposalDTO
	 * createMedicalClaimProposalDTO(MedicalClaimProposal medicalClaimProposal)
	 * { MedicalClaimProposalDTO medicalClaimProposalDTO = new
	 * MedicalClaimProposalDTO(); if (medicalClaimProposal.getId() != null &&
	 * (!medicalClaimProposal.getId().isEmpty())) {
	 * medicalClaimProposalDTO.setId(medicalClaimProposal.getId());
	 * medicalClaimProposalDTO.setExitEntity(true);
	 * medicalClaimProposalDTO.setVersion(medicalClaimProposal.getVersion()); }
	 * medicalClaimProposalDTO.setHospitalized(medicalClaimProposal.
	 * isHospitalized());
	 * medicalClaimProposalDTO.setDeath(medicalClaimProposal.isDeath());
	 * medicalClaimProposalDTO.setOperation(medicalClaimProposal.isOperation());
	 * medicalClaimProposalDTO.setMedication(medicalClaimProposal.isMedication()
	 * ); medicalClaimProposalDTO.setClaimRequestId(medicalClaimProposal.
	 * getClaimRequestId());
	 * medicalClaimProposalDTO.setClaimReportNo(medicalClaimProposal.
	 * getClaimReportNo());
	 * medicalClaimProposalDTO.setBranch(medicalClaimProposal.getBranch());
	 * medicalClaimProposalDTO.setSubmittedDate(medicalClaimProposal.
	 * getSubmittedDate());
	 * medicalClaimProposalDTO.setPolicyNo(medicalClaimProposal.getPolicyNo());
	 * medicalClaimProposalDTO.setAgent(medicalClaimProposal.getAgent());
	 * medicalClaimProposalDTO.setSaleman(medicalClaimProposal.getSaleman());
	 * medicalClaimProposalDTO.setPolicyInsuredPersonDTO(
	 * MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPersonDTO(
	 * medicalClaimProposal.getPolicyInsuredPerson()));
	 * 
	 * if (medicalClaimProposal.getMedicalClaimSurvey() != null) {
	 * medicalClaimProposalDTO.setMedicalClaimSurvey(MedicalClaimSurveyFactory.
	 * createMedicalClaimSurveyDTO(medicalClaimProposal.getMedicalClaimSurvey())
	 * ); } if (medicalClaimProposal.getMedicalClaimAttachmentList() != null &&
	 * medicalClaimProposal.getMedicalClaimAttachmentList().size() != 0) { for
	 * (MedicalClaimAttachment mca :
	 * medicalClaimProposal.getMedicalClaimAttachmentList()) {
	 * medicalClaimProposalDTO.addClaimAttachment(
	 * MedicalClaimAttachmentDTOFactory.getMedicalClaimAttachmentDTO(mca)); } }
	 * if (medicalClaimProposal.getMedicalClaimBeneficiariesList() != null &&
	 * medicalClaimProposal.getMedicalClaimBeneficiariesList().size() != 0) {
	 * for (MedicalClaimBeneficiary b :
	 * medicalClaimProposal.getMedicalClaimBeneficiariesList()) {
	 * medicalClaimProposalDTO.addClaimBeneficiary(MedicalClaimBeneFactoy.
	 * createMedicalClaimBeneficiaryDTO(b)); } } if
	 * (medicalClaimProposal.getRecorder() != null) {
	 * medicalClaimProposalDTO.setRecorder(medicalClaimProposal.getRecorder());
	 * } return medicalClaimProposalDTO; }
	 */}