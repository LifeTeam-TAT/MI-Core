/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.branch.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.BRA002;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.persistence.exception.DAOException;

public interface IBranchDAO {
	public void insert(Branch Branch) throws DAOException;

	public void update(Branch Branch) throws DAOException;

	public void delete(Branch Branch) throws DAOException;

	public Branch findById(String id) throws DAOException;

	public Branch findByCode(String code) throws DAOException;

	public List<Branch> findAll() throws DAOException;

	public List<BRA002> findAll_BRA002() throws DAOException;

	public List<BRA001> findAll_BRA001() throws DAOException;

	public Branch findByCSCBranchCode(String cscBranchCode) throws DAOException;
}
