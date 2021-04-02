package org.ace.insurance.life.claim.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.persistence.interfaces.IClaimMedicalFeesDAO;
import org.ace.insurance.life.claim.service.interfaces.IClaimMedicalFeesService;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("ClaimMedicalFeesService")
public class ClaimMedicalFeesService implements IClaimMedicalFeesService {

	@Resource(name = "ClaimMedicalFeesDAO")
	private IClaimMedicalFeesDAO dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void insert(ClaimMedicalFees claimMedicalFees) {
		try {
			dao.insert(claimMedicalFees);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a  claimMedicalFees", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(ClaimMedicalFees claimMedicalFees) {
		try {
			dao.delete(claimMedicalFees);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a  claimMedicalFees", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(ClaimMedicalFees claimMedicalFees) {
		try {
			dao.update(claimMedicalFees);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a  claimMedicalFees", e);
		} // TODO Auto-generated method stub

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<ClaimMedicalFees> findMedicalFeesBySanctionNo(String sanctionNo) {
		List<ClaimMedicalFees> result = null;
		try {
			result = dao.findMedicalFeesBySanctionNo(sanctionNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a  claimMedicalFees", e);
		} // TODO Auto-generated method stub
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateMedicalFeesStaus(List<LifeClaimMedicalFeeDTO> claimMedicalFeeInfoList) {
		try {
			dao.updateMedicalFeesStaus(claimMedicalFeeInfoList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a  claimMedicalFees", e);
		} // TODO A
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<ClaimMedicalFees> findMedicalFeesByInvoiceNo(String invoiceNo) {
		List<ClaimMedicalFees> result = null;
		try {
			result = dao.findMedicalFeesByInvoiceNo(invoiceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a  claimMedicalFees", e);
		}
		return result;
	}

}
