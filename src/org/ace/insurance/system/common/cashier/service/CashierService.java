/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.cashier.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.cashier.Cashier;
import org.ace.insurance.system.common.cashier.persistence.interfaces.ICashierDAO;
import org.ace.insurance.system.common.cashier.service.interfaces.ICashierService;
import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "CashierService")
public class CashierService extends BaseService implements ICashierService {

	@Resource(name = "CashierDAO")
	private ICashierDAO cashierDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewCashier(Cashier cashier) {
		try {
			cashierDAO.insert(cashier);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Cashier", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateCashier(Cashier cashier) {
		try {
			cashierDAO.update(cashier);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Cashier", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteCashier(Cashier cashier) {
		try {
			cashierDAO.delete(cashier);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Cashier", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Cashier> findAllCashier() {
		List<Cashier> result = null;
		try {
			result = cashierDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Cashier)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Cashier findCashierById(String id) {
		Cashier result = null;
		try {
			result = cashierDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Cashier (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean checkExistingCashier(Cashier cashier) {
		boolean existCashier = false;
		try {
			existCashier = cashierDAO.checkExistingCashier(cashier);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find existing customer", e);
		}
		return existCashier;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Cashier> findCasherByWorkShop(WorkShop workShop) {
		List<Cashier> result = null;
		try {
			result = cashierDAO.findByWorkShop(workShop);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Cashier (ID : " + workShop + ")", e);
		}
		return result;
	}
}