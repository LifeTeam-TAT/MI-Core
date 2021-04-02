package org.ace.insurance.medical.claim.frontService.initialReport.interfaces;

import java.util.List;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimInitialReportDTO;

public interface IMedicalClaimInitialReportFrontService {

	public ClaimInitialReport addNewMedicalClaimInitialRep(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO, List<ICD10> icdList);

	public ClaimInitialReport updateMedicalClaimInitialRep(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO, List<ICD10> icdList);

	public void updateMedicalClaimStatus(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO);

	public void updateByInsuredPersonId(String id, ClaimStatus claimStatus);
}
