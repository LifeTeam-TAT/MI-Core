package org.ace.insurance.web.manage.report.medical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.report.medical.MedicalPolicyMonthlyReportDTO;
import org.ace.insurance.report.medical.service.interfaces.IMedicalPolicyMonthlyReportService;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2015-11-17
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalPolicyMonthlyReportDTO</code> Medical Policy Monthly
 *          Report process
 * 
 ***************************************************************************************/

@ViewScoped
@ManagedBean(name = "MedicalPolicyMonthlyReportActionBean")
public class MedicalPolicyMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{MedicalPolicyMonthlyReportService}")
	private IMedicalPolicyMonthlyReportService medicalPolicyMonthlyReportService;

	public void setMedicalPolicyMonthlyReportService(IMedicalPolicyMonthlyReportService medicalPolicyMonthlyReportService) {
		this.medicalPolicyMonthlyReportService = medicalPolicyMonthlyReportService;
	}

	private boolean accessBranches;
	private MonthlyReportCriteria criteria;
	private List<MedicalPolicyMonthlyReportDTO> medicalPolicyMonthlyReportList;
	//private LazyDataModelUtil<MotorProposalReport> lazyModel;
	private User user;

	private final String reportName = "MedicalPolicy_Monthly_Report";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";
	private String branch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() {
		DateTime dateTime = new DateTime();
		criteria = new MonthlyReportCriteria();
		criteria.setMonth(0);
		criteria.setYear(dateTime.getYear());
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }
		medicalPolicyMonthlyReportList = new ArrayList<MedicalPolicyMonthlyReportDTO>();
		//lazyModel = new LazyDataModelUtil(medicalPolicyMonthlyReportList);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void filter() {
		try {
			medicalPolicyMonthlyReportList = medicalPolicyMonthlyReportService.findMedicalPolicyMonthlyReport(criteria);
			//lazyModel = new LazyDataModelUtil(medicalPolicyMonthlyReportList);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void generateReport() {
		try {
			if (criteria.getBranch() == null)
				branch = "All";
			else
				branch = criteria.getBranch().getName();
			FileHandler.forceMakeDirectory(dirPath);
			medicalPolicyMonthlyReportService.generateMedicalPolicyMonthlyReport(medicalPolicyMonthlyReportList, dirPath, fileName, branch);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStream() {
		String fileFullName = pdfDirPath + fileName;
		return fileFullName;
	}

	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1999; startYear <= endYear; startYear++) {
			years.add(startYear);
		}
		Collections.reverse(years);
		return years;
	}

	public MonthlyReportCriteria getCriteria() {
		return criteria;
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public void setAccessBranches(boolean accessBranches) {
		this.accessBranches = accessBranches;
	}

	public List<MedicalPolicyMonthlyReportDTO> getMedicalPolicyMonthlyReportList() {
		return medicalPolicyMonthlyReportList;
	}

	public void setMedicalPolicyMonthlyReportList(List<MedicalPolicyMonthlyReportDTO> medicalPolicyMonthlyReportList) {
		this.medicalPolicyMonthlyReportList = medicalPolicyMonthlyReportList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public LazyDataModel<MotorProposalReport> getLazyModel() {
//		return lazyModel;
//	}

	public int getTotalUnit() {
		int cell = 0;
		for (MedicalPolicyMonthlyReportDTO m : medicalPolicyMonthlyReportList) {
			cell += m.getUnit();
		}
		return cell;
	}

	public double getTotalPremium() {
		double cell = 0.0;
		for (MedicalPolicyMonthlyReportDTO m : medicalPolicyMonthlyReportList) {
			cell += m.getPremium();
		}
		return cell;
	}

	public double getTotalCommission() {
		double cell = 0.0;
		for (MedicalPolicyMonthlyReportDTO m : medicalPolicyMonthlyReportList) {
			cell += m.getCommission();
		}
		return cell;
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "Medical_Policy_Monthly_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), medicalPolicyMonthlyReportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export MedicalPolicy_Monthly_Report.xlsx", e);
		}
	}

	@SuppressWarnings("unused")
	private class ExportExcel {
		private int year;
		private String month;
		private List<MedicalPolicyMonthlyReportDTO> medicalPolicyMonthlyReportList;
		private XSSFWorkbook wb;

		public ExportExcel(int year, String month, List<MedicalPolicyMonthlyReportDTO> medicalPolicyMonthlyReportList) {
			this.year = year;
			this.month = month;
			this.medicalPolicyMonthlyReportList = medicalPolicyMonthlyReportList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/medical/MedicalPolicyMonthlyReport.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load MedicalPolicyMonthlyReport.xlsx tempalte", e);
			}
		}

		private XSSFCellStyle getTitleCell() {
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cellStyle.setWrapText(true);
			Font font = wb.createFont();
			font.setFontName("Myanmar3");
			font.setFontHeightInPoints((short) 11);
			cellStyle.setFont(font);
			return cellStyle;
		}

		public void generate(OutputStream op) {
			// try {
			// separateBranch();
			//
			// XSSFCellStyle defaultCellStyle =
			// ExcelUtils.getDefaultCellStyle(wb);
			// XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
			// XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
			// XSSFCellStyle currencyCellStyle =
			// ExcelUtils.getCurrencyCellStyle(wb);
			//
			// Row row;
			// Cell cell;
			//
			// XSSFDataFormat cellFormat = wb.createDataFormat();
			// Sheet sheet = wb.getSheet("MedicalPolicyMonthlyReport");
			//
			// String companyLabel = ApplicationSetting.getCompanyLabel();
			// String year = criteria.getYear() + "";
			// String month = Utils.getMonthString(criteria.getMonth());
			//
			// row = sheet.getRow(0);
			// cell = row.createCell(0);
			// cell.setCellValue(companyLabel);
			// cell.setCellStyle(getTitleCell());
			//
			// row = sheet.getRow(2);
			// cell = row.createCell(0);
			// cell.setCellValue(year);
			// cell.setCellStyle(getTitleCell());
			//
			// cell = row.createCell(2);
			// cell.setCellValue(month);
			// cell.setCellStyle(getTitleCell());
			//
			// cell = row.createCell(12);
			// cell.setCellValue(new Date());
			// cell.setCellStyle(getTitleCell());
			//
			// int i = 6;
			// int index = 0;
			// int startIndex;
			// String strFormula;
			// String GrandUnitFormula = "";
			// String GrandPremiumFormula = "";
			// String GrandCommissionFormula = "";
			// Map<String, List<MedicalPolicyMonthlyReportDTO>> map =
			// separateBranch();
			// for (List<MedicalPolicyMonthlyReportDTO> branchList :
			// map.values()) {
			// startIndex = i + 1 + 1;
			// for (MedicalPolicyMonthlyReportDTO report : branchList) {
			// i = i + 1;
			// index = index + 1;
			// row = sheet.createRow(i);
			// cell = row.createCell(0);
			// cell.setCellValue(index);
			// cell.setCellStyle(defaultCellStyle);
			//
			// cell = row.createCell(1);
			// cell.setCellValue(report.getPolicyStartDate());
			// cell.setCellStyle(dateCellStyle);
			//
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 2, 3));
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 2, 3), sheet, wb);
			// cell = row.createCell(2);
			// cell.setCellValue(report.getPolicyNo());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(4);
			// cell.setCellValue(report.getInsuredPersonName());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(5);
			// cell.setCellValue(Integer.valueOf(report.getUnit()));
			// cell.setCellStyle(defaultCellStyle);
			//
			// cell = row.createCell(6);
			// cell.setCellValue(report.getPremium());
			// cell.setCellStyle(currencyCellStyle);
			//
			// cell = row.createCell(7);
			// cell.setCellValue(report.getReceiptNo() + " (" +
			// Utils.getDateFormatString(report.getPaymentDate()) + ")");
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(8);
			// cell.setCellValue(report.getBeneficiaryName());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(9);
			// cell.setCellValue(report.getRelationship());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(10);
			// cell.setCellValue(report.getSalePersonName());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(11);
			// cell.setCellValue(report.getTypeOfSalePerson());
			// cell.setCellStyle(textCellStyle);
			//
			// cell = row.createCell(12);
			// cell.setCellValue(report.getCommission());
			// cell.setCellStyle(currencyCellStyle);
			// }
			// i = i + 1;
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
			// row = sheet.createRow(i);
			//
			// cell = row.createCell(0);
			// cell.setCellValue("Sub Total ");
			// cell.setCellStyle(defaultCellStyle);
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 0, 4), sheet, wb);
			//
			// cell = row.createCell(5);
			// cell.setCellStyle(defaultCellStyle);
			// strFormula = "SUM(F" + startIndex + ":F" + i + ")";
			// GrandUnitFormula += "F" + (i + 1) + "+";
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(strFormula);
			//
			// cell = row.createCell(6);
			// cell.setCellStyle(currencyCellStyle);
			// strFormula = "SUM(G" + startIndex + ":G" + i + ")";
			// GrandPremiumFormula += "G" + (i + 1) + "+";
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(strFormula);
			//
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 7, 11));
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 7, 11), sheet, wb);
			//
			// cell = row.createCell(12);
			// cell.setCellStyle(currencyCellStyle);
			// strFormula = "SUM(M" + startIndex + ":M" + i + ")";
			// GrandCommissionFormula += "M" + (i + 1) + "+";
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(strFormula);
			// }
			// i = i + 1;
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 4));
			// row = sheet.createRow(i);
			// cell = row.createCell(0);
			// cell.setCellValue("Grand Total ");
			// cell.setCellStyle(defaultCellStyle);
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 0, 4), sheet, wb);
			//
			// cell = row.createCell(5);
			// cell.setCellStyle(defaultCellStyle);
			// GrandUnitFormula = GrandUnitFormula.substring(0,
			// GrandUnitFormula.length() - 1);
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(GrandUnitFormula);
			//
			// cell = row.createCell(6);
			// cell.setCellStyle(currencyCellStyle);
			// GrandPremiumFormula = GrandPremiumFormula.substring(0,
			// GrandPremiumFormula.length() - 1);
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(GrandPremiumFormula);
			//
			// sheet.addMergedRegion(new CellRangeAddress(i, i, 7, 11));
			// ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new
			// CellRangeAddress(i, i, 7, 11), sheet, wb);
			//
			// cell = row.createCell(12);
			// cell.setCellStyle(currencyCellStyle);
			// GrandCommissionFormula = GrandCommissionFormula.substring(0,
			// GrandCommissionFormula.length() - 1);
			// cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			// cell.setCellFormula(GrandCommissionFormula);
			// wb.setPrintArea(0, 0, 12, 0, i);
			// wb.write(op);
			// op.flush();
			// op.close();
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
		}// catch (IOException e) {
			// e.printStackTrace();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
	}

	public Map<String, List<MedicalPolicyMonthlyReportDTO>> separateBranch() {
		Map<String, List<MedicalPolicyMonthlyReportDTO>> map = new LinkedHashMap<String, List<MedicalPolicyMonthlyReportDTO>>();
		for (MedicalPolicyMonthlyReportDTO report : medicalPolicyMonthlyReportList) {
			if (map.containsKey(report.getBranchId())) {
				map.get(report.getBranchId()).add(report);
			} else {
				List<MedicalPolicyMonthlyReportDTO> list = new ArrayList<MedicalPolicyMonthlyReportDTO>();
				list.add(report);
				map.put(report.getBranchId(), list);
			}
		}
		return map;
	}

}
