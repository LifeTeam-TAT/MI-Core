/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.paymenttype.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.persistence.interfaces.IPaymentTypeDAO;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PaymentTypeService")
public class PaymentTypeService extends BaseService implements IPaymentTypeService {

	@Resource(name = "PaymentTypeDAO")
	private IPaymentTypeDAO paymentTypeDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewPaymentType(PaymentType paymentType) {
		try {
			paymentTypeDAO.insert(paymentType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new PaymentType", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePaymentType(PaymentType paymentType) {
		try {
			paymentTypeDAO.update(paymentType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a PaymentType", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePaymentType(PaymentType paymentType) {
		try {
			paymentTypeDAO.delete(paymentType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a PaymentType", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentType> findAllPaymentType() {
		List<PaymentType> result = null;
		try {
			result = paymentTypeDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of PaymentType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentType findPaymentTypeById(String id) {
		PaymentType result = null;
		try {
			result = paymentTypeDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentType (ID : " + id + ")", e);
		}
		return result;
	}
}