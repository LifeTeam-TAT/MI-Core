/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.addon.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.addon.AddOn;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAddOnDAO {
	public void insert(AddOn AddOn) throws DAOException;

	public void update(AddOn AddOn) throws DAOException;

	public void delete(AddOn AddOn) throws DAOException;

	public AddOn findById(String id) throws DAOException;

	public List<AddOn> findAll() throws DAOException;

	public List<AddOn> findByCriteria(String criteria) throws DAOException;

	public List<AddOn> findPremiumRateOfAddOn() throws DAOException;

	public List<AddOn> findAddOnByProductId(String productId) throws DAOException;
}
