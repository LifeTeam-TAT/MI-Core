package org.ace.insurance.life.policyLog.service;

import javax.annotation.Resource;

import org.ace.insurance.life.policyLog.LifePolicyClaimLog;
import org.ace.insurance.life.policyLog.LifePolicyEndorseLog;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.insurance.life.policyLog.persistence.interfaces.ILifePolicyTimeLineLogDAO;
import org.ace.insurance.life.policyLog.service.interfaces.ILifePolicyTimeLineLogService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("LifePolicyTimeLineLogService")
public class LifePolicyTimeLineLogService implements ILifePolicyTimeLineLogService {
	@Resource(name = "LifePolicyTimeLineLogDAO")
	private ILifePolicyTimeLineLogDAO lifePolicyTimeLineLogDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) {
		try {
			lifePolicyTimeLineLogDAO.addLifePolicyEndorseLog(lifePolicyEndorseLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyEndorseLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) {
		try {
			lifePolicyTimeLineLogDAO.updateLifePolicyEndorseLog(lifePolicyEndorseLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a new LifePolicyEndorseLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyIdLog(LifePolicyIdLog lifePolicyIdLog) {
		try {
			lifePolicyTimeLineLogDAO.addLifePolicyIdLog(lifePolicyIdLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyIdLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		try {
			lifePolicyTimeLineLogDAO.addLifePolicyTimeLineLog(lifePolicyTimeLineLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyTimeLineLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		try {
			lifePolicyTimeLineLogDAO.updateLifePolicyTimeLineLog(lifePolicyTimeLineLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a new LifePolicyTimeLineLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyTimeLineLog findLifePolicyTimeLineLog(String id) {
		LifePolicyTimeLineLog result = new LifePolicyTimeLineLog();
		try {
			result = lifePolicyTimeLineLogDAO.findLifePolicyTimeLineLog(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a new LifePolicyTimeLineLog", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) {
		try {
			lifePolicyTimeLineLogDAO.deleteLifePolicyTimeLineLog(lifePolicyTimeLineLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a new LifePolicyTimeLineLog", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findMaxPolicyLifeCountByPolicyNo(String policyNo) {
		int result;
		try {
			result = lifePolicyTimeLineLogDAO.findMaxPolicyLifeCountByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "failed to find max policyLifeCount by PolicyNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyTimeLineLog findLifePolicyTimeLineLogByPolicyNo(String policyNo) {
		LifePolicyTimeLineLog result;
		try {
			result = lifePolicyTimeLineLogDAO.findLifePolicyTimeLineLogByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "failed to find LifePolicyTimeLineLog by PolicyNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) {
		try {
			lifePolicyTimeLineLogDAO.addLifePolicyClaimLog(lifePolicyClaimLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "failed to add new LifePolicyClaimLog ", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyClaimLog updateLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) {
		try {
			lifePolicyClaimLog = lifePolicyTimeLineLogDAO.updateLifePolicyClaimLog(lifePolicyClaimLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update LifePolicyClaimLog", e);
		}
		return lifePolicyClaimLog;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyClaimLog findLifePolicyClaimLogByLifeClaimId(String lifeClaimId) {
		LifePolicyClaimLog result;
		try {
			result = lifePolicyTimeLineLogDAO.findLifePolicyClaimLogByLifeClaimId(lifeClaimId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "failed to find LifePolicyClaimLog by lifeClaimId : " + lifeClaimId, e);
		}
		return result;
	}

}
