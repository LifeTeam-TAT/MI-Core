package org.ace.insurance.afpBankDiscountRate.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.afpBankDiscountRate.AFPBankDiscountRate;
import org.ace.insurance.afpBankDiscountRate.AFPR001;
import org.ace.insurance.afpBankDiscountRate.persistence.interfaces.IAFPBankDiscountDAO;
import org.ace.insurance.afpBankDiscountRate.service.interfaces.IAFPBankDiscountRateService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AFPBankDiscountRateService")
public class AFPBankDiscountRateService extends BaseService implements IAFPBankDiscountRateService {

	@Resource(name = "AFPBankDiscountDAO")
	private IAFPBankDiscountDAO afpBankDiscountDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AFPR001> findAFPBankDiscountRateDTOByProductGroupId(String productGroupId) {
		List<AFPR001> afpBankDiscountRateDTOList = new ArrayList<>();
		try {
			afpBankDiscountRateDTOList = afpBankDiscountDAO.findAFPBankDiscountRateDTOByProductGroupId(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AFPBankDiscountRateDTO By ProductGroupId", e);
		}
		return afpBankDiscountRateDTOList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllAFPBankDiscountRateByProductGroup(String id) {
		try {
			afpBankDiscountDAO.deleteAllAFPBankDiscountRateByProductGroup(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete All AFPBankDiscountRateDTO By ProductGroupId", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate) {
		try {
			afpBankDiscountDAO.insertAFPBankDiscountRate(afpBankDiscountRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert AFPBankDiscountRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate) {
		try {
			afpBankDiscountDAO.updateAFPBankDiscountRate(afpBankDiscountRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update AFPBankDiscountRate", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAFPBankDiscountRateById(String afpBankDiscountRateId) {
		try {
			afpBankDiscountDAO.deleteAFPBankDiscountRateById(afpBankDiscountRateId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete AFPBankDiscountRate By Id", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AFPBankDiscountRate findAFPBankDiscountRateById(String id) {
		AFPBankDiscountRate afpBankDiscountRate = null;
		try {
			afpBankDiscountRate = afpBankDiscountDAO.findAFPBankDiscountRateById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AFPBankDiscountRate By Id", e);
		}
		return afpBankDiscountRate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findAFPDiscountRate(String bankId, String productGroupId) {
		double afpBankDiscountRate = 0.0;
		try {
			afpBankDiscountRate = afpBankDiscountDAO.findAFPDiscountRate(bankId, productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AFPBankDiscountRate By Id", e);
		}
		return afpBankDiscountRate;
	}

}
