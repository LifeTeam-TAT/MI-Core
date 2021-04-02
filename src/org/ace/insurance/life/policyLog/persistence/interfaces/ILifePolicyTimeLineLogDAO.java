package org.ace.insurance.life.policyLog.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.policyLog.LifePolicyClaimLog;
import org.ace.insurance.life.policyLog.LifePolicyEndorseLog;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyTimeLineLogDAO {
	public void addLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) throws DAOException;

	public void updateLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog) throws DAOException;

	public void addLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException;

	public void updateLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException;

	public void deleteLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) throws DAOException;

	public LifePolicyTimeLineLog findLifePolicyTimeLineLog(String id) throws DAOException;

	public LifePolicyTimeLineLog findLifePolicyTimeLineLogByPolicyNo(String policyNo) throws DAOException;

	public int findMaxPolicyLifeCountByPolicyNo(String policyNo) throws DAOException;

	public LifePolicyTimeLineLog findLifePolicyTimeLineOf(String policyNo, int lastYear) throws DAOException;

	public List<LifePolicyClaimLog> findLifePolicyClaimLogByPolicyTimeLineId(String lifePolicyTimeLineLogId) throws DAOException;

	public void addLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) throws DAOException;

	public LifePolicyClaimLog updateLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog) throws DAOException;

	public LifePolicyClaimLog findLifePolicyClaimLogByLifeClaimId(String lifeClaimId) throws DAOException;

	public void addLifePolicyIdLog(LifePolicyIdLog lifePolicyIdLog) throws DAOException;

	public LifePolicyIdLog findLifePolicyIdLogByToId(String toId) throws DAOException;

}
