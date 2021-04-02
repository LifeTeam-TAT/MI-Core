package org.ace.insurance.web.manage.billcollection;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentDelegateService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

import net.sf.jasperreports.engine.JasperPrint;

@ViewScoped
@ManagedBean(name = "BillCollectionPaymentActionBean")
public class BillCollectionPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	private User user;
	private Payment payment;
	private boolean cashPayment = true;
	private boolean isAccountBank;
	private boolean isCheque;
	private boolean isBank;
	private boolean isTransfer;
	private boolean showPrintPreview;
	private boolean isEndorse;
	private String policyNo;

	LifePolicy lifePolicy;
	MedicalPolicy medicalPolicy;
	private String fileName;
	private SalesPoints salesPoint;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		payment = (payment == null) ? (Payment) getParam("payment") : payment;
	}

	@PreDestroy
	public void destroy() {
		removeParam("payment");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		if (PolicyReferenceType.LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			lifePolicy = lifePolicyService.findLifePolicyById(payment.getReferenceNo());
			policyNo = lifePolicy.getPolicyNo();
			fileName = "LifeCashReceipt.pdf";
		} else if (PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			medicalPolicy = medicalPolicyService.findMedicalPolicyById(payment.getReferenceNo());
			policyNo = medicalPolicy.getPolicyNo();
			fileName = "MedicalCashReceipt.pdf";
		}
	}

	public void paymentFireProposal() {
		try {
			paymentDelegateService.billCollectionPayment(payment, user.getBranch());
			showPrintPreview = true;
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.POLICY_NO, policyNo);
			System.out.println(reportName + "*************");
			System.err.println(fileName + "******************");
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void openTemplateDialog() {
		if (PolicyReferenceType.LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())) {
			putParam("lifePolicy", lifePolicy);
			openLifePolicyInfoTemplate();
		} else if (PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			putParam("medicalProposal", medicalPolicy.getMedicalProposal());
			openMedicalProposalInfoTemplate();
		} else if (PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			putParam("lifePolicy", lifePolicy);
			openStudentLifePolicyInfoTemplate();
		}
	}

	private final String reportName = "BillCollection";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateReport() {
		List<JasperPrint> printList = null;
		System.out.println(reportName + "*****************");
		if (PolicyReferenceType.LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			DocumentBuilder.generateLifeBillCollectionCashReceipt(lifePolicy, payment, dirPath, fileName);
		} else if (PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())
				|| PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
			DocumentBuilder.generateMedicalBillCollectionCashReceipt(medicalPolicy, payment, dirPath, fileName);
		}
	}

	public boolean isEndorse() {
		return isEndorse;
	}

	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		payment.setAccountBank(accountBank);
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		payment.setBank(bank);
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		payment.setAccountBank(null);
		payment.setBank(null);
		payment.setChequeNo(null);
		payment.setPoNo(null);
		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			isAccountBank = true;
			isCheque = true;
			isBank = true;
			isTransfer = false;
		} else if (payment.getPaymentChannel().equals(PaymentChannel.TRANSFER)) {
			isAccountBank = true;
			isCheque = false;
			isBank = true;
			isTransfer = true;
		} else if (payment.getPaymentChannel().equals(PaymentChannel.CASHED)) {
			isAccountBank = false;
			isCheque = false;
			isBank = false;
			isTransfer = false;
		}
	}

	public boolean isAccountBank() {
		return isAccountBank;
	}

	public void setAccountBank(boolean isAccountBank) {
		this.isAccountBank = isAccountBank;
	}

	public boolean isCheque() {
		return isCheque;
	}

	public void setCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}

	public boolean isBank() {
		return isBank;
	}

	public void setBank(boolean isBank) {
		this.isBank = isBank;
	}

	public boolean isTransfer() {
		return isTransfer;
	}

	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
	}

	public boolean isShowPrintPreview() {
		return showPrintPreview;
	}

	public boolean isCashPayment() {
		return cashPayment;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void returnSalesPoints(SelectEvent event) {
		salesPoint = (SalesPoints) event.getObject();
		payment.setSalesPoints(salesPoint);
	}

	public SalesPoints getSalesPoint() {
		return salesPoint;
	}

	public void setSalesPoint(SalesPoints salesPoint) {
		this.salesPoint = salesPoint;
	}
}
