package org.ace.insurance.web.manage.report.life;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.common.MonthNames;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.life.report.LifeMonthlyReport;
import org.ace.insurance.report.life.service.interfaces.ILifePolicyMonthlyReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.ExcelUtils;
import org.ace.java.component.SystemException;
import org.ace.java.web.ApplicationSetting;
import org.ace.java.web.common.BaseBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifePolicyMonthlyReportActionBean")
public class LifePolicyMonthlyReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePolicyMonthlyReportService}")
	private ILifePolicyMonthlyReportService lifePolicyMonthlyReportService;

	public void setLifePolicyMonthlyReportService(ILifePolicyMonthlyReportService lifePolicyMonthlyReportService) {
		this.lifePolicyMonthlyReportService = lifePolicyMonthlyReportService;
	}

	private List<LifeMonthlyReport> lifeMonthlyReportList;
	private SummaryReportCriteria criteria;
	private boolean isPublicLifeProduct = false;
	private boolean isGroupLifeProduct = false;
	private boolean isSnakeBiteProduct = false;
	private boolean accessBranches;
	private boolean isGroup;
	private User user;

	@PostConstruct
	public void init() {
		lifeMonthlyReportList = new ArrayList<LifeMonthlyReport>();
		reset();
	}

	public SummaryReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SummaryReportCriteria criteria) {
		this.criteria = criteria;
	}

	public boolean isSnakeBiteProduct() {
		return isSnakeBiteProduct;
	}

	public void setSnakeBiteProduct(boolean isSnakeBiteProduct) {
		this.isSnakeBiteProduct = isSnakeBiteProduct;
	}

	public boolean isPublicLifeProduct() {
		return isPublicLifeProduct;
	}

	public void setPublicLifeProduct(boolean isPublicLifeProduct) {
		this.isPublicLifeProduct = isPublicLifeProduct;
	}

	public boolean isGroupLifeProduct() {
		return isGroupLifeProduct;
	}

	public void setGroupLifeProduct(boolean isGroupLifeProduct) {
		this.isGroupLifeProduct = isGroupLifeProduct;
	}

	public List<LifeMonthlyReport> getLifeMonthlyReportList() {
		if (lifeMonthlyReportList == null) {
			lifeMonthlyReportList = new ArrayList<LifeMonthlyReport>();
		}
		return lifeMonthlyReportList;
	}

	public LifeProductType[] getLifeProductTypes() {
		return LifeProductType.values();
	}

	public EnumSet<MonthNames> getMonthSet() {
		return EnumSet.allOf(MonthNames.class);
	}

	public Map<Integer, Integer> getYears() {
		SortedMap<Integer, Integer> years = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1900; startYear <= endYear; startYear++) {
			years.put(startYear, startYear);
		}
		return years;
	}

	public void reset() {
		criteria = new SummaryReportCriteria();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		user = (User) getParam("LoginUser");
		criteria.setMonth(new Date().getMonth());
		criteria.setYear(cal.get(Calendar.YEAR));
		criteria.setLifeProductType(LifeProductType.PUBLIC_LIFE);
		criteria.setContinuePolicy(true);
		criteria.setBranch(user.getBranch());
		// if (user.isAccessAllBranch()) {
		accessBranches = true;
		// } else {
		criteria.setBranch(user.getBranch());
		// }

		lifeMonthlyReportList = new ArrayList<LifeMonthlyReport>();
		selectdProduct();
	}

	public void search() {
		selectdProduct();
		lifeMonthlyReportList = lifePolicyMonthlyReportService.findLifePolicyMonthlyReportByCriteria(criteria);

	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public void selectdProduct() {
		if (criteria.getLifeProductType() == LifeProductType.PUBLIC_LIFE) {
			isPublicLifeProduct = true;
			isGroupLifeProduct = false;
			isSnakeBiteProduct = false;
		}
		if (criteria.getLifeProductType() == LifeProductType.GROUP_LIFE) {
			isPublicLifeProduct = false;
			isGroupLifeProduct = true;
			isSnakeBiteProduct = false;
			isGroup = true;
		}
		if (criteria.getLifeProductType() == LifeProductType.SNAKE_BITE) {
			isPublicLifeProduct = false;
			isGroupLifeProduct = false;
			isSnakeBiteProduct = true;
			isGroup = false;
		}
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");

		if (isSnakeBiteProduct == false) {

			String fileName = "LifeMonthlyReport.xlsx";
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			try (OutputStream op = ec.getResponseOutputStream();) {
				ExportExcel exportExcel = new ExportExcel(criteria.getYear(), Utils.getMonthString(criteria.getMonth()), lifeMonthlyReportList);
				exportExcel.generate(op);
				getFacesContext().responseComplete();
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export LifeMonthlyReport.xlsx", e);
			}
		}

	}

	private class ExportExcel {
		private int year;
		private String month;
		private List<LifeMonthlyReport> lifeMonthlyReportList;
		private XSSFWorkbook wb;

		public ExportExcel(int year, String month, List<LifeMonthlyReport> lifeMonthlyReportList) {
			this.year = year;
			this.month = month;
			this.lifeMonthlyReportList = lifeMonthlyReportList;
			load();
		}

		private void load() {
			try {

				InputStream inp = this.getClass().getResourceAsStream("/report-template/life/PublicLife_Monthly_Report.xlsx");

				if (criteria.getLifeProductType().equals(LifeProductType.PUBLIC_LIFE)) {
					inp = this.getClass().getResourceAsStream("/report-template/life/PublicLife_Monthly_Report.xlsx");
				}
				if (criteria.getLifeProductType().equals(LifeProductType.GROUP_LIFE)) {
					inp = this.getClass().getResourceAsStream("/report-template/life/GroupLife_Monthly_Report.xlsx");
				}
				if (criteria.getLifeProductType().equals(LifeProductType.SNAKE_BITE)) {

					inp = this.getClass().getResourceAsStream("/report-template/life/SnakeBite_Policy_Monthly_Report.xlsx");

				}

				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load Motor_Monthly_Report.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				XSSFCellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				XSSFCellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				XSSFCellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row = null;
				Cell noCell = null;
				Cell cusNameCell = null;
				Cell ageCell = null;
				Cell policyNoCell = null;
				Cell customerAddressCell = null;
				Cell sumInsuredCell = null;
				Cell periodOfMonthCell = null;
				Cell paymentTypeCell = null;
				Cell premiumCell = null;
				Cell commissionCell = null;
				Cell cashReceiptCell = null;
				Cell agentNameCell = null;
				Cell addressCell = null;
				Cell noOfInsuCell = null;

				if (criteria.getLifeProductType().equals(LifeProductType.PUBLIC_LIFE)) {
					Sheet sheet = wb.getSheet("PublicLife");
					ExcelUtils.fillCompanyLogo(wb, sheet, 10);
					Row companyRow = sheet.getRow(1);
					Cell companyCell = companyRow.getCell(0);
					companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
					Row titleRow = sheet.getRow(2);
					Cell titleCell = titleRow.getCell(0);
					titleCell.setCellValue(year + " \u1001\u102F\u1014\u103E\u1005\u103A " + month
							+ " \u101C\u104A \u101C\u1001\u103B\u102F\u1015\u103A\u1019\u103E\u1010\u103A\u1010\u1019\u103A\u1038 (\u1015\u103C\u100A\u103A\u101E\u1030\u1015\u103C\u100A\u103A\u101E\u102C\u1038\u1021\u101E\u1000\u103A\u1021\u102C\u1019\u1001\u1036)");
					int i = 3;
					int index = 0;
					for (LifeMonthlyReport mmr : lifeMonthlyReportList) {
						i = i + 1;
						index = index + 1;

						row = sheet.createRow(i);
						noCell = row.createCell(0);
						noCell.setCellValue(index);
						noCell.setCellStyle(defaultCellStyle);

						cusNameCell = row.createCell(1);
						cusNameCell.setCellValue(mmr.getCustomerName());
						cusNameCell.setCellStyle(textCellStyle);

						ageCell = row.createCell(2);
						ageCell.setCellValue(mmr.getAge());
						ageCell.setCellStyle(defaultCellStyle);

						policyNoCell = row.createCell(3);
						policyNoCell.setCellValue(mmr.getPolicyNo());
						policyNoCell.setCellStyle(textCellStyle);

						customerAddressCell = row.createCell(4);
						customerAddressCell.setCellValue(mmr.getCustomerAddress());
						customerAddressCell.setCellStyle(textCellStyle);

						sumInsuredCell = row.createCell(5);
						sumInsuredCell.setCellValue(mmr.getSumInsured());
						sumInsuredCell.setCellStyle(currencyCellStyle);

						periodOfMonthCell = row.createCell(6);
						periodOfMonthCell.setCellValue(mmr.getPeriodOfMonth());
						periodOfMonthCell.setCellStyle(defaultCellStyle);

						paymentTypeCell = row.createCell(7);
						paymentTypeCell.setCellValue(mmr.getPaymentType());
						paymentTypeCell.setCellStyle(textCellStyle);

						premiumCell = row.createCell(8);
						premiumCell.setCellValue(mmr.getPremium());
						premiumCell.setCellStyle(currencyCellStyle);

						commissionCell = row.createCell(9);
						commissionCell.setCellValue(mmr.getCommission() + "%");
						commissionCell.setCellStyle(defaultCellStyle);

						cashReceiptCell = row.createCell(10);
						cashReceiptCell.setCellValue(mmr.getCashReceiptAndPaymentDate());
						cashReceiptCell.setCellStyle(textCellStyle);

						agentNameCell = row.createCell(11);
						agentNameCell.setCellValue(mmr.getAgentNameWithLiscenceNo());
						agentNameCell.setCellStyle(textCellStyle);
					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 7));
					row = sheet.createRow(i);
					cusNameCell = row.createCell(0);
					cusNameCell.setCellValue("Total");
					setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 7), sheet, wb);
					cusNameCell.setCellStyle(defaultCellStyle);

					premiumCell = row.createCell(8);
					premiumCell.setCellValue(getTotalPremium(lifeMonthlyReportList));
					premiumCell.setCellStyle(currencyCellStyle);
					wb.setPrintArea(0, "$A$1:$L$" + (i + 1));
				}
				if (criteria.getLifeProductType().equals(LifeProductType.GROUP_LIFE)) {
					Sheet sheet = wb.getSheet("GroupLife");
					ExcelUtils.fillCompanyLogo(wb, sheet, 8);
					Row companyRow = sheet.getRow(1);
					Cell companyCell = companyRow.getCell(0);
					companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
					Row titleRow = sheet.getRow(2);
					Cell titleCell = titleRow.getCell(0);
					titleCell.setCellValue(year + " \u1001\u102F\u1014\u103E\u1005\u103A " + month
							+ " \u101C\u104A \u101C\u1001\u103B\u102F\u1015\u103A\u1019\u103E\u1010\u103A\u1010\u1019\u103A\u1038 (\u1005\u102F\u1015\u1031\u102B\u1004\u103A\u1038\u1021\u101E\u1000\u103A\u1021\u102C\u1019\u1001\u1036)");
					int i = 3;
					int index = 0;
					for (LifeMonthlyReport mmr : lifeMonthlyReportList) {
						i = i + 1;
						index = index + 1;

						row = sheet.createRow(i);
						noCell = row.createCell(0);
						noCell.setCellValue(index);
						noCell.setCellStyle(textCellStyle);

						cusNameCell = row.createCell(1);
						cusNameCell.setCellValue(mmr.getCustomerName());
						cusNameCell.setCellStyle(textCellStyle);

						policyNoCell = row.createCell(2);
						policyNoCell.setCellValue(mmr.getPolicyNo());
						policyNoCell.setCellStyle(textCellStyle);

						addressCell = row.createCell(3);
						addressCell.setCellValue(mmr.getCustomerAddress());
						addressCell.setCellStyle(textCellStyle);

						sumInsuredCell = row.createCell(4);
						sumInsuredCell.setCellValue(mmr.getSumInsured());
						sumInsuredCell.setCellStyle(currencyCellStyle);

						noOfInsuCell = row.createCell(5);
						noOfInsuCell.setCellValue(mmr.getNoOfInsu());
						noOfInsuCell.setCellStyle(defaultCellStyle);

						premiumCell = row.createCell(6);
						premiumCell.setCellValue(mmr.getPremium());
						premiumCell.setCellStyle(currencyCellStyle);

						commissionCell = row.createCell(7);
						commissionCell.setCellValue(mmr.getCommission() + "%");
						commissionCell.setCellStyle(defaultCellStyle);

						cashReceiptCell = row.createCell(8);
						cashReceiptCell.setCellValue(mmr.getCashReceiptAndPaymentDate());
						cashReceiptCell.setCellStyle(textCellStyle);

						agentNameCell = row.createCell(9);
						agentNameCell.setCellValue(mmr.getAgentNameWithLiscenceNo());
						agentNameCell.setCellStyle(textCellStyle);
					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 5));
					row = sheet.createRow(i);
					cusNameCell = row.createCell(0);
					cusNameCell.setCellValue("Total");
					setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 5), sheet, wb);
					cusNameCell.setCellStyle(defaultCellStyle);

					cusNameCell = row.createCell(6);
					cusNameCell.setCellValue(getTotalPremium(lifeMonthlyReportList));
					cusNameCell.setCellStyle(currencyCellStyle);
					wb.setPrintArea(0, "$A$1:$J$" + (i + 1));
				}

				// start skb
				if (criteria.getLifeProductType().equals(LifeProductType.SNAKE_BITE)) {
					Sheet sheet = wb.getSheet("SnakeBite");
					ExcelUtils.fillCompanyLogo(wb, sheet, 5);
					Row companyRow = sheet.getRow(1);
					Cell companyCell = companyRow.getCell(0);
					companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
					Row titleRow = sheet.getRow(2);
					Cell titleCell = titleRow.getCell(0);
					titleCell.setCellValue(year + " \u1001\u102F\u1014\u103E\u1005\u103A " + month
							+ " \u101C\u104A \u101C\u1001\u103B\u102F\u1015\u103A\u1019\u103E\u1010\u103A\u1010\u1019\u103A\u1038 (   \u1021\u101E\u1000\u103A\u1021\u102C\u1019\u1001\u1036)");
					int i = 3;
					int index = 0;
					for (LifeMonthlyReport mmr : lifeMonthlyReportList) {
						i = i + 1;
						index = index + 1;
						row = sheet.createRow(i);

						noCell = row.createCell(0);
						noCell.setCellValue(index);
						noCell.setCellStyle(textCellStyle);

						cusNameCell = row.createCell(1);
						cusNameCell.setCellValue(mmr.getCustomerName());
						cusNameCell.setCellStyle(textCellStyle);

						customerAddressCell = row.createCell(2);
						customerAddressCell.setCellValue(mmr.getCustomerAddress());
						customerAddressCell.setCellStyle(textCellStyle);

						sumInsuredCell = row.createCell(3);
						sumInsuredCell.setCellValue(mmr.getSumInsured());
						sumInsuredCell.setCellStyle(currencyCellStyle);

						premiumCell = row.createCell(4);
						premiumCell.setCellValue(mmr.getPremium());
						premiumCell.setCellStyle(currencyCellStyle);

						cashReceiptCell = row.createCell(5);
						cashReceiptCell.setCellValue(mmr.getCashReceiptAndPaymentDate());
						cashReceiptCell.setCellStyle(textCellStyle);

						commissionCell = row.createCell(6);
						commissionCell.setCellValue(mmr.getCommission() + "%");
						commissionCell.setCellStyle(textCellStyle);

						agentNameCell = row.createCell(7);
						agentNameCell.setCellValue(mmr.getAgentNameWithLiscenceNo());
						agentNameCell.setCellStyle(textCellStyle);
					}
					i = i + 1;
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
					row = sheet.createRow(i);
					cusNameCell = row.createCell(0);
					cusNameCell.setCellValue("Total");
					setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 2), sheet, wb);
					cusNameCell.setCellStyle(defaultCellStyle);

					cusNameCell = row.createCell(3);
					cusNameCell.setCellValue(getTotalPremium(lifeMonthlyReportList));
					cusNameCell.setCellStyle(currencyCellStyle);
					wb.setPrintArea(0, 0, 7, 0, i);
				}
				// end skb

				wb.write(op);
				op.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (op != null) {
					try {
						op.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void setRegionBorder(int borderWidth, CellRangeAddress crAddress, Sheet sheet, Workbook workBook) {
			RegionUtil.setBorderTop(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderBottom(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderRight(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderLeft(borderWidth, crAddress, sheet, workBook);
		}

		public double getTotalPremium(List<LifeMonthlyReport> lifeMonthlyReportList) {
			double premium = 0.0;
			for (LifeMonthlyReport m : lifeMonthlyReportList) {
				premium += m.getPremium();
			}
			return premium;
		}

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
}
