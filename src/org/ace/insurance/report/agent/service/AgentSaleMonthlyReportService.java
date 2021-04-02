package org.ace.insurance.report.agent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.AgentSaleMonthlyDto;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleMonthlyReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleMonthlyReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentSaleMonthlyReportService")
public class AgentSaleMonthlyReportService extends BaseService implements IAgentSaleMonthlyReportService {

	@Resource(name = "AgentSaleMonthlyReportDAO")
	private IAgentSaleMonthlyReportDAO agentSaleMonthlyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleMonthlyDto> findMonthlySaleReport(AgentSalesReportCriteria criteria) {
		List<AgentSaleMonthlyDto> result = null;
		Map<String, AgentSaleMonthlyDto> agentSaleReportMap = new HashMap<String, AgentSaleMonthlyDto>();
		try {
			criteria.setProposalType("NEW");
			List<AgentSaleMonthlyDto> newAgentMonthlySale = findForNonLife(criteria);

			criteria.setProposalType("RENEWAL");
			List<AgentSaleMonthlyDto> renewalAgentMonthlySale = findForNonLife(criteria);

			combineNew_Renewal_AgentMonthlySale(newAgentMonthlySale, agentSaleReportMap);
			combineNew_Renewal_AgentMonthlySale(renewalAgentMonthlySale, agentSaleReportMap);
			result = new ArrayList<AgentSaleMonthlyDto>(agentSaleReportMap.values());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MonthlySaleReport by criteria.", e);
		}
		return result;
	}

	public void combineNew_Renewal_AgentMonthlySale(List<AgentSaleMonthlyDto> agentSaleList, Map<String, AgentSaleMonthlyDto> agentSaleReportMap) {
		int policy = 0;
		double premium = 0.0;
		for (AgentSaleMonthlyDto agentSale : agentSaleList) {
			if (!agentSaleReportMap.containsKey(agentSale.getCodeNo())) {
				policy = 0;
				premium = 0.0;
				policy = agentSale.getNewFirePolicy() + agentSale.getNewMotorPolicy() + agentSale.getNewCargoPolicy() + agentSale.getRenewalFirePolicy()
						+ agentSale.getRenewalMotorPolicy() + agentSale.getRenewalCargoPolicy();
				premium = agentSale.getNewFirePremium() + agentSale.getNewMotorPremium() + agentSale.getNewCargoPremium() + agentSale.getRenewalFirePremium()
						+ agentSale.getRenewalMotorPremium() + agentSale.getRenewalCargoPremium();
				agentSale.setTotalPolicy(policy);
				agentSale.setTotalPremium(premium);
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSale);
			} else {// Assume that agentSale is RENEWAL
				AgentSaleMonthlyDto agentSaleReport = agentSaleReportMap.get(agentSale.getCodeNo());
				policy = 0;
				premium = 0.0;

				// for Renewal Motor Policy
				policy = agentSale.getRenewalMotorPolicy();
				premium = agentSale.getRenewalMotorPremium();
				agentSaleReport.setRenewalMotorPolicy(policy);
				agentSaleReport.setRenewalMotorPremium(premium);

				// for Renewal Fire Policy
				policy = agentSale.getRenewalFirePolicy();
				premium = agentSale.getRenewalFirePremium();
				agentSaleReport.setRenewalFirePolicy(policy);
				agentSaleReport.setRenewalFirePremium(premium);

				// for Renewal Cargo Policy
				policy = agentSale.getRenewalCargoPolicy();
				premium = agentSale.getRenewalCargoPremium();
				agentSaleReport.setRenewalCargoPolicy(policy);
				agentSaleReport.setRenewalCargoPremium(premium);

				policy = agentSaleReport.getNewFirePolicy() + agentSaleReport.getNewMotorPolicy() + agentSaleReport.getNewCargoPolicy() + agentSaleReport.getRenewalFirePolicy()
						+ agentSaleReport.getRenewalMotorPolicy() + agentSaleReport.getRenewalCargoPolicy();
				premium = agentSaleReport.getNewFirePremium() + agentSaleReport.getNewMotorPremium() + agentSaleReport.getNewCargoPremium()
						+ agentSaleReport.getRenewalFirePremium() + agentSaleReport.getRenewalMotorPremium() + agentSaleReport.getRenewalCargoPremium();
				agentSaleReport.setTotalPolicy(policy);
				agentSaleReport.setTotalPremium(premium);
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSaleReport);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleMonthlyDto> findForNonLife(AgentSalesReportCriteria criteria) {
		List<AgentSaleMonthlyDto> motorAgentSaleResult = null;
		List<AgentSaleMonthlyDto> fireAgentSaleResult = null;
		List<AgentSaleMonthlyDto> cargoAgentSaleResult = null;
		List<AgentSaleMonthlyDto> result = null;
		Map<String, AgentSaleMonthlyDto> agentSaleReportMap = new HashMap<String, AgentSaleMonthlyDto>();
		String proposalType = criteria.getProposalType();

		try {
			motorAgentSaleResult = agentSaleMonthlyReportDAO.findForNonLifeByReferenceType(criteria, "MOTOR_POLICY");
			fireAgentSaleResult = agentSaleMonthlyReportDAO.findForNonLifeByReferenceType(criteria, "FIRE_POLICY");
			cargoAgentSaleResult = agentSaleMonthlyReportDAO.findForNonLifeByReferenceType(criteria, "CARGO_POLICY");

			calculatePolicyForNonLife(motorAgentSaleResult, agentSaleReportMap, proposalType, "MOTOR_POLICY");
			calculatePolicyForNonLife(fireAgentSaleResult, agentSaleReportMap, proposalType, "FIRE_POLICY");
			calculatePolicyForNonLife(cargoAgentSaleResult, agentSaleReportMap, proposalType, "CARGO_POLICY");

			result = new ArrayList<AgentSaleMonthlyDto>(agentSaleReportMap.values());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentSaleReport by criteria.", e);
		}
		return result;
	}

	public void calculatePolicyForNonLife(List<AgentSaleMonthlyDto> agentSaleList, Map<String, AgentSaleMonthlyDto> agentSaleReportMap, String proposalType, String referenceType) {
		for (AgentSaleMonthlyDto agentSale : agentSaleList) {
			if (!agentSaleReportMap.containsKey(agentSale.getCodeNo())) { // if
																			// agentSale
																			// CodeNo.
																			// doesn't
																			// contain
																			// in
																			// existing
																			// agentSaleReportMap
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSale);
			} else {
				AgentSaleMonthlyDto agentSaleReport = agentSaleReportMap.get(agentSale.getCodeNo());
				if (referenceType.equals("MOTOR_POLICY")) {
					if (proposalType.equals("NEW")) {
						agentSaleReport.setNewMotorPolicy(agentSale.getNewMotorPolicy());
						agentSaleReport.setNewMotorPremium(agentSale.getNewMotorPremium());
					} else {
						agentSaleReport.setRenewalMotorPolicy(agentSale.getRenewalMotorPolicy());
						agentSaleReport.setRenewalMotorPremium(agentSale.getRenewalMotorPremium());
					}

				} else if (referenceType.equals("FIRE_POLICY")) {
					if (proposalType.equals("NEW")) {
						agentSaleReport.setNewFirePolicy(agentSale.getNewFirePolicy());
						agentSaleReport.setNewFirePremium(agentSale.getNewFirePremium());
					} else {
						agentSaleReport.setRenewalFirePolicy(agentSale.getRenewalFirePolicy());
						agentSaleReport.setRenewalFirePremium(agentSale.getRenewalFirePremium());
					}
				} else {
					if (proposalType.equals("NEW")) {
						agentSaleReport.setNewCargoPolicy(agentSale.getNewCargoPolicy());
						agentSaleReport.setNewCargoPremium(agentSale.getNewCargoPremium());
					} else {
						agentSaleReport.setRenewalCargoPolicy(agentSale.getRenewalCargoPolicy());
						agentSaleReport.setRenewalCargoPremium(agentSale.getRenewalCargoPremium());
					}
				}
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSaleReport);
			}
		}
	}

}
