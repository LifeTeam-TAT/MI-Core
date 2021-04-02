package org.ace.insurance.web.manage.report.life;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.report.life.LifePremiumPaymentCriteria;
import org.ace.insurance.report.life.LifePremiumPaymentReport;
import org.ace.insurance.report.life.service.interfaces.ILifePremiumPaymentReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean(name = "LifePremiumPaymentReportActionBean")
public class LifePremiumPaymentReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePremiumPaymentReportService}")
	private ILifePremiumPaymentReportService premiumPaymentReportService;
	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setPremiumPaymentReportService(ILifePremiumPaymentReportService premiumPaymentReportService) {
		this.premiumPaymentReportService = premiumPaymentReportService;
	}

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private LifePremiumPaymentCriteria criteria;
	private List<LifePremiumPaymentReport> premiumPaymentList;
	private LazyDataModelUtil<LifePremiumPaymentReport> lazyModel;
	private boolean accessBranches;
	private User user;
	private String branchName;
	private List<String> productIdList;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		productIdList = new ArrayList<String>();
		productIdList.add(KeyFactorIDConfig.getPublicLifeId());
		productIdList.add(KeyFactorIDConfig.getGroupLifeId());
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new LifePremiumPaymentCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		filter();
	}

	public ProposalType[] getProposalTypeSelectItemList() {
		return ProposalType.values();
	}

	public LifePremiumPaymentCriteria getCriteria() {
		return criteria;
	}

	public LazyDataModel<LifePremiumPaymentReport> getLazyModel() {
		return lazyModel;
	}

	public void filter() {
		boolean valid = true;
		String formID = "premiumList";
		if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
			if (criteria.getStartDate().after(criteria.getEndDate())) {
				addErrorMessage(formID + ":startDate", MessageId.STARTDATE_MUSTBE_LESSTHAN_ENDDATE);
				valid = false;
			}
		}
		if (valid) {
			premiumPaymentList = premiumPaymentReportService.findPremiumPayment(criteria, productIdList);
			lazyModel = new LazyDataModelUtil<LifePremiumPaymentReport>(premiumPaymentList);
		}
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	private final String reportName = "LifePolicyReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	public String getStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		try {
			if (criteria.getBranch() == null) {
				branchName = "All";
			} else {
				branchName = criteria.getBranch().getName();
			}
			premiumPaymentReportService.generateLifePremiumPaymentReport(premiumPaymentList, dirPath, fileName, branchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public Map<String, List<LifePremiumPaymentReport>> separateBranch() {
		Map<String, List<LifePremiumPaymentReport>> map = new LinkedHashMap<String, List<LifePremiumPaymentReport>>();
		for (LifePremiumPaymentReport report : premiumPaymentList) {
			if (map.containsKey(report.getBranch())) {
				map.get(report.getBranch()).add(report);
			} else {
				List<LifePremiumPaymentReport> list = new ArrayList<LifePremiumPaymentReport>();
				list.add(report);
				map.put(report.getBranch(), list);
			}
		}
		return map;
	}

	// Exporting
	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "LifePremiumPaymentReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel();
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifePremiumPaymentReport.xlsx", e);
		}
	}

	private class ExportExcel {
		private XSSFWorkbook wb;

		public ExportExcel() {
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/life/LifePremiumPaymentReport.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifePremiumPaymentReport.xlsx template", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("LifePremiumPaymentReport");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell cell;

				ExcelUtils.fillCompanyLogo(wb, sheet, 5);
				row = sheet.getRow(0);
				cell = row.getCell(0);
				if (criteria.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n Life Premium Payment Report ( All )");
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n Life Premium Payment Report ( " + criteria.getBranch().getName() + " )");
				}

				int i = 2;
				int index = 0;
				int startIndex;
				String strFormula;
				String GrandSIFormula = "";
				String GrandPremiumFormula = "";
				String GrandIncomeFormula = "";
				Map<String, List<LifePremiumPaymentReport>> map = separateBranch();
				for (List<LifePremiumPaymentReport> branchList : map.values()) {
					startIndex = i + 1 + 1;
					for (LifePremiumPaymentReport LifePremiumPaymentReport : branchList) {
						index = index + 1;
						row = sheet.createRow(i);
						/*** index ***/
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);
						/*** proposalNo ***/
						cell = row.createCell(1);
						cell.setCellValue(LifePremiumPaymentReport.getPolicyNo());
						cell.setCellStyle(textCellStyle);
						/*** cell ***/
						cell = row.createCell(2);
						cell.setCellValue(LifePremiumPaymentReport.getCashReceiptNo());
						cell.setCellStyle(textCellStyle);
						/*** customer name ***/
						cell = row.createCell(3);
						cell.setCellValue(LifePremiumPaymentReport.getCustomerName());
						cell.setCellStyle(textCellStyle);
						/*** SumInsured ***/
						cell = row.createCell(4);
						cell.setCellValue(LifePremiumPaymentReport.getSumInsured());
						cell.setCellStyle(currencyCellStyle);
						/*** One Year Premium ***/
						cell = row.createCell(5);
						cell.setCellValue(LifePremiumPaymentReport.getOneYearPremium());
						cell.setCellStyle(currencyCellStyle);
						/*** cell ***/
						cell = row.createCell(6);
						cell.setCellValue(LifePremiumPaymentReport.getIncome());
						cell.setCellStyle(currencyCellStyle);
						/*** branch ***/
						cell = row.createCell(7);
						cell.setCellValue(LifePremiumPaymentReport.getBranch());
						cell.setCellStyle(textCellStyle);
						i = i + 1;
					}
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
					row = sheet.createRow(i);

					cell = row.createCell(0);
					cell.setCellValue("Sub Total");
					cell.setCellStyle(defaultCellStyle);
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 3), sheet, wb);

					cell = row.createCell(4);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(E" + startIndex + ":E" + i + ")";
					GrandSIFormula += "E" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					cell = row.createCell(5);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(F" + startIndex + ":F" + i + ")";
					GrandPremiumFormula += "F" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					cell = row.createCell(6);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(G" + startIndex + ":G" + i + ")";
					GrandIncomeFormula += "G" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					cell = row.createCell(6);
					cell.setCellStyle(defaultCellStyle);
					i = i + 1;
				}
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				cell.setCellStyle(defaultCellStyle);
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 3), sheet, wb);

				cell = row.createCell(4);
				cell.setCellStyle(currencyCellStyle);
				GrandSIFormula = GrandSIFormula.substring(0, GrandSIFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandSIFormula);

				cell = row.createCell(5);
				cell.setCellStyle(currencyCellStyle);
				GrandPremiumFormula = GrandPremiumFormula.substring(0, GrandPremiumFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandPremiumFormula);

				cell = row.createCell(6);
				cell.setCellStyle(currencyCellStyle);
				GrandIncomeFormula = GrandIncomeFormula.substring(0, GrandIncomeFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandIncomeFormula);

				cell = row.createCell(6);
				cell.setCellStyle(defaultCellStyle);

				wb.setPrintArea(0, 0, 7, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
