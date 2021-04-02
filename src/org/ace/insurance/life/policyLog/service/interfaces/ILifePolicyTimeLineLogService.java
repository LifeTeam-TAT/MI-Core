package org.ace.insurance.life.policyLog.service.interfaces;

import org.ace.insurance.life.policyLog.LifePolicyClaimLog;
import org.ace.insurance.life.policyLog.LifePolicyEndorseLog;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;

public interface ILifePolicyTimeLineLogService {
	
	public void addLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog) ;
	public void addLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog);
	public void addLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog);
	
	public void updateLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog);
	public void updateLifePolicyEndorseLog(LifePolicyEndorseLog lifePolicyEndorseLog);
	public LifePolicyClaimLog updateLifePolicyClaimLog(LifePolicyClaimLog lifePolicyClaimLog);
	
	public void deleteLifePolicyTimeLineLog(LifePolicyTimeLineLog lifePolicyTimeLineLog);

	public LifePolicyTimeLineLog findLifePolicyTimeLineLog(String id);
	public LifePolicyTimeLineLog findLifePolicyTimeLineLogByPolicyNo(String policyNo);
	public int findMaxPolicyLifeCountByPolicyNo(String policyNo);
	
	public LifePolicyClaimLog findLifePolicyClaimLogByLifeClaimId(String lifeClaimId);
	
	public void addLifePolicyIdLog(LifePolicyIdLog lifePolicyIdLog);
}
