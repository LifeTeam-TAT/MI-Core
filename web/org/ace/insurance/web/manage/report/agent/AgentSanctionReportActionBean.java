package org.ace.insurance.web.manage.report.agent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.report.agent.service.interfaces.IAgentSanctionService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentSanctionReportActionBean")
public class AgentSanctionReportActionBean<T extends IDataModel> extends BaseBean {
	@ManagedProperty(value = "#{AgentSanctionService}")
	private IAgentSanctionService agentSanctionService;

	public void setAgentSanctionService(IAgentSanctionService agentSanctionService) {
		this.agentSanctionService = agentSanctionService;
	}

	@ManagedProperty(value = "#{CurrencyService}")
	private ICurrencyService currencyService;

	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	private boolean sanction;
	private AgentSanctionCriteria criteria;
	private List<AgentSanctionInfo> agentSanctionList;
	private List<AgentSanctionInfo> selectedList;
	private List<Currency> currencyList;
	private Agent agent;

	private final String reportName = "AgentSanctionReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	@PostConstruct
	public void init() {
		reset();
		currencyList = currencyService.findAllCurrency();
	}

	public void reset() {
		criteria = new AgentSanctionCriteria();
		Calendar cal = Calendar.getInstance();
		if (criteria.getStartDate() == null) {
			cal.add(Calendar.DAY_OF_MONTH, -7);
			criteria.setStartDate(cal.getTime());
		}

		if (criteria.getEndDate() == null)
			criteria.setEndDate(new Date());

		agentSanctionList = Collections.emptyList();
		selectedList = Collections.emptyList();
		sanction = false;

	}

	public void filter() {
		agentSanctionList = agentSanctionService.findAgents(criteria);
		sanction = false;
		if (selectedList != null)
			selectedList.clear();
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public void sanctionAgent() {
		try {
			if (!selectedList.isEmpty()) {
				agentSanctionService.sanctionAgent(selectedList);
				sanction = true;
				addInfoMessage(null, MessageId.AGENT_SANCTION);
			} else {
				addInfoMessage(null, MessageId.ATLEAST_ONE_AGENTCOMMISSION);
			}
		} catch (SystemException exception) {
			handelException(exception);
		}
	}

	public void returnAgent(SelectEvent event) {
		agent = (Agent) event.getObject();
		criteria.setAgentId(agent.getId());
		criteria.setAgentName(agent.getFullName());
	}

	public AgentSanctionCriteria getCriteria() {
		return criteria;
	}

	public List<AgentSanctionInfo> getAgentSanctionList() {
		return agentSanctionList;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public String getReportName() {
		return reportName;
	}

	public List<PolicyReferenceType> getReferenceTypes() {
		return Arrays.asList(PolicyReferenceType.PA_POLICY, PolicyReferenceType.FARMER_POLICY, PolicyReferenceType.SNAKE_BITE_POLICY, PolicyReferenceType.GROUP_LIFE_POLICY,
				PolicyReferenceType.SPORT_MAN_POLICY, PolicyReferenceType.ENDOWNMENT_LIFE_POLICY, PolicyReferenceType.LIFE_BILL_COLLECTION,
				PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY, PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION, PolicyReferenceType.HEALTH_POLICY,
				PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION, PolicyReferenceType.MICRO_HEALTH_POLICY, PolicyReferenceType.CRITICAL_ILLNESS_POLICY,
				PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION, PolicyReferenceType.STUDENT_LIFE_POLICY, PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION,
				PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY, PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY_BILL_COLLECTION, 
				PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY, PolicyReferenceType.SEAMAN_LIFE_POLICY);
	}

	public boolean isSanction() {
		return sanction;
	}

	public List<AgentSanctionInfo> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<AgentSanctionInfo> selectedList) {
		this.selectedList = selectedList;
	}

	public void handleClose(CloseEvent event) {
		try {
			FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateAgentComission() {
		DocumentBuilder.generateAgentSanctionReport(selectedList, agent, criteria, dirPath, fileName);
	}

}
