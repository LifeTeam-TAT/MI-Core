package org.ace.insurance.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;

import org.ace.insurance.common.Utils;
import org.ace.insurance.eizip.ZipFileName;
import org.ace.insurance.eizip.service.interfaces.IExportImportZipService;

import org.ace.insurance.payment.TLF;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "ExportImportZipFileActionBean")
public class ExportImportZipFileActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ExportImportZipService}")
	private IExportImportZipService exportImportZipService;

	public void setexportImportZipService(IExportImportZipService exportImportZipService) {
		this.exportImportZipService = exportImportZipService;
	}

	private final String tempFilePath = getSystemPath() + "/temp/zipfolder/";
	private boolean export;
	private String tableName;
	private List<String> tableNameList;
	private List<TLF> tlfList;

	private List<Object> objectList;
	private Date fromDate;
	private Date toDate;
	private UploadedFile file;

	@PostConstruct
	public void init() {
		export = true;
		tableName = ZipFileName.TLF;
		tableNameList = new ArrayList<String>();
		tableNameList.add(ZipFileName.TLF);
		tableNameList.add(ZipFileName.SYSTEM);
		tableNameList.add(ZipFileName.TRAVEL);
		tableNameList.add(ZipFileName.MOTOR);
		tableNameList.add(ZipFileName.FIRE);
		tableNameList.add(ZipFileName.PAYMENT);
		tableNameList.add(ZipFileName.PRODUCT);
		tableNameList.add(ZipFileName.USERAUTHORITY);
		resetCriteria();
	}

	public void resetCriteria() {
		if (getFromDate() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			setFromDate(cal.getTime());
		}
		if (getToDate() == null) {
			Date endDate = new Date();
			setToDate(endDate);
		}
	}

	public void changeOrgEvent(AjaxBehaviorEvent event) {
		if (isExport()) {
			export = true;
		} else {
			export = false;
		}
	}

	public void changeSettingEvent(AjaxBehaviorEvent event) {
		// for render date setting
	}

	public void exportJsonFile() {
		FileWriter fw = null;
		FileInputStream fis = null;
		String zipFilePath = null;
		String fileName = null;
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/octet-stream");
		if (ZipFileName.TLF.equals(tableName) && fromDate != null && toDate != null) {
			fileName = tableName + "(" + Utils.getDateFormatString(fromDate) + " - " + Utils.getDateFormatString(toDate) + ").zip";
		} else {
			fileName = tableName + ".zip";
		}
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			zipFilePath = exportImportZipService.getGenerateZip(fromDate, toDate, tableName, tempFilePath);
			fis = new FileInputStream(zipFilePath);
			IOUtils.copy(fis, op);
			fis.close();
			ec.responseFlushBuffer();
			getFacesContext().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void importJsonFile(FileUploadEvent event) {
		String zipfileName = null;
		String extractedFolderName = null;
		file = event.getFile();
		if (file != null) {
			zipfileName = file.getFileName();
			StringTokenizer st = new StringTokenizer(zipfileName, "(.");
			if (st.hasMoreElements()) {
				extractedFolderName = st.nextToken();
			}
			try {
				FileUtils.copyInputStreamToFile(file.getInputstream(), new File(tempFilePath + zipfileName));
				Utils.unzip(tempFilePath + zipfileName, tempFilePath + extractedFolderName);
				exportImportZipService.insertByTableName(tempFilePath, extractedFolderName);
				getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Complete Upload.", "Successfully upload  " + zipfileName));
			} catch (SystemException e) {
				handelSysException(e);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<TLF> getTlfList() {
		return tlfList;
	}

	public void setTlfList(List<TLF> tlfList) {
		this.tlfList = tlfList;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getSystemPath() {
		Object context = getFacesContext().getExternalContext().getContext();
		String systemPath = ((ServletContext) context).getRealPath("/");
		return systemPath;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getTableNameList() {
		return tableNameList;
	}

	public void setTableNameList(List<String> tableNameList) {
		this.tableNameList = tableNameList;
	}

	public List<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

}
