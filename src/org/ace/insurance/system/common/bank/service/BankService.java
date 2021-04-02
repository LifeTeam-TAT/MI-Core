/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bank.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.COA001;
import org.ace.insurance.system.common.bank.persistence.interfaces.IBankDAO;
import org.ace.insurance.system.common.bank.service.interfaces.IBankService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "BankService")
public class BankService extends BaseService implements IBankService {

	@Resource(name = "BankDAO")
	private IBankDAO bankDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewBank(Bank bank) {
		try {
			bankDAO.insert(bank);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new Bank", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateBank(Bank bank) {
		try {
			bankDAO.update(bank);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a Bank", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteBank(Bank bank) {
		try {
			bankDAO.delete(bank);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a Bank", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<BNK001> findAllBank() {
		List<BNK001> result = null;
		try {
			result = bankDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of Bank)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Bank findBankById(String id) {
		Bank result = null;
		try {
			result = bankDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Bank (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<COA001> findAllCOAByAType() {
		List<COA001> result = null;
		try {
			result = bankDAO.findCAOByAType();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find COA By AType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<BNK001> findACodeNotNull() {
		List<BNK001> result = null;
		try {
			result = bankDAO.findACodeNotNull();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find All Banks which ACode is Not Null)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Bank findByCSCBankCode(String cscBankCode) throws SystemException {
		Bank result = null;
		try {
			result = bankDAO.findByCSCBankCode(cscBankCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Bank ( cscBankCode : " + cscBankCode + ")", e);
		}
		return result;
	}
}
