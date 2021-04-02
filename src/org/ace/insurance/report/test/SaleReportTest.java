package org.ace.insurance.report.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.insurance.report.common.service.interfaces.ISalesReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class SaleReportTest {
	private static Logger logger = LogManager.getLogger(SaleReportTest.class);
    private static ISalesReportService salesReportService;

	@BeforeClass
    public static void init() {
        logger.info("SaleReportTest is started.........................................");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
        BeanFactory factory = context;
        salesReportService = (ISalesReportService)factory.getBean("SalesReportService");
        logger.info("SaleReportTest instance has been loaded.");
                
    }
	
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(SaleReportTest.class.getName());
    }

    @Test
    public void report() throws Exception {
    	SalesReportCriteria criteria = new SalesReportCriteria();
		criteria.setStartDate(Utils.getDate("01-06-2013"));
		criteria.setEndDate(Utils.getDate("15-06-2013"));
    	List<SalesReport> salesReportList = salesReportService.findSalesReport(criteria);
		Map paramMap = new HashMap();
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(salesReportList));
		paramMap.put("totalPremium", getTotalPremium(salesReportList));
		paramMap.put("totalSumInsured", getTotalAmount(salesReportList));
		paramMap.put("totalCommission", getTotalCommission(salesReportList));
		
		InputStream inputStream = new FileInputStream("report-template/saleReportTemplate.jrxml");
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(salesReportList));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/selectReport.pdf");
    }
    
    @Test
    public void prepareReport() throws Exception {
    	List<SalesReport> salesReportList = new ArrayList<SalesReport>();
    	SalesReport salesReport = new SalesReport();
    	salesReport.setPolicyNo("policyNo"); 
		salesReport.setPolicyHolder("policyHolder");
		salesReport.setDateOfInsured(new Date());
		salesReport.setPeriod("period");
		salesReport.setInsuredAmount(1000.0);  
		salesReport.setPremium(100.0); 
		salesReport.setCommission(1000.0);
		salesReport.setProductType("productType");
		salesReportList.add(salesReport);
    	
		Map paramMap = new HashMap();
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(salesReportList));
		paramMap.put("totalPremium", getTotalPremium(salesReportList));
		paramMap.put("totalSumInsured", getTotalAmount(salesReportList));
		paramMap.put("totalCommission", getTotalCommission(salesReportList));
		
		InputStream inputStream = new FileInputStream("report-template/saleReportTemplate.jrxml");
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(salesReportList));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/selectReport.pdf");
    }

	private double getTotalPremium(List<SalesReport> salesReportList) {
		double totalPremium = 0.0;
		for(SalesReport s : salesReportList) {
			totalPremium += s.getPremium();
		}
		
		return totalPremium;
	}
	
	private double getTotalAmount(List<SalesReport> salesReportList) {
		double totalAmount = 0.0;
		for(SalesReport s : salesReportList) {
			totalAmount += s.getInsuredAmount();
		}
		
		return totalAmount;
	}
	
	private double getTotalCommission(List<SalesReport> salesReportList) {
		double totalCommission = 0.0;
		for(SalesReport s : salesReportList) {
			totalCommission += s.getCommission();
		}
		
		return totalCommission;
	}
}
