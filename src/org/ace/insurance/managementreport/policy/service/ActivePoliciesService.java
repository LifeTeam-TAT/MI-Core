package org.ace.insurance.managementreport.policy.service;

import javax.annotation.Resource;

import org.ace.insurance.managementreport.policy.ActivePolicies;
import org.ace.insurance.managementreport.policy.persistence.interfaces.IActivePoliciesDAO;
import org.ace.insurance.managementreport.policy.service.interfaces.IActivePoliciesService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ActivePoliciesService")
public class ActivePoliciesService implements IActivePoliciesService {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Resource(name = "ActivePoliciesDAO")
	private IActivePoliciesDAO activePoliciesDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findActivePoliciesByProducts() {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findActivePoliciesByProducts();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findTotalSumInsuredByProducts() {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findTotalSumInsuredByProducts();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findTotalPremiumByProducts() {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findTotalPremiumByProducts();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findLifePolicyByTimeLine(int year) {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findLifePolicyByTimeLine(year);
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	// To FIXME by THK
	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findFirePolicyByTimeLine(int year) {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findFirePolicyByTimeLine(year);
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActivePolicies findMotorPolicyByTimeLine(int year) {
		ActivePolicies result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = activePoliciesDAO.findMotorPolicyByTimeLine(year);
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

}
