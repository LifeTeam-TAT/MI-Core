package org.ace.insurance.life.renewal.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifePolicyService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifeProposalService;
import org.ace.insurance.product.Product;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "RenewalGroupLifePolicyService")
public class RenewalGroupLifePolicyService extends BaseService implements IRenewalGroupLifePolicyService {

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "RenewalGroupLifeProposalService")
	private IRenewalGroupLifeProposalService renewalGroupLifeProposalService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifePolicy(LifePolicy lifePolicy) {
		try {
			lifePolicyDAO.insert(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicy", e);
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

	@Override
	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate) {
		try {
			lifePolicyDAO.updatePaymentDate(lifePolicyId, paymentDate, paymentValidDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Life Policy payment status", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findLifePolicyByProposalId(String proposalId) {
		LifePolicy result = null;
		try {
			result = lifePolicyDAO.findByProposalId(proposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Policy by ProposalId : " + proposalId, e);
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
	public LifePolicy activateLifePolicy(LifeProposal lifeProposal) {
		LifePolicy lifePolicy = null;
		try {
			lifePolicy = lifePolicyDAO.findByProposalId(lifeProposal.getId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			int paymentMonth = lifeProposal.getPaymentType().getMonth();
			ProposalType proposalType = lifeProposal.getProposalType();
			String policyNo = null;
			String productCode = lifePolicy.getInsuredPersonInfo().get(0).getProduct().getProductGroup().getPolicyNoPrefix();
			if (isGroupLife(product)) {
				if (lifePolicy.getPolicyNo() == null) {
					policyNo = customIDGenerator.getNextId(SystemConstants.GROUPLIFE_POLICY_NO, productCode);
				} else {
					policyNo = lifePolicy.getPolicyNo();
				}
			}
			if (proposalType.equals(ProposalType.UNDERWRITING) || proposalType.equals(ProposalType.RENEWAL)) {
				if (paymentMonth != 0) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(lifeProposal.getStartDate());
					if (KeyFactorChecker.isPersonalAccident(product)) {
						cal.add(Calendar.MONTH, paymentMonth);
					} else {
						cal.add(Calendar.YEAR, paymentMonth);
					}
					lifePolicy.setCoverageDate(cal.getTime());
				} else {
					lifePolicy.setCoverageDate(lifeProposal.getEndDate());
				}
				lifePolicy.setLastPaymentTerm(1);
			}
			lifePolicy.setPolicyNo(policyNo);
			lifePolicy.setCommenmanceDate(lifePolicy.getActivedPolicyStartDate());
			lifePolicy.setActivedPolicyStartDate(lifePolicy.getActivedPolicyStartDate());
			lifePolicy.setActivedPolicyEndDate(lifePolicy.getActivedPolicyEndDate());
			lifePolicyDAO.update(lifePolicy);
			/*
			 * Don't remove this process : Zaw Than Oo
			 * if(isCoInsurance(lifePolicy)) {
			 * coinsuranceService.addNewCoinsurances(lifePolicy); }
			 */
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicy", e);
		}
		return lifePolicy;
	}
}
