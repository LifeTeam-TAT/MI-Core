package org.ace.insurance.report.life.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.life.LifeProposalCriteria;
import org.ace.insurance.report.life.LifeProposalReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifeProposalReportDAO;
import org.ace.insurance.report.life.service.interfaces.ILifeProposalReportService;
import org.ace.insurance.report.personalAccident.PersonalAccidentProposalReport;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Service(value = "LifeProposalReportService")
public class LifeProposalReportService implements ILifeProposalReportService {

	@Resource(name = "LifeProposalReportDAO")
	private ILifeProposalReportDAO lifeProposalReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeProposalReport> findLifeProposal(LifeProposalCriteria lifeProposalCriteria, List<String> productIdList) {
		List<LifeProposalReport> result = null;
		try {
			result = lifeProposalReportDAO.find(lifeProposalCriteria, productIdList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeProposalReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateLifeProposalReport(List<LifeProposalReport> lifeProposalReportList, String dirPath, String fileName, String branchName) throws Exception {
		List jasperPrintList = new ArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		double totalSumInsured = 0.0;
		double totalPremium = 0.0;
		for (LifeProposalReport report : lifeProposalReportList) {
			totalSumInsured += report.getSumInsured();
			totalPremium += report.getPremium();
		}
		params.put("LifeProposals", new JRBeanCollectionDataSource(lifeProposalReportList));
		params.put("totalSumInsured", totalSumInsured);
		params.put("totalPremium", totalPremium);
		params.put("branch", branchName);
		InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/lifeProposalReport.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
		jasperPrintList.add(policyJP);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		FileHandler.forceMakeDirectory(dirPath);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
		exporter.exportReport();

		// JRXlsExporter exporterXLS = new JRXlsExporter();
		// exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
		// policyJP);
		// exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, new
		// FileOutputStream(dirPath + "TEST.xls"));
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
		// Boolean.FALSE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
		// Boolean.TRUE);
		// exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
		// Boolean.FALSE);
		// exporterXLS.exportReport();

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PersonalAccidentProposalReport> findPersonalAccidentProposal(LifeProposalCriteria lifeProposalCriteria) {
		List<PersonalAccidentProposalReport> resultList;
		try {
			resultList = lifeProposalReportDAO.findPersonalAccidentProposal(lifeProposalCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find personal accident proposal report", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generatePersonalAcdtProposalReport(List<PersonalAccidentProposalReport> personalAcdtProposalReportList, String dirPath, String fileName, String branchName)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataList", personalAcdtProposalReportList);

		InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/personalAccident/personalAccidentProposalReport.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, policyJP);
		FileHandler.forceMakeDirectory(dirPath);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
		exporter.exportReport();
	}

}
