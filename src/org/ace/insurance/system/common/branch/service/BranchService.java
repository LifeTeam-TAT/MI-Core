/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.branch.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.BRA002;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.persistence.interfaces.IBranchDAO;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "BranchService")
public class BranchService extends BaseService implements IBranchService {

	@Resource(name = "BranchDAO")
	private IBranchDAO branchDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewBranch(Branch branch) {
		try {
			branchDAO.insert(branch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Branch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateBranch(Branch branch) {
		try {
			branchDAO.update(branch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Branch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteBranch(Branch branch) {
		try {
			branchDAO.delete(branch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Branch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Branch> findAllBranch() {
		List<Branch> result = null;
		try {
			result = branchDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Branch)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Branch findBranchById(String id) {
		Branch result = null;
		try {
			result = branchDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Branch (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Branch findByBranchCode(String branchCode) {
		Branch result = null;
		try {
			result = branchDAO.findByCode(branchCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Branch (branchCode : " + branchCode + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BRA002> findAll_BRA002() {
		List<BRA002> result = null;
		try {
			result = branchDAO.findAll_BRA002();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all BRA002 ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BRA001> findAll_BRA001() {
		List<BRA001> result = null;
		try {
			result = branchDAO.findAll_BRA001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all BRA002 ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Branch findByCSCBranchCode(String cscBranchCode) throws SystemException {
		Branch result = null;
		try {
			result = branchDAO.findByCSCBranchCode(cscBranchCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Branch (CSC branchCode : " + cscBranchCode + ")", e);
		}
		return result;
	}

}