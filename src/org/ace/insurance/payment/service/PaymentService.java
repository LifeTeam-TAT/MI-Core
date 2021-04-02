package org.ace.insurance.payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PaymentReferenceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.payment.AccountPayment;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.CashDeno;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.payment.TLFBuilder;
import org.ace.insurance.payment.enums.DoubleEntry;
import org.ace.insurance.payment.persistence.interfacs.IAgentCommissionDAO;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.agent.COACode;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.coinsurancecompany.service.interfaces.ICoinsuranceCompanyService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.PaymentTableDTO;
import org.ace.insurance.web.manage.life.billcollection.BC0001;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PaymentService")
public class PaymentService extends BaseService implements IPaymentService {

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "CurrencyService")
	private ICurrencyService currencyService;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;

	@Resource(name = "MedicalPolicyService")
	private IMedicalPolicyService medicalPolicyService;

	@Resource(name = "BranchService")
	private IBranchService branchService;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Resource(name = "CustomerService")
	private ICustomerService customerService;

	@Resource(name = "OrganizationService")
	private IOrganizationService organizationService;

	@Resource(name = "CoinsuranceCompanyService")
	private ICoinsuranceCompanyService coinsuranceCompanyService;

	@Resource(name = "AgentCommissionDAO")
	private IAgentCommissionDAO agentCommissionDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> prePayment(List<Payment> paymentList) {
		try {
			String receiptNo = null;
			if (null != (paymentList.get(0).getPaymentChannel())) {
				if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.TRANSFER)) {
					receiptNo = customIDGenerator.getNextId(SystemConstants.TRANSFER_RECEIPT_NO, null);
				} else if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CASHED)) {
					receiptNo = customIDGenerator.getNextId(SystemConstants.CASH_RECEIPT_NO, null);
				} else if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
					receiptNo = customIDGenerator.getNextId(SystemConstants.CHEQUE_RECEIPT_NO, null);
				}

				for (Payment payment : paymentList) {
					payment.setReceiptNo(receiptNo);
					payment = paymentDAO.insert(payment);
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Payment", e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> preClaimPayment(List<Payment> paymentList) {
		try {
			for (Payment payment : paymentList) {
				payment = paymentDAO.insert(payment);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Payment", e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findActivedRate() {
		double rate = 0.0;
		try {
			rate = paymentDAO.findActiveRate();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Payment", e);
		}
		return rate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> prePaymentForChalen(List<Payment> paymentList) {
		try {
			for (Payment payment : paymentList) {
				payment = paymentDAO.insert(payment);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Payment", e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> prePaymentForClaim(List<Payment> paymentList) {
		try {
			for (Payment payment : paymentList) {
				payment = paymentDAO.insert(payment);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Payment", e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activatePaymentAndTLF(List<Payment> paymentList, List<AgentCommission> agentCommissionList, Branch branch) {
		try {
			/** update Payment **/
			String receiptNo = null;

			if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.TRANSFER)) {
				receiptNo = customIDGenerator.getNextId(SystemConstants.TRANSFER_RECEIPT_NO, null);
			} else if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CASHED)) {
				receiptNo = customIDGenerator.getNextId(SystemConstants.CASH_RECEIPT_NO, null);
			} else if (paymentList.get(0).getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				receiptNo = customIDGenerator.getNextId(SystemConstants.CHEQUE_RECEIPT_NO, null);
			}
			for (Payment payment : paymentList) {
				if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
					payment.setPO(true);
				}

				payment.setComplete(true);
				payment.setPaymentDate(new Date());
				payment.setReceiptNo(receiptNo);
				paymentDAO.update(payment);
			}
			/** update TLF **/
			List<TLF> tlfList = findTLFbyTLFNo(paymentList.get(0).getReceiptNo(), false);
			for (TLF tlf : tlfList) {
				tlf.setPaid(true);
				tlf.setSettlementDate(new Date());
				updateTLF(tlf);
			}

			/** Add AgentCommission **/
			if (agentCommissionList != null && !agentCommissionList.isEmpty()) {
				for (AgentCommission agentcommission : agentCommissionList) {
					agentcommission.setReceiptNo(receiptNo);
					paymentDAO.insertAgentCommission(agentcommission);
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the actual Payment", e);
		}
	}

	/**
	 * This method is used to activate clearing for TLF from 'clearing stage'.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void activateTLFClearing(List<Payment> paymentList) {
		try {
			for (Payment payment : paymentList) {
				List<TLF> tlfList = findTLFbyTLFNo(payment.getReceiptNo(), true);
				for (TLF tlf : tlfList) {
					tlf.setPaid(true);
					updateTLF(tlf);
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the tlf clearing", e);
		}
	}

	/**
	 * This method is no longer in use since payment stage call the method named
	 * 'activatePaymentAndTLF()'.
	 * 
	 * **Remove this method when all the changes that need to be made are done.
	 * **
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void activatePayment(List<AccountPayment> accountPaymentList, String customerId, Branch branch, List<AgentCommission> agentCommissionList) {
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activateClaimPayment(List<AccountPayment> accountPaymentList, Branch branch) {
		try {
			for (AccountPayment accountPayment : accountPaymentList) {
				Payment payment = accountPayment.getPayment();
				payment.setComplete(true);
				payment.setPaymentDate(new Date());
				payment.setReceiptNo(accountPayment.getPayment().getReceiptNo());
				payment = paymentDAO.update(payment);
			}
			// update TLF
			for (AccountPayment accountPayment : accountPaymentList) {
				List<TLF> tlfList = findTLFbyENONo(accountPayment.getPayment().getReceiptNo());
				for (TLF tlf : tlfList) {
					tlf.setPaid(true);
					tlf.setSettlementDate(new Date());
					updateTLF(tlf);
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the actual Payment", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addAgentCommission(List<AgentCommission> agentCommissionList) {
		try {
			for (AgentCommission agentCommission : agentCommissionList) {
				paymentDAO.insertAgentCommission(agentCommission);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add agent commission No into TLF.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentDTO> findPaymentByRecipNo(List<String> receiptList, PolicyReferenceType referenceType, Boolean complete) {
		List<PaymentDTO> result = null;
		try {
			result = paymentDAO.findByReceiptNo(receiptList, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by CahsReceiptNo : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByPolicy(String policyId) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findByPolicy(policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ReferenceNo : " + policyId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentCommission findAgentCommissionByReferenceNo(String referenceNo) {
		AgentCommission result = null;
		try {
			result = paymentDAO.findAgentCommissionByReferenceNo(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a AgentCommission by ReferenceNo : " + referenceNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findByProposal(proposalId, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentList by Proposal Id : " + proposalId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByPolicy(String policyId, PolicyReferenceType referenceType, Boolean complete) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findByPolicy(policyId, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentList by Proposal Id : " + policyId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByClaimProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findByClaimProposal(proposalId, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentList by Proposal Id : " + proposalId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findClaimProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete) {
		Payment result = null;
		try {
			result = paymentDAO.findClaimProposal(proposalId, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentList by Proposal Id : " + proposalId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findCheckOfAccountCode(String acccountName, Branch branch, String currencyCode) {
		String result = null;
		try {
			result = paymentDAO.findCheckOfAccountNameByCode(acccountName, branch.getBranchCode(), currencyCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CheckOfAccountCode", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findCOAAccountNameByCode(String acccountCode) {
		String result = null;
		try {
			result = paymentDAO.findCOAAccountNameByCode(acccountCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to findCOAAccountNameByCode", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addCashDeno(Payment payment, String customerId, Branch branch, String receiptDeno, String refundDeno) {
		try {
			CashDeno cashDeno = new CashDeno();
			cashDeno.setSourceBR(branch.getBranchCode());
			cashDeno.setAmount(payment.getTotalAmount());
			cashDeno.setFromType(payment.getReferenceType().name());
			cashDeno.setBranchCode(branch.getBranchCode());
			cashDeno.setDeno_detail(receiptDeno);
			cashDeno.setDenoRefund_detail(refundDeno);
			cashDeno.setSettlementDate(new Date());
			cashDeno.setCash_date(new Date());
			cashDeno.setStatus("R");
			cashDeno.setTlf_eno(payment.getReceiptNo());
			cashDeno.setUserNo(getLoginUser().getUsercode());
			cashDeno.setRate(1.0);
			cashDeno.setCur("KYT");
			paymentDAO.insertCashDeno(cashDeno);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByClaimProposalComplete(String proposalId, PolicyReferenceType referenceType) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findByClaimProposalComplete(proposalId, referenceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a PaymentList by Proposal Id : " + proposalId, e);
		}
		return result;
	}

	/* Bill Collection Generation */
	@Transactional(propagation = Propagation.REQUIRED)
	public void extendPaymentTimes(List<Payment> paymentList, WorkFlowDTO workflowDTO) {
		try {
			Currency cur = null;
			String invoiceNo = null;
			for (Payment payment : paymentList) {
				/* Payment */
				if (PolicyReferenceType.LIFE_BILL_COLLECTION.equals(payment.getReferenceType())) {
					invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, KeyFactorIDConfig.getPublicLifeId());
					cur = currencyService.findHomeCurrency();
				} else if (PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())) {
					invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, KeyFactorIDConfig.getShortEndowLifeId());
					cur = currencyService.findHomeCurrency();
				} else if (PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
					invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_INVOICE_NO, KeyFactorIDConfig.getIndividualHealthInsuranceId());
					cur = currencyService.findHomeCurrency();
				} else if (PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
					invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_INVOICE_NO, KeyFactorIDConfig.getIndividualCriticalIllness_Id());
					cur = currencyService.findHomeCurrency();
				} else if (PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
					invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, KeyFactorIDConfig.getStudentLifeId());
					cur = currencyService.findHomeCurrency();
				}
				payment.setInvoiceNo(invoiceNo);
				if (cur != null)
					payment.setCurrency(cur);
				payment = paymentDAO.insert(payment);

				/* Workflow */
				workflowDTO.setReferenceNo(invoiceNo);
				workFlowDTOService.addNewWorkFlow(workflowDTO);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert payment : " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReferenceNoAndMaxDate(String referenceNo) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findPaymentByReferenceNoAndMaxDate(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ReferenceNo and Max PaymentDate : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findPaymentByReferenceNo(String referenceNo) {
		Payment result = null;
		try {
			result = paymentDAO.findPaymentByReferenceNo(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ReferenceNo and Max PaymentDate : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReferenceNoAndMaxDateForAgentInvoice(String referenceNo) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findPaymentByReferenceNoAndMaxDateForAgentInvoice(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ReferenceNo and Max PaymentDate : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_PremiumDebitSnakeBite(Payment payment, String customerId, Branch branch, String currencyCode) {
		try {
			String coaCode = null;
			String enoNo = payment.getReceiptNo();
			String narration = "Cash receipt for " + enoNo;
			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				coaCode = paymentDAO.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(), currencyCode);
			} else {
				coaCode = paymentDAO.findCheckOfAccountNameByCode(COACode.CASH, branch.getBranchCode(), currencyCode);
			}

			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, payment.getNetPremium(), customerId, branch.getBranchCode(), coaCode, null, narration, payment, false);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setPaid(true);

			paymentDAO.insertTLF(tlf);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReceiptNo(String receiptNo) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findPaymentByReceiptNo(receiptNo);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (DAOException pe) {
			throw new SystemException(pe.getErrorCode(), "Fail findPaymentByReceiptNo()");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BC0001> findBCPaymentByPolicyNo(PolicyCriteria policyCriteria) {
		List<BC0001> result = null;
		try {
			result = paymentDAO.findBCPaymentByPolicyNo(policyCriteria);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (DAOException pe) {
			throw new SystemException(pe.getErrorCode(), "Fail findPaymentByReceiptNo()");
		}
		return result;
	}

	@Override
	public List<TLF> findTLFbyTLFNo(String tlfNo, Boolean isClearing) {
		List<TLF> results;
		try {
			results = paymentDAO.findTLFbyTLFNo(tlfNo, isClearing);
		} catch (DAOException pe) {
			throw new SystemException(pe.getErrorCode(), "Fail findPaymentByReceiptNo()");
		}

		return results;
	}

	@Override
	public List<TLF> findTLFbyENONo(String enoNo) {
		List<TLF> results;
		try {
			results = paymentDAO.findTLFbyENONo(enoNo);
		} catch (DAOException pe) {
			throw new SystemException(pe.getErrorCode(), "Fail findPaymentByReceiptNo()");
		}

		return results;
	}

	@Override
	public List<TLF> findTLFbyReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType policyReferenceType) {
		List<TLF> results;
		try {
			results = paymentDAO.findTLFbyReferenceNoAndReferenceType(referenceNo, policyReferenceType);
		} catch (DAOException pe) {
			throw new SystemException(pe.getErrorCode(), "Fail findPaymentByReceiptNo()");
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TLF updateTLF(TLF tlf) {
		try {
			tlf = paymentDAO.updateTLF(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Organization", e);
		}

		return tlf;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePayments(List<Payment> paymentList) {
		try {
			paymentDAO.deletePayments(paymentList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Customer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TLFVoucherDTO> findTLFVoucher(String receiptNo) {
		List<TLFVoucherDTO> tlfVoucherList = null;
		try {
			tlfVoucherList = paymentDAO.findTLFVoucher(receiptNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find tlfVoucher", e);
		}

		return tlfVoucherList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimVoucherDTO> findClaimVoucher(String receiptNo, String damage) {
		List<ClaimVoucherDTO> claimVoucherList = null;
		try {
			claimVoucherList = paymentDAO.findClaimVoucher(receiptNo, damage);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find claimVoucher", e);
		}

		return claimVoucherList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePayment(Payment payment, String damagedVehicleId, WorkFlowDTO workFlowDTO) {
		try {
			List<TLF> tlfList = new ArrayList<TLF>();
			paymentDAO.update(payment);
			tlfList = findTLFbyReferenceNoAndReferenceType(damagedVehicleId, null);
			for (TLF tlf : tlfList) {
				tlf.setPaid(true);
				tlf.setSettlementDate(new Date());
				updateTLF(tlf);
			}
			workFlowService.updateWorkFlow(workFlowDTO, WorkflowTask.FINISHED);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to pre-payment a LifeProposal ID : ", e);
		}
	}

	@Override
	public Payment findPaymentByReferenceNoAndIsComplete(String referenceNo, Boolean complete) {
		Payment p = new Payment();
		try {
			p = paymentDAO.findPaymentByReferenceNoAndIsComplete(referenceNo, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find tlfVoucher", e);
		}

		return p;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean findPaymentByReferenceNoAndIsNotComplete(String referenceNo) {
		boolean result;
		try {
			result = paymentDAO.findPaymentByReferenceNoAndIsNotComplete(referenceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Payment By ReferenceNo And IsNotComplete", e);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findByChalanNo(String chalanNo) {
		Payment result = null;
		try {
			result = paymentDAO.findByChalanNo(chalanNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ChalanNo : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentTableDTO> findByChalanNoForClaim(List<String> receiptList, PaymentReferenceType referenceType, Boolean complete) {
		List<PaymentTableDTO> result = null;
		try {
			result = paymentDAO.findByChalanNoForClaim(receiptList, referenceType, complete);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ChalanNo : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activateMedicalClaimPayment(Payment payment) {
		try {
			payment = paymentDAO.update(payment);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by ChalanNo :", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentTrackDTO> findPaymentTrack(String policyNo) {
		List<PaymentTrackDTO> paymentTrackList = null;
		try {
			paymentTrackList = paymentDAO.findPaymentTrack(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find payment track for policyNo=" + policyNo, e);
		}
		return paymentTrackList;
	}

	/**
	 * This method is used to activate Payment at 'payment stage'.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void activatePayment(List<Payment> paymentList, String policyNo, double rate) throws SystemException {
		try {
			/** update Payment **/
			PaymentChannel channel = paymentList.get(0).getPaymentChannel();
			PolicyReferenceType refType = paymentList.get(0).getReferenceType();
			String receiptNo = null;
			switch (refType) {

				case SHORT_ENDOWMENT_LIFE_POLICY:
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.SHORT_ENDOWMENT_RECEIPT_NO, null);
					break;
				case GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY:
				case GOVERNMENT_SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.GOVERNMENT_SHORT_ENDOWMENT_RECEIPT_NO, null);
					break;
				case FARMER_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.FARMER_RECEIPT_NO, null);
					break;
				case PA_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.PA_RECEIPT_NO, null);
					break;
				case SPORT_MAN_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.SPORT_MAN_RECEIPT_NO, null);
					break;
				case ENDOWNMENT_LIFE_POLICY:
				case LIFE_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.ENDOWNMENT_RECEIPT_NO, null);
					break;
				case GROUP_LIFE_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.GROUP_LIFE_RECEIPT_NO, null);
					break;
				case STUDENT_LIFE_POLICY:
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.STUDENT_LIFE_RECEIPT_NO, KeyFactorChecker.getStudentLifeID());
					break;
				case HEALTH_POLICY:
				case HEALTH_POLICY_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_RECEIPT_NO, null);
					break;
				case CRITICAL_ILLNESS_POLICY:
				case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.CRITICAL_ILLNESS_RECEIPT_NO, null);
					break;
				case MICRO_HEALTH_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.MICRO_HEALTH_RECEIPT_NO, null);
					break;
				case PUBLIC_TERM_LIFE_POLICY:
					receiptNo = customIDGenerator.getCustomNextId(SystemConstants.PUBLIC_TERM_LIFE_RECEIPT_NO, null);
					break;
				default:
					if (channel.equals(PaymentChannel.CASHED)) {
						receiptNo = customIDGenerator.getNextId(SystemConstants.CASH_RECEIPT_NO, null);
					} else if (channel.equals(PaymentChannel.CHEQUE)) {
						receiptNo = customIDGenerator.getNextId(SystemConstants.CHEQUE_RECEIPT_NO, null);
					} else if (channel.equals(PaymentChannel.TRANSFER)) {
						receiptNo = customIDGenerator.getNextId(SystemConstants.TRANSFER_RECEIPT_NO, null);
					}
					break;
			}

			for (Payment payment : paymentList) {
				if (channel.equals(PaymentChannel.CHEQUE))
					payment.setPO(true);
				payment.setComplete(true);
				payment.setPaymentDate(new Date());
				payment.setReceiptNo(receiptNo);
				payment.setRate(rate);
				payment.setPolicyNo(policyNo);
				paymentDAO.update(payment);
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the actual Payment", e);
		}
	}

	/**
	 * 
	 * @param invoiceNo
	 * @return Payment of invoiceNo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findByInvoiceNo(String invoiceNo) {
		Payment result = null;
		try {
			result = paymentDAO.findByInvoiceNo(invoiceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by invoiceNo : ", e);
		}
		return result;
	}

	/**
	 * 
	 * @param invoiceNo
	 * @return Payment of invoiceNo
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentListByInvoiceNo(String invoiceNo) {
		List<Payment> result = null;
		try {
			result = paymentDAO.findListByInvoiceNo(invoiceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Payment by invoiceNo : ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activateClaimPayment(List<Payment> paymentList, String policyNo, double rate) throws SystemException {
		try {
			/** update Payment **/
			PaymentChannel channel = paymentList.get(0).getPaymentChannel();
			PolicyReferenceType refType = paymentList.get(0).getReferenceType();
			String receiptNo = null;

			receiptNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_CLAIM_PAYMENT_VOUCHER_NO);

			for (Payment payment : paymentList) {
				if (channel.equals(PaymentChannel.CHEQUE))
					payment.setPO(true);
				payment.setComplete(true);
				payment.setPaymentDate(new Date());
				payment.setReceiptNo(receiptNo);
				payment.setRate(rate);
				payment.setPolicyNo(policyNo);
				paymentDAO.update(payment);
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the actual Payment", e);
		}
	}

}
