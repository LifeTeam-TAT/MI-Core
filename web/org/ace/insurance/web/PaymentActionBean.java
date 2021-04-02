package org.ace.insurance.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.payment.service.interfaces.IPaymentDelegateService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "PaymentActionBean")
public class PaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private PolicyReferenceType referenceType;
	private PolicyReferenceType[] referenceTypes;
	private String remark;
	private String receiptStream;
	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{PaymentDelegateService}")
	private IPaymentDelegateService paymentDelegateService;

	public void setPaymentDelegateService(IPaymentDelegateService paymentDelegateService) {
		this.paymentDelegateService = paymentDelegateService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private Map<String, PaymentDTO> paymentMap;
	private User responsiblePerson;
	private List<User> userList;
	private User user;

	@PostConstruct
	public void init() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		referenceTypes = PolicyReferenceType.values();
		referenceType = PolicyReferenceType.values()[0];
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public String getReceiptStream() {
		return receiptStream;
	}

	public void setReceiptStream(String receiptStream) {
		this.receiptStream = receiptStream;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public List<User> getUserList() {
		return null;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<PaymentDTO> getPaymentList() {
		if (paymentMap != null) {
			RegNoSorter<PaymentDTO> sorter = new RegNoSorter<PaymentDTO>(new ArrayList<PaymentDTO>(paymentMap.values()));
			return sorter.getSortedList();
		}
		return null;
	}

	public PolicyReferenceType[] getReferenceTypes() {
		// FIXME CHECK REFTYPE
		return new PolicyReferenceType[] { PolicyReferenceType.ENDOWNMENT_LIFE_POLICY, PolicyReferenceType.HEALTH_POLICY, PolicyReferenceType.LIFE_BILL_COLLECTION,
				PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION };
	}

	public void search() {
		Set<String> receiptSet = new HashSet<String>();
		String[] receiptArray = receiptStream.split("\\r?\\n");
		for (String receiptNo : receiptArray) {
			// TODO FIXME PSH
			// Pattern pattern =
			// Pattern.compile("(CASH|CHQ|TRF)(\\/)[0-9]{4}(\\/)[0-9]{10}(\\/(.*))",
			// Pattern.CASE_INSENSITIVE);
			// Matcher matcher = pattern.matcher(receiptNo);
			// if (matcher.matches()) {
			// receiptSet.add(receiptNo.toUpperCase());
			// }
			receiptSet.add(receiptNo.toUpperCase());
			Pattern pattern = Pattern.compile("(CASH|CHQ|TRF)(\\/)[0-9]{2}(\\-)[0-9]{2}(\\/)[0-9]{10}(\\/)[0-9]{3}", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(receiptNo);
			if (matcher.matches()) {
				receiptSet.add(receiptNo.toUpperCase());
			}
		}
		List<PaymentDTO> paymentList = paymentService.findPaymentByRecipNo(new ArrayList<String>(receiptSet), referenceType, false);
		paymentMap = new HashMap<String, PaymentDTO>();
		for (PaymentDTO dto : paymentList) {
			paymentMap.put(dto.getReferenceNo(), dto);
		}
	}

	public void delete(PaymentDTO payment) {
		paymentMap.remove(payment.getReferenceNo());
	}

	public String getGrandTotalAmount() {
		if (paymentMap.values() != null) {
			double totalAmount = 0.0;
			for (PaymentDTO payment : paymentMap.values()) {
				totalAmount = totalAmount + payment.getBillCollectionTotalAmount();
			}
			return Utils.getCurrencyFormatString(totalAmount);
		}
		return "";
	}

	// public List<User> getUserList() {
	// return userList;
	// }
	public void loadUserList() {
		WorkFlowType workflowType = null;
		switch (referenceType) {
			
			// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
				workflowType = WorkFlowType.LIFE;
				break;
		}
		if (userList == null) {
			// userList = userService.findUserByPermission(WorkflowTask.ISSUING,
			// workflowType, TransactionType.UNDERWRITING, getLoginBranchId(),
			// null);
		}
	}

	public String payment() {
		String result = null;
		try {
			String formID = "paymentForm";
			if (!PolicyReferenceType.LIFE_BILL_COLLECTION.equals(referenceType)) {
				if (responsiblePerson == null) {
					addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
					return null;
				}
			}
			paymentDelegateService.payment(new ArrayList<PaymentDTO>(paymentMap.values()), referenceType, responsiblePerson, remark, user.getBranch());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}
