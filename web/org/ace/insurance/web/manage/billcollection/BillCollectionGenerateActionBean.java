package org.ace.insurance.web.manage.billcollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionCashReceiptDTO;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionModel;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * 
 * Life Payment Bill Collection ActionBean
 * 
 * @author Tun Lin Aung
 * @since 1.0.0
 * @date 2013/09/19
 */
@ViewScoped
@ManagedBean(name = "BillCollectionGenerateActionBean")
public class BillCollectionGenerateActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private BillCollectionDTO billCollection;
	private List<BillCollectionDTO> billCollectionList;
	private BillCollectionDTO[] selectedDTOList;
	private List<BillCollectionCashReceiptDTO> cashReceiptDTOList;
	private PolicyCriteria policyCriteria;
	private User user;
	private User responsiblePerson;
	private String remark;
	private boolean renderButton;
	private boolean availablePrint;
	private BRA001 crietriaBrach;
	private List<BRA001> bra001List;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		reset();
		bra001List = new ArrayList<BRA001>();
		for (Branch b : user.getAccessBranchList()) {
			bra001List.add(new BRA001(b.getId(), b.getName(), b.getBranchCode(), b.getDescription()));
		}
	}

	public void reset() {
		policyCriteria = new PolicyCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		policyCriteria.setFromDate(cal.getTime());
		Date endDate = new Date();
		policyCriteria.setToDate(endDate);
		billCollection = new BillCollectionDTO();
		billCollectionList = new ArrayList<BillCollectionDTO>();
	}

	public boolean validation() {
		boolean result = true;
		if ((policyCriteria.getPolicyCriteria() != null)) {
			if (policyCriteria.getCriteriaValue() == null || policyCriteria.getCriteriaValue().isEmpty()) {
				addErrorMessage("paymentBillCollectionForm:policyCriteria", MessageId.ATLEAST_ONE_REQUIRED);
				result = false;
			}
		}
		if (policyCriteria.getCriteriaValue() != null && !policyCriteria.getCriteriaValue().isEmpty()) {
			if (policyCriteria.getPolicyCriteria() == null) {
				addErrorMessage("paymentBillCollectionForm:selectPolicyCriteria", MessageId.ATLEAST_ONE_REQUIRED);
				result = false;
			}
		}
		return result;
	}

	public void search() {
		if (validation()) {
			if (ReferenceType.ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType()) || ReferenceType.SHORT_ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType())
					|| ReferenceType.STUDENT_LIFE.equals(policyCriteria.getReferenceType())) {
				billCollectionList = lifePolicyService.findBillCollectionByCriteria(policyCriteria);
				for (BillCollectionDTO billCollection : getBillCollectionList()) {
					LifePolicySummary lifePolicySummary = lifePolicySummaryService.findLifePolicyByPolicyNo(billCollection.getPolicyNo());
					if (lifePolicySummary != null) {
						billCollection.setRefund(lifePolicySummary.getRefund());
					}
				}
			} else if (ReferenceType.HEALTH.equals(policyCriteria.getReferenceType()) || ReferenceType.CRITICAL_ILLNESS.equals(policyCriteria.getReferenceType())) {
				billCollectionList = medicalPolicyService.findBillCollectionByCriteria(policyCriteria);
			}
			availablePrint = false;
			renderButton = getBillCollectionList().isEmpty() ? false : true;
			crietriaBrach = policyCriteria.getBranch();
		}
	}

	public void confirm() {
		if (validCorrelationCheck()) {
			try {
				List<Payment> payments = new ArrayList<Payment>();
				PolicyReferenceType policyReferenceType = null;
				cashReceiptDTOList = new ArrayList<BillCollectionCashReceiptDTO>();
				int paymentTimes;
				if (policyCriteria.getReferenceType().equals(ReferenceType.ENDOWMENT_LIFE)) {
					policyReferenceType = PolicyReferenceType.LIFE_BILL_COLLECTION;
				} else if (policyCriteria.getReferenceType().equals(ReferenceType.SHORT_ENDOWMENT_LIFE)) {
					policyReferenceType = PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION;
				} else if (policyCriteria.getReferenceType().equals(ReferenceType.HEALTH)) {
					policyReferenceType = PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION;
				} else if (policyCriteria.getReferenceType().equals(ReferenceType.CRITICAL_ILLNESS)) {
					policyReferenceType = PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION;
				} else if (policyCriteria.getReferenceType().equals(ReferenceType.STUDENT_LIFE)) {
					policyReferenceType = PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION;
				}
				WorkFlowDTO workflowDTO = new WorkFlowDTO(crietriaBrach.getId(), crietriaBrach.getId(), remark, WorkflowTask.PAYMENT, policyCriteria.getReferenceType(),
						TransactionType.BILL_COLLECTION, user, responsiblePerson);
				for (BillCollectionDTO billCollection : selectedDTOList) {
					paymentTimes = billCollection.getPaymentTimes();
					Payment payment = new Payment();
					payment.setPolicyNo(billCollection.getPolicyNo());
					payment.setReferenceType(policyReferenceType);
					payment.setReferenceNo(billCollection.getPolicyId());
					payment.setBasicPremium(billCollection.getBasicTermPremium() * paymentTimes);
					payment.setAddOnPremium(billCollection.getAddOnTermPremium() * paymentTimes);
					payment.setNcbPremium(billCollection.getTotalNcbTermPremium() * paymentTimes);
					payment.setSpecialDiscount(billCollection.getTermSpecialDiscount() * paymentTimes);
					payment.setComplete(false);
					payment.setFromTerm(billCollection.getLastPaymentTerm());
					payment.setToTerm(billCollection.getLastPaymentTerm() + paymentTimes - 1);
					payment.setPaymentType(billCollection.getPaymentType());
					payment.setRefund(billCollection.getRefund());
					payment.setConfirmDate(new Date());
					payment.setRenewalInterest(billCollection.getRenewalInterest());
					payment.setLoanInterest(billCollection.getLoanInterest());
					payment.setServicesCharges(billCollection.getServiceCharges());
					payment.setAmount(payment.getNetPremium());
					payments.add(payment);
					cashReceiptDTOList.add(new BillCollectionCashReceiptDTO(payment, billCollection, policyCriteria.getReferenceType()));
				}
				paymentService.extendPaymentTimes(payments, workflowDTO);
				addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS);
				availablePrint = true;
				renderButton = false;
			} catch (SystemException ex) {
				handelSysException(ex);
			}
		}
	}

	public void editBill() {
		int paymentTimes = billCollection.getPaymentTimes();
		if (paymentTimes < 1) {
			billCollection.setPaymentTimes(1);
			addErrorMessage("lifePaymentDialogForm:paymentTimes", MessageId.PAYMENTTIMES_GRT_ONE);
		} else {
			Date date = billCollection.getStartDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, billCollection.getPaymentType().getMonth() * paymentTimes);
			billCollection.setCoverageDate(calendar.getTime());
			if (billCollection.getCoverageDate().after(billCollection.getEndDate())) {
				addErrorMessage("lifePaymentDialogForm:paymentTimes", MessageId.PAYMENTTIMES_BIGGER_ERROR);
			} else {
				PrimeFaces.current().executeScript("PF('lifePaymentDialog').hide()");
			}
		}
	}

	private boolean validCorrelationCheck() {
		boolean valid = true;
		String formID = "paymentBillCollectionForm";
		if (selectedDTOList.length == 0) {
			addErrorMessage(formID + ":lifePolicyInfoTable", MessageId.ATLEAST_ONE_CHECK_REQUIRED);
			valid = false;
		}
		return valid;
	}

	private final String reportName = "LifeBillCollectionCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public void generateReport() {
		if (selectedDTOList.length == 0) {
			addErrorMessage("paymentBillCollectionForm:lifePolicyInfoTable", MessageId.ATLEAST_ONE_CHECK_REQUIRED);
			return;
		} else {
			for (BillCollectionCashReceiptDTO dto : cashReceiptDTOList) {
				if (ReferenceType.HEALTH.equals(dto.getReferenceType()) || ReferenceType.CRITICAL_ILLNESS.equals(dto.getReferenceType())) {
					MedicalPolicy policy = medicalPolicyService.findMedicalPolicyById(dto.getBillCollection().getPolicyId());
					dto.setMedicalPolicy(policy);
				} else {
					LifePolicy policy = lifePolicyService.findLifePolicyById(dto.getBillCollection().getPolicyId());
					dto.setLifePolicy(policy);
				}
			}
			DocumentBuilder.generateBillCollectionPaymentInvoice(cashReceiptDTOList, dirPath, fileName);
		}
	}

	public boolean isAvailablePrint() {
		return availablePrint;
	}

	public BillCollectionDTO getBillCollection() {
		return billCollection;
	}

	public void setBillCollection(BillCollectionDTO lineBean) {
		this.billCollection = lineBean;
	}

	public BillCollectionModel getBillCollectionModel() {
		return new BillCollectionModel(billCollectionList);
	}

	public BillCollectionDTO[] getSelectedDTOList() {
		return selectedDTOList;
	}

	public void setSelectedDTOList(BillCollectionDTO[] selectedLineBeans) {
		this.selectedDTOList = selectedLineBeans;
	}

	public PolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public String getSumTotalAmounts() {
		double sumTotalAmounts = 0.0;
		if (billCollectionList != null) {
			for (BillCollectionDTO line : billCollectionList) {
				sumTotalAmounts += line.getTotalAmount();
			}
		}
		return Utils.getCurrencyFormatString(sumTotalAmounts);
	}

	public String getTotalSI() {
		double totalSI = 0.0;
		if (billCollectionList != null) {
			for (BillCollectionDTO line : billCollectionList) {
				totalSI += line.getSumInsured();
			}
		}
		return Utils.getCurrencyFormatString(totalSI);
	}

	public boolean isRenderButton() {
		return renderButton;
	}

	public void setRenderButton(boolean renderButton) {
		this.renderButton = renderButton;
	}

	public ReferenceType[] getReferenceTypeList() {
		return new ReferenceType[] { ReferenceType.ENDOWMENT_LIFE, ReferenceType.SHORT_ENDOWMENT_LIFE, ReferenceType.STUDENT_LIFE, ReferenceType.HEALTH,
				ReferenceType.CRITICAL_ILLNESS };
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BillCollectionDTO> getBillCollectionList() {
		if (billCollectionList == null) {
			billCollectionList = new ArrayList<BillCollectionDTO>();
		}
		return billCollectionList;
	}

	public void selectUser() {
		if (ReferenceType.SHORT_ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType()) || ReferenceType.ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType())
				|| ReferenceType.STUDENT_LIFE.equals(policyCriteria.getReferenceType())) {
			selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE, TransactionType.BILL_COLLECTION, crietriaBrach.getId(), null);
		} else if (ReferenceType.HEALTH.equals(policyCriteria.getReferenceType()) || ReferenceType.CRITICAL_ILLNESS.equals(policyCriteria.getReferenceType())) {
			selectUser(WorkflowTask.PAYMENT, WorkFlowType.MEDICAL_INSURANCE, TransactionType.BILL_COLLECTION, crietriaBrach.getId(), null);
		}
	}

	public List<BRA001> getBranchList() {
		return bra001List;
	}
}
