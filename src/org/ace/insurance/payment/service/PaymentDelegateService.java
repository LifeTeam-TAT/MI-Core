package org.ace.insurance.payment.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyLog.persistence.interfaces.ILifePolicyTimeLineLogDAO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.persistence.interfacs.IAgentCommissionDAO;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentDelegateService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.payment.service.interfaces.ITlfProcessor;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.user.User;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PaymentDelegateService")
public class PaymentDelegateService extends BaseService implements IPaymentDelegateService {
	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "LifeProposalService")
	private ILifeProposalService lifeProposalService;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;
	@Resource(name = "MedicalPolicyService")
	private IMedicalPolicyService medicalPolicyService;
	@Resource(name = "MedicalProposalService")
	private IMedicalProposalService medicalProposalService;
	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;
	@Resource(name = "TlfProcessor")
	private ITlfProcessor tlfProcessor;
	@Resource(name = "AgentCommissionDAO")
	private IAgentCommissionDAO agentCommissionDAO;
	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "LifePolicyTimeLineLogDAO")
	private ILifePolicyTimeLineLogDAO lifePolicyTimeLineLogDAO;
	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void payment(List<PaymentDTO> paymentDTOList, PolicyReferenceType referenceType, User responsiblePerson, String remark, Branch branch) throws SystemException {
		// TODO fixe me
		String loginBranchId = "";
		for (PaymentDTO dto : paymentDTOList) {

			LifeProposal lifeProposal;
			MedicalProposal medicalProposal;
			LifePolicy lifePolicy;
			MedicalPolicy medicalPolicy;
			// ShortEndowmentExtraValue extraValue = null;
			switch (referenceType) {

				// FIXME CHECK REFTYPE
				case ENDOWNMENT_LIFE_POLICY: {
					lifeProposal = lifePolicyService.findLifePolicyById(dto.getReferenceNo()).getLifeProposal();
					List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), PolicyReferenceType.ENDOWNMENT_LIFE_POLICY, false);
					WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), loginBranchId, remark, WorkflowTask.ISSUING, ReferenceType.ENDOWMENT_LIFE, null,
							responsiblePerson, responsiblePerson);
					lifeProposalService.paymentLifeProposal(lifeProposal, workFlowDTO, paymentList, branch, null);
					paymentService.activatePaymentAndTLF(paymentList, null, null);
				}
					break;
				case HEALTH_POLICY:
				case CRITICAL_ILLNESS_POLICY:
				case MICRO_HEALTH_POLICY: {
					medicalProposal = medicalPolicyService.findMedicalPolicyById(dto.getReferenceNo()).getMedicalProposal();
					List<Payment> paymentList = paymentService.findByProposal(medicalProposal.getId(), referenceType, false);
					WorkflowTask workflowTask = null;
					ReferenceType wfReferenceType = null;
					switch (referenceType) {
						case HEALTH_POLICY:
							workflowTask = WorkflowTask.ISSUING;
							wfReferenceType = ReferenceType.HEALTH;
							break;
						case CRITICAL_ILLNESS_POLICY:
							workflowTask = WorkflowTask.ISSUING;
							wfReferenceType = ReferenceType.CRITICAL_ILLNESS;
							break;
						case MICRO_HEALTH_POLICY:
							workflowTask = WorkflowTask.ISSUING;
							wfReferenceType = ReferenceType.MICRO_HEALTH;
							break;
						default:
							break;
					}
					WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, workflowTask, wfReferenceType, null, responsiblePerson,
							responsiblePerson);
					medicalProposalService.paymentMedicalProposal(medicalProposal, workFlowDTO, paymentList, branch);
					paymentService.activatePaymentAndTLF(paymentList, null, null);
				}
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY: {
					/*
					 * lifeProposal =
					 * lifePolicyService.findLifePolicyById(dto.getReferenceNo()
					 * ).getLifeProposal(); ReferenceType proposalReferenceType
					 * = ReferenceType.SHORT_ENDOWMENT_LIFE_PROPOSAL;
					 * List<Payment> paymentList =
					 * paymentService.findByProposal(lifeProposal.getId(),
					 * referenceType, false); WorkFlowDTO workFlowDTO = new
					 * WorkFlowDTO(lifeProposal.getId(), remark,
					 * WorkflowTask.ISSUING, proposalReferenceType,
					 * responsiblePerson, responsiblePerson);
					 * lifeProposalService.paymentLifeProposal(lifeProposal,
					 * workFlowDTO, paymentList, branch, null);
					 * 
					 * String currencyCode =
					 * CurrencyUtils.getCurrencyCode(null);
					 * paymentService.activatePaymentAndTLF(paymentList, null,
					 * null, currencyCode);
					 */
				}
					break;
				case LIFE_BILL_COLLECTION: {
					lifePolicy = lifePolicyService.findLifePolicyById(dto.getReferenceNo());
					List<Payment> paymentList = paymentService.findPaymentByReceiptNo(dto.getReceiptNo());
					List<AgentCommission> agentCommissioList = null;
					if (lifePolicy.getAgent() != null) {
						agentCommissioList = getAgentCommissionsForLifeBillCollection(lifePolicy, paymentList.get(0));
					}
					int paymentTimes = (dto.getToTerm() - dto.getFromTerm()) + 1;
					Date calculatedEndDate = getCalculatedEndDate(paymentTimes, lifePolicy.getActivedPolicyEndDate(), lifePolicy.getPaymentType());
					lifePolicy.setActivedPolicyEndDate(calculatedEndDate);
					lifePolicy.setLastPaymentTerm(dto.getToTerm());
					lifePolicyService.activateBillCollection(lifePolicy);
					paymentService.activatePaymentAndTLF(paymentList, agentCommissioList, lifePolicy.getBranch());
				}
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					/*
					 * { lifePolicy =
					 * lifePolicyService.findLifePolicyById(dto.getReferenceNo()
					 * ); List<Payment> paymentList =
					 * paymentService.findPaymentByReceiptNo(dto.getReceiptNo())
					 * ; List<AgentCommission> agentCommissioList = null;
					 * 
					 * if (lifePolicy.isHasExtraValue()) { extraValue =
					 * shortEndowmentExtraValueService.
					 * findShortEndowmentExtraValueByPolicyNo(lifePolicy.
					 * getPolicyNo()); }
					 * 
					 * if (lifePolicy.getAgent() != null) { agentCommissioList =
					 * getAgentCommissionsForLifeBillCollection(lifePolicy,
					 * paymentList.get(0), extraValue); } int paymentTimes =
					 * (dto.getToTerm() - dto.getFromTerm()) + 1; Date
					 * calculatedEndDate = getCalculatedEndDate(paymentTimes,
					 * lifePolicy.getActivedPolicyEndDate(),
					 * lifePolicy.getPaymentType());
					 * lifePolicy.setActivedPolicyEndDate(calculatedEndDate);
					 * lifePolicy.setLastPaymentTerm(dto.getToTerm());
					 * 
					 * if (extraValue != null && !extraValue.isPaid()) {
					 * extraValue.setPaid(true);
					 * shortEndowmentExtraValueService.
					 * updateShortEndowmentExtraValue(extraValue);
					 * lifePolicy.setHasExtraValue(false); }
					 * 
					 * lifePolicyService.activateBillCollection(lifePolicy);
					 * paymentService.activatePaymentAndTLF(paymentList,
					 * agentCommissioList, lifePolicy.getBranch(), null); }
					 */
					break;
				case HEALTH_POLICY_BILL_COLLECTION:
				case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION: {
					medicalPolicy = medicalPolicyService.findMedicalPolicyById(dto.getReferenceNo());
					List<Payment> paymentList = paymentService.findPaymentByReceiptNo(dto.getReceiptNo());
					List<AgentCommission> agentCommissioList = null;
					if (medicalPolicy.getAgent() != null) {
						agentCommissioList = getAgentCommissionsForMedicalBillCollection(medicalPolicy, paymentList.get(0), referenceType);
					}
					int paymentTimes = (dto.getToTerm() - dto.getFromTerm()) + 1;
					Date calculatedEndDate = getCalculatedEndDate(paymentTimes, medicalPolicy.getActivedPolicyEndDate(), medicalPolicy.getPaymentType());
					medicalPolicy.setActivedPolicyEndDate(calculatedEndDate);
					medicalPolicy.setLastPaymentTerm(dto.getToTerm());
					medicalPolicyService.activateBillCollection(medicalPolicy);
					paymentService.activatePaymentAndTLF(paymentList, agentCommissioList, medicalPolicy.getBranch());
				}
					break;

				case LIFE_CLAIM:
				case LIFE_DIS_CLAIM: {
				}
					break;
			}
		}
	}

	/* Bill Collection Payment (Fire,Life,Health) */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void billCollectionPayment(Payment payment, Branch branch) {
		AgentCommission agentCommission = null;
		List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
		List<Payment> paymentList = new ArrayList<Payment>();
		List<TlfData> dataList = new ArrayList<>();
		TlfData tlfData = null;
		double rate = 1.0;
		Currency currency;
		int paymentTimes = payment.getPaymentTimes();
		PolicyReferenceType policyRefType = payment.getReferenceType();
		int fromTerm = payment.getFromTerm();
		int toTerm = payment.getToTerm();

		switch (policyRefType) {

			case STUDENT_LIFE_POLICY_BILL_COLLECTION:
			case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
			case LIFE_BILL_COLLECTION: {
				LifePolicy policy = lifePolicyService.findLifePolicyById(payment.getReferenceNo());
				String policyNo = policy.getPolicyNo();
				currency = payment.getCurrency();
				payment.setRate(rate);
				paymentList.add(payment);
				paymentService.activatePayment(paymentList, policyNo, rate);

				int month = policy.getPaymentType().getMonth();
				int oneYearPaymentTerm = 12 / (month == 0 ? 1 : month);
				double netPremium = payment.getTermNetAgentComPremium();
				double commPercent;
				AgentCommissionEntryType agEntryType;

				/*
				 * AgentCommission (payment time can be many (eg; 3 to 5), but
				 * agentCommission will insert for 3, 4 and 5)
				 */
				if (policy.getAgent() != null) {
					Product product = policy.getInsuredPersonInfo().get(0).getProduct();
					String receiptNo = payment.getReceiptNo();
					for (int i = fromTerm; i <= toTerm; i++) {
						if (i <= oneYearPaymentTerm) {
							commPercent = product.getFirstCommission();
							agEntryType = AgentCommissionEntryType.UNDERWRITING;
						} else {
							commPercent = product.getRenewalCommission();
							agEntryType = AgentCommissionEntryType.RENEWAL;
						}
						double agentComm = Utils.getPercentOf(commPercent, netPremium);
						agentCommission = new AgentCommission(policy.getId(), policyRefType, policyNo, policy.getCustomer(), policy.getOrganization(), policy.getBranch(),
								policy.getAgent(), agentComm, new Date(), receiptNo, netPremium, commPercent, agEntryType, rate, (rate * agentComm), currency, (rate * netPremium));
						agentCommissionList.add(agentCommission);
						agentCommissionDAO.addNewAgentCommisssion(agentCommission);
					}
				}
				/* TLF */
				tlfData = tlfDataProcessor.getInstance(policyRefType, policy, payment, agentCommissionList, false);
				dataList.add(tlfData);
				tlfProcessor.handleTlfProcess(dataList, branch);

				Date coverageDate = getCalculatedEndDate(paymentTimes, policy.getCoverageDate(), policy.getPaymentType());
				policy.setCoverageDate(coverageDate);
				policy.setLastPaymentTerm(payment.getToTerm());
				lifePolicyService.activateBillCollection(policy);
				workFlowService.deleteWorkFlowByRefNo(payment.getInvoiceNo());
			}
				break;
			case HEALTH_POLICY_BILL_COLLECTION:
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION: {
				MedicalPolicy policy = medicalPolicyService.findMedicalPolicyById(payment.getReferenceNo());
				String policyNo = policy.getPolicyNo();
				currency = payment.getCurrency();
				payment.setRate(rate);
				paymentList.add(payment);
				paymentService.activatePayment(paymentList, policyNo, rate);

				int month = policy.getPaymentType().getMonth();
				int oneYearPaymentTerm = 12 / (month == 0 ? 1 : month);
				double netPremium = payment.getTermNetAgentComPremium();
				double commPercent;
				AgentCommissionEntryType agEntryType;
				Agent agent = policy.getAgent();
				/* Agent Commission */
				if (agent != null) {
					Product product = policy.getPolicyInsuredPersonList().get(0).getProduct();
					String receiptNo = payment.getReceiptNo();
					for (int i = fromTerm; i <= toTerm; i++) {
						if (i <= oneYearPaymentTerm) {
							commPercent = product.getFirstCommission();
							agEntryType = AgentCommissionEntryType.UNDERWRITING;
						} else {
							commPercent = product.getRenewalCommission();
							agEntryType = AgentCommissionEntryType.RENEWAL;
						}
						double agentComm = Utils.getPercentOf(commPercent, netPremium);
						agentCommission = new AgentCommission(policy.getId(), policyRefType, policyNo, policy.getCustomer(), policy.getOrganization(), policy.getBranch(), agent,
								agentComm, new Date(), receiptNo, netPremium, commPercent, agEntryType, rate, (rate * agentComm), currency, (rate * netPremium));
						agentCommissionList.add(agentCommission);
						agentCommissionDAO.addNewAgentCommisssion(agentCommission);
					}
				}
				/* TLF */
				tlfData = tlfDataProcessor.getInstance(policyRefType, policy, payment, agentCommissionList, false);
				dataList.add(tlfData);
				tlfProcessor.handleTlfProcess(dataList, branch);

				Date coverageDate = getCalculatedEndDate(paymentTimes, policy.getCoverageDate(), policy.getPaymentType());
				policy.setCoverageDate(coverageDate);
				policy.setLastPaymentTerm(payment.getToTerm());
				medicalPolicyService.activateBillCollection(policy);
				workFlowService.deleteWorkFlowByRefNo(payment.getInvoiceNo());
			}
				break;
			default:
				break;
		}
	}

	private Date getCalculatedEndDate(int paymentTimes, Date activePolicyEndDate, PaymentType paymentType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(activePolicyEndDate);
		int month = paymentType == null ? 0 : paymentType.getMonth();
		calendar.add(Calendar.MONTH, month * paymentTimes);
		return calendar.getTime();
	}

	private List<AgentCommission> getAgentCommissionsForLifeBillCollection(LifePolicy policy, Payment payment) {
		List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
		Product product = policy.getPolicyInsuredPersonList().get(0).getProduct();
		int paymentType = 0;
		double commissionPercent = 0.00;
		paymentType = policy.getPaymentType().getMonth();
		int toterm = payment.getToTerm();
		int fromterm = payment.getFromTerm();
		AgentCommissionEntryType entryType = null;
		double commission = 0.0;
		double totalPremium = 0.0;
		int i = 0;
		int j = 0;
		int check = 12 / paymentType;
		j = toterm;
		for (i = fromterm; i <= j; i++) {
			if (i <= check) {
				commissionPercent = product.getFirstCommission();
				entryType = AgentCommissionEntryType.UNDERWRITING;
			} else {
				commissionPercent = product.getRenewalCommission();
				entryType = AgentCommissionEntryType.RENEWAL;
			}

			if (commissionPercent > 0) {
				totalPremium = policy.getTotalBasicTermPremium() + policy.getTotalAddOnTermPremium();
				commission = Utils.getPercentOf(commissionPercent, totalPremium);
			}
			// agent commission is inserted according to each
			// payment term, not payment time
			// payment time can be many (eg; 3 to 5), but agent
			// commission will insert for 3, 4 and 5
			agentCommissionList.add(new AgentCommission(policy.getId(), PolicyReferenceType.LIFE_BILL_COLLECTION, policy.getPolicyNo(), policy.getCustomer(),
					policy.getOrganization(), policy.getBranch(), policy.getAgent(), commission, new Date(), payment.getReceiptNo(), totalPremium, commissionPercent, entryType,
					0.0, 0.0, null, totalPremium));
		}

		return agentCommissionList;
	}

	private List<AgentCommission> getAgentCommissionsForMedicalBillCollection(MedicalPolicy policy, Payment payment, PolicyReferenceType referenceType) {
		List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
		Product product = policy.getPolicyInsuredPersonList().get(0).getProduct();
		int paymentType = 0;
		double commissionPercent = 0.00;
		paymentType = policy.getPaymentType().getMonth();
		int toterm = payment.getToTerm();
		int fromterm = payment.getFromTerm();
		AgentCommissionEntryType entryType = null;
		double commission = 0.0;
		double rate = payment.getRate();
		double totalPremium = 0.0;
		int i = 0;
		int j = 0;
		int check = 12 / paymentType;
		j = toterm;
		for (i = fromterm; i <= j; i++) {
			if (i <= check) {
				commissionPercent = product.getFirstCommission();
				entryType = AgentCommissionEntryType.UNDERWRITING;
			} else {
				commissionPercent = product.getRenewalCommission();
				entryType = AgentCommissionEntryType.RENEWAL;
			}

			if (commissionPercent > 0) {
				totalPremium = policy.getTotalTermPremium();
				commission = Utils.getPercentOf(commissionPercent, totalPremium);
			}
			// agent commission is inserted according to each
			// payment term, not payment time
			// payment time can be many (eg; 3 to 5), but agent
			// commission will insert for 3, 4 and 5
			agentCommissionList.add(new AgentCommission(policy.getId(), referenceType, policy.getPolicyNo(), policy.getCustomer(), policy.getOrganization(), policy.getBranch(),
					policy.getAgent(), commission, new Date(), payment.getReceiptNo(), totalPremium, commissionPercent, entryType, rate, rate * commission, null, totalPremium));
		}

		return agentCommissionList;
	}

}
