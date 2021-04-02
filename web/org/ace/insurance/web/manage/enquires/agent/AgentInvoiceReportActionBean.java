package org.ace.insurance.web.manage.enquires.agent;

import java.io.IOException;
import java.io.Serializable;
/**
 * @author NNH
 * @since 1.0.0
 * @date 2014/Feb/11
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.report.agent.AgentInvoiceCriteria;
import org.ace.insurance.report.agent.AgentInvoiceDTO;
import org.ace.insurance.report.agent.AgentInvoiceReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentInvoiceReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentInvoiceReportActionBean")
public class AgentInvoiceReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentInvoiceReportService}")
	private IAgentInvoiceReportService agentInvoiceReportService;

	public void setAgentInvoiceReportService(IAgentInvoiceReportService agentInvoiceReportService) {
		this.agentInvoiceReportService = agentInvoiceReportService;
	}

	private AgentInvoiceCriteria criteria;
	private List<AgentInvoiceDTO> agentInvoiceDTOList;
	private User user;
	private Map<String, List<AgentCommission>> agentCommissionMap;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		agentCommissionMap = new HashMap<String, List<AgentCommission>>();
		resetCriteria();
	}

	private void resetCriteria() {
		criteria = new AgentInvoiceCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		agentInvoiceDTOList = new ArrayList<AgentInvoiceDTO>();
		agentCommissionMap = new HashMap<String, List<AgentCommission>>();
	}

	public void reset() {
		resetCriteria();
	}

	public void searchByCriteria() {
		agentInvoiceDTOList = agentInvoiceReportService.findAgentInvoiceReports(criteria);
		populateAgentInvoiceReport();
	}

	public void populateAgentInvoiceReport() {
		// agentInvoiceReportList = new ArrayList<AgentInvoiceReport>();
		// agentCommissionMap = new HashMap<String, List<AgentCommission>>();
		// String key = null;
		// for (AgentInvoiceDTO commission : agentInvoiceDTOList) {
		// key = commission.getAgent().getCodeNo() + commission.getInvoiceNo();
		// if (agentCommissionMap.containsKey(key)) {
		// agentCommissionMap.get(key).add(commission);
		// } else {
		// List<AgentCommission> commissionList = new
		// ArrayList<AgentCommission>();
		// commissionList.add(commission);
		// agentCommissionMap.put(key, commissionList);
		// }
		// }
		// for (Map.Entry<String, List<AgentCommission>> entry :
		// agentCommissionMap.entrySet()) {
		// AgentInvoiceReport report = new AgentInvoiceReport(entry.getValue());
		// agentInvoiceReportList.add(report);
		// }
	}

	public Map<String, List<AgentCommission>> getAgentCommissionMap() {
		return agentCommissionMap;
	}

	public void setAgentCommissionMap(Map<String, List<AgentCommission>> agentCommissionMap) {
		this.agentCommissionMap = agentCommissionMap;
	}

	public AgentInvoiceCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentInvoiceCriteria criteria) {
		this.criteria = criteria;
	}

	private final String agentReportName = "AgentInvoiceReport";
	private final String agentPdfDirPath = "/pdf-report/" + agentReportName + "/" + System.currentTimeMillis() + "/";
	private final String agentDirPath = getSystemPath() + agentPdfDirPath;
	private final String agentFileName = agentReportName + ".pdf";

	public void generateAgentDetails(AgentInvoiceReport agentReport) {
		String key = agentReport.getAgentCodeNo() + agentReport.getInvoiceNo();
		// agentService.generateAgentInvoice(agentCommissionMap.get(key), true,
		// agentDirPath, agentFileName);
	}

	public String getAgentStream() {
		String fileFullPath = agentPdfDirPath + agentFileName;
		return fileFullPath;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(agentDirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		this.criteria.setAgentId(agent.getId());
		this.criteria.setAgentName(agent.getFullName());
	}

	public List<AgentInvoiceDTO> getAgentInvoiceDTOList() {
		return agentInvoiceDTOList;
	}

	public List<Branch> getBranches() {
		return user.getAccessBranchList();
	}

}
