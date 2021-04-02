package org.ace.insurance.life.policyHistory.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.life.policyHistory.persistence.interfaces.ILifePolicyInsuredPersonInfoHistoryDAO;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyInsuredPersonInfoHistoryService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PolicyInsuredPersonInfoHistoryService")
public class LifePolicyInsuredPersonInfoHistoryService implements ILifePolicyInsuredPersonInfoHistoryService {

	@Resource(name = "PolicyInsuredPersonInfoHistoryDAO")
	private ILifePolicyInsuredPersonInfoHistoryDAO policyInsuredPersonInfoDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.insert(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.update(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.delete(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a PolicyInsuredPersonInfo", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonHistory findPolicyInsuredPersonInfoById(String id) {
		PolicyInsuredPersonHistory result = null;
		try {
			result = policyInsuredPersonInfoDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PolicyInsuredPersonInfo (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPersonHistory> findAllPolicyInsuredPersonInfo() {
		List<PolicyInsuredPersonHistory> result = null;
		try {
			result = policyInsuredPersonInfoDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of PolicyInsuredPersonInfo)", e);
		}
		return result;
	}

}
