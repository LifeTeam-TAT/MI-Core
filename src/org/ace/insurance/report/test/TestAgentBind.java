package org.ace.insurance.report.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.insurance.system.common.agent.Agent;

public class TestAgentBind {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<AgentSaleReport> list1 = new ArrayList<AgentSaleReport>();
		Agent agent = new Agent();
		agent.setCodeNo("one");
		
		Agent a2 = new Agent();
		a2.setCodeNo("two");
		
		AgentSaleReport asr = new AgentSaleReport(agent, 1);
		asr.setProductName("LP");
		asr.setPublicLifeCount(34);
		AgentSaleReport asr2 = new AgentSaleReport(a2, 10);
		asr2.setProductName("LP");
		asr2.setPublicLifeCount(34);
		AgentSaleReport asr5 = new AgentSaleReport(a2, 10);
		asr5.setProductName("FP");
		asr5.setGroupLifeCount(34);
		list1.add(asr2);
		list1.add(asr);
		list1.add(asr5);
		Map<String, AgentSaleReport> agentSaleReportMap = new HashMap<String, AgentSaleReport>();
		for(AgentSaleReport agentSaleReport : list1){
			AgentSaleReport oldASR = agentSaleReport;
			
			if(agentSaleReportMap.containsKey(agentSaleReport.getAgentCodeNo())){
				
				oldASR = agentSaleReportMap.get(agentSaleReport.getAgentCodeNo());
			}
			if(agentSaleReport.getProductName().equals("LP")){
			
				oldASR.setPublicLifeCount(agentSaleReport.getPublicLifeCount());
				oldASR.setPublicLifePremium(agentSaleReport.getLifePolicyTotalPremium());
				oldASR.setPublicLifeCommission(agentSaleReport.getLifePolicyTotalCommission());
			} 
			else if(agentSaleReport.getProductName().equals("FP")){
				oldASR.setGroupLifeCount(agentSaleReport.getGroupLifeCount());
				oldASR.setGroupLifePremium(agentSaleReport.getGroupLifePremium());
				oldASR.setGroupLifeCommission(agentSaleReport.getLifePolicyTotalCommission());
			}else{
				oldASR.setSankeBiteCount(agentSaleReport.getLifePolicyCount());
				oldASR.setSnakeBitePremium(agentSaleReport.getLifePolicyTotalPremium());
				oldASR.setSankeBiteCommission(agentSaleReport.getLifePolicyTotalCommission());
			}
		 
			oldASR.setTotalPremium(agentSaleReport.getPublicLifePremium()+ agentSaleReport.getGroupLifePremium()+ agentSaleReport.getSnakeBitePremium());
			oldASR.setTotalCommission(agentSaleReport.getPublicLifeCommission()+ agentSaleReport.getGroupLifeCommission()+ agentSaleReport.getSankeBiteCommission());
			agentSaleReportMap.put(agentSaleReport.getAgentCodeNo(), oldASR);
		}
		
		System.out.println(agentSaleReportMap.size());
		List<AgentSaleReport> list2 = new ArrayList<AgentSaleReport>();
		 
		Agent a3 = new Agent();
		a3.setCodeNo("three");
		
		AgentSaleReport asr3 = new AgentSaleReport(agent, 1546432);
		AgentSaleReport asr4 = new AgentSaleReport(a3, 1221);
		list2.add(asr3);
		list2.add(asr4);
		
	 	list1.addAll(list2);
		HashMap<String, AgentSaleReport> hm = new HashMap<String, AgentSaleReport>();
		
		for(AgentSaleReport a : list1){ 
			hm.put(a.getAgentCodeNo(), a);
		}
		for(AgentSaleReport a : list2){ 
			hm.put(a.getAgentCodeNo(), a);
		}
		/*
		 * YOu can set result like that.. :D
		 */
	 /*   List<AgentSaleReport> result = (List<AgentSaleReport>) hm.values();//new ArrayList<AgentSaleReport>();
		for(AgentSaleReport a : hm.values()){
			result.add(a);
		 
		}*/
	 
		for(AgentSaleReport a : agentSaleReportMap.values()){
			System.out.println(a.getAgentCodeNo() + " "+ a.getPublicLifeCount()+ " "+a.getGroupLifeCount());
		}
		
		
	}

}
