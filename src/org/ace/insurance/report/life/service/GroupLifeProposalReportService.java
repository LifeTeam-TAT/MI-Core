package org.ace.insurance.report.life.service;

/**
 * @author NNH
 */
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.life.GroupLifeProposalCriteria;
import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.insurance.report.life.persistence.interfaces.IGroupLifeProposalReportDAO;
import org.ace.insurance.report.life.service.interfaces.IGroupLifeProposalReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "GroupLifeProposalReportService")
public class GroupLifeProposalReportService implements IGroupLifeProposalReportService {

	@Resource(name = "GroupLifeProposalReportDAO")
	private IGroupLifeProposalReportDAO grouplifeProposalReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<GroupLifeProposalReport> findLifeProposal(GroupLifeProposalCriteria grouplifeProposalCriteria) {
		List<GroupLifeProposalReport> result = null;
		try {
			result = grouplifeProposalReportDAO.find(grouplifeProposalCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find GroupLifeProposalReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateGroupLifeProposalReport(List<GroupLifeProposalReport> grouplifeProposalReports, String dirPath, String fileName, GroupLifeProposalCriteria criteria,
			List<Branch> branchList) {
		try {
			List jasperPrintList = new ArrayList();
			double grandTotal = 0.0;
			double grandTotalPremium = 0.0;
			String branch = grouplifeProposalReports.get(0).getBranch();
			for (GroupLifeProposalReport report : grouplifeProposalReports) {
				grandTotal = grandTotal + report.getSumInsured();
				grandTotalPremium = grandTotalPremium + report.getBasicPremium();
			}

			Map paramMap = new HashMap();
			paramMap.put("grandTotal", grandTotal);
			paramMap.put("grandTotalPremium", grandTotalPremium);
			paramMap.put("lastIndex", true);
			paramMap.put("branch", branch);
			paramMap.put("LifeProposals", new JRBeanCollectionDataSource(grouplifeProposalReports));

			InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/groupLifeProposalReport.jrxml");
			JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
			JasperPrint policyJP = JasperFillManager.fillReport(policyJR, paramMap, new JREmptyDataSource());
			jasperPrintList.add(policyJP);

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			FileHandler.forceMakeDirectory(dirPath);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
			exporter.exportReport();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate Group LifeProposal Report", e);
		}

	}

}
