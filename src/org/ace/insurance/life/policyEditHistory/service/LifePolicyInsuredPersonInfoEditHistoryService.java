package org.ace.insurance.life.policyEditHistory.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.life.policyEditHistory.persistence.interfaces.ILifePolicyInsuredPersonInfoEditHistoryDAO;
import org.ace.insurance.life.policyEditHistory.service.interfaces.ILifePolicyInsuredPersonInfoEditHistoryService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PolicyInsuredPersonInfoEditHistoryService")
public class LifePolicyInsuredPersonInfoEditHistoryService implements ILifePolicyInsuredPersonInfoEditHistoryService {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Resource(name = "PolicyInsuredPersonInfoEditHistoryDAO")
	private ILifePolicyInsuredPersonInfoEditHistoryDAO policyInsuredPersonInfoDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson) {
		try {
			logger.debug("addNewPolicyInsuredPersonInfo() method has been started.");
			policyInsuredPersonInfoDAO.insert(policyInsuredPerson);
			logger.debug("addNewPolicyInsuredPersonInfo() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewPolicyInsuredPersonInfo() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a new PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson) {
		try {
			logger.debug("updatePolicyInsuredPersonInfo() method has been started.");
			policyInsuredPersonInfoDAO.update(policyInsuredPerson);
			logger.debug("updatePolicyInsuredPersonInfo() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updatePolicyInsuredPersonInfo() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to update a PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson) {
		try {
			logger.debug("deletePolicyInsuredPersonInfo() method has been started.");
			policyInsuredPersonInfoDAO.delete(policyInsuredPerson);
			logger.debug("deletePolicyInsuredPersonInfo() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("deletePolicyInsuredPersonInfo() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to delete a PolicyInsuredPersonInfo", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonEditHistory findPolicyInsuredPersonInfoById(String id) {
		PolicyInsuredPersonEditHistory result = null;
		try {
			logger.debug("findPolicyInsuredPersonInfoById() method has been started.");
			result = policyInsuredPersonInfoDAO.findById(id);
			logger.debug("findPolicyInsuredPersonInfoById() method has been started.");
		} catch (DAOException e) {
			logger.error("findPolicyInsuredPersonInfoById() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find a PolicyInsuredPersonInfo (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPersonEditHistory> findAllPolicyInsuredPersonInfo() {
		List<PolicyInsuredPersonEditHistory> result = null;
		try {
			logger.debug("findAllPolicyInsuredPersonInfo() method has been started.");
			result = policyInsuredPersonInfoDAO.findAll();
			logger.debug("findAllPolicyInsuredPersonInfo() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("findAllPolicyInsuredPersonInfo() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find all of PolicyInsuredPersonInfo)", e);
		}
		return result;
	}

}
