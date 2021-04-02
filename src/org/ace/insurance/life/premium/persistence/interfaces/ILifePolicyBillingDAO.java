package org.ace.insurance.life.premium.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

public interface ILifePolicyBillingDAO {

	public void insert(LifePolicyBilling lifePolicyBilling) throws DAOException;

	public void update(LifePolicyBilling lifePolicyBilling) throws DAOException;

	public void delete(LifePolicyBilling lifePolicyBilling) throws DAOException;

	public LifePolicyBilling findById(String id) throws DAOException;

	public List<LifePolicyBilling> findAll() throws DAOException;

	public List<LifePolicyBilling> findLifePolicyBilling(String lifePolicyNo,
			String customerID, String agentID, Date startDate, Date endDate) throws DAOException;

	public LifePolicyBilling findByBillingNo(String billingNo) throws DAOException;
}
