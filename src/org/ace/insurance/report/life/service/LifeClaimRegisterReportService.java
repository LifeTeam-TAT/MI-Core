package org.ace.insurance.report.life.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifeClaimRegisterReportDAO;
import org.ace.insurance.report.life.service.interfaces.ILifeClaimRegisterReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
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

@Service(value = "LifeClaimRegisterReportService")
public class LifeClaimRegisterReportService implements ILifeClaimRegisterReportService {

	@Resource(name = "LifeClaimRegisterReportDAO")
	private ILifeClaimRegisterReportDAO lifeClaimRegisterReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimRegisterReport> findLifeClaimRegisterReports(EnquiryCriteria criteria, List<String> productIdList) {
		List<LifeClaimRegisterReport> result = null;
		try {
			result = lifeClaimRegisterReportDAO.find(criteria, productIdList);
		} catch (DAOException de) {
			throw new SystemException(de.getErrorCode(), "Faield to find LifeClaimRegisterReports by criteria.", de);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateReport(List<LifeClaimRegisterReport> reports, String fullPath, String fileName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TableDataSource", new JRBeanCollectionDataSource(reports));
		String fullTemplateFilePath = "report-template/life/lifeClaimRegisterReport.jrxml";
		new JRGenerateUtility().generateReport(fullTemplateFilePath, (fullPath + fileName), params);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateLifeClaimRegisterReport(String fullTemplateFilePath, EnquiryCriteria criteria, List<String> productIdList, List<Branch> branchList, String dirPath,
			String fileName) {
		try {
			List jasperPrintList = new ArrayList();
			Map<String, Object> params = new HashMap<String, Object>();
			double grandTotal = 0.0;
			if (criteria.getBranch() == null) {
				for (Branch branch : branchList) {
					EnquiryCriteria enquiryCriteria = new EnquiryCriteria();
					enquiryCriteria.setStartDate(criteria.getStartDate());
					enquiryCriteria.setEndDate(criteria.getEndDate());
					enquiryCriteria.setBranch(branch);
					enquiryCriteria.setAgent(criteria.getAgent());
					enquiryCriteria.setCustomer(criteria.getCustomer());
					enquiryCriteria.setPolicyNo(criteria.getPolicyNo());
					enquiryCriteria.setOrganization(criteria.getOrganization());
					enquiryCriteria.setProduct(criteria.getProduct());

					List<LifeClaimRegisterReport> reports = findLifeClaimRegisterReports(enquiryCriteria, productIdList);
					List<LifeClaimRegisterReport> temp = new ArrayList<LifeClaimRegisterReport>();

					double subTotal = 0.0;
					if (reports != null && !reports.isEmpty()) {
						for (LifeClaimRegisterReport report : reports) {
							subTotal = subTotal + report.getTotalSumInsured();
						}
						grandTotal = grandTotal + subTotal;
						for (LifeClaimRegisterReport report : reports) {
							report.setSubTotal(subTotal);
							temp.add(report);
						}
						params.put("TableDataSource", new JRBeanCollectionDataSource(temp));
						params.put("grandTotal", grandTotal);
						params.put("branch", branch.getName());

						if (branchList.lastIndexOf(branch) == branchList.size() - 1) {
							params.put("lastIndex", true);
						}

						InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
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
			} else {
				List<LifeClaimRegisterReport> reports = findLifeClaimRegisterReports(criteria, productIdList);
				List<LifeClaimRegisterReport> temp = new ArrayList<LifeClaimRegisterReport>();

				double subTotal = 0.0;
				if (reports != null && !reports.isEmpty()) {
					for (LifeClaimRegisterReport report : reports) {
						subTotal = subTotal + report.getTotalSumInsured();
					}
					grandTotal = grandTotal + subTotal;
					for (LifeClaimRegisterReport report : reports) {
						report.setSubTotal(subTotal);
						temp.add(report);
					}
					params.put("TableDataSource", new JRBeanCollectionDataSource(temp));
					params.put("grandTotal", grandTotal);
					params.put("lastIndex", false);
					params.put("branch", criteria.getBranch().getName());

					InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
