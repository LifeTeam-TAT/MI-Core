package org.ace.insurance.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.ace.java.component.SystemException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class JRGenerateUtility {
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void generateReportWithDS(String fullTemplateFilePath, String fullReportFilePath, Map<String, Object> params) {
		InputStream input = null;
		JRBeanCollectionDataSource ds = null;
		for (Object paramVal : params.values()) {
			if (paramVal instanceof JRBeanCollectionDataSource) {
				ds = (JRBeanCollectionDataSource)paramVal;
			}
		}
		if (ds == null) {
			String message = "No data to generate report for file [" + fullReportFilePath + "]";
			throw new SystemException(message, message);
		}
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
			JasperDesign design = JRXmlLoader.load(input);
			
			JasperReport report = JasperCompileManager.compileReport(design);
			
			JasperPrint print = JasperFillManager.fillReport(report, params, ds);
			JasperExportManager.exportReportToPdfFile(print, fullReportFilePath);
			
		} catch (JRException je) {
			String message = "Failed to generate report file [" + fullReportFilePath + "]";
			throw new SystemException(je.getMessage(), message, je);
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ie) {
				String message = "Failed to close template file [" + fullTemplateFilePath + "]";
				throw new SystemException(ie.getMessage(), message, ie);
			}
		}		
	}
	
	public void generateReport(String fullTemplateFilePath, String fullReportFilePath, Map<String, Object> params) {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fullTemplateFilePath);
			// loading the JasperTemplate file
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

			// Compiling the JasperReport instance with the template
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			// Populating the Report's data
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
			
			// Making the output Report instances
			JasperExportManager.exportReportToPdfFile(jasperPrint, fullReportFilePath);
		} catch (JRException je) {
			String message = "Failed to generate report file [" + fullReportFilePath + "]";
			throw new SystemException(je.getMessage(), message, je);
		} finally {
			try {
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ie) {
				String message = "Failed to close template file [" + fullTemplateFilePath + "]";
				throw new SystemException(ie.getMessage(), message, ie);
			}
		}		
	}
}
