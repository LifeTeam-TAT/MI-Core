package org.ace.insurance.web.manage.enquires.agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.report.agent.service.interfaces.IAgentSanctionService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentSanctionEnquiryActionBean")
public class AgentSanctionEnquiryActionBean extends BaseBean {

	@ManagedProperty(value = "#{AgentSanctionService}")
	private IAgentSanctionService agentSanctionService;

	public void setAgentSanctionService(IAgentSanctionService agentSanctionService) {
		this.agentSanctionService = agentSanctionService;
	}

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	private List<AgentSanctionDTO> agentSanctionDTOList;
	private AgentSanctionCriteria criteria;
	private User user;

	@PostConstruct
	private void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		resetCriteria();
	}

	public List<AgentSanctionDTO> searchByCriteria() {
		agentSanctionDTOList = agentSanctionService.findAgentSanctionDTO(criteria);
		return agentSanctionDTOList;
	}

	public void resetCriteria() {
		criteria = new AgentSanctionCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		criteria.setEnquiry(true);
		agentSanctionDTOList = new ArrayList<AgentSanctionDTO>();
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgentId(agent.getId());
		criteria.setAgentName(agent.getFullName());
	}

	private final String reportName = "AgentSanctionEnquiryReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	public void generateReport(AgentSanctionDTO agentSanctionDTO) {
		List<AgentSanctionInfo> agentSanctionList = agentSanctionService.findAgentCommissionBySanctionNo(agentSanctionDTO.getSanctionNo());
		String agentId = agentSanctionList.get(0).getId();
		Agent agent = agentService.findAgentById(agentId);
		DocumentBuilder.generateAgentSanctionReport(agentSanctionList, agent, criteria, dirPath, fileName);
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Branch> getBranches() {
		return user.getAccessBranchList();
	}

	public List<AgentSanctionDTO> getAgentSanctionDTOList() {
		return agentSanctionDTOList;
	}

	public void setCriteria(AgentSanctionCriteria criteria) {
		this.criteria = criteria;
	}

	public AgentSanctionCriteria getCriteria() {
		return criteria;
	}

}
