package org.ace.insurance.web.manage.medical.claim.factory;

public class MedicalClaimBeneFactoy {
	// TODO FIXME Claim Case
	/*
	 * public static MedicalClaimBeneficiary
	 * createMedicalClaimBeneficiary(MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiaryDTO) { MedicalClaimBeneficiary
	 * medicalClaimBeneficiary = new MedicalClaimBeneficiary(); if
	 * (medicalClaimBeneficiaryDTO.isExitEntity()) {
	 * medicalClaimBeneficiary.setVersion(medicalClaimBeneficiaryDTO.getVersion(
	 * )); medicalClaimBeneficiary.setId(medicalClaimBeneficiaryDTO.getId()); }
	 * 
	 * if (medicalClaimBeneficiaryDTO.getBeneficiaryRole().equals(
	 * MedicalBeneficiaryRole.SUCCESSOR)) {
	 * medicalClaimBeneficiary.setName(medicalClaimBeneficiaryDTO.getName()); }
	 * if (medicalClaimBeneficiaryDTO.getBeneficiaryRole().equals(
	 * MedicalBeneficiaryRole.SUCCESSOR)) {
	 * medicalClaimBeneficiary.setNrc(medicalClaimBeneficiaryDTO.getNrc()); } if
	 * (medicalClaimBeneficiaryDTO.getBeneficiaryRole().equals(
	 * MedicalBeneficiaryRole.SUCCESSOR)) {
	 * medicalClaimBeneficiary.setRelationShip(medicalClaimBeneficiaryDTO.
	 * getRelationShip()); }
	 * 
	 * medicalClaimBeneficiary.setClaimAmount(medicalClaimBeneficiaryDTO.
	 * getClaimAmount());
	 * 
	 * medicalClaimBeneficiary.setDeficitPremium(medicalClaimBeneficiaryDTO.
	 * getDeficitPremium());
	 * medicalClaimBeneficiary.setBeneficiaryRole(medicalClaimBeneficiaryDTO.
	 * getBeneficiaryRole());
	 * medicalClaimBeneficiary.setClaimStatus(medicalClaimBeneficiaryDTO.
	 * getClaimStatus());
	 * medicalClaimBeneficiary.setPayment(medicalClaimBeneficiaryDTO.getPayment(
	 * ));
	 * medicalClaimBeneficiary.setUnit(medicalClaimBeneficiaryDTO.getUnit());
	 * medicalClaimBeneficiary.setDeath(medicalClaimBeneficiaryDTO.isDeath());
	 * medicalClaimBeneficiary.setPercentage(medicalClaimBeneficiaryDTO.
	 * getPercentage());
	 * medicalClaimBeneficiary.setBeneficiaryNo(medicalClaimBeneficiaryDTO.
	 * getBeneficiaryNo()); if (medicalClaimBeneficiaryDTO.getNoOfHospDays() !=
	 * 0) { medicalClaimBeneficiary.setNoOfHospDays(medicalClaimBeneficiaryDTO.
	 * getNoOfHospDays()); }
	 * medicalClaimBeneficiary.setHospClaimAmount(medicalClaimBeneficiaryDTO.
	 * getHospClaimAmount());
	 * medicalClaimBeneficiary.setOperClaimAmount(medicalClaimBeneficiaryDTO.
	 * getOperClaimAmount());
	 * medicalClaimBeneficiary.setMediClaimAmount(medicalClaimBeneficiaryDTO.
	 * getMediClaimAmount());
	 * medicalClaimBeneficiary.setDeathClaimAmount(medicalClaimBeneficiaryDTO.
	 * getDeathClaimAmount());
	 * 
	 * if (medicalClaimBeneficiaryDTO.getMedPolicyGuardianDTO() != null) {
	 * medicalClaimBeneficiary.setMedicalPolicyInsuredPersonGuardian(
	 * GuardianFactory.getPolicyGuardian(medicalClaimBeneficiaryDTO.
	 * getMedPolicyGuardianDTO())); } if
	 * (medicalClaimBeneficiaryDTO.getMedPolicyInsuBeneDTO() != null) {
	 * medicalClaimBeneficiary.setMedicalPolicyInsuredPersonBeneficiaries(
	 * MedicalPolicyInsuredPersonBeneficiaryFactory
	 * .createMedicalPolicyInsuredPersonBeneficiary(medicalClaimBeneficiaryDTO.
	 * getMedPolicyInsuBeneDTO())); } if
	 * (medicalClaimBeneficiaryDTO.getMedicalPolicyInsuredPersonDTO() != null) {
	 * medicalClaimBeneficiary.setMedicalPolicyInsuredPerson(
	 * MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPerson(
	 * medicalClaimBeneficiaryDTO .getMedicalPolicyInsuredPersonDTO())); } if
	 * (medicalClaimBeneficiaryDTO.getRecorder() != null) {
	 * medicalClaimBeneficiary.setRecorder(medicalClaimBeneficiaryDTO.
	 * getRecorder()); } return medicalClaimBeneficiary; }
	 * 
	 * public static MedicalClaimBeneficiaryDTO
	 * createMedicalClaimBeneficiaryDTO(MedicalClaimBeneficiary
	 * medicalClaimBeneficiary) { MedicalClaimBeneficiaryDTO
	 * medicalClaimBeneficiaryDTO = new MedicalClaimBeneficiaryDTO(); if
	 * (medicalClaimBeneficiary.getId() != null) {
	 * medicalClaimBeneficiaryDTO.setExitEntity(true);
	 * medicalClaimBeneficiaryDTO.setVersion(medicalClaimBeneficiary.getVersion(
	 * )); medicalClaimBeneficiaryDTO.setId(medicalClaimBeneficiary.getId()); }
	 * 
	 * medicalClaimBeneficiaryDTO.setName(medicalClaimBeneficiary.getName());
	 * medicalClaimBeneficiaryDTO.setNrc(medicalClaimBeneficiary.getNrc());
	 * medicalClaimBeneficiaryDTO.setClaimAmount(medicalClaimBeneficiary.
	 * getClaimAmount());
	 * medicalClaimBeneficiaryDTO.setDeficitPremium(medicalClaimBeneficiary.
	 * getDeficitPremium());
	 * medicalClaimBeneficiaryDTO.setBeneficiaryRole(medicalClaimBeneficiary.
	 * getBeneficiaryRole());
	 * medicalClaimBeneficiaryDTO.setClaimStatus(medicalClaimBeneficiary.
	 * getClaimStatus());
	 * medicalClaimBeneficiaryDTO.setPayment(medicalClaimBeneficiary.getPayment(
	 * ));
	 * medicalClaimBeneficiaryDTO.setUnit(medicalClaimBeneficiary.getUnit());
	 * medicalClaimBeneficiaryDTO.setDeath(medicalClaimBeneficiary.isDeath());
	 * medicalClaimBeneficiaryDTO.setPercentage(medicalClaimBeneficiary.
	 * getPercentage());
	 * medicalClaimBeneficiaryDTO.setBeneficiaryNo(medicalClaimBeneficiary.
	 * getBeneficiaryNo());
	 * medicalClaimBeneficiaryDTO.setNoOfHospDays(medicalClaimBeneficiary.
	 * getNoOfHospDays());
	 * medicalClaimBeneficiaryDTO.setHospClaimAmount(medicalClaimBeneficiary.
	 * getHospClaimAmount());
	 * medicalClaimBeneficiaryDTO.setOperClaimAmount(medicalClaimBeneficiary.
	 * getOperClaimAmount());
	 * medicalClaimBeneficiaryDTO.setMediClaimAmount(medicalClaimBeneficiary.
	 * getMediClaimAmount());
	 * medicalClaimBeneficiaryDTO.setDeathClaimAmount(medicalClaimBeneficiary.
	 * getDeathClaimAmount());
	 * medicalClaimBeneficiaryDTO.setRelationShip(medicalClaimBeneficiary.
	 * getRelationShip());
	 * 
	 * if (medicalClaimBeneficiary.getMedicalPolicyInsuredPersonGuardian() !=
	 * null) {
	 * medicalClaimBeneficiaryDTO.setMedPolicyGuardianDTO(GuardianFactory.
	 * getPolicyGuardianDTO(medicalClaimBeneficiary.
	 * getMedicalPolicyInsuredPersonGuardian())); } if
	 * (medicalClaimBeneficiary.getMedicalPolicyInsuredPersonBeneficiaries() !=
	 * null) { medicalClaimBeneficiaryDTO.setMedPolicyInsuBeneDTO(
	 * MedicalPolicyInsuredPersonBeneficiaryFactory.
	 * createMedicalPolicyInsuredPersonBeneficiaryDTO(medicalClaimBeneficiary
	 * .getMedicalPolicyInsuredPersonBeneficiaries())); } if
	 * (medicalClaimBeneficiary.getMedicalPolicyInsuredPerson() != null) {
	 * medicalClaimBeneficiaryDTO.setMedicalPolicyInsuredPersonDTO(
	 * MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPersonDTO(
	 * medicalClaimBeneficiary .getMedicalPolicyInsuredPerson())); } if
	 * (medicalClaimBeneficiary.getRecorder() != null) {
	 * medicalClaimBeneficiaryDTO.setRecorder(medicalClaimBeneficiary.
	 * getRecorder()); } return medicalClaimBeneficiaryDTO; }
	 */}
