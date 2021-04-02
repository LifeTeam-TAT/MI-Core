package org.ace.insurance.managementreport.lifepolicyreport.service;

import javax.annotation.Resource;

import org.ace.insurance.managementreport.lifepolicyreport.LifeProductOverview;
import org.ace.insurance.managementreport.lifepolicyreport.persistence.interfaces.ILifeProductOverviewDAO;
import org.ace.insurance.managementreport.lifepolicyreport.service.interfaces.ILifeProductOverviewService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeProductOverviewService")
public class LifeProductOverviewService implements ILifeProductOverviewService {

	private Logger logger = LogManager.getLogger(this.getClass());
	@Resource(name = "LifeProductOverviewDAO")
	private ILifeProductOverviewDAO lifeProductOverviewDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByTownship() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByTownship();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByProductType() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByProductType();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByGender() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByGender();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByMonth(int year) {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByMonth(year);
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByPaymentType() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByPaymentType();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByChannel() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByChannel();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyByAge() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyByAge();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProductOverview findLifePolicyBySIAge() {
		LifeProductOverview result = null;
		try {
			logger.debug("findFinancialReport() method has been started.");
			result = lifeProductOverviewDAO.findLifePolicyBySIAge();
			logger.debug("findFinancialReport() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("findFinancialReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find SummeryReport by criteria.", e);
		}
		return result;
	}

}
