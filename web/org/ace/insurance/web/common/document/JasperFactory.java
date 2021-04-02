package org.ace.insurance.web.common.document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperFactory {
	public static JasperPrint generateJasperPrint(Map<String, Object> paramMap, String jasperTemplateDirPath, JRDataSource dataSource) {
		JasperPrint jprint = null;
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperTemplateDirPath);
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			jprint = JasperFillManager.fillReport(jreport, paramMap, dataSource);
		} catch (JRException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate JasperPrint", e);
		}

		return jprint;
	}

	public static JasperPrint generateJasperPrintTest(Map<String, Object> paramMap, String jasperTemplateDirPath, JRDataSource dataSource) {
		JasperPrint jprint = null;
		try {
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperTemplateDirPath);
			JasperReport jreport = (JasperReport) JRLoader.loadObject(inputStream);
			jprint = JasperFillManager.fillReport(jreport, paramMap, dataSource);
		} catch (JRException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate JasperPrint", e);
		}

		return jprint;
	}

	public static void exportReportToPdfFile(List<JasperPrint> jasperPrints, String dirPath, String fileName) {
		try {
			for (JasperPrint jprint : jasperPrints)
				Utils.removeBlankPages(jprint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
			FileHandler.forceMakeDirectory(dirPath);
			OutputStream outputStream = new FileOutputStream(dirPath + fileName);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			outputStream.close();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export PDF file", e);
		} catch (JRException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export PDF file", e);
		}
	}

}
