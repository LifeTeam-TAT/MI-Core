/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bankBranch.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.bankBranch.BKB001;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.java.component.persistence.exception.DAOException;

public interface IBankBranchDAO {
	public void insert(BankBranch bankBrach) throws DAOException;

	public void update(BankBranch bankBrach) throws DAOException;

	public void delete(BankBranch bankBrach) throws DAOException;

	public BankBranch findById(String id) throws DAOException;

	public List<BankBranch> findAll() throws DAOException;

	public List<BKB001> findAll_BKB001() throws DAOException;

	public List<BKB001> findAllAfp_BKB001(String productGroupId) throws DAOException;
}
