package org.ace.insurance.medical.claim.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.ClaimInitialReport;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimInitialRepDAO;
import org.ace.insurance.medical.claim.service.interfaces.IClaimInitialReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ClaimInitialReportService")
public class ClaimInitialReportService extends BaseService implements IClaimInitialReportService {

	@Resource(name = "MedicalClaimInitialRepDAO")
	private IMedicalClaimInitialRepDAO claimInitialReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimInitialReport> findAllClaimInitialReport() {
		List<ClaimInitialReport> result = null;
		try {
			result = claimInitialReportDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ClaimInitialReport)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimInitialReport> findAllActiveClaim() {
		List<ClaimInitialReport> result = null;
		try {
			result = claimInitialReportDAO.findAllActiveClaim();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ClaimInitialReport)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimInitialReport findByPolicyInsuredPersonId(String id) {
		ClaimInitialReport result = null;
		try {
			result = claimInitialReportDAO.findByPolicyInsuredPersonId(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ClaimInitialReport)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewClaimInitialReport(ClaimInitialReport claimInitialReport) {
		try {
			claimInitialReportDAO.insert(claimInitialReport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimInitialReport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateClaimInitialReport(ClaimInitialReport claimInitialReport) {
		try {
			claimInitialReportDAO.update(claimInitialReport);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimInitialReport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateByInsuredPerson(String id, ClaimStatus claimStatus) {
		try {
			claimInitialReportDAO.updateByPolicyInsured(id, claimStatus);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimInitialReport", e);
		}
	}

}
