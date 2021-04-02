package org.ace.insurance.life.policy.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyInsuredPersonInfoDAO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyInsuredPersonInfoService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PolicyInsuredPersonInfoService")
public class LifePolicyInsuredPersonInfoService implements ILifePolicyInsuredPersonInfoService {

	@Resource(name = "PolicyInsuredPersonInfoDAO")
	private ILifePolicyInsuredPersonInfoDAO policyInsuredPersonInfoDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.insert(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.update(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a PolicyInsuredPersonInfo", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson) {
		try {
			policyInsuredPersonInfoDAO.delete(policyInsuredPerson);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a PolicyInsuredPersonInfo", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPerson findPolicyInsuredPersonInfoById(String id) {
		PolicyInsuredPerson result = null;
		try {
			result = policyInsuredPersonInfoDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PolicyInsuredPersonInfo (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPerson> findAllPolicyInsuredPersonInfo() {
		List<PolicyInsuredPerson> result = null;
		try {
			result = policyInsuredPersonInfoDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of PolicyInsuredPersonInfo)", e);
		}
		return result;
	}

}
