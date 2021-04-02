package org.ace.insurance.life.premium.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.insurance.life.premium.persistence.interfaces.ILifePolicyBillingDAO;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyBillingService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/01
 */

@Service(value = "LifePolicyBillingService")
public class LifePolicyBillingService extends BaseService implements ILifePolicyBillingService {

	@Resource(name = "LifePolicyBillingDAO")
	private ILifePolicyBillingDAO lifePolicyBillingDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	private Payment payment;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifePolicyBilling(LifePolicyBilling lifePolicyBilling, WorkFlowDTO workflowDTO) {
		try {
			lifePolicyBillingDAO.insert(lifePolicyBilling);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyBilling", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyBilling(LifePolicyBilling lifePolicyBilling, WorkFlowDTO workflowDTO) {
		try {
			String billingID = customIDGenerator.getNextId(SystemConstants.LIFE_BILLING_NO, null);
			lifePolicyBilling.setBillingNo(billingID);
			payment = lifePolicyBilling.getPayment();
			payment.setReferenceNo(billingID);
			lifePolicyBilling.setPayment(payment);

			lifePolicyBillingDAO.update(lifePolicyBilling);

			workflowDTO.setReferenceNo(lifePolicyBilling.getBillingNo());
			workFlowDTOService.addNewWorkFlow(workflowDTO);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicyBilling", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicyBilling(LifePolicyBilling lifePolicyBilling) {
		try {
			lifePolicyBillingDAO.delete(lifePolicyBilling);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePolicyBilling", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyBilling findLifePolicyBillingById(String id) {
		LifePolicyBilling result = null;
		try {
			result = lifePolicyBillingDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicyBilling (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyBilling> findAllLifePolicyBilling() {
		List<LifePolicyBilling> result = null;
		try {
			result = lifePolicyBillingDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyBilling)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyBilling> findLifePolicyBilling(String lifePolicyNo, String customerID, String agentID, Date startDate, Date endDate) {
		List<LifePolicyBilling> result = null;
		try {
			result = lifePolicyBillingDAO.findLifePolicyBilling(lifePolicyNo, customerID, agentID, startDate, endDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find the active LifePolicyPremium", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyBilling findLifePolicyBillingByBillingNo(String id) {
		LifePolicyBilling result = null;
		try {
			result = lifePolicyBillingDAO.findByBillingNo(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicyBilling (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void LifePolicyBillingPayment(LifePolicyBilling lifePolicyBilling) {
		try {
			lifePolicyBillingDAO.update(lifePolicyBilling);
			workFlowDTOService.deleteWorkFlowByRefNo(lifePolicyBilling.getBillingNo());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield LifePolicyBillingPayment() process", e);
		}
	}
}
