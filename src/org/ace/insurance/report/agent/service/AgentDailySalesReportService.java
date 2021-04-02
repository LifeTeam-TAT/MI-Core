package org.ace.insurance.report.agent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.agent.AgentSaleComparisonReport;
import org.ace.insurance.report.agent.AgentSalesReportCriteria;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentDailySalesReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentDailySalesReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentDailySalesReportService")
public class AgentDailySalesReportService extends BaseService implements IAgentDailySalesReportService {
	@Resource(name = "AgentDailySalesReportDAO")
	private IAgentDailySalesReportDAO agentDailySalesReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleComparisonReport> findForLife(AgentSalesReportCriteria agentDailySalesCriteria) {
		List<AgentSaleComparisonReport> result = null;
		List<AgentSaleComparisonReport> endowmentLife = null;
		List<AgentSaleComparisonReport> groupLife = null;
		Map<String, AgentSaleComparisonReport> agentSaleReportMap = new HashMap<String, AgentSaleComparisonReport>();
		try {
			endowmentLife = agentDailySalesReportDAO.findForLife(agentDailySalesCriteria, "m.endowmentLife");
			groupLife = agentDailySalesReportDAO.findForLife(agentDailySalesCriteria, "m.groupLife");
			calculatePolicyFor_LIFE(endowmentLife, agentSaleReportMap);
			calculatePolicyFor_LIFE(groupLife, agentSaleReportMap);
			result = new ArrayList<AgentSaleComparisonReport>(agentSaleReportMap.values());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MotorProposalReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleComparisonReport> findForNonLife_NEW_RENEWAL(AgentSalesReportCriteria criteria) {
		List<AgentSaleComparisonReport> motorAgentSaleResult = null;
		Map<String, AgentSaleComparisonReport> agentSaleReportMap = new HashMap<String, AgentSaleComparisonReport>();
		try {
			criteria.setProposalType("NEW");
			List<AgentSaleComparisonReport> newAgentDailySale = findForNonLife(criteria);
			criteria.setProposalType("RENEWAL");
			List<AgentSaleComparisonReport> renewalAgentDailySale = findForNonLife(criteria);

			calculatePolicyFor_NEWRENEWAL(newAgentDailySale, agentSaleReportMap);
			calculatePolicyFor_NEWRENEWAL(renewalAgentDailySale, agentSaleReportMap);
			motorAgentSaleResult = new ArrayList<AgentSaleComparisonReport>(agentSaleReportMap.values());
			criteria.setProposalType("NEW_RENEWAL");
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find MotorProposalReport by criteria.", e);
		}
		return motorAgentSaleResult;
	}

	public void calculatePolicyFor_LIFE(List<AgentSaleComparisonReport> agentSaleList, Map<String, AgentSaleComparisonReport> agentSaleReportMap) {
		for (AgentSaleComparisonReport agentSale : agentSaleList) {
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
				AgentSaleComparisonReport agentSaleReport = agentSaleReportMap.get(agentSale.getCodeNo());
				int policyCount = 0;
				double totalAmount = 0;

				// set for Group Life
				agentSaleReport.setGroupLife(agentSale.getGroupLife());
				agentSaleReport.setGroupLifePremium(agentSale.getGroupLifePremium());

				policyCount = agentSaleReport.getEndowmentLife() + agentSale.getGroupLife();
				agentSaleReport.setLifePolicy(policyCount);
				totalAmount = agentSaleReport.getEndowmentPremium() + agentSale.getGroupLifePremium();
				agentSaleReport.setTotallifePremium(totalAmount);

				agentSaleReportMap.put(agentSale.getCodeNo(), agentSaleReport);
			}
		}
	}

	public void calculatePolicyFor_NEWRENEWAL(List<AgentSaleComparisonReport> agentSaleList, Map<String, AgentSaleComparisonReport> agentSaleReportMap) {
		for (AgentSaleComparisonReport agentSale : agentSaleList) {
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
				AgentSaleComparisonReport agentSaleReport = agentSaleReportMap.get(agentSale.getCodeNo());
				int policyCount = 0;
				double totalAmount = 0;
				// For Motor Policy
				policyCount = agentSaleReport.getMotorPolicy() + agentSale.getMotorPolicy();
				agentSaleReport.setMotorPolicy(policyCount);
				totalAmount = agentSaleReport.getTotalmotorPremium() + agentSale.getTotalmotorPremium();
				agentSaleReport.setTotalmotorPremium(totalAmount);
				// For Fire Policy
				policyCount = agentSaleReport.getFirePolicy() + agentSale.getFirePolicy();
				agentSaleReport.setFirePolicy(policyCount);
				totalAmount = agentSaleReport.getTotalfirePremium() + agentSale.getTotalfirePremium();
				agentSaleReport.setTotalfirePremium(totalAmount);
				// For Cargo Policy
				policyCount = agentSaleReport.getCargoPolicy() + agentSale.getCargoPolicy();
				agentSaleReport.setCargoPolicy(policyCount);
				totalAmount = agentSaleReport.getTotalCargoPremium() + agentSale.getTotalCargoPremium();
				agentSaleReport.setTotalCargoPremium(totalAmount);
				// For Total Policy
				policyCount = agentSaleReport.getPolicyCount() + agentSale.getPolicyCount();
				agentSaleReport.setPolicyCount(policyCount);
				// totalAmount = agentSaleReport.getTotalAmount() +
				// agentSale.getTotalAmount();
				totalAmount = agentSaleReport.getTotalmotorPremium() + agentSaleReport.getTotalfirePremium() + agentSaleReport.getTotalCargoPremium();
				agentSaleReport.setTotalAmount(totalAmount);
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSaleReport);
			}
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSaleComparisonReport> findForNonLife(AgentSalesReportCriteria criteria) {
		List<AgentSaleComparisonReport> motorAgentSaleResult = null;
		List<AgentSaleComparisonReport> fireAgentSaleResult = null;
		List<AgentSaleComparisonReport> cargoAgentSaleResult = null;
		List<AgentSaleComparisonReport> result = null;
		Map<String, AgentSaleComparisonReport> agentSaleReportMap = new HashMap<String, AgentSaleComparisonReport>();
		String proposalType = criteria.getProposalType();

		try {
			motorAgentSaleResult = agentDailySalesReportDAO.findForNonLifeByReferenceType(criteria, "MOTOR_POLICY");
			fireAgentSaleResult = agentDailySalesReportDAO.findForNonLifeByReferenceType(criteria, "FIRE_POLICY");

			calculatePolicyForNonLife(motorAgentSaleResult, agentSaleReportMap, proposalType, "MOTOR_POLICY");
			calculatePolicyForNonLife(fireAgentSaleResult, agentSaleReportMap, proposalType, "FIRE_POLICY");

			if (!proposalType.equals("RENEWAL")) {
				cargoAgentSaleResult = agentDailySalesReportDAO.findForNonLifeByReferenceType(criteria, "CARGO_POLICY");
				calculatePolicyForNonLife(cargoAgentSaleResult, agentSaleReportMap, proposalType, "CARGO_POLICY");
			}
			result = new ArrayList<AgentSaleComparisonReport>(agentSaleReportMap.values());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentSaleReport by criteria.", e);
		}
		return result;
	}

	public void calculatePolicyForNonLife(List<AgentSaleComparisonReport> agentSaleList, Map<String, AgentSaleComparisonReport> agentSaleReportMap, String proposalType,
			String referenceType) {
		for (AgentSaleComparisonReport agentSale : agentSaleList) {
			if (!agentSaleReportMap.containsKey(agentSale.getCodeNo())) { // if
																			// agentSale
																			// CodeNo.
																			// doesn't
																			// contain
																			// in
																			// existing
																			// agentSaleReportMap
				double totalPremium = agentSale.getTotalmotorPremium() + agentSale.getTotalfirePremium() + agentSale.getTotalCargoPremium();
				int policyCount = agentSale.getMotorPolicy() + agentSale.getFirePolicy() + agentSale.getCargoPolicy();
				agentSale.setPolicyCount(policyCount);

				agentSale.setTotalAmount(totalPremium);
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSale);
			} else {
				AgentSaleComparisonReport agentSaleReport = agentSaleReportMap.get(agentSale.getCodeNo());
				int policyCount = 0;
				double totalAmount = 0;
				if (referenceType.equals("MOTOR_POLICY")) {
					policyCount = agentSale.getMotorPolicy() + agentSaleReport.getFirePolicy() + agentSaleReport.getCargoPolicy();
					totalAmount = agentSale.getTotalmotorPremium() + agentSaleReport.getTotalfirePremium() + agentSaleReport.getTotalCargoPremium();
					agentSaleReport.setMotorPolicy(agentSale.getMotorPolicy());
					agentSaleReport.setTotalmotorPremium(agentSale.getTotalmotorPremium());
				} else if (referenceType.equals("FIRE_POLICY")) {
					policyCount = agentSale.getFirePolicy() + agentSaleReport.getMotorPolicy() + agentSaleReport.getCargoPolicy();
					totalAmount = agentSale.getTotalfirePremium() + agentSaleReport.getTotalmotorPremium() + agentSaleReport.getTotalCargoPremium();
					agentSaleReport.setFirePolicy(agentSale.getFirePolicy());
					agentSaleReport.setTotalfirePremium(agentSale.getTotalfirePremium());
				} else {
					policyCount = agentSale.getCargoPolicy() + agentSaleReport.getMotorPolicy() + agentSaleReport.getFirePolicy();
					totalAmount = agentSale.getTotalCargoPremium() + agentSaleReport.getTotalmotorPremium() + agentSaleReport.getTotalfirePremium();
					agentSaleReport.setCargoPolicy(agentSale.getCargoPolicy());
					agentSaleReport.setTotalCargoPremium(agentSale.getTotalCargoPremium());
				}
				agentSaleReport.setPolicyCount(policyCount);
				agentSaleReport.setTotalAmount(totalAmount);
				agentSaleReportMap.put(agentSale.getCodeNo(), agentSaleReport);
			}
		}
	}

}
