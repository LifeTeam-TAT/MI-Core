package org.ace.insurance.web.manage.medical.initialreport.factory;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimInitialReportDTO;

public class MedicalClaimInitialReportFactory {
	public static ClaimInitialReport createMedicalClaimInitialReport(MedicalClaimInitialReportDTO medClaInitialRepDTO) {
		ClaimInitialReport report = new ClaimInitialReport();
		if (medClaInitialRepDTO.isExistsEntity()) {
			report.setVersion(medClaInitialRepDTO.getVersion());
			report.setId(medClaInitialRepDTO.getId());
		}
		report.setCauseofHospitalized(medClaInitialRepDTO.getCauseofHospitalized());
		report.setClaimReportNo(medClaInitialRepDTO.getClaimReportNo());
		report.setReportDate(medClaInitialRepDTO.getReportDate());
		report.setHospitalizedStartDate(medClaInitialRepDTO.getHospitalizedStartDate());
		report.setAgent(medClaInitialRepDTO.getAgent());
		// TODO FIXME PSH
		// report.setSaleMan(medClaInitialRepDTO.getSaleMan());
		report.setMedicalPlace(medClaInitialRepDTO.getMedicalPlace());
		report.setPolicyNo(medClaInitialRepDTO.getPolicyNo());
		report.setReferral(medClaInitialRepDTO.getReferral());
		// report.setClaimStatus(medClaInitialRepDTO.getClaimStatus());
		report.setPolicyInsuredPerson(medClaInitialRepDTO.getPolicyInsuredPerson());
		if (medClaInitialRepDTO.getClaimInitialReporter() != null) {
			report.setClaimInitialReporter(medClaInitialRepDTO.getClaimInitialReporter());
		}

		if (medClaInitialRepDTO.getClaimInitialReportICDList() != null) {
			report.setClaimInitialReportICDList(medClaInitialRepDTO.getClaimInitialReportICDList());
		}

		// TODO FIXME PSH Claim
		/*
		 * if (medClaInitialRepDTO.getRecorder() != null) {
		 * report.setRecorder(medClaInitialRepDTO. getRecorder()); }
		 */
		return report;
	}

	public static MedicalClaimInitialReportDTO createMedicalClaimInitialReportDTO(ClaimInitialReport medClaInitialRep) {
		MedicalClaimInitialReportDTO report = new MedicalClaimInitialReportDTO();
		if (medClaInitialRep.getId() != null && (!medClaInitialRep.getId().isEmpty())) {
			report.setVersion(medClaInitialRep.getVersion());
			report.setId(medClaInitialRep.getId());
			report.setExistsEntity(true);
		}
		report.setCauseofHospitalized(medClaInitialRep.getCauseofHospitalized());
		report.setClaimReportNo(medClaInitialRep.getClaimReportNo());
		report.setReportDate(medClaInitialRep.getReportDate());
		report.setHospitalizedStartDate(medClaInitialRep.getHospitalizedStartDate());
		report.setAgent(medClaInitialRep.getAgent());
		// TODO FIXME PSH Claim
		// report.setSaleMan(medClaInitialRep.getSaleMan());
		report.setMedicalPlace(medClaInitialRep.getMedicalPlace());
		report.setPolicyNo(medClaInitialRep.getPolicyNo());
		report.setReferral(medClaInitialRep.getReferral());
		report.setPolicyInsuredPerson(medClaInitialRep.getPolicyInsuredPerson());

		if (medClaInitialRep.getClaimInitialReporter() != null) {
			report.setClaimInitialReporter(medClaInitialRep.getClaimInitialReporter());
		}

		if (medClaInitialRep.getClaimInitialReportICDList() != null) {
			report.setClaimInitialReportICDList(medClaInitialRep.getClaimInitialReportICDList());
		}

		// TODO FIXME PSH
		/*
		 * if (medClaInitialRep.getRecorder() != null) {
		 * report.setRecorder(medClaInitialRep. getRecorder()); }
		 */
		return report;
	}
}
