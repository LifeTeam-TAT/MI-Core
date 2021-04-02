package org.ace.insurance.web.manage.report.agent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.AgentSaleInsuranceType;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.agent.AgentSaleCriteria;
import org.ace.insurance.report.agent.AgentSaleReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AgentSaleReportActionBean")
public class AgentSaleReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AgentSaleReportService}")
	private IAgentSaleReportService agentSaleReportService;

	public void setAgentSaleReportService(IAgentSaleReportService agentSaleReportService) {
		this.agentSaleReportService = agentSaleReportService;
	}

	private AgentSaleCriteria criteria;
	private List<AgentSaleReport> reportList;
	private boolean isLifeInsurance;

	@PostConstruct
	public void init() {
		reset();
	}

	public void filter() {
		if (criteria.getInsuranceType().equals(AgentSaleInsuranceType.LIFE)) {
			reportList = agentSaleReportService.findForLife(criteria);
			isLifeInsurance = true;
		} else {
			reportList = agentSaleReportService.findForGrneral(criteria);
			isLifeInsurance = false;
		}
	}

	public boolean isLifeInsurance() {
		return isLifeInsurance;
	}

	public void reset() {
		criteria = new AgentSaleCriteria();
		criteria.setInsuranceType(AgentSaleInsuranceType.GENERAL);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		filter();
	}

	public AgentSaleCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AgentSaleCriteria criteria) {
		this.criteria = criteria;
	}

	public List<AgentSaleReport> getReportList() {
		return reportList;
	}

	public AgentSaleInsuranceType[] getInsuranceTypes() {
		return AgentSaleInsuranceType.values();
	}

	public void exportExcel() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName = "AgentSale_Monthly_Report.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel exportExcel = new ExportExcel(reportList);
			exportExcel.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export Fire_Monthly_Report.xlsx", e);
		}
	}

	private class ExportExcel {
		private List<AgentSaleReport> agentSaleReportsList;
		private XSSFWorkbook wb;

		public ExportExcel(List<AgentSaleReport> agentSaleReportsList) {
			this.agentSaleReportsList = agentSaleReportsList;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_Monthly_Report.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentSale_Monthly_Report.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("AgentSaleReport");
				ExcelUtils.fillCompanyLogo(wb, sheet, 18);
				Row companyRow = sheet.getRow(1);
				Cell companyCell = companyRow.getCell(0);
				companyCell.setCellValue(ApplicationSetting.getCompanyLabel());
				Row titleRow = sheet.getRow(2);
				Cell titleCell = titleRow.getCell(0);
				titleCell.setCellValue("Agent Sale Report");

				CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				CellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				CellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row;
				Cell noCell;
				Cell agentNameCell;
				Cell agentCodeCell;
				Cell firePolicyCountCell;
				Cell firePolicyPremiumCell;
				Cell firePolictCommissionCell;
				Cell motorPolicyCountCell;
				Cell motorPolicyPremiumCell;
				Cell motorPolicyCommissionCell;
				Cell cargoCountCell;
				Cell cargoPremiumCell;
				Cell cargoCommissionCell;
				Cell cisCountCell;
				Cell cisPremiumCell;
				Cell cisCommissionCell;
				Cell citCountCell;
				Cell citPremiumCell;
				Cell citCommissionCell;
				Cell totalCountCell;
				Cell totalPremiumCell;
				Cell totalCommissionCell;

				int i = 4;
				int index = 0;
				for (AgentSaleReport asr : reportList) {

					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);

					noCell = row.createCell(0);
					noCell.setCellValue(index);
					noCell.setCellStyle(defaultCellStyle);

					agentNameCell = row.createCell(1);
					agentNameCell.setCellValue(asr.getAgentName());
					agentNameCell.setCellStyle(textCellStyle);

					agentCodeCell = row.createCell(2);
					agentCodeCell.setCellValue(asr.getAgentCodeNo());
					agentCodeCell.setCellStyle(textCellStyle);

					firePolicyCountCell = row.createCell(3);
					firePolicyCountCell.setCellValue(asr.getFirePolicyCount());
					firePolicyCountCell.setCellStyle(defaultCellStyle);

					firePolicyPremiumCell = row.createCell(4);
					firePolicyPremiumCell.setCellValue(asr.getFirePolicyTotalPremium());
					firePolicyPremiumCell.setCellStyle(currencyCellStyle);

					firePolictCommissionCell = row.createCell(5);
					firePolictCommissionCell.setCellValue(asr.getFirePolicyTotalCommission());
					firePolictCommissionCell.setCellStyle(currencyCellStyle);

					motorPolicyCountCell = row.createCell(6);
					motorPolicyCountCell.setCellValue(asr.getMotorPolicyCount());
					motorPolicyCountCell.setCellStyle(defaultCellStyle);

					motorPolicyPremiumCell = row.createCell(7);
					motorPolicyPremiumCell.setCellValue(asr.getMotorPolicyTotalPremium());
					motorPolicyPremiumCell.setCellStyle(currencyCellStyle);

					motorPolicyCommissionCell = row.createCell(8);
					motorPolicyCommissionCell.setCellValue(asr.getMotorPolicyTotalCommission());
					motorPolicyCommissionCell.setCellStyle(currencyCellStyle);

					cargoCountCell = row.createCell(9);
					cargoCountCell.setCellValue(asr.getCargoPolicyCount());
					cargoCountCell.setCellStyle(defaultCellStyle);

					cargoPremiumCell = row.createCell(10);
					cargoPremiumCell.setCellValue(asr.getCargoPolicyTotalPremium());
					cargoPremiumCell.setCellStyle(currencyCellStyle);

					cargoCommissionCell = row.createCell(11);
					cargoCommissionCell.setCellValue(asr.getCargoPolicyTotalCommission());
					cargoCommissionCell.setCellStyle(currencyCellStyle);

					cisCountCell = row.createCell(12);
					cisCountCell.setCellValue(asr.getCisCount());
					cisCountCell.setCellStyle(defaultCellStyle);

					cisPremiumCell = row.createCell(13);
					cisPremiumCell.setCellValue(asr.getCisCount());
					cisPremiumCell.setCellStyle(currencyCellStyle);

					cisCommissionCell = row.createCell(14);
					cisCommissionCell.setCellValue(asr.getCisCommission());
					cisCommissionCell.setCellStyle(currencyCellStyle);

					citCountCell = row.createCell(15);
					citCountCell.setCellValue(asr.getCitCount());
					citCountCell.setCellStyle(defaultCellStyle);

					citPremiumCell = row.createCell(16);
					citPremiumCell.setCellValue(asr.getCitPremium());
					citPremiumCell.setCellStyle(currencyCellStyle);

					citCommissionCell = row.createCell(17);
					citCommissionCell.setCellValue(asr.getCitCommission());
					citCommissionCell.setCellStyle(currencyCellStyle);

					totalCountCell = row.createCell(18);
					totalCountCell.setCellValue(asr.getPolicyCount());
					totalCountCell.setCellStyle(defaultCellStyle);

					totalPremiumCell = row.createCell(19);
					totalPremiumCell.setCellValue(asr.getTotalPremium());
					totalPremiumCell.setCellStyle(currencyCellStyle);

					totalCommissionCell = row.createCell(20);
					totalCommissionCell.setCellValue(asr.getTotalCommission());
					totalCommissionCell.setCellStyle(currencyCellStyle);
				}
				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 17));
				row = sheet.createRow(i);
				agentNameCell = row.createCell(0);
				agentNameCell.setCellValue("Sub Total");
				ExcelUtils.setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 17), sheet, wb);
				agentNameCell.setCellStyle(defaultCellStyle);

				totalCountCell = row.createCell(18);
				totalCountCell.setCellValue(getTotalPolicyCount());
				totalCountCell.setCellStyle(defaultCellStyle);

				totalPremiumCell = row.createCell(19);
				totalPremiumCell.setCellValue(getTotalPremium());
				totalPremiumCell.setCellStyle(currencyCellStyle);

				totalCommissionCell = row.createCell(20);
				totalCommissionCell.setCellValue(getTotalCommission());
				totalCommissionCell.setCellStyle(currencyCellStyle);

				wb.setPrintArea(0, 0, 20, 0, i);
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

		private double getTotalPremium() {
			double result = 0.0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getTotalPremium();
			}
			return result;
		}

		private double getTotalCommission() {
			double result = 0.0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getTotalCommission();
			}
			return result;
		}

		private int getTotalPolicyCount() {
			int result = 0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getPolicyCount();
			}
			return result;
		}

	}

	public void exportExcel1() {
		ExternalContext ec = getFacesContext().getExternalContext();
		ec.responseReset();
		ec.setResponseContentType("application/vnd.ms-excel");
		String fileName2 = "AgentSale_Monthly_Report1.xlsx";
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName2 + "\"");
		try {
			OutputStream op = ec.getResponseOutputStream();
			ExportExcel1 exportExcel1 = new ExportExcel1(reportList);
			exportExcel1.generate(op);
			getFacesContext().responseComplete();
		} catch (IOException e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to export AgentSale_Monthly_Report1.xlsx", e);
		}
	}

	private class ExportExcel1 {
		private List<AgentSaleReport> agentSaleReportList1;
		private XSSFWorkbook wb;

		public ExportExcel1(List<AgentSaleReport> agentSaleReportsList1) {
			this.agentSaleReportList1 = agentSaleReportsList1;
			load();
		}

		private void load() {
			try {
				InputStream inp = this.getClass().getResourceAsStream("/report-template/agent/AgentSale_Monthly_Report1.xlsx");
				wb = new XSSFWorkbook(inp);
			} catch (IOException e) {
				throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load AgentSale_Monthly_Report1.xlsx tempalte", e);
			}
		}

		public void generate(OutputStream op) {
			try {
				Sheet sheet = wb.getSheet("AgentSaleReport1");

				Row titleRow = sheet.getRow(2);
				Cell titleCell = titleRow.getCell(0);
				titleCell.setCellValue("Agent Sale Report");

				CellStyle defaultCellStyle = ExcelUtils.getDefaultCellStyle(wb);
				CellStyle textCellStyle = ExcelUtils.getTextCellStyle(wb);
				CellStyle currencyCellStyle = ExcelUtils.getCurrencyCellStyle(wb);

				Row row = null;
				Cell noCell = null;
				Cell agentNameCell = null;
				Cell agentCodeCell = null;
				Cell groupLifeCountCell = null;
				Cell groupLifePremiumCell = null;
				Cell groupLifeCommissionCell = null;
				Cell publicLifeCountCell = null;
				Cell publicLifePremiumCell = null;
				Cell publicLifeCommissionCell = null;
				Cell snakeBiteCountCell = null;
				Cell snakeBitePremiumCell = null;
				Cell snakeBiteCommissionCell = null;
				Cell sportManCountCell = null;
				Cell sportManPremiumCell = null;
				Cell sportManCommissionCell = null;
				Cell medicalCountCell = null;
				Cell medicalPremiumCell = null;
				Cell medicalCommissionCell = null;
				Cell travelCell = null;
				Cell travelPremiumCell = null;
				Cell travelCommissionCell = null;
				Cell totalCountCell = null;
				Cell totalPremiumCell = null;
				Cell totalCommissionCell = null;

				int i = 4;
				int index = 0;
				for (AgentSaleReport asr : reportList) {

					i = i + 1;
					index = index + 1;
					row = sheet.createRow(i);

					noCell = row.createCell(0);
					noCell.setCellValue(index);
					noCell.setCellStyle(defaultCellStyle);

					agentNameCell = row.createCell(1);
					agentNameCell.setCellValue(asr.getAgentName());
					agentNameCell.setCellStyle(textCellStyle);

					agentCodeCell = row.createCell(2);
					agentCodeCell.setCellValue(asr.getAgentCodeNo());
					agentCodeCell.setCellStyle(textCellStyle);

					groupLifeCountCell = row.createCell(3);
					groupLifeCountCell.setCellValue(asr.getGroupLifeCount());
					groupLifeCountCell.setCellStyle(defaultCellStyle);

					groupLifePremiumCell = row.createCell(4);
					groupLifePremiumCell.setCellValue(asr.getGroupLifePremium());
					groupLifePremiumCell.setCellStyle(currencyCellStyle);

					groupLifeCommissionCell = row.createCell(5);
					groupLifeCommissionCell.setCellValue(asr.getGroupLifeCommission());
					groupLifeCommissionCell.setCellStyle(currencyCellStyle);

					publicLifeCountCell = row.createCell(6);
					publicLifeCountCell.setCellValue(asr.getPublicLifeCount());
					publicLifeCountCell.setCellStyle(defaultCellStyle);

					publicLifePremiumCell = row.createCell(7);
					publicLifePremiumCell.setCellValue(asr.getPublicLifePremium());
					publicLifePremiumCell.setCellStyle(currencyCellStyle);

					publicLifeCommissionCell = row.createCell(8);
					publicLifeCommissionCell.setCellValue(asr.getPublicLifeCommission());
					publicLifeCommissionCell.setCellStyle(currencyCellStyle);

					snakeBiteCountCell = row.createCell(9);
					snakeBiteCountCell.setCellValue(asr.getSankeBiteCount());
					snakeBiteCountCell.setCellStyle(defaultCellStyle);

					snakeBitePremiumCell = row.createCell(10);
					snakeBitePremiumCell.setCellValue(asr.getSnakeBitePremium());
					snakeBitePremiumCell.setCellStyle(currencyCellStyle);

					snakeBiteCommissionCell = row.createCell(11);
					snakeBiteCommissionCell.setCellValue(asr.getSankeBiteCommission());
					snakeBiteCommissionCell.setCellStyle(currencyCellStyle);

					sportManCountCell = row.createCell(12);
					sportManCountCell.setCellValue(asr.getSportManPolicyCount());
					sportManCountCell.setCellStyle(defaultCellStyle);

					sportManPremiumCell = row.createCell(13);
					sportManPremiumCell.setCellValue(asr.getSportMenPremium());
					sportManPremiumCell.setCellStyle(currencyCellStyle);

					sportManCommissionCell = row.createCell(14);
					sportManCommissionCell.setCellValue(asr.getSportMenCommission());
					sportManCommissionCell.setCellStyle(currencyCellStyle);

					medicalCountCell = row.createCell(15);
					medicalCountCell.setCellValue(asr.getMedicalPolicyCount());
					medicalCountCell.setCellStyle(defaultCellStyle);

					medicalPremiumCell = row.createCell(16);
					medicalPremiumCell.setCellValue(asr.getMedicalPremium());
					medicalPremiumCell.setCellStyle(currencyCellStyle);

					medicalCommissionCell = row.createCell(17);
					medicalCommissionCell.setCellValue(asr.getMedicalCommission());
					medicalCommissionCell.setCellStyle(currencyCellStyle);

					travelCell = row.createCell(18);
					travelCell.setCellValue(asr.getTravelPolicyCount());
					travelCell.setCellStyle(currencyCellStyle);
					travelCell.getCellStyle().setWrapText(true);
					travelCell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);

					travelPremiumCell = row.createCell(19);
					travelPremiumCell.setCellValue(asr.getTravelPremium());
					travelPremiumCell.setCellStyle(currencyCellStyle);

					travelCommissionCell = row.createCell(20);
					travelCommissionCell.setCellValue(asr.getTravelCommission());
					travelCommissionCell.setCellStyle(currencyCellStyle);

					totalCountCell = row.createCell(21);
					totalCountCell.setCellValue(asr.getPolicyCount());
					totalCountCell.setCellStyle(currencyCellStyle);

					totalPremiumCell = row.createCell(22);
					totalPremiumCell.setCellValue(asr.getTotalPremium());
					totalPremiumCell.setCellStyle(currencyCellStyle);

					totalCommissionCell = row.createCell(23);
					totalCommissionCell.setCellValue(asr.getTotalCommission());
					totalCommissionCell.setCellStyle(currencyCellStyle);
				}

				i = i + 1;
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 20));
				row = sheet.createRow(i);
				agentNameCell = row.createCell(0);
				agentNameCell.setCellValue("Sub Total");
				setRegionBorder(CellStyle.BORDER_THIN, new CellRangeAddress(i, i, 0, 20), sheet, wb);
				agentNameCell.getCellStyle().setAlignment(CellStyle.ALIGN_RIGHT);

				totalCountCell = row.createCell(21);
				totalCountCell.setCellValue(getTotalPolicyCount());
				totalCountCell.setCellStyle(defaultCellStyle);

				totalPremiumCell = row.createCell(22);
				totalPremiumCell.setCellValue(getTotalPremium());
				totalPremiumCell.setCellStyle(currencyCellStyle);

				totalCommissionCell = row.createCell(23);
				totalCommissionCell.setCellValue(getTotalCommission());
				totalCommissionCell.setCellStyle(currencyCellStyle);

				wb.setPrintArea(0, 0, 23, 0, i);
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

		private double getTotalPremium() {
			double result = 0.0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getTotalPremium();
			}
			return result;
		}

		private double getTotalCommission() {
			double result = 0.0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getTotalCommission();
			}
			return result;
		}

		private int getTotalPolicyCount() {
			int result = 0;
			for (AgentSaleReport asr : reportList) {
				result = result + asr.getPolicyCount();
			}
			return result;
		}

		private void setRegionBorder(int borderWidth, CellRangeAddress crAddress, Sheet sheet, Workbook workBook) {
			RegionUtil.setBorderTop(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderBottom(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderRight(borderWidth, crAddress, sheet, workBook);
			RegionUtil.setBorderLeft(borderWidth, crAddress, sheet, workBook);
		}
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		criteria.setBranch(branch);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

}
