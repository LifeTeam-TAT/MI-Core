package org.ace.insurance.report.life.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.TLF.MonthlyIncomeReportCriteria;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.farmer.FarmerMonthlyReport;
import org.ace.insurance.report.life.LifePolicyReport;
import org.ace.insurance.report.life.LifePolicyReportCriteria;
import org.ace.insurance.report.life.persistence.interfaces.ILifePolicyReportDAO;
import org.ace.insurance.report.life.service.interfaces.ILifePolicyReportService;
import org.ace.insurance.report.personalAccident.PersonalAccidentPolicyReport;
import org.ace.insurance.report.shortEndowLife.ShortEndowLifePolicyReport;
import org.ace.insurance.report.sportMan.SportManMonthlyReportDTO;
import org.ace.insurance.web.manage.report.shortEndowLife.ShortEndownLifeMonthlyReportDTO;
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

@Service(value = "LifePolicyReportService")
public class LifePolicyReportService implements ILifePolicyReportService {
	@Resource(name = "LifePolicyReportDAO")
	private ILifePolicyReportDAO lifePolicyReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyReport> findLifePolicyReport(LifePolicyReportCriteria lifePolicyCriteria, List<String> productIdList) {
		List<LifePolicyReport> result = null;
		try {
			result = lifePolicyReportDAO.findLifePolicyReport(lifePolicyCriteria, productIdList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LifePolicyReport by criteria.", e);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PersonalAccidentPolicyReport> findPersonalAccidentPolicyReport(LifePolicyReportCriteria criteria) {
		List<PersonalAccidentPolicyReport> personalAccidentPolicyList = null;
		try {
			personalAccidentPolicyList = lifePolicyReportDAO.findPersonalAccidentPolicyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find PersonalAccidentPolicyReport by criteria.", e);
		}
		return personalAccidentPolicyList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateLifePolicyReport(List<LifePolicyReport> reports, String dirPath, String fileName, String branch) {
		try {
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			Map<String, Object> params = new HashMap<String, Object>();
			double grandTotalSI = 0.0;
			double grandTotalPremium = 0.0;
			if (reports != null && !reports.isEmpty()) {
				double subTotalSI = 0.0;
				double subTotalPremium = 0.0;
				for (LifePolicyReport report : reports) {

					grandTotalSI += report.getSumInsured();
					grandTotalPremium += report.getPremium();
					report.setSubTotalPremium(subTotalPremium);
					report.setSubTotalSI(subTotalSI);
					if (reports.lastIndexOf(report) == reports.size() - 1) {
						params.put("lastIndex", true);
					} else {
						params.put("lastIndex", false);
					}
				}
				params.put("grandTotalSI", grandTotalSI);
				params.put("grandTotalPremium", grandTotalPremium);
				params.put("branch", branch);
				params.put("TableDataSource", new JRBeanCollectionDataSource(reports));
				InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/lifePolicyReport.jrxml");
				JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
				JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
				jasperPrintList.add(policyJP);
				JRExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
				FileHandler.forceMakeDirectory(dirPath);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
				exporter.exportReport();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generatePersonalAccidentPolicyReport(List<PersonalAccidentPolicyReport> reports, String dirPath, String fileName) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (reports != null && !reports.isEmpty()) {
				/*
				 * for (PersonalAccidentPolicyReport report : reports) { if
				 * (reports.lastIndexOf(report) == reports.size() - 1) {
				 * params.put("lastIndex", true); } else {
				 * params.put("lastIndex", false); } }
				 */
				params.put("dataList", reports);
				InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/personalAccident/personalAccidentPolicyReport.jrxml");
				JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
				JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
				JRExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, policyJP);
				FileHandler.forceMakeDirectory(dirPath);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
				exporter.exportReport();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<FarmerMonthlyReport> findFarmerMonthlyReport(MonthlyReportCriteria criteria) {
		List<FarmerMonthlyReport> resultList = null;
		try {
			resultList = lifePolicyReportDAO.findFarmerMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find farmer monthly report.", e);
		}
		return resultList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndowLifePolicyReport> findShortEndowLifePolicyReport(LifePolicyReportCriteria lifePolicyCriteria) {
		List<ShortEndowLifePolicyReport> result = null;
		try {
			result = lifePolicyReportDAO.findShortEndowLifePolicyReport(lifePolicyCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ShortEndowLifePolicyReport by criteria.", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndownLifeMonthlyReportDTO> findShortEndowLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria) {
		List<ShortEndownLifeMonthlyReportDTO> result = null;
		try {
			result = lifePolicyReportDAO.findShortEndowMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ShortEndowLifePolicyMonthlyReport by criteria.", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ShortEndownLifeMonthlyReportDTO> findPublicLifePolicyMonthlyReport(MonthlyIncomeReportCriteria criteria) {
		List<ShortEndownLifeMonthlyReportDTO> result = null;
		try {
			result = lifePolicyReportDAO.findPublicLifeMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find PublicLifePolicyMonthlyReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SportManMonthlyReportDTO> findSportManMonthlyReport(MonthlyIncomeReportCriteria criteria) {
		List<SportManMonthlyReportDTO> result = null;
		try {
			result = lifePolicyReportDAO.findSportManMonthlyReport(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find SportManMonthlyReport by criteria.", e);
		}
		return result;
	}

}
