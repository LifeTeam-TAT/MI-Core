package org.ace.insurance.life.policy.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.NotificationCriteria;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReceiptNoCriteria;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.LifeEndorseItem;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.LifePolicyAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonAddon;
import org.ace.insurance.life.policy.PolicyInsuredPersonAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.PolicyInsuredPersonKeyFactorValue;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.policy.persistence.LifePolicyInsuredPersonDAO;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyInsuredPersonInfoDAO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyEditHistory.service.interfaces.ILifePolicyEditHistoryService;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.life.policyHistory.persistence.interfaces.ILifePolicyInsuredPersonInfoHistoryDAO;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LPL002;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.LifePolicyNotificationDTO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifePolicyService")
public class LifePolicyService extends BaseService implements ILifePolicyService {

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;
	
	@Resource(name = "PolicyInsuredPersonInfoDAO")
	private ILifePolicyInsuredPersonInfoDAO lifePolicyInsuredPersonDAO;
	
	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "LifePolicyHistoryService")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	@Resource(name = "LifePolicyEditHistoryService")
	private ILifePolicyEditHistoryService lifePolicyEditHistoryService;

	@Resource(name = "LifeProposalService")
	private ILifeProposalService lifeProposalService;

	@Resource(name = "LifePolicySummaryService")
	private ILifePolicySummaryService lifePolicySummaryService;

	@Resource(name = "LifeEndorsementService")
	private ILifeEndorsementService lifeEndorsementService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Resource(name = "CurrencyService")
	private ICurrencyService currencyService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifePolicy(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.insert(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> addNewSportManTravelAbroad(List<SportManTravelAbroad> selectedSportManTravelList, WorkFlowDTO workFlowDTO) {
		List<Payment> paymentList = new ArrayList<Payment>();
		try {
			LifePolicy lifePolicy = selectedSportManTravelList.get(0).getPolicyInsuredPerson().getLifePolicy();
			String productId = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getId();
			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, productId.trim());
			workFlowDTO.setReferenceNo(invoiceNo);
			workFlowService.addNewWorkFlow(workFlowDTO);
			double addOnPremium = 0.0;
			double premium = 0.0;
			for (SportManTravelAbroad smt : selectedSportManTravelList) {
				premium = premium + smt.getPremium();
				addOnPremium = addOnPremium + smt.getTotalAdditionalPremium();
				smt.setInvoiceNo(invoiceNo);
			}
			lifePolicyDAO.insertSportManTravelAbroad(selectedSportManTravelList);
			double rate = 1.0;
			Currency cur = currencyService.findHomeCurrency();
			Payment payment = new Payment();
			payment.setInvoiceNo(invoiceNo);
			payment.setPolicyNo(lifePolicy.getPolicyNo());
			payment.setReferenceNo(lifePolicy.getId());
			payment.setReferenceType(PolicyReferenceType.SPORT_MAN_POLICY);
			payment.setBasicPremium(premium);
			payment.setAddOnPremium(addOnPremium);
			payment.setConfirmDate(new Date());
			payment.setRate(rate);
			payment.setCurrency(cur);
			paymentList.add(payment);
			for (Payment p : paymentList) {
				paymentDAO.insert(p);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SportManTravelAbroad", e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy activateLifePolicy(LifeProposal lifeProposal) {
		LifePolicy lifePolicy = null;
		try {
			lifePolicy = lifePolicyDAO.findByProposalId(lifeProposal.getId());
			String productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId().trim();
			boolean isShortEndowLife = KeyFactorChecker.isShortTermEndowment(productId);
			boolean isGovernmentShortEndowLife = KeyFactorChecker.isGovernmentShortTermEndowment(productId);
			int paymentMonth = lifeProposal.getPaymentType().getMonth();
			ProposalType proposalType = lifeProposal.getProposalType();
			String endorsementNo = null;
			String policyNo = null;
			if (lifePolicy.isEndorsementApplied()) {

				/* Set Old Policy No */
				String lifePolicyNo = lifeProposal.getLifePolicy().getPolicyNo();
				lifePolicy.setPolicyNo(lifePolicyNo);

				/* Set Endorsement No */
				endorsementNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_POLICY_ENDORSEMENT_NO, productId);

				lifePolicy.setEndorsementNo(endorsementNo);

				/*
				 * if PublicLife Policy, reset activePolicyEndDate By CoverTime
				 */
				LifeEndorseInfo lifeEndorseInfo = lifeEndorsementService.findByEndorsePolicyReferenceNo(lifeProposal.getLifePolicy().getId());
				if (!isShortEndowLife) {
					Date startDate = lifePolicy.getCommenmanceDate();
					Date endDate = lifePolicy.getActivedPolicyEndDate();
					int passedMonths = Utils.monthsBetween(startDate, endDate, false, true);
					if (lifePolicy.getInsuredPersonInfo().size() == 1) {
						LifePolicySummary summary = lifePolicySummaryService.findLifePolicyByPolicyNo(lifeEndorseInfo.getSourcePolicyReferenceNo());
						Date activePolicyDate = lifeProposal.getLifePolicy().getActivedPolicyEndDate();
						if (summary != null) {
							if (summary.getCoverTime() > 0) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(activePolicyDate);
								for (int i = 0; i < summary.getCoverTime(); i++) {
									cal.add(Calendar.MONTH, lifeProposal.getLifePolicy().getPaymentType().getMonth());
									activePolicyDate = cal.getTime();
									cal.setTime(activePolicyDate);
								}
							} else if (lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList().get(0).getLifeEndorseChangeList() != null
									&& !lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList().get(0).getLifeEndorseChangeList().isEmpty()) {
								if (lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList().get(0).getLifeEndorseChangeList().get(0).getEndorsePremium() != 0.0
										&& passedMonths % 12 > 5 && lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList().get(0).getLifeEndorseChangeList().get(0)
												.getLifeEndorseItem().equals(LifeEndorseItem.DECREASE_SI)) {
									int restmonths = 12 - (passedMonths % 12);

									activePolicyDate = Utils.addMonth(activePolicyDate, restmonths);
								}

							}
						}
						lifePolicy.setActivedPolicyEndDate(activePolicyDate);
					}
				} else {
					if (lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList().get(0).getLifeEndorseChangeList().get(0).getEndorsePremium() != 0.0) {
						lifePolicy.setActivedPolicyEndDate(Utils.addYears(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getPeriodOfInsurance()));
					}
				}
			} else {
				if (KeyFactorChecker.isStudentLife(productId)) {
					policyNo = customIDGenerator.getCustomNextId(SystemConstants.STUDENT_LIFE_POLICY_NO, productId);
				} else if (KeyFactorChecker.isPublicTermLife(productId)) {
					policyNo = customIDGenerator.getCustomNextId(SystemConstants.PUBLIC_TERM_LIFE_POLICY_NO, productId);
				} else {
					policyNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_POLICY_NO, productId);
				}
				lifePolicy.setPolicyNo(policyNo);
				if (proposalType.equals(ProposalType.UNDERWRITING) || proposalType.equals(ProposalType.RENEWAL)) {
					if (paymentMonth != 0) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(lifeProposal.getStartDate());
						cal.add(Calendar.MONTH, paymentMonth);
						lifePolicy.setCoverageDate(cal.getTime());
					} else {
						lifePolicy.setCoverageDate(lifeProposal.getEndDate());
					}
					lifePolicy.setLastPaymentTerm(1);
				}
				lifePolicy.setCommenmanceDate(new Date());
				lifePolicy.setActivedPolicyStartDate(lifeProposal.getStartDate());
				lifePolicy.setActivedPolicyEndDate(lifeProposal.getEndDate());
			}
			lifePolicyDAO.update(lifePolicy);
		} catch (

		DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicy", e);
		}
		return lifePolicy;
	}

	private void resetId(LifePolicy lifePolicy) {
		lifePolicy.overwriteId(null);
		lifePolicy.setVersion(0);
		for (LifePolicyAttachment attach : lifePolicy.getAttachmentList()) {
			attach.overwriteId(null);
			attach.setVersion(0);
		}
		for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
			person.overwriteId(null);
			person.setVersion(0);
			for (PolicyInsuredPersonAttachment attach : person.getAttachmentList()) {
				attach.overwriteId(null);
				attach.setVersion(0);
			}
			for (PolicyInsuredPersonAddon addOn : person.getPolicyInsuredPersonAddOnList()) {
				addOn.overwriteId(null);
				addOn.setVersion(0);
			}
			for (PolicyInsuredPersonKeyFactorValue kfv : person.getPolicyInsuredPersonkeyFactorValueList()) {
				kfv.overwriteId(null);
				kfv.setVersion(0);
			}
			for (PolicyInsuredPersonBeneficiaries beneficiaries : person.getPolicyInsuredPersonBeneficiariesList()) {
				beneficiaries.overwriteId(null);
				beneficiaries.setVersion(0);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicy(LifePolicy lifePolicy) {
		try {
			LifePolicy oldPolicy = findLifePolicyById(lifePolicy.getId());
			lifePolicyHistoryService.addNewLifePolicy(oldPolicy, PolicyStatus.UPDATE, PolicyHistoryEntryType.ENDORSEMENT);
			lifePolicy.setPolicyStatus(PolicyStatus.UPDATE);
			resetId(lifePolicy);
			addNewLifePolicy(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicy", e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyEndorsement(LifePolicy lifePolicy) {
		try {
			LifePolicy oldPolicy = findLifePolicyById(lifePolicy.getId());
			lifePolicyHistoryService.addNewLifePolicy(oldPolicy, PolicyStatus.UPDATE, PolicyHistoryEntryType.ENDORSEMENT);
			lifePolicy.setPolicyStatus(PolicyStatus.UPDATE);
			resetId(lifePolicy);
			addNewLifePolicy(lifePolicy);
//			for (PolicyInsuredPerson insuredPerson: oldPolicy.getPolicyInsuredPersonList()) {
//				lifePolicyInsuredPersonDAO.delete(insuredPerson);
//			}
			lifePolicyDAO.delete(oldPolicy);
			//deleteLifePolicy(oldPolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void overwriteLifePolicy(LifePolicy lifePolicy) {
		try {
			LifePolicy oldPolicy = findLifePolicyById(lifePolicy.getId());
			lifePolicyEditHistoryService.addNewLifePolicy(oldPolicy, PolicyStatus.UPDATE, PolicyHistoryEntryType.UNDERWRITING);
			lifePolicy.setPolicyStatus(PolicyStatus.UPDATE);
			for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
				for (PolicyInsuredPersonBeneficiaries beneficiary : person.getPolicyInsuredPersonBeneficiariesList()) {
					if (beneficiary.getBeneficiaryNo() == null) {
						beneficiary.setBeneficiaryNo(customIDGenerator.getNextId(SystemConstants.LIFE_BENEFICIARY_NO, null));
					}
				}
			}
			lifePolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to overwrite a LifePolicy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void terminateLifePolicy(LifePolicy lifePolicy) {
		try {
			PolicyStatus status = null;
			int policyPeriodYear = lifePolicy.getPeriodMonth() / 12;
			DateTime vDate = new DateTime(lifePolicy.getActivedPolicyEndDate());
			DateTime cDate = new DateTime(lifePolicy.getCommenmanceDate());
			int passedYear = (Months.monthsBetween(cDate, vDate).getMonths() + 1) / 12;
			if (lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getId().equals(KeyFactorIDConfig.getPublicLifeId())) {
				if (policyPeriodYear >= 5 && passedYear >= 2 || policyPeriodYear >= 12 && passedYear >= 3) {
					status = PolicyStatus.TERMINATE;
				}
			} else {
				status = PolicyStatus.DELETE;
			}
			LifePolicy oldPolicy = findLifePolicyById(lifePolicy.getId());
			lifePolicyHistoryService.addNewLifePolicy(oldPolicy, status, PolicyHistoryEntryType.ENDORSEMENT);
			lifePolicy.setPolicyStatus(status);
			lifePolicy.setDelFlag(true);
			resetId(lifePolicy);
			addNewLifePolicy(lifePolicy);
			deleteLifePolicy(oldPolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to terminate a LifePolicy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicy(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.delete(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePolicy", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findLifePolicyById(String id) {
		LifePolicy result = null;
		try {
			result = lifePolicyDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findLifePolicyByLifeProposalId(String lifeProposaId) {
		LifePolicy result = null;
		try {
			result = lifePolicyDAO.findByLifeProposalId(lifeProposaId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (lifeProposaId : " + lifeProposaId + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findPolicyByProposalId(String lifeProposaId) {
		LifePolicy result = null;
		try {
			result = lifePolicyDAO.findByProposalId(lifeProposaId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifePolicy", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findLifePolicyByPolicyId(String policyId) {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findByPolicyId(policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Policy by PolicyId : " + policyId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findLifePolicyByReceiptNo(String receiptNo) {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findByReceiptNo(receiptNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicy by ReceiptNo : " + receiptNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findAllLifePolicy() {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findAllActiveLifePolicy() {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findAllActiveLifePolicy();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ActiveLifePolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findByDate(Date startDate, Date endDate) {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findByDate(startDate, endDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void increaseLifePolicyPrintCount(String lifeProposalId) {
		try {
			lifePolicyDAO.increasePrintCount(lifeProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to increase LifePolicy print count. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId) {
		try {
			lifePolicyDAO.updateCommenmanceDate(date, lifeProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update LifePolicy commenmance date. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPL002> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList) {
		List<LPL002> result = null;
		try {
			result = lifePolicyDAO.findByEnquiryCriteria(criteria, productList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findByCustomer(Customer customer) {
		List<LifePolicy> ret = null;
		try {
			ret = lifePolicyDAO.findByCustomer(customer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find lifePolicy by customer", e);
		}
		return ret;
	}

	@Override
	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate) {
		try {
			lifePolicyDAO.updatePaymentDate(lifePolicyId, paymentDate, paymentValidDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Life Policy payment status", e);
		}
	}

	@Override
	public void updateEndorsementStatus(Boolean status, LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.updateEndorsementStatus(status, lifePolicy.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Life Policy endorsement status", e);
		}
	}

	@Override
	public void updateEndorsementStatusAndIssueDate(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.updateEndorsementStatusAndIssueDate(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Endorsement Status And Issue Date", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPerson findInsuredPersonByCodeNo(String codeNo) {
		PolicyInsuredPerson result = null;
		try {
			result = lifePolicyDAO.findInsuredPersonByCodeNo(codeNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find InsuredPerson By CodeNo : " + codeNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsuredPersonStatusByCodeNo(String codeNo, EndorsementStatus status) {
		try {
			lifePolicyDAO.updateInsuredPersonStatusByCodeNo(codeNo, status);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update InsuredPerson Status By CodeNo : " + codeNo, e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyStatusById(String id, PolicyStatus status) {
		try {
			lifePolicyDAO.updatePolicyStatusById(id, status);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Policy Status By Id : " + id, e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyAttachment(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.updatePolicyAttachment(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MotorProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyAttachment(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MotorProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findLifePolicyByPolicyNo(String policyNo) {
		LifePolicy result = null;
		try {
			result = lifePolicyDAO.findLifePolicyByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (PolicyNo : " + policyNo + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBeneficiaryClaimStatusById(String id, ClaimStatus status) {
		try {
			lifePolicyDAO.updateBeneficiaryClaimStatusById(id, status);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Claim Status By Id : " + id, e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findLifePolicyForClaimByCriteria(LifePolicyCriteria criteria) {
		List<LifePolicySearch> ret = new ArrayList<LifePolicySearch>();
		try {
			ret = lifePolicyDAO.findLifePolicyForClaimByCriteria(criteria);
		} catch (DAOException e) {
			throw new org.ace.java.component.SystemException(e.getErrorCode(), "Faield to retrieve Life Policy for Claim by Criteria", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findActiveLifePolicy(LifePolicyCriteria criteria, List<Product> productList) {
		List<LifePolicySearch> ret = new ArrayList<LifePolicySearch>();
		try {
			ret = lifePolicyDAO.findActiveLifePolicy(criteria, productList);
		} catch (DAOException e) {
			throw new org.ace.java.component.SystemException(e.getErrorCode(), "Faield to retrieve Life Policy for Claim by Criteria", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findActiveSportManLifePolicy(LifePolicyCriteria criteria) {
		List<LifePolicySearch> ret = new ArrayList<LifePolicySearch>();
		try {
			ret = lifePolicyDAO.findActiveSportManLifePolicy(criteria);
		} catch (DAOException e) {
			throw new org.ace.java.component.SystemException(e.getErrorCode(), "Faield to retrieve Life Policy for Claim by Criteria", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findLifePolicyByPolicyCriteria(PolicyCriteria criteria, int max) {
		List<LPC001> result = null;
		try {
			result = lifePolicyDAO.findByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findLifePolicyCloneByPolicyCriteria(PolicyCriteria criteria, int max) {
		List<LPC001> result = null;
		try {
			result = lifePolicyDAO.findPolicyByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria) {
		List<BillCollectionDTO> result = null;
		try {
			result = lifePolicyDAO.findBillCollectionByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BillCollectionDTO> findLifePaidUpProposalByCriteria(PolicyCriteria criteria) {
		List<BillCollectionDTO> result = null;
		try {
			result = lifePolicyDAO.findPaidUpPolicyByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findLifePolicyByPageCriteria(PolicyCriteria criteria) {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findByPageCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activateBillCollection(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.updateBillCollection(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update payment.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyNotificationDTO> findNotificationLifePolicy(NotificationCriteria criteria, List<String> productList) {
		List<LifePolicyNotificationDTO> result = null;
		try {
			result = lifePolicyDAO.findNotificationLifePolicy(criteria, productList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<EndorsementLifePolicyPrint> findEndorsementPolicyPrintByLifePolicyNo(String lifePolicyNo) {
		List<EndorsementLifePolicyPrint> result = null;
		try {
			result = lifePolicyDAO.findEndorsementPolicyPrintByLifePolicyNo(lifePolicyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield find EndorsementPolicyPrint By LifePolicyNo", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewEndorsementLifePolicyPrint(EndorsementLifePolicyPrint endorsementPolicyPrint) {
		try {
			lifePolicyDAO.insert(endorsementPolicyPrint);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new EndorsementLifePolicyPrint", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndorsementLifePolicyPrint(EndorsementLifePolicyPrint endorsementPolicyPrint) {
		try {
			lifePolicyDAO.update(endorsementPolicyPrint);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new EndorsementLifePolicyPrint", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public EndorsementLifePolicyPrint findEndorsementPolicyPrintById(String id) {
		EndorsementLifePolicyPrint result = null;
		try {
			result = lifePolicyDAO.findEndorsementLifePolicyPrintById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new EndorsementLifePolicyPrint", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findLifePolicyByReceiptNoCriteria(ReceiptNoCriteria criteria, int max) {
		List<Payment> result = null;
		try {
			result = lifePolicyDAO.findByReceiptNoCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findLifePolicyPOByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType) {
		List<LifePolicy> result = null;
		try {
			result = lifePolicyDAO.findPaymentOrderByReceiptNo(receiptNo, policyReferenceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find lifePolicy payment order by Receipt No ");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findByPolicyCriteria(PolicyCriteria criteria, int max) {
		List<LPC001> result = null;
		try {
			result = lifePolicyDAO.findEndorsementByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicy (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SportManTravelAbroad> findSportManAbroadListByInvoiceNo(String invoiceNo) {
		List<SportManTravelAbroad> result = null;
		try {
			result = lifePolicyDAO.findSportManAbroadListByInvoiceNo(invoiceNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a SportManTravelAbroad (invoiceNo : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<UPRReportView> findUPRReport(UPRReportCriteria criteria) {
		List<UPRReportView> result = null;
		try {
			result = lifePolicyDAO.findUPRReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a UPR Report " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findNonFullPaidDisClaimPolicy(LifePolicyCriteria lifePolicyCriteria) {
		List<LifePolicySearch> result = null;
		try {
			result = lifePolicyDAO.findNonFullPaidDisClaimPolicy(lifePolicyCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find Non Full Paid Disability Claim Policy" + e);
		}
		return result;
	}

}