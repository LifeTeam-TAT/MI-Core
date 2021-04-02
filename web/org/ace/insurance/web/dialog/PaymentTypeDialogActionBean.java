package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "PaymentTypeDialogActionBean")
@ViewScoped
public class PaymentTypeDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	private List<PaymentType> paymentTypeList;

	@PostConstruct
	public void init() {
		Product product = (Product) getParam("PRODUCT");
		List<PaymentType> tempList = paymentTypeService.findAllPaymentType();
		paymentTypeList = new ArrayList<PaymentType>();
		if (product != null) {
			paymentTypeList.addAll(product.getPaymentTypeList());
		} else
			paymentTypeList.addAll(tempList);
	}

	public List<PaymentType> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void selectPaymentType(PaymentType paymentType) {
		RequestContext.getCurrentInstance().closeDialog(paymentType);
	}
}
