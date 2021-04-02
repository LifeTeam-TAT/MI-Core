package org.ace.insurance.web.common.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AGSAN001;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.web.common.ntw.mym.AbstractMynNumConvertor;
import org.ace.insurance.web.common.ntw.mym.DefaultConvertor;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AgentDocumentBuilder extends JasperFactory {
	public static List<JasperPrint> generateAgentSanctionRepor(List<AgentSanctionInfo> sanctionInfoList, Agent agent, AgentSanctionCriteria criteria) {
		List<JasperPrint> jpringList = new ArrayList<JasperPrint>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String agentName = agent.getFullName();
		String licenseNo = agent.getLiscenseNo();
		String agentNrc = agent.getFullIdNo();
		String agentPhno = agent.getContentInfo().getPhoneOrMoblieNo();
		String agentAccountNo = agent.getAccountNoForView();
		Date minDate = sanctionInfoList.stream().map(AgentSanctionInfo::getPaymentDate).min(Date::compareTo).get();
		Date maxDate = sanctionInfoList.stream().map(AgentSanctionInfo::getPaymentDate).max(Date::compareTo).get();
		String startDate = Utils.formattedDate(minDate);
		String endDate = Utils.formattedDate(maxDate);
		String currency = "";
		if (criteria.getCurrencyCode().equalsIgnoreCase("USD")) {
			currency = "\u1031\u1012\u105A\u101C\u102C";
		} else {
			currency = "\u1000\u103A\u1015\u1039";
		}
		double totalPremium = 0.00;
		double totalCommession = 0.00;
		for (AgentSanctionInfo sanction : sanctionInfoList) {
			totalPremium = totalPremium + sanction.getPremium();
			totalCommession = totalCommession + sanction.getComission();
		}
		paramMap.put("currentDate", new Date());
		paramMap.put("agentName", agentName);
		paramMap.put("licenseNo", licenseNo);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("agentNrc", agentNrc);
		paramMap.put("agentPhno", agentPhno);
		paramMap.put("agentAccountNo", agentAccountNo);
		paramMap.put("totalPremium", Utils.formattedCurrency(totalPremium));
		paramMap.put("totalComission", Utils.formattedCurrency(totalCommession));
		paramMap.put("currency", currency);
		JasperPrint jprint = JasperFactory.generateJasperPrint(paramMap, JasperTemplate.AGENT_SANCTION, new JREmptyDataSource());
		jpringList.add(jprint);
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("fromDate", startDate);
		paramMap3.put("toDate", endDate);
		paramMap3.put("agentName", agentName);
		paramMap3.put("agentLicenseNo", licenseNo);
		paramMap3.put("commissionDetailDataSoruce", new JRBeanCollectionDataSource(sanctionInfoList));
		paramMap.put("currency", currency);
		if (criteria.getCurrencyCode().equalsIgnoreCase("USD")) {
			JasperPrint jprint3 = JasperFactory.generateJasperPrint(paramMap3, JasperTemplate.AGENT_COMMISSION_DOLLAR_DETAIL, new JREmptyDataSource());
			jpringList.add(jprint3);
		} else {
			JasperPrint jprint3 = JasperFactory.generateJasperPrint(paramMap3, JasperTemplate.AGENT_COMMISSION_DETAIL, new JREmptyDataSource());
			jpringList.add(jprint3);
		}

		Map<PolicyReferenceType, List<AgentSanctionInfo>> sanctionMap = groupByPolicyReferenceType(sanctionInfoList);
		List<AGSAN001> commissionList = new ArrayList<AGSAN001>();
		for (Map.Entry<PolicyReferenceType, List<AgentSanctionInfo>> entry : sanctionMap.entrySet()) {
			PolicyReferenceType type = entry.getKey();
			List<AgentSanctionInfo> sanctionList = entry.getValue();
			double totalCommission = 0.00;
			for (AgentSanctionInfo sanction : sanctionList) {
				totalCommission = totalCommission + sanction.getComission();
			}
			String sanctionDescription = "From ( " + startDate + " )" + "To (" + endDate + " ) " + type.getLabel();
			AGSAN001 commission = new AGSAN001(sanctionDescription, totalCommission, agentName);
			commissionList.add(commission);
		}
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		AbstractMynNumConvertor convertor = new DefaultConvertor();
		String currrencyword = "";
		if (criteria.getCurrencyCode().equalsIgnoreCase("USD")) {
			currrencyword = convertor.getNameWithDollarDecimal(totalCommession);
		} else {
			currrencyword = convertor.getNameWithDecimal(totalCommession);
		}
		paramMap2.put("commissionDataSource", new JRBeanCollectionDataSource(commissionList));
		paramMap2.put("currentDate", new Date());
		paramMap.put("currency", currency);
		paramMap2.put("totalCommissionInword", currrencyword);
		if (criteria.getCurrencyCode().equalsIgnoreCase("USD")) {
			JasperPrint jprint2 = JasperFactory.generateJasperPrint(paramMap2, JasperTemplate.AGENT_DOLLAR_COMMISSION, new JREmptyDataSource());
			jpringList.add(jprint2);
		} else {
			JasperPrint jprint2 = JasperFactory.generateJasperPrint(paramMap2, JasperTemplate.AGENT_COMMISSION, new JREmptyDataSource());
			jpringList.add(jprint2);
		}
		return jpringList;
	}

	public static Map<PolicyReferenceType, List<AgentSanctionInfo>> groupByPolicyReferenceType(List<AgentSanctionInfo> agentSanctionList) {
		for (AgentSanctionInfo agentSanctionInfo : agentSanctionList) {

			if (agentSanctionInfo.getReferenceType().equals(PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION)) {
				agentSanctionInfo.setReferenceType(PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY);
			}
			if (agentSanctionInfo.getReferenceType().equals(PolicyReferenceType.LIFE_BILL_COLLECTION)) {
				agentSanctionInfo.setReferenceType(PolicyReferenceType.ENDOWNMENT_LIFE_POLICY);
			}
			if (agentSanctionInfo.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)) {
				agentSanctionInfo.setReferenceType(PolicyReferenceType.HEALTH_POLICY);
			}
			if (agentSanctionInfo.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)) {
				agentSanctionInfo.setReferenceType(PolicyReferenceType.CRITICAL_ILLNESS_POLICY);
			}
		}
		Map<PolicyReferenceType, List<AgentSanctionInfo>> map = new TreeMap<PolicyReferenceType, List<AgentSanctionInfo>>();
		for (AgentSanctionInfo sanction : agentSanctionList) {
			List<AgentSanctionInfo> sanctionGroup = map.get(sanction.getReferenceType());
			if (sanctionGroup == null) {
				sanctionGroup = new ArrayList<AgentSanctionInfo>();
				map.put(sanction.getReferenceType(), sanctionGroup);
			}
			sanctionGroup.add(sanction);
		}
		return map;
	}

}
