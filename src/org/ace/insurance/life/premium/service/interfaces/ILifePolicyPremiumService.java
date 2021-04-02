package org.ace.insurance.life.premium.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.life.premium.LifePolicyPremium;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

public interface ILifePolicyPremiumService {

	public void addNewLifePolicyPremium(LifePolicyPremium lifePolicyPremium);

	public void updateLifePolicyPremium(LifePolicyPremium lifePolicyPremium);

	public void deleteLifePolicyPremium(LifePolicyPremium lifePolicyPremium);

	public LifePolicyPremium findLifePolicyPremiumById(String id);

	public List<LifePolicyPremium> findAllLifePolicyPremium();

	public List<LifePolicyPremium> findLifePolicyPremiumByLifePolicyPremium(String lifePolicyNo, String customerID,
			String agentID,
			Date startDate,
			Date endDate);
}
