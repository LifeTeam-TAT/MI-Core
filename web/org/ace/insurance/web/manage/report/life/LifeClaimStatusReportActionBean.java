package org.ace.insurance.web.manage.report.life;

import java.io.FileNotFoundException;
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
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.report.life.LifeClaimStatusReport;
import org.ace.insurance.report.life.LifeClaimStatusReportCriteria;
import org.ace.insurance.report.life.service.interfaces.ILifeClaimStatusReportService;
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

@ViewScoped
@ManagedBean(name = "LifeClaimStatusReportActionBean")
public class LifeClaimStatusReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{LifeClaimStatusReportService}")
	private ILifeClaimStatusReportService lifeClaimStatusReportService;

	public void setLifeClaimStatusReportService(ILifeClaimStatusReportService lifeClaimStatusReportService) {
		this.lifeClaimStatusReportService = lifeClaimStatusReportService;
	}

	private LifeClaimStatusReportCriteria criteria;
	private List<LifeClaimStatusReport> lifeClaimStatusList;
	private boolean accessBranches;
	private User user;
	private LazyDataModelUtil<LifeClaimStatusReport> lazyModel;

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// }
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new LifeClaimStatusReportCriteria();
		if (!accessBranches) {
			criteria.setBranch(user.getBranch());
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
	}

	public void filter() {
		boolean valid = true;
		String formID = "statusList";
		if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
			if (criteria.getStartDate().after(criteria.getEndDate())) {
				addErrorMessage(formID + ":startDate", MessageId.STARTDATE_MUSTBE_LESSTHAN_ENDDATE);
				valid = false;
			}
		}
		if (valid) {
			lifeClaimStatusList = lifeClaimStatusReportService.findLifeClaimStatusReport(criteria);
			lazyModel = new LazyDataModelUtil<LifeClaimStatusReport>(lifeClaimStatusList);
		}
	}

	public List<Branch> getBranchList() {
		return branchService.findAllBranch();
	}

	public PolicyStatus[] getPolicyStatusList() {
		return PolicyStatus.values();
	}

	public LifeClaimStatusReportCriteria getCriteria() {
		return criteria;
	}

	public List<LifeClaimStatusReport> getLifeClaimStatusList() {
		return lifeClaimStatusList;
	}

	public boolean isAccessBranches() {
		return accessBranches;
	}

	public User getUser() {
		return user;
	}

	public LazyDataModelUtil<LifeClaimStatusReport> getLazyModel() {
		return lazyModel;
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "LifeClaimStatusReport.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel();
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifeClaimStatusReport.xlsx", e);
		}

	}

	private class ExportExcel {
		private XSSFWorkbook wb;

		public ExportExcel() {
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/life/paidUp/LifeClaimStatusReport.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load LifeClaimStatusReport.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("lifeClaimStatusReport");

				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle dateCellStyle = ExcelUtils.getDateCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell cell;

				ExcelUtils.fillCompanyLogo(wb, sheet, 7);
				row = sheet.getRow(0);
				cell = row.getCell(0);
				if (criteria.getBranch() == null) {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n Life Claim Status Report ( All )");
				} else {
					cell.setCellValue(ApplicationSetting.getCompanyLabel() + " \n Life Claim Status Report ( " + criteria.getBranch().getName() + " )");
				}

				int i = 2;
				int index = 0;
				int startIndex;
				String strFormula;
				String GrandSIFormula = "";
				String GrandClaimAmountFormula = "";
				Map<String, List<LifeClaimStatusReport>> map = separateBranch();
				for (List<LifeClaimStatusReport> branchList : map.values()) {
					startIndex = i + 1 + 1;
					for (LifeClaimStatusReport report : branchList) {
						i = i + 1;
						index = index + 1;

						row = sheet.createRow(i);
						cell = row.createCell(0);
						cell.setCellValue(index);
						cell.setCellStyle(defaultCellStyle);

						cell = row.createCell(1);
						cell.setCellValue(report.getPolicyNo());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(2);
						cell.setCellValue(report.getCustomerName());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(3);
						cell.setCellValue(report.getAgentName());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(4);
						cell.setCellValue(report.getSubmittedDate());
						cell.setCellStyle(dateCellStyle);

						cell = row.createCell(5);
						cell.setCellValue(report.getPolicyPeriod());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(6);
						cell.setCellValue(report.getPaymentType());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(7);
						cell.setCellValue(report.getPolicyStatus().getLabel());
						cell.setCellStyle(textCellStyle);

						cell = row.createCell(8);
						cell.setCellValue(report.getSumInsured());
						cell.setCellStyle(currencyCellStyle);

						cell = row.createCell(9);
						cell.setCellValue(report.getAmount());
						cell.setCellStyle(currencyCellStyle);
					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 7));
					row = sheet.createRow(i);

					cell = row.createCell(0);
					cell.setCellValue("Sub Total");
					cell.setCellStyle(defaultCellStyle);
					ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 7), sheet, wb);

					cell = row.createCell(8);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(I" + startIndex + ":I" + i + ")";
					GrandSIFormula += "I" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);

					cell = row.createCell(9);
					cell.setCellStyle(currencyCellStyle);
					strFormula = "SUM(J" + startIndex + ":J" + i + ")";
					GrandClaimAmountFormula += "J" + (i + 1) + "+";
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(strFormula);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 7));
				row = sheet.createRow(i);

				cell = row.createCell(0);
				cell.setCellValue("Grand Total");
				cell.setCellStyle(defaultCellStyle);
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 7), sheet, wb);

				cell = row.createCell(8);
				cell.setCellStyle(currencyCellStyle);
				GrandSIFormula = GrandSIFormula.substring(0, GrandSIFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandSIFormula);

				cell = row.createCell(9);
				cell.setCellStyle(currencyCellStyle);
				GrandClaimAmountFormula = GrandClaimAmountFormula.substring(0, GrandClaimAmountFormula.length() - 1);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(GrandClaimAmountFormula);

				wb.setPrintArea(0, 0, 9, 0, i);
				wb.write(op);
				op.flush();
				op.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, List<LifeClaimStatusReport>> separateBranch() {
		Map<String, List<LifeClaimStatusReport>> map = new LinkedHashMap<String, List<LifeClaimStatusReport>>();
		if (lifeClaimStatusList != null) {
			for (LifeClaimStatusReport report : lifeClaimStatusList) {
				if (map.containsKey(report.getBranchName())) {
					map.get(report.getBranchName()).add(report);
				} else {
					List<LifeClaimStatusReport> list = new ArrayList<LifeClaimStatusReport>();
					list.add(report);
					map.put(report.getBranchName(), list);
				}
			}
		}
		return map;

	}

}
