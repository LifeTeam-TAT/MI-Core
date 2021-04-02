package org.ace.insurance.web.manage.life.claim.payment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.service.interfaces.IClaimMedicalFeesService;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.proxy.MEDFEES001;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewMedicalFeesClaimPaymentActionBean")
public class AddNewMedicalFeesClaimPaymentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService claimProposalService;

	public void setClaimProposalService(ILifeClaimProposalService claimProposalService) {
		this.claimProposalService = claimProposalService;
	}

	@ManagedProperty(value = "#{ClaimMedicalFeesService}")
	private IClaimMedicalFeesService claimMedicalFeesService;

	public void setClaimMedicalFeesService(IClaimMedicalFeesService claimMedicalFeesService) {
		this.claimMedicalFeesService = claimMedicalFeesService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;

	private User user;
	private MEDFEES001 dto;
	private SalesPoints salesPoint;
	private String payee;
	private boolean isPrint;;
	private List<ClaimMedicalFees> claimMedicalFeesList;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		dto = (dto == null) ? (MEDFEES001) getParam("MEDCALFEESDTO") : dto;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		claimMedicalFeesList = claimMedicalFeesService.findMedicalFeesByInvoiceNo(dto.getInvoiceNo());
	}

	@PreDestroy
	public void destroy() {
		removeParam("MEDCALFEESDTO");
	}

	public String payMedicalFees() {
		String result = null;
		try {
			claimProposalService.paymentMedicalFeesInvoice(claimMedicalFeesList, salesPoint, user.getBranch());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.PAYMENT_PROCESS_SUCCESS);
			isPrint = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	/** Generate Report */
	private final String reportName = "LifeReceipt";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = "ClaimMeidcalFeesPayment.pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generatePaymentLetter() {
		try {
			List<PolicyInsuredPerson> insuredPersonList = new ArrayList<>();
			for (ClaimMedicalFees claimMedicalFees : claimMedicalFeesList) {
				insuredPersonList.add(claimMedicalFees.getClaimPerson());
			}
			DocumentBuilder.generateLifeClaimMedicalFeesPaymentLetters(dto, dirPath, fileName, payee, insuredPersonList);
		} catch (SystemException se) {
			handelSysException(se);
		}
	}

	public void returnSalesPoints(SelectEvent event) {
		salesPoint = (SalesPoints) event.getObject();
	}

	public SalesPoints getSalesPoint() {
		return salesPoint;
	}

	public void setSalesPoint(SalesPoints salesPoint) {
		this.salesPoint = salesPoint;
	}

	public MEDFEES001 getDto() {
		return dto;
	}

	public void setDto(MEDFEES001 dto) {
		this.dto = dto;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

}
