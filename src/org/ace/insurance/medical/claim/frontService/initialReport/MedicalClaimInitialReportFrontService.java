package org.ace.insurance.medical.claim.frontService.initialReport;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimInitialReportICD;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.claim.frontService.initialReport.interfaces.IMedicalClaimInitialReportFrontService;
import org.ace.insurance.medical.claim.service.interfaces.IClaimInitialReportService;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.web.manage.medical.claim.MedicalClaimInitialReportDTO;
import org.ace.insurance.web.manage.medical.initialreport.factory.MedicalClaimInitialReportFactory;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalClaimInitialReportFrontService")
public class MedicalClaimInitialReportFrontService extends BaseService implements IMedicalClaimInitialReportFrontService {

	@Resource(name = "ClaimInitialReportService")
	private IClaimInitialReportService claimInitialReportService;

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimInitialReport addNewMedicalClaimInitialRep(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO, List<ICD10> icdList) {
		ClaimInitialReport medicalClaimInitialReport = MedicalClaimInitialReportFactory.createMedicalClaimInitialReport(medicalClaimInitialReportDTO);
		try {
			for (ICD10 icd : icdList) {
				ClaimInitialReportICD claimInitialReportICD = new ClaimInitialReportICD();
				claimInitialReportICD.setIcd10(icd);
				claimInitialReportICD.setClaimInitialReport(medicalClaimInitialReport);
				medicalClaimInitialReport.addClaimInitialReportICD(claimInitialReportICD);
			}
			String claimInitialNo = customIDGenerator.getNextId(SystemConstants.INITIAL_REPORT_NO, null);
			medicalClaimInitialReport.setClaimReportNo(claimInitialNo);
			claimInitialReportService.addNewClaimInitialReport(medicalClaimInitialReport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Death Claim", e);
		}
		return medicalClaimInitialReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimInitialReport updateMedicalClaimInitialRep(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO, List<ICD10> icdList) {
		ClaimInitialReport medicalClaimInitialReport = MedicalClaimInitialReportFactory.createMedicalClaimInitialReport(medicalClaimInitialReportDTO);
		try {
			medicalClaimInitialReport.setClaimInitialReportICDList(null);
			for (ICD10 icd : icdList) {
				ClaimInitialReportICD claimInitialReportICD = new ClaimInitialReportICD();
				claimInitialReportICD.setIcd10(icd);
				claimInitialReportICD.setClaimInitialReport(medicalClaimInitialReport);
				medicalClaimInitialReport.addClaimInitialReportICD(claimInitialReportICD);
			}
			claimInitialReportService.updateClaimInitialReport(medicalClaimInitialReport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Death Claim", e);
		}
		return medicalClaimInitialReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalClaimStatus(MedicalClaimInitialReportDTO medicalClaimInitialReportDTO) {
		ClaimInitialReport medicalClaimInitialReport = MedicalClaimInitialReportFactory.createMedicalClaimInitialReport(medicalClaimInitialReportDTO);
		try {
			claimInitialReportService.updateClaimInitialReport(medicalClaimInitialReport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Deat Claim", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateByInsuredPersonId(String id, ClaimStatus claimStatus) {

		try {
			claimInitialReportService.updateByInsuredPerson(id, claimStatus);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Death Claim", e);
		}
	}

}
