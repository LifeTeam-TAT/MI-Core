package org.ace.insurance.report.medical.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalPolicyMonthlyReportDTO;
import org.ace.insurance.report.medical.persistence.interfaces.IMedicalPolicyMonthlyReportDAO;
import org.ace.insurance.report.medical.service.interfaces.IMedicalPolicyMonthlyReportService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
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

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-11-17
 * @Version 1.0
 * @Purpose This class serves as Service Layer to show the
 *          <code>MedicalPolicyMonthlyReportService</code> Policy Monthly Report
 *          process.
 * 
 ***************************************************************************************/

@Service(value = "MedicalPolicyMonthlyReportService")
public class MedicalPolicyMonthlyReportService extends BaseService implements IMedicalPolicyMonthlyReportService {

	@Resource(name = "MedicalPolicyMonthlyReportDAO")
	private IMedicalPolicyMonthlyReportDAO medicalPolicyMonthlyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPolicyMonthlyReportDTO> findMedicalPolicyMonthlyReport(MonthlyReportCriteria criteria) {
		List<MedicalPolicyMonthlyReportDTO> resultList = new ArrayList<MedicalPolicyMonthlyReportDTO>();
		try {
			resultList = medicalPolicyMonthlyReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find healthMonthlyReportDTO", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateMedicalPolicyMonthlyReport(List<MedicalPolicyMonthlyReportDTO> medicalPolicyList, String dirPath, String fileName, String branchName) throws Exception {

		List jasperPrintList = new ArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		int totalUnits = 0;
		double totalPremium = 0.0;
		double totalCommission = 0.0;
		for (MedicalPolicyMonthlyReportDTO report : medicalPolicyList) {
			totalUnits += Integer.valueOf(report.getUnit());
			totalPremium += report.getPremium();
			totalCommission += report.getCommission();
		}
		params.put("totalUnits", totalUnits);
		params.put("totalPremium", totalPremium);
		params.put("totalCommission", totalCommission);
		params.put("dataSource", medicalPolicyList);
		InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/medical/medicalPolicy_MonthlyReport.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
		jasperPrintList.add(policyJP);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		FileHandler.forceMakeDirectory(dirPath);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
		exporter.exportReport();
	}
}
