package org.ace.java.web;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.insurance.report.common.service.interfaces.ISalesReportService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@SessionScoped
@ManagedBean(name = "JasperReportActionBean")
public class JasperReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{SalesReportService}")
	private ISalesReportService salesReportService;

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}

	public String getReportStream() {
		String filePath = Constants.REPORT_DIR + "/SaleReport.pdf";
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/saleReportTemplate.jrxml");
			List<SalesReport> saleReports = salesReportService.findSalesReport(new SalesReportCriteria());
			Map paramMap = new HashMap();
			paramMap.put("ReportTitle", "Sale Report");
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(saleReports));
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(saleReports));
			String outputFilePdf = getWebRootPath() + filePath;
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePdf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
