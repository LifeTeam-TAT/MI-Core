package org.ace.insurance.web.manage.life.billcollection;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyBillingService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

/**
 * Life Bill Collection ActionBean
 * 
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/17
 */
@ViewScoped
@ManagedBean(name = "LifeBillcollectionActionBean")
public class LifeBillcollectionActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private LifePolicyBilling lifePolicyBilling;
	private Payment payment;
	private User user;
	private User responsiblePerson;
	private String chequeNo;
	@ManagedProperty(value = "#{LifePolicyBillingService}")
	private ILifePolicyBillingService lifePolicyBillingService;

	public void setLifePolicyBillingService(ILifePolicyBillingService lifePolicyBillingService) {
		this.lifePolicyBillingService = lifePolicyBillingService;
	}

	private boolean paymentChannel;

	private Bank bank;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
	}

	public String OK() {
		String formID = "billCollectionForm";
		if (responsiblePerson == null) {
			addErrorMessage(formID + ":responsiblePerson", MessageId.REQUIRED_VALUES);
			return null;
		}

		if (!paymentChannel) {
			if (bank.getName() == null) {
				addErrorMessage(formID + ":bank", MessageId.REQUIRED_VALUES);
				return null;
			} else if (chequeNo == null) {
				addErrorMessage(formID + ":chequeNo", MessageId.REQUIRED_VALUES);
				return null;
			}
		}
		// YHO Start
		WorkFlowDTO workFlowDTO = null;
		// TODO
		// = new WorkFlowDTO("", "", WorkflowTask.PAYMENT,
		// ReferenceType.LIFE_BILL_COLLECTION, TransactionType.UNDERWRITING,
		// user, responsiblePerson);
		if (paymentChannel) {
			payment.setPaymentChannel(PaymentChannel.CASHED);
		} else {
			payment.setBank(bank);
			payment.setChequeNo(chequeNo);
			payment.setPaymentChannel(PaymentChannel.CHEQUE);
		}
		payment = new Payment();
		// FIXME CHECK REFTYPE
		payment.setReferenceType(PolicyReferenceType.ENDOWNMENT_LIFE_POLICY);
		payment.setComplete(false);
		lifePolicyBilling.setPayment(payment);
		// YHO End
		lifePolicyBillingService.updateLifePolicyBilling(lifePolicyBilling, workFlowDTO);
		addInfoMessage(null, MessageId.PAYMENT_PROCESS_SUCCESS, lifePolicyBilling.getId());
		ExternalContext extContext = getFacesContext().getExternalContext();
		extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
		return "dashboard";
	}

	/**
	 * @return lifePolicyBilling
	 */
	public LifePolicyBilling getLifePolicyBilling() {
		return lifePolicyBilling;
	}

	/**
	 * @param lifePolicyBilling
	 */
	public void setLifePolicyBilling(LifePolicyBilling lifePolicyBilling) {
		this.lifePolicyBilling = lifePolicyBilling;
	}

	/**
	 * @return paymentChannel
	 */
	public boolean isPaymentChannel() {
		return paymentChannel;
	}

	/**
	 * @param paymentChannel
	 */
	public void setPaymentChannel(boolean paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	/**
	 * @return responsiblePerson
	 */
	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public Bank getBank() {
		return bank;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		this.bank = bank;
	}

	public void returnResponsibleUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}
}
