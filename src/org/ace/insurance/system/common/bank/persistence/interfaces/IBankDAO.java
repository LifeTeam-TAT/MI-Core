/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bank.persistence.interfaces;

import java.util.List;

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.COA001;
import org.ace.java.component.persistence.exception.DAOException;

public interface IBankDAO {
	public void insert(Bank Bank) throws DAOException;

	public void update(Bank Bank) throws DAOException;

	public void delete(Bank Bank) throws DAOException;

	public Bank findById(String id) throws DAOException;

	public List<BNK001> findAll() throws DAOException;

	public List<BNK001> findACodeNotNull() throws DAOException;

	public Bank findByCSCBankCode(String cscBankCode) throws DAOException;

	public List<COA001> findCAOByAType() throws DAOException;

}
