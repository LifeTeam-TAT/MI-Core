package org.ace.insurance.web.manage.life.studentLife.confirm;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewStudentLifeProposalReceiptActionBean")
public class AddNewStudentLifeProposalReceiptActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private WorkFlowDTO workFlowDTO;
	private PaymentDTO payment;
	private String fileName;
	private boolean actualPayment;
	private int periodInsurance;
	private int insuredAge;
	private int maxTerm;
	private ProposalInsuredPerson insuredPerson;
	private PaymentDTO paymentDTO;
	private Branch branch;
	private String status;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO) getParam("workFlowDTO") : workFlowDTO;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
		removeParam("workFlowDTO");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		payment = new PaymentDTO();
		AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(lifeProposal.getId());
		if (acceptedInfo != null) {
			payment.setDiscountPercent(acceptedInfo.getDiscountPercent());
			payment.setServicesCharges(acceptedInfo.getServicesCharges());
			payment.setStampFees(acceptedInfo.getStampFeesAmount());
		}
		payment.setConfirmDate(new Date());
		fileName = "StudentLifeCashReceipt.pdf";
		insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
		maxTerm = insuredPerson.getProduct().getMaxTerm();
		changeConfirmDate();
	}

	public void addNewLifeProposalReceiptInfo() {
		try {
			if (insuredAge < 1 || insuredAge > 12) {
				addErrorMessage("proposalReceiiptEntryForm:insuredPersonAge", MessageId.CHILD_AGE_MUST_BE_BETWEEN_30DAYS_AND_12YEARS);
			} else {
				List<Payment> paymentList = null;
				if (lifeProposal.isSkipPaymentTLF()) {
					paymentList = lifeProposalService.confirmSkipPaymentTLFLifeProposal(lifeProposal, workFlowDTO, paymentDTO, branch, status);
				} else {
					paymentList = lifeProposalService.confirmLifeProposal(lifeProposal, workFlowDTO, paymentDTO, branch, status);
				}
				paymentDTO = new PaymentDTO(paymentList);
				actualPayment = true;
				addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	private void setKeyFactorValue(ProposalInsuredPerson person) {
		for (InsuredPersonKeyFactorValue vehKF : person.getKeyFactorValueList()) {
			KeyFactor kf = vehKF.getKeyFactor();
			if (KeyFactorChecker.isAge(kf)) {
				vehKF.setValue(person.getAge() + "");
			}
			if (KeyFactorChecker.isTerm(kf)) {
				vehKF.setValue(lifeProposal.getPeriodOfYears() + "");
			}
		}
	}

	public void changeConfirmDate() {
		periodInsurance = maxTerm - insuredAge + 1;
		insuredAge = Utils.getAgeForNextYear(insuredPerson.getDateOfBirth(), lifeProposal.getStartDate());
		insuredPerson.setAge(insuredAge);
		lifeProposal.setPeriodMonth(periodInsurance * 12);
		if (insuredAge < 1 || insuredAge > 12) {
			addErrorMessage("proposalReceiiptEntryForm:insuredPersonAge", MessageId.CHILD_AGE_MUST_BE_BETWEEN_30DAYS_AND_12YEARS);
			return;
		}
		setKeyFactorValue(insuredPerson);
		lifeProposalService.calculatePremium(lifeProposal);
	}

	public int getAgeForNextYear(Date dateOfBirth) {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(payment.getConfirmDate());
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);

		if (DateUtils.resetEndDate(payment.getConfirmDate()).after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	public void changePaymentChannel(AjaxBehaviorEvent event) {
		payment.setAccountBank(null);
		payment.setBank(null);
		payment.setChequeNo(null);
		payment.setPoNo(null);
	}

	public PaymentChannel[] getChannelValues() {
		return PaymentChannel.values();
	}

	public boolean isActualPayment() {
		return actualPayment;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	private final String reportName = "StudentLifeCashReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private String fullTemplateFilePath;
	private List<TLFVoucherDTO> cashReceiptDTOList;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		String customerName = lifeProposal.getCustomerName();
		if (customerName.contains("\\")) {
			customerName = customerName.replace("\\", "");
		}
		if (customerName.contains("/")) {
			customerName = customerName.replace("/", "");
		}
		fileName = "StudentLife_" + customerName + "_Receipt" + ".pdf";
		DocumentBuilder.generateTLFVoucher(cashReceiptDTOList, fullTemplateFilePath, dirPath, fileName);
	}

	public void returnAccountBank(SelectEvent event) {
		Bank accountBank = (Bank) event.getObject();
		payment.setAccountBank(accountBank);
	}

	public void returnBank(SelectEvent event) {
		Bank bank = (Bank) event.getObject();
		payment.setBank(bank);
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Date getToday() {
		return new Date();
	}

	public String getPageHeader() {
		return "Student Life Proposal Confirm Print";
	}

	public int getPeriodInsurance() {
		return periodInsurance;
	}

	public void setPeriodInsurance(int periodInsurance) {
		this.periodInsurance = periodInsurance;
	}

	public int getInsuredAge() {
		return insuredAge;
	}

	public void setInsuredAge(int insuredAge) {
		this.insuredAge = insuredAge;
	}

}
