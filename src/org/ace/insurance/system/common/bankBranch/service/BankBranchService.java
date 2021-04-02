/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bankBranch.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.bankBranch.BKB001;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.bankBranch.persistence.interfaces.IBankBranchDAO;
import org.ace.insurance.system.common.bankBranch.service.interfaces.IBankBranchService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "BankBranchService")
public class BankBranchService extends BaseService implements IBankBranchService {

	@Resource(name = "BankBranchDAO")
	private IBankBranchDAO bankBranchDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewBankBranch(BankBranch bankBranch) {
		try {
			bankBranchDAO.insert(bankBranch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new BankBranch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBankBranch(BankBranch bankBranch) {
		try {
			bankBranchDAO.update(bankBranch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a BankBranch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBankBranch(BankBranch bankBranch) {
		try {
			bankBranchDAO.delete(bankBranch);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a BankBranch", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<BKB001> findAll_BKB001() {
		List<BKB001> result = null;
		try {
			result = bankBranchDAO.findAll_BKB001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of BKB001)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<BKB001> findAllAfp_BKB001(String productGroupId) {
		List<BKB001> result = null;
		try {
			result = bankBranchDAO.findAllAfp_BKB001(productGroupId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of BKB001)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<BankBranch> findAllBankBranch() {
		List<BankBranch> result = null;
		try {
			result = bankBranchDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of BankBranch)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BankBranch findBankBranchById(String id) {
		BankBranch result = null;
		try {
			result = bankBranchDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a BankBranch (ID : " + id + ")", e);
		}
		return result;
	}
}