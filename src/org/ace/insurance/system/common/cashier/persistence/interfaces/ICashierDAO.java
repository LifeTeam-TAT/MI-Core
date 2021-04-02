/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.cashier.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICashierDAO {

	public void insert(Cashier cashier) throws DAOException;

	public void update(Cashier cashier) throws DAOException;

	public void delete(Cashier cashier) throws DAOException;

	public Cashier findById(String id) throws DAOException;

	public List<Cashier> findAll() throws DAOException;

	public boolean checkExistingCashier(Cashier cashier) throws DAOException;

	public List<Cashier> findByWorkShop(WorkShop workShop) throws DAOException;

}
