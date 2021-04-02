package org.ace.insurance.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.cashreceipt.CashReceiptCriteria;
import org.ace.insurance.cashreceipt.CashReceiptDTO;
import org.ace.insurance.cashreceipt.LifeCashReceiptListReportDTO;
import org.ace.insurance.cashreceipt.service.interfaces.ICashReceiptService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "CashReceiptPrintActionBean")
public class CashReceiptPrintActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	@ManagedProperty(value = "#{CashReceiptService}")
	private ICashReceiptService cashReceiptService;

	public void setCashReceiptService(ICashReceiptService cashReceiptService) {
		this.cashReceiptService = cashReceiptService;
	}

	private String printTitle;
	private List<CashReceiptDTO> selectedConfirmationList;
	private PaymentDTO payment;
	private List<LifeCashReceiptListReportDTO> lifeCashReceiptList;
	CashReceiptCriteria criteria;
	private boolean isCheque;
	private boolean showPrintPreview;
	private User responsiblePerson;
	private String remark;
	private ReferenceType referenceType;

	@PostConstruct
	public void init() {
		payment = new PaymentDTO();
		referenceType = (ReferenceType) getParam("WorkFlowType");
	}

	public void confirmProposalList() {
		try {
			if (responsiblePerson == null) {
				addErrorMessage("cashReceiptForm" + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				return;
			}
			if (!validPayment()) {
				return;
			}
			if (isCheque) {
				payment.setPaymentChannel(PaymentChannel.CHEQUE);
			} else {
				payment.setPaymentChannel(PaymentChannel.CASHED);
			}
			List<CashReceiptDTO> selectedConfirmationList = (List<CashReceiptDTO>) getParam("ConfirmationList");

			if (referenceType.equals(ReferenceType.ENDOWMENT_LIFE)) {
				lifeCashReceiptList = cashReceiptService.confirmLifeProposalsForCashReceipt(selectedConfirmationList, referenceType, payment, responsiblePerson, user, remark);
			}
			setShowPrintPreview(true);
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private boolean validPayment() {
		boolean valid = true;
		String formID = "cashReceipForm";
		if (isCheque) {
			if (payment.getChequeNo() == null || payment.getChequeNo().isEmpty()) {
				addErrorMessage(formID + ":chequeNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (payment.getBank() == null) {
				addErrorMessage(formID + ":bankName", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		return valid;
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		if (!isCheque) {
			payment.setBank(null);
			payment.setChequeNo(null);
		}
	}

	public String getReportStream() {
		String fileFullPath = getPDFDirPath() + getReportName() + ".pdf";
		System.out.println(fileFullPath);
		return fileFullPath;
	}

	private final String milliSeconds = System.currentTimeMillis() + "";

	public String getPDFDirPath() {
		return "/pdf-report/" + getReportName() + "/" + milliSeconds + "/";
	}

	public String getReportName() {
		String reportName = null;
		if (criteria.getReferenceType().equals(ReferenceType.ENDOWMENT_LIFE)) {
			reportName = "LifeCashReceipt";
		}
		return reportName;
	}

	public String getDirPath() {
		return getWebRootPath() + getPDFDirPath();
	}

	public void generateReport() {
		try {
			FileHandler.forceMakeDirectory(getDirPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (criteria.getReferenceType().equals(ReferenceType.ENDOWMENT_LIFE)) {
			// DocumentBuilder.generateLifeCashReceiptList(lifeCashReceiptList,
			// getDirPath(), getReportName() + ".pdf");
		}
	}

	public void select() {
		if (criteria.getReferenceType() != null) {
			// if
			// (criteria.getReferenceType().equals(ReferenceType.ENDOWMENT_LIFE))
			// {
			// selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE);
			// }
		}
	}

	public boolean getIsCheque() {
		return isCheque;
	}

	public void setIsCheque(boolean isCheque) {
		this.isCheque = isCheque;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public boolean isShowPrintPreview() {
		return showPrintPreview;
	}

	public void setShowPrintPreview(boolean showPrintPreview) {
		this.showPrintPreview = showPrintPreview;
	}

	public List<LifeCashReceiptListReportDTO> getLifeCashReceiptList() {
		return lifeCashReceiptList;
	}

	public void setLifeCashReceiptList(List<LifeCashReceiptListReportDTO> lifeCashReceiptList) {
		this.lifeCashReceiptList = lifeCashReceiptList;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		payment.setBank(bank);
	}

	public void selectUser() {
		switch (referenceType) {
			// case MOTOR:
			// selectUser(WorkflowTask.PAYMENT, WorkFlowType.MOTOR);
			// break;
			// case FIRE:
			// selectUser(WorkflowTask.PAYMENT, WorkFlowType.FIRE);
			// break;
			// case ENDOWMENT_LIFE:
			// selectUser(WorkflowTask.PAYMENT, WorkFlowType.LIFE);
			// break;
		}
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}
