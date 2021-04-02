package org.ace.insurance.web.manage.life.billcollection;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.life.premium.LifePolicyBilling;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyBillingService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

/**
 * Life Bill Collection Payment ActionBean
 * 
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/17
 */
@ViewScoped
@ManagedBean(name = "LifeBillcollectionPaymentActionBean")
public class LifeBillcollectionPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private LifePolicyBilling lifePolicyBilling;
	@ManagedProperty(value = "#{LifePolicyBillingService}")
	private ILifePolicyBillingService lifePolicyBillingService;

	public void setLifePolicyBillingService(ILifePolicyBillingService lifePolicyBillingService) {
		this.lifePolicyBillingService = lifePolicyBillingService;
	}

	private Payment payment;

	@PostConstruct
	public void init() {
		lifePolicyBilling = billCollectionSearch(lifePolicyBilling.getBillingNo());
		payment = new Payment();
	}

	public String confirm() {
		String result = null;
		try {
			Payment payment = lifePolicyBilling.getPayment();
			payment.setComplete(true);
			lifePolicyBilling.setPayment(payment);
			lifePolicyBilling.setCollectDate(new Date());
			lifePolicyBilling.setCollected(true);
			lifePolicyBillingService.LifePolicyBillingPayment(lifePolicyBilling);
			addInfoMessage(null, MessageId.PAYMENT_PROCESS_SUCCESS, lifePolicyBilling.getId());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}
	
	public void returnSalesPoints(SelectEvent event) {
		SalesPoints salesPoints = (SalesPoints) event.getObject();
		lifePolicyBilling.getPayment().setSalesPoints(salesPoints);
	}

	private LifePolicyBilling billCollectionSearch(String billingNo) {
		LifePolicyBilling result = new LifePolicyBilling();
		result = lifePolicyBillingService.findLifePolicyBillingByBillingNo(billingNo);
		return result;
	}

	public LifePolicyBilling getLifePolicyBilling() {
		return lifePolicyBilling;
	}

	public void setLifePolicyBilling(LifePolicyBilling lifePolicyBilling) {
		this.lifePolicyBilling = lifePolicyBilling;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj) ? true : false;
	}
}
