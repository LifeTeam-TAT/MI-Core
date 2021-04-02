package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.payment.TlfData;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.SystemException;

public interface ITlfProcessor {
	public void handleTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException;

	/** used from Agent Commission Payment */
	public void handleAgentCommTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException;

	public void handleLifeClaimTLFProcess(List<TlfData> dataList, Branch branch) throws SystemException;

	public void handleClaimMedicalFeesTlfProcess(List<TlfData> dataList, Branch branch) throws SystemException;

}
