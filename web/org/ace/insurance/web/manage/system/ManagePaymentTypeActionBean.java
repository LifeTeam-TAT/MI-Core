/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManagePaymentTypeActionBean")
public class ManagePaymentTypeActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	private PaymentType paymentType;
	private boolean createNew;
	private List<PaymentType> paymentTypeList;

	@PostConstruct
	public void init() {
		createNewPaymentType();
		loadPaymentType();
	}

	private void loadPaymentType() {
		paymentTypeList = paymentTypeService.findAllPaymentType();
	}

	public void createNewPaymentType() {
		createNew = true;
		paymentType = new PaymentType();
	}

	public void prepareUpdatePaymentType(PaymentType paymentType) {
		createNew = false;
		this.paymentType = paymentType;
	}

	public void addNewPaymentType() {
		try {
			paymentTypeService.addNewPaymentType(paymentType);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, paymentType.getName());
			createNewPaymentType();
			loadPaymentType();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updatePaymentType() {
		try {
			paymentTypeService.updatePaymentType(paymentType);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, paymentType.getName());
			createNewPaymentType();
			loadPaymentType();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deletePaymentType() {
		try {
			paymentTypeService.deletePaymentType(paymentType);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, paymentType.getName());
			createNewPaymentType();
			loadPaymentType();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<PaymentType> getPaymentTypeList() {
		return paymentTypeList;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
}
