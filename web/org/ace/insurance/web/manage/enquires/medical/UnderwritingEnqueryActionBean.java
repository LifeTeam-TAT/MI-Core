package org.ace.insurance.web.manage.enquires.medical;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.proposal.MP001;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.common.document.DocumentBuilder;
//import org.ace.insurance.web.common.document.medical.MedicalUnderwritingDocFactory;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "UnderwritingEnqueryActionBean")
public class UnderwritingEnqueryActionBean extends BaseBean {
	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	boolean approved = false;
	private EnquiryCriteria criteria;
	private PaymentDTO paymentDTO;
	private User user;
	private List<MP001> proposalList;
	private String message;

	@PostConstruct
	public void init() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new EnquiryCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setProposalType(ProposalType.UNDERWRITING);
		criteria.setEndDate(endDate);
		proposalList = new ArrayList<>();
		criteria.setBranch(user.getLoginBranch());
	}

	public void search() {
		proposalList = medicalProposalService.findAllMedicalPolicy(criteria);
		sortLists(proposalList);
	}

	public void sortLists(List<MP001> proposalList) {
		Collections.sort(proposalList, new Comparator<MP001>() {
			@Override
			public int compare(MP001 obj1, MP001 obj2) {
				if (obj1.getProposalNo().equals(obj2.getProposalNo()))
					return -1;
				else
					return obj1.getProposalNo().compareTo(obj2.getProposalNo());
			}
		});
	}

	private void showInformationDialog(String msg) {
		this.message = msg;
		PrimeFaces.current().executeScript("PF('informationDialog').show()");
	}

	public String editMedicalProposal(MP001 mp001) {
		String result = null;
		if (allowToEdit(mp001.getId())) {
			MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(mp001.getId());
			outjectMedicalProposal(medicalProposal);
			result = "editMedicalProposal";
		} else {
			result = null;
		}
		return result;
	}

	public void outjectMedicalProposal(MedicalProposal medicalProposal) {
		putParam("enquiryEditMedicalProposal", medicalProposal);
	}

	private boolean allowToEdit(String refNo) {
		boolean flag = true;
		WorkFlow wf = workFlowService.findWorkFlowByRefNo(refNo);
		if (wf == null) {
			flag = false;
			this.message = "This proposal has been legalized as policy contract.";
			PrimeFaces.current().executeScript("PF('informationDialog').show()");
		} else {
			if (WorkflowTask.SURVEY.equals(wf.getWorkflowTask()) || WorkflowTask.INFORM.equals(wf.getWorkflowTask()) || WorkflowTask.CONFIRMATION.equals(wf.getWorkflowTask())
					|| WorkflowTask.APPROVAL.equals(wf.getWorkflowTask())) {
				flag = true;
			} else {
				flag = false;
				this.message = "This proposal is not in the editable workflow phase. It's currently at " + wf.getWorkflowTask().getLabel() + " phase";
				PrimeFaces.current().executeScript("PF('informationDialog').show()");
			}
		}
		return flag;
	}

	private boolean allowToPrint(String id, WorkflowTask... workflowTasks) {
		List<WorkFlowHistory> wfhList = workFlowService.findWorkFlowHistoryByRefNo(id, workflowTasks);
		if (wfhList == null || wfhList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/*************** Sanction letter *************/

	private final String reportName = "Medical";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateAcceptanceLetter(MP001 mProposal) {
		boolean allowToPrint = allowToPrint(mProposal.getId(), WorkflowTask.CONFIRMATION, WorkflowTask.PAYMENT, WorkflowTask.ISSUING);
		if (allowToPrint) {
			MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(mProposal.getId());
			if (checkApproved(medicalProposal)) {
				AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(mProposal.getId());
				DocumentBuilder.generateMedicalAcceptanceLetter(medicalProposal, acceptedInfo, dirPath, fileName);
			} else {
				DocumentBuilder.generateMedicalRejectLetter(medicalProposal, dirPath, fileName);
			}
			PrimeFaces.current().executeScript("PF('pdfDialog').show()");
		} else {
			showInformationDialog(getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.INFORM.getLabel().toLowerCase()));
		}
	}

	public void generateInvoice(MP001 mProposal) {
		boolean allowToPrint = allowToPrint(mProposal.getId(), WorkflowTask.PAYMENT, WorkflowTask.ISSUING);
		if (allowToPrint) {
			MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(mProposal.getId());
			MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyByProposalId(mProposal.getId());
			List<Payment> paymentList = paymentService.findByPolicy(medicalPolicy.getId());
			paymentDTO = new PaymentDTO(paymentList);
			DocumentBuilder.generateMedicalPaymentInvoice(medicalProposal, paymentDTO, dirPath, fileName);
			PrimeFaces.current().executeScript("PF('pdfDialog').show()");
		} else {
			showInformationDialog(getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.INFORM.getLabel().toLowerCase()));
		}
	}

	public void generateCashReceipt(MP001 proposal) {
		boolean allowToPrint = allowToPrint(proposal.getId(), WorkflowTask.ISSUING);
		if (allowToPrint) {
			MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(proposal.getId());
			MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyByProposalId(proposal.getId());
			Payment payment = paymentService.findPaymentByReferenceNo(medicalPolicy.getId());
			DocumentBuilder.generateMedicalReceiptLetter(medicalProposal, payment, dirPath, fileName);
			PrimeFaces.current().executeScript("PF('pdfDialog').show()");
		} else {
			showInformationDialog(getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.CONFIRMATION.getLabel().toLowerCase()));
		}
	}

	public void generatePolicyIssue(MP001 medicalProposalDTO) {
		boolean allowToPrint = allowToPrint(medicalProposalDTO.getId(), WorkflowTask.ISSUING);
		if (allowToPrint) {
			MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyByProposalId(medicalProposalDTO.getId());
			Payment payment = paymentService.findPaymentByReferenceNo(medicalPolicy.getId());
			DocumentBuilder.generateMedicalPolicyIssueLetter(medicalPolicy, payment, dirPath, fileName);
			PrimeFaces.current().executeScript("PF('pdfDialog').show()");
		} else {
			showInformationDialog(getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.ISSUING.getLabel().toLowerCase()));
		}
	}

	public void openTemplateDialog(MP001 dto) {
		MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(dto.getId());
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList(dto.getId()));
		openMedicalProposalInfoTemplate();
	}

	private boolean checkApproved(MedicalProposal medicalProposal) {
		boolean approved = false;
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			if (person.isApproved()) {
				approved = true;
			}
		}
		return approved;
	}

	public ProposalType[] getProposalTypes() {
		ProposalType[] types = { ProposalType.UNDERWRITING, ProposalType.ENDORSEMENT, ProposalType.RENEWAL };
		return types;
	}

	public List<Branch> getBranches() {
		return user.getAccessBranchList();
	}

	public List<WorkFlowHistory> getWorkFlowList(String proposalId) {
		return workFlowService.findWorkFlowHistoryByRefNo(proposalId);
	}

	public String getMessage() {
		return message;
	}

	public EnquiryCriteria getCriteria() {
		return criteria;
	}

	public List<MP001> getProposalList() {
		RegNoSorter<MP001> regNoSorter = new RegNoSorter<MP001>(proposalList);
		List<MP001> proposalList = regNoSorter.getSortedList();
		return proposalList;
	}

	public void setCriteria(EnquiryCriteria criteria) {
		this.criteria = criteria;
	}

	public List<SaleChannelType> getSaleChannel() {
		return Arrays.asList(SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		criteria.setCustomer(customer);
	}

	public void returnSaleBank(SelectEvent event) {
		BankBranch bankBranch = (BankBranch) event.getObject();
		criteria.setSaleBank(bankBranch);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		criteria.setOrganization(organization);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProduct(product);
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePoint(salePoint);
	}

}
