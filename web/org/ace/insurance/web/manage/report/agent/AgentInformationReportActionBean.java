package org.ace.insurance.web.manage.report.agent;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.common.AgentCriteriaItems;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentInformationReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentInformationReportActionBean")
public class AgentInformationReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	@ManagedProperty(value = "#{AgentInformationReportService}")
	private IAgentInformationReportService agentInformationService;

	public void setAgentInformationService(IAgentInformationReportService agentInformationService) {
		this.agentInformationService = agentInformationService;
	}

	@ManagedProperty(value = "#{OrganizationService}")
	private IOrganizationService organizationService;

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	private boolean attachFlag;
	private AgentInformationCriteria criteria;
	private AgentInformationReport agentIndi;
	private Agent agent;
	private List<AgentInformationReport> agentInformationList;
	private final String reportName = "AgentInformationReport";
	private final String agentReportName = "AgentInformationIndividualReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String agentPdfDirPath = "/pdf-report/" + agentReportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String agentDirPath = getSystemPath() + agentPdfDirPath;
	private final String fileName = reportName + ".pdf";
	private final String agentFileName = agentReportName + ".pdf";
	private List<Agent> agentList;
	private AgentCriteria agentCriteria;
	private String selectedAgentCriteria;
	private List<SelectItem> agentCriteriaItemList;

	@PostConstruct
	public void init() {
		resetAgentCriteria();
	}

	public void createNewAgentCriteria() {
		criteria = new AgentInformationCriteria();
	}

	public boolean isAttachFlag() {
		return attachFlag;
	}

	public void setAttach(boolean attachFlag) {
		this.attachFlag = attachFlag;
	}

	public AgentInformationReport getAgentIndi() {
		return agentIndi;
	}

	public void setAgentIndi(AgentInformationReport agent) {
		this.agentIndi = agent;
	}

	public void prepareIndividual(AgentInformationReport agent) {
		if (agent.filePath == null || agent.filePath.isEmpty())
			attachFlag = false;
		else
			attachFlag = true;

		this.agentIndi = agent;
	}

	public List<AgentInformationReport> getAgentInformationList() {
		return agentInformationList;
	}

	public void setAgentInformationList(List<AgentInformationReport> agentInformationList) {
		this.agentInformationList = agentInformationList;
	}

	public AgentInformationCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentInformationCriteria criteria) {
		this.criteria = criteria;
	}

	public void filter() {
		agentInformationList = agentInformationService.findAgentInformation(criteria);
		criteria = new AgentInformationCriteria();
	}

	public IAgentService getAgentService() {
		return agentService;
	}

	public ReferenceType[] getReferenceTypes() {
		return ReferenceType.values();
	}

	/*
	 * ** Agent methods start
	 */

	public AgentCriteria getAgentCriteria() {
		return agentCriteria;
	}

	public List<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
	}

	public void setAgentCriteria(AgentCriteria agentCriteria) {
		this.agentCriteria = agentCriteria;
	}

	public String getSelectedAgentCriteria() {
		return selectedAgentCriteria;
	}

	public void setSelectedAgentCriteria(String selectedAgentCriteria) {
		this.selectedAgentCriteria = selectedAgentCriteria;
	}

	public List<SelectItem> getAgentCriteriaItemList() {
		return agentCriteriaItemList;
	}

	public void setAgentCriteriaItemList(List<SelectItem> agentCriteriaItemList) {
		this.agentCriteriaItemList = agentCriteriaItemList;
	}

	public ProductGroupType[] getProductGroupTypeSelectItemList() {
		return ProductGroupType.values();
	}

	public void searchAgent() {
		agentCriteria.setAgentCriteriaItems(null);
		for (AgentCriteriaItems criteriaItem : AgentCriteriaItems.values()) {
			if (criteriaItem.toString().equals(selectedAgentCriteria)) {
				agentCriteria.setAgentCriteriaItems(criteriaItem);
			}
		}
		if (inputCheck(agentCriteria)) {
			agentList = agentService.findAgentByCriteria(agentCriteria);
		}
	}

	private boolean inputCheck(AgentCriteria agentCriteria) {
		boolean result = true;
		if (agentCriteria.getAgentCriteriaItems() == null) {
			addErrorMessage("selectCustomerForm:selectMotorCustomerCriteria", MessageId.REQUIRED_VALUES);
			result = false;
		}
		return result;
	}

	public void resetAgentCriteria() {
		createNewAgentCriteria();
		agent = new Agent();
		agentList = agentService.findAllAgent();
		agentCriteria = new AgentCriteria();
		agentCriteriaItemList = new ArrayList<SelectItem>();
		for (AgentCriteriaItems criteriaItem : AgentCriteriaItems.values()) {
			agentCriteriaItemList.add(new SelectItem(criteriaItem.getLabel(), criteriaItem.getLabel()));
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		agentInformationList = new ArrayList<AgentInformationReport>();
	}

	public void reset() {
		resetAgentCriteria();
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public String getAgentStream() {
		String fileFullPath = agentPdfDirPath + agentFileName;
		return fileFullPath;
	}

	public void generateAgentDetails(AgentInformationReport agent) {
		try {
			InputStream is = null;
			if (agent.getFilePath() != null) {
				is = getFacesContext().getExternalContext().getResourceAsStream(agent.getFilePath());
			}
			FileHandler.forceMakeDirectory(agentDirPath);
			agentInformationService.generateAgentDetails(agent, agentDirPath, agentFileName, is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateReport() {
		try {
			FileHandler.forceMakeDirectory(dirPath);
			List<AgentInformationReport> reports = getAgentInformationList();
			agentInformationService.generateReport(reports, dirPath, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		criteria.setOrganization(organization);
	}
}