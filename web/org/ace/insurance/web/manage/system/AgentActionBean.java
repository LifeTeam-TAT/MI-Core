/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.system.common.agent.AGP002;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AgentActionBean")
public class AgentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentService}")
	private IAgentService agentService;

	public void setAgentService(IAgentService agentService) {
		this.agentService = agentService;
	}

	private AgentCriteria agentCriteria;
	private List<AGP002> agentList;

	@PostConstruct
	public void init() {
		resetAgent();
	}

	public void resetAgent() {
		agentCriteria = new AgentCriteria();
		agentList = new ArrayList<>();
	}

	public void searchAgent() {
		agentList = agentService.findAgentByCriteria(agentCriteria, 30);
	}

	public void outjectAgent(Agent agent) {
		putParam("agent", agent);

	}

	public String updateAgent(AGP002 agp002) {
		String result = null;
		try {
			Agent agent = agentService.findAgentById(agp002.getId());
			outjectAgent(agent);
			result = "addNewAgent";

		} catch (SystemException ex) {
			addErrorMessage(null, MessageId.CHILD_RECORD_FOUND, ex);
		}

		return result;
	}

	public String creatNewAgent() {
		return "addNewAgent";
	}

	public void deleteAgent(AGP002 agp002) {
		try {
			Agent agent = agentService.findAgentById(agp002.getId());
			agentService.deleteAgent(agent);
			agentList.remove(agp002);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, agp002.getFullName());
		} catch (SystemException ex) {
			String response = (String) ex.getResponse();
			addErrorMessage(null, MessageId.CHILD_RECORD_FOUND, response);
		}
	}

	// private final String reportName = "AgentInfo";
	// private final String pdfDirPath = "/pdf-report/" + reportName + "/" +
	// System.currentTimeMillis() + "/";
	// private final String dirPath = getWebRootPath() + pdfDirPath;
	// private final String fileName = reportName + ".pdf";
	//
	// public String getReportStream() {
	// return pdfDirPath + fileName;
	// }
	//
	// public void generateReport(AGP002 agp002) {
	// Agent agent = agentService.findAgentById(agp002.getId());
	// DocumentBuilder.generateInfo(customer, dirPath, fileName);
	//
	// }

	// public void handleClose(CloseEvent event) {
	// try {
	// org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public AgentCriteria getAgentCriteria() {
		return agentCriteria;
	}

	public List<AGP002> getAgentList() {
		return agentList;
	}

	public void checkMessage(ComponentSystemEvent event) {
		String messageId = (String) getParam(Constants.MESSAGE_ID);
		String proposalNo = (String) getParam(Constants.PROPOSAL_NO);
		if (messageId != null && proposalNo != null) {
			addInfoMessage(null, messageId, proposalNo);
			removeParam(Constants.MESSAGE_ID);
			removeParam(Constants.PROPOSAL_NO);
		}
	}

}
