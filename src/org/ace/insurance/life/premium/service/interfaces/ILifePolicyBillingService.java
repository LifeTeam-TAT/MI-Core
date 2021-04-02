package org.ace.insurance.life.premium.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.premium.LifePolicyBilling;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

public interface ILifePolicyBillingService {

	public void addNewLifePolicyBilling(LifePolicyBilling lifePolicyBilling, WorkFlowDTO workflowDTO);

	public void updateLifePolicyBilling(LifePolicyBilling lifePolicyBilling, WorkFlowDTO workflowDTO);

	public void deleteLifePolicyBilling(LifePolicyBilling lifePolicyBilling);

	public LifePolicyBilling findLifePolicyBillingById(String id);

	public List<LifePolicyBilling> findAllLifePolicyBilling();

	public List<LifePolicyBilling> findLifePolicyBilling(String lifePolicyNo,
			String customerID, String agentID, Date startDate, Date endDate);

	public LifePolicyBilling findLifePolicyBillingByBillingNo(String id);

	public void LifePolicyBillingPayment(LifePolicyBilling lifePolicyBilling);
}
