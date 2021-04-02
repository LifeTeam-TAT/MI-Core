package org.ace.insurance.life.premium.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.life.premium.LifePolicyPremium;
import org.ace.java.component.persistence.exception.DAOException;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

public interface ILifePolicyPremiumDAO {

	public void insert(LifePolicyPremium lifePolicyPremium) throws DAOException;

	public void update(LifePolicyPremium lifePolicyPremium) throws DAOException;

	public void delete(LifePolicyPremium lifePolicyPremium) throws DAOException;

	public LifePolicyPremium findById(String id) throws DAOException;

	public List<LifePolicyPremium> findAll() throws DAOException;

	public List<LifePolicyPremium> findByLifePolicyPremium(String lifePolicyNo, String customerID, String agentID,
			Date startDate,
			Date endDate) throws DAOException;
}
