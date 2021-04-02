package org.ace.insurance.report.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ace.insurance.report.JRGenerateUtility;
import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.ISalesReportDAO;
import org.ace.insurance.report.common.service.interfaces.ISalesReportService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SalesReportService")
public class SalesReportService implements ISalesReportService {

	@Resource(name = "SalesReportDAO")
	private ISalesReportDAO salesReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalesReport> findSalesReport(SalesReportCriteria criteria) {
		List<SalesReport> result = null;
		try {
			result = salesReportDAO.find(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SalesReportReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateSaleReport(List<SalesReport> salesReportList, String fullReportFilePath) {
		Map paramMap = new HashMap();
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(salesReportList));
		paramMap.put("totalPremium", getTotalPremium(salesReportList));
		paramMap.put("totalSumInsured", getTotalAmount(salesReportList));
		paramMap.put("totalCommission", getTotalCommission(salesReportList));
		String fullTemplateFilePath = "report-template/saleReportTemplate.jrxml";
		new JRGenerateUtility().generateReport(fullTemplateFilePath, fullReportFilePath, paramMap);
	}

	private double getTotalPremium(List<SalesReport> salesReportList) {
		double totalPremium = 0.0;
		for (SalesReport s : salesReportList) {
			totalPremium += s.getPremium();
		}

		return totalPremium;
	}

	private double getTotalAmount(List<SalesReport> salesReportList) {
		double totalAmount = 0.0;
		for (SalesReport s : salesReportList) {
			totalAmount += s.getInsuredAmount();
		}

		return totalAmount;
	}

	private double getTotalCommission(List<SalesReport> salesReportList) {
		double totalCommission = 0.0;
		for (SalesReport s : salesReportList) {
			totalCommission += s.getCommission();
		}

		return totalCommission;
	}
}