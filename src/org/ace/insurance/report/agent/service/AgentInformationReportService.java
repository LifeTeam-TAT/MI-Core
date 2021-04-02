package org.ace.insurance.report.agent.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentInformationReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentInformationReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentInformationReportService")
public class AgentInformationReportService implements IAgentInformationReportService {

	@Resource(name = "AgentInformationReportDAO")
	private IAgentInformationReportDAO lifeProposalReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentInformationReport> findAgentInformation(AgentInformationCriteria criteria) {
		List<AgentInformationReport> result = null;
		try {
			result = lifeProposalReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentInformationReport by criteria.", e);
		}
		return result;
	}

	@Override
	public void generateReport(List<AgentInformationReport> reportList, String filePath, String fileName) {
		final String templatePath = "/report-template/agent/";
		final String templateName = "agentInformationReport.jrxml";
		String templateFullPath = templatePath + templateName;
		String outputFilePdf = filePath + fileName;

		// Create the DataSource instance with the given list which
		// in turn to be filled up in the report
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportList);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("TableDataSource", ds);

		new JRGenerateUtility().generateReport(templateFullPath, outputFilePdf, paramMap);
	}

	@Override
	public void generateAgentDetails(AgentInformationReport agent, String filePath, String fileName, InputStream is) {
		final String templatePath = "/report-template/agent/";
		final String templateName = "agentInformationDetailReport.jrxml";
		String templateFullPath = templatePath + templateName;
		String outputFilePdf = filePath + fileName;

		// Create the DataSource instance with the given list which
		// in turn to be filled up in the report
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("image", is);
		paramMap.put("name", agent.getAgentName());
		paramMap.put("nrc", agent.getNrc());
		paramMap.put("dob", agent.getDob());
		paramMap.put("codeNo", agent.getAgentCode());
		paramMap.put("service", agent.getService());
		paramMap.put("appDate", agent.getAppDate());
		paramMap.put("age", agent.getAge());
		paramMap.put("training", agent.getTraining() == null ? "-" : agent.getTraining());
		paramMap.put("qualification", agent.getQualificaiton() == null ? "-" : agent.getQualificaiton());
		paramMap.put("address", agent.getAddress());
		paramMap.put("mobile", agent.getMobile() == null ? "-" : agent.getMobile());
		paramMap.put("telephone", agent.getPhoneNo() == null ? "" : agent.getPhoneNo());
		paramMap.put("email", agent.getEmail() == null ? "-" : agent.getEmail());
		paramMap.put("outstand", agent.getOutstandingEvent() == null ? "-" : agent.getOutstandingEvent());
		paramMap.put("licenseNo", agent.getLiscenseNo());
		paramMap.put("organization", agent.getOrganization() == null ? "-" : agent.getOrganization());
		paramMap.put("typeOfAgent", agent.getGroupType() == null ? "-" : agent.getGroupType());
		new JRGenerateUtility().generateReport(templateFullPath, outputFilePdf, paramMap);
	}

}
