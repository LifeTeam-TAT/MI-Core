package org.ace.insurance.report.medical.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.medical.HealthProposalReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IHealthProposalReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IHealthProposalReportService;
import org.ace.insurance.web.manage.report.medical.HealthProposalReportCriteria;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.web.ApplicationSetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Service(value = "HealthProposalReportService")
public class HealthProposalReportService implements IHealthProposalReportService {
	@Resource(name = "HealthProposalReportDAO")
	private IHealthProposalReportDAO healthProposalReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HealthProposalReportDTO> findHealthProposalReportDTO(HealthProposalReportCriteria criteria) {
		List<HealthProposalReportDTO> resultList = new ArrayList<HealthProposalReportDTO>();
		try {
			resultList = healthProposalReportDAO.findHealthProposalReportDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthProposalReportDTO", e);
		}
		return resultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void generateHealthProposalReport(List<HealthProposalReportDTO> healthProposalReportList, String dirPath, String fileName, String branchName) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reportLogo", ApplicationSetting.getCompanyLogoNew());
		params.put("reportAddressLogo", ApplicationSetting.getGGIAddress());
		params.put("branchName", branchName);
		params.put("dataList", healthProposalReportList);

		InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/medical/healthProposalReport.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, policyJP);
		FileHandler.forceMakeDirectory(dirPath);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
		exporter.exportReport();
	}
}
